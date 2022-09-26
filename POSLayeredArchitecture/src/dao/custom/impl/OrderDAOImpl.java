package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Customer;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Orders (orderID, orderDate, cusID,amount) VALUES (?,?,?,?)", dto.getOrderID(), dto.getOrderDate(), dto.getCusID(),dto.getAmount());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(Order orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Order search(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Orders WHERE orderID=?", orderID);
        rst.next();
        return new Order(rst.getString("orderID"), LocalDate.parse(rst.getString("orderDate")), rst.getString("cusID"));
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean ifOrderExist(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderID FROM Orders WHERE orderID=?", orderID);
        return rst.next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderID FROM Orders ORDER BY orderID DESC LIMIT 1;");

        String od= rst.next() ? String.format("OD%03d", (Integer.parseInt(rst.getString("orderID").replace("OD", "")) + 1)) : "OD001";
        return od;
    }

}
