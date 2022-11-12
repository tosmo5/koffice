package com.tosmo.akcel.metadata.csv

import org.apache.poi.ss.SpreadsheetVersion
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

class CsvCell(
    val cwvWorkbook: CsvWorkbook,
    val csvSheet: CsvSheet,
    val csvRow: CsvRow,
    private val mColumnIndex: Int,
    private var mCellType: CellType? = null
) : CellBase() {
    
    var cellValue: CellValue? = null
    
    private var mFormula: String? = null
    
    private var mRichTextString: RichTextString? = null
    
    private var mCellStyle: CellStyle? = null
    
    override fun setCellTypeImpl(cellType: CellType) {
        this.cellType = cellType
    }
    
    override fun setCellFormulaImpl(formula: String) {
        cellFormula = formula
        cellType = CellType.FORMULA
    }
    
    override fun removeFormulaImpl() {}
    override fun setCellValueImpl(value: Double) {
        cellValue = CellValue(value)
        cellType = CellType.NUMERIC
    }
    
    override fun setCellValueImpl(value: Date) {
        cellValue = CellValue(value.toInstant().epochSecond.toDouble())
        cellType = CellType.NUMERIC
    }
    
    override fun setCellValueImpl(value: LocalDateTime) {
        cellValue = CellValue(
            value.toEpochSecond(OffsetDateTime.now(ZoneId.systemDefault()).offset).toDouble()
        )
        cellType = CellType.NUMERIC
    }
    
    override fun setCellValueImpl(value: Calendar) {
        cellValue = CellValue(value.toInstant().epochSecond.toDouble())
        cellType = CellType.NUMERIC
    }
    
    override fun setCellValueImpl(value: String) {
        cellValue = CellValue(value)
        cellType = CellType.STRING
    }
    
    override fun setCellValueImpl(value: RichTextString) {
        mRichTextString = value
        cellValue = CellValue(value.string)
        cellType = CellType.STRING
    }
    
    override fun setCellValue(value: String?) {
        if (value == null) {
            setBlank()
        } else {
            setCellValueImpl(value)
        }
    }
    
    override fun setCellValue(value: RichTextString?) {
        if (value == null || value.string == null) {
            setBlank()
        } else {
            setCellValueImpl(value)
        }
    }
    
    override fun setCellValue(value: Boolean) {
        cellValue = CellValue.valueOf(value)
        cellType = CellType.BOOLEAN
    }
    
    override fun setCellErrorValue(value: Byte) {
        cellValue = CellValue.getError(value.toInt())
        cellType = CellType.ERROR
    }
    
    override fun getSpreadsheetVersion(): SpreadsheetVersion? = null
    
    override fun getColumnIndex(): Int = mColumnIndex
    
    override fun getRowIndex(): Int {
        return csvRow.rowNum
    }
    
    override fun getCellType(): CellType? = mCellType
    
    override fun getRow(): Row = csvRow
    
    override fun getSheet(): Sheet = csvRow.sheet
    
    override fun getCachedFormulaResultType(): CellType? = cellType
    
    override fun getCellFormula(): String? = mFormula
    
    override fun getNumericCellValue(): Double {
        return cellValue?.numberValue ?: 0.0
    }
    
    override fun getDateCellValue(): Date? {
        return cellValue?.let {
            Date(it.numberValue.toLong())
        }
    }
    
    override fun getLocalDateTimeCellValue(): LocalDateTime? {
        return cellValue?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochSecond(it.numberValue.toLong()), ZoneId.systemDefault()
            )
        }
    }
    
    override fun getRichStringCellValue(): RichTextString? {
        return mRichTextString
    }
    
    override fun getStringCellValue(): String {
        return cellValue?.stringValue ?: ""
    }
    
    override fun getBooleanCellValue(): Boolean {
        return cellValue?.booleanValue ?: false
    }
    
    override fun getErrorCellValue(): Byte {
        return cellValue?.errorValue ?: 0
    }
    
    override fun setCellStyle(style: CellStyle?) {
        mCellStyle = style
    }
    
    override fun getCellStyle(): CellStyle? {
        return mCellStyle
    }
    
    override fun setAsActiveCell() {}
    override fun setCellComment(comment: Comment?) {}
    override fun getCellComment(): Comment? = null
    override fun removeCellComment() {}
    
    override fun getHyperlink(): Hyperlink? = null
    override fun setHyperlink(link: Hyperlink?) {}
    override fun removeHyperlink() {}
    
    override fun getArrayFormulaRange(): CellRangeAddress? = null
    
    override fun isPartOfArrayFormulaGroup(): Boolean = false
}