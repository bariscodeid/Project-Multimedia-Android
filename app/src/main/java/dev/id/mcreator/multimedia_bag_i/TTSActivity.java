package dev.id.mcreator.multimedia_bag_i;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO 2: Implemented to Method android.speech.tts.TextToSpeech.OnInitListener
//TODO 3: If Error Underline you must be Alt + Enter in red Underline and Choose the Implement Method
public class TTSActivity extends AppCompatActivity implements
android.speech.tts.TextToSpeech.OnInitListener {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btnSpeech)
    Button btnSpeech;

    //TODO 1: Add the libray old version 25 or 26 stable
    android.speech.tts.TextToSpeech tts;
    //TODO 5: Set Variable Global for Tempt
    String getInputan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        ButterKnife.bind(this);

        tts = new android.speech.tts.TextToSpeech(this, this);
    }

    @OnClick(R.id.btnSpeech)
    public void onViewClicked() {
        getInputan = editText.getText().toString().trim();
        tts.speak(getInputan, TextToSpeech.QUEUE_FLUSH, null);
    }

    //TODO 4: Change variable in type integer with status
    @Override
    public void onInit(int status) {
        if (status == android.speech.tts.TextToSpeech.SUCCESS) {
            int hasil = tts.setLanguage(Locale.ENGLISH);

            if (hasil == TextToSpeech.LANG_MISSING_DATA ||
                    hasil == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Bahasa gak support bro...",
                        Toast.LENGTH_SHORT).show();
            } else {
                onViewClicked();
                btnSpeech.setEnabled(true);
            }
        }
    }
}
