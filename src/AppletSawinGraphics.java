import java.awt.event.MouseAdapter;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;



/**
 *
 * @author Braden Ericson
 *
 * The AppletSawinGraphics class is used to create an applet version of the GUI
 * for the SawinInternationalBank.
 *
 */
public class AppletSawinGraphics extends JApplet implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JButton button1;
	private JButton button2;
	private JSpinner spinner;
	private JLabel imagePane = new JLabel();
	public JLabel musicIcon = new JLabel();
	public JLabel muteMusicIcon = new JLabel();
	private boolean driveInOpen = false;
	private final ImageIcon[] images = {new ImageIcon("SawinInternational.png"),new ImageIcon("SawinInternationalDriveThru.png")};
	public Music music = new Music("SawinInternational.wav");
	private JTextArea output = new JTextArea("Output will show here...");
 
	public void init()
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					try 
					{
						//creates the GUI for the application
						createAndShowGUI();
					
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			});
		}
		catch(Exception e)
		{
			System.err.println("The Applet did not run correctly");
		}
	}
	
	
	/**
	 * generates the GUI and shows it on the screen.
	 *
	 * @throws IOException
	 */
	public void createAndShowGUI() throws IOException
	{
		this.imagePane.setIcon(images[0]);
		this.imagePane.setVisible(true);
		JLayeredPane lpane = new JLayeredPane();
		JPanel contentPane = new JPanel();
		//JFrame frame = new JFrame("Sawin International");


		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//creating the layers
		imagePane.setBounds(0, 0, 910, 497);
		contentPane.setBounds(0, 0, 910, 497);
		contentPane.setOpaque(false);
		addComponentsToPane(contentPane);

		//set the layerdPaned
		lpane.setBounds(0, 0, 910, 497);
		lpane.add(imagePane, new Integer(0), 0);
		lpane.add(contentPane, new Integer(1), 0);
		add(lpane);
		

		setSize(910, 497);
		setContentPane(lpane);
		setVisible(true);
		setName("Sawin International");
		
		music.start();
	}

	/**
	 *
	 * The addComponentsToPane method is used to add the main content to the frame.
	 * All button and text functionality is added to the passed Container.
	 *
	 *
	 *
	 * @param pane	the container that will hold the main content
	 */
	public void addComponentsToPane(Container pane)
	{
		//initialize all of the components
		this.musicIcon 		= new JLabel();
		this.button1 		= new JButton("Begin Bank Simulation");
		this.button2 		= new JButton("Quit Bank Simulation");
		JLabel tellerText	= new JLabel("Number of Tellers");
		JLabel driveInText 	= new JLabel("Drive-In Open?");
		JLabel yes		   	= new JLabel("Yes");
		JLabel no		   	= new JLabel("No");
		Font newLabelFont  	= new Font(tellerText.getFont().getName(),tellerText.getFont().getStyle(),20);
		SpinnerModel model 	= new SpinnerNumberModel(1, 0, 100, 1); //begin, low, high, step
		this.spinner 		= new JSpinner(model);
		ButtonGroup group 	= new ButtonGroup();
		JRadioButtonMenuItem radio1 = new JRadioButtonMenuItem("Yes", false);
		JRadioButtonMenuItem radio2 = new JRadioButtonMenuItem("No", true);
		JScrollPane scrollPane 		= new JScrollPane(this.output);


	//absolute layout positioning
		pane.setLayout(null);


		
	//create the Music Icon
		this.musicIcon.setIcon(new ImageIcon("musicIcon.png"));
		this.musicIcon.setBounds(5, 5, 21,25);
		this.musicIcon.addMouseListener(new MouseAdapter()
		{
			/**
			 * Will be used to mute music in future update.
			 */
			public void mouseClicked(MouseEvent me) 
			{
				//mutes or unmutes the music
				music.muteMusic();
			}
		});
		




	//set the bounds of all the components
		this.button1.setBounds(725, 20, 175, 30);	//Begin Bank Simulation
		this.button2.setBounds(725, 70, 175, 30);	//Quit  Bank  Simulation
		tellerText.setBounds(725, 125, 190, 30); 	//"Number Of Tellers"
		this.spinner.setBounds(825, 160, 50,20);	//input field
		driveInText.setBounds(735, 200, 190, 30); 	//"Drive-In Open?"
		yes.setBounds(750,235, 40, 20);				//"Yes"
		radio1.setBounds(780, 235, 20, 20);			//YES radio input button
		no.setBounds(820,235, 40, 20);				//"No"
		radio2.setBounds(850, 235, 20, 20);			//NO radio input button



	//sets the font of the Labels
		tellerText.setFont(newLabelFont);
		driveInText.setFont(newLabelFont);

	//formats the output field
		this.output.setLineWrap(true);
		this.output.setWrapStyleWord(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(640, 300, 250, 150);

	//format the radio buttons
		radio1.setOpaque(false);
		radio2.setOpaque(false);
		group.add(radio1);
		group.add(radio2);

	//add eventListeners to the buttons of importance
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		

	//add all components to the passed container
		pane.add(this.musicIcon);
		pane.add(this.button1);
		pane.add(this.button2);
		pane.add(tellerText);
		pane.add(driveInText);
		pane.add(this.spinner);
		pane.add(yes);
		pane.add(radio1);
		pane.add(no);
		pane.add(radio2);
		pane.add(scrollPane);

	}

	/**
	 * 	The actionPerformed method allows us to add functionality
	 * to the different buttons. All of the buttons use the same
	 * function. To determine which button was pressed, the
	 * <code>.getActionCommand()</code> method is used.
	 *
	 * @Override actionPerformed
	 * @param e	the ActionEvent that is being handled
	 */

	public void actionPerformed(ActionEvent e)
	{

		if(e.getActionCommand() == "Yes") 	 //if radio button "Yes" is selected
		{
			this.imagePane.setIcon(images[1]); //swap picture
			this.driveInOpen = true;			//Program will run with drive-in available
		}
		else if(e.getActionCommand() == "No")//if radio button "no" is selected
		{
			this.imagePane.setIcon(images[0]); 	//swap picture
			this.driveInOpen = false;			//Program will run with drive-in unavailable
		}
		else if(e.getActionCommand() == "Quit Bank Simulation") // user presses "Quit Bank Simulation" Button
		{
			 System.exit(1);	//quits the GUI program
		}
		else if(e.getActionCommand() == "Begin Bank Simulation") //user presses "Begin Bank Simulation" Button
		{
			new BankSimulator(Integer.parseInt(this.spinner.getValue().toString()),this.driveInOpen, this.output); //run bank program
		}


	}//actionPerformed

}//end Class
