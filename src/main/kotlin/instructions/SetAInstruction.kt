package instructions

class SetAInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["addr"] = instruction and 0xfff
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        context.registers.A = nibbles["addr"]!!.toUShort()
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}