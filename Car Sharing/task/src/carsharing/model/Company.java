package carsharing.model;

import java.util.ArrayList;

public class Company {
    private int id;
    private String name;
    private ArrayList<Car> list = new ArrayList<>();
    private static int counter = 1;

    public Company(String name) {
        this.setName(name);
        id = counter;
        counter++;
    }
    public Company(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Car> getList() {
        return list;
    }

    public void setList(ArrayList<Car> list) {
        this.list = list;
    }

    public void addCar(Car car) {
        list.add(car);
    }
}