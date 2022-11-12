package com.tosmo.koffice.listener

fun interface ReadListener<T : Any> {
    fun invoke(data: T)
}