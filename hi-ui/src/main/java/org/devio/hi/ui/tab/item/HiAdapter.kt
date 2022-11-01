package org.devio.hi.ui.tab.item

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.lang.reflect.ParameterizedType

class HiAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private var mContext: Context
    private var mInflater: LayoutInflater? = null
    private var dataSets = ArrayList<HiDataItem<*, ViewHolder>>()
    private var typeArrays =
        SparseArray<HiDataItem<*, ViewHolder>>() // 查找效率高，用来关联item和type的

    init {
        this.mContext = context
        mInflater = LayoutInflater.from(mContext)
    }

    /**
     * @param index 给指定位置添加item
     * @param item item
     * @param notify 添加之后是否要刷新
     */
    fun addItem(index: Int, item: HiDataItem<*, ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }
        val notifyPos = if (index > 0) index else dataSets.size - 1
        notifyItemChanged(notifyPos)

    }

    /**
     * 添加list
     */
    fun addItems(@NonNull items: List<HiDataItem<*, ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        items.forEach {
            dataSets.add(it)
        }
        if (notify) {
            // 刷新指定区间的数据
            notifyItemRangeInserted(start, dataSets.size - 1)
        }
    }

    /**
     * 删除指定位置的item
     */
    fun removeItem(index: Int): HiDataItem<*, *>? {
        if (index > 0 && index < dataSets.size) {
            return dataSets.removeAt(index)
        }
        return null
    }

    /**
     * 删除指定元素
     */
    fun removeItem(@NonNull item: HiDataItem<*, *>) {
        val index = dataSets.indexOf(item)
        removeItem(index)
    }
    fun refreshItem(@NonNull item: HiDataItem<*, *>) {
        val index = dataSets.indexOf(item)
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataItem = typeArrays[viewType]
        var itemView = dataItem.getItemView(parent)
        if (itemView == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                throw RuntimeException("dataItem: " + dataItem.javaClass.name + " must override getItemView or getItemLayoutRes.")
            }
            itemView = mInflater?.inflate(layoutRes, parent)
        }
        return createViewHolderInternal(dataItem.javaClass, itemView)!!

    }

    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, ViewHolder>>,
        view: View?
    ): ViewHolder? {
        // 通过javaClass.genericSuperclass获取HiDataItem 的class对象
        val superclass = javaClass.genericSuperclass
        // 判断是否是参数泛型类型的
        if (superclass is ParameterizedType) {
            // 获取泛型集合
            var arguments = superclass.actualTypeArguments
            for (argument in arguments) {
                // 跌倒泛型参数的class对象
                if (argument is Class<*> && ViewHolder::class.java.isAssignableFrom(argument)) run {
                    return argument.getConstructor(View::class.java).newInstance(view) as ViewHolder?
                }
            }
        }
        return object : ViewHolder(view!!){}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hiDataItem = dataSets[position]
        hiDataItem.onBindData(holder,position)
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    override fun getItemViewType(position: Int): Int {
        // item和viewtype关联，可以用item的class的hancode作为type，相同类型的item返回值是一样的
        val dataItem = dataSets[position]
        val type = dataItem.javaClass.hashCode()
        // 因为还没有关联，所以需要先做关联
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size){
                        val hiDataItem = dataSets[position]
                        val spanSize = hiDataItem.getSpanSize()
                        return  if (spanSize <= 0) spanCount else spanSize
                    }
                    return spanCount
                }

            }
        }
    }
}