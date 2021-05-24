/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileWriter;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Project extends JFrame {
    private Image background0;
    private boolean klawisze[];
    private Timer timer;
    private ObjectControl obj_con;
    public long now;
    public boolean save_latch = false;
    public boolean load_latch = false;
    public Summary summary;

    // klasa będąca pętlą główną
    class Loop extends TimerTask {
        // metoda odpowiedzialna za sterowanie statkiem
        public void piloting() {
            if (klawisze[8]) {
                System.exit(0);
            }
            if (klawisze[6] && !save_latch) {
                save_latch = true;
                save();
            }
            if (klawisze[7] && !load_latch) {
                load_latch = true;
                load();
            }
            float speed_x = obj_con.player.speed_x;
            float speed_y = obj_con.player.speed_y;
            // przytrzymanie klawisza shift zmniejsza szybkość ruchu
            if (klawisze[5]) {
                speed_x /= 2;
                speed_y /= 2;
            }
            // statek lecący na ukos powinien lecieć z taką samą szybkością jak gdyby leciał
            // prosto lub na boki
            if (klawisze[0] && klawisze[2] || klawisze[0] && klawisze[3] || klawisze[1] && klawisze[2]
                    || klawisze[1] && klawisze[3]) {
                if (obj_con.player.x != 0 && obj_con.player.x != 720) {
                    speed_y *= 0.7071;
                }
                if (obj_con.player.y != 0 && obj_con.player.y != 520) {
                    speed_x *= 0.7071;
                }
            }
            if (klawisze[0]) {
                obj_con.player.y -= speed_y;
            } else if (klawisze[1]) {
                obj_con.player.y += speed_y;
            }
            if (klawisze[2]) {
                obj_con.player.x -= speed_x;
            } else if (klawisze[3]) {
                obj_con.player.x += speed_x;
            }

            // określenie granic ekranu
            int border_x = 800 - obj_con.player.width;
            int border_y = 600 - obj_con.player.height;
            obj_con.player.x = (obj_con.player.x < 0) ? 0 : obj_con.player.x;
            obj_con.player.x = (obj_con.player.x > border_x) ? border_x : obj_con.player.x;
            obj_con.player.y = (obj_con.player.y < 0) ? 0 : obj_con.player.y;
            obj_con.player.y = (obj_con.player.y > border_y) ? border_y : obj_con.player.y;
            obj_con.player.center_x = obj_con.player.x + obj_con.player.width / 2;
            obj_con.player.center_y = obj_con.player.y + obj_con.player.height / 2;

            // strzelanie gracza
            if (klawisze[4]) {
                if (obj_con.player.shot()) {
                    obj_con.audio_player.soundPlayer(obj_con.player.sound_of_shooting);
                    obj_con.player_bullets_list
                            .add(new PlayerBullet0(obj_con.player.x + obj_con.player.width / 2, obj_con.player.y));
                }
            }

        }

        public void run() {
            if (obj_con.state == 1) {
                // Po każdej zakończonej fali następuje kilku sekundowa przerwa oraz przejście
                // do następnej fali
                if (obj_con.finished_wave) {
                    now = System.nanoTime() / 1000000;
                    if (now - obj_con.end_of_wave > obj_con.delay_between_waves) {
                        if (obj_con.current_wave == 2) {
                            obj_con.state = 2;
                        } else {
                            obj_con.last_wave_event = System.nanoTime() / 1000000;
                            obj_con.current_wave++;
                            obj_con.wave_event = 1;
                            obj_con.finished_wave = false;
                        }
                    }
                }
                piloting();
                obj_con.update();
            }
            repaint();
            if (obj_con.state != 1) {
                timer.cancel();
            }

        }

    }

    Project() {
        super("Gra");
        setBounds(500, 200, 806, 635);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        summary = new Summary();
        obj_con = new ObjectControl();
        setVisible(true);
        createBufferStrategy(2);
        klawisze = new boolean[9];
        background0 = new ImageIcon("images/background0.jpg").getImage();
        timer = new Timer();
        timer.scheduleAtFixedRate(new Loop(), 0, 1000 / 60);
        this.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        klawisze[0] = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        klawisze[1] = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        klawisze[2] = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        klawisze[3] = true;
                        break;
                    case KeyEvent.VK_Z:
                        klawisze[4] = true;
                        break;
                    case KeyEvent.VK_SHIFT:
                        klawisze[5] = true;
                        break;
                    case KeyEvent.VK_S:
                        klawisze[6] = true;
                        break;
                    case KeyEvent.VK_L:
                        klawisze[7] = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        klawisze[8] = true;
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        klawisze[0] = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        klawisze[1] = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        klawisze[2] = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        klawisze[3] = false;
                        break;
                    case KeyEvent.VK_Z:
                        klawisze[4] = false;
                        break;
                    case KeyEvent.VK_SHIFT:
                        klawisze[5] = false;
                        break;
                    case KeyEvent.VK_S:
                        klawisze[6] = false;
                        save_latch = false;
                        break;
                    case KeyEvent.VK_L:
                        klawisze[7] = false;
                        load_latch = false;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        klawisze[8] = false;
                        break;
                }
            }

            public void keyTyped(KeyEvent e) {
            }
        });
    }

    public static void main(String[] args) {
        Project okno = new Project();

        okno.repaint();

    }

    public void drawGame(Graphics2D g2d) {
        // rysowanie tła
        g2d.drawImage(background0, 0, 0, null);
        // rysowanie gracza
        g2d.drawImage(obj_con.player.sprite, (int) obj_con.player.x, (int) obj_con.player.y, obj_con.player.width,
                obj_con.player.height, null);
        // wypisanie punktów zdrowia gracza
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("" + this.obj_con.player.health, (int) this.obj_con.player.x, (int) this.obj_con.player.y);
        // rysowanie przeciwników
        for (int i = obj_con.enemies_list.size() - 1; i >= 0; i--) {
            g2d.rotate(Math.toRadians(180));
            g2d.translate(-obj_con.enemies_list.get(i).x - obj_con.enemies_list.get(i).width,
                    -obj_con.enemies_list.get(i).y - obj_con.enemies_list.get(i).height);
            g2d.drawImage(obj_con.enemies_list.get(i).sprite, 0, 0, obj_con.enemies_list.get(i).width,
                    obj_con.enemies_list.get(i).height, null);
            g2d.translate(obj_con.enemies_list.get(i).x + obj_con.enemies_list.get(i).width,
                    obj_con.enemies_list.get(i).y + obj_con.enemies_list.get(i).height);
            g2d.rotate(-Math.toRadians(180));
            g2d.drawString("" + (int) obj_con.enemies_list.get(i).health, (int) obj_con.enemies_list.get(i).x,
                    (int) obj_con.enemies_list.get(i).y);
        }
        // rysowanie pocisków gracza
        g2d.setColor(Color.red);
        for (int i = 0; i < obj_con.player_bullets_list.size(); i++) {
            g2d.fillOval((int) obj_con.player_bullets_list.get(i).x, (int) obj_con.player_bullets_list.get(i).y,
                    (int) obj_con.player_bullets_list.get(i).bullet_radius * 2,
                    (int) obj_con.player_bullets_list.get(i).bullet_radius * 2);
        }
        // rysowanie pocisków przeciwników
        g2d.setColor(Color.green);
        for (int i = 0; i < obj_con.enemies_bullets_list.size(); i++) {
            g2d.fillOval((int) obj_con.enemies_bullets_list.get(i).x, (int) obj_con.enemies_bullets_list.get(i).y,
                    (int) obj_con.enemies_bullets_list.get(i).bullet_radius * 2,
                    (int) obj_con.enemies_bullets_list.get(i).bullet_radius * 2);
        }
        g2d.setColor(Color.orange);
        for (int i = 0; i < obj_con.coins_list.size(); i++) {
            g2d.fillOval((int) obj_con.coins_list.get(i).x, (int) obj_con.coins_list.get(i).y,
                    obj_con.coins_list.get(i).width, obj_con.coins_list.get(i).height);
        }
        // wyświetlenie obszaru kolizji gracza
        if (klawisze[5]) {
            g2d.setColor(Color.yellow);
            g2d.fillOval((int) obj_con.player.x + obj_con.player.width / 2 - obj_con.player.collider_radius,
                    (int) obj_con.player.y + obj_con.player.height / 2 - obj_con.player.collider_radius,
                    obj_con.player.collider_radius * 2, obj_con.player.collider_radius * 2);
        }
        // wypisanie punktów gracza
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Score: " + this.obj_con.player.score, 0, 30);
    }

    public void drawWin(Graphics2D g2d) {
        g2d.setColor(Color.green);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Wygrałeś!", 0, 30);
    }

    public void drawLose(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Przegrałeś!", 0, 30);
    }

    public void paint(Graphics g) {

        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bstrategy.getDrawGraphics();
        g2d.translate(0, 32);
        if (obj_con.state == 1) {
            drawGame(g2d);
        } else {
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, 800, 600);
            if (obj_con.state == 0) {
                drawLose(g2d);
            } else if (obj_con.state == 2) {
                drawWin(g2d);
            }
            summary.gameSummary(this.obj_con.player.score);
            g2d.setColor(Color.white);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("name | score", 0, 70);
            for (int i = 0; i < 10; i++) {
                if (summary.players_scores[i].name != "") {
                    g2d.drawString(
                            (i + 1) + ". " + summary.players_scores[i].name + " " + summary.players_scores[i].score, 0,
                            70 + (i + 1) * 30);
                } else {
                    g2d.drawString((i + 1) + ". ", 0, 70 + (i + 1) * 30);
                }
            }

        }
        g2d.dispose();
        bstrategy.show();
    }

    // wszystko poniżej jest związane z zapisywaniem i odczytywaniem
    public String prepareStr(String str) {
        return str.substring(0, str.length() - 1);
    }

    public void save() {
        File f = new File("save.txt");
        try {
            f.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (f.canWrite()) {
            try {
                FileWriter fw = new FileWriter(f, false);
                Formatter fm = new Formatter(f);
                Scanner sf = new Scanner(f);
                fm.format("%s \r\n", "Parameters of Game");
                fm.format("%s \r\n", summary.player_name);
                fm.format("%s \r\n", this.obj_con.current_wave);
                fm.format("%s \r\n", this.obj_con.wave_event);
                fm.format("%s \r\n",
                        this.obj_con.delay_between_waves - (System.nanoTime() / 1000000 - this.obj_con.end_of_wave));
                fm.format("%s \r\n", obj_con.finished_wave);
                fm.format("%s \r\n", this.obj_con.fromLastEvent());
                fm.format("%s \r\n", "Parameters of Player");
                fm.format("%s \r\n", this.obj_con.player.x);
                fm.format("%s \r\n", this.obj_con.player.y);
                fm.format("%s \r\n", this.obj_con.player.health);
                fm.format("%s \r\n", this.obj_con.player.score);
                fm.format("%s \r\n", this.obj_con.player.untilNextShot());
                fm.format("%s \r\n", "List of Player Bullets");
                fm.format("%s \r\n", this.obj_con.player_bullets_list.size());
                for (int i = 0; i < this.obj_con.player_bullets_list.size(); i++) {
                    fm.format("%s \r\n", this.obj_con.player_bullets_list.get(i).center_x);
                    fm.format("%s \r\n", this.obj_con.player_bullets_list.get(i).center_y);
                    fm.format("%s \r\n", this.obj_con.player_bullets_list.get(i).bullet_radius);
                }
                fm.format("%s \r\n", "List of Enemies");
                fm.format("%s \r\n", this.obj_con.enemies_list.size());
                for (int i = 0; i < this.obj_con.enemies_list.size(); i++) {
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).type);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).belongs_to_wave);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).belongs_to_event);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).center_x);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).center_y);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).alfa);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).radius);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).rotation);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).health);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).rotation);
                    fm.format("%s \r\n", this.obj_con.enemies_list.get(i).untilNextShot());
                }
                fm.format("%s \r\n", "List of Enemies Bullets");
                fm.format("%s \r\n", this.obj_con.enemies_bullets_list.size());
                for (int i = 0; i < this.obj_con.enemies_bullets_list.size(); i++) {
                    fm.format("%s \r\n", this.obj_con.enemies_bullets_list.get(i).center_x);
                    fm.format("%s \r\n", this.obj_con.enemies_bullets_list.get(i).center_y);
                    fm.format("%s \r\n", this.obj_con.enemies_bullets_list.get(i).alfa);
                    fm.format("%s \r\n", this.obj_con.enemies_bullets_list.get(i).radius);
                    fm.format("%s \r\n", this.obj_con.enemies_bullets_list.get(i).getType());
                }
                fm.format("%s \r\n", "List of Coins");
                fm.format("%s \r\n", this.obj_con.coins_list.size());
                for (int i = 0; i < this.obj_con.coins_list.size(); i++) {
                    fm.format("%s \r\n", this.obj_con.coins_list.get(i).x + this.obj_con.coins_list.get(i).width / 2);
                    fm.format("%s \r\n", this.obj_con.coins_list.get(i).y + this.obj_con.coins_list.get(i).height / 2);
                }

                fm.close();
                fw.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void load() {
        File f = new File("save.txt");
        if (f.exists()) {
            if (f.canRead()) {
                try {
                    while (this.obj_con.enemies_list.size() > 0) {
                        this.obj_con.enemies_list.remove(0);
                    }
                    while (this.obj_con.enemies_bullets_list.size() > 0) {
                        this.obj_con.enemies_bullets_list.remove(0);
                    }
                    while (this.obj_con.player_bullets_list.size() > 0) {
                        this.obj_con.player_bullets_list.remove(0);
                    }
                    while (this.obj_con.coins_list.size() > 0) {
                        this.obj_con.coins_list.remove(0);
                    }
                    Scanner sf = new Scanner(f);
                    String title;
                    int lists_size;
                    while (sf.hasNextLine()) {
                        title = prepareStr(sf.nextLine());
                        if (title.equals("Parameters of Game")) {
                            this.summary = new Summary(prepareStr(sf.nextLine()));
                            this.obj_con.current_wave = Integer.parseInt(prepareStr(sf.nextLine()));
                            this.obj_con.wave_event = Integer.parseInt(prepareStr(sf.nextLine()));
                            this.obj_con.end_of_wave = System.nanoTime() / 1000000
                                    - Long.parseLong(prepareStr(sf.nextLine()));
                            this.obj_con.finished_wave = Boolean.parseBoolean(prepareStr(sf.nextLine()));
                            this.obj_con.last_wave_event = System.nanoTime() / 1000000
                                    - Long.parseLong(prepareStr(sf.nextLine()));

                        } else if (title.equals("Parameters of Player")) {
                            this.obj_con.player.x = Float.parseFloat(sf.nextLine());
                            this.obj_con.player.y = Float.parseFloat(sf.nextLine());
                            this.obj_con.player.health = Integer.parseInt(prepareStr(sf.nextLine()));
                            this.obj_con.player.score = Integer.parseInt(prepareStr(sf.nextLine()));
                            this.obj_con.player.last_shot = System.nanoTime() / 1000000
                                    - Long.parseLong(prepareStr(sf.nextLine()));
                        } else if (title.equals("List of Player Bullets")) {
                            lists_size = Integer.parseInt(prepareStr(sf.nextLine()));
                            for (int i = 0; i < lists_size; i++) {
                                float center_x = Float.parseFloat(sf.nextLine());
                                float center_y = Float.parseFloat(sf.nextLine());
                                float bullet_radius = Float.parseFloat(sf.nextLine());
                                this.obj_con.player_bullets_list
                                        .add(new PlayerBullet0(center_x, center_y + bullet_radius));
                            }
                        } else if (title.equals("List of Enemies")) {
                            lists_size = Integer.parseInt(prepareStr(sf.nextLine()));
                            for (int i = 0; i < lists_size; i++) {
                                int type = Integer.parseInt(prepareStr(sf.nextLine()));
                                int belongs_to_wave = Integer.parseInt(prepareStr(sf.nextLine()));
                                int belongs_to_event = Integer.parseInt(prepareStr(sf.nextLine()));
                                float center_x = Float.parseFloat(sf.nextLine());
                                float center_y = Float.parseFloat(sf.nextLine());
                                double alfa = Double.parseDouble(sf.nextLine());
                                float radius = Float.parseFloat(sf.nextLine());
                                int Rotation = Integer.parseInt(prepareStr(sf.nextLine()));
                                int health = Integer.parseInt(prepareStr(sf.nextLine()));
                                int rotation = Integer.parseInt(prepareStr(sf.nextLine()));
                                long first_shot_delay = -Long.parseLong(
                                        (String) this.obj_con.factory.enemy_modules_list.get(type).get("shot_delay"))
                                        + Long.parseLong(prepareStr(sf.nextLine()))
                                        - Long.parseLong((String) this.obj_con.factory.enemy_modules_list.get(type)
                                                .get("first_shot_delay"));
                                this.obj_con.enemies_list
                                        .add(new Enemy0(center_x, center_y, alfa, radius, first_shot_delay, type,
                                                this.obj_con.factory, belongs_to_wave, belongs_to_event));
                                this.obj_con.enemies_list.get(i).health = health;
                                this.obj_con.enemies_list.get(i).rotation = rotation;
                            }
                        } else if (title.equals("List of Enemies Bullets")) {
                            lists_size = Integer.parseInt(prepareStr(sf.nextLine()));
                            for (int i = 0; i < lists_size; i++) {
                                float center_x = Float.parseFloat(sf.nextLine());
                                float center_y = Float.parseFloat(sf.nextLine());
                                double alfa = Double.parseDouble(sf.nextLine());
                                float radius = Float.parseFloat(sf.nextLine());
                                int type = Integer.parseInt(prepareStr(sf.nextLine()));
                                this.obj_con.enemies_bullets_list.add(
                                        new EnemyBullet0(center_x, center_y, alfa, radius, type, this.obj_con.factory));
                            }
                        } else if (title.equals("List of Coins")) {
                            lists_size = Integer.parseInt(prepareStr(sf.nextLine()));
                            for (int i = 0; i < lists_size; i++) {
                                float x = Float.parseFloat(sf.nextLine());
                                float y = Float.parseFloat(sf.nextLine());
                                this.obj_con.coins_list.add(new Coin(x, y));
                            }
                        }

                    }
                    sf.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        }

    }
}
