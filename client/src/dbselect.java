import java.sql.*;
//数据访问层

public class dbselect {
    private Connection conn;

    public dbselect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String dburl = "jdbc:mysql://mydatabs.cafgymca99lf.us-east-1.rds.amazonaws.com:3306/bank?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false";
        Connection conn = DriverManager.getConnection(dburl,"yy","yy183713");
        System.out.println("数据库连接成功");

    }

    public void doselect_1()
    {
        try{
            Statement statement = conn.createStatement();
            String sql = "select  `前收盘价(元)`  from bank ;";//我的表格叫home
            ResultSet resultSet = statement.executeQuery(sql);
            String data;
            while (resultSet.next()) {
                data= resultSet.getString("前收盘价(元)");
                System.out.println("前收盘价(元)：" + data);
            }
            resultSet.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}

