package com.edpub.tesla

interface ResponseCallback {
    fun onSuccess(response:String)
    fun onError(response: String)
}