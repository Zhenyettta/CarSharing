package carsharing.data;

import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final String managerLogIn = "1. Log in as a manager";
    private static final String customerLogIn = "2. Log in as a customer";
    private static final String customerCreate = "3. Create a customer";
    private static final String exit = "0. Exit";
    private static final String back = "0. Back";
    private static final String companyList = "1. Company list";
    private static final String addCompany = "2. Create a company";
    private static final String carList = "1. Car list";
    private static final String carCreate = "2. Create a car";
    private static final String rentCar = "1. Rent a car";
    private static final String returnCar = "2. Return a rented car";
    private static final String myRented = "3. My rented car";
    private static CompanyDAO DAO;
    private static ArrayList<Company> listCompanies = new ArrayList<>();
    private static ArrayList<Customer> listCustomers = new ArrayList<>();

    public Menu(CompanyDAO DAO) {
        this.DAO = DAO;
        listCustomers = DAO.retrieveCustomers();
        listCompanies = DAO.retrieveCompanies();
        listCompanies.forEach(i -> {
            i.setList(DAO.retrieveCarsForCompany(i));
        });
        logInPage();
    }

    public static void logInPage() {
        System.out.println(managerLogIn + "\n" + customerLogIn + "\n" + customerCreate + "\n" + exit);
        Scanner scan = new Scanner(System.in);
        switch (scan.nextInt()) {
            case 0:
                System.exit(0);
                break;
            case 1:
                System.out.println();
                managerPage();
                break;
            case 2:
                System.out.println();
                customerList();
                break;
            case 3:
                System.out.println();
                addCustomer();
                break;
            default:
                System.out.println("Unsupported button :(");
        }
    }

    public static void managerPage() {
        System.out.println(companyList + "\n" + addCompany + "\n" + back);
        Scanner scan = new Scanner(System.in);
        switch (scan.nextInt()) {
            case 0:
                System.out.println();
                logInPage();
                break;
            case 1:
                System.out.println();
                listCompanies = DAO.retrieveCompanies();
                companyList();
                break;
            case 2:
                System.out.println();
                addCompany();
                break;
            default:
                System.out.println("Unsupported button :(");
        }
    }

    public static void customerList() {
        if (listCustomers.isEmpty()) {
            System.out.println("The customer list is empty!\n");
            logInPage();
        }
        System.out.println("Choose a customer:");
        for (int i = 0; i < listCustomers.size(); i++) {
            System.out.println((i + 1) + ". " + listCustomers.get(i).getName());
        }
        System.out.println(back);

        int num = new Scanner(System.in).nextInt();
        if (num == 0) {
            System.out.println();
            logInPage();
        }

        System.out.println();
        customerPage(listCustomers.get(num - 1));

    }

    public static void customerPage(Customer customer) {
        System.out.println(rentCar + "\n" + returnCar + "\n" + myRented + "\n" + back);
        Scanner scan = new Scanner(System.in);
        switch (scan.nextInt()) {
            case 1:
                System.out.println();
                rentCar(customer);
                break;
            case 2:
                System.out.println();
                returnCar(customer);
                break;
            case 3:
                System.out.println();
                rentedCar(customer);
                break;
            case 0:
                System.out.println();
                logInPage();
                break;

        }
    }


    public static void rentedCar(Customer customer) {
        Car car = customer.getCar();
        if (car == null) {
            System.out.println("You didn't rent a car!\n");
            customerPage(customer);
        }
        Company rentedCompany = customer.getCompany();

        System.out.println("Your rented car:\n" + car.getName() + "\n"
                + "Company:\n" + rentedCompany.getName() + "\n");

        customerPage(customer);
    }

    public static void rentCar(Customer customer) {
        if (customer.getCar() != null) {
            System.out.println("You've already rented a car!\n");
            customerPage(customer);
        }

        if (listCompanies.isEmpty()) {
            System.out.println("The company list is empty!\n");
            customerPage(customer);
        }

        System.out.println("Choose a company:" + "\n");
        for (int i = 0; i < listCompanies.size(); i++) {
            System.out.println((i + 1) + ". " + listCompanies.get(i).getName());
        }

        System.out.println(back);
        int num = new Scanner(System.in).nextInt();

        if (num == 0) {
            System.out.println();
            managerPage();
        }
        System.out.println();
        carListRent(listCompanies.get(num - 1), customer);
    }

    public static void carListRent(Company company, Customer customer) {
        if (company.getList().isEmpty()) {
            System.out.println("No available cars in the '" + company.getName() + "' company\n");
            customerPage(customer);
        }
        customer.setCompany(company);

        System.out.println("Choose a car:");
        for (int i = 0; i < company.getList().size(); i++) {
            System.out.println((i + 1) + ". " + company.getList().get(i).getName());
        }
        System.out.println(back);
        int num = new Scanner(System.in).nextInt();
        if (num == 0){
            System.out.println();
            rentCar(customer);
        }
        System.out.println();
        RentCar(customer, company.getList().get(num - 1));
    }

    public static void returnCar(Customer customer) {
        if (customer.getCar() == null) {
            System.out.println("You didn't rent a car!\n");
            customerPage(customer);
        }
        DAO.returnRentCar(customer);
        customer.getCompany().getList().add(customer.getCar());
        customer.setCar(null);
        System.out.println("You've returned a rented car!\n");
        customerPage(customer);

    }

    public static void RentCar(Customer customer, Car car) {
        DAO.insertRentCar(customer, car);
        customer.setCar(car);
        customer.getCompany().getList().remove(car);
        System.out.println("You rented " + "'" + car.getName() + "'");

        System.out.println();
        customerPage(customer);

    }


    public static void companyList() {
        if (listCompanies.isEmpty()) {
            System.out.println("The company list is empty!\n");
            managerPage();
        } else {
            System.out.println("Choose the company:");
            for (int i = 0; i < listCompanies.size(); i++) {
                System.out.println((i + 1) + ". " + listCompanies.get(i).getName());
            }
            System.out.println(back);


            int num = new Scanner(System.in).nextInt();

            if (num == 0) {
                System.out.println();
                managerPage();
            }

            System.out.println();
            companyPage(listCompanies.get(num - 1));
        }
    }

    public static void addCompany() {
        System.out.println("Enter the company name:");
        Scanner scan = new Scanner(System.in);
        Company company = new Company(scan.nextLine());
        DAO.insertCompany(company);
        listCompanies.add(company);
        System.out.println("The company was created!\n");
        managerPage();
    }

    public static void addCustomer() {
        System.out.println("Enter the customer name:");
        Scanner scan = new Scanner(System.in);
        Customer customer = new Customer(scan.nextLine());
        DAO.insertCustomer(customer);
        listCustomers.add(customer);
        System.out.println("The customer was added!\n");
        logInPage();
    }

    public static void companyPage(Company company) {
        System.out.println(company.getName() + " company");
        System.out.println(carList + "\n" + carCreate + "\n" + back);
        Scanner scan = new Scanner(System.in);
        switch (scan.nextInt()) {
            case 1:
                System.out.println();
                carList(company);
                break;
            case 2:
                System.out.println();
                carCreate(company);
                break;
            case 0:
                System.out.println();
                managerPage();
                break;

        }
    }


    public static void carList(Company company) {
        if (DAO.retrieveCarsForCompany(company).isEmpty()) {
            System.out.println("The car list is empty!\n");
            companyPage(company);
        }

        System.out.println("Car list:");
        for (int i = 0; i < DAO.retrieveCarsForCompany(company).size(); i++) {
            System.out.println((i + 1) + ". " + DAO.retrieveCarsForCompany(company).get(i).getName());
        }

        System.out.println();
        companyPage(company);
    }

    public static void carCreate(Company company) {
        System.out.println("Enter the car name:");
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        if (line.isBlank() || isNumeric(line)) {
            System.out.println("The car list is empty!\n");
            companyPage(company);
        }
        Car car = new Car(line);
        company.addCar(car);
        DAO.insertCar(company, car);
        System.out.println("The car was added!\n");
        companyPage(company);
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}