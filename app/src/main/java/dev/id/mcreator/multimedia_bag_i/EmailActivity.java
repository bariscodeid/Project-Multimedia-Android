package dev.id.mcreator.multimedia_bag_i;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.edtto)
    EditText edtto;
    @BindView(R.id.edtsubject)
    EditText edtsubject;
    @BindView(R.id.edtmessage)
    EditText edtmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String to = edtto.getText().toString();
        String message =edtmessage.getText().toString();
        String subject =edtsubject.getText().toString();

        if (id==R.id.mn_send) {
            if (TextUtils.isEmpty(to)) {
                edtto.setError("Email tidak boleh kosong!");
                edtto.requestFocus();
            } else if (TextUtils.isEmpty(message)) {
                edtmessage.setError("Pesan tidak boleh kosong!");
                edtmessage.requestFocus();
            } else if (TextUtils.isEmpty(subject)) {
                edtsubject.setError("Subject tidak boleh kosong!");
                edtsubject.requestFocus();
            } else {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,
                        "Chose Email Client"));
            }
        } else {
            edtto.setText("");
            edtmessage.setText("");
            edtsubject.setText("");
        }
        return super.onOptionsItemSelected(item);
    }
}
