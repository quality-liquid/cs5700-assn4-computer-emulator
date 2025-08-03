@file:OptIn(ExperimentalUnsignedTypes::class)
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
        println(instruction)
        // todo
    }

}