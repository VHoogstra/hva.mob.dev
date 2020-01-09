package com.example.hvalevel6_task1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hvalevel6_task1.database.ColorItem
import com.example.hvalevel6_task1.database.ColorRepository

class MainActivityViewModel : ViewModel(){
    private val colorRepository = ColorRepository()

    val colorItems = MutableLiveData<List<ColorItem>>().apply {
        value = colorRepository.getColorItems()
    }
}
