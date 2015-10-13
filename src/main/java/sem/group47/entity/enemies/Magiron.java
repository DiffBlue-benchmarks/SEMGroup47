package sem.group47.entity.enemies;

import javax.imageio.ImageIO;

import sem.group47.entity.MapObject;
import sem.group47.entity.Player;
import sem.group47.tilemap.TileMap;

public class Magiron extends Enemy {	
	private MapObject target;

	public Magiron(TileMap tm) {
		super(tm);

		setScorePoints(100);
		setWidth(90);
		setHeight(112);
		setCwidth(30);
		setCheight(30);
		setMovSpeed(1.3);

		setFacingRight(true);

		if (Math.round(Math.random()) == 0) {
			setLeft(true);
		} else {
			setRight(true);
		}

		try {
			setSprite(ImageIO.read(getClass().getResourceAsStream(
					"/enemies/magiaaron.gif")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setTarget(MapObject t) {
		target = t;
	}

	@Override
	public void update() {
		if(target != null) {
			moveTowards(target);
		}
	}
	
	public void moveTowards(MapObject mo) {
		moveTowards(mo.getx(), mo.gety());
	}
	
	public void moveTowards(double x, double y) {
		double newX = getx();
		double newY = gety();
		double speed = getMovSpeed();
		if (x - speed > getx() ) {
			newX += speed;
			facingRight = false;
		} else if (x + speed < getx()) {
			newX -= speed;
			facingRight = true;
		}
		if(y - speed > gety()) {
			newY += speed;
		} else if (y + speed < gety()) {
			newY -= speed;
		}
		setPosition(newX, newY);
	}

	@Override
	public void hit() {
		caught = false;
	}

	@Override
	public void setCaught() {
		caught = false;
	}

	@Override
	public void setCaught(boolean isCaught) {
		caught = false;
	}

}
