package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlPerson;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans.ConsErrors;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.adapter.PersonListAdapter;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.generic.GenericActivity;

public class PersonsActivity extends GenericActivity implements View.OnClickListener
        {

    private android.support.v7.widget.RecyclerView listRecyclerPersons;
    private android.widget.TextView tvNoAdd;
    private android.widget.ImageView btnAddPerson;
    private ControlPerson controlPerson;
    private Conexion con;
    private List<PersonVO> personList;
    private PersonListAdapter adapter;

    public static View positionAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);
        this.tvNoAdd =  findViewById(R.id.tvNoAdd);
        this.btnAddPerson = findViewById(R.id.btnAddPerson);
        this.listRecyclerPersons = findViewById(R.id.listRecyclerPersons);
        this.btnAddPerson.setOnClickListener(this);
        this.listRecyclerPersons.setHasFixedSize(true);
        this.listRecyclerPersons.setLayoutManager(new LinearLayoutManager(this));
        showCharging("Cargando");
        con = new Conexion(this);
        controlPerson = new ControlPerson(con);
//        fillList();
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
            personList = controlPerson.read();
            if(personList.isEmpty()) {
                tvNoAdd.setVisibility(View.VISIBLE);
                listRecyclerPersons.setVisibility(View.INVISIBLE);
            }else{
                tvNoAdd.setVisibility(View.INVISIBLE);
                listRecyclerPersons.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new PersonListAdapter(personList,this,controlPerson);

        listRecyclerPersons.setAdapter(adapter);
        hideCharging();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){

            case R.id.btnAddPerson:
                Intent pantallaAdd = new Intent(this,AddPersonActivity.class);
                pantallaAdd.putExtra("person",(Serializable) null);
                startActivity(pantallaAdd);
                break;
            case PersonListAdapter.ID_CHILD_ADAPTER:
                Log.e("ID","identification: "+id);
                break;


        }
    }

}
