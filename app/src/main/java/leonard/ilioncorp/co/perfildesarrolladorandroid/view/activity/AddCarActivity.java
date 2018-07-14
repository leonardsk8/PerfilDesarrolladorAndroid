package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlCar;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;

public class AddCarActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener{

    public static final int CODE_DESIGN_RESULT = 777;
    private android.widget.TextView tvTitleCar;
    private android.widget.EditText etPlateCar;
    private android.widget.EditText etBrandCar;
    private android.widget.EditText etModelCar;
    private android.widget.EditText etTypeCar;
    private android.widget.TextView tvNumDoorsCar;
    private android.widget.Spinner spListCars;
    private android.widget.Button btnSave;
    private ControlCar controlCar;

    private Conexion conexion;
    private CarVO car;
    private int numDoors;
    private String[] doors;
    private TextView textView;
    private android.support.v7.widget.CardView cvBtnPersonalizeCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_car);
        this.cvBtnPersonalizeCar =  findViewById(R.id.cvBtnPersonalizeCar);
        this.textView = findViewById(R.id.textView);
        this.btnSave =  findViewById(R.id.btnSave);
        this.spListCars = findViewById(R.id.spListCars);
        this.tvNumDoorsCar =  findViewById(R.id.tvNumDoorsCar);
        this.etTypeCar =  findViewById(R.id.etTypeCar);
        this.etModelCar =  findViewById(R.id.etModelCar);
        this.etBrandCar =  findViewById(R.id.etBrandCar);
        this.etPlateCar =  findViewById(R.id.etPlateCar);
        this.tvTitleCar =  findViewById(R.id.tvTitleCar);
        this.cvBtnPersonalizeCar.setOnClickListener(this::onClick);
        this.btnSave.setOnClickListener(this::onClick);
        numDoors = 2;
        conexion = new Conexion(this);
        controlCar = new ControlCar(conexion);
        car = (CarVO) getIntent().getExtras().getSerializable("car");

        tvTitleCar.setText("Nuevo Carro");
        if(car != null){
            fillFields();
            tvTitleCar.setText("Editar Carro");
            this.etPlateCar.setEnabled(false);
            doors = new String[]{String.valueOf(car.getCar_doors()),"2","3","4","5","6","7","8"};
        }else {
            doors = new String[]{"2", "3", "4", "5", "6", "7", "8"};
            car = new CarVO();
        }
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,doors);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spListCars.setAdapter(aa);
    }

    private void fillFields() {
        etPlateCar.setText(car.getCar_plate());
        etBrandCar.setText(car.getCar_brand());
        etModelCar.setText(car.getCar_model());
        etTypeCar.setText(car.getCar_type());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_DESIGN_RESULT) {
            Bundle extras = data.getExtras();
            if (extras != null)
                extras.getInt("position");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnSave:
                saveUpdate();
                break;
            case R.id.cvBtnPersonalizeCar:
                int d = Integer.parseInt(car.getCar_color_doors()==null?DesignCarActivity.WITHOUT_COLOR+"":car.getCar_color_doors());
                int w = Integer.parseInt(car.getCar_color_wheels()==null?DesignCarActivity.WITHOUT_COLOR+"":car.getCar_color_wheels());
                int h = Integer.parseInt(car.getCar_color_hoods()==null?DesignCarActivity.WITHOUT_COLOR+"":car.getCar_color_hoods());
                Intent pantallaDesign = new Intent(this,DesignCarActivity.class);
                pantallaDesign.putExtra("doors",d);
                pantallaDesign.putExtra("wheels",w);
                pantallaDesign.putExtra("hoods",h);
                startActivityForResult(pantallaDesign,CODE_DESIGN_RESULT);
                break;
        }

    }

    private void saveUpdate() {
        try {
            if (car != null) {
                fillCar();
                controlCar.update(car);
            } else {
                fillCar();
                controlCar.insert(car);
            }
            finish();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error APP",e.getMessage());
            //Agregar al log de errores
        }
    }

    private void fillCar() {
        car = new CarVO();
        car.setCar_brand(etBrandCar.getText().toString());
        car.setCar_doors(numDoors);
        car.setCar_model(etModelCar.getText().toString());
        car.setCar_type(etTypeCar.getText().toString());
        car.setCar_plate(etPlateCar.getText().toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        numDoors = Integer.parseInt(doors[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
