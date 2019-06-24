package com.zksyp.studyrecord

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.zksyp.studyrecord.aidl.LocalClient
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created with Android Studio.
 * User: zhaokai
 * Date: 2019-06-24
 * Time: 15:53
 * Desc:
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_jump.setOnClickListener {
            val intent = Intent(this, LocalClient::class.java)
            startActivity(intent)
        }
    }
}