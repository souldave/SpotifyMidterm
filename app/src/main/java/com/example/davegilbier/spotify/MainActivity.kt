package com.example.davegilbier.spotify.Controller

import android.annotation.SuppressLint

import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import com.example.davegilbier.spotify.SongAdapter
import com.example.davegilbier.spotify.Songs
import com.example.davegilbier.spotify.R
import kotlinx.android.synthetic.main.activity_main.*
import android.database.Cursor
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mNestedScrollView: NestedScrollView? = null
    private var songList = ArrayList<Songs>()
    private var REQUEST = 111


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        collapse()
        findView()

        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST)
        } else {
            loadMusic()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }


    }


    private fun collapse() {
        val collapsingToolbar = findViewById<View>(R.id.id1) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbarlayout) as AppBarLayout
        appBarLayout.setExpanded(true)

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    collapsingToolbar.setCollapsedTitleTextColor(Color.TRANSPARENT)
                    isShow = false
                }
            }
        })
    }

    private fun findView() {
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mNestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
    }

    private fun loadMusic() {
        var songCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null)
        while (songCursor != null && songCursor.moveToNext()) {
            var songTitle = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songSinger = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            var songAlbum = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            var songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            songList.add(Songs(
                    songTitle, songSinger, songAlbum, songPath))
        }

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = SongAdapter(songList, applicationContext, this)
        mRecyclerView!!.adapter = adapter
        mRecyclerView!!.addItemDecoration(DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL))
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadMusic()
            }
        }
    }
}

