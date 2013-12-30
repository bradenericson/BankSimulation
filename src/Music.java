import java.io.File; 
import java.net.URI;
import java.net.URL;


import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

 /**
  *	The Music Class allows us to play music in the background of our project.
  * We can  stop, start, or mute the music with method calls.
  * 
  * We open our music file in a clip, and start the clip in a separate thread.
  *
  * @author Braden Ericson
  * 
  * 
  */

public class Music extends Thread 
{ 
 
    private String musicFileName;	//name of the music file
	private Clip clip = null;	//The structure used to hold and control the music 
	public boolean isPaused = false; //the pause boolean
    public boolean isMute = false; //the mute boolean
    
 
    /**
     * initializes the instance variables of our Music Class
     * 
     * @param wavfile the name of the music file
     */
    public Music(String wavfile) 
    { 
        musicFileName = wavfile;
    } 
    
    /**
     * The <code>pauseMusic</code> method allows us to pause the music at the current location.
     * 
     * calling the method again will resume the music at the current location. The same effect can
     * be achieved by calling the <code>startMusic</code> method.
     */
    public void pauseMusic()
    {
    	//toggle the isPaused boolean
    	this.isPaused = !this.isPaused;
    	
    	if(this.isPaused)
    	{
    		//pauses the current clip
    		this.clip.stop();	
    	}
    	else
    	{
    		//starts the clip again at the paused location
    		this.clip.start();
    	}
    	
    }
    /**
     * The <code>stopMusic</code> method stops the current song and sets the frame position back to 0.
     * 
     * When the music is started again after this method is called, it will start at the beginning of the song, rather
     * than the position in which it was stopped.
     */
    public void stopMusic()
    {
    	this.clip.stop();
    	this.clip.setFramePosition(0);
    }
    
    public void startMusic()
    {
    	this.clip.start();
    	this.isPaused = false;
    }
    
    /**
     * Mutes the Java Program.
     *  **This will mute ALL music being output from the program**
     *  Calling this method a second time will unmute the java program.
     *   
     */
    
    public void muteMusic()
    {
    	//toggle the mute boolean
    	this.isMute = !this.isMute;
    	
    	//grab the mixer information
    	Mixer.Info[] infos = AudioSystem.getMixerInfo();
    	
    	//for each mixer's information...
    	for (Mixer.Info info: infos) 
    	{
    		//grab the mixer
    	    Mixer mixer = AudioSystem.getMixer(info);
    	    
    	    //grab the lines associated with current mixer
    	    Line lines[] = mixer.getSourceLines();
    	    
    	    //for each of the current mixer's lines...
    	    for (Line line: lines)
    	    {
    	    	//grab the BooleanControl associated with the current line's mute property
    	    	BooleanControl bc = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
    	    	
    	    	//if the mute property was found
    	    	if (bc != null)
    	    	{
    	    		//sets the mute property
    	    		bc.setValue(this.isMute);
    	    	}
    	    			
    	    }//for each line in lines
    	    
    	}//for each mixer.info in infos
    }//updateMixers
    
 /**
  * Starts the thread and starts playing the music.
  */
    public void run() 
    { 
  	   AudioInputStream ais = null;
  	 
  	 	   try
  	 	   {
  	 		   //converts the filename into a usable URL
  	 		   URI uri = new File(this.musicFileName).toURI();
  	 		   URL url = uri.toURL();
  	 		   
  	 		   //gets the audioInputStream from the url
  	 		   ais = AudioSystem.getAudioInputStream(url);
  	 	   
  	 		   //assign the audioInputSteram to the clip 
  	 		   clip = AudioSystem.getClip();
  	 		   clip.open( ais );
  	 	    
  	 		   //set how many times to loop
  	 		   clip.loop(Clip.LOOP_CONTINUOUSLY);
  	 	   }
  	 	   catch(Exception e)
  	 	   {
  	 		   e.printStackTrace();
  	 		   return;
  	 	   }
 	    clip.start(); 
    } 
    
  
    
} 