package com.example.teran.data.model

data class Survey(val text: String, val type: QuestionType, val options: List<String> = emptyList())

enum class QuestionType {
    RADIO,
    TEXT,
    DROPDOWN,
    GENDER,
    MAJOR,
    YEAR
}
