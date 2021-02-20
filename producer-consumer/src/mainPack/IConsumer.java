package mainPack;

public interface IConsumer { //machine

    /*
    * to set the output queue
    * */
    public void setOutput(IProducer output);
    /*
    * to set the current consumed product
    * */
    public void setP(IProduct p);
    /*
    * return true if machine is not processing, false otherwise
    * */
    public boolean getState();
    /*
    * processes the current set product
    * */
    public void consume() throws InterruptedException;

}
