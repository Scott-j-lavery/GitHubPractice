package services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.MediaController;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.util.PlayerControl;

/**
 * Created by scott on 9/17/15.
 */
public class MediaStreamService extends Service {

    public MediaStreamBinder mBinder;
    private ExoPlayer exoPlayer;
    private MediaController.MediaPlayerControl control;

    private class MediaStreamBinder extends Binder {

        private MediaStreamService service;

        public MediaStreamBinder(MediaStreamService service) {
            this.service = service;
        }

        public MediaStreamService getMediaStreamService() {
            return service;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new MediaStreamBinder(this);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        setUpMediaPlayer();
    }

    private void setUpMediaPlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Factory.newInstance(1);
            control = new PlayerControl(exoPlayer);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
