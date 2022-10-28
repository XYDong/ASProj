package com.joker.asproj.banner

import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.joker.asproj.R
import com.joker.common.ui.component.HiBaseActivity
import org.devio.hi.library.hilibrary.log.HiLog
import org.devio.hi.ui.tab.binner.HiBanner
import org.devio.hi.ui.tab.binner.indicator.HiCircleIndicator
import org.devio.hi.ui.tab.binner.indicator.HiIndicator

class HiBannerDemoActivity : HiBaseActivity() {
    lateinit var mHiBanner: HiBanner
    private var autoPlay: Boolean = false
    private var hiIndicator: HiIndicator<*>? = null
    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_banner_demo)
        initView(HiCircleIndicator(this), false)
        findViewById<Switch>(R.id.auto_play).setOnCheckedChangeListener{ _,isChecked ->
            autoPlay = isChecked
            initView(HiCircleIndicator(this), autoPlay)
        }
        findViewById<TextView>(R.id.tv_switch).setOnClickListener { view ->
            if (hiIndicator is HiCircleIndicator) {

            }
        }
    }

    /**
     * 是否自动播放
     */
    private fun initView(hiIndicator: HiIndicator<*>?, autoPlay: Boolean) {
        this.hiIndicator = hiIndicator
        mHiBanner = findViewById(R.id.banner)
        val moList : MutableList<BannerMo> = ArrayList()
        for (i in 0..7) {
            val mo = BannerMo()
            mo.url = urls[i%urls.size]
            moList.add(mo)
        }
        mHiBanner!!.setHiIndicator(hiIndicator)
        mHiBanner.setAutoPlay(autoPlay)
        mHiBanner.setIntervalTime(3000)
        mHiBanner.setBannerData(R.layout.banner_item_layout,moList)
        mHiBanner.setBindAdapter{viewHolder, mo, position ->
            run {
                val imageView: ImageView = viewHolder.findViewById(R.id.iv_image)
                Glide.with(this@HiBannerDemoActivity).load(mo.url).into(imageView)
                val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
                titleView.text = mo.url
                HiLog.d("----position:", position.toString() + "url:" + mo.url)

            }
        }
    }
}