@file:OptIn(ExperimentalUnsignedTypes::class)
package memory


class ROM(private val data: UByteArray): MemoryAdapter {
    override val size: Int = data.size

    override fun read(address: UShort): UByte {
        return data[address.toInt()]
    }

    override fun write(address: UShort, value: UByte) {
        throw UnsupportedOperationException("You can't write to ROM silly")
    }
}