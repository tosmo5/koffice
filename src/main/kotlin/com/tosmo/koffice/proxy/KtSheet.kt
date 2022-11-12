package com.tosmo.koffice.proxy

import com.tosmo.koffice.listener.ReadListener
import com.tosmo.koffice.metadata.propety.SheetProperty
import org.apache.poi.ss.usermodel.Sheet
import kotlin.reflect.KClass

class KtSheet<T : Any> internal constructor(
    val ktWorkbook: KtWorkbook,
    internal val rawSheet: Sheet,
    val kClass: KClass<T>
) : MutableIterable<KtRow<T>> {
    
    constructor(ktWorkbook: KtWorkbook, kClass: KClass<T>) : this(
        ktWorkbook, ktWorkbook.rawBook.getSheet(SheetProperty[kClass].sheetName), kClass
    )
    
    /**
     * Sheet的配置信息
     * @see SheetProperty
     */
    val properties = SheetProperty[kClass]
    
    /**
     * 数据行号的区间
     */
    val indices: IntRange
        get() = properties.headRowNum..rawSheet.lastRowNum
    
    /**
     * 读取sheet中所有的数据，以[List]的结果返回，在读取到每一个数据时，调用一次[readListener]
     */
    fun read(readListener: ReadListener<T>? = null): List<T> {
        return buildList {
            for (i in this@KtSheet.indices) {
                val data = this@KtSheet[i].createBean()
                readListener?.invoke(data)
                add(data)
            }
        }
    }
    
    operator fun get(rowIx: Int): KtRow<T> {
        return KtRow(this, rowIx, kClass)
    }
    
    operator fun get(rowIx: Int, colIx: Int): KtCell {
        return KtCell(KtRow(this, rowIx, kClass), colIx)
    }
    
    override fun iterator(): MutableIterator<KtRow<T>> {
        return object : MutableIterator<KtRow<T>> {
            private val iter = rawSheet.rowIterator()
            override fun hasNext(): Boolean = iter.hasNext()
            override fun next(): KtRow<T> = KtRow(this@KtSheet, iter.next(), kClass)
            override fun remove() {
                iter.remove()
            }
        }
    }
}