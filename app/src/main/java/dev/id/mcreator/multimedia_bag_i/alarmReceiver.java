package dev.id.mcreator.multimedia_bag_i;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

//TODO 6: Extend ke Broadcast Receiver, Jika merah Alt + Enter pilih Implement Method yang onReceive
public class alarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO 7: Tambahkan player untuk memutar sound
        MediaPlayer player = MediaPlayer.create(context, R.raw.analog);
        player.start();
    }
}
