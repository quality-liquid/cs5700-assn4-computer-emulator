import kotlin.test.*
import java.io.File
import java.io.FileNotFoundException

class EmulatorTest {
    
    @Test
    fun testEmulatorCreation() {
        // Create a temporary test file first
        val testFile = File("test_creation_rom.out")
        testFile.writeBytes(byteArrayOf(0x01.toByte(), 0x02.toByte()))
        
        try {
            val emulator = Emulator { "test_creation_rom.out" }
            assertNotNull(emulator.CPU)
            assertNotNull(emulator.ROM)
        } finally {
            testFile.delete()
        }
    }
    
    @Test
    fun testLoadProgramValidFile() {
        // Create a temporary test file
        val testFile = File("test_rom.out")
        testFile.writeBytes(byteArrayOf(0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte()))
        
        try {
            // Use inputProvider to avoid console input
            val emulator = Emulator { "test_rom.out" }
            val rom = emulator.ROM // ROM is already loaded in constructor
            
            assertEquals(4, rom.size)
            assertEquals(0x01u, rom.read(0u))
            assertEquals(0x02u, rom.read(1u))
            assertEquals(0x03u, rom.read(2u))
            assertEquals(0x04u, rom.read(3u))
        } finally {
            testFile.delete()
        }
    }
    
    @Test
    fun testLoadProgramInvalidFile() {
        assertFailsWith<FileNotFoundException> {
            Emulator { "nonexistent_file.out" }
        }
    }
    
    @Test
    fun testLoadProgramMethod() {
        // Test the loadProgram method directly
        val testFile = File("test_load_method.out")
        testFile.writeBytes(byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte()))
        
        try {
            // Create emulator with dummy file first
            val dummyFile = File("dummy.out")
            dummyFile.writeBytes(byteArrayOf(0x00.toByte()))
            
            try {
                val emulator = Emulator { "dummy.out" }
                val rom = emulator.loadProgram { "test_load_method.out" }
                
                assertEquals(3, rom.size)
                assertEquals(0xABu, rom.read(0u))
                assertEquals(0xCDu, rom.read(1u))
                assertEquals(0xEFu, rom.read(2u))
            } finally {
                dummyFile.delete()
            }
        } finally {
            testFile.delete()
        }
    }
}