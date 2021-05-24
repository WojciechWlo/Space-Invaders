/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Image;

/**
 *
 * @author vojci
 */
abstract public class Enemy
{
    public int score;
    public int rotation;
    public int type;
    public double alfa;
    public float trajectory_alfa;
    public float trajectory_center_x;
    public float trajectory_center_y;
    public float changing_radius;
    public float radius;
    public float x;
    public float y;
    public float center_x;
    public float center_y;
    public int width;
    public int height;
    public long now;
    public long last_shot;
    public int health;
    public Image sprite;
    public int shot_delay;
    public int bullets_at_once;
    public int bullet_delay;
    public int belongs_to_event;
    public int belongs_to_wave;
    public int type_of_bullets;
    public String sound_of_explosion;
    public String sound_of_shooting;
    abstract public void update();
    abstract public boolean shot();
    abstract public boolean isBeyound();
    abstract public long untilNextShot();

}
