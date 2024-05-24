package com.project.tpcconfessions

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfessionViewModel: ViewModel() {
    private val _confessions =  MutableStateFlow<List<DataEntity>>(emptyList())
    val confessions: StateFlow<List<DataEntity>> = _confessions


    fun fetchConfessions(roll_no : String, context: Context) = viewModelScope.launch {
        val response = RetrofitInstance.api.getConfessions(roll_no)
        if(response.isSuccessful) {
            _confessions.value = response.body()?: emptyList()
            Toast.makeText(context, "Fetched Confessions Successfully", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Failed to Fetch Confessions", Toast.LENGTH_SHORT).show()
        }
    }

    fun postConfession(bodyDataEntity: BodyDataEntity, context: Context) = viewModelScope.launch {
        val responses = RetrofitInstance.api.postConfessions(bodyDataEntity)
        if(responses.isSuccessful){
            Toast.makeText(context,"Confession Posted Successfully", Toast.LENGTH_SHORT).show()
            fetchConfessions(bodyDataEntity.roll_no, context)
        }
        else{
            Toast.makeText(context,"Failed to Post Confession", Toast.LENGTH_SHORT).show()
        }
    }
}
