package carsharing.data;

import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompanyDAO {
    private final ConnectionFactory factory;

    public CompanyDAO(String fileName) {
        this.factory = new ConnectionFactory(fileName);
    }

    public void dropAllTables() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "DROP TABLE IF EXISTS CUSTOMER, CAR, COMPANY";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void createCompanyTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS COMPANY " +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME varchar_ignorecase(255) unique NOT NULL);";

            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void createCarTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS CAR " +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME varchar_ignorecase(255) unique NOT NULL, " +
                    "COMPANY_ID INTEGER NOT NULL, " +
                    "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID));";

            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void createCustomerTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME varchar_ignorecase(255) unique NOT NULL, " +
                    "RENTED_CAR_ID INTEGER, " +
                    "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR (ID));";

            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void insertCompany(Company company) {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "INSERT INTO COMPANY(NAME) " +
                    "VALUES ('" + company.getName() + "');";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void insertCar(Company company, Car car) {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "INSERT INTO CAR(NAME, COMPANY_ID) " +
                    "VALUES ('" + car.getName() + "'," + "'" + company.getId() + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void insertRentCar(Customer customer, Car car) {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "UPDATE CUSTOMER " + "SET RENTED_CAR_ID =" + car.getId()
                    + " WHERE ID =" + customer.getId();

            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void returnRentCar(Customer customer) {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "UPDATE CUSTOMER " + "SET RENTED_CAR_ID =" + null
                    + " WHERE ID =" + customer.getId();

            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void insertCustomer(Customer customer) {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "INSERT INTO CUSTOMER(NAME) " +
                    "VALUES ('" + customer.getName() + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }


    public ArrayList<Company> retrieveCompanies() {
        ArrayList<Company> list = new ArrayList<>();
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "SELECT * FROM COMPANY ORDER BY ID";
            ResultSet set = stmt.executeQuery(sql);
            while (set.next()) {
                list.add(new Company(set.getInt(1), set.getString(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
        return list;
    }

    public ArrayList<Customer> retrieveCustomers() {
        ArrayList<Customer> list = new ArrayList<>();
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "SELECT * FROM CUSTOMER";
            ResultSet set = stmt.executeQuery(sql);
            while (set.next()) {
                list.add(new Customer(set.getString(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
        return list;
    }

    public ArrayList<Car> retrieveCarsForCompany(Company company) {
        ArrayList<Car> list = new ArrayList<>();
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            String sql = "SELECT DISTINCT * FROM CAR " +
                    "WHERE CAR.COMPANY_ID = " + company.getId();
            ResultSet set = stmt.executeQuery(sql);
            while (set.next()) {
                list.add(new Car(set.getInt(1), set.getString(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
        return list;
    }


}