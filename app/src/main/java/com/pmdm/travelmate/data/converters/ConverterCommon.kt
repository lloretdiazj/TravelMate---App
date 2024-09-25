package com.pmdm.travelmate.data.converters


private fun String.aMutableListInt(): MutableList<Int> {
    return this.split(",").map { if (it != "") it.toInt() else -1 }.filter { it != -1 }
        .toMutableList()
}

fun MutableList<Int>.aString(): String =
    this.joinToString(separator = ",")