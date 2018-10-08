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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallPhoneActivity extends AppCompatActivity {

    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.btn_tampil_call)
    Button btnTampilCall;
    @BindView(R.id.btnlistcontact)
    Button btnlistcontact;
    @BindView(R.id.edtnumber)
    EditText edtnumber;

    String NomorTelpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_call, R.id.btn_tampil_call, R.id.btnlistcontact})
    public void onViewClicked(View view) {
        NomorTelpon = edtnumber.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_call:
                call();
                break;
            case R.id.btn_tampil_call:
                sentNumbertoPhone();
                break;
            case R.id.btnlistcontact:
                showListContact();
                break;
        }
    }

    private void call() {
        if (TextUtils.isEmpty(NomorTelpon)) {
            edtnumber.setError("Must be fill the EditText!");
            edtnumber.requestFocus();
        } else {
            int checkPermission = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE);

            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this, new String[]{
                                Manifest.permission.CALL_PHONE}, 2);

            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + NomorTelpon)));
            }
        }
    }

    private void sentNumbertoPhone() {

        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + NomorTelpon)));
    }

    private void showListContact() {
        Intent listContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        listContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(listContact, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Cursor cursor = null;
            try {
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[] {
                        ContactsContract.CommonDataKinds.Phone.NUMBER},
                        null, null, null);

                if (cursor != null && cursor.moveToNext()) {
                    String phone = cursor.getString(0);
                    edtnumber.setText(phone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
