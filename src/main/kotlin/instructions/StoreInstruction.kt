package instructions

class StoreInstruction(): InstructionTemplate {

    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()

        nibbles["destinationRegister"] = (instruction shr 8) and 0x0f
        nibbles["value"] = instruction and 0xff
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val value = nibbles["value"]?.toUByte()
        when (nibbles["destinationRegister"]) {
            0 -> context.registers.r0.value = value ?: 0u
            1 -> context.registers.r1.value = value ?: 0u
            2 -> context.registers.r2.value = value ?: 0u
            3 -> context.registers.r3.value = value ?: 0u
            4 -> context.registers.r4.value = value ?: 0u
            5 -> context.registers.r5.value = value ?: 0u
            6 -> context.registers.r6.value = value ?: 0u
            7 -> context.registers.r7.value = value ?: 0u
        }
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }

}