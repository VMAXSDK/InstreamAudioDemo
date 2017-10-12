package com.vmax.demo.instreamvideo;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.vmax.android.ads.api.VmaxAdView;
import com.vmax.android.ads.common.VmaxAdListener;
import com.vmax.android.ads.exception.VmaxAdError;


import java.io.IOException;


/** Its Recommended To Use VMAX plugin For Android Studio To Add Your Dependencies
 and Manage Changes in AndroidManifest as Well as Proguard,
 However You Can Manually Do This By Referring To Our Documentation Or following this Demo Project  */


public class MainActivity extends AppCompatActivity {


    public  VmaxAdView vmaxAdView;
    private FrameLayout parent_layout;
    RelativeLayout audio_1,audio_2,audio_3,audio_4,audio_5;
    Boolean audioPlaying=false;
    MediaPlayer mediaPlayer=new MediaPlayer();
    ImageView btn_play_pause;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /** Initialize App UI */
        IntiUi();



    }


    /** Method for Loading Instream Video */
    public void loadInstreamAudio()
    {


        /** Initializing vmaxAdView with an Adspot, Repalce With the adspot Configured by you */
        vmaxAdView = new VmaxAdView(this,"de117ea7",VmaxAdView.UX_INSTREAM_AUDIO);


        /** To Fetch Your AdvId you can check your device's Google settings under ads subMenu Or You can Run this app Once and check
         * the logs for 'AdRequested with url' under the tag vmax, from the url your Advid
         * would be one of the parameters in the post request eg. advid=2cf626f0-08ac-4a4d-933c-00ecd0256cf4*/

/** DON'T INCLUDE vmaxAdView.setTestDevices() WHILE GOING LIVE WITH YOUR PROJECT AS THIS SERVES ONLY TEST ADS;*/



        vmaxAdView.setAdListener(new VmaxAdListener() {
            @Override public void onAdError(VmaxAdError error) {
                Toast.makeText(getApplicationContext(),
                        "Failed To Load Ad Please Try Again Later",Toast.LENGTH_LONG).show();
                play("Tangerine.mp3");

            }

            @Override
            public void onAdClose() {
                
            }

            @Override
            public void onAdMediaEnd(boolean b, long l) {
                
            }

            @Override public void onAdReady(VmaxAdView adView) {

            }

            @Override
            public void onAdSkippable() {

            }

            @Override
            public void onAdMediaStart() {
                super.onAdMediaStart();
            }
        });
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.e("vmax","Sysytem volume"+currentVolume);
        vmaxAdView.showAd();
    }



    private void play( String file) {
        try {
            audioPlaying=true;
            btn_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.pause));
            mediaPlayer.reset();
            AssetFileDescriptor afd = getAssets().openFd(file);
            mediaPlayer.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength()
            );
            afd.close();
            mediaPlayer.prepare();
            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
            Log.e("vmax","System volume"+currentVolume);
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    audioPlaying=false;
                    btn_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.play_button));
                    loadInstreamAudio();

                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void IntiUi()
    {

        btn_play_pause=(ImageView)findViewById(R.id.btn_play_pause);

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(audioPlaying)
                {
                   mediaPlayer.stop();
                    btn_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.play_button));
                    audioPlaying=false;
                }
                else
                {
                    play("Track_1.mp3");
                    btn_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                }
            }
        });

        audio_1=(RelativeLayout)findViewById(R.id.audio_1);
        audio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                play("Track_1.mp3");
            }
        });

        audio_2=(RelativeLayout)findViewById(R.id.audio_2);
        audio_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                play("Track_2.mp3");
            }
        });

        audio_3=(RelativeLayout)findViewById(R.id.audio_3);
        audio_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play("Track_3.mp3");
            }
        });

        audio_4=(RelativeLayout)findViewById(R.id.audio_4);
        audio_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play("Track_4.mp3");
            }
        });


        audio_5=(RelativeLayout)findViewById(R.id.audio_5);
        audio_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play("Track_5.mp3");
            }
        });
    }
   
    /** Handle vmaxAdView object for Activity Lifecycle changes */

    @Override
    protected void onDestroy() {
        if (vmaxAdView != null) {
       /** To Destroy vmaxAdView when Activity Is No Longer Available  */
            vmaxAdView.onDestroy();
        }
        super.onDestroy();
    }

}
