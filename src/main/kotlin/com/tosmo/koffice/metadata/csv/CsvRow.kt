package com.tosmo.akcel.metadata.csv

import org.apache.poi.ss.usermodel.*

class CsvRow(val csvWorkbook: CsvWorkbook, val csvSheet: CsvSheet, private var rowIndex: Int) :
    Row {
    private val mCells = sortedMapOf<Int, CsvCell>()
    
    private var mCellStyle: CellStyle? = null
    
    override fun createCell(column: Int): Cell {
        return createCell(column, null)
    }
    
    override fun createCell(column: Int, type: CellType?): Cell {
        return CsvCell(csvWorkbook, csvSheet, this, column, type).apply {
            mCells[column] = this
        }
    }
    
    override fun removeCell(cell: Cell) {
        mCells.remove(cell.columnIndex, cell)
    }
    
    override fun getRowNum(): Int {
        return rowIndex
    }
    
    override fun setRowNum(rowNum: Int) {
        rowIndex = rowNum
    }
    
    override fun getCell(cellnum: Int): Cell? {
        return getCell(cellnum, csvWorkbook.missingCellPolicy)
    }
    
    override fun getCell(cellnum: Int, policy: Row.MissingCellPolicy): Cell? {
        val cell = mCells[cellnum]
        return when (policy) {
            Row.MissingCellPolicy.RETURN_NULL_AND_BLANK -> cell
            Row.MissingCellPolicy.RETURN_BLANK_AS_NULL ->
                if (cell != null && cell.cellType == CellType.BLANK) {
                    null
                } else cell
            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK -> cell ?: createCell(
                cellnum, CellType.BLANK
            )
        }
    }
    
    
    override fun getFirstCellNum(): Short {
        return mCells.firstNotNullOfOrNull { it.key.toShort() } ?: -1
    }
    
    override fun getLastCellNum(): Short {
        return if (mCells.isNotEmpty()) {
            mCells.lastKey() + 1
        } else {
            -1
        }.toShort()
    }
    
    override fun getPhysicalNumberOfCells(): Int = rowNum
    
    override fun getHeight(): Short = 0
    
    override fun getZeroHeight(): Boolean = false
    
    override fun setZeroHeight(zHeight: Boolean) {
    }
    
    override fun getHeightInPoints(): Float = 0f
    
    override fun setHeight(height: Short) {
    }
    
    override fun isFormatted(): Boolean = false
    
    override fun getRowStyle(): CellStyle? = mCellStyle
    override fun setRowStyle(style: CellStyle?) {
        mCellStyle = style
    }
    
    override fun cellIterator(): MutableIterator<Cell> {
        return object : MutableIterator<Cell> {
            private val iter = mCells.iterator()
            
            override fun hasNext(): Boolean {
                return iter.hasNext()
            }
            
            override fun next(): Cell {
                return iter.next().value
            }
            
            override fun remove() {
                return iter.remove()
            }
        }
    }
    
    override fun getSheet(): Sheet {
        return csvSheet
    }
    
    override fun getOutlineLevel(): Int {
        return 0
    }
    
    override fun shiftCellsLeft(firstShiftColumnIndex: Int, lastShiftColumnIndex: Int, step: Int) {
    }
    
    override fun shiftCellsRight(firstShiftColumnIndex: Int, lastShiftColumnIndex: Int, step: Int) {
    }
    
    override fun setHeightInPoints(height: Float) {
    }
}