package com.tosmo.koffice.enums

enum class VerticalAlignment {
    TOP,
    CENTER,
    BOTTOM,
    JUSTIFY,
    DISTRIBUTED
    ;
    
    companion object {
        internal fun valueOf(align: org.apache.poi.ss.usermodel.VerticalAlignment): VerticalAlignment {
            return when(align) {
                org.apache.poi.ss.usermodel.VerticalAlignment.TOP -> TOP
                org.apache.poi.ss.usermodel.VerticalAlignment.CENTER -> CENTER
                org.apache.poi.ss.usermodel.VerticalAlignment.BOTTOM -> BOTTOM
                org.apache.poi.ss.usermodel.VerticalAlignment.JUSTIFY -> JUSTIFY
                org.apache.poi.ss.usermodel.VerticalAlignment.DISTRIBUTED -> DISTRIBUTED
            }
        }
        
        internal fun valueOf(align: VerticalAlignment): org.apache.poi.ss.usermodel.VerticalAlignment {
            return when(align) {
                TOP -> org.apache.poi.ss.usermodel.VerticalAlignment.TOP
                CENTER -> org.apache.poi.ss.usermodel.VerticalAlignment.CENTER
                BOTTOM -> org.apache.poi.ss.usermodel.VerticalAlignment.BOTTOM
                JUSTIFY -> org.apache.poi.ss.usermodel.VerticalAlignment.JUSTIFY
                DISTRIBUTED -> org.apache.poi.ss.usermodel.VerticalAlignment.DISTRIBUTED
            }
        }
    }
}