package sem.group47.entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import sem.group47.entity.Animation;
import sem.group47.entity.MapObject;
import sem.group47.tilemap.TileMap;

public class Magiron extends Enemy {
	private MapObject target;

	public Magiron(final TileMap tm) {
		super(tm);

		setScorePoints(100);
		setWidth(90);
		setHeight(112);
		setCwidth(30);
		setCheight(30);
		setMovSpeed(1.3);

		setFacingRight(true);

		BufferedImage[] animationSprites = null;
		try {
			ImageReader reader = ImageIO.getImageReadersByFormatName("gif")
					.next();
			File input = new File("src/main/resources/enemies/magiaaron.gif");
			ImageInputStream stream = ImageIO.createImageInputStream(input);
			reader.setInput(stream);

			int count = reader.getNumImages(true);
			animationSprites = new BufferedImage[count];
			for (int index = 0; index < count; index++) {
				BufferedImage frame = reader.read(index);
				animationSprites[index] = frame;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(animationSprites);
		animation.setDelay(60);
	}

	@Override
	public final void draw(final Graphics2D g) {
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (getXpos() - getWidth()
					/ (double) 2),
					(int) (getYpos() - getHeight() / (double) 2), getWidth(),
					getHeight(), null);
		} else {
			g.drawImage(animation.getImage(), (int) (getXpos() + getWidth()
					/ (double) 2),
					(int) (getYpos() - getHeight() / (double) 2), -getWidth(),
					getHeight(), null);
		}
	}

	public final void setTarget(final MapObject t) {
		target = t;
	}

	@Override
	public final void update() {
		animation.update();
		if (target != null) {
			moveTowards(target);
		}
	}

	public final void moveTowards(final MapObject mo) {
		moveTowards(mo.getx(), mo.gety());
	}

	public final void moveTowards(final double x, final double y) {
		double newX = getx();
		double newY = gety();
		double speed = getMovSpeed();
		if (x - speed > getx()) {
			newX += speed;
			facingRight = false;
		} else if (x + speed < getx()) {
			newX -= speed;
			facingRight = true;
		}
		if (y - speed > gety()) {
			newY += speed;
		} else if (y + speed < gety()) {
			newY -= speed;
		}
		setPosition(newX, newY);
	}

	@Override
	public final void hit() {
		caught = false;
	}

	@Override
	public final void setCaught() {
		caught = false;
	}

	@Override
	public final void setCaught(final boolean isCaught) {
		caught = false;
	}

}
