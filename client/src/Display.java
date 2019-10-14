import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Display extends JFrame {

    private clinet cc;
    private JPanel pan;
    private JComboBox<String> faceCombo1;//下拉选项
    private JComboBox<String> faceCombo2;
    private JComboBox<String> faceCombo3;
    public static  String select1;//获取下拉选项
    public static  String select2;
    public static  String select3;
    public static JTextArea tarea;//服务器端日志



    public Display(clinet cl) {
        this.cc=cl;

        JFrame frame1=new JFrame();
        frame1.setTitle("客户端");
        frame1.setBounds(100, 100, 550, 500);
        frame1.setLocation(500,300);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 550, 500);
        pan = new JPanel();
        pan.setBorder(new EmptyBorder(5, 5, 10, 5));
        frame1.setContentPane(pan);
        pan.setLayout(null);

        String[] listData1 = new String[]{" ","前收盘价(元)", "开盘价(元)", "最高价(元)", "最低价(元)", "收盘价(元)", "成交量(股)", "涨跌(元)", "均价(元)"};
        String[] listData2 = new String[]{" ","1999/11/10","2000/11/10", "2001/11/10", "2002/11/10", "2003/11/10", "2004/11/10", "2005/11/10", "2006/11/10"};
        String[] listData3 = new String[]{" ","1999/11/10","2000/11/10", "2001/11/10", "2002/11/10", "2003/11/10", "2004/11/10", "2005/11/10", "2006/11/10"};

        ItemListener ite1 = arg0 -> {
            // TODO Auto-generated method stub
            if (ItemEvent.SELECTED == arg0.getStateChange()) {
                       String selectedItem = arg0.getItem().toString();
                       select1=selectedItem;
                       System.out.printf("查询内容 : %s%n",select1);
//                       tarea.append("查询内容："+select1+"\n");

            }
        };

        ItemListener ite2 = arg0 -> {
            // TODO Auto-generated method stub
            if (ItemEvent.SELECTED == arg0.getStateChange()) {
                String selectedItem = arg0.getItem().toString();
                select2=selectedItem;
                System.out.printf("查询时间 : %s%n",select2);

            }
        };

        ItemListener ite3 = arg0 -> {
            // TODO Auto-generated method stub
            if (ItemEvent.SELECTED == arg0.getStateChange()) {
                String selectedItem = arg0.getItem().toString();
                select3=selectedItem;
                System.out.printf("查询时间 : %s%n",select3);
//                tarea.append("查询时间："+select2+"--"+select3+"\n");

            }
        };


        faceCombo1 = new JComboBox<String>(listData1);
        faceCombo1.setEditable(true);
        faceCombo1.addItemListener(ite1);
        faceCombo1.setEnabled(true);
        add(faceCombo1, BorderLayout.SOUTH);
        faceCombo1.setBounds(10, 20, 100, 30);

        faceCombo2 = new JComboBox<String>(listData2);
        faceCombo2.setEditable(true);
        faceCombo2.addItemListener(ite2);
        faceCombo2.setEnabled(true);
        add(faceCombo2, BorderLayout.SOUTH);
        faceCombo2.setBounds(200, 20, 100, 30);

        faceCombo3 = new JComboBox<String>(listData3);
        faceCombo3.setEditable(true);
        faceCombo3.addItemListener(ite3);
        faceCombo3.setEnabled(true);
        add(faceCombo3, BorderLayout.SOUTH);
        faceCombo3.setBounds(400, 20, 100, 30);

        pan.add(faceCombo1);
        pan.add(faceCombo2);
        pan.add(faceCombo3);

        JButton Start = new JButton("查询");
        Start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tarea.append("查询内容："+select1+"\n");
                    tarea.append("查询时间："+select2+"--"+select3+"\n");
                    cc.sendmess(select1,select2,select3);
                    cc.revemess ();
                } catch (IOException ex) {
                    ex.printStackTrace ();
                }

//                    new Thread(new Runnable() {
//                        public void run() {
//                            clinet cl= null;
//                            try {
//                                cl = new clinet();
//                                cl.sendmess(select1,select2,select3);
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    }).start();

            }
        });
        Start.setBounds(10, 100, 100, 30);
        pan.add(Start);

        JButton Sec = new JButton("折线图");
        Sec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makegra();

            }
        });
        Sec.setBounds(200, 100, 100, 30);
        pan.add(Sec);



        tarea = new JTextArea();
        tarea.setBounds(10, 200, 500, 250);
        pan.add(tarea);
        tarea.setColumns(10);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 200, 500, 250);
        scrollPane.setViewportView(tarea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pan.add(scrollPane);
    }

    public  void makegra()
    {
        XYDataset xydataset = createDataset();

        JFreeChart chart = null;
        chart = ChartFactory.createTimeSeriesChart("浦发银行"+select1, "M", "F",xydataset, true, true, true);

        chart.setTitle(new TextTitle ("浦发银行"+select2+"-"+select3+select1+"图"));
                //设置子标题
        chart.setAntiAlias(true);
                 //设置时间轴的范围。
        XYPlot plot = (XYPlot) chart.getPlot();
//        DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
//        dateaxis.setDateFormatOverride(new SimpleDateFormat("M"));
//        dateaxis.setTickUnit(new DateTickUnit (DateTickUnit.MONTH, 1));

                //设置曲线是否显示数据点
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) plot.getRenderer();
        xylinerenderer.setBaseShapesVisible(true);

                //设置曲线显示各数据点的值
        XYItemRenderer xyitem = plot.getRenderer();
        xyitem.setBaseItemLabelsVisible(true);
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition (ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator ());
        xyitem.setBaseItemLabelFont(new Font("Dialog", Font.BOLD, 12));
        plot.setRenderer(xyitem);

        JPanel jPanel = new ChartPanel(chart);


        JFrame frame = new JFrame("折线图");
        frame.add(jPanel);
        frame.setBounds(0, 0, 800, 600);
        frame.setVisible(true);


    }

    public XYDataset createDataset() {
        TimeSeries timeseries = new TimeSeries(select1,
                org.jfree.data.time.Day.class);
        for(String key : cc.mp.keySet()){
            System.out.printf(key+"??"+cc.mp.get(key));
            double value = Double.valueOf(cc.mp.get(key));
            int year=Integer.parseInt(key.split ("-")[0]);
            int month=Integer.parseInt(key.split ("-")[1]);
            int day=Integer.parseInt(key.split ("-")[2]);
            timeseries.add (new Day (day,month,year),value);

        }

        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection ();
        timeseriescollection.addSeries(timeseries);
        return timeseriescollection;

    }


        /**
         * Launch the application.
         */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    clinet cl= null;
                    cl = new clinet();
                    Display frame = new Display(cl);




                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

}







