package mainPack;

public interface IProducer { //Queue

    /*
    * adds a product to the queue
    * */
    public void add(IProduct p);
    /*
    * gives a product to a random machine
    * */
    public void produce() throws InterruptedException;
}
