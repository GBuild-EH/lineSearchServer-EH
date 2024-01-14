package cop2805;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class LineClient {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run() {
				constructGUI();
			}
		}); 
		
	}
	
	private static void constructGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		LClientGui frame = new LClientGui();
		frame.setVisible(true);
	}	
}

class LClientGui extends JFrame {
	public JTextField toSend;
	public JList<String> received;
	public JButton connect;
	public DefaultListModel<String> listModel = new DefaultListModel<String>();
	
	public LClientGui () {
		super();
		init();
	}
	
	private void init() { //main window components
		toSend = new JTextField();
		received = new JList<String>(listModel);
		connect = new JButton("Connect");
		connect.addActionListener(new ClientListener(this));
		
		//assembling window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Line Searcher Client");
		this.setLayout(new GridLayout(3,2));
		this.add(new JLabel("Server request:"));
		this.add(toSend);
		toSend.setText("Enter target line number or shutdown command");
		this.add(new JLabel("Server Response:"));
		this.add(received);
		this.add(new JLabel());
		this.add(connect);
		int frameWidth = 1000;
		int frameHeight = 500;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) (screenSize.getWidth()/2) - frameWidth,
				(int) (screenSize.getHeight() / 2) - frameHeight,
				frameWidth, frameHeight);
	}
} 

class ClientListener implements ActionListener {
	LClientGui cFrame;
	String toSend;
	
	public ClientListener (LClientGui frame) {
		cFrame = frame;
	}
	
	public void actionPerformed (ActionEvent e) {
		cFrame.listModel.clear();
		toSend = cFrame.toSend.getText() + "\n";
        int port = 1236;
		try {
			Socket sock = new Socket("127.0.0.1", port);
			OutputStream out = sock.getOutputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out.write(toSend.getBytes());
			
			String serverString = in.readLine();
			while (serverString != null) {
				cFrame.listModel.addElement(serverString);
				serverString = in.readLine();
			}
			sock.close();			
			
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
