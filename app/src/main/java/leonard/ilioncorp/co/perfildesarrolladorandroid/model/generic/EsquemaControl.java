package leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic;

import android.os.Handler;

import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;

public interface EsquemaControl<T>  {


    void insert(T obj) throws AppExceptions;
    void delete(T obj) throws AppExceptions;
    void update(T obj) throws AppExceptions;
    List<T> read() throws AppExceptions;

    String formatJson();
}
