@file:OptIn(ExperimentalUnsignedTypes::class)
package memory

class RAM(override val size: Int) : MemoryAdapter {
    private val memory = UByteArray(size)

    override fun read(address: UShort): UByte {
        return memory[address.toInt()]
    }

    override fun write(address: UShort, value: UByte) {
        memory[address.toInt()] = value
    }
}