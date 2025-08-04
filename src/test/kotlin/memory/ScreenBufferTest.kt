import memory.ScreenBuffer
import ScreenBufferObserver
import kotlin.test.*

class MockScreenObserver : ScreenBufferObserver {
    var updateCount = 0
    var lastMemory: UByteArray? = null
    
    override fun update(memory: UByteArray) {
        updateCount++
        lastMemory = memory.copyOf()
    }
}

class ScreenBufferTest {
    
    @Test
    fun testScreenBufferSize() {
        val buffer = ScreenBuffer(64)
        assertEquals(64, buffer.size)
    }
    
    @Test
    fun testScreenBufferReadWrite() {
        val buffer = ScreenBuffer(64)
        
        buffer.write(10u, 65u) // 'A'
        assertEquals(65u, buffer.read(10u))
    }
    
    @Test
    fun testObserverNotification() {
        val buffer = ScreenBuffer(64)
        val observer = MockScreenObserver()
        
        buffer.subscribe(observer)
        assertEquals(0, observer.updateCount)
        
        buffer.write(0u, 72u) // 'H'
        assertEquals(1, observer.updateCount)
        assertEquals(72u, observer.lastMemory!![0])
    }
    
    @Test
    fun testMultipleObservers() {
        val buffer = ScreenBuffer(64)
        val observer1 = MockScreenObserver()
        val observer2 = MockScreenObserver()
        
        buffer.subscribe(observer1)
        buffer.subscribe(observer2)
        
        buffer.write(5u, 100u)
        
        assertEquals(1, observer1.updateCount)
        assertEquals(1, observer2.updateCount)
    }
    
    @Test
    fun testUnsubscribeObserver() {
        val buffer = ScreenBuffer(64)
        val observer = MockScreenObserver()
        
        buffer.subscribe(observer)
        buffer.write(0u, 50u)
        assertEquals(1, observer.updateCount)
        
        buffer.unsubscribe(observer)
        buffer.write(1u, 60u)
        assertEquals(1, observer.updateCount) // Should not increment
    }
}
