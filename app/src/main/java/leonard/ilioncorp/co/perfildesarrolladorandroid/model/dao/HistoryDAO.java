package leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.controller.ControlHistory;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.HistoryVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.CursorGenerico;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.GenericoDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.IConsultaGenerica;

public class HistoryDAO extends GenericoDAO<HistoryVO> implements IConsultaGenerica<HistoryVO> {


    public HistoryDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public List<HistoryVO> consultarTodos() {
        List<HistoryVO> list;
        String sql = "select * from history";
        list = consultar(sql,null,this);
        return list;
    }


    @Override
    public HistoryVO registro(CursorGenerico cursor)
    {
        HistoryVO history = new HistoryVO();
        history.setUser_user_identification(cursor.getString("user_user_identification"));
        history.setCar_car_plate(cursor.getString("car_car_plate"));
        history.setUser_car_actual(Boolean.parseBoolean(cursor.getString("user_car_actual")));
        return history;
    }

    public int insert(HistoryVO history) {
        try{
            ContentValues values  =new ContentValues();
            values.put("user_user_identification",history.getUser_user_identification());
            values.put("car_car_plate",history.getCar_car_plate());
            values.put("user_car_actual",history.getUser_car_actual()+"");
            return (int) db.insert("history",null,values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int delete(HistoryVO obj) {
        try {
            String[] parameters = new String[]{obj.getUser_user_identification(),obj.getCar_car_plate()};
            return db.delete("history", "user_user_identification=? and car_car_plate=?", parameters);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public void update(HistoryVO obj) {
        try{
            ContentValues values = new ContentValues();
            values.put("user_user_identification",obj.getUser_user_identification());
            values.put("car_car_plate",obj.getCar_car_plate());
            values.put("user_car_actual",obj.getUser_car_actual());
            String[] parameters = new String[]{obj.getUser_user_identification(),obj.getCar_car_plate()};
            db.update("history",values,"user_user_identification=? and car_car_plate=?",parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HistoryVO getActualHistory(HistoryVO obj) {
        HistoryVO actualHistory = new HistoryVO();
        String sql = "select * from history where user_user_identification='"+obj.getUser_user_identification()+"'" +
                "and user_car_actual='true'";
        List<HistoryVO> list = consultar(sql,null,this::registro);
        if(list.size()>0)
            return list.get(0);
        else {
            actualHistory.setCar_car_plate("");
            return actualHistory;
        }
    }

    public String synchronizedI() {
        List<HistoryVO> list = consultarTodos();
        if(list.size()==0){
            return "[]";
        }
        return new Gson().toJson(list);
    }
}
