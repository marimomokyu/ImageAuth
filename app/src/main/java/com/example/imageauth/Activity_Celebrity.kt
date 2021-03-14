package com.example.imageauth
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import android.content.Intent as AndroidContentIntent

//有名人の認識画面 2021/02/23_a.suzuki_test
class Activity_Celebrity : AppCompatActivity() {

    //リクエストコード（定数）設定
    companion object {
        const val CAMERA_REQUEST_CODE = 1
        const val CAMERA_PERMISSION_REQUEST_CODE = 2
        private const val READ_REQUEST_CODE: Int = 42
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    //val common= Common()
    //val txtMSG: TextView =findViewById(R.id.txtMSG)
    //val imageView01: ImageView =findViewById(R.id.imageView01)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__celebrity)

        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text=""//画面初期表示

    }

    /*　↓↓↓　ボタン押下イベント　↓↓↓　--------------------------------------------------------------------------------------*/
    // Top画面_ボタン押下
    fun onTopContentButton(view: View) {
        //TOP画面へ遷移　
        val intent = AndroidContentIntent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //カメラ_ボタン押下
    fun onBtnCameraTapped(view: View){
        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text="カメラ_ボタン押下"
//        //画像変更test
//        val imageView01: ImageView =findViewById(R.id.imageView01)
//        imageView01.setImageResource(R.drawable.koikeyuriko)//小池知事の画像

//        // 01_カメラ機能を実装したアプリが存在するかチェック
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
//            txtMSG.text="カメラ_ボタン押下　02"
            txtMSG.text="03_カメラのパーミッションを持っているか"
            //03_カメラのパーミッションを持っているか
            if (checkCameraPermission()) {
                txtMSG.text="06_カメラアプリを起動する"
                //06_カメラアプリを起動する
                takePicture()

            } else {
                txtMSG.text="04_パーミッションを得る"
                //パーミッションを得る
                grantCameraPermission()

            }
//            //02_カメラアプリが無い旨を通知する
//        } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()


    }

    //画像選択_ボタン押下
    fun onBtnImageSelectTapped(view: View) {""
        //TODO:テストのため後ほどコメントアウト
        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text="画像選択_ボタン押下"
        val imageView01: ImageView =findViewById(R.id.imageView01)
        imageView01.setImageResource(R.drawable.image01)//AWSの画像

        //フォルダから画像選択
        selectPhoto()//TODO:どのフォルダに配置すればよいか未調査
        onActivityResult(1,1,intent)

    }

    //画像処理開始_ボタン押下
    fun onBtnImageSearchTapped(view: View) {
        //TODO:テストのため後ほどコメントアウト
        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text="画像処理開始_ボタン押下"
        val imageView01: ImageView =findViewById(R.id.imageView01)
//        imageView01.setImageResource(R.drawable.suga)//菅さん画像

        //画像シェイクのアニメーション（おみくじアプリ本の処理）呼出し
        val common= Common()
        common.imageViewCom=imageView01
        common.shake()

        //TODO:AWSのAPIで有名人認識
    }
    /* 　↑↑↑　ボタン押下イベント　↑↑↑　------------------------------------------------------------------------------------------------*/

    /* 　↓↓↓　ファンクション01_画像選択　↓↓↓　------------------------------------------------------------------------------------------*/
    //ギャラリーから画像を選択　→フォルダ「No　items」表示となる
    fun selectPhoto() {
        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text="画像選択_01_画像ファイルを取出し"

        //画像ファイルを取出し
        val intent = AndroidContentIntent(AndroidContentIntent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(AndroidContentIntent.CATEGORY_OPENABLE)
            type = "image/*"
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE)//CAMERA_REQUEST_CODE　　　READ_REQUEST_CODE
        onActivityResult(1,1,intent)
    }

    /* 　↑↑↑　ファンクション_画像選択　↑↑↑　------------------------------------------------------------------------------------------ */

    //カメラアプリで写真を撮影する
    private fun dispatchTakePictureIntent() {
        AndroidContentIntent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    /* 　↓↓↓　ファンクション04_カメラ20210311test　↓↓↓　------------------------------------------------------------------------------------------ */
    //参照URL　https://qiita.com/naoi/items/04e44308221f6eb73024

    //03_カメラのパーミッションを持っているか
    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)

    //04_パーミッションを得る
    private fun grantCameraPermission() =
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE)

    //05_カメラのパーミッションが得られたか
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text="05_カメラのパーミッションが得られたか"

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            }
        }
    }

    //06_カメラアプリを起動する
    private fun takePicture() {
        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text="06_カメラアプリを起動する"

        val intent = AndroidContentIntent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(AndroidContentIntent.CATEGORY_DEFAULT)
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    //07_撮影した画像のサムネイルを取得する
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: AndroidContentIntent?) {
        val imageView01: ImageView =findViewById(R.id.imageView01)
        val txtMSG: TextView =findViewById(R.id.txtMSG)
        txtMSG.text="07_撮影した画像のサムネイルを取得する"

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data")?.let {
                //08_画像をImageViewに表示する
                imageView01.setImageBitmap(it as Bitmap)
            }
        }
    }
    /* 　↑↑↑　ファンクション04_カメラ20210311test　↑↑↑　------------------------------------------------------------------------------------------ */

}