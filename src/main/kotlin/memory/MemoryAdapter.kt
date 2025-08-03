package memory

interface MemoryAdapter {
    val size: Int
    fun read(address: UShort): UByte
    fun write(address: UShort)
}