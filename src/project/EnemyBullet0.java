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
public class EnemyBullet0 extends Bullet {
    public int type;

    public void EnemyBullet_constructor(float center_x, float center_y, double alfa, float radius, int type,
            Factory factory) {
        this.bullet_radius = Float
                .parseFloat((String) factory.enemy_bullets_modules_list.get(type).get("bullet_radius"));
        this.trajectory_center_y = Float
                .parseFloat((String) factory.enemy_bullets_modules_list.get(type).get("trajectory_center_y"));
        this.trajectory_center_x = Float
                .parseFloat((String) factory.enemy_bullets_modules_list.get(type).get("trajectory_center_x"));
        this.damage = Integer.parseInt((String) factory.enemy_bullets_modules_list.get(type).get("damage"));
        this.trajectory_alfa = Float
                .parseFloat((String) factory.enemy_bullets_modules_list.get(type).get("trajectory_alfa"));
        this.changing_radius = Float
                .parseFloat((String) factory.enemy_bullets_modules_list.get(type).get("changing_radius"));
        this.type = type;
    }

    EnemyBullet0(float center_x, float center_y, double alfa, float radius, int type, Factory factory) {
        EnemyBullet_constructor(center_x, center_y, alfa, radius, type, factory);
        this.radius = radius;
        this.center_x = center_x;
        this.center_y = center_y + this.bullet_radius;
        this.alfa = alfa;
        double radians = Math.toRadians(this.alfa);
        double x = this.radius * Math.cos(radians) + this.center_x - this.bullet_radius;
        this.x = (int) x;
        double y = this.radius * Math.sin(radians) + this.center_y - this.bullet_radius;
        this.y = (int) y;
    }

    EnemyBullet0(float center_x, float center_y, double alfa, float radius, float player_center_x,
            float player_center_y, int type, Factory factory) {
        EnemyBullet_constructor(center_x, center_y, alfa, radius, type, factory);
        this.alfa = Math.toDegrees(Math.atan2(center_y - player_center_y, center_x - player_center_x));
        this.radius = (float) Math.hypot(player_center_x - center_x, player_center_y - center_y);
        this.center_x = player_center_x;
        this.center_y = player_center_y;
        double radians = Math.toRadians(this.alfa);
        double x = this.radius * Math.cos(radians) + this.center_x - this.bullet_radius;
        this.x = (int) x;
        double y = this.radius * Math.sin(radians) + this.center_y - this.bullet_radius;
        this.y = (int) y;

    }

    public void update() {
        if (this.type == 0) {
            this.center_y += this.trajectory_center_y;
            this.y = this.center_y - this.bullet_radius;
        } else if (this.type == 1 || this.type == 2) {
            this.alfa += this.trajectory_alfa;
            this.alfa += trajectory_alfa;
            this.alfa %= 360;
            this.radius += this.changing_radius;
            double radians = Math.toRadians(this.alfa);
            double x = this.radius * Math.cos(radians) + this.center_x - this.bullet_radius;
            this.x = (int) x;
            double y = this.radius * Math.sin(radians) + this.center_y;
            this.y = (int) y;
        } else if (this.type == 3) {

            this.radius -= this.changing_radius;
            double radians = Math.toRadians(this.alfa);
            double x = this.radius * Math.cos(radians) + this.center_x - this.bullet_radius;
            this.x = (int) x;
            double y = this.radius * Math.sin(radians) + this.center_y - this.bullet_radius;
            this.y = (int) y;
        }

    }

    public boolean isAlive() {
        if (this.type == 0 && this.y > 600) {
            return false;

        } else if ((this.type == 1 || this.type == 2) && (Math.abs(this.radius) + this.bullet_radius > 1000))
            return false;
        else
            return true;

    }

    public int getType() {
        return this.type;
    }

}
