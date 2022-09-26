package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import entity.Customer;
import entity.Item;
import entity.Order;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    public static int count=0;
    public static int count2=0;
    @Override
    public String getMostMove() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(" SELECT itemCode , SUM(orderQty) AS max from orderDetail GROUP BY itemCode ORDER BY max DESC LIMIT 1;");
        rst.next();
        String item=rst.getString(1)+"-"+rst.getString(2);
        return item;
    }

    @Override
    public String getLeastMove() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(" SELECT itemCode , SUM(orderQty) AS max from orderDetail GROUP BY itemCode ORDER BY max ASC LIMIT 1;");
        rst.next();
        String item=rst.getString(1)+"-"+rst.getString(2);
        return item;
    }

    @Override
    public ArrayList<Item> getStockOutItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE qtyOnHand<100;");
        rst.next();
        ArrayList list = new ArrayList();
        if(rst.next()) {
            while (rst.next()) {
                count++;
                list.add(new Item(rst.getString("itemCode"), rst.getString("description"), rst.getInt("packSize"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand")));
            }
            return list;
        }else{
            list.add(new Item("No Item","",0, BigDecimal.valueOf(0.0),0));
            return list;
        }

    }

    @Override
    public ArrayList<Customer> getOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT cusID, COUNT (cusID) FROM Orders GROUP BY cusID");
        rst.next();
        ArrayList list = new ArrayList();
        if(rst.next()) {
            while (rst.next()) {
                count2++;
                list.add(new Customer(rst.getString(1),rst.getInt(2)));
            }
            return list;
        }else{
            list.add(new Customer("No Sales",0));
            return list;
        }
    }
    @Override
    public ArrayList<Order> getAllIncome() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allIncome = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Orders");
        while (rst.next()) {
            allIncome.add(new Order(rst.getString(1), LocalDate.parse(rst.getString(2)),rst.getString(3),rst.getDouble(4)));
        }
        return allIncome;
    }
    @Override
    public boolean add(Object o) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object o) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Object o) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Object search(Object o) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
