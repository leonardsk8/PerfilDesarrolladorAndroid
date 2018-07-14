package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic.GenericActivity;

public class MainActivity extends GenericActivity implements View.OnClickListener{

    private android.widget.ImageView btnPerson;
    private android.widget.ImageView btnCars;
    private android.widget.ImageView btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnSettings = findViewById(R.id.btnSettings);
        this.btnCars = findViewById(R.id.btnCars);
        this.btnPerson = findViewById(R.id.btnPerson);
        this.btnSettings.setOnClickListener(this);
        this.btnCars.setOnClickListener(this);
        this.btnPerson.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnPerson:
                startActivity(new Intent(this,PersonsActivity.class));
                break;
            case R.id.btnCars:
                startActivity(new Intent(this,CarsActivity.class));
                break;
            case R.id.btnSettings:
                startActivity(new Intent(this,SettingActivity.class));
                break;
        }
    }
}
