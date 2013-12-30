
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

/**
 * @authors Braden Ericson and Nicole Lopez
 *
 * This class is a simulator that starts the Sawin International bank simulation.
 * 
 * 	**THIS DRIVER IS RUN THROUGH THE SAWINGRAPHICS CLASS. IT CANNOT BE RUN BY ITSELF.**
 *
 */
public class BankSimulator 
{

	public BankSimulator(int tellers, boolean driveIn, JTextArea textarea) 
	{
		JFileChooser chooser = new JFileChooser();
		String result = "";
		try 
		{
			if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) 
			{
				textarea.setText("ERROR:\nPlease select an input file.");
				throw new Error("Input file not selected");
			}
			File customers = chooser.getSelectedFile();

			int numberOfTellers = tellers;
			boolean DriveInOpen = driveIn;
			SawinInternational fluffy = new SawinInternational(numberOfTellers,customers, DriveInOpen);
			fluffy.openBank();
			
			result +="Global time at the end of the day is:\n"+ fluffy.getGlobalTime() + " seconds\n\n";
			result +="Total number of customers helped is:\n"+ fluffy.getNumberOfCustomersHelped()+"\n\n";
			result +="Average customer waiting time is:\n"	+ fluffy.getAverageCustomerWaitingTime() + " seconds\n\n";
			result +="Standard deviation of the waiting time of all customers is:\n"+ fluffy.getStandarDeviationOfWaitTime()+ " seconds\n\n";
			
			System.out.println("Global time at the end of the day is "+ fluffy.getGlobalTime() + " seconds");
			System.out.println("Total number of customers helped is "+ fluffy.getNumberOfCustomersHelped());
			System.out.println("Average customer waiting time is "	+ fluffy.getAverageCustomerWaitingTime() + " seconds");
			System.out.println("Standard deviation of the waiting time of all customers is: "+ fluffy.getStandarDeviationOfWaitTime()+ " seconds");
		
			for (int i = 0; i < numberOfTellers; i++) 
			{
				result+="Teller " + i + " helped:\n"+ fluffy.getTellerNumberOfCustomersHelped(i)+ " customers.\n\n";
				System.out.println("Teller " + i + " helped "+ fluffy.getTellerNumberOfCustomersHelped(i)+ " customers.");
			}
			for (int i = 0; i < numberOfTellers; i++) 
			{
				result += "Teller " + i + " was idle "	+ fluffy.getPercentIdlenessForTeller(i)	+ "% of the time.\n\n";
				System.out.println("Teller " + i + " was idle "	+ fluffy.getPercentIdlenessForTeller(i)	+ "% of the time.");
			}
			
			result +="Standard deviation of the idleness of all tellers is:\n"	+ fluffy.getStandardDeviationOfIdleTellers()+ " seconds\n";
			System.out.println("Standard deviation of the idleness of all tellers is: "	+ fluffy.getStandardDeviationOfIdleTellers()+ " seconds");
			textarea.setText(result);
		
		}
		catch (NumberFormatException nfe) 
		{
			System.out.println("Number of tellers must be an int value greater than 0");
			textarea.setText("NumberFormatException:\nNumber of tellers must be an int value greater than 0");
		} 
		catch (Exception e) 
		{
			System.err.println("A mysterious error occurred.");
			e.printStackTrace(System.err);
			textarea.setText(System.err.toString());
		}
	}// end constructor

}// end class