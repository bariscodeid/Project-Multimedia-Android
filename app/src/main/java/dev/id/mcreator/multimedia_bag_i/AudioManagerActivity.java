package dev.id.mcreator.multimedia_bag_i;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioManagerActivity extends AppCompatActivity {

    @BindView(R.id.ring)
    Button ring;
    @BindView(R.id.vibrate)
    Button vibrate;
    @BindView(R.id.silent)
    Button silent;

    AudioManager manager;

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manager);
        ButterKnife.bind(this);

        manager = (AudioManager) getSystemService(AUDIO_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},
                        10);
            }
            return;
        }
    }

    @OnClick({R.id.ring, R.id.vibrate, R.id.silent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ring:
                setRing();
                break;
            case R.id.vibrate:
                setVibrate();
                break;
            case R.id.silent:
                setSilent();
                break;
        }
    }

    private void setRing() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
            Toast.makeText(this, "Mode Normal is Active!", Toast.LENGTH_SHORT).show();
        } else {
            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    private void setVibrate() {
        manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        Toast.makeText(this, "Mode Vibrate is Active!", Toast.LENGTH_SHORT).show();
    }

    private void setSilent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
            manager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
            Toast.makeText(this, "Mode Silent is Active", Toast.LENGTH_SHORT).show();
        } else {
            manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
}
