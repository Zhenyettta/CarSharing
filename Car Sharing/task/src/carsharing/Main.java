package carsharing;


import carsharing.data.CompanyDAO;
import carsharing.data.Menu;

public class Main {

    public static void main(String[] args) {
        String databaseFileName = "carsharing.mv.db";


        CompanyDAO dao = new CompanyDAO(databaseFileName);
        dao.createCompanyTable();
        dao.createCarTable();
        dao.createCustomerTable();
        Menu menu = new Menu(dao);

    }
}