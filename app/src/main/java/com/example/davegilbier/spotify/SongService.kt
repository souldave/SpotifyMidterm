package com.example.davegilbier.spotify

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

/**
 * Created by Dave Gilbier on 1/28/18.
 */
class SongService : Service() {
    private val SONGPLAY = "songplay"
    private val PAUSE = "pausesong"
    private val PLAY = "resumesong"
    var currentPosition:Int = 0
    var songDataList:ArrayList<String> = ArrayList()
    var media: MediaPlayer?=null
    var position = 0
    override fun onBind(p0: Intent?): IBinder ? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent!!.action == SONGPLAY) {
            currentPosition = intent!!.getIntExtra(SongAdapter.SONGPOS, 0)
            songDataList = intent.getStringArrayListExtra(SongAdapter.SONGLIST)
            if (media != null) {
                media!!.stop()
                media!!.release()
                media = null
            }

            media = MediaPlayer()
            media!!.setDataSource(songDataList[currentPosition])
            media!!.prepare()
            media!!.start()
        }
        if (intent!!.action == PAUSE) {

            position = media!!.currentPosition
            media!!.pause()
        }
        if (intent!!.action == PLAY){
            media!!.seekTo (position)
            media!!.start()
        }

        return super.onStartCommand(intent, flags, startId)
    }



}