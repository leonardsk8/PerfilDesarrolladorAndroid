

package leonard.ilioncorp.co.perfildesarrolladorandroid.utils.exception;

import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans.ConsErrors;

/**
 *
 * @author Administrador
 */
public class AppExceptions extends Exception {

    public static ConsErrors errorApp;

    public AppExceptions(ConsErrors errorApp) {
        super(errorApp.getMensaje().replace("#DATO#",""));
        this.errorApp = errorApp;
    }
    
    public AppExceptions(ConsErrors errorApp, Throwable ex) {
        super(errorApp.getMensaje().replace("#DATO#"," "+ex.getMessage()+" "));
        this.errorApp = errorApp;
    }
    
    public AppExceptions(ConsErrors errorApp, String ex) {
        super(errorApp.getMensaje().replace("#DATO#"," "+ex+" "));
        this.errorApp = errorApp;
    }

    public ConsErrors getErrorApp() {
        return errorApp;
    }

    public void setErrorApp(ConsErrors errorApp) {
        this.errorApp = errorApp;
    }
}
