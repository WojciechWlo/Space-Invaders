/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author vojci
 */
public class Player
{
    public float x;
    public float y;
    public int width;
    public int height;
    public Image sprite;
    public long last_shot;
    public long shot_delay;
    public int health;
    public float speed_x;
    public float speed_y;
    public int collider_radius;
    public float center_x;
    public int score;
    public float center_y;
    public String sound_of_shooting = "music/shot0.wav";
    public String sound_of_explosion = "music/explosion.wav";
    //zmienna pomocnicza (do zapisu)
    Player()
    {
        this.score = 0;
        this.speed_x = 5;
        this.speed_y = 5;
        this.health = 30;
        this.shot_delay = 120;
        this.width = 40;
        this.height = 40;
        this.collider_radius = 5; 
        this.x = (800-this.width)/2;
        this.center_x = this.x +this.width/2;
        this.y = 520;
        this.center_y = this.y+this.height/2;
        this.last_shot = System.nanoTime()/1000000;
        this.sprite = new ImageIcon("images/sprity/statek1blue.png").getImage();
    }
    public boolean shot()
    {
        long now = System.nanoTime()/1000000;
        if(now-this.last_shot>this.shot_delay){
            this.last_shot = now;
            return true;
        }
        return false;
    }
    //do zapisu
    public long untilNextShot()
    {
        long now = System.nanoTime()/1000000;
        if(now-this.last_shot>this.shot_delay) return 0;
        else return this.shot_delay-(System.nanoTime()/1000000-this.last_shot);
    }
}
