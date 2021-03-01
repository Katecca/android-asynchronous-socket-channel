package com.katecca.ascexample.service

import android.content.Context
import android.util.Log
import com.katecca.ascexample.R
import com.katecca.ascexample.viewmodel.ConnectBtnViewModel
import com.katecca.ascexample.viewmodel.ResultPanelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.nio.charset.Charset

class SocketChannelConnectService(context: Context) {

    companion object {
        private const val TAG = "CONN_INFO"
    }

    private lateinit var sockChannel:AsynchronousSocketChannel
    private val context = context

    suspend fun connect(
        host: String,
        port: Int,
        resultPanelViewModel: ResultPanelViewModel,
        connectBtnViewModel: ConnectBtnViewModel
    ) {
        sockChannel = AsynchronousSocketChannel.open()
        connectBtnViewModel.onNameChanged(context.getString(R.string.btn_connecting))
        withContext(Dispatchers.IO) {
            try {
                sockChannel.connect(
                    InetSocketAddress(host, port),
                    sockChannel,
                    object : CompletionHandler<Void?, AsynchronousSocketChannel?> {
                        override fun completed(result: Void?, channel: AsynchronousSocketChannel?) {
                            connectBtnViewModel.onNameChanged("Connected")
                            connectBtnViewModel.onEnabledChanged(false)
                            startRead(resultPanelViewModel)
                        }

                        override fun failed(exc: Throwable?, channel: AsynchronousSocketChannel?) {
                            Log.e(TAG, "CONNECT ERROR", exc)
                        }
                    })
            } catch (e: Exception) {
                connectBtnViewModel.onNameChanged("Connect")
                connectBtnViewModel.onEnabledChanged(true)
                Log.e(TAG, "CONNECT ERROR", e)
            }
        }
    }

    suspend fun disconnect(connectBtnViewModel: ConnectBtnViewModel) {
        withContext(Dispatchers.IO) {
            try {
                sockChannel.close()
                connectBtnViewModel.onNameChanged(context.getString(R.string.btn_connect))
                connectBtnViewModel.onEnabledChanged(true)
            }catch (e: Exception){
                Log.e(TAG, "CONNECT ERROR", e)
            }
        }
    }

    fun startRead(resultPanelViewModel: ResultPanelViewModel) {
        val buf = ByteBuffer.allocate(2048)
        sockChannel.read(
            buf,
            sockChannel,
            object : CompletionHandler<Int?, AsynchronousSocketChannel?> {
                override fun completed(result: Int?, channel: AsynchronousSocketChannel?) {
                    //print the message
                    var readResult = String(trim(buf.array()), Charset.forName("UTF-8"))
                    resultPanelViewModel.onResultChanged(readResult)
                    startRead(resultPanelViewModel)

                }

                override fun failed(exc: Throwable?, channel: AsynchronousSocketChannel?) {
                    Log.i(TAG, "Disconnect / Read failed")
                }
            })
    }

    suspend fun startWrite(message: String) {
        withContext(Dispatchers.IO) {
            val buf = ByteBuffer.wrap(message.toByteArray())
            sockChannel.write(
                buf,
                sockChannel,
                object : CompletionHandler<Int?, AsynchronousSocketChannel?> {
                    override fun completed(result: Int?, channel: AsynchronousSocketChannel?) {
                    }

                    override fun failed(exc: Throwable?, channel: AsynchronousSocketChannel?) {
                        Log.e(TAG,"WRITE ERROR")
                    }
                })
        }
    }

    /*
    Remove NULL from byte array
    NULL = 0 in ASCII code
    */
    fun trim(bytes: ByteArray): ByteArray {
        var i = bytes.size - 1
        while (i >= 0 && bytes[i].toInt() == 0) {
            --i
        }
        return bytes.copyOf(i + 1)
    }
}