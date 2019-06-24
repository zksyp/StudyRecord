package com.zksyp.studyrecord.aidl

import android.os.*
import com.zksyp.studyrecord.aidl.AIDLTestManager.Stub.Companion.DESCRIPTOR

/**
 * Created with Android Studio.
 * User: zhaokai
 * Date: 2019-06-24
 * Time: 11:24
 * Desc: AIDL 远程服务的功能接口
 */
interface AIDLTestManager : IInterface {

    @Throws(RemoteException::class)
    fun getInfo(): String?

    @Throws(RemoteException::class)
    fun setInfo(info: String)

    abstract class Stub : Binder(), AIDLTestManager {


        init {
            attachInterface(this@Stub, DESCRIPTOR)
        }

        companion object {
            const val DESCRIPTOR = "com.example.yiguo.testproject.aidl.com.zksyp.studyrecord.aidl.AIDLTestManager"
            const val TRANSAVTION_getInfo = IBinder.FIRST_CALL_TRANSACTION
            const val TRANSAVTION_setInfo = IBinder.FIRST_CALL_TRANSACTION + 1


            fun asInterface(binder: IBinder?): AIDLTestManager? {
                if (binder == null) {
                    return null
                }
                val iin = binder.queryLocalInterface(DESCRIPTOR)
                if (iin != null && iin is AIDLTestManager) {
                    return iin
                }
                return Proxy(binder)
            }
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {

            when (code) {
                IBinder.INTERFACE_TRANSACTION -> {
                    reply?.writeString(DESCRIPTOR)
                    return true
                }
                TRANSAVTION_getInfo -> {
                    //校验功能接口是否相同
                    data.enforceInterface(DESCRIPTOR)
                    val info = getInfo()
                    reply?.writeNoException()
                    reply?.writeString(info)
                    return true

                }
                TRANSAVTION_setInfo -> {
                    data.enforceInterface(DESCRIPTOR)
                    val info = if (data.readInt() != 0)
                        data.readString()
                    else ""
                    setInfo(info)
                    reply?.writeNoException()
                    return true
                }
                else -> {
                    return super.onTransact(code, data, reply, flags)
                }
            }
        }
    }

    class Proxy(var remote: IBinder) : AIDLTestManager {


        override fun getInfo(): String? {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()
            val result: String?

            try {
                data.writeInterfaceToken(DESCRIPTOR)
                remote.transact(Stub.TRANSAVTION_getInfo, data, reply, 0)
                reply.readException()
                result = reply.readString()
            } finally {
                data.recycle()
                reply.recycle()
            }

            return result
        }

        override fun setInfo(info: String) {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()

            try {
                data.writeInterfaceToken(DESCRIPTOR)
                data.writeInt(1)
                data.writeString(info)
                remote.transact(Stub.TRANSAVTION_setInfo, data, reply, 0)
                reply.readException()
            } finally {
                data.recycle()
                reply.recycle()
            }
        }


        fun getServerDes(): String {
            return Stub.DESCRIPTOR
        }

        override fun asBinder(): IBinder {
            return remote
        }
    }
}