package com.example.eramo.core.data.utils

import com.example.eramo.core.presentation.base.BaseState
import kotlinx.coroutines.flow.*
import java.net.UnknownHostException
import javax.inject.Inject

class ExecuteResponse @Inject constructor() {

    fun <T : Any> execute(
        flow: Flow<BaseState<T>>,
        state: MutableStateFlow<BaseState<T>>
    ): Flow<BaseState<T>> {
        return flow
            .onStart { state.value = BaseState.IsLoading(true) }
            .catch { e ->
                state.value = BaseState.IsLoading(false)
                state.value = BaseState.ShowToast(
                    e.message.toString(),
                    e is UnknownHostException
                )
            }
            .map {
                state.value = BaseState.IsLoading(false)
                when (it) {
                    is BaseState.Error -> BaseState.Error(it.code, it.message)
                    is BaseState.Success -> BaseState.Success(it.items)
                    BaseState.Init -> TODO()
                    is BaseState.IsLoading -> TODO()
                    is BaseState.ShowToast -> TODO()
                }
            }
    }

}