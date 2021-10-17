package com.ckr.listener;

import java.awt.*;
//import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Shadowckr
 * @create 2021-09-12 21:04
 */

// 监听器：GUI编程中经常使用
public class TestGUIListener {
    public static void main(String[] args) {
        // 新建一个窗体
        Frame frame = new Frame("黑暗之魂三");
        // 新建一个面板
        Panel panel = new Panel();
        // 设置窗体的布局
        frame.setLayout(null);

        frame.setBounds(300, 300, 600, 600);
        frame.setBackground(new Color(148, 0, 211));

        panel.setBounds(50, 50, 200, 200);
        panel.setBackground(new Color(255, 105, 180));

        frame.add(panel);
        frame.setVisible(true);

        // 监听事件（监听关闭事件）
//        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.out.println("WindowClosing");
//            }
//        });

        // 监听事件
        frame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
                System.out.println("WindowOpened");
            }

            public void windowClosing(WindowEvent e) {
                System.out.println("WindowClosing");
                System.exit(0);// 正常退出
            }

            public void windowClosed(WindowEvent e) {
                System.out.println("WindowClosed");
            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowActivated(WindowEvent e) {
                System.out.println("WindowActivated");
            }

            public void windowDeactivated(WindowEvent e) {
                System.out.println("WindowDeactivated");
            }
        });

    }
}
