package com.wpy.basedialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.wpy.basedialog.dialog.BaseDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mDialog: BaseDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDialog = BaseDialog.Budiler(this)
            .setContentView(R.layout.detail_comment_dialog)
            .fullWidth()
            .formBottom(true)
            .creatr()
        show_dialog.setOnClickListener {
            mDialog.show()
        }
        val etView = mDialog.getView<EditText>(R.id.comment_editor)
        val tvView = mDialog.getView<TextView>(R.id.submit_btn)
        tvView?.text = "发送"
        mDialog.setOnclickListener(R.id.submit_btn, View.OnClickListener {
            var content = etView?.text.toString()
            if (content.isBlank()) {
                content = "请输入内容"
            }
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
        })

        mDialog.setOnDismissListener {
            Toast.makeText(this, "弹框消失", Toast.LENGTH_SHORT).show()
        }
    }
}
