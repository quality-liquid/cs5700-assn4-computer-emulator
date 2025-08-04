package instructions

class JumpInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["addr"] = instruction and 0xfff
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val addr: Int = nibbles["addr"]!!
        if ((addr % 2) != 0) {
            throw IllegalArgumentException("program counter must be divisible by 2")
        }
        context.registers.P = addr.toUShort()
    }

    override fun programCounter(context: CPU.CPUContext) {
        // no operation
    }
}