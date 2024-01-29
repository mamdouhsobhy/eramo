package com.example.eramo.core.data.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.eramo.core.presentation.base.BaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class EmittingResponse @Inject constructor() {

    suspend fun <T : Any> makeApiCall(call: suspend () -> Response<T>): Flow<BaseState<T>> {
        return flow {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseState.Success(body))
            } else {
                val type = object : TypeToken<T>() {}.type
                val err: T =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                emit(
                    BaseState.Error(
                        "500",
                        "Internal Server Error"
                    )
                )
            }
        }
    }
}
