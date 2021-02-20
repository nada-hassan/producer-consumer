package GuiPack;

public class connection {
	public int machine;
	public int queue;
	public boolean direction; //true : machine to queue         false:queue to machine
	public connection(int m,int q , boolean d ){
		this.machine=m;
		this.queue=q;
		this.direction=d;
		
	}
}
