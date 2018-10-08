package dev.id.mcreator.multimedia_bag_i;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioRecordActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.btnPlay)
    Button btnPlay;
    @BindView(R.id.btnRecordStop)
    Button btnRecordStop;

    MediaRecorder mediaRecorder;
    String outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        ButterKnife.bind(this);

        btnPlay.setEnabled(false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        30);
            }
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        31);
            }
            return;
        }
    }

    @OnClick({R.id.btnPlay, R.id.btnRecordStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                aksiPlay();
                break;
            case R.id.btnRecordStop:
                aksiRecordAudio();
                break;
        }
    }

    public String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void aksiPlay() {
        MediaPlayer player = new MediaPlayer();
        try{
            Toast.makeText(this, "Play audio recorder latest action..", Toast.LENGTH_SHORT).show();
            player.setDataSource(outputFile);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void aksiRecordAudio() {
        if (btnRecordStop.getText().toString().equalsIgnoreCase("RECORD")) {
            try {
                mediaRecorder = new MediaRecorder();
                String folderRecordAudio = "audioKu";

                File file = new File(Environment.getExternalStorageDirectory(), folderRecordAudio);
                if (!file.exists()) {
                    file.mkdir();
                }

                outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/" + folderRecordAudio + "/REC" + currentDate() + ".3gp";
                Log.d("Directory Save File: ", " " + outputFile);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                mediaRecorder.setOutputFile(outputFile);
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Exeption: ", "" + e);
            }
            Toast.makeText(this, "Record started!", Toast.LENGTH_SHORT).show();
            mediaRecorder.start();
            btnRecordStop.setText("STOP");
        } else if (btnRecordStop.getText().toString()
                .equalsIgnoreCase("STOP")) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            btnPlay.setEnabled(true);
            Toast.makeText(this, "Record stoped!.. and files already saved in: " + outputFile, Toast.LENGTH_SHORT).show();
            btnRecordStop.setText("RECORD");
        }
    }
}
