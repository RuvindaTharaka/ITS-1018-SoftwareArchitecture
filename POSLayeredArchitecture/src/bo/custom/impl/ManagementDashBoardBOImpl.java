package bo.custom.impl;

import bo.custom.ManagementDashBoardBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.QueryDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.Customer;
import entity.Item;
import entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManagementDashBoardBOImpl implements ManagementDashBoardBO {
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String getMostMovingItem() throws SQLException, ClassNotFoundException {
        String item;
        item= queryDAO.getMostMove();
        return  item;
    }

    @Override
    public String getLeastMovingItem() throws SQLException, ClassNotFoundException {
        String item;
        item=queryDAO.getLeastMove();
        return item;
    }

    @Override
    public ArrayList<ItemDTO> getStockOut() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getItemCode(),i.getDescription(),i.getPackSize(),i.getUnitPrice(),i.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public ArrayList<CustomerDTO> getNoOfSales() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allItems = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer i : all) {
            allItems.add(new CustomerDTO(i.getCusID(),i.getNoOfSales()));
        }
        return allItems;
    }

    @Override
    public ArrayList<OrderDTO> getAllIncome() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allItems = new ArrayList<>();
        ArrayList<Order> all = queryDAO.getAllIncome();
        for (Order i : all) {
            allItems.add(new OrderDTO(i.getOrderID(),i.getOrderDate(),i.getCusID(),i.getAmount()));
        }
        return allItems;
    }


}
