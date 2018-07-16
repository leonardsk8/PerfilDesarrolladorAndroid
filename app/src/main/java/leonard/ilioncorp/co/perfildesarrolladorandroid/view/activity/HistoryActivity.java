package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlCar;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlHistory;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.adapter.CarListAdapter;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic.GenericActivity;

public class HistoryActivity extends GenericActivity {


    private android.support.v7.widget.RecyclerView listRecyclerCarsHistory;
    private android.widget.TextView tvNoAddHistory;
    private CarListAdapter adapter;
    private ControlHistory controlHistory;
    private ControlCar controlCar;
    private List<CarVO> listCars;
    private PersonVO person;
    private TextView tvIdPersonPreview;
    private TextView tvNamePersonPreview;
    private TextView tvBirthPersonPreview;
    private TextView tvSurNamePersonPreview;
    private TextView tvProfessionPersonPreview;
    private TextView tvMarriedPersonPreview;
    private TextView tvSalaryPersonPreview;
    private TextView tvVehiclePersonPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
                
        //Item Person
        this.tvVehiclePersonPreview =  findViewById(R.id.tvVehiclePersonPreview);
        this.tvSalaryPersonPreview =  findViewById(R.id.tvSalaryPersonPreview);
        this.tvMarriedPersonPreview =  findViewById(R.id.tvMarriedPersonPreview);
        this.tvProfessionPersonPreview =  findViewById(R.id.tvProfessionPersonPreview);
        this.tvSurNamePersonPreview =  findViewById(R.id.tvSurNamePersonPreview);
        this.tvBirthPersonPreview =  findViewById(R.id.tvBirthPersonPreview);
        this.tvNamePersonPreview =  findViewById(R.id.tvNamePersonPreview);
        this.tvIdPersonPreview =  findViewById(R.id.tvIdPersonPreview);
        
        
        this.tvNoAddHistory = findViewById(R.id.tvNoAddHistory);
        this.listRecyclerCarsHistory = findViewById(R.id.listRecyclerCarsHistory);
        this.listRecyclerCarsHistory.setHasFixedSize(true);
        this.listRecyclerCarsHistory.setLayoutManager(new LinearLayoutManager(this));
        person = (PersonVO) getIntent().getExtras().getSerializable("person");

        //Fill Person
        
        tvVehiclePersonPreview.setText(person.getUser_vehicle());
        tvBirthPersonPreview.setText(person.getUser_birth());
        tvIdPersonPreview.setText(person.getUser_identification());
        tvMarriedPersonPreview.setText(person.isUser_married()?"Casado":"Soltero");
        tvNamePersonPreview.setText(person.getUser_name());
        tvSurNamePersonPreview.setText(person.getUser_surname());
        tvSalaryPersonPreview.setText(person.getUser_salary()+"");
        tvProfessionPersonPreview.setText(person.getUser_profession());

        Conexion con = new Conexion(this);
        controlCar = new ControlCar(con);
        controlHistory = new ControlHistory(con);
        

    }

    @Override
    public void onStart() {
        super.onStart();
        fillList();
    }

    @Override
    public void onResume() {
        super.onResume();
        fillList();
    }

    private void fillList() {
        try {
            listCars = controlCar.getCarsPerson(person.getUser_identification());
            if(listCars.isEmpty()) {
                tvNoAddHistory.setVisibility(View.VISIBLE);
                listRecyclerCarsHistory.setVisibility(View.INVISIBLE);
            }else{
                tvNoAddHistory.setVisibility(View.INVISIBLE);
                listRecyclerCarsHistory.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(listCars);
        adapter = new CarListAdapter(listCars,this,controlHistory,CarListAdapter.HISTORY,person.getUser_identification());
        listRecyclerCarsHistory.setAdapter(adapter);

    }


}
