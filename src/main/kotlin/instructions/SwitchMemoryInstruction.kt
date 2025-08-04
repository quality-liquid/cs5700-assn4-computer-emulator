package instructions

class SwitchMemoryInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        context.registers.M = !context.registers.M
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}