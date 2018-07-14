package leonard.ilioncorp.co.perfildesarrolladorandroid.model.dto;

import java.io.Serializable;

public class CarVO implements Serializable {
    private String car_plate;
    private String car_brand;
    private String car_model;
    private int car_doors;
    private String car_type;
    private String car_color_wheels;
    private String car_color_hoods;
    private String car_color_doors;

    public String getCar_color_wheels() {
        return car_color_wheels;
    }

    public void setCar_color_wheels(String car_color_wheels) {
        this.car_color_wheels = car_color_wheels;
    }

    public String getCar_color_hoods() {
        return car_color_hoods;
    }

    public void setCar_color_hoods(String car_color_hoods) {
        this.car_color_hoods = car_color_hoods;
    }

    public String getCar_color_doors() {
        return car_color_doors;
    }

    public void setCar_color_doors(String car_color_doors) {
        this.car_color_doors = car_color_doors;
    }

    public String getCar_plate() {
        return car_plate;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public int getCar_doors() {
        return car_doors;
    }

    public void setCar_doors(int car_doors) {
        this.car_doors = car_doors;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }
}
