package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean add(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer (cusID,cusTitle,cusName,cusAddress,cusContact) VALUES (?,?,?,?,?)", dto.getCusID(),dto.getCusTitle(),dto.getCusName(),dto.getCusAddress(),dto.getCusContact());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE cusID=?", id);
        rst.next();
        System.out.println(rst.getString("cusName"));
        return new Customer(id, rst.getString("cusTitle"), rst.getString("cusName"),rst.getString("cusAddress"),rst.getString("cusContact"));
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString("cusID"), rst.getString("cusTitle"), rst.getString("cusName"),rst.getString("cusAddress"),rst.getString("cusContact")));
        }
        return allCustomers;
    }


    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT cusID FROM Customer WHERE cusID=?", id).next();
    }


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT cusID FROM Customer ORDER BY cusID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("cusID");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
    }
}
