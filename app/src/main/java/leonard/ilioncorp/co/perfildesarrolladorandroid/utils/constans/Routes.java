package leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans;

public enum Routes {
    SYNCHRONIZED("/Synchronization","Route for synchronized tables"),
    DOMAIN("http://35.237.73.197:8080/PruebaDesarrolloAndroid","Domain of the project"),


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
