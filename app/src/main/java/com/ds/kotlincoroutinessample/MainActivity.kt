package com.ds.kotlincoroutinessample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "RESULT#1"
    private val RESULT_2 = "RESULT#2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
/*
            IO,Main,Default
*/
            CoroutineScope(IO).launch {
                fakeAPIrqst()
            }
        }
    }

    private fun setNewText(input:String){
        val newText=txtvw.text.toString()+"\n$input"
        txtvw.text=newText
    }
    private suspend fun setNewTextMainThread(input:String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeAPIrqst() {
        val result1 = getResultApi1()
        println("Debug :$result1")
        setNewTextMainThread(result1)

        val result2 = getResultApi2()
        setNewTextMainThread(result2)
    }

    private suspend fun getResultApi1(): String {
        logThread("getResultApi1")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResultApi2(): String {
        logThread("getResultApi2")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        println("debug:${methodName}:${Thread.currentThread().name}")
    }
}