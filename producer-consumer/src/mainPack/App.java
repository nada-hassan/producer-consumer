package mainPack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import GuiPack.connection;

public class App {

    private Map<Color, Integer> ProductsMap = new HashMap<>();
    private ArrayList<Machine> m = new ArrayList<>();  //list of machines
    private ArrayList<QueueP>  QP= new ArrayList<>(); //list of queues
    private ArrayList<IProduct> products =new ArrayList<>();
    ArrayList<Thread> res=new ArrayList<>();
    private int max ; //number of products
    private ReentrantLock lock = new ReentrantLock(); // shared key
    private CareTaker care = new CareTaker();
    private JButton strtBtn, rplyBtn,clearBtn;
    
   
    public void setBtns(JButton start, JButton replay,JButton clear) {
    	this.strtBtn = start;
    	this.rplyBtn = replay;
    	this.clearBtn=clear;
    }
     public void begin(){
    	 new Thread(new Runnable() {
    		 boolean flag=true;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				strtBtn.setEnabled(false);
				rplyBtn.setEnabled(false);
				clearBtn.setEnabled(false);
				while(flag) {
		         QueueP qin  = QP.get(0);
		         generateP();
		         System.out.println(products.size());
		         createThreads();

		        //generate random products with random rate
		         for(int i = 0; i < max; i++){
		            IProduct product=products.get(i);
		            int time =(int) ((Math.random() * (7000)) + 3000);  //range 3000 , 10000
		             //int time=1000;
		             System.out.println("generate new product : "+time);
		             qin.add(product);
		             synchronized (lock) {
		                 lock.notifyAll();
		             }
		           try {
		                saveState(product,time);
		                Thread.sleep(time);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		         
		        flag= endThreads();
		        strtBtn.setEnabled(true);
		        rplyBtn.setEnabled(true);
		        clearBtn.setEnabled(true);
				}

				
			}
    		 
    	 }).start();
    }
     public boolean endThreads() {
    	 QueueP qout = QP.get(QP.size()-1);
         while(!qout.getEnd(max)){
             try {
                 Thread.sleep(1000);
                 
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         int j=0;
         for(int i=0;i<m.size();i++) {
         	this.m.get(i).stopThread();
         	this.res.get(j++).interrupt();
         	
         }
         for(int i=0;i<QP.size();i++) {
         	this.QP.get(i).stopThread();
         	this.res.get(j++).interrupt();
         }
         
         return false;
     }
    public void createSystem(ArrayList<JLabel>lmachine,ArrayList<JLabel> lqueue,ArrayList<connection> links,int max){
    	this.care.reset();
    	this.QP.clear();
    	this.m.clear();
    	this.products.clear();
    	this.ProductsMap.clear();
    	this.max=max;
    	//create machines
    	for(int i=0;i<lmachine.size();i++) {
    		this.m.add(new Machine(i,lock,lmachine.get(i)));
    	}
    	
    	//create queues
    	for(int i=0;i<lqueue.size();i++) {
    		this.QP.add(new QueueP(lock,i,lqueue.get(i)));
    	}
    	
    	//set connections
    	for(int i=0;i<links.size();i++) {
    		connection curr=links.get(i);
    		if(curr.direction) {//machine to queue
    			this.m.get(curr.machine).setOutput(this.QP.get(curr.queue));
    		}
    		else {//queue to machine
    			this.QP.get(curr.queue).addMachine(this.m.get(curr.machine));
    		}
    	}

    }
    private void createThreads(){
        	res.clear();
         //add machines
         for(int i=0;i<m.size();i++){
        	
             res.add( new Thread(m.get(i)));
         }
         //add queues
        for(int i=0;i<QP.size();i++){
        	
            res.add( new Thread(QP.get(i)));
        }
        //start thread
        for(int i=0;i<res.size();i++){
            res.get(i).setDaemon(true);
            res.get(i).start();
        }
    }
    public void replay(){
    	
    	new Thread(new Runnable() {
    		boolean flag=true;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				strtBtn.setEnabled(false);
				rplyBtn.setEnabled(false);
				clearBtn.setEnabled(false);
		         //reset system
				while(flag) {
		        for(int i=0;i<QP.size();i++) {
		        	QP.get(i).reset();
		        }
		        for(int i=0;i<m.size();i++) {
		        	m.get(i).reset();
		        }
		        createThreads();

		        QueueP qin  = QP.get(0);
		        for(int i = 0; i < max; i++){
		            Memento current= care.getNext(i);
		            IProduct product= current.getProduct();
		            int time =current.getTime();  //range 3000 , 10000
		            System.out.println("generate new product : "+time);
		            qin.add(product);
		            synchronized (lock) {
		                lock.notifyAll();
		            }
		            try {
		                Thread.sleep(time);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }

		        flag=endThreads();
		        strtBtn.setEnabled(true);
		        rplyBtn.setEnabled(true);
		        clearBtn.setEnabled(true);
				}
				
			}
    		
    	}).start();


    }
    // public void setState(String state){ this.state=state;}
    //public String getState(){return this.state;}
    public void saveState(IProduct product,int time){
         care.add(new Memento(product,time));
    }
   /* public void getStatefromM(Memento memento){
         this.state=memento.getState();
    }*/
    public void generateP(){
        for(int i=0;i<max;i++) {
            Random rand = new Random();
            float r, g, b;
            Color c;
            do {
                r = rand.nextFloat();
                g = rand.nextFloat();
                b = rand.nextFloat();
                c = new Color(r, g, b);
            } while (this.ProductsMap.containsKey(c));
            ProductsMap.put(c, i);
            this.products.add(new Product(c,i));
        }

    }
}