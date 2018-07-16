package leonard.ilioncorp.co.perfildesarrolladorandroid.controller;

import android.os.Handler;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao.HistoryDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.HistoryVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.EsquemaControl;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;

public class ControlHistory implements EsquemaControl<HistoryVO> {

    private HistoryDAO dao;
    AppExceptions exApp;
    public ControlHistory(Conexion con) {
        this.dao = new HistoryDAO(con.open());
    }

    @Override
    public void insert(HistoryVO obj) throws AppExceptions {
        dao.insert(obj);
    }

    @Override
    public void delete(HistoryVO obj) throws AppExceptions {
        dao.delete(obj);
    }

    @Override
    public void update(HistoryVO obj) throws AppExceptions {
        dao.update(obj);
    }

    @Override
    public List<HistoryVO> read() throws AppExceptions {
        return null;
    }

    @Override
    public String formatJson() {
        return dao.synchronizedI();
    }

    public HistoryVO getActualHistory(HistoryVO historyVO) {
        HistoryVO history ;
        history = dao.getActualHistory(historyVO);
        return history;
    }
}
