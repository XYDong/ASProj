package com.joker.asproj

import android.os.Bundle
import android.os.PersistableBundle
import com.joker.asproj.logic.MainActivityLogic
import com.joker.asproj.logic.MainActivityLogic.ActivityProvider
import com.joker.common.ui.component.HiBaseActivity

class MainActivity : HiBaseActivity() ,ActivityProvider {

    lateinit var mainActivityLogic : MainActivityLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityLogic = MainActivityLogic(this, savedInstanceState)
    }

}