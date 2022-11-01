package org.devio.hi.ui.tab.item

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * recyclerView item适配
 */
abstract class HiDataItem<DATA,VH: ViewHolder>(data: DATA) {
    private var mAdapter: HiAdapter? = null
    var mData: DATA? =null
    init {
        this.mData = data
    }

    fun setAdapter(adapter: HiAdapter){
        this.mAdapter = adapter
    }

    /**
     * 绑定数据
     */
    abstract fun onBindData(holder: ViewHolder, position: Int)

    /**
     * 返回该item的资源id
     */
    open fun getItemLayoutRes(): Int{
        return -1
    }

    /**
     * 返回该item的视图
     */
    open fun getItemView(parent: ViewGroup): View?{
        return null
    }

    /**
     * 刷新列表
     */
    fun refreshItem(){
        mAdapter?.refreshItem(this)
    }

    /**
     * 从列表上移除
     */
    fun removeItem(){
        mAdapter?.removeItem(this)
    }

    /**
     * 该item在列表上占据几列
     */
    fun getSpanSize(): Int{
        return 0
    }

}