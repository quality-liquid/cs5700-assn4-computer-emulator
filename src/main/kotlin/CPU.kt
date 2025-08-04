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
import kotlinx.coroutines.*

class CPU(val ROM: MemoryAdapter) {
    val registers: CPURegisters = CPURegisters()
    val RAM: MemoryAdapter = RAM(4096)
    val SCREEN_BUFFER = ScreenBuffer(64)
    val SCREEN = Screen()
    
    private var isRunning = false
    private var cpuJob: Job? = null
    private var timerJob: Job? = null

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
        SCREEN_BUFFER.subscribe(SCREEN)
        isRunning = true
        
        // Start timer in separate coroutine
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (isRunning) {
                if (registers.T.value > 0u) {
                    registers.T.value = (registers.T.value - 1u).toUByte()
                }
                delay(1000 / 60) // 60Hz timer
            }
        }
        
        // Start CPU execution in separate coroutine
        cpuJob = CoroutineScope(Dispatchers.Default).launch {
            while (isRunning) {
                step()
                delay(2) // 500Hz CPU clock
            }
        }
        
        // Wait for completion
        runBlocking {
            cpuJob?.join()
        }
    }

    fun stop() {
        isRunning = false
        cpuJob?.cancel()
        timerJob?.cancel()
    }

    fun step() {
        if (registers.P + 1u >= ROM.size.toUInt()) {
            println("End of program or out of bounds")
            stop()
            return
        }

        val high = ROM.read(registers.P)
        val low = ROM.read((registers.P + 1u).toUShort())
        val instruction = (high.toInt() shl 8) or low.toInt()

        executeInstruction(instruction)
    }

    private fun executeInstruction(rawInstruction: Int) {
        // get the first nibble
        val opcode: Int = rawInstruction shr 12
        
        // Simple heuristic: if we encounter an instruction that would access 
        // an invalid register (like WriteInstruction with reg 8+), it's likely data
        if (opcode == 0x4) { // WriteInstruction
            val srcReg = ((rawInstruction shr 8) and 0x0f)
            if (srcReg > 7) {
                println("Detected data section (invalid register $srcReg). Halting execution.")
                stop()
                return
            }
        }
        
        val instruction: InstructionTemplate = instructionFactory(opcode)
        instruction.execute(rawInstruction, CPUContext(this.registers, this.RAM,
            this.SCREEN_BUFFER, this.ROM))
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