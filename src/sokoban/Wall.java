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
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
public class Wall extends Movement_Adjuster
{
    public Wall(int x,int y)
    {
        super(x,y);
        ImageIcon ico=new ImageIcon("Wall.jpg");
        Image img=ico.getImage();
        this.setImage(img);
    }
}