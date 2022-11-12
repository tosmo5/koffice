package com.tosmo.akcel.metadata.csv

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellAddress
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.PaneInformation

class CsvSheet: Sheet {
    override fun createRow(rownum: Int): Row {
        TODO("Not yet implemented")
    }
    
    override fun removeRow(row: Row?) {
        TODO("Not yet implemented")
    }
    
    override fun getRow(rownum: Int): Row {
        TODO("Not yet implemented")
    }
    
    override fun getPhysicalNumberOfRows(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getFirstRowNum(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getLastRowNum(): Int {
        TODO("Not yet implemented")
    }
    
    override fun setColumnHidden(columnIndex: Int, hidden: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isColumnHidden(columnIndex: Int): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setRightToLeft(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isRightToLeft(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setColumnWidth(columnIndex: Int, width: Int) {
        TODO("Not yet implemented")
    }
    
    override fun getColumnWidth(columnIndex: Int): Int {
        TODO("Not yet implemented")
    }
    
    override fun getColumnWidthInPixels(columnIndex: Int): Float {
        TODO("Not yet implemented")
    }
    
    override fun setDefaultColumnWidth(width: Int) {
        TODO("Not yet implemented")
    }
    
    override fun getDefaultColumnWidth(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getDefaultRowHeight(): Short {
        TODO("Not yet implemented")
    }
    
    override fun getDefaultRowHeightInPoints(): Float {
        TODO("Not yet implemented")
    }
    
    override fun setDefaultRowHeight(height: Short) {
        TODO("Not yet implemented")
    }
    
    override fun setDefaultRowHeightInPoints(height: Float) {
        TODO("Not yet implemented")
    }
    
    override fun getColumnStyle(column: Int): CellStyle {
        TODO("Not yet implemented")
    }
    
    override fun addMergedRegion(region: CellRangeAddress?): Int {
        TODO("Not yet implemented")
    }
    
    override fun addMergedRegionUnsafe(region: CellRangeAddress?): Int {
        TODO("Not yet implemented")
    }
    
    override fun validateMergedRegions() {
        TODO("Not yet implemented")
    }
    
    override fun setVerticallyCenter(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun setHorizontallyCenter(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getHorizontallyCenter(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun getVerticallyCenter(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun removeMergedRegion(index: Int) {
        TODO("Not yet implemented")
    }
    
    override fun removeMergedRegions(indices: MutableCollection<Int>?) {
        TODO("Not yet implemented")
    }
    
    override fun getNumMergedRegions(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getMergedRegion(index: Int): CellRangeAddress {
        TODO("Not yet implemented")
    }
    
    override fun getMergedRegions(): MutableList<CellRangeAddress> {
        TODO("Not yet implemented")
    }
    
    override fun rowIterator(): MutableIterator<Row> {
        TODO("Not yet implemented")
    }
    
    override fun setForceFormulaRecalculation(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getForceFormulaRecalculation(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setAutobreaks(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun setDisplayGuts(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun setDisplayZeros(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isDisplayZeros(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setFitToPage(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun setRowSumsBelow(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun setRowSumsRight(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getAutobreaks(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun getDisplayGuts(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun getFitToPage(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun getRowSumsBelow(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun getRowSumsRight(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun isPrintGridlines(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setPrintGridlines(show: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isPrintRowAndColumnHeadings(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setPrintRowAndColumnHeadings(show: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getPrintSetup(): PrintSetup {
        TODO("Not yet implemented")
    }
    
    override fun getHeader(): Header {
        TODO("Not yet implemented")
    }
    
    override fun getFooter(): Footer {
        TODO("Not yet implemented")
    }
    
    override fun setSelected(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getMargin(margin: Short): Double {
        TODO("Not yet implemented")
    }
    
    override fun getMargin(margin: PageMargin?): Double {
        TODO("Not yet implemented")
    }
    
    override fun setMargin(margin: Short, size: Double) {
        TODO("Not yet implemented")
    }
    
    override fun setMargin(margin: PageMargin?, size: Double) {
        TODO("Not yet implemented")
    }
    
    override fun getProtect(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun protectSheet(password: String?) {
        TODO("Not yet implemented")
    }
    
    override fun getScenarioProtect(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setZoom(scale: Int) {
        TODO("Not yet implemented")
    }
    
    override fun getTopRow(): Short {
        TODO("Not yet implemented")
    }
    
    override fun getLeftCol(): Short {
        TODO("Not yet implemented")
    }
    
    override fun showInPane(topRow: Int, leftCol: Int) {
        TODO("Not yet implemented")
    }
    
    override fun shiftRows(startRow: Int, endRow: Int, n: Int) {
        TODO("Not yet implemented")
    }
    
    override fun shiftRows(
        startRow: Int,
        endRow: Int,
        n: Int,
        copyRowHeight: Boolean,
        resetOriginalRowHeight: Boolean
    ) {
        TODO("Not yet implemented")
    }
    
    override fun shiftColumns(startColumn: Int, endColumn: Int, n: Int) {
        TODO("Not yet implemented")
    }
    
    override fun createFreezePane(colSplit: Int, rowSplit: Int, leftmostColumn: Int, topRow: Int) {
        TODO("Not yet implemented")
    }
    
    override fun createFreezePane(colSplit: Int, rowSplit: Int) {
        TODO("Not yet implemented")
    }
    
    override fun createSplitPane(
        xSplitPos: Int,
        ySplitPos: Int,
        leftmostColumn: Int,
        topRow: Int,
        activePane: Int
    ) {
        TODO("Not yet implemented")
    }
    
    override fun createSplitPane(
        xSplitPos: Int,
        ySplitPos: Int,
        leftmostColumn: Int,
        topRow: Int,
        activePane: PaneType?
    ) {
        TODO("Not yet implemented")
    }
    
    override fun getPaneInformation(): PaneInformation {
        TODO("Not yet implemented")
    }
    
    override fun setDisplayGridlines(show: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isDisplayGridlines(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setDisplayFormulas(show: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isDisplayFormulas(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setDisplayRowColHeadings(show: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isDisplayRowColHeadings(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setRowBreak(row: Int) {
        TODO("Not yet implemented")
    }
    
    override fun isRowBroken(row: Int): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun removeRowBreak(row: Int) {
        TODO("Not yet implemented")
    }
    
    override fun getRowBreaks(): IntArray {
        TODO("Not yet implemented")
    }
    
    override fun getColumnBreaks(): IntArray {
        TODO("Not yet implemented")
    }
    
    override fun setColumnBreak(column: Int) {
        TODO("Not yet implemented")
    }
    
    override fun isColumnBroken(column: Int): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun removeColumnBreak(column: Int) {
        TODO("Not yet implemented")
    }
    
    override fun setColumnGroupCollapsed(columnNumber: Int, collapsed: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun groupColumn(fromColumn: Int, toColumn: Int) {
        TODO("Not yet implemented")
    }
    
    override fun ungroupColumn(fromColumn: Int, toColumn: Int) {
        TODO("Not yet implemented")
    }
    
    override fun groupRow(fromRow: Int, toRow: Int) {
        TODO("Not yet implemented")
    }
    
    override fun ungroupRow(fromRow: Int, toRow: Int) {
        TODO("Not yet implemented")
    }
    
    override fun setRowGroupCollapsed(row: Int, collapse: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun setDefaultColumnStyle(column: Int, style: CellStyle?) {
        TODO("Not yet implemented")
    }
    
    override fun autoSizeColumn(column: Int) {
        TODO("Not yet implemented")
    }
    
    override fun autoSizeColumn(column: Int, useMergedCells: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getCellComment(ref: CellAddress?): Comment {
        TODO("Not yet implemented")
    }
    
    override fun getCellComments(): MutableMap<CellAddress, out Comment> {
        TODO("Not yet implemented")
    }
    
    override fun getDrawingPatriarch(): Drawing<*> {
        TODO("Not yet implemented")
    }
    
    override fun createDrawingPatriarch(): Drawing<*> {
        TODO("Not yet implemented")
    }
    
    override fun getWorkbook(): Workbook {
        TODO("Not yet implemented")
    }
    
    override fun getSheetName(): String {
        TODO("Not yet implemented")
    }
    
    override fun isSelected(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setArrayFormula(formula: String?, range: CellRangeAddress?): CellRange<out Cell> {
        TODO("Not yet implemented")
    }
    
    override fun removeArrayFormula(cell: Cell?): CellRange<out Cell> {
        TODO("Not yet implemented")
    }
    
    override fun getDataValidationHelper(): DataValidationHelper {
        TODO("Not yet implemented")
    }
    
    override fun getDataValidations(): MutableList<out DataValidation> {
        TODO("Not yet implemented")
    }
    
    override fun addValidationData(dataValidation: DataValidation?) {
        TODO("Not yet implemented")
    }
    
    override fun setAutoFilter(range: CellRangeAddress?): AutoFilter {
        TODO("Not yet implemented")
    }
    
    override fun getSheetConditionalFormatting(): SheetConditionalFormatting {
        TODO("Not yet implemented")
    }
    
    override fun getRepeatingRows(): CellRangeAddress {
        TODO("Not yet implemented")
    }
    
    override fun getRepeatingColumns(): CellRangeAddress {
        TODO("Not yet implemented")
    }
    
    override fun setRepeatingRows(rowRangeRef: CellRangeAddress?) {
        TODO("Not yet implemented")
    }
    
    override fun setRepeatingColumns(columnRangeRef: CellRangeAddress?) {
        TODO("Not yet implemented")
    }
    
    override fun getColumnOutlineLevel(columnIndex: Int): Int {
        TODO("Not yet implemented")
    }
    
    override fun getHyperlink(row: Int, column: Int): Hyperlink {
        TODO("Not yet implemented")
    }
    
    override fun getHyperlink(addr: CellAddress?): Hyperlink {
        TODO("Not yet implemented")
    }
    
    override fun getHyperlinkList(): MutableList<out Hyperlink> {
        TODO("Not yet implemented")
    }
    
    override fun getActiveCell(): CellAddress {
        TODO("Not yet implemented")
    }
    
    override fun setActiveCell(address: CellAddress?) {
        TODO("Not yet implemented")
    }
}