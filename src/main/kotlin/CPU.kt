@file:OptIn(ExperimentalUnsignedTypes::class)
import instructions.AddInstruction
import instructions.ConvertByteToASCIIInstruction
import instructions.ConvertToBaseTenInstruction
import instructions.DrawInstruction
import instructions.InstructionTemplate
import instructions.JumpInstruction
import instructions.ReadInstruction
import instructions.ReadKeyboardInstruction
import instructions.ReadTInstruction
import instructions.SetAInstruction
import instructions.SetTInstruction
import instructions.SkipEqualInstruction
import instructions.SkipNotEqualInstruction
import instructions.StoreInstruction
import instructions.SubInstruction
import instructions.SwitchMemoryInstruction
import instructions.WriteInstruction
import memory.MemoryAdapter
import memory.RAM
import memory.ScreenBuffer
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CPU(val ROM: MemoryAdapter) {
    val registers: CPURegisters = CPURegisters()
    val RAM: MemoryAdapter = RAM(4096)
    val SCREEN_BUFFER: MemoryAdapter = ScreenBuffer(4096)
    val executor = Executors.newSingleThreadScheduledExecutor()

    class CPUContext(
        val registers: CPURegisters,
        val ram: MemoryAdapter,
        val screenBuffer: MemoryAdapter,
        val rom: MemoryAdapter
        ) {
        val registerMap = mutableMapOf<Int, Register>(
            0 to registers.r0,
            1 to registers.r1,
            2 to registers.r2,
            3 to registers.r3,
            4 to registers.r4,
            5 to registers.r5,
            6 to registers.r6,
            7 to registers.r7
        )
    }


    fun executeProgramInROM() {
        executor.scheduleAtFixedRate( {step()}, 0, 2, TimeUnit.MILLISECONDS)
    }

    fun step() {
        if (registers.P + 1u >= ROM.size.toUInt()) {
            println("End of program or out of bounds")
            executor.shutdown()
            return
        }

        val high = ROM.read(registers.P)
        val low = ROM.read((registers.P + 1u).toUShort())
        val instruction = (high.toInt() shl 8) or low.toInt()

        executeInstruction(instruction)

        registers.P = (registers.P + 2u).toUShort()
    }

    private fun executeInstruction(rawInstruction: Int) {
        // get the first nibble
        val opcode: Int = rawInstruction shr 12
        val instruction: InstructionTemplate = instructionFactory(opcode)
        instruction.execute(rawInstruction, CPUContext(this.registers, this.RAM, this.SCREEN_BUFFER, this.ROM))
    }

    private fun instructionFactory(opcode: Int): InstructionTemplate {
        when (opcode) {
            0x0 -> return StoreInstruction()
            0x1 -> return AddInstruction()
            0x2 -> return SubInstruction()
            0x3 -> return ReadInstruction()
            0x4 -> return WriteInstruction()
            0x5 -> return JumpInstruction()
            0x6 -> return ReadKeyboardInstruction()
            0x7 -> return SwitchMemoryInstruction()
            0x8 -> return SkipEqualInstruction()
            0x9 -> return SkipNotEqualInstruction()
            0xa -> return SetAInstruction()
            0xb -> return SetTInstruction()
            0xc -> return ReadTInstruction()
            0xd -> return ConvertToBaseTenInstruction()
            0xe -> return ConvertByteToASCIIInstruction()
            0xf -> return DrawInstruction()
            else -> throw UnsupportedOperationException("Invalid OpCode")
        }
    }

}