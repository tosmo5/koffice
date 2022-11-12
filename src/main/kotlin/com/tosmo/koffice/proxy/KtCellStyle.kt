package com.tosmo.koffice.proxy

import com.tosmo.koffice.enums.HorizontalAlignment
import com.tosmo.koffice.enums.VerticalAlignment
import org.apache.poi.ss.usermodel.CellStyle

class KtCellStyle internal constructor(internal var rawStyle: CellStyle) {
    
    /**
     * 单元格的水平对齐类型
     */
    var horizontalAlignment: HorizontalAlignment
        get() = HorizontalAlignment.valueOf(rawStyle.alignment)
        set(value) {
            rawStyle.alignment = HorizontalAlignment.valueOf(value)
        }
    
    /**
     * 单元格的垂直对齐类型
     */
    var verticalAlignment: VerticalAlignment
        get() = VerticalAlignment.valueOf(rawStyle.verticalAlignment)
        set(value) {
            rawStyle.verticalAlignment = VerticalAlignment.valueOf(value)
        }
    
    /**
     * 单元格隐藏
     */
    var isHidden: Boolean
        get() = rawStyle.hidden
        set(value) {
            rawStyle.hidden = value
        }
    
    /**
     * 获取工作簿中的索引
     */
    val index: Int
        get() = rawStyle.index.toInt()
    
    /**
     * 单元格锁定
     */
    var isLocked: Boolean
        get() = rawStyle.locked
        set(value) {
            rawStyle.locked = value
        }
    
    /**
     * 设置文本是否应该换行。将此标志设置为true将使所有内容可见，并在一个单元格中通过在多行上显示它
     */
    var isWrapText: Boolean
        get() = rawStyle.wrapText
        set(value) {
            rawStyle.wrapText = value
        }
    
    /**
     * 单元格背景色
     */
    var backgroundColor: Short
        get() = rawStyle.fillBackgroundColor
        set(value) {
            rawStyle.fillBackgroundColor = value
        }
    
    /**
     * 单元格前景色
     */
    var foregroundColor: Short
        get() = rawStyle.fillForegroundColor
        set(value) {
            rawStyle.fillForegroundColor = value
        }
    
    fun copyFrom(source: KtCellStyle) {
        rawStyle.cloneStyleFrom(source.rawStyle)
    }
}