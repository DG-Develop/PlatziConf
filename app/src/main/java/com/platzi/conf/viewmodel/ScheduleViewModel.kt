package com.platzi.conf.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.conf.model.Conference
import com.platzi.conf.network.Callback
import com.platzi.conf.network.FirestoreService
import java.lang.Exception

class ScheduleViewModel: ViewModel(){
    val firestoreService = FirestoreService()
    var listSchedule : MutableLiveData<List<Conference>> = MutableLiveData() //Datos actualizados
    var isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        getScheduleFromFirebase()
    }

    fun getScheduleFromFirebase(){
        firestoreService.getSchedule(object: Callback<List<Conference>> {
            override fun onSucces(result: List<Conference>?) {
                listSchedule.postValue(result)
                processFinished()
            }

            override fun onFailure(exception: Exception) {
                processFinished()
            }
        })
    }

    fun processFinished(){
        isLoading.value = true
    }

}