package leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception.AppExceptions;

/**
 * Created by lrey on 4/27/18.
 */

public abstract class GenericoDAO<T> {

    protected final SQLiteDatabase db;
    private AppExceptions exApp;

    public GenericoDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public <T extends Object> List<T> consultar(String sql,
                                                String[] args,
                                                IConsultaGenerica<T> generico) {
        if (args == null) {
            args = new String[]{};
        }
        Cursor cursor = db.rawQuery(sql, args);
        if (!cursor.moveToFirst()) {
            return new ArrayList<>();
        }
        List<T> lista = new ArrayList<>();
        CursorGenerico cursorGenerico = new CursorGenerico(cursor);
        do {
            lista.add(generico.registro(cursorGenerico));
        } while (cursor.moveToNext());
        return lista;
    }

    public abstract List<T> consultarTodos();


}
