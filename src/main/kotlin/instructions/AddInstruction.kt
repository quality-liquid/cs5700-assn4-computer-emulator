package instructions

class AddInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()

        nibbles["src1"] = (instruction shr 8) and 0x0f
        nibbles["src2"] = instruction and 0xf0
        nibbles["dest"] = instruction and 0x0f
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val src1: UByte = context.registers.
    }

    override fun programCounter(context: CPU.CPUContext) {
        TODO("Not yet implemented")
    }

}