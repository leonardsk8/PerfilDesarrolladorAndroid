package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlCar;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlHistory;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlPerson;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.Temporizador;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao.Synchronized;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic.GenericActivity;

public class SettingActivity extends GenericActivity implements View.OnClickListener,Runnable,Handler.Callback{

    private android.widget.ImageView btnSynchronization;
    private android.widget.TimePicker timePickerTask;
    private android.widget.TextView textView;
    private android.support.v7.widget.CardView cvBtnProgrammingTask;
    private ControlPerson controlPerson;
    private ControlHistory controlHistory;
    private ControlCar controlCar;
    private Handler bridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.cvBtnProgrammingTask =  findViewById(R.id.cvBtnProgrammingTask);
        this.textView =  findViewById(R.id.textView);
        this.timePickerTask = findViewById(R.id.timePickerTask);
        this.btnSynchronization = findViewById(R.id.btnSynchronization);
        Conexion con = new Conexion(this);
        controlPerson = new ControlPerson(con);
        controlCar = new ControlCar(con);
        controlHistory = new ControlHistory(con);
        bridge = new Handler(this);
        this.btnSynchronization.setOnClickListener(this::onClick);
        this.cvBtnProgrammingTask.setOnClickListener(this::onClick);
    }

    @Override
    public boolean handleMessage(Message message) {
        hideCharging();
        messageToast(message.obj+"");
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSynchronization:
                showCharging("Sincronizando");
                if(!isOnlineNet()){
                    messageSnackBar("Sin internet",view);
                    hideCharging();
                    return;
                }

                new Thread(this).start();
             break;
            case R.id.cvBtnProgrammingTask:
                createAlarm();
                messageToast("Sincronización programada");
                break;
        }
    }

    private void createAlarm() {
        int hour = timePickerTask.getCurrentHour();
        int min = timePickerTask.getCurrentMinute();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,0);
        startAlarm(c);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Temporizador.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }


    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com.co");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        sincronizar();
    }

    public void sincronizar() {

        String jsonPerson = controlPerson.formatJson();
        String jsonCar = controlCar.formatJson();
        String jsonHistory = controlHistory.formatJson();

        Synchronized sincro = new Synchronized();
        sincro.synchronizedTables(jsonPerson,jsonCar,jsonHistory,bridge);
    }
}
