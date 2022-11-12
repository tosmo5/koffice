package com.tosmo.koffice.enums

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import kotlin.reflect.KClass

enum class CellDataType(var defaultKClass: KClass<*>) {
    EMPTY(Any::class),
    STRING(String::class),
    NUMBER(Number::class),
    BOOLEAN(Boolean::class),
    
    /**
     * 日期，只在写的时候有用
     */
    DATE(Number::class),
    ERROR(String::class),
    
    /**
     * 富文本类型，只在写的时候有用
     */
    RICH_TEXT_STRING(String::class);
    
    companion object {
        
        fun valueOf(cellType: CellType): CellDataType {
            return when (cellType) {
                CellType.NUMERIC -> NUMBER
                CellType.STRING -> STRING
                CellType.FORMULA -> STRING
                CellType.BOOLEAN -> BOOLEAN
                CellType.ERROR -> ERROR
                else -> EMPTY
            }
        }
        
        fun valueOf(cell: Cell): CellDataType {
            return valueOf(cell.cellType)
        }
    }
}