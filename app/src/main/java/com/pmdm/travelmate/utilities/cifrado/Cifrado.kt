package com.pmdm.travelmate.utilities.cifrado

import java.security.MessageDigest

@OptIn(ExperimentalStdlibApi::class)
fun String.toSHA256(): String = MessageDigest
    .getInstance("SHA-256")
    .digest(this.toByteArray())
    .toHexString()