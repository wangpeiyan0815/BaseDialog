package com.wpy.basedialog.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.wpy.basedialog.R


class BaseDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    val mAlert: BaseDialogController = BaseDialogController(this, window)

    // 设置文本
    fun setText(viewId: Int, text: CharSequence) {
        mAlert.setText(viewId, text)
    }

    // 获取View
    fun <T : View> getView(viewId: Int): T? {
        return mAlert.getView(viewId)
    }

    // 设置点击事件
    fun setOnclickListener(viewId: Int, listnter: View.OnClickListener) {
        mAlert.setOnClickListener(viewId, listnter)
    }

    class Budiler(val mContext: Context, val mThemeResId: Int) {

        constructor(context: Context) : this(context, R.style.dialog)

        val P: BaseDialogController.DialogParams = BaseDialogController.DialogParams(mContext, mThemeResId)

        fun setContentView(view: View): Budiler {
            P.mView = view
            P.mLayoutResId = 0
            return this
        }

        // 设置布局内容Id
        fun setContentView(layoutResId: Int): Budiler {
            P.mView = null
            P.mLayoutResId = layoutResId
            return this
        }

        // 设置文本
        fun setText(viewId: Int, text: CharSequence): Budiler {
            P.mTextArray.put(viewId, text)
            return this
        }

        // 设置点击事件
        fun setOnlickListenter(view: Int, listnter: View.OnClickListener): Budiler {
            P.mClickArray.put(view, listnter)
            return this
        }

        // 配置一些万能参数
        fun fullWidth(): Budiler {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT
            return this
        }

        // 从底部弹出
        fun formBottom(isAnimation: Boolean): Budiler {
            if (isAnimation) {
                P.mAnimations = R.style.dialog_from_bottom_anim
            }
            P.mGravity = Gravity.BOTTOM
            return this
        }

        // 设置 dialog 的宽高
        fun setWidthAndHeight(width: Int, height: Int): Budiler {
            P.mWidth = width
            P.mHeight = height
            return this
        }

        // 添加默认动画
        fun setDefaultAnimation(): Budiler {
            P.mAnimations = R.style.dialog_scale_anim
            return this
        }

        // 设置动画
        fun setAnimations(syleAnimation: Int): Budiler {
            P.mAnimations = syleAnimation
            return this
        }

        // 设置点击空白处是否消失
        fun setCancelable(isCancelable: Boolean): Budiler {
            P.mCancelable = isCancelable
            return this
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener): Budiler {
            P.mOnCancelListener = onCancelListener
            return this
        }

        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): Budiler {
            P.mOnDismissListener = onDismissListener
            return this
        }

        fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener): Budiler {
            P.mOnKeyListener = onKeyListener
            return this
        }

        fun creatr(): BaseDialog {
            val dialog = BaseDialog(mContext, mThemeResId)
            P.applyParams(dialog.mAlert)
            dialog.setCancelable(P.mCancelable)
            if (P.mOnCancelListener != null) {
                dialog.setOnCancelListener(P.mOnCancelListener)
            }
            if (P.mOnDismissListener != null) {
                dialog.setOnDismissListener(P.mOnDismissListener)
            }
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener)
            }
            return dialog
        }

        fun show(): BaseDialog {
            val dialog = creatr()
            dialog.show()
            return dialog
        }
    }
}