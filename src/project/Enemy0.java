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

public class Enemy0 extends Enemy
{
    Enemy0(float center_x, float center_y, double alfa, float radius, long first_shot_delay, int type, Factory factory, int current_wave, int wave_event)
    {
        //type do zapisywania
        this.type = type;
        this.last_shot = System.nanoTime()/1000000 + Long.parseLong((String) factory.enemy_modules_list.get(type).get("first_shot_delay"))+first_shot_delay;
        this.alfa = alfa;
        this.radius = radius;
        this.width = Integer.parseInt((String) factory.enemy_modules_list.get(type).get("width"));
        this.height = Integer.parseInt((String) factory.enemy_modules_list.get(type).get("height"));
        this.center_x = center_x;
        this.center_y = center_y;
        this.trajectory_center_x = Float.parseFloat((String) factory.enemy_modules_list.get(type).get("trajectory_center_x"));
        this.trajectory_center_y = Float.parseFloat((String) factory.enemy_modules_list.get(type).get("trajectory_center_y"));
        double radians =Math.toRadians(this.alfa);
        double x = this.radius*Math.cos(radians)+this.center_x - this.width/2;
        this.x = (int) x;
        double y = this.radius*Math.sin(radians)+this.center_y - this.height/2;
        this.y = (int) y;
        this.health = Integer.parseInt((String) factory.enemy_modules_list.get(type).get("health"));
        this.trajectory_alfa = Float.parseFloat((String) factory.enemy_modules_list.get(type).get("trajectory_alfa"));
        this.shot_delay = Integer.parseInt((String) factory.enemy_modules_list.get(type).get("shot_delay"));
        this.sprite = new ImageIcon(String.valueOf(factory.enemy_modules_list.get(type).get("sprite"))).getImage();
        this.rotation = 0;
        this.bullets_at_once = Integer.parseInt((String) factory.enemy_modules_list.get(type).get("bullets_at_once"));
        this.type_of_bullets = Integer.parseInt((String) factory.enemy_modules_list.get(type).get("type_of_bullets"));
        this.changing_radius = Float.parseFloat((String) factory.enemy_modules_list.get(type).get("changing_radius"));
        this.belongs_to_event = wave_event;
        this.belongs_to_wave = current_wave;
        this.score = Integer.parseInt((String) factory.enemy_modules_list.get(type).get("score"));
        this.sound_of_explosion = "music/explosion.wav";
        this.sound_of_shooting = "music/shot1.wav";
                

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
        return this.shot_delay-(System.nanoTime()/1000000-this.last_shot);
    }
    public void update()
    {
        if(this.belongs_to_wave == 1)
        {
            
            if(this.belongs_to_event == 1)
            {
                if(this.center_y<=200)
                {
                    this.center_y +=this.trajectory_center_y;
                }
                else
                {
                    if(this.rotation<8)
                    {
                        this.alfa +=trajectory_alfa;
                        if(this.alfa>=630 || this.alfa<=-450){
                            this.alfa %=360;
                            this.rotation++;
                        }
                    }
                    else this.center_x +=this.trajectory_center_x;
                }
                double radians =Math.toRadians(this.alfa);
                double x = this.radius*Math.cos(radians)+this.center_x - this.width/2;;
                this.x = (int) x;
                double y = this.radius*Math.sin(radians)+this.center_y - this.height/2;;
                this.y = (int) y;       
            }
            if(this.belongs_to_event == 2)
            {
                if(this.center_y<=200)
                {
                    this.center_y +=this.trajectory_center_y;
                    this.y = this.center_y-this.height/2;
                }
            }
            if(this.belongs_to_event == 3||this.belongs_to_event == 4)
            {
                if(this.center_y<=100)
                {
                    this.center_y +=this.trajectory_center_y;
                    this.y = this.center_y-this.height/2;
                }
                else
                {
                    if(this.center_x + this.trajectory_center_x>=720 || this.center_x + this.trajectory_center_x<=80)this.trajectory_center_x*=-1;
                    this.center_x +=this.trajectory_center_x;
                    this.x = this.center_x-this.width/2;
                }
            }
    
        }
        if(this.belongs_to_wave == 2)
        {
            
            if(this.belongs_to_event == 1)
            {
                if(this.center_y<=200)
                {
                    this.center_y +=this.trajectory_center_y;
                }
                else
                {

                    this.alfa +=trajectory_alfa;
                    if(this.alfa>=630 || this.alfa<=-450){
                        this.alfa %=360;
                        this.rotation++;
                    }

                }
                double radians =Math.toRadians(this.alfa);
                double x = this.radius*Math.cos(radians)+this.center_x - this.width/2;;
                this.x = (int) x;
                double y = this.radius*Math.sin(radians)+this.center_y - this.height/2;;
                this.y = (int) y;       
            }
            if(this.belongs_to_event == 2)
            {
                if(this.center_y<=200)
                {
                    this.center_y +=this.trajectory_center_y;
                    this.y = this.center_y-this.height/2;
                }
            }
            if(this.belongs_to_event == 3||this.belongs_to_event == 4)
            {
                if(this.center_y<=100)
                {
                    this.center_y +=this.trajectory_center_y;
                    this.y = this.center_y-this.height/2;
                }
                else
                {
                    if(this.center_x + this.trajectory_center_x>=720 || this.center_x + this.trajectory_center_x<=80)this.trajectory_center_x*=-1;
                    this.center_x +=this.trajectory_center_x;
                    this.x = this.center_x-this.width/2;
                }
            }
    
        }
    }
    public boolean isBeyound()
    {
        if((this.belongs_to_wave == 1 || this.belongs_to_wave == 2)&& this.belongs_to_event==1 && (this.x >800 || this.x+this.width <0))return true;
        else return false;
    }
}
