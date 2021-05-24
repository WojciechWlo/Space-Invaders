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
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
public class Factory {

    public ArrayList<HashMap> enemy_modules_list = new ArrayList<HashMap>();
    public ArrayList<HashMap> enemy_bullets_modules_list = new ArrayList<HashMap>();
    Factory()
    {
        // pierwszy zestaw przeciwników
        HashMap<String, String> enemy_module0 = new HashMap<String, String>();
        enemy_module0.put("width", "40");
        enemy_module0.put("height", "40");
        enemy_module0.put("trajectory_center_x", "3f");
        enemy_module0.put("trajectory_center_y", "3f");
        enemy_module0.put("trajectory_alfa", "3f");
        enemy_module0.put("trajectory_radius", "0f");
        enemy_module0.put("health", "5");
        enemy_module0.put("shot_delay", "1200");
        enemy_module0.put("bullet_delay", "0");
        enemy_module0.put("sprite", "images/sprity/statek3red.png");
        enemy_module0.put("type_of_bullets", "2");
        enemy_module0.put("first_shot_delay", "1000");
        enemy_module0.put("bullets_at_once", "0");
        enemy_module0.put("changing_radius","0f");
        enemy_module0.put("score","10");
        enemy_modules_list.add(enemy_module0);
        
        //drugi zestaw przeciwników
        HashMap<String, String> enemy_module1 = new HashMap<String, String>();
        enemy_module1.put("width", "40");
        enemy_module1.put("height", "40");
        enemy_module1.put("trajectory_center_x", "-3f");
        enemy_module1.put("trajectory_center_y", "3f");
        enemy_module1.put("trajectory_alfa", "-3f");
        enemy_module1.put("trajectory_radius", "0f");
        enemy_module1.put("health", "5");
        enemy_module1.put("shot_delay", "1200");
        enemy_module1.put("bullet_delay", "0");
        enemy_module1.put("sprite", "images/sprity/statek3red.png");
        enemy_module1.put("type_of_bullets", "2");
        enemy_module1.put("first_shot_delay", "1000");
        enemy_module1.put("bullets_at_once", "0");
        enemy_module1.put("changing_radius","0f");
        enemy_module1.put("score","10");
        enemy_modules_list.add(enemy_module1);

        //trzeci zestaw przeciwników
        HashMap<String, String> enemy_module2 = new HashMap<String, String>();
        enemy_module2.put("width", "80");
        enemy_module2.put("height", "80");
        enemy_module2.put("trajectory_center_x", "0f");
        enemy_module2.put("trajectory_center_y", "4f");
        enemy_module2.put("trajectory_alfa", "0f");
        enemy_module2.put("trajectory_radius", "0f");
        enemy_module2.put("health", "30");
        enemy_module2.put("shot_delay", "1200");
        enemy_module2.put("bullet_delay", "0");
        enemy_module2.put("sprite", "images/sprity/statek1red.png");
        enemy_module2.put("type_of_bullets", "1");
        enemy_module2.put("first_shot_delay", "3000");
        enemy_module2.put("bullets_at_once", "20");
        enemy_module2.put("changing_radius","0f");
        enemy_module2.put("score","20");
        enemy_modules_list.add(enemy_module2);

        //czwarty zestaw przeciwników
        HashMap<String, String> enemy_module3 = new HashMap<String, String>();
        enemy_module3.put("width", "40");
        enemy_module3.put("height", "40");
        enemy_module3.put("trajectory_center_x", "3f");
        enemy_module3.put("trajectory_center_y", "4f");
        enemy_module3.put("trajectory_alfa", "0f");
        enemy_module3.put("trajectory_radius", "0f");
        enemy_module3.put("health", "10");
        enemy_module3.put("shot_delay", "1200");
        enemy_module3.put("bullet_delay", "0");
        enemy_module3.put("sprite", "images/sprity/statek1red.png");
        enemy_module3.put("type_of_bullets", "0");
        enemy_module3.put("first_shot_delay", "2000");
        enemy_module3.put("bullets_at_once", "20");
        enemy_module3.put("changing_radius","0f");
        enemy_module3.put("score","25");
        enemy_modules_list.add(enemy_module3);

        // piąty zestaw przeciwników
        HashMap<String, String> enemy_module4 = new HashMap<String, String>();
        enemy_module4.put("width", "40");
        enemy_module4.put("height", "40");
        enemy_module4.put("trajectory_center_x", "3f");
        enemy_module4.put("trajectory_center_y", "3f");
        enemy_module4.put("trajectory_alfa", "3f");
        enemy_module4.put("trajectory_radius", "0f");
        enemy_module4.put("health", "15");
        enemy_module4.put("shot_delay", "2400");
        enemy_module4.put("bullet_delay", "0");
        enemy_module4.put("sprite", "images/sprity/statek2red.png");
        enemy_module4.put("type_of_bullets", "1");
        enemy_module4.put("first_shot_delay", "1000");
        enemy_module4.put("bullets_at_once", "6");
        enemy_module4.put("changing_radius","0f");
        enemy_module4.put("score","25");
        enemy_modules_list.add(enemy_module4);

        //pierwszy zestaw wrogich pocisków
        HashMap<String, String> enemy_bullets_module0 = new HashMap<String, String>();
        enemy_bullets_module0.put("width", "10");
        enemy_bullets_module0.put("height", "10");
        enemy_bullets_module0.put("bullet_radius", "5f");
        enemy_bullets_module0.put("trajectory_center_x", "0f");
        enemy_bullets_module0.put("trajectory_center_y", "7f");
        enemy_bullets_module0.put("trajectory_alfa", "3f");
        enemy_bullets_module0.put("changing_radius", "0f");
        enemy_bullets_module0.put("damage", "1");
        enemy_bullets_modules_list.add(enemy_bullets_module0);


        //drugi zestaw wrogich pocisków
        HashMap<String, String> enemy_bullets_module1 = new HashMap<String, String>();
        enemy_bullets_module1.put("width", "10");
        enemy_bullets_module1.put("height", "10");
        enemy_bullets_module1.put("bullet_radius", "5f");
        enemy_bullets_module1.put("trajectory_center_x", "0f");
        enemy_bullets_module1.put("trajectory_center_y", "0f");
        enemy_bullets_module1.put("trajectory_alfa", "0.2f");
        enemy_bullets_module1.put("changing_radius", "0.9f");
        enemy_bullets_module1.put("damage", "1");
        enemy_bullets_modules_list.add(enemy_bullets_module1);
        
        //trzeci zestaw wrogich pocisków
        HashMap<String, String> enemy_bullets_module2 = new HashMap<String, String>();
        enemy_bullets_module2.put("width", "10");
        enemy_bullets_module2.put("height", "10");
        enemy_bullets_module2.put("bullet_radius", "5f");
        enemy_bullets_module2.put("trajectory_center_x", "0f");
        enemy_bullets_module2.put("trajectory_center_y", "0f");
        enemy_bullets_module2.put("trajectory_alfa", "-0.2f");
        enemy_bullets_module2.put("changing_radius", "0.9f");
        enemy_bullets_module2.put("damage", "1");
        enemy_bullets_modules_list.add(enemy_bullets_module2);
        
        //czwarty zestaw wrogich pocisków
        HashMap<String, String> enemy_bullets_module3 = new HashMap<String, String>();
        enemy_bullets_module3.put("width", "10");
        enemy_bullets_module3.put("height", "10");
        enemy_bullets_module3.put("bullet_radius", "5f");
        enemy_bullets_module3.put("trajectory_center_x", "0f");
        enemy_bullets_module3.put("trajectory_center_y", "0f");
        enemy_bullets_module3.put("trajectory_alfa", "-0.2f");
        enemy_bullets_module3.put("changing_radius", "2f");
        enemy_bullets_module3.put("damage", "1");
        enemy_bullets_modules_list.add(enemy_bullets_module3);
    }
}
