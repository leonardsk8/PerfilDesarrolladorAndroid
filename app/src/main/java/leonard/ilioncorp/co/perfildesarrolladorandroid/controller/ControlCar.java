package leonard.ilioncorp.co.perfildesarrolladorandroid.controller;

import android.os.Handler;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao.CarDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.CarVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.EsquemaControl;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans.ConsErrors;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;

public class ControlCar implements EsquemaControl<CarVO> {

    private CarDAO dao;

    AppExceptions exApp = null;
    public ControlCar(Conexion con) {
        this.dao = new CarDAO(con.open());

    }


    @Override
    public void insert(CarVO obj) throws AppExceptions {
        dao.insert(obj);
    }

    @Override
    public void delete(CarVO obj) throws AppExceptions {
        dao.delete(obj);
    }

    @Override
    public void update(CarVO obj) throws AppExceptions {
        dao.update(obj);
    }

    @Override
    public List<CarVO> read() throws AppExceptions {
        List<CarVO> list;
        try {
            list = dao.consultarTodos();
        }catch (Exception e){
            exApp = new AppExceptions(ConsErrors.ERROR_READING_DATA);
            throw exApp;
        }
        return list;
    }

    @Override
    public String formatJson() {

        return dao.synchronizedI();
    }

    public List<CarVO> getCarsPerson(String personID){
        List<CarVO> list=null;
            list = dao.consultarCarrosPersona(personID);
        return list;
    }
}
