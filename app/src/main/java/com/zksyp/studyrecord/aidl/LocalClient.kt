package com.zksyp.studyrecord.aidl

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.zksyp.studyrecord.R
import kotlinx.android.synthetic.main.activity_local_client.*


/**
 * Created with Android Studio.
 * User: zhaokai
 * Date: 2019-06-24
 * Time: 14:59
 * Desc:
 */
class LocalClient : Activity() {

    private var mAIDLTestM: AIDLTestManager? = null
    private var mIsConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_client)
        btn_set.setOnClickListener {
            if (!mIsConnected) {
                tryToBindService()
                return@setOnClickListener
            }

            mAIDLTestM?.setInfo("This is test info")
        }

        btn_get.setOnClickListener {
            if (!mIsConnected) {
                tryToBindService()
                return@setOnClickListener
            }

            Log.e("TestAIDL", "Client try to get info: ${mAIDLTestM?.getInfo()}")
        }
    }

    private fun tryToBindService() {
        val intent = Intent(this, RemoteService::class.java)
        intent.action = "com.zksyp.studyrecord.AIDL"
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            mIsConnected = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mIsConnected = true
            mAIDLTestM = AIDLTestManager.Stub.asInterface(service)

        }
    }
}