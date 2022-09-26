package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManagementDashBoardBO extends SuperBO {
    String getMostMovingItem()  throws SQLException, ClassNotFoundException;
    String getLeastMovingItem()  throws SQLException, ClassNotFoundException;
    ArrayList<ItemDTO> getStockOut()  throws SQLException, ClassNotFoundException;
    ArrayList<CustomerDTO> getNoOfSales()  throws SQLException, ClassNotFoundException;
    ArrayList<OrderDTO> getAllIncome()   throws SQLException, ClassNotFoundException;
}
