package com.dixit.mymotorwaydemo.mymodule.data.datasource

import com.dixit.mymotorwaydemo.mymodule.data.constant.ALLOWED_CHARACTERS
import java.security.SecureRandom

/**
 *  countWordsFromString
 *  This function will calculate the words from the given string
 *
 */
fun countWordsFromString(data : String): Int {
    return if(data.isNotEmpty()) data.trim().split("\\s+".toRegex()).size else 0
}

/**
 *  getRandomString
 *  This function will generate the random word from the ALLOWED_CHARACTERS limit
 *
 */
fun getRandomString(): String {
    val secureRandom = SecureRandom()
    val allowedChars = ALLOWED_CHARACTERS.toCharArray()
    val randomMax = (2..10).random()
    val sb = StringBuilder()
    sb.ensureCapacity(randomMax)
    repeat(randomMax) {
        sb.append(allowedChars[secureRandom.nextInt(allowedChars.size)])
    }
    return sb.toString()
}

/**
 *  getRandomParagraph
 *  This function will generate the random Paragraph from the getRandomString()
 *
 */
fun getRandomParagraph(): String {
    val random = (1..20).random()
    val words = List(random) { getRandomString() }
    return words.joinToString(separator = " ")
}