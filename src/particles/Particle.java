package particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Particle {
  private final int ORIG_FADE_TIME = 20;

  private int size;

  private double x, y, z;
  private double xVel, yVel, zVel;

  private int life;

  private int fadeTime;
  private float alpha;

  private Color color;

  public boolean alive;

  private Random r;

  public Particle(double x, double y) {
    r = new Random();

    this.x = x;
    this.y = y;

    this.size = r.nextInt(10) + 5;
    this.life = r.nextInt(100) + 50;
    this.fadeTime = ORIG_FADE_TIME;
    this.alpha = 1f;

    this.color = Color.DARK_GRAY;

    this.xVel = (r.nextDouble() * 5 - 2.5) * 150.0 / life;
    this.yVel = (r.nextDouble() * 5 - 2.5) * 150.0 / life;

    this.alive = true;
  }

  public void update() {
    if (this.life > ORIG_FADE_TIME) {
      --this.life;
    } else {
      fade();
    }
    this.x += xVel;
    this.y += yVel;
  }

  public void draw(Graphics2D g2d) {
    AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    g2d.setComposite(acomp);
    g2d.setColor(this.color);
    g2d.fillOval((int) this.x, (int) this.y, this.size, this.size);
  }

  private void fade() {
    if (this.fadeTime > 0) {
      --this.fadeTime;
      this.alpha -= 1.0 / ORIG_FADE_TIME;
      if(alpha <= 0) this.alpha = 0;
    } else {
      alive = false;
    }
  }
}
