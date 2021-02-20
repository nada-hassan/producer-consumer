package GuiPack;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import mainPack.App;

public class Home extends JFrame {
	Point corner=new Point(0,0);
	JPanel panel;
	
	 App app = new App();
	
	ArrayList<JLabel> machines=new ArrayList<>();
	ArrayList<JLabel> queues=new ArrayList<>();
	ArrayList<connection> links=new ArrayList<>();
		
	private JTextField from;
	private JTextField to;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setTitle("Producer-Consumer Simulation");
		 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 50, 1095, 722);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 225));

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		
	
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 500, 1));
		spinner.setBounds(942, 509, 56, 44);
		contentPane.add(spinner);
		
		
		JButton start_btn = new JButton("Start");
		start_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("no of threads replay: "+Thread.activeCount());
				int max;
				if(queues.size()==0||machines.size()==0||links.size()==0) {
					return;
				}
				try {
					max = (int) spinner.getValue();
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Wrong Input !");
					return;
				}

				app.createSystem(machines,queues,links,max);
				app.begin();

				System.out.println("no of threads after replay: "+Thread.activeCount());
			}
		});
		start_btn.setBackground(new Color(169, 169, 169));
		start_btn.setOpaque(true);
		start_btn.setForeground(new Color(128, 0, 128));
		start_btn.setFont(new Font("Tahoma", Font.BOLD, 15));
		start_btn.setBounds(881, 577, 85, 38);
		contentPane.add(start_btn);
		
		JLabel Queue = new JLabel("ADD Queue"); //old
		Queue.setFont(new Font("Tahoma", Font.BOLD, 15));
		Queue.setHorizontalAlignment(SwingConstants.CENTER);
	
		Queue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createQueue();

			}
		});

		
		Queue.setBackground(new Color(204, 153, 204));
		Queue.setOpaque(true);
		Queue.setBounds(920, 29, 116, 82);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		Queue.setBorder(border);
		contentPane.add(Queue);
		
		panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.setBackground(new Color(245, 245, 245));
		panel.setBorder(new LineBorder(Color.GRAY, 2, true));
		panel.setBounds(10, 65, 849, 610);
		contentPane.add(panel);

		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("number \r\nof products");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setLabelFor(spinner);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(897, 467, 150, 32);
		contentPane.add(lblNewLabel_1);
		
		JButton btnReplay = new JButton("Replay");
		//btnReplay.setEnabled(false);
		btnReplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("no of threads replay: "+Thread.activeCount());
				app.replay();
				System.out.println("no of threads after replay: "+Thread.activeCount());
			}
		});
		btnReplay.setOpaque(true);
		btnReplay.setEnabled(false);
		btnReplay.setForeground(new Color(255, 0, 0));
		btnReplay.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnReplay.setBackground(new Color(169, 169, 169));
		btnReplay.setBounds(976, 577, 82, 38);
		contentPane.add(btnReplay);
		
		JLabel lblAddMachine = new JLabel("ADD Machine");
		lblAddMachine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createMachine();
			}
		});
		lblAddMachine.setOpaque(true);
		lblAddMachine.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddMachine.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddMachine.setBackground(new Color(204, 153, 102));
		lblAddMachine.setBounds(920, 143, 116, 82);
		border = BorderFactory.createLineBorder(Color.BLACK, 2);
		lblAddMachine.setBorder(border);
		 
		contentPane.add(lblAddMachine);
		
		JLabel lblConnections = new JLabel("Connections");
		lblConnections.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnections.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblConnections.setBounds(897, 260, 150, 60);
		contentPane.add(lblConnections);
		
		JLabel lblStart = new JLabel("From");
		lblStart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblStart.setBounds(881, 327, 56, 32);
		contentPane.add(lblStart);
		
		from = new JTextField();
		from.setBounds(942, 330, 96, 32);
		contentPane.add(from);
		from.setColumns(10);
		
		to = new JTextField();
		to.setColumns(10);
		to.setBounds(940, 372, 96, 32);
		contentPane.add(to);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTo.setBounds(891, 369, 33, 32);
		contentPane.add(lblTo);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String end=to.getText().toLowerCase().strip();
				String start=from.getText().toLowerCase().strip();
				if(end.length()<2||start.length()<2||start.charAt(0)==end.charAt(0)
						|| (start.charAt(0)!='q' && start.charAt(0)!='m') ||
						(end.charAt(0)!='q' && end.charAt(0)!='m')) {
					JOptionPane.showMessageDialog(null, "Wrong Input !");
					return;
				}
				try {
				int istart=Integer.parseInt(start.substring(1));
				int iend=Integer.parseInt(end.substring(1));
				if(istart<0||(istart>=machines.size()&&start.charAt(0)=='m'  )||(istart>=queues.size()&&start.charAt(0)=='q')
						||iend<0||(iend>=machines.size()&&end.charAt(0)=='m'  )||(iend>=queues.size()&&end.charAt(0)=='q')) {
					
					JOptionPane.showMessageDialog(null, "Wrong Input !");
					return;
					
				}
				
				//set connections 
				
				if(start.charAt(0)=='m') {
					links.add(new connection(istart,iend,true));
					drawLink(istart,iend,true);
				}
				else {
					links.add(new connection(iend,istart,false));
					drawLink(iend,istart,false);
				}
				
				
		
				
				
				
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Wrong Input !");
					return;
				}

			}
		});
		btnConnect.setOpaque(true);
		btnConnect.setForeground(new Color(0, 102, 0));
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnConnect.setBackground(new Color(169, 169, 169));
		btnConnect.setBounds(942, 430, 96, 27);
		contentPane.add(btnConnect);
		

			
			
		
		
		JButton btnClearAll = new JButton("CLEAR ALL");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				links.clear();
				machines.clear();
				queues.clear();
				panel.removeAll();
				panel.repaint();
			}
		});

		btnClearAll.setBounds(10, 17, 122, 38);
		contentPane.add(btnClearAll);
		btnClearAll.setOpaque(true);
		btnClearAll.setForeground(new Color(165, 42, 42));
		btnClearAll.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClearAll.setBackground(new Color(211, 211, 211));
		
		app.setBtns(start_btn, btnReplay,btnClearAll);



		 
	}
	
	//draw link
	public void drawLink(int m , int q,boolean d) {
		Graphics g= panel.getGraphics();
		JLabel l1=machines.get(m);
		JLabel l2=queues.get(q);
		
		g.setColor(new Color(165, 42, 42));
		
        if ( l1.getX() > l2.getX())
        {
            g.drawLine(l1.getX()  , l1.getY() + l1.getHeight() / 2 , l2.getX() + l2.getWidth() , l2.getY()  +  l2.getHeight() / 2);
            if(d) {//machine to queue
            	System.out.println("m to q");
            	drawHead(l1.getX(), l1.getY() + l1.getHeight() / 2,
            			l2.getX() + l2.getWidth(),l2.getY()  +  l2.getHeight() / 2);
            }
            else {
            	System.out.println("q to m");
            	drawHead(l2.getX() + l2.getWidth(),l2.getY()  +  l2.getHeight() / 2,
            			l1.getX(), l1.getY() + l1.getHeight() / 2);
            }
            
           
        }
        else if (l1.getX() < l2.getX())
        {
            g.drawLine(l2.getX() , l2.getY() + l2.getHeight() / 2 ,l1.getX() + l1.getWidth() ,l1.getY()  + l1.getHeight() / 2);
            
            if(d) {//machine to queue
            	System.out.println("m to q");
            	drawHead(l1.getX() + l1.getWidth() ,l1.getY()  + l1.getHeight() / 2,
            			l2.getX() , l2.getY() + l2.getHeight() / 2 );
            }
            else {
            	System.out.println("q to m");
            	drawHead(l2.getX() , l2.getY() + l2.getHeight() / 2 ,
            			l1.getX() + l1.getWidth() ,l1.getY()  + l1.getHeight() / 2);
            }
            
        }
        else if (l1.getY() > l2.getY())
        {
            g.drawLine(l1.getX() + l1.getWidth() / 2 , l1.getY() , l2.getX() + l2.getWidth() / 2, l2.getY() + l2.getHeight());
            
            if(d) {//machine to queue
            	System.out.println("m to q");
            	drawHead(l1.getX() + l1.getWidth() / 2 , l1.getY(),
            			 l2.getX() + l2.getWidth() / 2, l2.getY() + l2.getHeight());
            }
            else {
            	System.out.println("q to m");
            	drawHead( l2.getX() + l2.getWidth() / 2, l2.getY() + l2.getHeight(),
            			l1.getX() + l1.getWidth() / 2 , l1.getY());
            }
        }
        else if (l1.getY() < l2.getY())
        {
            g.drawLine(l2.getX() + l2.getWidth() / 2 ,l2.getY() ,l1.getX() + l1.getWidth() / 2, l1.getY() + l1.getHeight());
            
            if(d) {//machine to queue
            	System.out.println("m to q");
            	drawHead(l1.getX() + l1.getWidth() / 2, l1.getY() + l1.getHeight(),
            			l2.getX() + l2.getWidth() / 2 ,l2.getY());
            }
            else {
            	System.out.println("q to m");
            	drawHead(l2.getX() + l2.getWidth() / 2 ,l2.getY(),
            			l1.getX() + l1.getWidth() / 2, l1.getY() + l1.getHeight());
            }
        }
        
        
	}
	
	//draw arrow head
	//the arrow goes from 1 to 2
	public void drawHead(int x1,int y1,int x2,int y2) {
		Graphics g= panel.getGraphics();
		g.setColor(new Color(165, 42, 42));
        int d1=15,h=5;
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d1, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        g.fillPolygon(xpoints, ypoints, 3);
        
	}
	
	//create new label for queue
	private void createQueue() {
		JLabel sampleQueue = new JLabel("Q"+queues.size());
		panel.add(sampleQueue);
		sampleQueue.setFont(new Font("Tahoma", Font.BOLD, 12));
		sampleQueue.setHorizontalAlignment(SwingConstants.CENTER);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1,true);
		sampleQueue.setBorder(border);
		sampleQueue.setOpaque(true);
		sampleQueue.setBackground(new Color(255, 204, 0));
		sampleQueue.setBounds(666, 36, 86, 28);
		
		
		
		sampleQueue.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point curr=e.getPoint();
				corner.translate(curr.x, curr.y);
				sampleQueue.setBounds(corner.x, corner.y, 86, 28);
				repaint();
				
			}
		});
		sampleQueue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				for(int i=0;i<links.size();i++) {
					connection curr=links.get(i);
					drawLink(curr.machine,curr.queue,curr.direction);
				}
			}
		});
		
		queues.add(sampleQueue);
	}

	//create new label for machine
	private void createMachine() {
		JLabel sampleMachine = new JLabel("M"+machines.size());
		panel.add(sampleMachine);
		sampleMachine.setBackground(new Color(255, 255, 255));
		sampleMachine.setOpaque(true);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1,true);
		sampleMachine.setBorder(border);
		sampleMachine.setBounds(666, 36, 55, 55);
		sampleMachine.setFont(new Font("Tahoma", Font.BOLD, 12));
		sampleMachine.setHorizontalAlignment(SwingConstants.CENTER);

		
		
		
		sampleMachine.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point curr=e.getPoint();
				corner.translate(curr.x, curr.y);
				sampleMachine.setBounds(corner.x, corner.y, 55, 55);
				repaint();
				
			}
		});
		
		sampleMachine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				for(int i=0;i<links.size();i++) {
					connection curr=links.get(i);
					drawLink(curr.machine,curr.queue,curr.direction);
				}

			}
		});

		machines.add(sampleMachine);
	}
	public void changeColor() {
		machines.get(0).setBackground(Color.BLACK);
		try {
			Thread.sleep(2000);
			machines.get(0).setBackground(Color.yellow);
			Thread.sleep(2000);
			machines.get(0).setBackground(Color.RED);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}