package OOSE_Final_Project.Blog.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Publisher {

    protected List<Observer> observers = new ArrayList<>();


    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Object data) {
        for (Observer observer : observers) {
            observer.update(data);
        }
    }
}
