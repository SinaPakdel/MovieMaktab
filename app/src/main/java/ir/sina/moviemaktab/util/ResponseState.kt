package ir.sina.moviemaktab.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.sina.moviemaktab.model.dto.ApiItemError
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.net.ssl.SSLException

sealed class ResponseState<out T> {
    object Loading : ResponseState<Nothing>()
    data class Error(val message: String) : ResponseState<Nothing>()
    class Success<out T>(val data: T) : ResponseState<T>()
}

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> Response<T>
) = flow {
    try {
        val response = apiCall()
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            emit(ResponseState.Success(responseBody))
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                val type = object : TypeToken<ApiItemError>() {}
                val responseError =
                    Gson().fromJson<ApiItemError>(errorBody.charStream(), type)
                emit(ResponseState.Error(responseError.message))
            } else {
                emit(ResponseState.Error("Error is null"))
            }
        }
    } catch (e: SSLException) {
        emit(ResponseState.Error(e.message.toString()))
//        emit(ResponseState.Error("SSl problem!!!"))
    } catch (e: IOException) {
        emit(ResponseState.Error(e.message.toString()))
    } catch (e: HttpException) {
        emit(ResponseState.Error(e.message.toString()))
    } catch (e: Throwable) {
        emit(ResponseState.Error(e.message.toString()))
    }
}.onStart {
    emit(ResponseState.Loading)
}.catch {

}