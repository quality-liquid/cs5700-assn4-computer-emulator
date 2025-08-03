import kotlin.test.*

class ROMTest {
    private lateinit var emulator: Emulator

    @BeforeTest
    fun setup() {
        emulator = Emulator(inputProvider = { "roms/addition.out" })
    }

    @Test
    fun TestROMLoads() {
        assertNotEquals(0u, emulator.CPU.ROM.read(0xFu))
    }
}