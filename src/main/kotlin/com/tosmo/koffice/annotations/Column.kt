package com.tosmo.koffice.annotations

import com.tosmo.koffice.converters.AutoConverter
import com.tosmo.koffice.converters.Converter
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Column(
    /**
     * 列的名称
     */
    val name: String,

    /**
     * 所在表格中每条数据中的位置索引，优先级：index > name
     */
    val index: Int = -1,

    /**
     * 读取数据时用到的转换器
     */
    val readConverter: KClass<out Converter<*>> = AutoConverter::class,

    val writeConverter: KClass<out Converter<*>> = AutoConverter::class,
)