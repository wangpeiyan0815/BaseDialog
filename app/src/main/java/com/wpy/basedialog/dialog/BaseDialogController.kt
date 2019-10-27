package com.wpy.basedialog.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window

class BaseDialogController(val mDailog: BaseDialog, val mWindow: Window?) {

    var mViewHelper: DialogViewHelper? = null

    fun getDialog(): BaseDialog {
        return mDailog
    }

    fun getWindow(): Window? {
        return mWindow
    }

    fun <T : View> getView(viewId: Int): T? {
        return mViewHelper?.getView(viewId)
    }

    fun setViewHelper(viewHelper: DialogViewHelper) {
        mViewHelper = viewHelper
    }

    fun setText(viewId: Int, text: CharSequence?) {
        mViewHelper?.setText(viewId, text)
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener?) {
        mViewHelper?.setOnClickListener(viewId, listener)
    }

    class DialogParams(val mContext: Context, val mThemeResId: Int) {
        // 布局View
        var mView: View? = null
        // 布局 layoutid
        var mLayoutResId: Int = 0
        // 文字修改 array
        val mTextArray = SparseArray<CharSequence>()
        // 存放View 点击事件
        val mClickArray = SparseArray<View.OnClickListener>()
        // dialog 的宽度
        var mWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        // dialog 的高度
        var mHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        // dialog 的位置
        var mGravity = Gravity.CENTER
        // 动画
        var mAnimations: Int = 0
        // 点击空白处是否取消
        var mCancelable = true
        //
        var mOnCancelListener: DialogInterface.OnCancelListener? = null
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        var mOnKeyListener: DialogInterface.OnKeyListener? = null

        fun applyParams(mAlter: BaseDialogController) {

            var viewHelper: DialogViewHelper? = null
            if (mLayoutResId != 0) {
                viewHelper = DialogViewHelper(mContext, mLayoutResId)
            }

            if (mView != null) {
                viewHelper = DialogViewHelper()
                viewHelper.setContentView(mView!!)
            }
            requireNotNull(viewHelper) { "请设置布局setContentView()" }
            // 给 dialog 设置布局
            mAlter.getDialog().setContentView(viewHelper.getContentView())
            //设置 Controller的辅助类
            mAlter.setViewHelper(viewHelper)
            // 设置文本
            val textArraySize = mTextArray.size()
            for (i in 0 until textArraySize) {
                mAlter.setText(mTextArray.keyAt(i), mTextArray.valueAt(i))
            }

            // 设置点击事件
            val clickArraySize = mClickArray.size()
            for (i in 0 until clickArraySize) {
                mAlter.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i))
            }
            // 配置自定义的效果  全屏  从底部弹出    默认动画
            val window = mAlter.getWindow()
            window?.let {
                it.setGravity(mGravity)
                if(mAnimations != 0){
                    it.setWindowAnimations(mAnimations)
                }
                // 设置宽高
                val params =  it.attributes
                params?.width = mWidth
                params?.height = mHeight
                window?.attributes = params
            }
        }
    }
}