package POJO;

import java.util.List;

public class CreateOrderData {

    // ключ firstName стал полем типа String
    private String firstName;
    // ключ lastName стал полем типа String
    private String lastName;
    // ключ address стал полем типа String
    private String address;
    // ключ metroStation стал полем типа String
    private String metroStation;
    // ключ phone стал полем типа String
    private String phone;
    // ключ rentTime стал полем типа int
    private int rentTime;
    // ключ deliveryDate стал полем типа String
    private String deliveryDate;
    // ключ comment стал полем типа String
    private String comment;
    // ключ color стал массивом
    private String[] color;

    // конструктор со всеми параметрами
    public CreateOrderData(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    // конструктор без параметров
    public CreateOrderData() {
    }

    // геттер для поля firstName
    public String getFirstName() {
        return firstName;
    }
    // геттер для поля lastName
    public String getLastName() {
        return lastName;
    }
    // геттер для поля address
    public String getAddress() {
        return address;
    }
    // геттер для поля metroStation
    public String getMetroStation() {
        return metroStation;
    }
    // геттер для поля phone
    public String getPhone() {
        return phone;
    }
    // геттер для поля rentTime
    public int getRentTime() {
        return rentTime;
    }
    // геттер для поля deliveryDate
    public String getDeliveryDate() {
        return deliveryDate;
    }
    // геттер для поля comment
    public String getComment() {
        return comment;
    }
    // геттер для поля color
    public String[] getColor() {
        return color;
    }
    // сеттер для поля firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    // сеттер для поля lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    // сеттер для поля address
    public void setAddress(String address) {
        this.address = address;
    }
    // сеттер для поля metroStation
    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }
    // сеттер для поля phone
    public void setPhone(String phone) {
        this.phone = phone;
    }
    // сеттер для поля rentTime
    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }
    // сеттер для поля deliveryDate
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    // сеттер для поля comment
    public void setComment(String comment) {
        this.comment = comment;
    }
    // сеттер для массива color
    public void setColor(String[] color) {
        this.color = color;
    }
}
