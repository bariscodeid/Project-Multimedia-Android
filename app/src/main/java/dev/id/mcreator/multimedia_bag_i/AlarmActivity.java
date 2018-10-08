package dev.id.mcreator.multimedia_bag_i;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.ac_clock)
    AnalogClock acClock;
    @BindView(R.id.btn_set_alarm)
    Button btnSetAlarm;
    @BindView(R.id.tv_show_alarm)
    TextView tvShowAlarm;

    //TODO 1: Set Library untuk time picker
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_set_alarm)
    public void onViewClicked() {

        //TODO 2: Bikin method seperti dibawah Alt+Enter
        openTimerDialog(false);
    }

    //TODO 3: Buat keadaan untuk setting waktu
    private void openTimerDialog(boolean b) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(
                this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    //TODO 4: Set Time Listener
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            Calendar callSet = (Calendar)calendar.clone();
            callSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            callSet.set(Calendar.MINUTE, minute);
            callSet.set(Calendar.SECOND, 0);
            callSet.set(Calendar.MILLISECOND, 0);

            if (callSet.compareTo(calendar) <= 0) {
                calendar.add(Calendar.DATE, 1);
            } else if (callSet.compareTo(calendar) > 0) {
                Log.i("Error: ", "> 0");
            }
            setAlarm(callSet);
        }
    };

    //TODO 5: Baca settingan alarm
    @SuppressLint("SetTextI18n")
    private void setAlarm(Calendar c) {
        tvShowAlarm.setText("***\n "
                + "Alarm Set On"
                + c.getTime()
                + "\n***");

        Intent intent = new Intent(getBaseContext(), alarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }
}
