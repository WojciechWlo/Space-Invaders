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
abstract public class Bullet
{
    public float x;
    public float y;
    public int width;
    public int height;
    public float radius;
    public int damage;
    public float center_x;
    public float center_y;
    public double alfa;
    public float trajectory_alfa;
    public float trajectory_center_x;
    public float trajectory_center_y;
    public float bullet_radius;
    public double changing_radius;
    public Image sprite;
    abstract public boolean isAlive();
    abstract public void update();
    //do zapisywania
    abstract public int getType();
}
