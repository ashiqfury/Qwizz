package com.example.qwizz.data.utils

import com.example.qwizz.model.Question


fun pickNItems(list: List<Question>, count: Int): List<Question> = list.shuffled().take(count)