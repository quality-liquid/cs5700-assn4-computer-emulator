class Screen: ScreenBufferObserver {

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun update(memory: UByteArray) {
        for (i in 0..7) {
            for (j in 0..7) {
                val byteValue = memory[i * 8 + j]
                if (byteValue == 0.toUByte()) {
                    print('0') // Display '0' for empty pixels
                } else {
                    print(byteValue.toInt().toChar()) // Convert to ASCII character
                }
            }
            println()
        }
        println("\n")
    }
}