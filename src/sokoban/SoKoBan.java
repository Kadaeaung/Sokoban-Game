/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

/**
 *
 * @author User
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
public class SoKoBan extends JFrame{
      JLabel lblUN, lblMoveCount, lblCurrentLevel;
      JLabel lblActorImg, lblBoxing, lblDiamondImg, lblWallImg;
      JLabel lblInstruction1,lblInstruction2,lblInstruction3, lblDownArrow,lblUpArrow, lblLeftArrow, lblRightArrow;
      JLabel lblGameBoardimage;
      JPanel pnlGameBoard;
      Wall objWall;
      Crates objBox;
      Diamond objEndPoint;
      Actor objActor;
      private int movecount=0;
      private final int OFFSET=180;
      private final int SPACE=30;
      private final int LEFT_COLLISION=1;
      private final int RIGHT_COLLISION=2;
      private final int TOP_COLLISION=3;
      private final int BOTTOM_COLLISION=4;
      private ArrayList alWalls= new ArrayList();
      private ArrayList alBaggs=new ArrayList();
      private ArrayList alAreas=new ArrayList();
      
      private int width=0;
      private int height=0;
      private boolean completestatus=false;
      String completelevel="";
      GameLevel m=new GameLevel();
      public SoKoBan() {
          this.setTitle("Sokoban Game");
          this.setSize(600, 500);
          this.setLayout(null);
          lblUN=new JLabel("Kadae Aung");
          lblMoveCount=new JLabel();
          lblCurrentLevel=new JLabel();
          lblUN.setBounds(20,20,100,30);
          this.add(lblUN);
          lblMoveCount.setBounds(20,60,100,30);
          this.add(lblMoveCount);
          lblCurrentLevel.setBounds(20, 100, 100, 30);
          this.add(lblCurrentLevel);
          pnlGameBoard=new JPanel();
          pnlGameBoard.setBounds(130, 20, 350, 430);
          this.add(pnlGameBoard);
          
          lblGameBoardimage=new JLabel();
          lblGameBoardimage.setBounds(10,10,350,430);
          lblInstruction1=new JLabel("Use Arrow Key");
          lblInstruction1.setBounds(10, 180, 100, 30);
          this.add(lblInstruction1);
          lblUpArrow =new JLabel(new ImageIcon("Up.PNG"));
          lblUpArrow.setBounds(30,210,30,30);
          this.add(lblUpArrow);
          lblLeftArrow=new JLabel(new ImageIcon("Left.PNG"));
          this.add(lblLeftArrow);
          lblRightArrow=new JLabel(new ImageIcon("Right.PNG"));
          lblRightArrow.setBounds(50, 250, 30, 30);
          this.add(lblRightArrow);
          lblDownArrow=new JLabel(new ImageIcon("Down.PNG"));
          lblDownArrow.setBounds(30, 250, 30, 30);
          this.add(lblDownArrow);
          lblInstruction2=new JLabel("Restrat level");
          lblInstruction2.setBounds(10,280,100,30);
          this.add(lblInstruction2);
          lblInstruction3=new JLabel("Press R");
          lblInstruction3.setBounds(10,300,100,30);
          this.add(lblInstruction3);
          
          Crates b=new Crates(30,30);
          lblBoxing=new JLabel(new ImageIcon (b.getImage()));
          lblBoxing.setSize(30,30);
          Wall w=new Wall(30,30);
          lblWallImg=new JLabel(new ImageIcon (w.getImage()));
          lblWallImg.setSize(30, 30);
          Diamond a=new Diamond(30,30);
          lblDiamondImg=new JLabel(new ImageIcon (a.getImage()));
          lblDiamondImg.setSize(30, 30);
          Actor ac=new Actor (30,30);
          lblActorImg=new JLabel(new ImageIcon (ac.getImage()));
          completelevel=m.level_1;
          lblCurrentLevel.setText("Level 1");
          startGameBoard(completelevel);
          pnlGameBoard.add(lblGameBoardimage);
          pnlGameBoard.addKeyListener(new MovementKeyAdapter());
          pnlGameBoard.setFocusable(true);
          this.setVisible(true);      
      }
       public void startGameBoard(String level) {
           int x= OFFSET;
           int y=130;
           for (int i=0;i<level.length(); i++){
               char item=level.charAt(i);
               if (item=='\n'){
               y += SPACE;
               if(this.width<x){
                   this.width=x;
               }
               x=OFFSET;
           }
               else if (item=='#'){
                
                 objWall=new Wall(x,y);
                 alWalls.add(objWall);
                 x+= SPACE;              
}
               
               else if (item=='$'){
                
                 objBox=new Crates(x,y);
                 alBaggs.add(objBox);
                 x+= SPACE;              
}
               else if (item=='.'){
                
                 objEndPoint=new Diamond(x,y);
                 alAreas.add(objEndPoint);
                 x+= SPACE;              
}
               else if(item=='@'){
                 objActor=new Actor(x,y);
                 x+=SPACE;
           } else if (item==' '){
               x+=SPACE;
}
               height=y;
    }
}
       public void buildLevel_Map(Graphics g){
           Graphics2D g2d= (Graphics2D) g;
           ImageIcon iia=new ImageIcon("Wallpaper.JPG");
           Image img=iia.getImage();
           g2d.drawImage(img, 130, 20, null);
           g2d.fillRect(0, 0, lblGameBoardimage.getWidth(), lblGameBoardimage.getHeight());
           ArrayList alLevelMap=new ArrayList();
           alLevelMap.addAll(alWalls);
           alLevelMap.addAll(alAreas);
           alLevelMap.addAll(alBaggs);
           alLevelMap.add(objActor);
           for (int i=0; i<alLevelMap.size();i++) {
               Movement_Adjuster item= (Movement_Adjuster) alLevelMap.get(i);
               if ((item instanceof Actor)|| (item instanceof Crates)) {
               g2d.drawImage(item.getImage(), item.getX() +2, item.getY() + 2, lblGameBoardimage);
                }
               else{
               g2d.drawImage(item.getImage(), item.getX(),item.getY(),lblGameBoardimage);
                }
               if (completestatus) {
                 g2d.setColor(new Color(0,0,0));
                 g2d.drawString("Completed",25,20);
                 if (completelevel.equals("level 1"))
                 {
                     completelevel="level2";
                     startGameBoard(completelevel);
                 }
                 else if (completelevel.equals("level 2"))
                 {
                     completelevel="level3";
                     startGameBoard(completelevel);
                 }
                  else if (completelevel.equals("level 3"))
                  {
                       completelevel="level4";
                     startGameBoard(completelevel);
                  }
                   else if (completelevel.equals("level 4"))
                   {
                      completelevel="level5";
                     startGameBoard(completelevel);              
                   }
           }
           
       }
       }

    /**
     * @param args the command line arguments
     */
     @Override
     public void paint(Graphics g) {
         Graphics2D g2d=(Graphics2D) g;
         super.paint(g2d);
         //lblGameBoardimage.paint(g);
         buildLevel_Map(g2d);
     }
     class MovementKeyAdapter extends KeyAdapter {
         @Override
         public void keyPressed(KeyEvent e){
             if (completestatus)
             {
                 return;
         }
         int key=e.getKeyCode();
         if (key==KeyEvent.VK_A){
         if (checkWallCollision(objActor, LEFT_COLLISION)) {
             JOptionPane.showMessageDialog(null, "Left Wall");
             return;
         }
         if (checkBagCollection(LEFT_COLLISION)) {
             JOptionPane.showMessageDialog(null, "Left Wall");
             return;
         }
         movecount +=1;
         objActor.move(-SPACE, 0);
     } else if (key==KeyEvent.VK_D){
         if (checkWallCollision(objActor,RIGHT_COLLISION)){
             JOptionPane.showMessageDialog(null, "Right Wall");
             return;
         }
         if (checkBagCollection(RIGHT_COLLISION)) {
             JOptionPane.showMessageDialog(null, "Right Wall");
             return;
         }
         movecount+=1;
         objActor.move(SPACE, 0);
     } else if (key==KeyEvent.VK_W) {
         if (checkWallCollision(objActor,TOP_COLLISION)) {
             JOptionPane.showMessageDialog(null, "Top Wall");
             return;
         }
         if (checkBagCollection(TOP_COLLISION)) {
             JOptionPane.showMessageDialog(null, "Top Wall");
             return;
         }
         movecount+=1;
         objActor.move(0, -SPACE);
     } else if(key==KeyEvent.VK_S) {
         if (checkWallCollision(objActor,BOTTOM_COLLISION)) {
             JOptionPane.showMessageDialog(null,"Bottom Wall");
             return;
         }
         if (checkBagCollection(BOTTOM_COLLISION)) {
            JOptionPane.showMessageDialog(null, "Bottom Wall");
            return;
         }
         movecount+=1;
         objActor.move(0, SPACE);
     } else if (key==KeyEvent.VK_R) {
         nextLevel();
     }
     lblMoveCount.setText(String.valueOf(movecount));
     repaint();
     }
}
private boolean checkWallCollision(Movement_Adjuster actor,int type){
            if (type==LEFT_COLLISION) {
            for (int i=0;i<alWalls.size(); i++) {
            Wall wall=(Wall) alWalls.get(i);
              if (actor.isleftcollection(wall)) {
              return true;
}
}
           return false;
} else if (type==RIGHT_COLLISION) {
              for (int i=0;i<alWalls.size();i++) {
              Wall wall=(Wall) alWalls.get(i);
              if (actor.isrightcollision(wall)){
              return true;
}
}
               return false;
} else if (type==TOP_COLLISION) {
               for (int i=0;i<alWalls.size();i++) {
               Wall wall =(Wall) alWalls.get(i);
               if (actor.istopcollision(wall)) {
               return true;
}
}
            return false;
} else if (type==BOTTOM_COLLISION) {
               for (int i=0;i<alWalls.size();i++) {
               Wall wall =(Wall) alWalls.get(i);
               if (actor.isbottomcollision(wall)) {
               return true;
}
    }
               return false;
}
            return false;
}

