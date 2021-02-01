package com.example.imageauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //ラベル認証画面ボタン押下
    fun onLblButtonTapped(view: View?){
        val intent = Intent(this, Activity_Label::class.java)
        startActivity(intent)
    }

    //コンテンツ節度測定ボタン押下
    fun onContentButtonTapped(view: View?){
        val intent = Intent(this, Activity_Content::class.java)
        startActivity(intent)
    }
    //テキスト検出ボタン押下
    fun onTextButtonTapped(view: View?){
        val intent = Intent(this, Activity_Text::class.java)
        startActivity(intent)
    }
   // 顔検出と分析ボタン押下
   fun onFaceButtonTapped(view: View?){
       val intent = Intent(this, Activity_Face::class.java)
       startActivity(intent)
   }
    //有名人の認識ボタン押下
    fun onCelebrityButtonTapped(view: View?){
        val intent = Intent(this, Activity_Celebrity::class.java)
        startActivity(intent)
    }

    //0201テスト
}