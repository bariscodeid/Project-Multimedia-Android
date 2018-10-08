package dev.id.mcreator.multimedia_bag_i;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class STTActivity extends AppCompatActivity {

    @BindView(R.id.etInputText)
    EditText etInputText;
    @BindView(R.id.btnSpeak)
    Button btnSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSpeak)
    public void onViewClicked() {
        //TODO 5.1 munculin form untuk ambil suara
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //TODO 5.2 set language
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //ambil default settingan device
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"HEI");
        //TODO 5.3 send request
        startActivityForResult(i,23);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO 5.4 get result
        ArrayList<String> datas =  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        //TODO 5.5 set ke textview
        etInputText.setText(datas.get(0));
    }
}