private boolean checkBagCollection(int collision_type){
       if (collision_type==LEFT_COLLISION) {
         for (int i=0;i<alBaggs.size(); i++) {
          Crates bag=(Crates) alBaggs.get(i);
           if (objActor.isleftcollection(bag)) {
           for (int j=0;j<alBaggs.size(); j++) {
             Crates item=(Crates) alBaggs.get(j);
             if (!bag.equals(item)) {
               if (bag.isleftcollection(item)) {
                  return true;
              }
           }
            if (checkWallCollision(bag,LEFT_COLLISION)) {
                return true;
              }
           }
            bag.move (-SPACE,0);
            isLevelComplete();  
        }
    }
        return false;
        }
      else if (collision_type ==RIGHT_COLLISION){
        for (int i=0; i<alBaggs .size(); i++){
         Crates Bag=(Crates) alBaggs .get(i);
          if (objActor.isrightcollision(Bag)) {
             for (int j=0; j<alBaggs.size(); j++){
                  Crates item=(Crates) alBaggs.get(j);
                  if (!Bag.equals(item)) {
                     if (Bag.isrightcollision(item)){
                     return true;
        }
    }
     if (checkWallCollision(Bag,RIGHT_COLLISION)) {
       return true;
        }
    }
    Bag.move(SPACE,0);
    isLevelComplete();
        }
    }    
       return false;
       }
        else if (collision_type ==TOP_COLLISION){
        for (int i=0; i<alBaggs .size(); i++){
         Crates Bag=(Crates) alBaggs .get(i);
          if (objActor.istopcollision(Bag)) {
             for (int j=0; j<alBaggs.size(); j++){
                  Crates item=(Crates) alBaggs.get(j);
                  if (!Bag.equals(item)) {
                     if (Bag.istopcollision(item)){
                     return true;
        }
    }
     if (checkWallCollision(Bag,TOP_COLLISION)) {
       return true;
        }
    }
    Bag.move(0,-SPACE);
    isLevelComplete();
        }
    }    
       return false;
       }
        else if (collision_type ==BOTTOM_COLLISION){
            for (int i=0; i<alBaggs .size(); i++)
{
         Crates Bag=(Crates) alBaggs .get(i);
          if (objActor.isbottomcollision(Bag))    {   
          
             for (int j=0; j<alBaggs.size(); j++){
                  Crates item=(Crates) alBaggs.get(j);
                  if (!Bag.equals(item)) {
                     if (Bag.isbottomcollision(item)){
                     return true;
        }
    }
     if (checkWallCollision(Bag,BOTTOM_COLLISION)) {
       return true;
        }
    }
    Bag.move(0,SPACE);
    isLevelComplete();
          }
        }
    }    

       return false;
       }
    
     public void isLevelComplete(){
         int num=alBaggs.size();
         int compl=0;
         for (int i=0;i<num;i++) {
         Crates bag=(Crates) alBaggs.get(i);
         for (int j=0;j<num;j++) {
         Diamond area=(Diamond) alAreas.get(j);
          if (bag.getX()==area.getX() 
                      && bag.getY()==area.getY()) {
                 compl +=1;
            }
        }
    }
     if (compl==num){
      completestatus=false;

      if (completelevel.equals(m.level_1)) {
       completelevel=m.level_2;
        lblCurrentLevel.setText("Level 2");
        startGameBoard(completelevel);
        }
    else if (completelevel.equals(m.level_2)) {
       completelevel=m.level_3;
        lblCurrentLevel.setText("Level 3");
        startGameBoard(completelevel);
    }
else if (completelevel.equals(m.level_3)){ 
       completelevel=m.level_4;
        lblCurrentLevel.setText("Level 4");
        startGameBoard(completelevel);
    }
else if(completelevel.equals(m.level_4)) {
       completelevel=m.level_5;
        lblCurrentLevel.setText("Level 5");
        startGameBoard(completelevel);       
    }
else{
    
}
     JOptionPane.showMessageDialog(null,"level Complete");
     nextLevel();
     repaint();
     }
    }
   public void nextLevel() {
    alAreas.clear();
    alBaggs.clear();
    alWalls.clear();
    startGameBoard(completelevel);
    movecount=0;
    if (completestatus) {
     completestatus=false;
        }
    } 
    public static void main(String[] args) throws IOException {
        SoKoBan objSoko=new SoKoBan();
    }
}

