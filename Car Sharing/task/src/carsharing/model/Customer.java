package carsharing.model;

public class Customer {
    private Car car;
    private String name;
    private static int counter = 1;
    private int id;
    Company company;

    public Customer(String name) {
        this.name = name;
        id = counter;
        counter++;

    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
