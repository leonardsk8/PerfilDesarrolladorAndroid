package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlCar;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.adapter.CarListAdapter;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic.GenericActivity;

public class CarsActivity extends GenericActivity implements View.OnClickListener {

    private android.widget.ImageView btnAddCar;
    private android.support.v7.widget.RecyclerView listRecyclerCar;
    private android.widget.TextView tvNoAdd;
    private List<CarVO> carList;
    private ControlCar controlCar;

    private CarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        this.tvNoAdd = findViewById(R.id.tvNoAddCars);
        this.listRecyclerCar= findViewById(R.id.listRecyclerCars);
        this.btnAddCar = findViewById(R.id.btnAddCar);
        this.listRecyclerCar.setHasFixedSize(true);
        this.listRecyclerCar.setLayoutManager(new LinearLayoutManager(this));
        Conexion con = new Conexion(this);
        controlCar = new ControlCar(con);
        btnAddCar.setOnClickListener(this::onClick);
    }
    private void fillList() {
        try {
            carList = controlCar.read();
            if(carList.isEmpty()) {
                tvNoAdd.setVisibility(View.VISIBLE);
                listRecyclerCar.setVisibility(View.INVISIBLE);
            }else{
                tvNoAdd.setVisibility(View.INVISIBLE);
                listRecyclerCar.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new CarListAdapter(carList,this,controlCar,CarListAdapter.LIST_CARS);
        listRecyclerCar.setAdapter(adapter);
        hideCharging();
    }

    @Override
    public void onClick(View view) {
        Intent pantallaAdd = new Intent(this,AddCarActivity.class);
        pantallaAdd.putExtra("car",(Serializable) null);
        pantallaAdd.putExtra("option",AddCarActivity.CODE_CREATE);
        startActivity(pantallaAdd);
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
}
