package mainPack;

import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Machine implements Runnable, IConsumer,IObserver{
    private int id;
    private int time;
    private Color c;
    private JLabel machine;
    private boolean flag=true;
    //private ArrayList<Queue> input;
    private IProducer output;
    private IProduct p;
    public ReentrantLock lock;

    public Machine(int id, ReentrantLock lock,JLabel l){
        this.time = (int) ((Math.random() * (7000)) + 3000); //range 3000 , 10000
        this.c = new Color(255,255,255);
        this.p = null;
        this.machine=l;
        this.lock = lock;
        this.id=id;
    }

    public Machine(int id, ReentrantLock lock,int time){
        this.time=time;
        this.c = new Color(255,255,255);
        this.p = null;
        this.lock = lock;
        this.id=id;
    }
    public void reset() {
    	this.c = new Color(255,255,255);
    	this.flag=true;
    	
    }

    public void setOutput(IProducer output){
        this.output = output;
    }
    public int getTime(){return this.time;}
    public void setP(IProduct p){
        synchronized (lock){
            this.p = p;
            lock.notify();
        }
    }

    public boolean getState(){
        if(this.p == null){
            return true;
        }else {
            return false;
        }
    }

    public void consume() throws InterruptedException {
       // System.out.println(">>>consume");
        synchronized (lock) {
            while (this.p == null) {
                // System.out.println("no product to consume, you have to wait");
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
        }
               // System.out.println("start processing : " + p );

                this.c = p.getColor();
                this.machine.setBackground(this.c);
                System.out.println("Color: +"+this.c+" thread sleeps for " + this.time + " machine "+this.id);
                Thread.sleep(this.time); //processing time
                System.out.println("thread wakes up after " + this.time +"machine "+this.id);
                this.output.add(this.p);
               // System.out.println("end processing: " + p);
                this.p = null;
                this.c = new Color(255,255,255);
                this.machine.setBackground(this.c);
                try {
                Thread.sleep(700);
                }catch(Exception e) {
                	System.out.println("catched");
                }
                synchronized (lock) {
                    lock.notifyAll();
                }


    }




    @Override
    public void run() {
        try {
            while (flag){
                //System.out.println("while loop entered");
                this.consume();

            }
        } catch (InterruptedException e) {
        		
            e.printStackTrace();
        }
    }
    
    public void stopThread() {
    	this.flag=false;	
    }
    
    
}
