package leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.CursorGenerico;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.GenericoDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.IConsultaGenerica;

public class PersonDAO extends GenericoDAO implements IConsultaGenerica<PersonVO> {



    public PersonDAO(SQLiteDatabase db) {
        super(db);

    }

    @Override
    public List<PersonVO> consultarTodos(){
        List<PersonVO> list;
        String sql = "select * from user_db";
        list = consultar(sql,null,this);
        return list;
    }

    public int delete(PersonVO person){
        try {
            String[] parameters = new String[]{String.valueOf(person.getUser_identification())};
            return db.delete("user_db", "user_identification=?", parameters);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    public int insert(PersonVO person){
        try {
            ContentValues values = new ContentValues();
            values.put("user_identification", person.getUser_identification());
            values.put("user_name", person.getUser_name());
            values.put("user_surname", person.getUser_surname());
            values.put("user_birth", person.getUser_birth());
            values.put("user_profession", person.getUser_profession());
            values.put("user_vehicle", person.getUser_vehicle());
            values.put("user_salary", person.getUser_salary());
            values.put("user_married", person.isUser_married()+"");
            return (int) db.insert("user_db", null, values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int update(PersonVO person) {
        try {
            ContentValues values = new ContentValues();
            values.put("user_name", person.getUser_name());
            values.put("user_surname", person.getUser_surname());
            values.put("user_birth", person.getUser_birth());
            values.put("user_profession", person.getUser_profession());
            values.put("user_vehicle", person.getUser_vehicle());
            values.put("user_salary", person.getUser_salary());
            values.put("user_married", person.isUser_married()+"");
            return db.update("user_db", values, "user_identification=?", new String[]{person.getUser_identification()});
        }catch (Exception e){
            e.printStackTrace();
        }

        return -1;
    }
    @Override
    public PersonVO registro(CursorGenerico cursor) {
        PersonVO person = new PersonVO();
        person.setUser_identification(cursor.getString("user_identification"));
        person.setUser_name(cursor.getString("user_name"));
        person.setUser_surname(cursor.getString("user_surname"));
        person.setUser_birth(cursor.getString("user_birth"));
        person.setUser_vehicle(cursor.getString("user_vehicle"));
        person.setUser_profession(cursor.getString("user_profession"));
        person.setUser_married(Boolean.parseBoolean(cursor.getString("user_married")));
        person.setUser_salary(Double.parseDouble(cursor.getString("user_salary")));
        return person;
    }

}
