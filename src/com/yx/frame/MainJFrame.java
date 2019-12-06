package com.yx.frame;

import com.yx.Application;
import com.yx.config.Config;
import com.yx.utils.LogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TimerTask;

/**
 * 程序组件
 */
public class MainJFrame extends JFrame implements ActionListener {
    // 自定义组件
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();


    private JLabel label1 = new JLabel("资源地址");
    private JLabel label2 = new JLabel("输出地址");
    private JLabel label3 = new JLabel("执行打包");
    private JLabel label4 = new JLabel("");

    private JTextField textField1 = new JTextField(48);
    private JTextField textField2 = new JTextField(48);
    private JTextArea textArea1 = new JTextArea(12,60);
    private JScrollPane panel4 = new JScrollPane(textArea1);

    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton radio1 = new JRadioButton("是");
    private JRadioButton radio2 = new JRadioButton("否");

    private JButton button1 = new JButton("选择");
    private JButton button2 = new JButton("选择");
    private JButton button3 = new JButton("开始执行");
    private JButton button4 = new JButton("清空日志");

    private int width = 700;
    private int height = 400;
    private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public MainJFrame(){
        textField1.setEnabled(false);
        textField2.setEnabled(false);
        textArea1.setEnabled(false);
        textField1.setText(Config.pathSour);
        textField2.setText(Config.pathDest);
        textArea1.setText(Config.message);

        panel1.add(label1);
        panel1.add(textField1);
        panel1.add(button1);

        panel2.add(label2);
        panel2.add(textField2);
        panel2.add(button2);

        panel3.add(label3);
        panel3.add(radio1);
        panel3.add(radio2);
        buttonGroup.add(radio1);
        buttonGroup.add(radio2);
        radio1.setSelected(true);
        panel3.add(button3);
        panel3.add(button4);
        panel3.add(label4);

        panel4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setSize(width, height);
        this.setLocation((screenWidth - width)/2, (screenHeight-height)/2);
        this.setTitle("MoveJar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        radio1.addActionListener(this);
        radio2.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == button1){
            String path = selectFilePath();
            if(null != path && !"".equals(path)){
                Config.pathSour = path;
                this.textField1.setText(Config.pathSour);
            }
        }
        if(source == button2){
            String path = selectFilePath();
            if(null != path && !"".equals(path)){
                Config.pathDest = path;
                this.textField2.setText(Config.pathDest);
            }
        }
        if(radio1.isSelected()){
            Config.rePackage = Boolean.TRUE;
        }
        if(radio2.isSelected()){
            Config.rePackage = Boolean.FALSE;
        }
        if(source == button3){
            if(checkPathDir(Config.pathSour) && checkPathDir(Config.pathDest)){
                java.util.Timer timer = startupTimelabel();     //执行开始，打开计时器
                LogUtils.logListener(this);             //开始信息监听
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Application.start();                    //开始执行主程序
                        shutdownTimelabel(timer);               //执行结束，关闭计时器
                    }
                }).start();
            }else{
                LogUtils.printInfo("资源地址或输出地址不能为空...");
            }
        }
        if(source == button4){
            Config.message = Config.defaultMeaasge();
            textArea1.setText(Config.message);
        }
    }

    private String selectFilePath(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showDialog(new JLabel(), "选择");
        File file = chooser.getSelectedFile();
        if(null != file){
            return file.getAbsolutePath().replace("\\",File.separator);
        }
        return "";
    }

    private boolean checkPathDir(String path){
        return null != Config.pathSour && !"".equals(Config.pathSour);
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public void setTextArea1(JTextArea textArea1) {
        this.textArea1 = textArea1;
    }

    //时间显示
    private java.util.Timer startupTimelabel() {
        java.util.Timer timer =  new java.util.Timer();
        timer.schedule(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                label4.setText("已执行: " + i++  + " s");
            }
        }, 0L, 1000L);
        return timer;
    }
    private void shutdownTimelabel(java.util.Timer timer) {
        timer.cancel();
        label4.setText(label4.getText() + " 执行完毕");
    }

}
