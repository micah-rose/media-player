/***********************************************
* Authors: Micah Lund, Cris Cooper, Kyla Kunz
*
* Group Project - Music Player
************************************************/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class PlayerGUI extends JFrame {
   
   private int trackNumber = 0;
   private boolean isPlaying = false;
   private boolean Pause = false;
   private long clipTime = 0;
   private boolean playPauseImage = true;
   Player player = new Player();
   
   JButton rewindButton = new JButton();
   JButton previousButton = new JButton();
   JButton playPauseButton = new JButton();
   JButton stopButton = new JButton();
   JButton nextButton = new JButton();
   JButton fastforwardButton = new JButton();
   JButton shuffleButton = new JButton();
   JPanel currentSongPanel = new JPanel();
   JLabel currentSong = new JLabel();

   public void GUIControlls() {
      PlayerGUI musicController = new PlayerGUI();
      musicController.setVisible(true);
      
      buttonActions();
      contentLayout(musicController);  
      controllerDefaults(musicController);   
   }
     
   private void buttonActions() {
   
      rewindButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
         
            if (player.clip.getMicrosecondPosition() > 5000000) {
               player.clip.setMicrosecondPosition(player.clip.getMicrosecondPosition() - 5000000);
            } else {
               player.clip.setMicrosecondPosition(0);
            }
         }
      });
    
      
      previousButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
         
            if (isPlaying) {
               player.clip.stop();
            }
            
            if (trackNumber == 0) {
               trackNumber = Player.getSongArrayLength()-1;
            } else {
               trackNumber -= 1;
            }
            
            player.player(1, trackNumber);
            isPlaying = true;
            Pause = false;
            currentSong.setText(player.getCurrentSong(trackNumber));
            
            playPauseImage = false;
            playPause();
         }
      });
      
      
      playPauseButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event){
            if (!isPlaying) {
               playPauseImage = false;
               playPause();

               player.player(1, trackNumber);
               isPlaying = true;
            }
            else {
               playPauseImage = true;
               playPause();
               
               if (!Pause) {            
                  clipTime = player.clip.getMicrosecondPosition();
                  Player.clip.stop();
                  Pause = true;
                  
                  playPauseImage = true;
                  playPause();

               } else {            
                  player.clip.setMicrosecondPosition(clipTime);            
                  player.clip.start();
                  Pause = false;
                  
                  playPauseImage = false;
                  playPause();
               }
            }
         } 
      });
      
      stopButton.addActionListener(new ActionListener() {         
         public void actionPerformed(ActionEvent event) {
            if (isPlaying) {
               player.clip.stop();
               player.player(2, trackNumber);
               isPlaying = false;
               
               playPauseImage = true;
               playPause();
               currentSong.setText("Stopped - " + (Player.getCurrentSong(trackNumber)));
            }
         }
      });
            
      nextButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {            
            if (isPlaying) {
               player.clip.stop();
            }
            
            if (trackNumber == Player.getSongArrayLength()-1) {
               trackNumber = 0;
            } else {
               trackNumber += 1;
            }
            
            player.player(1, trackNumber);
            isPlaying = true;
            Pause = false;
            currentSong.setText(Player.getCurrentSong(trackNumber));
            
            playPauseImage = false;
            playPause();
         }
      });
      
      fastforwardButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
         
            if (player.clip.getMicrosecondPosition() + 5000000 < player.clip.getMicrosecondLength()) {
               player.clip.setMicrosecondPosition(player.clip.getMicrosecondPosition() + 5000000);
            } else {
               player.clip.stop();
            }
         }
      });
      
      
      shuffleButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            
            if (isPlaying) {
               player.clip.stop();
            }
                       
            Random rand = new Random();
            int randNumber = rand.nextInt(Player.getSongArrayLength());
            trackNumber = randNumber;
            player.player(1, trackNumber);
            isPlaying = true;
            Pause = false;
            
            playPauseImage = false;
            playPause();
            currentSong.setText(Player.getCurrentSong(trackNumber));
         }
      });
   }
   
   private void contentLayout(PlayerGUI musicController) {
      JPanel content = new JPanel();
      
      previousButton.setIcon(new ImageIcon("previousButton.png"));
      previousButton.setBackground(Color.WHITE);
      
      rewindButton.setIcon(new ImageIcon("rewindButton.png"));
      rewindButton.setBackground(Color.WHITE);    
      
      stopButton.setIcon(new ImageIcon("stopButton.png"));
      stopButton.setBackground(Color.WHITE);
      
      fastforwardButton.setIcon(new ImageIcon("fastforwardButton.png"));
      fastforwardButton.setBackground(Color.WHITE);
      
      nextButton.setIcon(new ImageIcon("nextButton.png"));
      nextButton.setBackground(Color.WHITE);      
      
      shuffleButton.setIcon(new ImageIcon("shuffleButton.png"));
      shuffleButton.setBackground(Color.WHITE);
      validate();
      
      content.add(new JLabel("Prev."));    
      content.add(new JLabel("RWD"));
      content.add(new JLabel("Play/Pause"));
      content.add(new JLabel("Stop"));      
      content.add(new JLabel("FF"));
      content.add(new JLabel("Next"));  
      content.add(new JLabel("Shuffle"));
      
      content.add(previousButton);
      content.add(rewindButton);
      playPause();
      content.add(playPauseButton);
      content.add(stopButton);      
      content.add(fastforwardButton);
      content.add(nextButton);
      content.add(shuffleButton);  
      
      content.setLayout(new GridLayout(2, 6, 2, 0)); 
      
      currentSongPanel.add(new JLabel("Currently Playing: "));
      currentSong.setText(Player.getCurrentSong(trackNumber));
      currentSongPanel.add(currentSong);
      
      currentSongPanel.setLayout(new GridLayout(2, 2, 2, 2));
      
      musicController.setLayout(new BorderLayout());
      musicController.add(content, BorderLayout.SOUTH);
      musicController.add(currentSongPanel, BorderLayout.NORTH);
      musicController.pack();

   }
   
   private void controllerDefaults(PlayerGUI musicController) {
      musicController.setSize(500,175);      
      musicController.setTitle("Music Player");
      musicController.setLocationRelativeTo(null);
      musicController.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   
   private void playPause() {
      if(playPauseImage) {
         playPauseButton.setIcon(new ImageIcon("playButton.png"));
         playPauseButton.setBackground(Color.WHITE);
      }
      else {
         playPauseButton.setIcon(new ImageIcon("pauseButton.png"));
         playPauseButton.setBackground(Color.WHITE);
      }
   }
}