import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player {

	public static enum PLAYERTYPE {
		MAGE, HEAVY, ARCHER, I_HAVE_LITERALLY_NO_IDEA_OTHER_CLASSES,
	};

	public static enum moveDirection{
		UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, NONE,
	};
	
	public moveDirection moveDir = moveDirection.NONE;
	
	public int x = 100, y = 100, motionX, motionY, width = 48, height = 64,
			offSet = 8, aISmartTick = 0, aIDiff = 3;

	public float health = 100;

	public int playerNum;

	public boolean blocking = false;

	public boolean aIGo = false;
	
	public boolean aI = false, aISmart = false;
	
	public boolean detonate = false;

	public int strafeTick = 0;

	public int speedTicks = 0;

	public SpriteSheetReader reader;
	
	private static Random random;
	
	public boolean strafe = false;

	public int faction = 1; // 1 blackguard 2 moonshit

	public AffineTransform at;

	public int cool1 = 1000; // cooldown time in milliseconds for main attack
	public int cool2 = 5000;// cooldown time in milliseconds for the ability 1
	public int cool3 = 6000; // cooldown time in milliseconds for ability 2
	public long tack1Start = -cool1, tack2Start = -cool2, tack3Start = -cool3;

	public int speed = 2;
	public int aISpeed = 2; 

	public Animation bMageS, bMageD, bMageL, bMageU, bMageR, rMageS, rMageD,
			rMageL, rMageU, rMageR, bArcherS, bArcherD, bArcherL, bArcherU,
			bArcherR, rArcherS, rArcherD, rArcherL, rArcherU, rArcherR,
			bHeavyS, bHeavyD, bHeavyL, bHeavyU, bHeavyR, rHeavyS, rHeavyD,
			rHeavyL, rHeavyU, rHeavyR;

	public BufferedImage[] bMageSI, bMageDI, bMageLI, bMageUI, bMageRI,
			rMageSI, rMageDI, rMageLI, rMageUI, rMageRI, bArcherSI, bArcherDI,
			bArcherLI, bArcherUI, bArcherRI, rArcherSI, rArcherDI, rArcherLI,
			rArcherUI, rArcherRI, bHeavySI, bHeavyDI, bHeavyLI, bHeavyUI,
			bHeavyRI, rHeavySI, rHeavyDI, rHeavyLI, rHeavyUI, rHeavyRI;

	public int pointing = 1; // 1 up 2 right 3 down 4 left

	public float maxHealth = 100;
	
	public Rectangle front, back, left, right, player;
	public Rectangle frontB, backB, leftB, rightB, playerB;
	public Rectangle playerShoot;

	public boolean xGood = false, yGood = false;
	
	public Rectangle screenTop, screenBottom, screenLeft, screenRight;

	public PLAYERTYPE type;

	public Player(PLAYERTYPE p, int playerNumber, boolean aI) {
		random = new Random();
		reader = new SpriteSheetReader();
		this.playerNum = playerNumber;
		if (playerNum == 2) {
			x = 700;
			y = 100;
		}
		this.aI = aI;
		front = new Rectangle(x + offSet, y, width, 0);
		back = new Rectangle(x + offSet, y + height - 1, width, 1);
		left = new Rectangle(x + offSet, y, 1, height);
		right = new Rectangle(x + width - 1, y, 1, height);
		frontB = new Rectangle(x + offSet, y, width, 0);
		backB = new Rectangle(x + offSet, y + height - 1, width, 1);
		leftB = new Rectangle(x + offSet, y, 1, height);
		rightB = new Rectangle(x + width - 1, y, 1, height);
		player = new Rectangle(x + offSet, y, width, height);
		playerB = player.getBounds();
		screenTop = new Rectangle(0, 0, 1280, 1);
		screenBottom = new Rectangle(0, 720, 1280, 1);
		screenLeft = new Rectangle(0, 0, 1, 720);
		screenRight = new Rectangle(1280, 0, 1, 720);
		playerShoot = new Rectangle(x - 16, y - 16, 96, 96);
		this.type = p;
		bMageSI = reader
				.getSprites(
						4,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Mage/MAGES.png"));
		bMageS = new Animation(9, bMageSI[0], bMageSI[1], bMageSI[2],
				bMageSI[3]);
		bMageUI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Mage/MAGEU.png"));
		bMageU = new Animation(3, bMageUI[0], bMageUI[1], bMageUI[2],
				bMageUI[3], bMageUI[4], bMageUI[5]);
		bMageDI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Mage/MAGED.png"));
		bMageD = new Animation(3, bMageDI[0], bMageDI[1], bMageDI[2],
				bMageDI[3], bMageDI[4], bMageDI[5]);
		bMageRI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Mage/MAGER.png"));
		bMageR = new Animation(3, bMageRI[0], bMageRI[1], bMageRI[2],
				bMageRI[3], bMageRI[4], bMageRI[5]);
		bMageLI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Mage/MAGEL.png"));
		bMageL = new Animation(3, bMageLI[0], bMageLI[1], bMageLI[2],
				bMageLI[3], bMageLI[4], bMageLI[5]);
		bHeavySI = reader
				.getSprites(
						4,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/heavy/HEAVYS.png"));
		bHeavyS = new Animation(9, bHeavySI[0], bHeavySI[1], bHeavySI[2],
				bHeavySI[3]);
		bHeavyUI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/heavy/HEAVYU.png"));
		bHeavyU = new Animation(3, bHeavyUI[0], bHeavyUI[1], bHeavyUI[2],
				bHeavyUI[3], bHeavyUI[4], bHeavyUI[5]);
		bHeavyDI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/heavy/HEAVYD.png"));
		bHeavyD = new Animation(3, bHeavyDI[0], bHeavyDI[1], bHeavyDI[2],
				bHeavyDI[3], bHeavyDI[4], bHeavyDI[5]);
		bHeavyLI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/heavy/HEAVYL.png"));
		bHeavyL = new Animation(3, bHeavyLI[0], bHeavyLI[1], bHeavyLI[2],
				bHeavyLI[3], bHeavyLI[4], bHeavyLI[5]);
		bHeavyRI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/heavy/HEAVYR.png"));
		bHeavyR = new Animation(3, bHeavyRI[0], bHeavyRI[1], bHeavyRI[2],
				bHeavyRI[3], bHeavyRI[4], bHeavyRI[5]);
		bArcherSI = reader
				.getSprites(
						4,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Archer/ARCHERS.png"));
		bArcherS = new Animation(9, bArcherSI[0], bArcherSI[1], bArcherSI[2],
				bArcherSI[3]);
		bArcherUI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Archer/ARCHERU.png"));
		bArcherU = new Animation(3, bArcherUI[0], bArcherUI[1], bArcherUI[2],
				bArcherUI[3], bArcherUI[4], bArcherUI[5]);
		bArcherDI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Archer/ARCHERD.png"));
		bArcherD = new Animation(3, bArcherDI[0], bArcherDI[1], bArcherDI[2],
				bArcherDI[3], bArcherDI[4], bArcherDI[5]);
		bArcherLI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Archer/ARCHERL.png"));
		bArcherL = new Animation(3, bArcherLI[0], bArcherLI[1], bArcherLI[2],
				bArcherLI[3], bArcherLI[4], bArcherLI[5]);
		bArcherRI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Archer/ARCHERR.png"));
		bArcherR = new Animation(3, bArcherRI[0], bArcherRI[1], bArcherRI[2],
				bArcherRI[3], bArcherRI[4], bArcherRI[5]);
		bMageSI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/blackguard/Mage/MAGES.png"));

		rMageSI = reader
				.getSprites(
						4,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Mage/MAGES.png"));
		rMageS = new Animation(9, rMageSI[0], rMageSI[1], rMageSI[2],
				rMageSI[3]);
		rMageUI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Mage/MAGEU.png"));
		rMageU = new Animation(3, rMageUI[0], rMageUI[1], rMageUI[2],
				rMageUI[3], rMageUI[4], rMageUI[5]);
		rMageDI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Mage/MAGED.png"));
		rMageD = new Animation(3, rMageDI[0], rMageDI[1], rMageDI[2],
				rMageDI[3], rMageDI[4], rMageDI[5]);
		rMageRI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Mage/MAGER.png"));
		rMageR = new Animation(3, rMageRI[0], rMageRI[1], rMageRI[2],
				rMageRI[3], rMageRI[4], rMageRI[5]);
		rMageLI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Mage/MAGEL.png"));
		rMageL = new Animation(3, rMageLI[0], rMageLI[1], rMageLI[2],
				rMageLI[3], rMageLI[4], rMageLI[5]);
		rHeavySI = reader
				.getSprites(
						4,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/heavy/HEAVYS.png"));
		rHeavyS = new Animation(9, rHeavySI[0], rHeavySI[1], rHeavySI[2],
				rHeavySI[3]);
		rHeavyUI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/heavy/HEAVYU.png"));
		rHeavyU = new Animation(3, rHeavyUI[0], rHeavyUI[1], rHeavyUI[2],
				rHeavyUI[3], rHeavyUI[4], rHeavyUI[5]);
		rHeavyDI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/heavy/HEAVYD.png"));
		rHeavyD = new Animation(3, rHeavyDI[0], rHeavyDI[1], rHeavyDI[2],
				rHeavyDI[3], rHeavyDI[4], rHeavyDI[5]);
		rHeavyLI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/heavy/HEAVYL.png"));
		rHeavyL = new Animation(3, rHeavyLI[0], rHeavyLI[1], rHeavyLI[2],
				rHeavyLI[3], rHeavyLI[4], rHeavyLI[5]);
		rHeavyRI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/heavy/HEAVYR.png"));
		rHeavyR = new Animation(3, rHeavyRI[0], rHeavyRI[1], rHeavyRI[2],
				rHeavyRI[3], rHeavyRI[4], rHeavyRI[5]);
		rArcherSI = reader
				.getSprites(
						4,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Archer/ARCHERS.png"));
		rArcherS = new Animation(9, rArcherSI[0], rArcherSI[1], rArcherSI[2],
				rArcherSI[3]);
		rArcherUI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Archer/ARCHERU.png"));
		rArcherU = new Animation(3, rArcherUI[0], rArcherUI[1], rArcherUI[2],
				rArcherUI[3], rArcherUI[4], rArcherUI[5]);
		rArcherDI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Archer/ARCHERD.png"));
		rArcherD = new Animation(3, rArcherDI[0], rArcherDI[1], rArcherDI[2],
				rArcherDI[3], rArcherDI[4], rArcherDI[5]);
		rArcherLI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Archer/ARCHERL.png"));
		rArcherL = new Animation(3, rArcherLI[0], rArcherLI[1], rArcherLI[2],
				rArcherLI[3], rArcherLI[4], rArcherLI[5]);
		rArcherRI = reader
				.getSprites(
						6,
						imageLoader
								.imageLoader("./MMO-master/src/grahpics/moonshadow/Archer/ARCHERR.png"));
		rArcherR = new Animation(3, rArcherRI[0], rArcherRI[1], rArcherRI[2],
				rArcherRI[3], rArcherRI[4], rArcherRI[5]);

	}

	public void render(Graphics2D g) {
		
		if (front.intersects(screenTop)) {
			//  ("yay");
		}
		if (this.health <= 0) {
			if (this.playerNum == 1) {
				Game.winner = 2;
			}
			if (this.playerNum == 2) {
				Game.winner = 1;
			}
			Game.State = Game.STATE.WIN;
		}
		if (motionX != 0 || motionY != 0) {
			if (faction == 1) {
				if (this.type == PLAYERTYPE.HEAVY) {
					switch (pointing) {
					case 1:
						rHeavyU.drawAnimation(g, x, y, 0);
						break;
					case 2:
						rHeavyR.drawAnimation(g, x, y, 0);
						break;
					case 3:
						rHeavyD.drawAnimation(g, x, y, 0);
						break;
					case 4:
						rHeavyL.drawAnimation(g, x, y, 0);
						break;
					}

				} else if (this.type == PLAYERTYPE.MAGE) {
					switch (pointing) {
					case 1:
						rMageU.drawAnimation(g, x, y, 0);
						break;
					case 2:
						rMageR.drawAnimation(g, x, y, 0);
						break;
					case 3:
						rMageD.drawAnimation(g, x, y, 0);
						break;
					case 4:
						rMageL.drawAnimation(g, x, y, 0);
						break;
					}
				} else if (this.type == PLAYERTYPE.ARCHER) {
					switch (pointing) {
					case 1:
						rArcherU.drawAnimation(g, x, y, 0);
						break;
					case 2:
						rArcherR.drawAnimation(g, x, y, 0);
						break;
					case 3:
						rArcherD.drawAnimation(g, x, y, 0);
						break;
					case 4:
						rArcherL.drawAnimation(g, x, y, 0);
						break;
					}
				}
			} else {
				if (this.type == PLAYERTYPE.HEAVY) {
					switch (pointing) {
					case 1:
						bHeavyU.drawAnimation(g, x, y, 0);
						break;
					case 2:
						bHeavyR.drawAnimation(g, x, y, 0);
						break;
					case 3:
						bHeavyD.drawAnimation(g, x, y, 0);
						break;
					case 4:
						bHeavyL.drawAnimation(g, x, y, 0);
						break;

					}
				} else if (this.type == PLAYERTYPE.MAGE) {
					switch (pointing) {
					case 1:
						bMageU.drawAnimation(g, x, y, 0);
						break;
					case 2:
						bMageR.drawAnimation(g, x, y, 0);
						break;
					case 3:
						bMageD.drawAnimation(g, x, y, 0);
						break;
					case 4:
						bMageL.drawAnimation(g, x, y, 0);
						break;
					}
				} else if (this.type == PLAYERTYPE.ARCHER) {
					switch (pointing) {
					case 1:
						bArcherU.drawAnimation(g, x, y, 0);
						break;
					case 2:
						bArcherR.drawAnimation(g, x, y, 0);
						break;
					case 3:
						bArcherD.drawAnimation(g, x, y, 0);
						break;
					case 4:
						bArcherL.drawAnimation(g, x, y, 0);
						break;
					}
				}
			}
		} else {
			if (faction == 1) {
				if (this.type == PLAYERTYPE.ARCHER) {
					rArcherS.drawAnimation(g, x, y, 0);
				} else if (this.type == PLAYERTYPE.HEAVY) {
					rHeavyS.drawAnimation(g, x, y, 0);
				} else if (this.type == PLAYERTYPE.MAGE) {
					rMageS.drawAnimation(g, x, y, 0);
				}
			} else {
				if (this.type == PLAYERTYPE.ARCHER) {
					bArcherS.drawAnimation(g, x, y, 0);
				} else if (this.type == PLAYERTYPE.HEAVY) {
					bHeavyS.drawAnimation(g, x, y, 0);
				} else if (this.type == PLAYERTYPE.MAGE) {
					bMageS.drawAnimation(g, x, y, 0);
				}
			}
		}
		g.setFont(new Font("Minecraft", Font.PLAIN, 10));
		g.drawString("" + health, x, y);

	

	}

	public void tick() {
		if (strafe) {
			strafeTick++;
		}
		if(strafeTick > 480){
			strafe = false;
			strafeTick = 0;
		}

		at = AffineTransform.getRotateInstance(Math.toRadians(pointing),
				x + 16, y + 16);
		switch (pointing) {
		case 1:
			front.setBounds(x + offSet, y, width, 1);
			back.setBounds(x + offSet, y + height - 1, width, 1);
			left.setBounds(x + offSet, y, 1, height);
			right.setBounds(x + width - 1, y, 1, height);
			frontB.setBounds(x + offSet, y, width, 1);
			backB.setLocation(x + offSet, y + height);
			leftB.setLocation(x + offSet, y);
			rightB.setLocation(x + width - 1, y);
			player.setLocation(x + offSet, y);
			playerB.setLocation(x + offSet, y);
			break;
		case 2:
			front.setBounds(x + width - 1, y, 1, height);
			back.setBounds(x + offSet, y, 1, height);
			left.setBounds(x + offSet, y, width, 1);
			right.setBounds(x + offSet, y + height - 1, width, 1);
			player.setLocation(x + offSet, y);
			playerB.setLocation(x + offSet, y);
			frontB.setBounds(x + offSet, y, width, 1);
			backB.setLocation(x + offSet, y + height);
			leftB.setLocation(x + offSet, y);
			rightB.setLocation(x + width - 1, y);
			break;
		case 3:
			front.setBounds(x + offSet, y + height - 1, width, 1);
			back.setBounds(x + offSet, y, width, 1);
			left.setBounds(x + width - 1, y, 1, height);
			right.setBounds(x + offSet, y, 1, height);
			player.setLocation(x + offSet, y);
			playerB.setLocation(x + offSet, y);
			frontB.setBounds(x + offSet, y, width, 1);
			backB.setLocation(x + offSet, y + height);
			leftB.setLocation(x + offSet, y);
			rightB.setLocation(x + width - 1, y);
			break;
		case 4:
			front.setBounds(x + offSet, y, 1, height);
			back.setBounds(x + width - 1, y, 1, height);
			left.setBounds(x + offSet, y + height - 1, width, 1);
			right.setBounds(x + offSet, y, width, 1);
			player.setLocation(x + offSet, y);
			playerB.setLocation(x + offSet, y);
			frontB.setBounds(x + offSet, y, width, 1);
			backB.setLocation(x + offSet, y + height);
			leftB.setLocation(x + offSet, y);
			rightB.setLocation(x + width - 1, y);
			break;
		}

		//  ("pointing = " + pointing);

		if (Game.aIStatus && playerNum == 1 && Game.State == Game.STATE.GAME){
			aI();
		}
		
		playerShoot.setLocation(x - 16, y - 16);
		if (motionX != 0 || motionY != 0) {
			if (faction == 2) {
				if (this.type == PLAYERTYPE.HEAVY) {
					switch (pointing) {
					case 1:
						bHeavyU.runAnimation();
						break;
					case 2:
						bHeavyR.runAnimation();
						break;
					case 3:
						bHeavyD.runAnimation();
						break;
					case 4:
						bHeavyL.runAnimation();
						break;
					}
				} else if (this.type == PLAYERTYPE.MAGE) {
					switch (pointing) {
					case 1:
						bMageU.runAnimation();
						break;
					case 2:
						bMageR.runAnimation();
						break;
					case 3:
						bMageD.runAnimation();
						break;
					case 4:
						bMageL.runAnimation();
						break;
					}
				} else if (this.type == PLAYERTYPE.ARCHER) {
					switch (pointing) {
					case 1:
						bArcherU.runAnimation();
						break;
					case 2:
						bArcherR.runAnimation();
						break;
					case 3:
						bArcherD.runAnimation();
						break;
					case 4:
						bArcherL.runAnimation();
						break;
					}
				}
			} else {
				if (this.type == PLAYERTYPE.HEAVY) {
					switch (pointing) {
					case 1:
						rHeavyU.runAnimation();
						break;
					case 2:
						rHeavyR.runAnimation();
						break;
					case 3:
						rHeavyD.runAnimation();
						break;
					case 4:
						rHeavyL.runAnimation();
						break;

					}
				} else if (this.type == PLAYERTYPE.MAGE) {
					switch (pointing) {
					case 1:
						rMageU.runAnimation();
						break;
					case 2:
						rMageR.runAnimation();
						break;
					case 3:
						rMageD.runAnimation();
						break;
					case 4:
						rMageL.runAnimation();
						break;
					}
				} else if (this.type == PLAYERTYPE.ARCHER) {
					switch (pointing) {
					case 1:
						rArcherU.runAnimation();
						break;
					case 2:
						rArcherR.runAnimation();
						break;
					case 3:
						rArcherD.runAnimation();
						break;
					case 4:
						rArcherL.runAnimation();
						break;
					}
				}
			}
		} else {
			if (faction == 1) {
				if (this.type == PLAYERTYPE.ARCHER) {
					rArcherS.runAnimation();
				} else if (this.type == PLAYERTYPE.HEAVY) {
					rHeavyS.runAnimation();
				} else if (this.type == PLAYERTYPE.MAGE) {
					rMageS.runAnimation();
				}
			} else {
				if (this.type == PLAYERTYPE.ARCHER) {
					bArcherS.runAnimation();
				} else if (this.type == PLAYERTYPE.HEAVY) {
					bHeavyS.runAnimation();
				} else if (this.type == PLAYERTYPE.MAGE) {
					bMageS.runAnimation();
				}
			}
		}

		
		if (playerB.getX() + speed + 20 + playerB.getWidth() > 1280) {
			motionX = 0;
			x--;
		}

		if (playerB.getX() - speed - 20 < 0) {
			motionX = 0;
			x++;
		}
		if (playerB.getY() + speed + 20 + playerB.getHeight() > 720) {
			motionY = 0;
			y--;
		}
		if (playerB.getY() - speed - 20 < 0) {
			motionY = 0;
			y++;
		}
		
		
		
		if(motionX == 0 && motionY == 0){
			moveDir = moveDirection.NONE;
		}else if(motionX < 0 && motionY == 0){
			moveDir = moveDirection.LEFT;
		} else if(motionX > 0 && motionY == 0){
			moveDir = moveDirection.RIGHT;
		} else if(motionX == 0 && motionY < 0){
			moveDir = moveDirection.UP;
		} else if(motionX == 0 && motionY > 0){
			moveDir = moveDirection.DOWN;
		}else if(motionX < 0 && motionY < 0){
			moveDir = moveDirection.UP_LEFT;
		}else if(motionX < 0 && motionY > 0){
			moveDir = moveDirection.DOWN_LEFT;
		}else if(motionX > 0 && motionY < 0){
			moveDir = moveDirection.UP_RIGHT;
		}else if(motionX > 0 && motionY > 0){
			moveDir = moveDirection.DOWN_RIGHT;
		}
		if(playerNum == 1){
			switch(moveDir){
			case NONE:
				
				break;
			case UP:
				if(playerB.intersects(Game.game.player2.backB)){
					y += 10;
				} 
				break;
			case DOWN:
				if(playerB.intersects(Game.game.player2.frontB)){
					y -= 10;
					 
				} 
				break;
			case LEFT:
				if(playerB.intersects(Game.game.player2.rightB)){
					x += 10;
				} 
				break;
			case RIGHT:
				if(playerB.intersects(Game.game.player2.leftB)){
					x -= 10;
				} 
				break;
			case UP_RIGHT:
				if(playerB.intersects(Game.game.player2.leftB)){
					x -= 10;
					
				} else if(playerB.intersects(Game.game.player2.backB)){
					
					y += 10;
				}
				break;
			case UP_LEFT:
				if(playerB.intersects(Game.game.player2.rightB)){
					x += 10;
					
				} else if(playerB.intersects(Game.game.player2.backB)){
					y += 10;
				}
				break;
			case DOWN_RIGHT:
				if(playerB.intersects(Game.game.player2.leftB)){
					x -= 10;
					
				} else if(playerB.intersects(Game.game.player2.frontB)){
					
					y -= 10;
				}
				break;
			case DOWN_LEFT:
				if(playerB.intersects(Game.game.player2.rightB)){
					x += 10;
					
				} else if(playerB.intersects(Game.game.player2.frontB)){
					
					y -= 10;
				}
				break;
			}
		}
		
		if(playerNum == 2){
			switch(moveDir){
			case NONE:
				
				break;
			case UP:
				if(playerB.intersects(Game.game.player1.backB)){
					y += 10;
				} 
				break;
			case DOWN:
				if(playerB.intersects(Game.game.player1.frontB)){
					y -= 10;
					 
				} 
				break;
			case LEFT:
				if(playerB.intersects(Game.game.player1.rightB)){
					x += 10;
				} 
				break;
			case RIGHT:
				if(playerB.intersects(Game.game.player1.leftB)){
					x -= 10;
				} 
				break;
			case UP_RIGHT:
				if(playerB.intersects(Game.game.player1.leftB)){
					x -= 10;
					
				} else if(playerB.intersects(Game.game.player1.backB)){
					
					y += 10;
				}
				break;
			case UP_LEFT:
				if(playerB.intersects(Game.game.player1.rightB)){
					x += 10;
					
				} else if(playerB.intersects(Game.game.player1.backB)){
					y += 10;
				}
				break;
			case DOWN_RIGHT:
				if(playerB.intersects(Game.game.player1.leftB)){
					x -= 10;
					
				} else if(playerB.intersects(Game.game.player1.frontB)){
					 
					y -= 10;
				}
				break;
			case DOWN_LEFT:
				if(playerB.intersects(Game.game.player1.rightB)){
					x += 10;
					
				} else if(playerB.intersects(Game.game.player1.frontB)){
					 
					y -= 10;
				}
				break;
			}
		}
		
		
		
		if (blocking == false) {
			x += motionX;
			y += motionY;
		} else {
			motionX = 0;
			motionY = 0;
		}

		if (speed > 2) {
			speedTicks++;
			if (speedTicks > 600) {
				speed = 2;
			}
		}

	}

	public void control(KeyEvent e, boolean pressed) {
		if (playerNum == 1) {
			if (pressed) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					motionY = -speed;
					if (strafe == false) {
						pointing = 1;
					}
					if (playerB.getY() - speed - 20 < 0) {
						motionY = 0;
						y++;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					motionY = speed;
					if (strafe == false) {
						pointing = 3;
					}
					if (playerB.getY() + speed + 20 + playerB.getHeight() > 720) {
						motionY = 0;
						y--;
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					motionX = -speed;
					if (strafe == false) {
						pointing = 4;
					}
					if (playerB.getX() - speed - 20 < 0) {
						motionX = 0;
						x++;
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					motionX = speed;
					if (strafe == false) {
						pointing = 2;
					}
					if (playerB.getX() + speed + 20 + playerB.getWidth() > 1280) {
						motionX = 0;
						x--;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_Q) {
					if (System.currentTimeMillis() - tack1Start > cool1) {
						tack1Start = System.currentTimeMillis();
						attack();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_E) {
					if (this.type == PLAYERTYPE.HEAVY) {
						blocking = true;
					}
					if (System.currentTimeMillis() - tack2Start > cool2) {
						tack2Start = System.currentTimeMillis();
						ability();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					if (System.currentTimeMillis() - tack3Start > cool3) {
						tack3Start = System.currentTimeMillis();
						ability2();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_F) {
					detonate = true;
				}

			}
			if (!pressed) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					motionY = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					motionY = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					motionX = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					motionX = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_R) {

				}
				if (e.getKeyCode() == KeyEvent.VK_F) {
					detonate = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_E) {
					if (this.type == PLAYERTYPE.HEAVY) {
						blocking = false;
					}
				}
			}
		} else  {
			if (pressed) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					motionY = -speed;
					if (strafe == false) {
						pointing = 1;
					}
					if (playerB.getY() - speed - 20 < 0) {
						motionY = 0;
						y++;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					motionY = speed;
					if (strafe == false) {
						pointing = 3;
					}
					if (playerB.getY() + speed + 20 + playerB.getHeight() > 720) {
						motionY = 0;
						y--;
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					motionX = -speed;
					if (strafe == false) {
						pointing = 4;
					}
					if (playerB.getX() - speed - 20 < 0) {
						motionX = 0;
						x++;
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					motionX = speed;
					if (strafe == false) {
						pointing = 2;
					}
					if (playerB.getX() + speed + 20 + playerB.getWidth() > 1280) {
						motionX = 0;
						x--;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (System.currentTimeMillis() - tack1Start > cool1) {
						tack1Start = System.currentTimeMillis();
						attack();
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					if (this.type == PLAYERTYPE.HEAVY) {
						blocking = true;
					}
					if (System.currentTimeMillis() - tack2Start > cool2) {
						tack2Start = System.currentTimeMillis();
						ability();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
					detonate = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
					if (System.currentTimeMillis() - tack3Start > cool3) {
						tack3Start = System.currentTimeMillis();
						ability2();
					}
				}
			}
			if (!pressed) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					motionY = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					motionY = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					motionX = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					motionX = 0;

				}
				if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
					detonate = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					if (this.type == PLAYERTYPE.HEAVY) {
						blocking = false;
					}
				}
			}
		}
	}

	public int[] shotPoint() {
		int[] point = new int[2];
		switch (pointing) {
		case 1:
			point[0] = (int) ((playerShoot.getWidth() / 2) + playerShoot.getX());
			point[1] = (int) playerShoot.getY();
			break;
		case 2:
			point[0] = (int) (playerShoot.getWidth() + playerShoot.getX());
			point[1] = (int) (playerShoot.getY() + (playerShoot.getHeight() / 2));
			break;
		case 3:
			point[0] = (int) ((playerShoot.getWidth() / 2) + playerShoot.getX());
			point[1] = (int) (playerShoot.getY() + playerShoot.getHeight());
			break;
		case 4:
			point[0] = (int) (playerShoot.getX());
			point[1] = (int) (playerShoot.getY() + (playerShoot.getHeight() / 2));
			break;
		}
		return point;
	}

	public void attack() {
		int[] point = shotPoint();
		if (this.type == PLAYERTYPE.ARCHER) {
			Projectile arrow1 = new Projectile(point[0], point[1], ID.Arrow,
					this);
			Game.game.handler.addObject(arrow1);

		}
		if (this.type == PLAYERTYPE.MAGE) {
			Projectile magic1 = new Projectile(point[0], point[1], ID.magic,
					this);
			Game.game.handler.addObject(magic1);

		}
		if (this.type == PLAYERTYPE.HEAVY) {
			Projectile melee1 = new Projectile(point[0], point[1], ID.Melee,
					this);
			Game.game.handler.addObject(melee1);

		}
	}

	public void setPlayerType(PLAYERTYPE type, int faction) {
		this.type = type;
		this.faction = faction;
		if (this.type == PLAYERTYPE.HEAVY) {
			this.health = 300;
			this.cool3 = 3000;
			maxHealth = 300;
		}
		if (this.type == PLAYERTYPE.MAGE) {
			this.health = 60;
			this.cool3 = 4000;
			maxHealth = 60;
		}
		if (this.type == PLAYERTYPE.ARCHER) {
			this.cool3 = 10000;
			maxHealth = 100;
		}
	}

	public void reset() {
		motionX = 0;
		motionY = 0;
		health = 100;
		maxHealth = 100;
		aISpeed = 2;
		if (this.type == PLAYERTYPE.HEAVY) {
			this.health = 300;
			maxHealth = 300;
		}
		if (this.type == PLAYERTYPE.MAGE) {
			this.health = 60;
			maxHealth = 60;
		}
		blocking = false;
		detonate = false;
		if(this.playerNum ==1){
			this.x= 100;
			this.y = 100;
		}else{
			this.x = 500;
		}
	}

	public void ability() {
		int[] point = shotPoint();
		if (this.type == PLAYERTYPE.HEAVY) {

		}
		if (this.type == PLAYERTYPE.MAGE) {
			Projectile melee1 = new Projectile(point[0], point[1],
					ID.Mage_Ability, this);
			Game.game.handler.addObject(melee1);
		}
		if (this.type == PLAYERTYPE.ARCHER) {
			long start = 10;
			int n = 0;
			while (n < 6) {
				if (start - System.currentTimeMillis() < -9) {
					Projectile melee1 = new Projectile(point[0], point[1],
							ID.Arrow, this);
					Game.game.handler.addObject(melee1);
					start = System.currentTimeMillis();
					 
					n++;
				}
			}
		}
	}

	public void ability2() {
		 
		int[] point = shotPoint();
		if (this.type == PLAYERTYPE.HEAVY) {
			Projectile melee1 = new Projectile(point[0], point[1],
					ID.Heavy_Ability, this);
			Game.game.handler.addObject(melee1);
		}
		if (this.type == PLAYERTYPE.ARCHER) {
			strafe = true;
		}
		if (this.type == PLAYERTYPE.MAGE) {
			Projectile melee1 = new Projectile(point[0], point[1],
					ID.MAge_Ability2, this);
			Game.game.handler.addObject(melee1);
		}

	}

	public void aI(){
		if(aIDiff <= 1){
			aISpeed = 3;
			aISmart = true;
		}else{
			aIGo = false;
			
		}
		if(aISmartTick > 20 && aIGo == false && aIDiff >= 1){
			if (random.nextInt(aIDiff) == 0){
				aISmart = true;
			}else{
				aISmart = false;
				switch(random.nextInt(3)){
				case 0:
					motionX = -aISpeed;
					pointing = 4;
					break;
				case 1:
					motionX = 0;
				case 2:
					motionX = aISpeed;
					pointing = 1;
				}
				switch (random.nextInt(3)){
				case 0:
					motionY = -aISpeed;
					break;
				case 1:
					motionY = 0;
					break;
				case 2:
					motionY = aISpeed;
					break;
				}
			}
			aISmartTick = 0;
				
			
		}else{
			aISmartTick++;
			
		}
		if(aISmart){
		if(Game.powerUpB){
			if (Game.game.powerUpX > x){
				motionX = aISpeed;
				pointing = 3;
			}else if (Game.game.powerUpX < x){
				motionX = - aISpeed;
				pointing = 1;
			}else {
				motionX = 0;
				
			}
			if (Game.game.powerUpY > y){
				motionY = aISpeed;
			}else if (Game.game.powerUpY < y){
				motionY = -aISpeed;
			}else{
				motionY = 0;
			}
		}else {
			switch (aIMove(150, Game.game.player2.x, x, 50, true)){
			case 0:
				motionX = -aISpeed;
				pointing = 4;
				break;
			case 1:
				xGood = true;
				break;
			case 2:
				motionX = aISpeed;pointing = 1;
				break;
			}

			switch (aIMove(100, Game.game.player2.y, y, 50, false)){
			case 0:
				motionY = -aISpeed;
				
				break;
			case 1:
				yGood = true;
				break;
			case 2:
				motionY = aISpeed;
				break;
			}
			
		}
			if(true){
				//System.out.println("shoot");
				yGood = false;
				xGood =false;
				if(Game.game.player2.x > x){
					pointing = 2;
				}else{
					pointing = 4;
				}
				if (System.currentTimeMillis() - tack2Start > cool2) {
					tack2Start = System.currentTimeMillis();
					System.out.println("abitliy");
					ability();
				}
				if (System.currentTimeMillis() - tack1Start > cool1) {
					tack1Start = System.currentTimeMillis();
					System.out.println("Attack");
					attack();
				}
				
				
			}
		
		
	
		
	
	
	}
	}
	
	public int aIMove(int buffer, int player2, int aI, int plusMinus, boolean xOrY){
		// true = X false = Y
		if(!xOrY){
		if (player2 > aI + buffer + plusMinus || player2> aI + buffer - plusMinus){
			return 2;
		}
		if (player2 < aI - buffer - plusMinus || player2 < aI - buffer + plusMinus){
			return 0;
		}else{
			return 1;
		}
		}else{
			if ((Game.game.player2.x > this.x - buffer - plusMinus || Game.game.player2.x > this.x - buffer + plusMinus) && (Game.game.player2.x < this.x + buffer - plusMinus ||  Game.game.player2.x < this.x + buffer + plusMinus)){
			//System.out.println("back up");
				if (player2 > aI  + plusMinus || player2> aI  - plusMinus){
				return 0;
			
					}
			
				if (player2 < aI -plusMinus || player2 < aI  + plusMinus){
				return 2;
			
				}else{
				return 1;
			}
			}else{
			//	System.out.println("not back up");
				if (player2 > aI + buffer + plusMinus || player2> aI + buffer - plusMinus){
					return 2;
				}
				if (player2 < aI - buffer - plusMinus || player2 < aI - buffer + plusMinus){
					return 0;
				}else{
					return 1;
				}
			}
		}
	}
	
}	