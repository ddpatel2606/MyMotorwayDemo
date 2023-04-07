package com.dixit.mymotorwaydemo.mymodule.presentation.ui.main

import com.dixit.mymotorwaydemo.di.RepositoryModule
import com.dixit.mymotorwaydemo.mymodule.domain.repository.RandomTextRepository
import com.dixit.mymotorwaydemo.mymodule.domain.usecases.GetCountedWordUseCase
import com.dixit.mymotorwaydemo.mymodule.domain.usecases.GetRandomParagraphUseCase
import com.dixit.mymotorwaydemo.MainCoroutineRule
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 *  MainActivityViewModelTest
 *  Unit test case for MainActivityViewModel
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(value = [RepositoryModule::class])
class MainActivityViewModelTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    private lateinit var viewModel: MainActivityViewModel

    @Mock
    private lateinit var getCountedWordUseCase: GetCountedWordUseCase

    @Mock
    private lateinit var getRandomParagraphUseCase: GetRandomParagraphUseCase

    @Inject
    lateinit var randomTextRepository: RandomTextRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        getCountedWordUseCase = GetCountedWordUseCase(randomTextRepository)
        getRandomParagraphUseCase = GetRandomParagraphUseCase(randomTextRepository)
        viewModel = MainActivityViewModel(getCountedWordUseCase, getRandomParagraphUseCase)
    }

    @Test
    fun updateEditText_withInput_shouldUpdateEditText() {
        val input = "hello world"
        viewModel.updateEditText(input)
        val actualOutput = viewModel.editTextAndCountTask.value.editText
        Truth.assertThat(input).isEqualTo(actualOutput)
    }

    @Test
    fun calculateWordCount_withInput_shouldUpdateWordCount() = runTest {
        val expectedOutput = 4
        viewModel.calculateWordCount("")
        advanceUntilIdle()
        val actualOutput = viewModel.editTextAndCountTask.value.wordCount
        Truth.assertThat(expectedOutput).isEqualTo(actualOutput)
    }

    @Test
    fun calculateWordCount_withInput_shouldUpdateWordCountWithFakeData() = runTest {
        randomTextRepository = Mockito.mock(RandomTextRepository::class.java)
        val input = "hello world"
        val expectedOutput = 2
        getCountedWordUseCase = Mockito.mock(GetCountedWordUseCase::class.java)
        Mockito.`when`(getCountedWordUseCase(input)).thenReturn(flowOf(expectedOutput))
        viewModel = MainActivityViewModel(getCountedWordUseCase, getRandomParagraphUseCase)
        viewModel.calculateWordCount(input)
        advanceUntilIdle()
        val actualOutput = viewModel.editTextAndCountTask.value.wordCount
        Truth.assertThat(expectedOutput).isEqualTo(actualOutput)
    }

    @Test
    fun getRandomText_shouldUpdateEditTextAndWordCount() = runTest {
        val expectedOutput = 4
        viewModel.getRandomText()
        advanceUntilIdle()
        val actualEditText = viewModel.editTextAndCountTask.value.editText
        val actualWordCount = viewModel.editTextAndCountTask.value.wordCount
        Truth.assertThat("test testing testActual test").isEqualTo(actualEditText)
        Truth.assertThat(expectedOutput).isEqualTo(actualWordCount)
    }

    @Test
    fun getRandomText_shouldUpdateEditTextAndWordCountWithFakeData() = runTest {
        randomTextRepository = Mockito.mock(RandomTextRepository::class.java)

        val expectedParagraph = "lorem ipsum dolor sit amet"
        val expectedWordCount = 5

        getCountedWordUseCase = Mockito.mock(GetCountedWordUseCase::class.java)
        getRandomParagraphUseCase = Mockito.mock(GetRandomParagraphUseCase::class.java)

        Mockito.`when`(getRandomParagraphUseCase()).thenReturn(flowOf(expectedParagraph))
        Mockito.`when`(getCountedWordUseCase(expectedParagraph))
            .thenReturn(flowOf(expectedWordCount))
        viewModel = MainActivityViewModel(getCountedWordUseCase, getRandomParagraphUseCase)
        viewModel.getRandomText()
        advanceUntilIdle()

        val actualEditText = viewModel.editTextAndCountTask.value.editText
        val actualWordCount = viewModel.editTextAndCountTask.value.wordCount
        Truth.assertThat(expectedParagraph).isEqualTo(actualEditText)
        Truth.assertThat(expectedWordCount).isEqualTo(actualWordCount)
    }

}