package dev.id.mcreator.multimedia_bag_i;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class smsServiceActivity extends AppCompatActivity {

    private static final int REQUESTSMS = 1;
    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.edt_msh_sms)
    EditText edtMshSms;
    @BindView(R.id.btn_sms_intent)
    Button btnSmsIntent;
    @BindView(R.id.btn_kirim_sms)
    Button btnKirimSms;
    
    String noHP, pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_service);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.edt_number, R.id.btn_sms_intent, R.id.btn_kirim_sms})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.edt_number:
                ambilNoTelp();
                break;
            case R.id.btn_sms_intent:
                kirimIntent();
                break;
            case R.id.btn_kirim_sms:
                kirimSMSLangsung();
                break;
        }
    }

    private void kirimSMSLangsung() {
        noHP = edtNumber.getText().toString().trim();
        pesan = edtMshSms.getText().toString().trim();
        String sample = edtNumber.getText().toString();
        System.out.print("PEsan: " + sample);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        REQUESTSMS);
            }
        }else {
            try {
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(noHP,null,pesan,null,null);
                Toast.makeText(this, "berhasil mengirim sms", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "gagal mengirim sms"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void kirimIntent() {

        noHP = edtNumber.getText().toString().trim();
        pesan = edtMshSms.getText().toString().trim();

        String msgError = "Tidak boleh kosong bro!";

        if (TextUtils.isEmpty(noHP)) {
            edtNumber.setError(msgError);
            edtNumber.requestFocus();
        } else if (TextUtils.isEmpty(pesan)) {
            edtMshSms.setError(msgError);
            edtMshSms.requestFocus();
        } else {
            Intent sms = new Intent(Intent.ACTION_VIEW);
            sms.setType("vnd.android-dir/mms-sms");
            sms.putExtra("address", noHP);
            sms.putExtra("sms_body", pesan);
            startActivity(sms);
        }
    }

    private void ambilNoTelp() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);

        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Cursor cursor = null;

            try {
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[] {
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                }, null, null, null);

                if (cursor != null && cursor.moveToNext()) {
                    String phone = cursor.getString(0);
                    edtNumber.setText(phone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
