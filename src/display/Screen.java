package display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import entities.Entity;
import entities.Food;
import entities.Snake;
import levels.Level;
import menus.GameOverMenu;
import menus.Menu;
import particles.Particle;

public class Screen extends JPanel {
  public static Screen self;

  public static int CELL_SIZE = 30;

  public static final int WIDTH = CELL_SIZE * 21;
  public static final int HEIGHT = WIDTH;

  private Snake snake;

  private Food food;

  public static List<Entity> walls;
  public static List<Particle> particles;

  public boolean paused = false;

  private Level level;

  private Menu menu;

  public Screen() {
    self = this;
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setBackground(Color.GRAY);
    snake = new Snake();
    level = new Level();
    walls = level.loadLevel("outline");
    particles = new ArrayList<Particle>();
    food = new Food();
  }

  public void restart() {
    snake = new Snake();
    level = new Level();
    walls = level.loadLevel("outline");
    particles = new ArrayList<Particle>();
    food = new Food();
  }

  private void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    snake.draw(g2d);
    food.draw(g2d);
    for (Entity wall : walls) {
      wall.draw(g2d);
    }
    for (Particle p : particles) {
      p.draw(g2d);
    }
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    if (menu != null) {
      menu.draw(g2d);
    }
  }

  public void tick(boolean[] keys) {
    if (menu != null) {
      menu.tick(keys);
    } else {
      snake.update(keys);
      snake.eat(food);
      if (snake.isDead()) {
        // gameover code here
        setMenu(new GameOverMenu(snake.score));
        paused = true;
      }
      for (int i = particles.size() - 1; i >= 0; i--) {
        Particle p = particles.get(i);
        p.update();
        if (!p.alive) particles.remove(p);
      }
    }
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }

  public void render() {
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }
}
