package org.devio.hi.library.hilibrary.restful

import java.io.IOException

interface HiCall<T> {

    /**
     * 同步请求
     */
    @Throws(IOException::class)
    fun execute(): HiResponse<T>

    /**
     * 异步请求
     */
    fun enqueue(callback: HiCallback<T>)

    interface  Factory {
        fun newCall(request: HiRequest):HiCall<*>
    }
}