package be.uantwerpen.sd.project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeeklyPlan implements Subject {
    private final Map<String, DayPlan> days = new HashMap<>();

    private final Set<Observer> observers = new HashSet<>();


    public WeeklyPlan() {

    }

    public DayPlan getDay(String name) {
        return this.days.get(name);
    }

    public void setDay(String name, DayPlan day) {
        this.days.put(name, day);
        notifyObservers("new_dayplan",this.days.get(name));
    }
    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }
    @Override
    public void notifyObservers(String event, Object payload) {
        for (Observer o : this.observers) {
            o.update(event, payload);
        }
    }
}
