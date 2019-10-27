package com.wpy.basedialog.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.lang.ref.WeakReference


class DialogViewHelper() {

    private lateinit var mContentView: View
    private var mContext: Context? = null
    private var mLayoutResId: Int = 0
    private val mViews  = SparseArray<WeakReference<View>>()

    constructor(context: Context, layoutResId: Int) : this() {
        this.mContext = context
        this.mLayoutResId = layoutResId
        mContentView = LayoutInflater.from(mContext).inflate(mLayoutResId, null)
    }

    fun setContentView(contentView: View) {
        mContentView = contentView
    }

    fun getContentView(): View {
        return mContentView
    }

    fun setText(viewId: Int, text: CharSequence?) {
        val tv: TextView? = getView(viewId)
        tv?.text = text
    }

    /**
     *  获取 view
     */
    fun <T : View> getView(viewId: Int): T {
        val viewReference = mViews.get(viewId)
        var view = viewReference?.get()
        if(view == null){
            view = mContentView.findViewById(viewId)
            if(view != null){
                mViews.put(viewId,WeakReference(view))
            }
        }
        return view as T
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener?) {
        val view:View? = getView(viewId)
        view?.setOnClickListener(listener)
    }

}