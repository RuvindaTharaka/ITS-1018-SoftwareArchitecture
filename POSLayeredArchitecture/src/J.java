import dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class J{
    public static void main (String [] args) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(" SELECT itemCode , SUM(orderQty) AS max from orderDetail GROUP BY itemCode ORDER BY max DESC LIMIT 1;");
        rst.next();
        System.out.println(rst.getString(1)+"-"+rst.getString(2));
    }
}
