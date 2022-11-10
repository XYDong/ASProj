package com.joker.asproj.api

import org.devio.hi.library.hilibrary.restful.HiCall
import org.devio.hi.library.hilibrary.restful.annotation.Filed
import org.devio.hi.library.hilibrary.restful.annotation.GET
import org.json.JSONObject

interface TestApi {


    @GET("cities")
    fun listCities(@Filed("name") name: String): HiCall<JSONObject>

}