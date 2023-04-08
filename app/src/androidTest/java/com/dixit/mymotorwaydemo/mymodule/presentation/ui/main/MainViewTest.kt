package com.dixit.mymotorwaydemo.mymodule.presentation.ui.main

import androidx.compose.material.Surface
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dixit.mymotorwaydemo.HiltTestActivity
import com.dixit.mymotorwaydemo.R
import com.dixit.mymotorwaydemo.mymodule.data.repositoryIml.UITestRandomTextRepository
import com.dixit.mymotorwaydemo.mymodule.domain.repository.RandomTextRepository
import com.dixit.mymotorwaydemo.mymodule.domain.usecases.GetCountedWordUseCase
import com.dixit.mymotorwaydemo.mymodule.domain.usecases.GetRandomParagraphUseCase
import com.dixit.mymotorwaydemo.ui.theme.MyMotorwayDemoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import javax.inject.Inject

/**
 *  This is Instrumentation test for MainView,
 *  It will check the word count which is generating from DI and Manually by mock Data.
 *
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainViewTest {

    // inject all the rules for DI
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    // inject all the rules for compose framework
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var randomTextRepository: RandomTextRepository

    private lateinit var viewModel: MainActivityViewModel

    @Mock
    private lateinit var getCountedWordUseCase: GetCountedWordUseCase

    @Mock
    private lateinit var getRandomParagraphUseCase: GetRandomParagraphUseCase

    private val dummyText =
        "2oLHfTM8 oza SX8Oawu PQLxe nzGm HiNRALCjNp 2oO IwsS xXL1Rn4O6 DRB YHrMLn55rj QE5Tz DP0Y 2QKdz b19Vt 7vkCuom1 3tlGGNLhk1 VlHqj Yvi 9gXAr"
    private val dummyText2 =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled"

    // inject all the dependencies and randomTextRepository will use data from Testing DI Module.
    private fun injectAndSetupDi() {
        hiltRule.inject()

        getCountedWordUseCase = GetCountedWordUseCase(randomTextRepository)
        getRandomParagraphUseCase = GetRandomParagraphUseCase(randomTextRepository)
        viewModel = MainActivityViewModel(getCountedWordUseCase, getRandomParagraphUseCase)
        setContent()
    }

    // Init actual randomTextRepository will use real time data.
    private fun injectManualAndSetup() {
        randomTextRepository = UITestRandomTextRepository()
        getCountedWordUseCase = GetCountedWordUseCase(randomTextRepository)
        getRandomParagraphUseCase = GetRandomParagraphUseCase(randomTextRepository)
        viewModel = MainActivityViewModel(getCountedWordUseCase, getRandomParagraphUseCase)
        setContent()
    }

    private fun setContent() {
        composeTestRule.setContent {
            MyMotorwayDemoTheme {
                Surface {
                    MainView(
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    @Test
    fun getRandomText_shouldUpdateEditTextAndWordCountByDefault() {
        injectAndSetupDi()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.edit_text_hint))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.word_count_string, 0))
            .assertIsDisplayed()

        findTextField(R.string.edit_text_hint).performTextClearance()
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.refresh_button))
            .performClick()

        composeTestRule
            .onNodeWithText("test testing testActual test")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.word_count_string, 4))
            .assertIsDisplayed()
    }

    @Test
    fun getRandomText_shouldUpdateEditTextAndWordCountWithActualData() {
        injectManualAndSetup()

        findTextField(R.string.edit_text_hint).performTextReplacement(dummyText)

        composeTestRule
            .onNodeWithText(activity.getString(R.string.word_count_string, 20))
            .assertIsDisplayed()


        findTextField(dummyText).performTextReplacement(dummyText2)

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.word_count_string, 36))
            .assertIsDisplayed()

        findTextField(dummyText2).performTextReplacement("                 1 ")

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.word_count_string, 1))
            .assertIsDisplayed()

        findTextField("                 1 ").performTextReplacement("                  test  test dd    ")

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.word_count_string, 3))
            .assertIsDisplayed()


        findTextField("                  test  test dd    ").performTextReplacement("")

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.word_count_string, 0))
            .assertIsDisplayed()
    }


    private fun findTextField(textId: Int): SemanticsNodeInteraction {
        return composeTestRule.onNode(
            hasSetTextAction() and hasText(activity.getString(textId))
        )
    }

    private fun findTextField(text: String): SemanticsNodeInteraction {
        return composeTestRule.onNode(
            hasSetTextAction() and hasText(text)
        )
    }

}
