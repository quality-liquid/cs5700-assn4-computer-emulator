import memory.ROM
import kotlin.test.*

class ROMTest {
    
    @Test
    fun testROMSize() {
        val data = ubyteArrayOf(1u, 2u, 3u, 4u)
        val rom = ROM(data)
        assertEquals(4, rom.size)
    }
    
    @Test
    fun testROMRead() {
        val data = ubyteArrayOf(0x12u, 0x34u, 0x56u, 0x78u)
        val rom = ROM(data)
        
        assertEquals(0x12u, rom.read(0u))
        assertEquals(0x34u, rom.read(1u))
        assertEquals(0x56u, rom.read(2u))
        assertEquals(0x78u, rom.read(3u))
    }
    
    @Test
    fun testROMWriteThrowsException() {
        val data = ubyteArrayOf(1u, 2u, 3u, 4u)
        val rom = ROM(data)
        
        assertFailsWith<UnsupportedOperationException> {
            rom.write(0u, 100u)
        }
    }
    
    @Test
    fun testEmptyROM() {
        val rom = ROM(ubyteArrayOf())
        assertEquals(0, rom.size)
    }
}
