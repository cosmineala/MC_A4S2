package com.example.mcapp.Cryptography

import java.io.ByteArrayOutputStream
import java.security.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Crypto() {

// NOTE AES
    fun aesEncrypt(v: String, secretKey: String, seed: String ) = AES256.encrypt(v, secretKey, seed)
    fun aesDecrypt(v: String, secretKey: String, seed: String ) = AES256.decrypt(v, secretKey, seed)
    fun aesNewKey() = AES256.newKey()

// NOTE RSA
    fun rsaNewKey() = RSA.generateKey()

    fun rsaEncryptWithPrivate( input: String, privateKey: PrivateKey ) = RSA.encryptByPrivateKey( input, privateKey )
    fun rsaEncryptWithPublic( input: String, publicKey: PublicKey ) = RSA.encryptByPublicKey( input, publicKey )

    fun rsaDecryptWithPrivate( input: String, privateKey: PrivateKey ) = RSA.decryptByPrivateKey( input, privateKey )
    fun rsaDecryptWithPublic(  input: String, publicKey: PublicKey  ) = RSA.decryptByPublicKey( input, publicKey)

    private object AES256{

        fun newKey(): String{
            val key = ByteArray(22)
            SecureRandom().nextBytes(key)
            return String(encorder.encode(key))
        }

        private fun cipher(opmode:Int, secretKey:String, seed: String):Cipher{

            if(secretKey.length != 32)
            {
                throw RuntimeException("SecretKey length is not 32 chars")
            }

            val chiper = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")

            val seedHash = Hash.md5ByteArray( seed )

            val iv = IvParameterSpec( seedHash )

            chiper.init(opmode, sk, iv)

            return chiper
        }
        fun encrypt(str:String, secretKey:String, seed: String):String{

            val encrypted = cipher(Cipher.ENCRYPT_MODE, secretKey, seed).doFinal(str.toByteArray(Charsets.UTF_8))

            return String(encorder.encode(encrypted))

        }
        fun decrypt(str:String, secretKey:String, seed: String):String{

            val byteStr = decorder.decode(str.toByteArray(Charsets.UTF_8))

            return String(cipher(Cipher.DECRYPT_MODE, secretKey, seed).doFinal(byteStr))
        }


    }

    object RSA {

        val ENCRYPT_MAX_SIZE = 117 //Encryption: The maximum encryption length each time
        val DECRYPT_MAX_SIZE = 128 //Decryption: maximum encryption length each time

        private val alg = "RSA"

        fun generateKey() : KeyPair {
            val a = KeyPairGenerator.getInstance(alg)
            a.initialize(1024)
            val key = a.genKeyPair()
            return key
        }

        fun encryptByPrivateKey(input: String, privateKey: PrivateKey): String {

            //1 Create cipher object
            val cipher = Cipher.getInstance(alg)

            //2 Initialize the cipher object
            cipher.init(Cipher.ENCRYPT_MODE, privateKey)

            //3 encryption or decryption
            val encrypt = cipher.doFinal(input.toByteArray())

            return String( encorder.encode( encrypt ) )
        }

        fun encryptByPublicKey(input: String, publicKey: PublicKey): String {

            //1 Create cipher object
            val cipher = Cipher.getInstance(alg)

            //2 Initialize the cipher object
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)

            //3 encryption or decryption
            val encrypt = cipher.doFinal(input.toByteArray())

            return String( encorder.encode( encrypt ) )
        }

        fun decryptByPrivateKey(input: String, privateKey: PrivateKey): String {
            //********************Asymmetric encryption trilogy********************//

            val byteArray = decorder.decode(input)

            //1. Create a cipher object
            val cipher = Cipher.getInstance(alg)
            //2. Initialize cipher
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            //3. Segment decryption
            var temp: ByteArray? = null
            var offset = 0 //Current offset position

            val bos = ByteArrayOutputStream()

            while (byteArray.size - offset > 0) { //Not encrypted
                //The maximum decryption is 128 bytes at a time
                if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
                    temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
                    //Recalculate the offset position
                    offset += DECRYPT_MAX_SIZE
                } else {
                    //Encrypt the last piece
                    temp = cipher.doFinal(byteArray, offset, byteArray.size - offset)
                    //Recalculate the offset position
                    offset = byteArray.size
                }
                //Store to temporary buffer
                bos.write(temp)
            }
            bos.close()

            return String(bos.toByteArray())
        }

        fun decryptByPublicKey(input: String, publicKey: PublicKey): String {
            //********************Asymmetric encryption trilogy********************//

            val byteArray: ByteArray = decorder.decode(input)

            //1. Create a cipher object
            val cipher = Cipher.getInstance(alg)
            //2. Initialize cipher
            cipher.init(Cipher.DECRYPT_MODE, publicKey)
            //3. Segment decryption
            var temp: ByteArray? = null
            var offset = 0 //Current offset position

            val bos = ByteArrayOutputStream()

            while (byteArray.size - offset > 0) { //Not encrypted
                //The maximum decryption is 128 bytes at a time
                if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
                    temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
                    //Recalculate the offset position
                    offset += DECRYPT_MAX_SIZE
                } else {
                    //Encrypt the last piece
                    temp = cipher.doFinal(byteArray, offset, byteArray.size - offset)
                    //Recalculate the offset position
                    offset = byteArray.size
                }
                //Store to temporary buffer
                bos.write(temp)
            }
            bos.close()
            return String(bos.toByteArray())
        }
    }

    companion object{
        private val encorder = Base64.getEncoder()
        private val decorder = Base64.getDecoder()
    }
}