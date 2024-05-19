package io.sebi.demos

var printCtr = 0
fun blockPrint(str: String) {
    if (printCtr >= 32) {
        println()
        printCtr = 0
    }
    print("$str")
    printCtr += str.length
}
