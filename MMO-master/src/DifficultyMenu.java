import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DifficultyMenu {
	public BufferedImage background;
	
	//public Rectangle onePLayer = new Rectangle((Game.WIDTH/2) - 320, 250, 600, 80);
	//public Rectangle twoPLayer = new Rectangle((Game.WIDTH/2) - 320, 350, 600, 80);
	public DifficultyMenu (){
		background = imageLoader.imageLoader("./menuBackground.png");
		
	}
	
	public void render(Graphics g){
		FontLoader.loadFont("./MMO-master/Minecraft.ttf");
		Font titleFont = new Font("Minecraft",Font.PLAIN, 93);
		Font buttonFont = new Font("Minecraft",Font.PLAIN, 50);
		g.setColor(Color.BLACK);
		g.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g.setFont(buttonFont);
		g.drawString("INPUT DIFFICULTY", 200, 100);
		Graphics2D g2d = (Graphics2D) g;
		g.drawString("Enter Difficulty Number 1-9", 200, 200);
		g.drawString("0 is Extreme!!!!", 200, 300);
		
		
	}
	
}

