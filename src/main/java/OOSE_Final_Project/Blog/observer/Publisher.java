package OOSE_Final_Project.Blog.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Publisher {

    protected List<Observer> observers = new ArrayList<>();

    protected void subscribe(Observer observer) {
        observers.add(observer);
    }

    protected void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    Publisher() {

    }

    abstract void notifyObservers(Object data);
}
