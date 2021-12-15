package com.nasa.apod.utils

import kotlinx.coroutines.flow.*
import java.lang.Exception

fun <ResultType, RequestType> networkBoundResource(
    query: () -> Flow<ResultType>,
    fetch: suspend () -> RequestType,
    saveFetchResult: suspend (RequestType) -> Unit,
    shouldFetch: (ResultType?) -> Boolean = { true }
) = flow {
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(Resource.loading(data))
        try {
            if(Variables.isNetworkConnected) {
                saveFetchResult(fetch())
                query().map { Resource.success(it) }
            } else {
                query().map { Resource.nointernet("No internet connection!!", null) }
            }
        } catch (e: Exception) {
            query().map { e.message?.let { e -> Resource.error(e, it) } }
        }
    } else {
        query().map { Resource.success(it) }
    }

    emitAll(flow)
}