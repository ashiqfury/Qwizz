package com.example.qwizz.model

data class Question(
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
)

data class QuestionResponse(
    val results: List<Question>
)