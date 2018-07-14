package leonard.ilioncorp.co.perfildesarrolladorandroid.controller;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao.HistoryDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.HistoryVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.EsquemaControl;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;

public class ControlHistoy implements EsquemaControl<HistoryVO> {

    private HistoryDAO dao;
    AppExceptions exApp;
    public ControlHistoy(Conexion con) {
        this.dao = new HistoryDAO(con.open());
    }

    @Override
    public void insert(HistoryVO obj) throws AppExceptions {

    }

    @Override
    public void delete(HistoryVO obj) throws AppExceptions {

    }

    @Override
    public void update(HistoryVO obj) throws AppExceptions {

    }

    @Override
    public List<HistoryVO> read() throws AppExceptions {
        return null;
    }
}
