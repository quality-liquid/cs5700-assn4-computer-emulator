package instructions

interface InstructionTemplate {
    fun execute(instruction: Int) {
        parseNibbles()
        performOperation()
        programCounter()
    }

    fun parseNibbles()
    fun performOperation()
    fun programCounter()

}