package leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {


    public Conexion(Context context) {
        super(context, "carsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder user = new StringBuilder();
        StringBuilder car = new StringBuilder();
        StringBuilder history = new StringBuilder();
        user.append("CREATE TABLE user_db (")
                .append("user_identification TEXT PRIMARY KEY,")
                .append("user_name TEXT NOT NULL,")
                .append("user_surname TEXT NOT NULL,")
                .append("user_birth TEXT,")
                .append("user_profession TEXT,")
                .append("user_married TEXT,")
                .append("user_salary TEXT,")
                .append("user_vehicle TEXT")
                .append(");");
        car.append("create table car(")
                .append("car_plate text primary key,")
                .append("car_brand text,")
                .append("car_model text,")
                .append("car_type text,")
                .append("car_doors integer,")
                .append("car_color_wheels text,")
                .append("car_color_hoods text,")
                .append("car_color_doors text")
                .append(");");
        history.append("create table history (")
                .append("user_user_identification text primary key,")
                .append("car_car_plate text,")
                .append("user_car_actual text")
                .append(");");
        db.execSQL(user.toString());
        db.execSQL(car.toString());
        db.execSQL(history.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public SQLiteDatabase open(){
        return getWritableDatabase();
    }
}
