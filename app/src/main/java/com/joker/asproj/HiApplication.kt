package com.joker.asproj

import com.alibaba.fastjson.JSONObject
import com.joker.common.ui.component.HiBaseApplication
import org.devio.hi.library.hilibrary.log.HiLogManager
import org.devio.hi.library.hilibrary.log.HiLogConfig
import org.devio.hi.library.hilibrary.log.HiLogConfig.JsonParser
import org.devio.hi.library.hilibrary.log.HiLogPrinter

/**
 * @ProjectName: ASProj
 * @Package: com.joker.asproj
 * @ClassName: HiApplication
 * @Description:
 * @Author: xiayd
 * @CreateDate: 2022/10/27 17:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/27 17:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class HiApplication : HiBaseApplication() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> JSONObject.toJSONString(src) }
            }

            override fun getGlobalTag(): String {
                return "MApplication"
            }

            override fun enable(): Boolean {
                return true
            }

            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        })
    }
}