package memory

interface MemoryAdapter {
    fun read(address: UShort): UByte
    fun write(address: UShort)
}