package leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans;

public enum Routes {
    USER_SERVICE("/service","Route for all the service"),
    DOMAIN("http://35.237.73.197:8080/JukeboxAdministrator","Domain of the project"),
    CHECK_LOGIN("checkLogin","Option for login on the application"),

    ;

    private String url;
    private String description;


    Routes(String url, String description) {

        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
