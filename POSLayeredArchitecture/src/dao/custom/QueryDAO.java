package dao.custom;

import dao.CrudDAO;
import entity.Customer;
import entity.Item;
import entity.Order;


import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends CrudDAO {
    String getMostMove()throws SQLException, ClassNotFoundException;
    String getLeastMove()throws SQLException, ClassNotFoundException;
    ArrayList<Item> getStockOutItem() throws SQLException, ClassNotFoundException;
    ArrayList<Customer>getOrderCount() throws SQLException, ClassNotFoundException;
    ArrayList<Order> getAllIncome() throws SQLException, ClassNotFoundException;
}
