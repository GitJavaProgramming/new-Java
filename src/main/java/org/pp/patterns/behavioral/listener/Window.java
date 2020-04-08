package org.pp.patterns.behavioral.listener;

import java.util.ArrayList;
import java.util.List;

public class Window {
    private List<EventListener> listeners;

    public Window() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void fireAction(Event event) {
        listeners.forEach(listener -> listener.process(event));
    }

    @Override
    public String toString() {
        return "Window";
    }
}
