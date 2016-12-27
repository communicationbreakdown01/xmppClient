/**
 * Created by Jessica Laxa on 18.12.2016.
 */
public interface ISubject {

    public void registerObserver(Observer o);
    public void removeObserver (Observer o);
    public void notifyObservers();

}
