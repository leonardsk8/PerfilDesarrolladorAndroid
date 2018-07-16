package leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic;


import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;


public abstract class GenericActivity  extends AppCompatActivity {

    private AlertDialog alert;

    public void showCharging(String title){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        View item = LayoutInflater.from(this).inflate(R.layout.item_cargando,null);
        alert.setView(item);
        alert.setCancelable(false);
        this.alert=alert.create();
        this.alert = alert.show();
    }
    public void hideCharging(){
        if (alert == null){
            return;
        }
        alert.dismiss();

    }
    public void messageToast (String message){
        Toast.makeText(this,message + "" , Toast.LENGTH_SHORT).show();
    }
    public void messageSnackBar(String s, View view) {
        Snackbar.make(view,s,Snackbar.LENGTH_SHORT).show();
    }



}
