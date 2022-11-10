package com.joker.asproj

import android.os.Bundle
import com.joker.asproj.api.TestApi
import com.joker.asproj.logic.MainActivityLogic
import com.joker.asproj.logic.MainActivityLogic.ActivityProvider
import com.joker.common.http.ApiFactory
import com.joker.common.ui.component.HiBaseActivity
import org.devio.hi.library.hilibrary.log.HiLog
import org.devio.hi.library.hilibrary.restful.HiCallback
import org.devio.hi.library.hilibrary.restful.HiResponse
import org.json.JSONObject

class MainActivity : HiBaseActivity() ,ActivityProvider {

    lateinit var mainActivityLogic : MainActivityLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityLogic = MainActivityLogic(this, savedInstanceState)
//        supportFragmentManager
//            .beginTransaction()
//            .add(1,HomePageFragment())
//            .replace(2,HomePageFragment())
//            .remove(HomePageFragment())
//            .addToBackStack(null)
//            .commit()
//        startActivity(Intent())

        ApiFactory.create(TestApi::class.java).listCities("北京").enqueue(object :
            HiCallback<JSONObject> {
            override fun onSuccess(response: HiResponse<JSONObject>) {
                HiLog.i("接口请求成功：${response.data}")
                HiLog.i("接口请求成功：${response.msg}")
            }

            override fun onFailed(throwable: Throwable) {
                HiLog.i("接口请求成功：$throwable.message")
            }

        })
    }

    override fun onResume() {
        super.onResume()
        // 获取 view的宽和高的值，使用最后两种方式
//        Log.e("onresume",fragment_tab_view.width.toString() + "----" + fragment_tab_view.height)
//        fragment_tab_view.post {
//            Log.e("onresume:post",fragment_tab_view.width.toString() +
//                    "----" + fragment_tab_view.height)
//        }
//        fragment_tab_view.viewTreeObserver.addOnDrawListener {
//            // 这段代码是禁止重复的调用addOnDrawListener
//            fragment_tab_view.viewTreeObserver.removeOnGlobalLayoutListener{ this }
//            Log.e("onresume:OnDrawListener",fragment_tab_view.width.toString() +
//                    "----" + fragment_tab_view.height)
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


}