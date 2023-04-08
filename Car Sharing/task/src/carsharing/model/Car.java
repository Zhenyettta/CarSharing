package carsharing.model;

public class Car {
    private int id;
    private String name;
    private static int counter = 1;

    public Car(String name) {
        this.name = name;
        id = counter;
        counter++;
    }

    public Car(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
