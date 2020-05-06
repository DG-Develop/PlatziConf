package com.platzi.conf.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.conf.model.Speaker
import com.platzi.conf.network.Callback
import com.platzi.conf.network.FirestoreService

class SpeakerViewModel : ViewModel(){
    val firestoreService = FirestoreService()
    var listSchedule : MutableLiveData<List<Speaker>> = MutableLiveData() //Datos actualizados
    var isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        getSpeakerFromFirebase()
    }
    fun getSpeakerFromFirebase(){
        firestoreService.getSpeakers(object: Callback<List<Speaker>> {
            override fun onSucces(result: List<Speaker>?) {
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