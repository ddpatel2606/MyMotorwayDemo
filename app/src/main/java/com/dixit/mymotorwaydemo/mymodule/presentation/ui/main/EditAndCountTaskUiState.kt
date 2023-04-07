package com.dixit.mymotorwaydemo.mymodule.presentation.ui.main


/**
 *  EditAndCountTaskUiState
 *  This Data class will maintain the state of editText and wordCount
 */
data class EditAndCountTaskUiState(
    val editText: String = "",
    val wordCount: Int = 0
)