package com.richpathanimator.sample

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.pathanimator.sample.kt.R
import com.richpath.RichPathView
import com.richpathanimator.AnimationListener
import com.richpathanimator.RichPathAnimator

class MainActivity : AppCompatActivity() {

    private var richPathAnimator: RichPathAnimator? = null

    // Declare the RichPathView as a class property since it is used in multiple methods
    private lateinit var icAndroidRichPathView: RichPathView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the views
        icAndroidRichPathView = findViewById(R.id.icAndroidRichPathView)
        val animationSamplesButton = findViewById<View>(R.id.animationSamplesButton)
        val compoundViewSamplesButton = findViewById<View>(R.id.compoundViewSamplesButton)

        icAndroidRichPathView.setOnClickListener { animateAndroid() }
        animationSamplesButton.setOnClickListener { openAnimationSamples() }
        compoundViewSamplesButton.setOnClickListener { openCompoundViewSamples() }
    }

    override fun onResume() {
        super.onResume()
        animateAndroid()
    }

    private fun animateAndroid() {
        // Ensure the view is initialized before using it
        // Note: findAllRichPaths might return an array, spread operator (*) works on it
        val allPaths = icAndroidRichPathView.findAllRichPaths()
        val head = icAndroidRichPathView.findRichPathByName("head")!!
        val body = icAndroidRichPathView.findRichPathByName("body")!!
        val rHand = icAndroidRichPathView.findRichPathByName("r_hand")!!
        val lHand = icAndroidRichPathView.findRichPathByName("l_hand")!!

        richPathAnimator = RichPathAnimator.animate(*allPaths)
            .trimPathEnd(0f, 1f)
            .duration(800)
            .animationListener(object : AnimationListener {
                override fun onStart() {
                    head.fillColor = Color.TRANSPARENT
                    body.fillColor = Color.TRANSPARENT
                    rHand.fillColor = Color.TRANSPARENT
                    lHand.fillColor = Color.TRANSPARENT
                    rHand.rotation = 0f
                }

                override fun onStop() {}
            })
            .thenAnimate(*allPaths)
            .fillColor(Color.TRANSPARENT, -0x5b39c7)
            .interpolator(AccelerateInterpolator())
            .duration(900)
            .thenAnimate(rHand)
            .rotation(-150f)
            .duration(700)
            .thenAnimate(rHand)
            .rotation(-150f, -130f, -150f, -130f, -150f, -130f, -150f)
            .duration(2000)
            .thenAnimate(rHand)
            .rotation(0f)
            .duration(500)
            .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        richPathAnimator?.cancel()
    }

    private fun openAnimationSamples() {
        startActivity(Intent(this, AnimationSamplesActivity::class.java))
    }

    private fun openCompoundViewSamples() {
        startActivity(Intent(this, CompoundViewSamplesActivity::class.java))
    }
}