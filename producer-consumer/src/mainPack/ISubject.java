package mainPack;

public interface ISubject {

    /*
     * connects a machine to the queue
     * */
     void addMachine(IObserver m);
     /*
     update system
      */
     void notifyObserver();

}
