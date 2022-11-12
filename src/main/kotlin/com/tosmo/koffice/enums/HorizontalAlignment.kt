package com.tosmo.koffice.enums

enum class HorizontalAlignment {
    GENERAL,
    LEFT,
    CENTER,
    RIGHT,
    FILL,
    JUSTIFY,
    CENTER_SELECTION,
    DISTRIBUTED
    ;
    
    companion object {
        internal fun valueOf(align: org.apache.poi.ss.usermodel.HorizontalAlignment): HorizontalAlignment {
            return when (align) {
                org.apache.poi.ss.usermodel.HorizontalAlignment.GENERAL -> GENERAL
                org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT -> LEFT
                org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER -> CENTER
                org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT -> RIGHT
                org.apache.poi.ss.usermodel.HorizontalAlignment.FILL -> FILL
                org.apache.poi.ss.usermodel.HorizontalAlignment.JUSTIFY -> JUSTIFY
                org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER_SELECTION -> CENTER_SELECTION
                org.apache.poi.ss.usermodel.HorizontalAlignment.DISTRIBUTED -> DISTRIBUTED
            }
        }
        
        
        internal fun valueOf(align: HorizontalAlignment): org.apache.poi.ss.usermodel.HorizontalAlignment {
            return when (align) {
                GENERAL -> org.apache.poi.ss.usermodel.HorizontalAlignment.GENERAL
                LEFT -> org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT
                CENTER -> org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER
                RIGHT -> org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT
                FILL -> org.apache.poi.ss.usermodel.HorizontalAlignment.FILL
                JUSTIFY -> org.apache.poi.ss.usermodel.HorizontalAlignment.JUSTIFY
                CENTER_SELECTION -> org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER_SELECTION
                DISTRIBUTED -> org.apache.poi.ss.usermodel.HorizontalAlignment.DISTRIBUTED
            }
        }
    }
}