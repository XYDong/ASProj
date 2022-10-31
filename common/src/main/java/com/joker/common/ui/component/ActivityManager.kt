package com.joker.common.ui.component

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * 声明周期监听器
 */
class ActivityManager private constructor() {

    /**
     * 存放activity，使用弱引用，防止内存泄漏
     */
    private val activityRefs = ArrayList<WeakReference<Activity>>()

    private val frontBackCallBack = ArrayList<FrontBackCallBack>()

    /**
     * 当前前台有多少个activity
     */
    private var activityStartCount = 0

    /**
     * 当前应用是否在前台
     */
    private var front = true

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks());
    }

    inner class InnerActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            // activityStartCount > 0 应用处于可见状态，也就是前台
            // 有可能是打开新的activity或者后台切前台
            // 所以需要判断之前是不是在后台
            if (!front && activityStartCount > 0) {
                front = true
                onFrontBackChanged(true)
            }
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount <= 0 && front) {
                front = false
                onFrontBackChanged(front)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef.get() == activity) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }

    }

    /**
     * 监听变化后传递出去
     */
    private fun onFrontBackChanged(front: Boolean) {
        for (frontBackCallBack in frontBackCallBack) {
            frontBackCallBack.onChanged(front)
        }
    }

    val topActivity:Activity? get() {
        if (activityRefs.size <= 0){
            return null
        }
        return activityRefs[activityRefs.size-1].get()
    }

    fun addFrontBackCallBack(callBack: FrontBackCallBack) {
        frontBackCallBack.add(callBack)
    }

    fun removeFrontBackCallBack(callBack: FrontBackCallBack) {
        frontBackCallBack.remove(callBack)
    }


    /**
     * 切换监听
     */
    interface FrontBackCallBack {
        fun onChanged(front: Boolean)
    }

    companion object {
        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

}