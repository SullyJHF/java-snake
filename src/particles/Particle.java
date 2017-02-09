package particles;

import java.awt.Graphics2D;
import java.util.Random;

public class Particle {
  private double x, y, z;
  private double xVel, yVel, zVel;

  private int life;

  public boolean alive;

  private Random r;

  public Particle(double x, double y) {
    r = new Random();

    this.x = x;
    this.y = y;
    this.life = r.nextInt(200) + 100;

    this.xVel = r.nextDouble() * 5 - 2.5;
    this.yVel = r.nextDouble() * 5 - 2.5;

    this.alive = true;
  }

  public void update() {
    if(this.life > 0) {
      --this.life;
    } else {
      this.alive = false;
    }
    this.x += xVel;
    this.y += yVel;
  }

  public void draw(Graphics2D g2d) {
    g2d.fillOval((int) this.x, (int) this.y, 2, 2);
  }
}
