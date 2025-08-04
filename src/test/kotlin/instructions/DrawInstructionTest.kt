import instructions.DrawInstruction
import memory.RAM
import memory.ROM
import memory.ScreenBuffer
import kotlin.test.*

class DrawInstructionTest {
    
    private fun createTestContext(): CPU.CPUContext {
        val registers = CPURegisters()
        val ram = RAM(100)
        val screenBuffer = ScreenBuffer(64)
        val rom = ROM(ubyteArrayOf())
        return CPU.CPUContext(registers, ram, screenBuffer, rom)
    }
    
    @Test
    fun testParseNibbles() {
        val instruction = DrawInstruction()
        val nibbles = instruction.parseNibbles(0xF123)
        
        assertEquals(1, nibbles["src"])
        assertEquals(2, nibbles["row"])
        assertEquals(3, nibbles["col"])
    }
    
    @Test
    fun testPerformOperation() {
        val instruction = DrawInstruction()
        val context = createTestContext()
        
        context.registers.r5.value = 65u // 'A'
        
        val nibbles = mapOf("src" to 5, "row" to 2, "col" to 3)
        instruction.performOperation(nibbles, context)
        
        // Address = row * 8 + col = 2 * 8 + 3 = 19
        assertEquals(65u, context.screenBuffer.read(19u))
    }
    
    @Test
    fun testValueTooLarge() {
        val instruction = DrawInstruction()
        val context = createTestContext()
        
        context.registers.r0.value = 200u // > 127
        
        val nibbles = mapOf("src" to 0, "row" to 0, "col" to 0)
        
        assertFailsWith<IllegalArgumentException> {
            instruction.performOperation(nibbles, context)
        }
    }
    
    @Test
    fun testRowOutOfBounds() {
        val instruction = DrawInstruction()
        val context = createTestContext()
        
        context.registers.r0.value = 65u
        
        val nibbles = mapOf("src" to 0, "row" to 8, "col" to 0)
        
        assertFailsWith<IllegalArgumentException> {
            instruction.performOperation(nibbles, context)
        }
    }
    
    @Test
    fun testColOutOfBounds() {
        val instruction = DrawInstruction()
        val context = createTestContext()
        
        context.registers.r0.value = 65u
        
        val nibbles = mapOf("src" to 0, "row" to 0, "col" to 8)
        
        assertFailsWith<IllegalArgumentException> {
            instruction.performOperation(nibbles, context)
        }
    }
}
