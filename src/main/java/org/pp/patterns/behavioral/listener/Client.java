package org.pp.patterns.behavioral.listener;

/**
 * 监听器模式
 */
public class Client {
    public static void main(String[] args) {
        // 事件源 + 监听
        Window window = new Window();
        window.addListener(new EventListener() { // 有多个监听接口时可以用适配器
            @Override
            void process(Event event) {
                System.out.println(event.getSource());
            }
        });
        // 构造事件
        Event event = new Event(window);
        // 手动触发事件
        window.fireAction(event);
    }
}
