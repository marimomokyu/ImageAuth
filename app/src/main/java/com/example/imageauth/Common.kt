package com.example.imageauth

import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
//import kotlin.random.Random
import java.util.*


//2021/01_a.suzuki_test
class Common {
lateinit var imageViewCom: ImageView
    var finish =false

    val number: Int
    get(){
        val rnd = Random()
        return rnd.nextInt(20)
    }

    //アニメーションで画像を動かす（おみくじアプリ本test01）
    fun shake(){
        val translate = TranslateAnimation(0f,0f,0f,-200f)
        translate.repeatMode= Animation.REVERSE
        translate.repeatCount=5
        translate.duration=100
        /*
         val imageView01: ImageView =findViewById(R.id.imageView01)
         imageView01.startAnimation(translate)
         //画像変更test
         imageView01.setImageResource(R.drawable.suga)
         */
        //アニメーションを合成
        //val imageView01: ImageView =findViewById(R.id.imageView01)
        val rotate = RotateAnimation(0f,-36f,imageViewCom.width/2f,imageViewCom.height/2f)
        rotate.duration=200

        val set = AnimationSet(true)
        set.addAnimation(rotate)
        set.addAnimation(translate)

        imageViewCom.startAnimation(set)

        finish=true
    }
}