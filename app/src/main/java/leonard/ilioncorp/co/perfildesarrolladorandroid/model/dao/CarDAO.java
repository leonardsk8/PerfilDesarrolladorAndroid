package leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.CursorGenerico;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.GenericoDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.IConsultaGenerica;

public class CarDAO extends GenericoDAO<CarVO> implements IConsultaGenerica<CarVO> {

    private SQLiteDatabase db;

    public CarDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public CarVO registro(CursorGenerico cursor) {
        return null;
    }

    @Override
    public List<CarVO> consultarTodos() {
        List<CarVO> list;
        String sql = "select * from car";
        list = consultar(sql,null,this);
        return list;
    }
}
