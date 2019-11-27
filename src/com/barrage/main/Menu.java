package com.barrage.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.barrage.main.Game.STATE;

public class Menu extends MouseAdapter {
	private Game game;
	private Handler handler;
	private Random r = new Random();
	private HUD hud;
	
	public Menu (Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (game.gameState == STATE.Menu) {
			if (mouseOver(mx, my, 150, 190, 340, 60)) {
				game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
				for (int i = 0; i < 1; i++) {
					// subtracting 50 from width and height to make sure no enemies spawn outside of game
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 100), r.nextInt(Game.HEIGHT -100), ID.BasicEnemy, handler));
				} 
			}
		} else if(game.gameState == STATE.GameOver) {
			if (mouseOver(mx, my, 180, 350, 320, 60)) {
				hud.setLevel(0);
				hud.setScore(0);
				game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
				for (int i = 0; i < 1; i++) {
					// subtracting 50 from width and height to make sure no enemies spawn outside of game
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 100), r.nextInt(Game.HEIGHT -100), ID.BasicEnemy, handler));
				} 
				
			}
		}
	}
	
//	public void mouseReleased(MouseEvent e) {
//
//	}
	
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width && my > y && my < y + height) {
				return true;
		} else {
			return false;
		}
	}
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font font = new Font("Comic Sans", 1, 50);
		g.setFont(font);
		if (game.gameState == STATE.Menu) {
			g.setColor(Color.white);
			g.drawRect(150, 190, 340, 60);
			g.drawString("Click to Play" , 160, Game.HEIGHT/2);
		} else if (game.gameState == STATE.GameOver){
			g.setColor(Color.RED);
			g.drawString("GAME OVER" , 160, Game.HEIGHT/4);
			g.drawString("Final Score: " + hud.getScore() , 120, Game.HEIGHT/2);
			
			g.drawRect(180, 350, 320, 60);
			g.drawString("Try Again?", 200, 400);
		}
	}
	
}
