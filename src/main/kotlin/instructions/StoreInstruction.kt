package instructions

class StoreInstruction(): InstructionTemplate {

    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()

        nibbles["dest"] = (instruction shr 8) and 0x0f
        nibbles["value"] = instruction and 0xff
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