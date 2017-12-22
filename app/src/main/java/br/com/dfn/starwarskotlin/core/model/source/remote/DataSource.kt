package br.com.dfn.starwarskotlin.core.model.source.remote

import android.util.Log
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by diegonascimento on 19/12/17.
 */
abstract class DataSource {

    protected open fun handleError(t: Throwable) {
        when (t) {
            is HttpException -> {
                val errorBody = t.response().errorBody()?.string()
                Log.d("Main", "HttpException: " + errorBody)
            }
            is IOException -> Log.d("Main", "IOError: " + t.message)
            is Exception -> Log.d("Main", "Exception: " + t.message)
        }
    }
}