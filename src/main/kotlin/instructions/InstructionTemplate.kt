package instructions

import CPU

interface InstructionTemplate {
    fun execute(instruction: Int, context: CPU.CPUContext) {
        val nibbles: Map<String, Int> = parseNibbles(instruction)
        performOperation(nibbles, context)
        programCounter(context)
    }

    fun parseNibbles(instruction: Int): Map<String, Int>
    fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext)
    fun programCounter(context: CPU.CPUContext)

}