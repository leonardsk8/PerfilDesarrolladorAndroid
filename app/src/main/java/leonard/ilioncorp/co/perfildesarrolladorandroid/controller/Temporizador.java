package leonard.ilioncorp.co.perfildesarrolladorandroid.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao.Synchronized;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity.SettingActivity;

public class Temporizador extends BroadcastReceiver implements Runnable {
    private ControlCar controlCar;
    private ControlPerson controlPerson;
    private ControlHistory controlHistory;
    private Synchronized sin;

    @Override
    public void onReceive(Context context, Intent intent) {
        sin = new Synchronized();
        Toast.makeText(context,"ENTRO",Toast.LENGTH_SHORT).show();
        Log.e("llego","alarma");
        Conexion con = new Conexion(context);
        controlCar = new ControlCar(con);
        controlPerson = new ControlPerson(con);
        controlHistory = new ControlHistory(con);

        new Thread(this).start();
//        SettingActivity settingActivity = new SettingActivity();
//        settingActivity.sincronizar();
    }

    @Override
    public void run() {
        sin.synchronizedTables(controlPerson.formatJson(),controlCar.formatJson(),
                controlHistory.formatJson());
    }
}
