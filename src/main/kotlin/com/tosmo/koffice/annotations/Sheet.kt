package com.tosmo.koffice.annotations


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Sheet(
    /**
     * sheet的名称
     */
    val name: String,
    
    /**
     * 表头的行数
     */
    val headRowNum: Int = 0,
    
    /**
     * 自动去掉两边空白字符
     */
    val autoTrim: Boolean = true,
    
    val use1904windowing: Boolean = false,
    
    val useScientificFormat: Boolean = false
)