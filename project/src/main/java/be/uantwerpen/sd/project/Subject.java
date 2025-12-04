package be.uantwerpen.sd.project;

public interface Subject {
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(String event, Object payload);
}
