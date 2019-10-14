import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;


public class clinet {

    private OutputStream out;
    private InputStream in;
    private  Socket s;
    public Map<String,String> mp;

    public clinet() throws IOException {
        mp=new LinkedHashMap<> ();
        try {
            s = new Socket("122.51.50.84", 8080);
            System.out.println("客户端启动！！！！！");
            in = s.getInputStream();
            byte[] buf = new byte[1024];
            int len = in.read(buf);
                //String str = new String(buf);

            if(len>0) {
                String str2 = new String(buf, 0, len);
                System.out.println("客户端接收到信息：" + str2);

            }



        } catch (SocketException e) {
            System.out.println("断开连接");
            in.close ();
            out.close ();
            s.close ();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void sendmess(String a,String b,String c ) throws IOException {
        out = s.getOutputStream();
        String str= String.join(" ", a, b, c);
        System.out.println(a+b+c);
//        System.out.println("到这里了吗");
        out.write(str.getBytes());
        out.flush();
//        System.out.println("到这里了");
    }

    public void revemess( ) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        in = s.getInputStream ();
                        byte[] buf = new byte[1024];
                        int len = in.read (buf);
                        //String str = new String(buf);

                        if (len > 0) {
                            String str2 = new String (buf, 0, len);
                            System.out.println ("客户端接收到信息：" +"\n" +str2);
                           ;
                            if(str2.contains (","))
                            {
                                System.out.println (str2.split (",")[0]+"         "+str2.split (",")[1]+"\n");
                                Display.tarea.append (str2.split (",")[0]+"  "+str2.split (",")[1]+"\n");
                                mp.put (str2.split (",")[0],str2.split (",")[1]);
                            }


                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
//       while(true) {
//            in = s.getInputStream ();
//            byte[] buf = new byte[1024];
//            int len = in.read (buf);
//            //String str = new String(buf);
//
//            if (len > 0) {
//                String str2 = new String (buf, 0, len);
//                System.out.println ("客户端接收到信息：" + str2);
//
//            }
//       }
//        System.out.println("到这里了");
    }

    public void closesock()
    {
        try
        {
        s.close ();
        }

        catch (IOException e) {
        e.printStackTrace();
    }

    }

}
