import instructions.AddInstruction
import memory.RAM
import memory.ROM
import memory.ScreenBuffer
import kotlin.test.*

class AddInstructionTest {
    
    private fun createTestContext(): CPU.CPUContext {
        val registers = CPURegisters()
        val ram = RAM(100)
        val screenBuffer = ScreenBuffer(64)
        val rom = ROM(ubyteArrayOf())
        return CPU.CPUContext(registers, ram, screenBuffer, rom)
    }
    
    @Test
    fun testParseNibbles() {
        val instruction = AddInstruction()
        val nibbles = instruction.parseNibbles(0x1234)
        
        assertEquals(2, nibbles["src1"])
        assertEquals(3, nibbles["src2"])
        assertEquals(4, nibbles["dest"])
    }
    
    @Test
    fun testPerformOperation() {
        val instruction = AddInstruction()
        val context = createTestContext()
        
        context.registers.r1.value = 10u
        context.registers.r2.value = 20u
        
        val nibbles = mapOf("src1" to 1, "src2" to 2, "dest" to 3)
        instruction.performOperation(nibbles, context)
        
        assertEquals(30u, context.registers.r3.value)
    }
    
    @Test
    fun testAdditionOverflow() {
        val instruction = AddInstruction()
        val context = createTestContext()
        
        context.registers.r0.value = 200u
        context.registers.r1.value = 100u
        
        val nibbles = mapOf("src1" to 0, "src2" to 1, "dest" to 2)
        instruction.performOperation(nibbles, context)
        
        // Should wrap around: (200 + 100) % 256 = 44
        assertEquals(44u, context.registers.r2.value)
    }
}
