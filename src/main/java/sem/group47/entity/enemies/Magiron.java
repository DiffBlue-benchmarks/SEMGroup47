package sem.group47.entity.enemies;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import sem.group47.gamestate.LevelState;
import sem.group47.tilemap.TileMap;

public class Magiron extends Enemy {

	public int i;

	public Magiron(TileMap tm) {
		super(tm);

		setScorePoints(100);
		setWidth(30);
		setHeight(30);
		setCwidth(30);
		setCheight(30);
		setMovSpeed(1);
		setMaxSpeed(2);

		setFacingRight(true);

		if (Math.round(Math.random()) == 0) {
			setLeft(true);
		} else {
			setRight(true);
		}

		try {
			setSprite(ImageIO.read(getClass().getResourceAsStream(
					"/hud/Bubble_Heart.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getNextXPosition() {
		if (isRight()) {
			setDx(getDx() + getMovSpeed());
			if (getDx() > getMaxSpeed()) {
				setDx(getMaxSpeed());
			}
		} else if (isLeft()) {
			setDx(getDx() - getMovSpeed());
			if (getDx() < -getMaxSpeed()) {
				setDx(-getMaxSpeed());
			}
		}
		if (getDx() > 0) {
			setFacingRight(true);
		} else if (getDx() < 0) {
			setFacingRight(false);
		}
	}

	private void getNextYPosition() {
		if (isUp()) {
			setDy(getDy() - getMovSpeed());
			if (getDy() < -getMaxSpeed()) {
				setDy(-getMaxSpeed());
			}
		} else if (isDown()) {
			setDy(getDy() + getMovSpeed());
			if (getDy() > getMaxSpeed()) {
				setDy(getMaxSpeed());
			}
		}
	}

	private void movementDice() {
		double dice = Math.random() * 100;
		if (dice < 25) {
			setUp(true);
			setDown(false);
			setRight(false);
			setLeft(false);
		} else if (dice < 50) {
			setLeft(true);
			setRight(false);
			setUp(false);
			setDown(false);
		} else if (dice < 75) {
			setLeft(false);
			setRight(true);
			setUp(false);
			setDown(false);
		} else {
			setDown(true);
			setUp(false);
			setLeft(false);
			setRight(false);
		}
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - LevelState.time > 9000) {

			i++;
			if (i == 20) {
				movementDice();
				i = 0;
			}
			getNextXPosition();
			getNextYPosition();
			checkTileMapCollision();
			setPosition(getXposNew(), getYposNew());
		}
	}

	@Override
	public void checkYCollision() {
		calculateCorners(xpos, ydest);
		if (dy < 0) {
			if (topLeftBlocked || topRightBlocked) {
				dy = 0;
				yposNew = currRow * tileSize + cheight / 2;
			} else {
				if (yposNew <= 60) {
					yposNew = 560;
				} else {
					yposNew += dy;
				}

			}
		} else if (dy > 0) {
			if (bottomLeftBlocked || bottomRightBlocked) {
				dy = 0;
				falling = false;
				yposNew = (currRow + 1) * tileSize - cheight / 2;
			} else {
				falling = true;
				yposNew += dy;

				if (yposNew >= tileMap.getHeight() - 15) {
					yposNew = 3 * tileMap.getTileSize();
				}
			}

		}

		if (!falling) {
			calculateCorners(xpos, ydest + 1);
			if (!bottomLeftBlocked && !bottomRightBlocked) {
				falling = true;
			}
		}
	}

	@Override
	public final void draw(final Graphics2D g) {
		 if (System.currentTimeMillis() - LevelState.time > 9000) {
		super.draw(g);
		}
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
