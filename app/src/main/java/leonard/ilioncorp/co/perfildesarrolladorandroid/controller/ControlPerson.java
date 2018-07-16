package leonard.ilioncorp.co.perfildesarrolladorandroid.controller;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.model.conexion.Conexion;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao.PersonDAO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto.PersonVO;
import leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic.EsquemaControl;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans.ConsErrors;
import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;

public class ControlPerson implements EsquemaControl<PersonVO> {

    private PersonDAO dao;
    private AppExceptions exApp;
    public ControlPerson(Conexion con) {
        this.dao = new PersonDAO(con.open());

    }


    @Override
    public void insert(PersonVO obj) throws AppExceptions {
            int ans = dao.insert(obj);
            /*if (ans == -1) {
                exApp = new AppExceptions(ConsErrors.ERROR_INSERTING_DATA,obj.getUser_identification());
                throw exApp;
            }*/
    }

    @Override
    public void delete(PersonVO obj) throws AppExceptions {
        int ans = dao.delete(obj);
        /*if(ans > 0 ) {
            exApp = new AppExceptions(ConsErrors.ERROR_DELETING_DATA,obj.getUser_identification());
            throw exApp;
        }*/
    }

    @Override
    public void update(PersonVO obj) throws AppExceptions {
        int ans = dao.update(obj);
        /*if (ans == -1) {
            exApp = new AppExceptions(ConsErrors.ERROR_MODIFYING_DATA,obj.getUser_identification());
            throw exApp;
        }*/
    }

    @Override
    public List<PersonVO> read() throws AppExceptions {
        List<PersonVO> list;
        try {
            list = dao.consultarTodos();
        }catch (Exception e){
           e.printStackTrace();
           list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public String formatJson() {
       return dao.synchronizedInternet();
    }
}
