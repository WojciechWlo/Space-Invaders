/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.util.ArrayList;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
/**
 *
 * @author vojci
 */
//Obiekt odpowiedzialny za kontrolę nad wszystkimi obiektami poruszającymi się na ekranie
public class ObjectControl
{
    public Factory factory = new Factory();
    public Player player = new Player();
    public ArrayList<Coin> coins_list = new ArrayList<Coin>();
    public ArrayList<Bullet> player_bullets_list = new ArrayList<Bullet>();
    public ArrayList<Enemy> enemies_list = new ArrayList<Enemy>();
    public ArrayList<Bullet> enemies_bullets_list = new ArrayList<Bullet>();
    public long now;
    public int current_wave = 0;
    public int delay_between_waves = 5000;
    public long end_of_wave = System.nanoTime()/1000000;
    public long last_wave_event;
    public boolean finished_wave = true;
    public int wave_event;
    public int state = 1;
    public AudioPlayer audio_player = new AudioPlayer();
    //Metoda w której wykonują się działania takie jak poruszanie się obiektów czy strzelanie
    public void update()
    {
        waves();
        for(int i = 0;i<coins_list.size();i++)
        {
            coins_list.get(i).update();
            if(!coins_list.get(i).isBeyound())
            {
                coins_list.remove(i);
                i--;
            }
        }
        for(int i = 0;i<player_bullets_list.size();i++)
        {
            player_bullets_list.get(i).update();
            if(!player_bullets_list.get(i).isAlive())
            {
                player_bullets_list.remove(i);
                i--;
            }
        }
        
        for(int i = 0;i<enemies_bullets_list.size();i++)
        {
            enemies_bullets_list.get(i).update();
            if(!enemies_bullets_list.get(i).isAlive())
            {
                enemies_bullets_list.remove(i);
                i--;
            }
        }
        
        for(int i = 0;i<enemies_list.size();i++)
        {
            enemies_list.get(i).update();
            if(enemies_list.get(i).isBeyound())
            {
                enemies_list.remove(i);
                i--;
            }
            else if(enemies_list.get(i).shot())
            {
                if(enemies_list.get(i).type_of_bullets ==0){

                   enemies_bullets_list.add(new EnemyBullet0(enemies_list.get(i).x+ enemies_list.get(i).width/2, enemies_list.get(i).y+ enemies_list.get(i).height, 0,0,0, factory));

                }
                else if(enemies_list.get(i).type_of_bullets ==1)
                {
                    for(int j =0;j <enemies_list.get(i).bullets_at_once;j++)
                    {
                        enemies_bullets_list.add(new EnemyBullet0(enemies_list.get(i).x+ enemies_list.get(i).width/2, enemies_list.get(i).y+ enemies_list.get(i).height/2, j*360/enemies_list.get(i).bullets_at_once,0,1, factory));
                        enemies_bullets_list.add(new EnemyBullet0(enemies_list.get(i).x+ enemies_list.get(i).width/2, enemies_list.get(i).y+ enemies_list.get(i).height/2, j*360/enemies_list.get(i).bullets_at_once,0,2, factory));

                    }
                }
                else if(enemies_list.get(i).type_of_bullets ==2)
                {
                    enemies_bullets_list.add(new EnemyBullet0(enemies_list.get(i).x+ enemies_list.get(i).width/2, enemies_list.get(i).y+ enemies_list.get(i).height, 0,0,this.player.center_x, this.player.center_y, 3, factory));
                }
                audio_player.soundPlayer(enemies_list.get(i).sound_of_shooting);
            }
        }
        collisions();
    }  
    //kolizje obiektów między sobą
    public void collisions()
    {
        Ellipse2D player_collider = new Ellipse2D.Double(player.x + player.width/2-player.collider_radius,player.y + player.height/2-player.collider_radius, player.collider_radius*2, player.collider_radius*2);
        Area pc = new Area(player_collider);  
        Ellipse2D oval = new Ellipse2D.Double();
        int t_pol_x[] = new int[4];
        int t_pol_y[] = new int[4];
        int points = 4;
        Polygon t_pol = new Polygon();
        //sprawdzanie kolizji pomiędzy pociskami gracza a przeciwnikami
        for(int i=0;i<player_bullets_list.size();i++)
        {   
            oval = new Ellipse2D.Double(player_bullets_list.get(i).x, player_bullets_list.get(i).y, player_bullets_list.get(i).bullet_radius, player_bullets_list.get(i).bullet_radius);
            for(int j = 0;j<enemies_list.size();j++)
            {
                t_pol_x[0] = (int)enemies_list.get(j).x;
                t_pol_y[0] = (int)enemies_list.get(j).y;
                t_pol_x[1] = (int)enemies_list.get(j).x+ enemies_list.get(j).width;
                t_pol_y[1] = (int)enemies_list.get(j).y;
                t_pol_x[2] = (int)enemies_list.get(j).x+ enemies_list.get(j).width;
                t_pol_y[2] = (int)enemies_list.get(j).y + enemies_list.get(j).height;
                t_pol_x[3] = (int)enemies_list.get(j).x;
                t_pol_y[3] = (int)enemies_list.get(j).y + enemies_list.get(j).height;
                t_pol = new Polygon(t_pol_x,t_pol_y, points);
                Area a = new Area(oval);
                a.intersect(new Area(t_pol));
                if(!a.isEmpty())
                {
                    enemies_list.get(j).health-= player_bullets_list.get(i).damage;
                    player_bullets_list.remove(i);
                    if(enemies_list.get(j).health<=0)
                    {
                        audio_player.soundPlayer(enemies_list.get(j).sound_of_explosion);
                        player.score += enemies_list.get(j).score;
                        coins_list.add(new Coin(enemies_list.get(j).x + enemies_list.get(j).width/2, enemies_list.get(j).y+ enemies_list.get(j).height/2));
                        enemies_list.remove(j);
                    }
                    i--;

                    break;
                }

            }
        
        }
        //sprawdzanie kolizji pomiędzy graczem a kulami przeciwników
        for(int i=0;i<enemies_bullets_list.size();i++)
        {   
            oval = new Ellipse2D.Double(enemies_bullets_list.get(i).x, enemies_bullets_list.get(i).y, enemies_bullets_list.get(i).bullet_radius, enemies_bullets_list.get(i).bullet_radius);
            Area a = new Area(oval);          
            a.intersect(new Area(pc));
            if(!a.isEmpty())
            {
                player.health-= enemies_bullets_list.get(i).damage;
                enemies_bullets_list.remove(i);
                if(player.health<=0)
                {
                    audio_player.soundPlayer(player.sound_of_explosion);
                    state = 0;
                }

            }
        
        }
        //sprawdzanie kolizji pomiędzy graczem a monetami
        for(int i=0;i<coins_list.size();i++)
        {   
            oval = new Ellipse2D.Double(coins_list.get(i).x, coins_list.get(i).y, coins_list.get(i).width, coins_list.get(i).height);
            Area a = new Area(oval);          
            a.intersect(new Area(pc));
            if(!a.isEmpty())
            {
                player.score+=coins_list.get(i).value;
                audio_player.soundPlayer(coins_list.get(i).sound);
                coins_list.remove(i);
                i--;

            }
        
        }
    }
    //metoda w której wykonują się zdarzenia w odpowiednich falach
    public void waves()
    {
        switch(current_wave)
        {
            //fala pierwsza
            case(1):
                if(wave_event == 1)
                {   
                    //last_wave_event = System.nanoTime()/1000000;
                    for(int i =0; i<8;i++)
                    {
                        enemies_list.add(new Enemy0(120,-80-45*i,0,100, i*150,0,factory, current_wave, wave_event));
                        enemies_list.add(new Enemy0(680,-80-45*i,180,100, i*150,1,factory, current_wave, wave_event));                    
                    }              
                    wave_event=2;
                }
                else if(wave_event ==2)
                {

                    now = System.nanoTime()/1000000;
                    if(now - last_wave_event >7000)
                    {
                        last_wave_event = now;
                        enemies_list.add(new Enemy0(400,-80,0,0, 0,2,factory, current_wave, wave_event));                 
                        wave_event=3;
                    }

                }
                else if(wave_event ==3)
                {

                    now = System.nanoTime()/1000000;
                    if(now - last_wave_event >7000)
                    {
                        last_wave_event = now;
                        for(int i = 0;i<4;i++)
                        {
                            enemies_list.add(new Enemy0(128+168*i,-80,0,0, 0,3,factory, current_wave, wave_event));                       
                        }
                        wave_event=4;
                    }

                }
                else if(wave_event ==4)
                {

                    now = System.nanoTime()/1000000;
                    if(now - last_wave_event >3000)
                    {
                        last_wave_event = now;
                        for(int i = 0;i<4;i++)
                        {
                            enemies_list.add(new Enemy0(128+168*i,-80,0,0, 0,3,factory, current_wave, wave_event));                       
                        }
                        wave_event=5;
                    }
                }
                else if(wave_event>4 && enemies_list.size() ==0 && finished_wave == false)
                {
                    finished_wave = true;
                    end_of_wave = System.nanoTime()/1000000;
                }
            break;
            //fala druga
            case(2):
                if(wave_event == 1)
                {   
                    last_wave_event = System.nanoTime()/1000000;
                    for(int i =0; i<5;i++)
                    {
                        enemies_list.add(new Enemy0(400,-70*i,-90,80, i*150,4,factory, current_wave, wave_event));                
                    }              
                    wave_event=2;
                }
                else if(wave_event ==2)
                {

                    now = System.nanoTime()/1000000;
                    if(now - last_wave_event >7000)
                    {
                        last_wave_event = now;
                        enemies_list.add(new Enemy0(400,-80,0,0, 0,2,factory, current_wave, wave_event));                 
                        wave_event=3;
                    }

                }
                else if(wave_event ==3)
                {

                    now = System.nanoTime()/1000000;
                    if(now - last_wave_event >7000)
                    {
                        last_wave_event = now;
                        for(int i = 0;i<4;i++)
                        {
                            enemies_list.add(new Enemy0(128+168*i,-80,0,0, 0,3,factory, current_wave, wave_event));                       
                        }
                        wave_event=4;
                    }

                }
                else if(wave_event ==4)
                {

                    now = System.nanoTime()/1000000;
                    if(now - last_wave_event >3000)
                    {
                        last_wave_event = now;
                        for(int i = 0;i<4;i++)
                        {
                            enemies_list.add(new Enemy0(128+168*i,-80,0,0, 0,3,factory, current_wave, wave_event));                       
                        }
                        wave_event=5;
                    }
                }
                else if(wave_event>4 && enemies_list.size() ==0 && finished_wave == false)
                {
                    finished_wave = true;
                    end_of_wave = System.nanoTime()/1000000;
                }

            break;
            default:
                
            break;
        
        }
   
    }
    //do zapisu
    public long fromLastEvent()
    {
        return (System.nanoTime()/1000000-last_wave_event);
    }

}
