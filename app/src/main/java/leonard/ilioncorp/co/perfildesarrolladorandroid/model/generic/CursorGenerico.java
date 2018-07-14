package leonard.ilioncorp.co.perfildesarrolladorandroid.model.generic;

import android.database.Cursor;

import java.util.Date;

/**
 * Created by lrey on 4/27/18.
 */

public class CursorGenerico {

    private final Cursor cursor;

    public CursorGenerico(Cursor cursor) {
        this.cursor = cursor;
    }

    public String getString(String nombreColumna) {
        return cursor.getString(cursor.getColumnIndex(nombreColumna));
    }

    public Integer getInt(String nombreColumna) {
        return cursor.getInt(cursor.getColumnIndex(nombreColumna));
    }

    public Long getLong(String nombreColumna) {
        return cursor.getLong(cursor.getColumnIndex(nombreColumna));
    }

    public Date getDate(String nombreColumna) {
        return new Date(cursor.getLong(cursor.getColumnIndex(nombreColumna)));
    }
    public double getDouble(String nombreColumna){
        return cursor.getColumnIndex(nombreColumna);
    }

}
