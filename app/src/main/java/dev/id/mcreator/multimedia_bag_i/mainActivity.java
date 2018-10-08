package dev.id.mcreator.multimedia_bag_i;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class mainActivity extends AppCompatActivity {

    @BindView(R.id.btnAlarm)
    Button btnAlarm;
    @BindView(R.id.btnBluetooth)
    Button btnBluetooth;
    @BindView(R.id.btnCallPhone)
    Button btnCallPhone;
    @BindView(R.id.btnTTS)
    Button btnTTS;
    @BindView(R.id.btnWIFI)
    Button btnWIFI;
    @BindView(R.id.btnSMS)
    Button btnSMS;
    @BindView(R.id.btnCamera)
    Button btnCamera;
    @BindView(R.id.btnEmail)
    Button btnEmail;
    @BindView(R.id.btnUrlBrowser)
    Button btnUrlBrowser;
    @BindView(R.id.btnAudioManager)
    Button btnAudioManager;
    @BindView(R.id.btnVidioRecorder)
    Button btnVidioRecorder;
    @BindView(R.id.btnAudioRecorder)
    Button btnAudioRecorder;
    @BindView(R.id.btnSTT)
    Button btnSTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSTT, R.id.btnAudioRecorder, R.id.btnVidioRecorder, R.id.btnAudioManager, R.id.btnUrlBrowser, R.id.btnEmail, R.id.btnCamera, R.id.btnAlarm, R.id.btnBluetooth, R.id.btnCallPhone, R.id.btnTTS, R.id.btnWIFI, R.id.btnSMS})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAlarm:
                startActivity(new Intent(mainActivity.this, AlarmActivity.class));
                break;
            case R.id.btnBluetooth:
                startActivity(new Intent(mainActivity.this, bluetoothActivity.class));
                break;
            case R.id.btnCallPhone:
                startActivity(new Intent(mainActivity.this, CallPhoneActivity.class));
                break;
            case R.id.btnTTS:
                startActivity(new Intent(mainActivity.this, TTSActivity.class));
                break;
            case R.id.btnWIFI:
                startActivity(new Intent(mainActivity.this, WiFiActivity.class));
                break;
            case R.id.btnSMS:
                startActivity(new Intent(mainActivity.this, smsServiceActivity.class));
                break;
            case R.id.btnCamera:
                startActivity(new Intent(mainActivity.this, CameraActivity.class));
                break;
            case R.id.btnEmail:
                startActivity(new Intent(mainActivity.this, EmailActivity.class));
                break;
            case R.id.btnUrlBrowser:
                startActivity(new Intent(mainActivity.this, IntenUrlBrowserActivity.class));
                break;
            case R.id.btnAudioManager:
                startActivity(new Intent(mainActivity.this, AudioManagerActivity.class));
                break;
            case R.id.btnVidioRecorder:
                startActivity(new Intent(mainActivity.this, VidioRecorderActivity.class));
                break;
            case R.id.btnAudioRecorder:
                startActivity(new Intent(mainActivity.this, AudioRecordActivity.class));
                break;
            case R.id.btnSTT:
                startActivity(new Intent(mainActivity.this, STTActivity.class));
                break;
        }
    }
}
