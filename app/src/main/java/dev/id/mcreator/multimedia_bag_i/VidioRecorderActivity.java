package dev.id.mcreator.multimedia_bag_i;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VidioRecorderActivity extends AppCompatActivity {

    @BindView(R.id.btnvideo)
    Button btnvideo;

    Uri lokasifile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidio_recorder);
        ButterKnife.bind(this);

        permissionCameraNStorage();

    }

    private void permissionCameraNStorage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        10);
            }
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        107);
            }
            return;
        }
    }

    @OnClick(R.id.btnvideo)
    public void onViewClicked() {
        takeVidio();
    }

    private void takeVidio() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String foldercamera = "videoKu";
        File file = new File(Environment.getExternalStorageDirectory(), foldercamera);
        if (!file.exists()) {
            file.mkdir();
        }

        File isifile = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + foldercamera + "/VID" + currentDate() + ".mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        lokasifile = Uri.fromFile(isifile);
        Log.d("Info", " Lokasi Penyimpanan di : "  + lokasifile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lokasifile);
        startActivityForResult(intent, 1);

    }

    public String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Berhasil menyimpan video \n lokasi" + lokasifile.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Proses di cancel!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sorry!, Gagal mengambil gambar, Try again!..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
