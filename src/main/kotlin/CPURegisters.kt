class CPURegisters {
    // general purpose registers
    val r0 = Register()
    val r1 = Register()
    val r2 = Register()
    val r3 = Register()
    val r4 = Register()
    val r5 = Register()
    val r6 = Register()
    val r7 = Register()

    // special registers
    val P: UShort = 0u
    val T = Register()
    val A: UShort = 0u
    var M: Boolean = false
}