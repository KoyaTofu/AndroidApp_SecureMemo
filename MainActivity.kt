package com.example.passwordauthentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // メイン画面設定
        setContentView(R.layout.main_activity)

        // アプリの実行状態(レイヤー)監視
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver(this))
    }
}

class AppLifecycleObserver(private val context: Context) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        // アプリがフォアグラウンドに来た時の処理
        performLogin()
    }

    private fun performLogin() {
        // ログイン処理を実装
        println("Logging in...")
        // ログイン処理を開始
        val intent: Intent = Intent(context, LoginPasswd::class.java)
        context.startActivity(intent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        // アプリがバックグラウンドに移動した時の処理（必要なら）
        println("App moved to background")
    }
}


