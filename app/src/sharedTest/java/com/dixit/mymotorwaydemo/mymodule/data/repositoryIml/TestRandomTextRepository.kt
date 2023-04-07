package com.dixit.mymotorwaydemo.mymodule.data.repositoryIml

import com.dixit.mymotorwaydemo.mymodule.domain.repository.RandomTextRepository
import com.dixit.mymotorwaydemo.MOCK_TEXT
import com.dixit.mymotorwaydemo.MOCK_WORD_COUNT
import javax.inject.Inject

/**
 *  TestRandomTextRepository
 *  This is a TestRandomTextRepository, It will use for the testing purpose.
 */
class TestRandomTextRepository @Inject constructor() : RandomTextRepository {

    override suspend fun getRandomParagraphText(): String {
        return MOCK_TEXT
    }

    override suspend fun getCountWordFromText(text: String): Int {
        return MOCK_WORD_COUNT
    }

}
