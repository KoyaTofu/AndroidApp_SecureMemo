package com.example.passwordauthentication

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.File

class CtrlCryptoData(context: Context) {
    // 保存先ファイル名
    private val fileName: String = "secur_private"

    // MasterKey インスタンス生成
    val mainKey: MasterKey =
        MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    // EncryptedSharedPreferences インスタンス生成
    val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        fileName,
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,  //Key暗号化用
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM //Value暗号化用
    )

    // 暗号化 & 保存
    fun store( key: String, value: String) {
        with(prefs.edit()) {
            putString( key, value)
            apply()
        }
    }

    // 復号化 & 取出
    fun load( key:String ): String {
        val loadValue: String = prefs.getString( key, "").toString()
        return  loadValue
    }

    // ファイルの後始末
    fun deletFile() {
        val appName: String = "passwordauthentication"
        val filePath = "/data/data/com." + appName + "/shared_prefs/" + fileName + ".xml"
        val deletePrefFile = File(filePath)
        if( deletePrefFile.delete() ){
            println("Shared-Prefs file is deleted !!")
        }
    }
}