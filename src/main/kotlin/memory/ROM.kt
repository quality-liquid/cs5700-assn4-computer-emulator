@file:OptIn(ExperimentalUnsignedTypes::class)
package memory


class ROM(private val data: UByteArray): MemoryAdapter {

    override fun read(address: UShort): UByte {
        TODO("Not yet implemented")
    }

    override fun write(address: UShort) {
        TODO("Not yet implemented")
    }
}