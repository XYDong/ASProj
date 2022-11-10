package org.devio.hi.library.hilibrary.restful

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap


class HiRestful constructor(val baseUrl: String, val callFactory: HiCall.Factory){
    private var scheduler: Scheduler

    // 存储拦截器
    private var interceptors: MutableList<HiInterceptor> = mutableListOf()
    // 避免多次解析，造成不必要的浪费
    private var methodService: ConcurrentHashMap<Method,MethodParser> = ConcurrentHashMap()


    init {
        scheduler = Scheduler(callFactory,interceptors)

    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service), object : InvocationHandler{
            override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
                var methodParser = methodService.get(method)
                if (methodParser == null) {
                    methodParser = MethodParser.parse(baseUrl,method)
                    methodService[method] = methodParser
                }
                //bugFix：此处 应当考虑到 methodParser复用，每次调用都应当解析入参
                val newRequest = methodParser.newRequest(method, args)
//                callFactory.newCall(newRequest)
                return scheduler.newCall(newRequest)
            }
        }
        ) as T
    }

    fun addInterceptor(interceptor: HiInterceptor) {
        interceptors.add(interceptor)
    }
}