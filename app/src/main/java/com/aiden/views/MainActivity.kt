package com.aiden.views

import android.os.Bundle
import android.os.Handler
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.IntegerRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.aiden.views.morphingbuttonlibrary.MorphingButton
import com.aiden.views.morphingbuttonlibrary.impl.IndeterminateProgressButton
import com.aiden.views.morphingbuttonlibrary.impl.LinearProgressButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        processLogic()
    }

    var mMorphCounter1:Int = 1
    var mMorphCounter2:Int = 1
    var mMorphCounter3:Int = 1
    var mMorphCounter4:Int = 1

    private fun processLogic() {
        val m = findViewById<MorphingButton>(R.id.mpb1)
        Log.e("tag: ",m.toString())
        //1
        m.setOnClickListener {
            onMorphButton1Clicked(it as MorphingButton)
        }
        morphToSquare(mpb1, 0)

        //2
        mpb2.setOnClickListener {
            onMorphButton2Clicked(it as MorphingButton)
        }
        morphToFailure2(mpb2, integer(R.integer.mb_animation))
        //3
        ipb.setOnClickListener {
            onMorphButton4Clicked(it as IndeterminateProgressButton)
        }
        morphToSquare4(ipb, 0)
        //4
        lpb.setOnClickListener {
            onMorphButton3Clicked(it as LinearProgressButton)
        }
        morphToSquare3(lpb, 0)
    }

    private fun onMorphButton4Clicked(btnMorph: IndeterminateProgressButton) {
        if (mMorphCounter4 == 0) {
            mMorphCounter4++
            morphToSquare4(btnMorph, integer(R.integer.mb_animation))
        } else if (mMorphCounter1 == 1) {
            mMorphCounter4 = 0
            simulateProgress4(btnMorph)
        }
    }

    private fun morphToSquare4(btnMorph: IndeterminateProgressButton, duration: Int) {
        val square = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
            .width(dimen(R.dimen.mb_width_100))
            .height(dimen(R.dimen.mb_height_56))
            .color(color(R.color.mb_blue))
            .colorPressed(color(R.color.mb_blue_dark))
            .text(getString(R.string.mb_button))
        btnMorph.morph(square)
    }

    private fun simulateProgress4(button: IndeterminateProgressButton) {
        val progressColor1 = color(R.color.holo_blue_bright)
        val progressColor2 = color(R.color.holo_green_light)
        val progressColor3 = color(R.color.holo_orange_light)
        val progressColor4 = color(R.color.holo_red_light)
        val color = color(R.color.mb_gray)
        val progressCornerRadius = dimen(R.dimen.mb_corner_radius_4)
        val width = dimen(R.dimen.mb_width_200)
        val height = dimen(R.dimen.mb_height_8)
        val duration = integer(R.integer.mb_animation)

        val handler = Handler()
        handler.postDelayed({
            morphToSuccess(button)
            button.unblockTouch()
        }, 4000)

        button.blockTouch() // prevent user from clicking while button is in progress
        button.morphToProgress(
            color, progressCornerRadius, width, height, duration, progressColor1, progressColor2,
            progressColor3, progressColor4
        )
    }

    private fun onMorphButton3Clicked(btnMorph: LinearProgressButton) {
        if (mMorphCounter3 == 0) {
            mMorphCounter3++
            morphToSquare3(btnMorph, integer(R.integer.mb_animation))
        } else if (mMorphCounter3 == 1) {
            mMorphCounter3 = 0
            simulateProgress3(btnMorph)
        }
    }

    private fun morphToSquare3(btnMorph: MorphingButton, duration: Int) {
        val square = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
            .width(dimen(R.dimen.mb_width_100))
            .height(dimen(R.dimen.mb_height_56))
            .color(color(R.color.mb_blue))
            .colorPressed(color(R.color.mb_blue_dark))
            .text(getString(R.string.mb_button))
        btnMorph.morph(square)
    }

    private fun simulateProgress3(button: LinearProgressButton) {
        val progressColor = color(R.color.mb_purple)
        val color = color(R.color.mb_gray)
        val progressCornerRadius = dimen(R.dimen.mb_corner_radius_4)
        val width = dimen(R.dimen.mb_width_200)
        val height = dimen(R.dimen.mb_height_8)
        val duration = integer(R.integer.mb_animation)

        val generator = ProgressGenerator(object : ProgressGenerator.OnCompleteListener {
            override fun onComplete() {
                morphToSuccess(button)
                button.unblockTouch()
            }
        })
        button.blockTouch() // prevent user from clicking while button is in progress
        button.morphToProgress(color, progressColor, progressCornerRadius, width, height, duration)
        generator.start(button)
    }
    private fun onMorphButton1Clicked(btnMorph: MorphingButton) {
        if (mMorphCounter1 == 0) {
            mMorphCounter1++
            morphToSquare(btnMorph, integer(R.integer.mb_animation))
        } else if (mMorphCounter1 == 1) {
            mMorphCounter1 = 0
            morphToSuccess(btnMorph)
        }
    }

    private fun morphToSquare(btnMorph: MorphingButton, duration: Int) {
        val square = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
            .width(dimen(R.dimen.mb_width_200))
            .height(dimen(R.dimen.mb_height_56))
            .color(color(R.color.mb_blue))
            .colorPressed(color(R.color.mb_blue_dark))
            .text(getString(R.string.mb_button))
        btnMorph.morph(square)
    }

    private fun morphToSuccess(btnMorph: MorphingButton) {
        val circle = MorphingButton.Params.create()
            .duration(integer(R.integer.mb_animation))
            .cornerRadius(dimen(R.dimen.mb_height_56))
            .width(dimen(R.dimen.mb_height_56))
            .height(dimen(R.dimen.mb_height_56))
            .color(color(R.color.mb_green))
            .colorPressed(color(R.color.mb_green_dark))
            .icon(R.drawable.ic_done)
        btnMorph.morph(circle)
    }

    private fun onMorphButton2Clicked(btnMorph: MorphingButton) {
        if (mMorphCounter2 == 0) {
            mMorphCounter2++
            morphToFailure2(btnMorph, integer(R.integer.mb_animation))
        } else if (mMorphCounter2 == 1) {
            mMorphCounter2 = 0
            morphToSquare2(btnMorph, integer(R.integer.mb_animation))
        }
    }

    private fun morphToSquare2(btnMorph: MorphingButton, duration: Int) {
        val square = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
            .width(dimen(R.dimen.mb_width_200))
            .height(dimen(R.dimen.mb_height_56))
            .color(color(R.color.mb_blue))
            .colorPressed(color(R.color.mb_blue_dark))
            .text(getString(R.string.mb_button))
        btnMorph.morph(square)
    }

    private fun morphToFailure2(btnMorph: MorphingButton, duration: Int) {
        val circle = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(R.dimen.mb_height_56))
            .width(dimen(R.dimen.mb_height_56))
            .height(dimen(R.dimen.mb_height_56))
            .color(color(R.color.mb_red))
            .colorPressed(color(R.color.mb_red_dark))
            .icon(R.drawable.ic_lock)
        btnMorph.morph(circle)
    }










    private fun dimen(@DimenRes resId:Int):Int{
        return resources.getDimension(resId).toInt()
    }

    fun color(@ColorRes resId: Int): Int {
        return resources.getColor(resId)
    }

    fun integer(@IntegerRes resId: Int): Int {
        return resources.getInteger(resId)
    }


}
