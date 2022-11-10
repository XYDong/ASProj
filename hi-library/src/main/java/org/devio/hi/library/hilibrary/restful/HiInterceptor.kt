package org.devio.hi.library.hilibrary.restful

/**
 * 拦截器
 * Chain 对象会在我们派发拦截器的时候 创建
 */
interface HiInterceptor {

    /**
     * 拦截方法
     */
    fun intercept(chain: Chain): Boolean

    interface Chain{

        /**
         * 在发起请求之前判断但钱是否在request阶段，从而决定是否要response处理
         */
        val isRequestPeriod: Boolean get() = false

        fun request(): HiRequest

        /**
         * 这个response对象 在网络发起之前 ，是为空的
         */
        fun response(): HiResponse<*>?
    }
}