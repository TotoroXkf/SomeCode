package com.example.camera

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.PrintWriter


class MainActivity : AppCompatActivity() {
	private lateinit var mCameraFragment: Fragment
	private var mDeviceNumber = 0
	
	private val mCommandExecutor =
		{ command: String, writer: PrintWriter, closeCallback: () -> Unit ->
			Log.e("xkf123456789", "收到命令$command")
			val data = command.split("-")
			val type = data[1]
			val info = data[2]
			if (type == "parameter") {
				handlerParameter(info, writer, closeCallback)
			} else if (type == "action") {
				handlerAction(info, writer, closeCallback)
			}
		}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		runCommandSocket(mCommandExecutor)
		init()
	}
	
	private fun init() {
		window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) //隐藏状态栏
		mCameraFragment = CameraFragment()
		
		leanCloudInit(applicationContext)
		getServerIpAddress {
			sendDeviceIp(it)
		}
	}
	
	private fun sendDeviceIp(serverIp: String) {
		Thread {
			val deviceIp = getDeviceIp(this@MainActivity)
			mDeviceNumber = sendIpAndGetDeviceNumber(serverIp, deviceIp)
			showCamera()
		}.start()
	}
	
	private fun showCamera() {
		runOnUiThread {
			number.text = mDeviceNumber.toString()
			
			supportFragmentManager.beginTransaction().replace(R.id.replaceContent, mCameraFragment)
				.commit()
		}
		
	}
	
	private fun handlerAction(info: String, writer: PrintWriter, closeCallback: () -> Unit) {
	
	}
	
	private fun handlerParameter(info: String, writer: PrintWriter, closeCallback: () -> Unit) {
	
	}
}
