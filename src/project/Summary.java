/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import java.io.FileWriter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author vojci
 */
public class Summary {
    public String player_name;
    public PlayerScore players_scores[];
    Summary()
    {
        this.player_name = JOptionPane.showInputDialog("Wpisz swoje imię");
        if(this.player_name == ""|| this.player_name ==null){
            player_name = "Anonymous";
        }
    }
    Summary(String player_name)
    {
        this.player_name =player_name;
    }
    public void gameSummary(int score)
    {
        players_scores = new PlayerScore[10];
        for(int i = 0;i<10;i++)
        {
            players_scores[i]=new PlayerScore("",0);
        }
        HashMap<String, String> players = new HashMap<String, String>();
        File f = new File("punktacja.txt");
        if(!f.exists())
        {
            try
            {
                f.createNewFile();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            this.players_scores[0].name = player_name;
            this.players_scores[0].score = score;
            save(f);
        }
        else
        {
            load(f);
            check(score);
            save(f);
        }
    }
    public void save(File f)
    {
        if(f.canWrite())
        {
            try
            {
                FileWriter fw = new FileWriter(f, false); 
                Formatter fm = new Formatter(f);
                Scanner sf = new Scanner(f);
                for(int i=0;i<10;i++)
                {
                    fm.format("%s \r\n", players_scores[i].name);  
                    fm.format("%s \r\n", players_scores[i].score);
                }
                fm.close();
                fw.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }     
    }
    public void check(int score)
    {
        PlayerScore ps = new PlayerScore(this.player_name,score);
        int i = 0;
        boolean overwritten = false;
        while(i<10 && !overwritten)
        {
            if(score>players_scores[i].score)
            {
                overwritten = true;
                for(int j =8;j>=i;j--)
                {
                    players_scores[j+1] = players_scores[j];
                }
                players_scores[i] = ps;
            }
            i++;
        }
    }
    public String prepareStr(String str)
    {
        return str.substring(0, str.length()-1);
    }
    public void load(File f)
    {
        if(f.canRead())
        {
            try
            {
                String name;
                int score;
                int i=0;
                Scanner sf = new Scanner(f);
                while(sf.hasNextLine() && i<10)
                {
                    this.players_scores[i].name=prepareStr(sf.nextLine());
                    this.players_scores[i].score=Integer.parseInt(prepareStr(sf.nextLine()));
                    i++;
                }
                sf.close(); 
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
