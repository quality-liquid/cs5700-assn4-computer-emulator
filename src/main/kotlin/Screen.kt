class Screen: ScreenBufferObserver {

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun update(memory: UByteArray) {
        for (i in 0..7) {
            for (j in 0..7) {
                print(memory[i * j])
            }
            println()
        }
        println("\n")
    }
}