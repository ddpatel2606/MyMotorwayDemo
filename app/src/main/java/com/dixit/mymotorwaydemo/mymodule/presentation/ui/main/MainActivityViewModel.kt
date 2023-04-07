package com.dixit.mymotorwaydemo.mymodule.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixit.mymotorwaydemo.mymodule.domain.usecases.GetCountedWordUseCase
import com.dixit.mymotorwaydemo.mymodule.domain.usecases.GetRandomParagraphUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  MainActivityViewModel
 *  This is MainActivityViewModel attached with MainView, It will fetch the data from useCases and Repository
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCountedWordUseCase: GetCountedWordUseCase,
    private val getRandomParagraphUseCase: GetRandomParagraphUseCase
) : ViewModel() {

    // It will manage the state
    private val _editTextAndCountTask = MutableStateFlow(EditAndCountTaskUiState())
    val editTextAndCountTask get() = _editTextAndCountTask.asStateFlow()

    // It will update the editText value to the state
    fun updateEditText(data: String) {
        _editTextAndCountTask.update {
            it.copy(editText = data)
        }
    }

    // It will calculate the words from the editText and update value to the state
    fun calculateWordCount(data: String) {
        viewModelScope.launch {
            getCountedWordUseCase(data).collect { counts ->
                _editTextAndCountTask.update {
                    it.copy(wordCount = counts)
                }
            }
        }
    }

    // It will generate the random words which is used in TextField
    fun getRandomText() {
        viewModelScope.launch {
            getRandomParagraphUseCase().collect { data->
                _editTextAndCountTask.update {
                    it.copy(editText = data)
                }
                calculateWordCount(data)
            }
        }
    }
}