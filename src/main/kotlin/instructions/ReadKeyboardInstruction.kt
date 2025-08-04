package instructions

import kotlinx.coroutines.*
import java.util.concurrent.CompletableFuture

class ReadKeyboardInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()
        
        nibbles["dest"] = ((uInstruction shr 8) and 0x0fu).toInt()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        println("Enter up to one byte as one or two hex digits (0-f): ")
        
        // Use a separate thread for input to avoid blocking the CPU
        val inputFuture = CompletableFuture.supplyAsync {
            readln()
        }
        
        // Get the input (this will block, but timer continues in separate coroutine)
        val value = inputFuture.get()
        val valueAsByte: UByte = try {
            value.toUByte(16) // Parse as hex
        } catch (e: NumberFormatException) {
            0u // Default to 0 if invalid input
        }
        
        context.registerMap[nibbles["dest"]]?.value = valueAsByte
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}