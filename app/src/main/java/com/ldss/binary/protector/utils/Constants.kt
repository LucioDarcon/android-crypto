package com.ldss.binary.protector.utils

object Constants {

    fun getKeyLocal(key: String): String {
        return "com.ldss.binary.protector_$key"
    }

}