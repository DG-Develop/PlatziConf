package com.platzi.conf.network

import java.lang.Exception

interface Callback <T>{

    fun onSucces(result: T?)

    fun onFailure(exception: Exception)
}