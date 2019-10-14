import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
//数据访问层

public class dbselect {
    private Connection conn;
    private  Map<String,String> mp;


    public dbselect() throws ClassNotFoundException, SQLException {
        mp=new LinkedHashMap<> ();
        Class.forName("com.mysql.jdbc.Driver");
        String dburl = "jdbc:mysql://mydatabs.cafgymca99lf.us-east-1.rds.amazonaws.com:3306/bank?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false";
        conn = DriverManager.getConnection(dburl,"yy","yy183713");
        System.out.println("数据库连接成功");

    }

    public  void closeconnect()
    {
        try{
            conn.close ();
            System.out.println("数据库断开连接");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public Map<String,String> doselect_1(String mess)
    {

        try{
            Statement statement = conn.createStatement();
            System.out.println(mess.split(" ")[0]);
//            String sql = "select  "+"`"+mess.split(" ")[0]+"`" +" from bank ；";
            String sql = "select  "+"`"+mess.split(" ")[0]+"`,`日期`" +" from bank "+" where"+" `日期` >"+"'"+mess.split(" ")[1]+"'"+"and  `日期` <"+"'"+mess.split(" ")[2]+";"+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            String data1;
            String data2;
            while (resultSet.next()) {
                data1= resultSet.getString(mess.split(" ")[0]);
                data2= resultSet.getString("日期");
                mp.put (data2,data1);
                System.out.println("日期:"+data2+" " +mess.split(" ")[0]+":" + data1);
            }
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mp;


    }

}
