package OOSE_Final_Project.Blog.observer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ObserverRegistry {

    private final List<Publisher> publishers;

    private final List<Observer> observers;

    @Autowired
    public ObserverRegistry(List<Publisher> publishers, List<Observer> observers) {
        this.publishers = publishers;
        this.observers = observers;
    }

    @PostConstruct
    public void registerObservers() {
        Map<String, Publisher> publisherMap = publishers.stream()
                                                        .collect(Collectors.toMap(
                                                                publisher -> {
                                                                    String className = publisher.getClass()
                                                                                                .getName();
                                                                    className = className.substring(
                                                                            className.lastIndexOf(".") + 1);
                                                                    return className;
                                                                },
                                                                Function.identity()));


        for (Observer observer : observers) {
            if (observer instanceof Observer typedObserver) {
                String target = typedObserver.getPublisherType();
                Publisher p = publisherMap.get(target);
                if (p != null) {
                    p.subscribe(observer);
                }
            }
        }
    }
}
