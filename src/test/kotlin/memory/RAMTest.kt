import memory.RAM
import kotlin.test.*

class RAMTest {
    
    @Test
    fun testRAMSize() {
        val ram = RAM(1024)
        assertEquals(1024, ram.size)
    }
    
    @Test
    fun testRAMReadWrite() {
        val ram = RAM(100)
        
        ram.write(50u, 123u)
        assertEquals(123u, ram.read(50u))
    }
    
    @Test
    fun testRAMDefaultValue() {
        val ram = RAM(100)
        assertEquals(0u, ram.read(0u))
        assertEquals(0u, ram.read(99u))
    }
    
    @Test
    fun testRAMMultipleWrites() {
        val ram = RAM(100)
        
        ram.write(0u, 10u)
        ram.write(1u, 20u)
        ram.write(99u, 255u)
        
        assertEquals(10u, ram.read(0u))
        assertEquals(20u, ram.read(1u))
        assertEquals(255u, ram.read(99u))
    }
}
