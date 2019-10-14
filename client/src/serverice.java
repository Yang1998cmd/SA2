
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;

//中间服务层/业务逻辑层
public class serverice {
    private dbselect databs;
    private final int port=8080;


    public serverice() throws IOException, SQLException, ClassNotFoundException {
        databs =new dbselect();
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("服务器启动。。。");
        try {
            while (true) {
                Socket s = ss.accept();
                InputStream in = s.getInputStream();
                OutputStream out = s.getOutputStream();


                byte[] buf = new byte[1024];
                int len = in.read(buf);


                String str = new String(buf, 0, len);

                System.out.println("服务端接收到信息：" + str);



                in.close();
                out.close();
                s.close();
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            System.out.println("断开连接");

        }

    }



    public void doreaddatabase() {
      databs.doselect_1();

    }


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        serverice s = new serverice();
        s.doreaddatabase();
    }
}

