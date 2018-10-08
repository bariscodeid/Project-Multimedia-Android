package dev.id.mcreator.multimedia_bag_i;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class bluetoothActivity extends AppCompatActivity {

    @BindView(R.id.btn_turn_on)
    Button btnTurnOn;
    @BindView(R.id.btn_list)
    Button btnList;
    @BindView(R.id.btn_find)
    Button btnFind;
    @BindView(R.id.btn_visible)
    Button btnVisible;
    @BindView(R.id.btn_turn_off)
    Button btnTurnOff;
    @BindView(R.id.list_device)
    ListView listDevice;
    @BindView(R.id.list_find)
    ListView listFind;

    //TODO BLUETOOTH 1: Buat adapter bluetooth & aktivasinya untuk mengecek ketersediaan service
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> bluetoothDeviceSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        //TODO BLUETOOTH 2: Ketersediaan Bluetooth di cek oleh sistem android
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth tidak tersedia!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btn_turn_on, R.id.btn_list, R.id.btn_find, R.id.btn_visible, R.id.btn_turn_off})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_turn_on:
                turnOn();
                break;
            case R.id.btn_list:
                listBluetooth();
                break;
            case R.id.btn_find:
                //Find Belum Dicari Dulu
                findBluetooth();
                break;
            case R.id.btn_visible:
                visibleBluetooth();
                break;
            case R.id.btn_turn_off:
                turnOff();
                break;
        }
    }

    private void turnOff() {
        //TODO BLUETOOTH 4: Mematikan service Bluetooth, jika merah ketika ditambahkan method dibawah Alt + Enter tambahkan Permission Bluetooth Admin
        bluetoothAdapter.disable();
        //TODO BLUETOOTH 5: Berikan informasi dengan fungsi negasi dari aktif service
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth sudah dinon-aktifkan...",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void visibleBluetooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(intent, 2);
    }

    private void findBluetooth() {
    }

    private void listBluetooth() {
        bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
        ArrayList arrayList = new ArrayList();

        if (bluetoothDeviceSet.size() > 0) {
            for (BluetoothDevice device: bluetoothDeviceSet) {
                arrayList.add(device.getName() + "/n" + device.getAddress());
                Toast.makeText(this, "Device yang terhung tersedia...",
                        Toast.LENGTH_SHORT).show();
                ArrayAdapter adapter = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, arrayList);
                listDevice.setAdapter(adapter);
            }
        } else if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth belum aktif, silahkan aktifkan dulu", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Belum tersedia bluetooth yang terhubung...", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO BLUETOOTH 3: Hidupkan Bluetooth
    private void turnOn() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent hidupkanBluetooth =
                    new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(hidupkanBluetooth, 1);
            Toast.makeText(this, "Bluetooth diaktifkan...",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bluetooth udah aktif bro...", Toast.LENGTH_SHORT).show();
        }
    }
}
