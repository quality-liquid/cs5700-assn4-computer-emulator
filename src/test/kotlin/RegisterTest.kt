import kotlin.test.*

class RegisterTest {
    
    @Test
    fun testRegisterDefaultValue() {
        val register = Register()
        assertEquals(0u, register.value)
    }
    
    @Test
    fun testRegisterInitialValue() {
        val register = Register(42u)
        assertEquals(42u, register.value)
    }
    
    @Test
    fun testRegisterValueUpdate() {
        val register = Register(10u)
        register.value = 255u
        assertEquals(255u, register.value)
    }
}
