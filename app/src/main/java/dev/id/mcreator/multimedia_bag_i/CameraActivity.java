package dev.id.mcreator.multimedia_bag_i;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.btnTake)
    Button btnTake;
    @BindView(R.id.btnShow)
    Button btnShow;
    @BindView(R.id.ivShowPic)
    ImageView ivShowPic;

    //TODO 1: Add the URI library for External Storage
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        //TODO 2: Add 2 Permission standard Camera & External Storage Add check self permission untuk API Nougat Keatas atau 16 Keatas
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.CAMERA) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.CAMERA
                }, 10);
            }
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 11);
            }
        }
    }

    @OnClick({R.id.btnTake, R.id.btnShow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnTake:
                takeCamera();
                break;
            case R.id.btnShow:
                showPicture();
                break;
        }
    }

    private void takeCamera() {
        //TODO 3: Action Take Photo with Camera
        StrictMode.VmPolicy.Builder builder =
                new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String folderCamera = "photo";
        File file = new File(Environment.getExternalStorageDirectory(), folderCamera);

        if (!file.exists()) {
            file.mkdir();
        }

        File isiFile = new File(Environment.getExternalStorageDirectory()
        .getAbsolutePath() + "/" + folderCamera + "/VID" + ".jpg");

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        uri = Uri.fromFile(isiFile);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Gambar Tersimpan", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Dibatalkan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Gagal Mengambil Gambar", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == 7) {

            if (resultCode == RESULT_OK) {
                Uri locationImages = data.getData();
                InputStream stream = null;

                try {
                    stream = getContentResolver().openInputStream(locationImages);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                ivShowPic.setImageBitmap(bitmap);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Dibatalkan", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Gagal Menampilkan Gambar", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPicture() {
        Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galery, 7);
    }
}
