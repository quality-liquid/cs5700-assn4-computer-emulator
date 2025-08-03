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

    private fun executeInstruction(instruction: Int) {
        // get the first nibble
        val opcode: Int = instruction shr 12
        val instruction: InstructionTemplate = instructionFactory(opcode)
        // todo: execute instruction template method
        // todo: implement each instruction
    }

    private fun instructionFactory(opcode: Int): InstructionTemplate {
        when (opcode) {
            0x0 -> StoreInstruction()
            0x1 -> AddInstruction()
            0x2 -> SubInstruction()
            0x3 -> ReadInstruction()
            0x4 -> WriteInstruction()
            0x5 -> JumpInstruction()
            0x6 -> ReadKeyboardInstruction()
            0x7 -> SwitchMemoryInstruction()
            0x8 -> SkipEqualInstruction()
            0x9 -> SkipNotEqualInstruction()
            0xa -> SetAInstruction()
            0xb -> SetTInstruction()
            0xc -> ReadTInstruction()
            0xd -> ConvertToBaseTenInstruction()
            0xe -> ConvertByteToASCIIInstruction()
            0xf -> DrawInstruction()
        }
    }

}