package leonard.ilioncorp.co.perfildesarrolladorandroid.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlPerson;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.ItemClickListener;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity.AddPersonActivity;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity.PersonsActivity;


public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder>
        implements View.OnClickListener,ItemClickListener
{
    public static final int ID_CHILD_ADAPTER = 45142;


    private AlertDialog dialog;
    List<PersonVO> personList;
    Activity activity;
    private ControlPerson controlPerson;
   


    public PersonListAdapter(List<PersonVO> personList, Activity activity, ControlPerson con) {
        this.personList = personList;
        this.activity = activity;
        this.controlPerson = con;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person_preview,parent,false);
        
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonListAdapter.ViewHolder holder, int position) {
        final PersonVO person = personList.get(position);
        holder.tvIdPersonPreview.setText(person.getUser_identification());
        holder.tvNamePersonPreview.setText(person.getUser_name());
        holder.tvSurNamePersonPreview.setText(person.getUser_surname());
        holder.tvBirthPersonPreview.setText(person.getUser_birth());
        holder.tvProfessionPersonPreview.setText(person.getUser_profession());
        holder.tvSalaryPersonPreview.setText(String.valueOf(person.getUser_salary()));
        holder.tvVehiclePersonPreview.setText(person.getUser_vehicle());
        holder.tvMarriedPersonPreview.setText(person.isUser_married()?"Casado":"Soltero");
        if(person.getUser_vehicle()==null || person.getUser_vehicle().isEmpty())
            holder.tvVehiclePersonPreview.setText("Sin vehiculo");
        holder.setItemClickListener(this);


    }
    public void viewAlert(View v,int position) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = activity.getLayoutInflater().inflate(R.layout.item_dialog, null);
        Button edit = mView.findViewById(R.id.btnEdit);
        Button delete = mView.findViewById(R.id.btnDelete);
        PersonsActivity.positionAlertDialog = v;
        edit.setOnClickListener(view->{
            Intent pantalla = new Intent(activity,AddPersonActivity.class);
            PersonVO person= personList.get(position);
            pantalla.putExtra("person",person);
            activity.startActivity(pantalla);
            dialog.cancel();
        });
        delete.setOnClickListener(view -> {
            try {
                controlPerson.delete(personList.get(position));
                PersonsActivity person = (PersonsActivity) activity;
                person.messageToast("Usuario eliminado");
                dialog.cancel();
                ((PersonsActivity) activity).onResume();

            } catch (AppExceptions appExceptions) {
                appExceptions.printStackTrace();
                Log.e("Error APP",appExceptions.getErrorApp().getMensaje());
            }
        });
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(View v, int pos) {
        Log.e("Click","Click Sencillo");
    }

    @Override
    public void onItemLongClick(View v, int pos) {
        viewAlert(v,pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    ,View.OnLongClickListener{
        private android.widget.TextView tvIdPersonPreview;
        private android.widget.TextView tvNamePersonPreview;
        private android.widget.TextView tvBirthPersonPreview;
        private android.widget.TextView tvSurNamePersonPreview;
        private android.widget.TextView tvProfessionPersonPreview;
        private android.widget.TextView tvMarriedPersonPreview;
        private android.widget.TextView tvSalaryPersonPreview;
        private android.widget.TextView tvVehiclePersonPreview;
        public View v;
        ItemClickListener itemClickListener;
        public ViewHolder(View v)  {
            super(v);
            this.v = v;
            this.tvVehiclePersonPreview =  v.findViewById(R.id.tvVehiclePersonPreview);
            this.tvSalaryPersonPreview =  v.findViewById(R.id.tvSalaryPersonPreview);
            this.tvMarriedPersonPreview =  v.findViewById(R.id.tvMarriedPersonPreview);
            this.tvProfessionPersonPreview =  v.findViewById(R.id.tvProfessionPersonPreview);
            this.tvSurNamePersonPreview =  v.findViewById(R.id.tvSurNamePersonPreview);
            this.tvBirthPersonPreview =  v.findViewById(R.id.tvBirthPersonPreview);
            this.tvNamePersonPreview =  v.findViewById(R.id.tvModelCarPReview);
            this.tvIdPersonPreview =  v.findViewById(R.id.tvIdPersonPreview);
            this.v.setOnClickListener(this::onClick);
            this.v.setOnLongClickListener(this::onLongClick);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }

        @Override
        public boolean onLongClick(View view) {
            this.itemClickListener.onItemLongClick(view,getLayoutPosition());
            return false;
        }
    }
}
