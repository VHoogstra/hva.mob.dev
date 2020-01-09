package com.example.swipequiz

data class Question(var question: String, var awnser: Boolean) {
    companion object {
        val QUESTION_QUESTION = arrayOf(
            "a val and var are the same.",
            "mobile app dev grants 12 ects",
            "a unit in kotlin corresponds to a void in java",
            "in kotlin when replaces the switch operator in java"
        )

        val QUESTION_ANWSER = arrayOf(
            true,
            true,
            false,
            true
        )
    }
}