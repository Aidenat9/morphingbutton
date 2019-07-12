package com.aiden.views

import android.os.Handler
import com.aiden.views.morphingbuttonlibrary.IProgress
import java.util.*

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/7/12 17:16
 * package：com.aiden.views
 * version：1.0
 * <p>description：              </p>
 */
class ProgressGenerator(private val mListener: OnCompleteListener) {
    private var mProgress: Int = 0

    private val random = Random()

    interface OnCompleteListener {

        fun onComplete()
    }

    @JvmOverloads
    fun start(button: IProgress, duration: Int = 500) {
        mProgress = 0
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                mProgress += 5
                button.setProgress(mProgress)
                if (mProgress < 100) {
                    handler.postDelayed(this, generateDelay().toLong())
                } else {
                    mListener.onComplete()
                }
            }
        }, duration.toLong())
    }

    private fun generateDelay(): Int {
        return random.nextInt(100)
    }
}