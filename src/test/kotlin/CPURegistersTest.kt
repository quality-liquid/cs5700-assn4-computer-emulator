import kotlin.test.*

class CPURegistersTest {
    
    @Test
    fun testDefaultValues() {
        val registers = CPURegisters()
        
        assertEquals(0u, registers.r0.value)
        assertEquals(0u, registers.r1.value)
        assertEquals(0u, registers.r2.value)
        assertEquals(0u, registers.r3.value)
        assertEquals(0u, registers.r4.value)
        assertEquals(0u, registers.r5.value)
        assertEquals(0u, registers.r6.value)
        assertEquals(0u, registers.r7.value)
        
        assertEquals(0u, registers.P)
        assertEquals(0u, registers.T.value)
        assertEquals(0u, registers.A)
        assertEquals(false, registers.M)
    }
    
    @Test
    fun testRegisterModification() {
        val registers = CPURegisters()
        
        registers.r0.value = 100u
        registers.P = 200u
        registers.A = 300u
        registers.M = true
        
        assertEquals(100u, registers.r0.value)
        assertEquals(200u, registers.P)
        assertEquals(300u, registers.A)
        assertEquals(true, registers.M)
    }
}
