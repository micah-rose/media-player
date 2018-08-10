/***********************************************
* Authors: Micah Lund, Cris Cooper, Kyla Kunz
*
* Group Project - Music Player
************************************************/

import java.net.URL;
import java.io.IOException;

import javax.sound.sampled.Mixer;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioInputStream;

public class Player {
   
   static Mixer mixer;
   static Clip clip;
   
   private static String[] songs = {"Africa.wav", "Hurricane.wav", "WeWillRockYou.wav", "Forsaken.wav", "WishYouWereHere.wav"};

   public static void player(int action, int track) {
       
      Mixer.Info[] mixInfo = AudioSystem.getMixerInfo();
      mixer = AudioSystem.getMixer(mixInfo[0]);
      
      DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
      try {
         clip = (Clip)mixer.getLine(dataInfo);
      }
      
      catch(LineUnavailableException lue) { lue.printStackTrace(); }
      
      try {
         
         URL soundURL = Player.class.getResource(songs[track]);
         AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
         clip.open(audioStream);
      }
      
      catch(LineUnavailableException lue) { lue.printStackTrace();}
      catch(UnsupportedAudioFileException uafe) { uafe.printStackTrace(); }
      catch(IOException ioe) { ioe.printStackTrace(); }
      
      clip.stop();
      
      if (action == 1) {      
         clip.start();
         if (clip == null) {
            track++;
            clip.start();
         }      
      } else if (action==2) {
         clip.stop();
         clip.close();
      }
   }
   
   public static String getTrackList() {
      System.out.println("Tracks in playlist: ");
      String trackList = null;
      for(int i = 0; i < songs.length; i++) {         
         System.out.println((i + 1) + ". " + songs[i]);
         trackList = (i + 1) + ". " + songs[i];
      }
      return trackList;
   }
   
   public static String getCurrentSong(int track) {
      return songs[track];
   }
   
   public static int getSongArrayLength() {
      return songs.length;
   }
}