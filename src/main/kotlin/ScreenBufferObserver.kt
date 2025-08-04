interface ScreenBufferObserver {
    @OptIn(ExperimentalUnsignedTypes::class)
    fun update(memory: UByteArray)
}