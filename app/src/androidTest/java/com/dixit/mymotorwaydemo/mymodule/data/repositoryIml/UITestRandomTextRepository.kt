package com.dixit.mymotorwaydemo.mymodule.data.repositoryIml

import com.dixit.mymotorwaydemo.mymodule.data.datasource.countWordsFromString
import com.dixit.mymotorwaydemo.mymodule.data.datasource.getRandomParagraph
import com.dixit.mymotorwaydemo.mymodule.domain.repository.RandomTextRepository

class UITestRandomTextRepository : RandomTextRepository {

    override suspend fun getRandomParagraphText(): String {
        return getRandomParagraph()
    }

    override suspend fun getCountWordFromText(text: String): Int {
        return countWordsFromString(text)
    }

}
