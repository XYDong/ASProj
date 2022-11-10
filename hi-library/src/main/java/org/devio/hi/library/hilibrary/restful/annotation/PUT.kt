package org.devio.hi.library.hilibrary.restful.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PUT(val value: String, val formPost: Boolean = false)