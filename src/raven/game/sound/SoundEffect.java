package raven.game.sound;

import java.io.*;
import javax.sound.sampled.*;


	/** Java class that implements sounds for Raven game.
	 * Plays a short clip every time a bot spawns and when a bot dies
	 * @author Tanya
	 *
	 */
	public class SoundEffect{
		 private Clip clip;
		 public SoundEffect(String fileName) {
		        // specify the sound to play
		        // (assuming the sound can be played by the audio system)
		        // from a wave File
		        try {
		            File file = new File(fileName);
		            if (file.exists()) {
		                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
		             // load the sound into memory (a Clip)
		                clip = AudioSystem.getClip();
		                clip.open(sound);
		                //clip.start();
		            }
		            else {
		                throw new RuntimeException("Sound: file not found: " + fileName);
		            }
		     
		        } catch (UnsupportedAudioFileException e) {
		        	e.printStackTrace();
		        } catch (IOException e) {
		        	e.printStackTrace();
		        } catch (LineUnavailableException e) {
		        	e.printStackTrace();
		        }
		 }
		 	
		 public void play(){
		        if (clip.isRunning())
		            clip.stop();   // Stop the player if it is still running
		        clip.setFramePosition(0);  // Must always rewind!
		        clip.start();
		    }
}



	 
		   

	   