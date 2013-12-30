import java.io.IOException;
/**
 *
 * @author Braden Ericson and Nicole Lopez
 *
 * The GUIDriver generates the GUI version of the
 * Sawin International Bank Simulation.
 *
 *
 *
 */
public class GUIDriver
{
    public static void main(String[] args)
    {

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
					SawinGraphics gui = new SawinGraphics();
					gui.createAndShowGUI();

				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }//main
}//class