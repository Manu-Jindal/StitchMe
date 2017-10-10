package com.manujindal.stichme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class LaunchVideo extends AppCompatActivity {

    VideoView launchvid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_video);

        getSupportActionBar().hide();

        launchvid = (VideoView) findViewById(R.id.launchvideo);

        String videopath = "android.resource://com.manujindal.stichme/"+R.raw.launchvideo;
        Uri uri = Uri.parse(videopath);
        launchvid.setVideoURI(uri);
        launchvid.start();

        launchvid.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
