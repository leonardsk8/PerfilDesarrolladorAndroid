package leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.HistoryVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.CursorGenerico;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.GenericoDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.IConsultaGenerica;

public class HistoryDAO extends GenericoDAO<HistoryVO> implements IConsultaGenerica<HistoryVO> {

    private SQLiteDatabase db;
    public HistoryDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public List<HistoryVO> consultarTodos() {
        return null;
    }


    @Override
    public HistoryVO registro(CursorGenerico cursor) {
        return null;
    }
}
