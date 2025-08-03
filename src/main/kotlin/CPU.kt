import memory.MemoryAdapter
import memory.RAM
import memory.ROM
import memory.ScreenBuffer

class CPU {
    val registers: CPURegisters = CPURegisters()
    val RAM: MemoryAdapter = RAM()
    val ROM: MemoryAdapter = ROM()
    val SCREEN_BUFFER: MemoryAdapter = ScreenBuffer()

    // todo
}