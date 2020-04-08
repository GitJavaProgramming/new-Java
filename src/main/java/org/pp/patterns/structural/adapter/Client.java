package org.pp.patterns.structural.adapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.*;

/**
 * 作为多个接口的公共抽象子类，接口兼容
 *
 * 监听鼠标click、move，滚轮移动和窗口关闭事件
 * 2秒后显示提示消息
 * 5秒后关闭系统
 */
public class Client extends JFrame {

    public Client() throws HeadlessException {
        // MouseAdapter兼容三种接口MouseListener, MouseWheelListener, MouseMotionListener
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseHandler());
        addMouseWheelListener(new MouseHandler());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("窗口关闭.");
                System.exit(0);
            }
        });
        this.setSize(300, 300);
        this.setLayout(new BorderLayout());
        this.add(new JLabel("界面上随便点."));
        this.setLocationRelativeTo(null);
    }

    public void start() {
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // main logic
        final Client frame = new Client();
        SwingUtilities.invokeLater(() -> frame.start());

        // jobs
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture future = service.schedule(() -> {
            JOptionPane.showMessageDialog(frame, "系统即将关闭.", "警告", JOptionPane.WARNING_MESSAGE); // blocked
            System.out.println("现在关闭.");
            System.exit(0);
        }, 1, TimeUnit.SECONDS);
        try {
            future.get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            frame.dispose(); // 超时毁掉窗体
        } finally {
            // hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("系统关闭.")));
        }
    }
}
