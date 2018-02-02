package com.example.davegilbier.spotify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Dave Gilbier on 1/28/18.
 */
private val KEY="101"
private val PAUSE = "pausesong"
private val RESUME = "resumesong"

class SongFragment : Fragment(){
    private var mTitle: TextView? = null
    private var mArtist: TextView? = null
    private var mPause: ImageView? = null
    private var mResume: ImageView? = null

    companion object {

        fun newInstance(song: String, artist: String): SongFragment {

            val args = Bundle()
            args.putString(KEY, song)
            args.putString(KEY, artist)
            val fragment = SongFragment()
            fragment.arguments = args

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater?.inflate(R.layout.fragment_song, container, false)

        mTitle = rootView!!.findViewById(R.id.songTitle) as TextView
        mArtist = rootView!!.findViewById(R.id.songSinger) as TextView
        mPause = rootView!!.findViewById(R.id.pause_icon) as ImageView
        mResume = rootView!!.findViewById(R.id.play_icon) as ImageView
        val song = arguments.getString(KEY)
        val artist = arguments.getString(KEY)

        if(song != null && artist != null){
            mTitle!!.text = song
            mArtist!!.text = artist
        }
        mPause!!.setOnClickListener {
            mPause!!.visibility = View.INVISIBLE
            mResume!!.visibility = View.VISIBLE
            var songIntent = Intent(context, SongService::class.java).apply {
                action = PAUSE
            }
            context.startService(songIntent)
        }
        mResume!!.setOnClickListener {
            mPause!!.visibility = View.VISIBLE
            mResume!!.visibility = View.INVISIBLE
            var songIntent = Intent(context, SongService::class.java).apply {
                action = RESUME
            }
            context.startService(songIntent)
        }
        return rootView

    }


}