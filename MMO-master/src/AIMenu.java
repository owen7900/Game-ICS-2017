import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class AIMenu {
	
	public BufferedImage background;
	public Rectangle onePLayer = new Rectangle((Game.WIDTH/2) - 320, 250, 600, 80);
	public Rectangle twoPLayer = new Rectangle((Game.WIDTH/2) - 320, 350, 600, 80);
	public AIMenu (){
		background = imageLoader.imageLoader("./menuBackground.png");
		
	}
	
	public void render(Graphics g){
		FontLoader.loadFont("./MMO-master/Minecraft.ttf");
		Font titleFont = new Font("Minecraft",Font.PLAIN, 93);
		Font buttonFont = new Font("Minecraft",Font.PLAIN, 50);
		g.setColor(Color.BLACK);
		g.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g.setFont(buttonFont);
		g.drawString("SELECT NUMBER OF PLAYERS", 200, 100);
		Graphics2D g2d = (Graphics2D) g;
		g2d.fill(onePLayer);
		g2d.fill(twoPLayer);
		g.setColor(Color.WHITE);
		g.drawString("One", (Game.WIDTH/2) - 320, 300);
		g.drawString("Two", (Game.WIDTH/2) - 320, 400);
	}
	
}
