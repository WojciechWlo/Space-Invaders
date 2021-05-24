/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author vojci
 */
public class Coin {
    public float x;
    public float y;
    public int width;
    public int height;
    public float speed;
    public int value;
    public String sound = "music/acquiring.wav";
    Coin(float x, float y)
    {
        this.width = 20;
        this.height = 20;
        this.x = x-this.width/2;
        this.y = y-this.height/2;
        this.speed = 3;
        this.value = 5;
    }
    public void update()
    {
        this.y +=this.speed;
    }
    public boolean isBeyound()
    {
        if(this.y>600)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
