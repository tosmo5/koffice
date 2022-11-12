package com.tosmo.akcel.metadata.csv

import org.apache.poi.ss.SpreadsheetVersion
import org.apache.poi.ss.formula.EvaluationWorkbook
import org.apache.poi.ss.formula.udf.UDFFinder
import org.apache.poi.ss.usermodel.*
import java.io.OutputStream

class CsvWorkbook: Workbook {
    override fun close() {
        TODO("Not yet implemented")
    }
    
    override fun getActiveSheetIndex(): Int {
        TODO("Not yet implemented")
    }
    
    override fun setActiveSheet(sheetIndex: Int) {
        TODO("Not yet implemented")
    }
    
    override fun getFirstVisibleTab(): Int {
        TODO("Not yet implemented")
    }
    
    override fun setFirstVisibleTab(sheetIndex: Int) {
        TODO("Not yet implemented")
    }
    
    override fun setSheetOrder(sheetname: String?, pos: Int) {
        TODO("Not yet implemented")
    }
    
    override fun setSelectedTab(index: Int) {
        TODO("Not yet implemented")
    }
    
    override fun setSheetName(sheet: Int, name: String?) {
        TODO("Not yet implemented")
    }
    
    override fun getSheetName(sheet: Int): String {
        TODO("Not yet implemented")
    }
    
    override fun getSheetIndex(name: String?): Int {
        TODO("Not yet implemented")
    }
    
    override fun getSheetIndex(sheet: Sheet?): Int {
        TODO("Not yet implemented")
    }
    
    override fun createSheet(): Sheet {
        TODO("Not yet implemented")
    }
    
    override fun createSheet(sheetname: String?): Sheet {
        TODO("Not yet implemented")
    }
    
    override fun cloneSheet(sheetNum: Int): Sheet {
        TODO("Not yet implemented")
    }
    
    override fun sheetIterator(): MutableIterator<Sheet> {
        TODO("Not yet implemented")
    }
    
    override fun getNumberOfSheets(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getSheetAt(index: Int): Sheet {
        TODO("Not yet implemented")
    }
    
    override fun getSheet(name: String?): Sheet {
        TODO("Not yet implemented")
    }
    
    override fun removeSheetAt(index: Int) {
        TODO("Not yet implemented")
    }
    
    override fun createFont(): Font {
        TODO("Not yet implemented")
    }
    
    override fun findFont(
        bold: Boolean,
        color: Short,
        fontHeight: Short,
        name: String?,
        italic: Boolean,
        strikeout: Boolean,
        typeOffset: Short,
        underline: Byte
    ): Font {
        TODO("Not yet implemented")
    }
    
    override fun getNumberOfFonts(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getNumberOfFontsAsInt(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getFontAt(idx: Int): Font {
        TODO("Not yet implemented")
    }
    
    override fun createCellStyle(): CellStyle {
        TODO("Not yet implemented")
    }
    
    override fun getNumCellStyles(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getCellStyleAt(idx: Int): CellStyle {
        TODO("Not yet implemented")
    }
    
    override fun write(stream: OutputStream?) {
        TODO("Not yet implemented")
    }
    
    override fun getNumberOfNames(): Int {
        TODO("Not yet implemented")
    }
    
    override fun getName(name: String?): Name {
        TODO("Not yet implemented")
    }
    
    override fun getNames(name: String?): MutableList<out Name> {
        TODO("Not yet implemented")
    }
    
    override fun getAllNames(): MutableList<out Name> {
        TODO("Not yet implemented")
    }
    
    override fun createName(): Name {
        TODO("Not yet implemented")
    }
    
    override fun removeName(name: Name?) {
        TODO("Not yet implemented")
    }
    
    override fun linkExternalWorkbook(name: String?, workbook: Workbook?): Int {
        TODO("Not yet implemented")
    }
    
    override fun setPrintArea(sheetIndex: Int, reference: String?) {
        TODO("Not yet implemented")
    }
    
    override fun setPrintArea(
        sheetIndex: Int,
        startColumn: Int,
        endColumn: Int,
        startRow: Int,
        endRow: Int
    ) {
        TODO("Not yet implemented")
    }
    
    override fun getPrintArea(sheetIndex: Int): String {
        TODO("Not yet implemented")
    }
    
    override fun removePrintArea(sheetIndex: Int) {
        TODO("Not yet implemented")
    }
    
    override fun getMissingCellPolicy(): Row.MissingCellPolicy {
        TODO("Not yet implemented")
    }
    
    override fun setMissingCellPolicy(missingCellPolicy: Row.MissingCellPolicy?) {
        TODO("Not yet implemented")
    }
    
    override fun createDataFormat(): DataFormat {
        TODO("Not yet implemented")
    }
    
    override fun addPicture(pictureData: ByteArray?, format: Int): Int {
        TODO("Not yet implemented")
    }
    
    override fun getAllPictures(): MutableList<out PictureData> {
        TODO("Not yet implemented")
    }
    
    override fun getCreationHelper(): CreationHelper {
        TODO("Not yet implemented")
    }
    
    override fun isHidden(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setHidden(hiddenFlag: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun isSheetHidden(sheetIx: Int): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun isSheetVeryHidden(sheetIx: Int): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun setSheetHidden(sheetIx: Int, hidden: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getSheetVisibility(sheetIx: Int): SheetVisibility {
        TODO("Not yet implemented")
    }
    
    override fun setSheetVisibility(sheetIx: Int, visibility: SheetVisibility?) {
        TODO("Not yet implemented")
    }
    
    override fun addToolPack(toolpack: UDFFinder?) {
        TODO("Not yet implemented")
    }
    
    override fun setForceFormulaRecalculation(value: Boolean) {
        TODO("Not yet implemented")
    }
    
    override fun getForceFormulaRecalculation(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun getSpreadsheetVersion(): SpreadsheetVersion {
        TODO("Not yet implemented")
    }
    
    override fun addOlePackage(
        oleData: ByteArray?,
        label: String?,
        fileName: String?,
        command: String?
    ): Int {
        TODO("Not yet implemented")
    }
    
    override fun createEvaluationWorkbook(): EvaluationWorkbook {
        TODO("Not yet implemented")
    }
    
    override fun getCellReferenceType(): CellReferenceType {
        TODO("Not yet implemented")
    }
    
    override fun setCellReferenceType(cellReferenceType: CellReferenceType?) {
        TODO("Not yet implemented")
    }
}