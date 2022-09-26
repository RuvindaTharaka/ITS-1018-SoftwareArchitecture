package dao.custom;

import dao.CrudDAO;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item, String> {
    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    public boolean add(Item dto) throws SQLException, ClassNotFoundException;

    public boolean delete(String code) throws SQLException, ClassNotFoundException;

    public boolean update(Item dto) throws SQLException, ClassNotFoundException;

    public Item search(String code) throws SQLException, ClassNotFoundException;

    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException;



}
