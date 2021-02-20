package mainPack;

import java.util.ArrayList;

public class CareTaker {
    private ArrayList<Memento> mementoList=new ArrayList<>();
    
    public void add(Memento state){
        mementoList.add(state);
    }
    public Memento getNext(int index){
        if(index>=mementoList.size()){
            return null;
        }
        return mementoList.get(index++);
    }
    
    public void reset() {
    	this.mementoList.clear();
    }

}
