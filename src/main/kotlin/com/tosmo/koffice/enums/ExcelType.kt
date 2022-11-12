package com.tosmo.koffice.enums

import java.io.File

enum class ExcelType {
    XLS,
    XLSX;
//    CSV;
    
    companion object {
        fun valueOf(file: File): ExcelType {
            return valueOf(file.nameWithoutExtension.split('.').last().uppercase())
        }
        
        fun valueOfFileName(fileName: String): ExcelType {
            return valueOf(fileName.split('.').last().uppercase())
        }
    }
}