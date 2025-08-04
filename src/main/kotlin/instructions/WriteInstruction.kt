package instructions

class WriteInstruction: InstructionTemplate {
    override fun parseNibbles(instruction: Int): Map<String, Int> {
        val nibbles = mutableMapOf<String, Int>()
        nibbles["src"] = (instruction shr 8) and 0x0f
        return nibbles
    }

    override fun performOperation(nibbles: Map<String, Int>, context: CPU.CPUContext) {
        val value = context.registerMap[nibbles["src"]]?.value
        if (context.registers.M) {
            context.rom.write(context.registers.A, value!!)
        } else {
            context.ram.write(context.registers.A, value!!)
        }
    }

    override fun programCounter(context: CPU.CPUContext) {
        context.registers.P = (context.registers.P + 2u).toUShort()
    }
}