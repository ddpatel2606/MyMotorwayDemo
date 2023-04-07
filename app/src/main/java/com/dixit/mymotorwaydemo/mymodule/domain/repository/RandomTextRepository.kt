package com.dixit.mymotorwaydemo.mymodule.domain.repository


/**
 *  RandomTextRepository
 *  This is RandomTextRepository interface, this will provide the abstraction layer on app.
 */
interface RandomTextRepository {
    suspend fun getRandomParagraphText(): String
    suspend fun getCountWordFromText(text: String): Int
}