package com.example.mcapp.Cryptography

import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class Hash {



    companion object{


        fun md5ByteArray(input: String): ByteArray {
            val md = MessageDigest.getInstance("MD5")
            //return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
            return md.digest(input.toByteArray() )
        }

        fun md5String(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
            //return md.digest(input.toByteArray())
        }
    }
}


