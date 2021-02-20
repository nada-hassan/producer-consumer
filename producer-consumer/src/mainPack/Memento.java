package mainPack;
public class Memento {
    private IProduct product;
    private int time; //sleep after generating this product
    public Memento(IProduct product,int time){
        this.product=product;
        this.time=time;
    }
    public IProduct getProduct(){
        return product;
    }
    public int getTime(){
        return time;
    }
}
