import instructions.JumpInstruction
import memory.RAM
import memory.ROM
import memory.ScreenBuffer
import kotlin.test.*

class JumpInstructionTest {
    
    private fun createTestContext(): CPU.CPUContext {
        val registers = CPURegisters()
        val ram = RAM(100)
        val screenBuffer = ScreenBuffer(64)
        val rom = ROM(ubyteArrayOf())
        return CPU.CPUContext(registers, ram, screenBuffer, rom)
    }
    
    @Test
    fun testParseNibbles() {
        val instruction = JumpInstruction()
        val nibbles = instruction.parseNibbles(0x5ABC)
        
        assertEquals(0xABC, nibbles["addr"])
    }
    
    @Test
    fun testPerformOperation() {
        val instruction = JumpInstruction()
        val context = createTestContext()
        context.registers.P = 100u
        
        val nibbles = mapOf("addr" to 0x200)
        instruction.performOperation(nibbles, context)
        
        assertEquals(0x200u, context.registers.P)
    }
    
    @Test
    fun testOddAddressThrowsException() {
        val instruction = JumpInstruction()
        val context = createTestContext()
        
        val nibbles = mapOf("addr" to 0x201) // Odd address
        
        assertFailsWith<IllegalArgumentException> {
            instruction.performOperation(nibbles, context)
        }
    }
    
    @Test
    fun testProgramCounterNotIncremented() {
        val instruction = JumpInstruction()
        val context = createTestContext()
        context.registers.P = 100u
        
        // Jump instruction should not increment PC
        instruction.programCounter(context)
        
        assertEquals(100u, context.registers.P)
    }
}
