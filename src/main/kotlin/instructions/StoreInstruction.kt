package instructions

class StoreInstruction(): InstructionTemplate {

    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        val uInstruction = instruction.toUInt()

        nibbles["dest"] = ((uInstruction shr 8) and 0x0fu).toInt()
        nibbles["value"] = (uInstruction and 0xffu).toInt()
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val value = nibbles["value"]?.toUByte()
        context.registerMap[nibbles["dest"]!!]?.value = value!!
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }

}