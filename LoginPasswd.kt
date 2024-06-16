package com.example.passwordauthentication

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordauthentication.databinding.LoginPasswdBinding

//region パスワード表示・非表示　チェックボックス選択
class ShowHide : AppCompatActivity() {
    fun isCheckedShow( editPwd: EditText, chbShow: CheckBox) {
        chbShow.setOnCheckedChangeListener { _, isChecked ->
            if ( isChecked ) {
                //パスワード表示
                runOnUiThread {
                    editPwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }
            } else {
                //パスワード非表示
                runOnUiThread {
                    editPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
            }
        }
    }
}
//endregion

class LoginPasswd : AppCompatActivity() {
    private lateinit var binding: LoginPasswdBinding
    private lateinit var editPwd: EditText
    private lateinit var chbShow: CheckBox

    lateinit var ctrlCryptoData: CtrlCryptoData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPasswdBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // インスタンス生成
        ctrlCryptoData = CtrlCryptoData(this)

        // パスワード暗号化&保存(初期設定)
        ctrlCryptoData.store("password", "password")

        // 各要素の紐づけ
        editPwd = binding.editPassword
        chbShow = binding.chbShowPassword

        //チェックボックスをもとにパスワードの表示・非表示選択
        val showHide = ShowHide()
        showHide.isCheckedShow(editPwd, chbShow)

        //ボタン:OKが押された時ログイン処理
        binding.btnOk.setOnClickListener {
            pwdAuthenticate()
        }

        //エンターキーが押された時ログイン処理
        isEnterLogin(editPwd) {pwdAuthenticate()}

    }
    //region isEnterLogin: エンターキーが押されたらログイン処理実行
    private fun isEnterLogin(editText: TextView, action: () -> Unit) {
        editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                action()
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }
    }
    //endregion
    //region pwdAuthenticate: ログイン処理
    private fun pwdAuthenticate() {
        val editPwd: String = editPwd.text.toString()
        val loadPwd: String = ctrlCryptoData.load("password")

        if( loadPwd == editPwd ){
            // パスワードが正しければスルー
            finish()
        } else {
            // 間違っていればエラー
            AlertDialog.Builder(this)
                .setTitle("エラー")
                .setMessage("パスワードが違います")
                .setPositiveButton("OK"){ _, _ ->}
                .show()
            binding.editPassword.editableText.clear()
        }

    }
    //endregion

}