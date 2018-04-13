package com.icoalert.ui.viewmodel


enum class ProgressStatuses(val content: Boolean, val emptyData: Boolean, val progressShown: Boolean) {
    Content(true, false, false), Progress(false, false, true), Empty(false, true, false)
}