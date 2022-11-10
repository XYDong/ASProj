package org.devio.hi.library.hilibrary.restful

/**
 * 代理CallFactory创建出来的call对象，从而实现拦截器的派发动作
 */
class Scheduler constructor(
    private val callFactory: HiCall.Factory,
    private val interceptors: MutableList<HiInterceptor>
) {
    fun newCall(request: HiRequest): HiCall<*> {
        var newCall = callFactory.newCall(request)
        return ProxyCall(newCall, request)

    }

    inner class ProxyCall<T>(
        val delegate: HiCall<T>,
        val request: HiRequest
    ) : HiCall<T> {
        override fun execute(): HiResponse<T> {
            dispatcheInterceptor(request, null)
            val response = delegate.execute()
            dispatcheInterceptor(request,response)
            return response
        }

        override fun enqueue(callback: HiCallback<T>) {
            dispatcheInterceptor(request,null)
            delegate.enqueue(object : HiCallback<T>{
                override fun onSuccess(response: HiResponse<T>) {
                    dispatcheInterceptor(request,response)
                    if (callback != null) {
                        callback.onSuccess(response)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    if (callback != null) {
                        callback.onFailed(throwable)
                    }
                }
            })
        }


    }

    private fun dispatcheInterceptor(request: HiRequest, response: HiResponse<*>?) {
        InterceptorChain(request, response)
    }

    internal inner class InterceptorChain(val request: HiRequest, val response: HiResponse<*>?) :
        HiInterceptor.Chain {

        var callIndex = 0
        override val isRequestPeriod: Boolean
            get() = response == null

        override fun request(): HiRequest {
            return request
        }

        override fun response(): HiResponse<*>? {
            return response
        }

        fun dispatch() {
            val interceptor = interceptors[callIndex]
            val intercept = interceptor.intercept(this)
            callIndex++
            if (!intercept && callIndex < interceptors.size ){
                // 递归调用
                dispatch()
            }
        }

    }

}

