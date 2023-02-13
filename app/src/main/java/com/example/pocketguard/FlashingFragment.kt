package com.example.pocketguard

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView

class FlashingFragment : Fragment() {

    private lateinit var flashingImage: ImageView
    private var player: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flashing, container, false)

        flashingImage = view.findViewById(R.id.flashingImage)
        startLight()
        startSiren()

        return view
    }

    private fun startSiren() {
        if(player?.isPlaying == true){
            player?.stop()
        }
        player = MediaPlayer.create(activity, R.raw.police_siren)
        player?.isLooping = true
        player?.start()
    }

    private fun startLight() {
        val anim = ObjectAnimator.ofInt(flashingImage, "BackgroundColor", Color.RED, Color.BLUE)
        with(anim){
            duration = 120
            setEvaluator(ArgbEvaluator())
            repeatMode = ValueAnimator.REVERSE
            repeatCount= Animation.INFINITE
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.stop()
        player?.release()
    }
}