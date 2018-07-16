package leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.CursorGenerico;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.GenericoDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.IConsultaGenerica;

public class CarDAO extends GenericoDAO<CarVO> implements IConsultaGenerica<CarVO> {



    public CarDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public CarVO registro(CursorGenerico cursor) {
        CarVO car = new CarVO();
        car.setCar_plate(cursor.getString("car_plate"));
        car.setCar_brand(cursor.getString("car_brand"));
        car.setCar_type(cursor.getString("car_type"));
        car.setCar_model(cursor.getString("car_model"));
        car.setCar_doors(cursor.getInt("car_doors"));
        car.setCar_color_wheels(cursor.getString("car_color_wheels"));
        car.setCar_color_hoods(cursor.getString("car_color_hoods"));
        car.setCar_color_doors(cursor.getString("car_color_doors"));
        return car;
    }

    @Override
    public List<CarVO> consultarTodos() {
        List<CarVO> list;
        String sql = "select * from car";
        list = consultar(sql,null,this);
        return list;
    }

    public List<CarVO> consultarCarrosPersona(String personId) {
        List<CarVO> list;
        String sql = "select c.* from history as h,car as c where user_user_identification='"+personId+"' and c.car_plate=h.car_car_plate";
        list = consultar(sql,null,this);
        return list;
    }

    public int insert(CarVO car) {
        try {
            ContentValues values = new ContentValues();
            values.put("car_plate", car.getCar_plate());
            values.put("car_brand", car.getCar_brand());
            values.put("car_model", car.getCar_model());
            values.put("car_doors", car.getCar_doors());
            values.put("car_type", car.getCar_type());
            values.put("car_color_wheels", car.getCar_color_wheels());
            values.put("car_color_hoods",car.getCar_color_hoods());
            values.put("car_color_doors", car.getCar_color_doors());
            return (int) db.insert("car", null, values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int update(CarVO car) {
        try {
            ContentValues values = new ContentValues();
            values.put("car_brand", car.getCar_brand());
            values.put("car_model", car.getCar_model());
            values.put("car_doors", car.getCar_doors());
            values.put("car_color_wheels", car.getCar_color_wheels());
            values.put("car_color_hoods",car.getCar_color_hoods());
            values.put("car_color_doors", car.getCar_color_doors());
            return db.update("car", values,"car_plate=?",new String[]{car.getCar_plate()});
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int delete(CarVO car){
        try {
            String[] parameters = new String[]{String.valueOf(car.getCar_plate())};
            return db.delete("car", "car_plate=?", parameters);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public String synchronizedI() {
        List<CarVO> list = consultarTodos();
        if(list.size()==0){
            return "[]";
        }
        return new Gson().toJson(list);
    }
}
