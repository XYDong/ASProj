package com.joker.asproj

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.joker.asproj.fragment.HomePageFragment
import com.joker.asproj.logic.MainActivityLogic
import com.joker.asproj.logic.MainActivityLogic.ActivityProvider
import com.joker.common.ui.component.HiBaseActivity
import kotlinx.android.synthetic.main.activity_main.*

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
    }

    override fun onResume() {
        super.onResume()
        // 获取 view的宽和高的值，使用最后两种方式
        Log.e("onresume",fragment_tab_view.width.toString() + "----" + fragment_tab_view.height)
        fragment_tab_view.post {
            Log.e("onresume:post",fragment_tab_view.width.toString() +
                    "----" + fragment_tab_view.height)
        }
        fragment_tab_view.viewTreeObserver.addOnDrawListener {
            // 这段代码是禁止重复的调用addOnDrawListener
            fragment_tab_view.viewTreeObserver.removeOnGlobalLayoutListener{ this }
            Log.e("onresume:OnDrawListener",fragment_tab_view.width.toString() +
                    "----" + fragment_tab_view.height)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


}