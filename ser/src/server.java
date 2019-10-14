
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

//中间服务层/业务逻辑层
public class server {
    private dbselect databs;
    private final int port=8080;
    private Map<String,String> mp;


    public server() throws IOException, SQLException, ClassNotFoundException {
        mp=new LinkedHashMap<> ();
        databs =new dbselect();
        ServerSocket ss = new ServerSocket(port);
        System.out.println("服务器启动！！！！");
        Socket s = ss.accept();
        try {
            OutputStream out = null;
            out = s.getOutputStream();
            String aaa="服务端以开启！！！";
            out.write(aaa.getBytes());
            while (true) {
                //Socket s = ss.accept();
                InputStream in = s.getInputStream();
                byte[] buf = new byte[1024];
                int len = in.read(buf);
                String str=" ";
                if(len>0) {
                    str = new String(buf, 0, len);
                    System.out.println("服务端接收到信息：" + str);
                    String b="服务端接收到信息！！！";
                    out.write(b.getBytes());
                    this.doreaddatabase(str);
                    returnmess (s);
                }

//                in.close();
//                out.close();

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            System.out.println("断开连接");
            //databs.closeconnect ();

        }

    }



    public void doreaddatabase(String s) {
         mp=databs.doselect_1(s);
//       System.out.println("?????");
    }

    public void returnmess(Socket s){

        new Thread(new Runnable() {
                        public void run() {
                            try {
                                OutputStream out = null;
                                out = s.getOutputStream ();
                                String returnmes="";
                                for(String key : mp.keySet()){
                                    String value = mp.get(key);
                                    returnmes=key+","+value+"\n";
                                    // System.out.println("日期:"+key+":" + value);
                                    out.write (returnmes.getBytes ());
                                    sleep(30);

                                }
                                out.write ("OVER".getBytes ());
                                out.flush ();
                            }
                             catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace ();
                            }
                        }
                    }).start();
//        try {
//            OutputStream out = null;
//            out = s.getOutputStream ();
//            String returnmes="";
//            for(String key : mp.keySet()){
//                String value = mp.get(key);
//                returnmes=key+","+value+"\n";
//               // System.out.println("日期:"+key+":" + value);
//                out.write (returnmes.getBytes ());
//
//
//            }
//            out.write ("OVER".getBytes ());
//            out.flush ();
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }

    }



    public static void run(){
        try{
            server s = new server();
        } catch (Exception e) {
            System.out.println("Excpn:"+e);
        }
    }


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

       run();
//        server s=new server();

    }
}


