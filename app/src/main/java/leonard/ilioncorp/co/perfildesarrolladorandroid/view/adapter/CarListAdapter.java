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
import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlCar;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.ItemClickListener;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;
import leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity.AddCarActivity;

import leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity.CarsActivity;


public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> implements
        View.OnClickListener,ItemClickListener{

     List<CarVO> carList;
    Activity activity;
    ControlCar controlCar;
    AlertDialog dialog;


    public CarListAdapter(List<CarVO> carList, CarsActivity cars, ControlCar controlCar) {

        this.carList = carList;
        this.activity = cars;
        this.controlCar = controlCar;

    }


    @Override
    public CarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_preview
                ,parent,false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CarListAdapter.ViewHolder holder, int position) {
        final CarVO car = carList.get(position);
        holder.tvBrandCarPreview.setText(car.getCar_brand());
        holder.tvDoorsCarPreview.setText(car.getCar_doors()+"");
        holder.tvModelCarPReview.setText(car.getCar_model());
        holder.tvTypeCarPreview.setText(car.getCar_type());
        holder.tvPlateCarPreview.setText(car.getCar_plate());
        holder.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    @Override
    public void onClick(View view) {

    }
    public void viewAlert(View v,int position) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = activity.getLayoutInflater().inflate(R.layout.item_dialog, null);
        Button edit = mView.findViewById(R.id.btnEdit);
        Button delete = mView.findViewById(R.id.btnDelete);
        edit.setOnClickListener(view->{
            Intent pantalla = new Intent(activity, AddCarActivity.class);
            CarVO car= carList.get(position);
            pantalla.putExtra("car",car);
            pantalla.putExtra("option",AddCarActivity.CODE_UPDATE);
            activity.startActivity(pantalla);
            dialog.cancel();
        });
        delete.setOnClickListener(view -> {
            try {
                controlCar.delete(carList.get(position));
                CarsActivity person = (CarsActivity) activity;
                person.messageToast("Carro eliminado");
                dialog.cancel();
                ((CarsActivity) activity).onResume();
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
    public void onItemClick(View v, int pos) {
        Log.e("Click","Click Sencillo desde car");
    }

    @Override
    public void onItemLongClick(View v, int pos)   {
        viewAlert(v,pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
    View.OnLongClickListener{

        private final View v;
        private android.widget.TextView tvPlateCarPreview;
        private android.widget.TextView tvModelCarPReview;
        private android.widget.TextView tvBrandCarPreview;
        private android.widget.TextView tvDoorsCarPreview;
        private android.widget.TextView tvTypeCarPreview;
        ItemClickListener itemClickListener;
        public ViewHolder(View v) {
            super(v);
            this.v = v;
            this.tvTypeCarPreview =  v.findViewById(R.id.tvTypeCarPreview);
            this.tvDoorsCarPreview =  v.findViewById(R.id.tvDoorsCarPreview);
            this.tvBrandCarPreview =  v.findViewById(R.id.tvBrandCarPreview);
            this.tvModelCarPReview =  v.findViewById(R.id.tvModelCarPReview);
            this.tvPlateCarPreview =  v.findViewById(R.id.tvPlateCarPreview);
            this.v.setOnClickListener(this::onClick);
            this.v.setOnLongClickListener(this::onLongClick);
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view,getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            this.itemClickListener.onItemLongClick(view,getLayoutPosition());
            return false;
        }
    }
}
