package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlCar;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic.GenericActivity;

public class AddCarActivity extends GenericActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener{

    public static final int CODE_DESIGN_RESULT = 777;
    private android.widget.TextView tvTitleCar;
    private android.widget.EditText etPlateCar;
    private android.widget.Spinner spBrandCar;
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
    private int option;
    public static final int CODE_UPDATE = -25;
    public static final int CODE_CREATE = -335;
    private TextView tvColorWheels;
    private TextView tvColorHood;
    private TextView tvColorDoor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_car);
        this.tvColorDoor =  findViewById(R.id.tvColorDoor);
        this.tvColorHood = findViewById(R.id.tvColorHood);
        this.tvColorWheels = findViewById(R.id.tvColorWheels);
        this.cvBtnPersonalizeCar =  findViewById(R.id.cvBtnPersonalizeCar);
        this.textView = findViewById(R.id.textView);
        this.btnSave =  findViewById(R.id.btnSave);
        this.spListCars = findViewById(R.id.spListCars);
        this.tvNumDoorsCar =  findViewById(R.id.tvNumDoorsCar);
        this.etTypeCar =  findViewById(R.id.etTypeCar);
        this.etModelCar =  findViewById(R.id.etModelCar);
        this.spBrandCar =  findViewById(R.id.etBrandCar);
        this.etPlateCar =  findViewById(R.id.etPlateCar);
        this.tvTitleCar =  findViewById(R.id.tvTitleCar);
        this.cvBtnPersonalizeCar.setOnClickListener(this::onClick);
        this.btnSave.setOnClickListener(this::onClick);
        numDoors = 2;
        conexion = new Conexion(this);
        controlCar = new ControlCar(conexion);
        car = (CarVO) getIntent().getExtras().getSerializable("car");
        option = (int) getIntent().getExtras().getSerializable("option");
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
        int d = Integer.parseInt(car.getCar_color_doors() == null ? Color.WHITE + "" : car.getCar_color_doors());
        int w = Integer.parseInt(car.getCar_color_wheels() == null ? Color.WHITE + "" : car.getCar_color_wheels());
        int h = Integer.parseInt(car.getCar_color_hoods() == null ? Color.WHITE + "" : car.getCar_color_hoods());
        tvColorDoor.setTextColor(d);
        tvColorHood.setTextColor(h);
        tvColorWheels.setTextColor(w);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,doors);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spListCars.setAdapter(aa);

    }

    private void fillFields() {
        etPlateCar.setText(car.getCar_plate());

        etModelCar.setText(car.getCar_model());
        etTypeCar.setText(car.getCar_type());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_DESIGN_RESULT) {
            if(data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    int door = extras.getInt("doors");
                    int hood = extras.getInt("hoods");
                    int wheel = extras.getInt("wheels");

                    car.setCar_color_doors(String.valueOf(door));
                    car.setCar_color_hoods(String.valueOf(hood));
                    car.setCar_color_wheels(String.valueOf(wheel));
                    messageToast("Colores Guardados");
                    tvColorDoor.setTextColor(door);
                    tvColorHood.setTextColor(hood);
                    tvColorWheels.setTextColor(wheel);

                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnSave:
                saveUpdate(view);
                break;
            case R.id.cvBtnPersonalizeCar:
                int d = Integer.parseInt(car.getCar_color_doors() == null ? Color.WHITE + "" : car.getCar_color_doors());
                int w = Integer.parseInt(car.getCar_color_wheels() == null ? Color.WHITE + "" : car.getCar_color_wheels());
                int h = Integer.parseInt(car.getCar_color_hoods() == null ? Color.WHITE + "" : car.getCar_color_hoods());
                Intent pantallaDesign = new Intent(this,DesignCarActivity.class);
                pantallaDesign.putExtra("doors",d);
                pantallaDesign.putExtra("wheels",w);
                pantallaDesign.putExtra("hoods",h);
                startActivityForResult(pantallaDesign,CODE_DESIGN_RESULT);
                break;
        }

    }

    private void saveUpdate(View view) {
        try {
            if (option == CODE_UPDATE) {
                if(!fillCar()){
                    messageSnackBar("todos los campos son obligatorios",view);
                    return;
                }
                controlCar.update(car);
            } else if (option == CODE_CREATE) {
                if(!fillCar()){
                    messageSnackBar("todos los campos son obligatorios",view);
                    return;
                }
                controlCar.insert(car);
            }
            finish();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error APP",e.getMessage());
            //Agregar al log de errores
        }
    }

    private boolean fillCar() {
        if(car==null)
        car = new CarVO();
        if(etPlateCar.getText().toString().isEmpty() ||
        etModelCar.getText().toString().isEmpty() || etTypeCar.getText().toString().isEmpty())
            return false;
        if(spBrandCar.getSelectedItem().toString().isEmpty())
            return false;
        car.setCar_brand(spBrandCar.getSelectedItem().toString());
        car.setCar_doors(Integer.parseInt(spListCars.getSelectedItem().toString()));
        car.setCar_model(etModelCar.getText().toString());
        car.setCar_type(etTypeCar.getText().toString());
        car.setCar_plate(etPlateCar.getText().toString());
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        numDoors = Integer.parseInt(doors[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
