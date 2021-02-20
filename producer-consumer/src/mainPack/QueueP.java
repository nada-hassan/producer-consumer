package mainPack;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;

public class QueueP implements Runnable,IProducer, ISubject {

    private Queue<IProduct> q;
    private int id;
    private boolean flag=true;
    private JLabel queue;
    private String name;
    private ArrayList<IConsumer> m;
    private ReentrantLock lock;
    int counter=0;
    public QueueP(ReentrantLock lock,int i,JLabel l){
        this.q = new LinkedList<>();
        this.m = new ArrayList<>();
        this.lock = lock;
        this.queue=l;
        this.name=l.getText();
        this.id=i;
    }
    
    public void reset() {
    	this.counter=0;
    	this.q.clear();
    	this.flag=true;
    	this.queue.setText(name);
    }

    public void addMachine(IObserver m){ //add new machines, needs to be set before running
        this.m.add((IConsumer) m);
    }

    public void add(IProduct p){
        synchronized (lock){

            this.q.add(p);
            counter++;
            if (this.m==null||this.m.size()==0) {
                System.out.println("counter" + q.size());
            }
            this.queue.setText(name+" Size : "+q.size());
            try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
           this.notifyObserver();
        }
    }
    public boolean getEnd(int max){
        return counter == max;
    }
    public void produce() throws InterruptedException {
        //System.out.println(">>> produce");
            synchronized (lock){
       
            	System.out.println("choose index in queue " + this.id);
            	int index = 0;
            	while(!this.m.get(index).getState()){
            		//System.out.println("before >> index = " + index);
            		index = (index+1)%this.m.size();
            		//System.out.println("after >>> index = " + index);
            		if(index == this.m.size()-1) {
            			try {
            			lock.wait();
            			}catch(Exception e) {
            				return;
            			}
            		}
            	}
            	System.out.println("index chosen in queue " + this.id + " >> " + index);
            	
                //Random rand = new Random();
                //int index = rand.nextInt(this.m.size());
               // System.out.println("index >> "+index);
                while (!this.m.get(index).getState()||this.q.size()==0){
                   // System.out.println("machine" + index + " cannot process, you have to wait"+" size :"+q.size()+" state: "+this.m.get(index).getState());
                	if(!flag) {
                		lock.notifyAll();
                		
                		return;
                	}
                	try {
                    lock.wait();
                	}catch(Exception e) {
                		return;
                	}
                }
                
                System.out.println("give to machine " + index+" size :"+this.q.size());
                //System.out.println(this.m.get(index));
                this.m.get(index).setP(this.q.remove());
                this.queue.setText(name+" Size : "+q.size());
                notifyObserver();
            }

    }
    public void notifyObserver(){
        this.lock.notifyAll();
    }

    @Override
    public void run() {
        try {
            while (flag){
                if(this.m == null||this.m.size() == 0){
                   // System.out.println("final queue : "+this.q.size());
                    break;
                }
                else {
                    //System.out.println("while loop produce");
                    this.produce();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void stopThread() {
    	this.flag=false;	
    }
}
