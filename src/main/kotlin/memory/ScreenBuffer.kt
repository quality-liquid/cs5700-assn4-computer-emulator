@file:OptIn(ExperimentalUnsignedTypes::class)
package memory

import Observable
import ScreenBufferObserver

class ScreenBuffer(override val size: Int) : MemoryAdapter, Observable {
    private val memory = UByteArray(size)
    override val observers = mutableListOf<ScreenBufferObserver>()

    override fun read(address: UShort): UByte {
        return memory[address.toInt()]
    }

    override fun write(address: UShort, value: UByte) {
        memory[address.toInt()] = value
        notifyObservers()
    }

    override fun notifyObservers() {
        for (observer in observers) {
            observer.update(memory)
        }
    }

    override fun subscribe(observer: ScreenBufferObserver) {
        observers += observer
    }

    override fun unsubscribe(observer: ScreenBufferObserver) {
        observers -= observer
    }
}