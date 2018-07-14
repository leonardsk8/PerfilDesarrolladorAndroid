package leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans;

/**
 * Created by leona on 2018-03-23.
 */

public enum ConsErrors {
    ERROR_INSERTING_DATA(-1,  "Error al insertar los datos #DATO#"),
    ERROR_MODIFYING_DATA(-2, "Error al modificar los datos #DATO#"),
    ERROR_DELETING_DATA(-3,  "Error al eliminar los datos #DATO#"),
    ERROR_READING_DATA(-4, "Error al consultar los datos #DATO#"),
    EMPTY_FIELD(-5, "Campo obligatorio #DATO#"),
    ERROR_OBJETO_NULO(-6, "Objeto nulo #DATO#"),
    ERROR_CONNECTION(-7, "Error De conexión #DATO#")

    ;



    int codigo;
    String mensaje;

    ConsErrors(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
