package org.pp.patterns.behavioral.observer;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Observable {

    private State state = State.LOOKING;
    private final List<Observer> observers;

    private volatile boolean stop = false;

    public Observable(List<Observer> observers) {
        this.observers = observers;
    }

    public void change() {
        new ChangeThread().start();
    }

    class ChangeThread extends Thread {
        @Override
        public void run() {
            while (!stop) {
                state = Observable.State.FOLLOWING;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyAllObservers();
            }
        }
    }

    public void stop() {
        stop = true;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private void notifyAllObservers() {
        observers.stream().forEach(o -> o.update(this));
    }

    enum State {
        LOOKING, FOLLOWING, LEADING, OBSERVING
    }
}
