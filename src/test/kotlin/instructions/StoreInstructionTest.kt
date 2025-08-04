import instructions.StoreInstruction
import memory.RAM
import memory.ROM
import memory.ScreenBuffer
import kotlin.test.*

class StoreInstructionTest {
    
    private fun createTestContext(): CPU.CPUContext {
        val registers = CPURegisters()
        val ram = RAM(100)
        val screenBuffer = ScreenBuffer(64)
        val rom = ROM(ubyteArrayOf())
        return CPU.CPUContext(registers, ram, screenBuffer, rom)
    }
    
    @Test
    fun testParseNibbles() {
        val instruction = StoreInstruction()
        val nibbles = instruction.parseNibbles(0x03FF)
        
        assertEquals(3, nibbles["dest"])
        assertEquals(0xFF, nibbles["value"])
    }
    
    @Test
    fun testPerformOperation() {
        val instruction = StoreInstruction()
        val context = createTestContext()
        val nibbles = mapOf("dest" to 2, "value" to 123)
        
        instruction.performOperation(nibbles, context)
        
        assertEquals(123u, context.registers.r2.value)
    }
    
    @Test
    fun testProgramCounter() {
        val instruction = StoreInstruction()
        val context = createTestContext()
        context.registers.P = 10u
        
        instruction.programCounter(context)
        
        assertEquals(12u, context.registers.P)
    }
    
    @Test
    fun testFullExecution() {
        val instruction = StoreInstruction()
        val context = createTestContext()
        context.registers.P = 0u
        
        instruction.execute(0x07AB, context) // Store 0xAB in register 7
        
        assertEquals(0xABu, context.registers.r7.value)
        assertEquals(2u, context.registers.P)
    }
}
