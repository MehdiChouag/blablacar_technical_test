package com.blablacar.technicaltest.common.viewmodel.state

import io.reactivex.functions.Function
import retrofit2.HttpException

enum class DataState {
    LOADING, SUCCESS, ERROR
}

class State<T> private constructor(
    val state: DataState,
    val data: T?,
    val error: Exception?
) {

    override fun toString(): String {
        return "State{" +
                "state=" + state +
                ", data=" + data +
                ", error=" + error +
                '}'.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as State<*>?
        if (state != that!!.state) return false
        if (if (data != null) data != that.data else that.data != null) return false
        return error?.equals(that.error) ?: (that.error == null)
    }

    override fun hashCode(): Int {
        var result = state.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

    companion object {
        fun <T> loading(): State<T> {
            return State(
                DataState.LOADING,
                null,
                null
            )
        }

        fun <T> error(error: Exception): State<T> {
            return State(
                DataState.ERROR,
                null,
                error
            )
        }

        fun <T> success(data: T): State<T> {
            return State(
                DataState.SUCCESS,
                data,
                null
            )
        }

        fun <T> mapToError(): Function<Throwable, State<T>> {
            return Function { throwable ->
                if (throwable is HttpException) {
                    error(
                        throwable
                    )
                } else {
                    error(
                        Exception()
                    )
                }
            }
        }

        fun <T> mapToSuccess(): Function<T, State<T>> {
            return Function { result ->
                success(
                    result
                )
            }
        }
    }
}