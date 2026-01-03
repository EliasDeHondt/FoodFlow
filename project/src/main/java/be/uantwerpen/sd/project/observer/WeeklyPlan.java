/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.uantwerpen.sd.project.DayPlan;

public class WeeklyPlan implements Subject {
    private final Map<String, DayPlan> days = new HashMap<>();

    private final Set<Observer> observers = new HashSet<>();

    public WeeklyPlan() {
        String[] dayNames = {"mon","tue","wed","thu","fri","sat","sun"};
        for (int col = 0; col < 7; col++) {
            days.put(dayNames[col], new DayPlan());
        }
    }

    public DayPlan getDay(String name) {
        return this.days.get(name);
    }

    public List<DayPlan> getDays() {
        return new ArrayList<>(this.days.values());
    }

    public void setDay(String name, DayPlan day) {
        this.days.put(name, day);
        notifyObservers("new_dayplan",this);
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