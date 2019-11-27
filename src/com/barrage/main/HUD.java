package com.barrage.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	public static int HEALTH = 100;
	private int greenHealth = 255;
	private int score;
	private int level = 1;
	
	public void tick() {
		HEALTH = Game.clamp(HEALTH, 0, 100);
		greenHealth = Game.clamp(greenHealth, 0, 255);
		greenHealth = HEALTH *2;
		
		score++;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(new Color(150, greenHealth, 0));
		g.fillRect(15, 15, HEALTH * 2, 32);
		g.setColor(Color.white);
		g.drawRect(15, 15, 200, 32);
		
		g.drawString("Score " + score, 15, 60);
		g.drawString("Level " + level, 15, 75);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
