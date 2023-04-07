package com.dixit.mymotorwaydemo.mymodule.domain.usecases

import com.dixit.mymotorwaydemo.mymodule.domain.repository.RandomTextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *  GetRandomParagraphUseCase
 *  This usecase will used for generating random paragraph text
 */
class GetRandomParagraphUseCase @Inject constructor(
    private val randomTextRepository: RandomTextRepository)
{
    operator fun invoke(): Flow<String> = flow {
        emit(
            randomTextRepository.getRandomParagraphText()
        )
    }
}