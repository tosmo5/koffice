package com.tosmo.koffice.proxy

import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.metadata.DataFormatData
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType

class KtCell internal constructor(var ktRow: KtRow<*>, internal val rawCell: Cell) {

    internal constructor(ktRow: KtRow<*>, columnIndex: Int) : this(
        ktRow, ktRow.rawRow.getCell(columnIndex)
    )

    /**
     * 单元格所在列坐标
     */
    val columnIndex: Int
        get() = rawCell.columnIndex

    /**
     * 单元格所在行坐标
     */
    val rowIndex: Int
        get() = rawCell.rowIndex

    /**
     * 单元格持有的数据
     */
    val data: Any?
        get() {
            val value = ktRow.ktSheet.ktWorkbook.evaluator.evaluate(rawCell)
            return value?.let {
                when (it.cellType) {
                    CellType.NUMERIC -> it.numberValue
                    CellType.STRING -> it.stringValue
                    CellType.BOOLEAN -> it.booleanValue
                    CellType.ERROR -> it.errorValue
                    else -> null
                }
            }
        }

    val type: CellDataType = CellDataType.valueOf(rawCell)

    /**
     * @see CellDataType.NUMBER
     * @see CellDataType.DATE
     */
    val numberValue: Double
        get() = data?.toString()?.toDoubleOrNull() ?: 0.0

    /**
     * @see CellDataType.STRING
     * @see CellDataType.ERROR
     */
    val stringValue: String
        get() = data?.toString() ?: ""

    /**
     * @see CellDataType.BOOLEAN
     */
    val booleaValue: Boolean
        get() = data?.toString()?.toBooleanStrictOrNull() ?: false

    /**
     * 公式
     */
    var formula: String?
        get() = if (rawCell.cellType == CellType.FORMULA) {
            rawCell.cellFormula
        } else null
        set(value) {
            rawCell.cellFormula = value
        }

    var dataFormatData: DataFormatData? = null

    /**
     * 单元格样式
     */
    var style: KtCellStyle? = rawCell.cellStyle?.let { KtCellStyle(it) }
}
