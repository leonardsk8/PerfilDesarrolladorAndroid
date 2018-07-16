package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlCar;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlHistory;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlPerson;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.HistoryVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans.ConsErrors;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic.GenericActivity;

public class AddPersonActivity extends GenericActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

  
    private android.widget.EditText etIdentificationP;
    private android.widget.Spinner spSelectCar;
    private android.widget.EditText etNameP;
    private android.widget.TextView tvMessageNoCar;
    private android.widget.TextView tvTitle;
    private android.widget.EditText etSurNameP;
    
    private android.widget.EditText etBirthP;
    
    private android.widget.EditText etPrefessionP;
    
    private android.widget.RadioButton rbTrue;
    private android.widget.RadioButton rbFalse;
    private PersonVO person;
    private ControlPerson controlPerson;
    private ControlHistory controlHistory;
    private android.widget.EditText etSalaryP;
    private android.widget.Button btnSave;
    private ArrayList<String> cars;
    private String state;
    private ControlCar controlCar;
    private String carSelected;
    private String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_person);
        this.btnSave = findViewById(R.id.btnSave);
        this.etSalaryP =  findViewById(R.id.etSalaryP);
        this.tvMessageNoCar = findViewById(R.id.tvMessageNoCar);
        this.tvTitle = findViewById(R.id.tvTitle);
        this.rbFalse =  findViewById(R.id.rbFalse);
        this.rbTrue =  findViewById(R.id.rbTrue);
        this.etPrefessionP =  findViewById(R.id.etPrefessionP);
        this.etBirthP =  findViewById(R.id.etBirthP);
        this.etSurNameP =  findViewById(R.id.etSurNameP);
        this.etNameP =  findViewById(R.id.etNameP);
        this.spSelectCar = findViewById(R.id.spListCars);
        this.etIdentificationP =  findViewById(R.id.etIdentificationP);
        this.btnSave.setOnClickListener(this);
        this.spSelectCar.setOnItemSelectedListener(this);
        carSelected = "";

        Conexion conexion = new Conexion(this);
        controlPerson = new ControlPerson(conexion);
        controlHistory = new ControlHistory(conexion);
        controlCar = new ControlCar(conexion);
        person = (PersonVO) getIntent().getExtras().getSerializable("person");
        option = (String) getIntent().getExtras().getSerializable("option");
        cars = getCarsDB();
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cars);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSelectCar.setAdapter(aa);
        tvTitle.setText("Nuevo Usuario");
        if(person != null){
            fillFields();
            tvTitle.setText("Editar Usuario");
            this.etIdentificationP.setEnabled(false);
        }

        
    }

    private void fillFields() {
        etSalaryP.setText(String.valueOf(person.getUser_salary()));
        etBirthP.setText(person.getUser_birth());
        if (person.isUser_married()) {
            rbTrue.setChecked(true);
        } else {
            rbFalse.setChecked(true);
        }
        etPrefessionP.setText(person.getUser_profession());
        etIdentificationP.setText(person.getUser_identification());
        etNameP.setText(person.getUser_name());
        etSurNameP.setText(person.getUser_surname());
    }

    @Override
    public void onClick(View view) {
        try {
            if (option.contains("update")) {
                if(person.getUser_vehicle() != null)
                if(!person.getUser_vehicle().isEmpty()) {
                    HistoryVO history = new HistoryVO();
                    history.setUser_user_identification(person.getUser_identification());
                    history.setCar_car_plate(person.getUser_vehicle().split(" - ")[0]);
                    history.setUser_car_actual(false);
                    controlHistory.update(history);
                }
                if(!fillPerson()) {
                    messageSnackBar("los campos con * son obligatorios",view);
                    return;
                }
                controlPerson.update(person);

            } else {
                if(!fillPerson()){
                    messageSnackBar("los campos con * son obligatorios",view);
                    return;
                }
                controlPerson.insert(person);
            }
            carSelected = spSelectCar.getSelectedItem().toString();
            if(!carSelected.isEmpty() & !carSelected.contains("sin vehiculo")) {
                HistoryVO history = new HistoryVO();
                history.setUser_user_identification(person.getUser_identification());
                history.setCar_car_plate(carSelected.split(" - ")[0]);
                history.setUser_car_actual(true);
                controlHistory.insert(history);
            }

            finish();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error APP",e.getMessage());
            //Agregar al log de errores
        }
    }

    private boolean fillPerson() {
        if(person == null)
        person = new PersonVO();
        if(!etIdentificationP.getText().toString().isEmpty())
            person.setUser_identification(etIdentificationP.getText().toString());
        else
            return false;
        if(!etNameP.getText().toString().isEmpty())
            person.setUser_name(etNameP.getText().toString());
        else
            return false;
        if(!etSalaryP.getText().toString().isEmpty())
            person.setUser_salary(Double.parseDouble(etSalaryP.getText().toString()));
        else
            person.setUser_salary(0);

            person.setUser_married(rbTrue.isChecked()?true:false);
        if(!etSurNameP.getText().toString().isEmpty())
            person.setUser_surname(etSurNameP.getText().toString());
        else
            return false;
        if(!etBirthP.getText().toString().isEmpty())
            person.setUser_birth(etBirthP.getText().toString());
        else
            person.setUser_birth("0-00-0000");
        if(!etPrefessionP.getText().toString().isEmpty())
            person.setUser_profession(etPrefessionP.getText().toString());
        else
            person.setUser_profession("Sin profesión");
        if(!spSelectCar.getSelectedItem().toString().isEmpty())
            person.setUser_vehicle(spSelectCar.getSelectedItem().toString());

        return true;
    }

    public ArrayList<String> getCarsDB() {
        List<CarVO> lisCars;
        ArrayList<String> carsDB = new ArrayList<>();
        try {
            lisCars = controlCar.read();
            if(lisCars.isEmpty())
                tvMessageNoCar.setVisibility(View.VISIBLE);
            else
                tvMessageNoCar.setVisibility(View.INVISIBLE);
            carsDB.add("");
            carsDB.add("sin vehiculo");
            for (int i = 0 ; i<lisCars.size() ; i++) {
                CarVO car = lisCars.get(i);
                carsDB.add(car.getCar_plate() + " - " + car.getCar_brand() + " - "+car
                .getCar_model());
            }
        } catch (AppExceptions appExceptions) {
            appExceptions.printStackTrace();
            carsDB = new ArrayList<>();
        }
        return carsDB;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();
        String[] i_t_e_m = item.split(" - ");
        this.carSelected = i_t_e_m[0];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
