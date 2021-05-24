/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javax.swing.ImageIcon;

/**
 *
 * @author vojci
 */
public class PlayerBullet0 extends Bullet
{

    PlayerBullet0(float center_x, float center_y)
    {
        this.height = 10;
        this.width = 10;
        this.bullet_radius = 5;
        this.center_x = center_x;
        this.x = this.center_x-this.bullet_radius;
        this.center_y = center_y-bullet_radius;
        this.y = this.center_y-this.bullet_radius;
        this.trajectory_center_y = 7;
        this.damage =1;
        this.sprite = new ImageIcon("images/sterowiec.png").getImage();
        
    }
    public void update()
    {
        this.center_y-=this.trajectory_center_y;
        this.y = this.center_y-this.height/2;
    }
    public boolean isAlive()
    {
        if(this.y+this.bullet_radius*2<0)
        {
            return false;
        } 
        else return true;
    
    }
    public int getType() {
        return 0;
    }

}
