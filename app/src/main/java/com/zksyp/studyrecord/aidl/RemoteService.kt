package com.zksyp.studyrecord.aidl
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Created with Android Studio.
 * User: zhaokai
 * Date: 2019-06-24
 * Time: 14:54
 * Desc:
 */
class RemoteService : Service() {


    private var mInfo: String = "TestAIDL"

    override fun onBind(intent: Intent?): IBinder? {
        return mAIDLTestM
    }

    private var mAIDLTestM = object : AIDLTestManager.Stub() {
        override fun getInfo(): String? {
            return mInfo
        }

        override fun setInfo(info: String) {
            mInfo = info
            Log.e("TestAIDL", "GetInfo: $mInfo")
        }

        override fun asBinder(): IBinder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}