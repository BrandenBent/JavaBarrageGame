package com.barrage.main;

import java.util.Random;

public class Spawn {
	private Handler handler;
	private HUD hud;
	private int levelScore;
	private Random r = new Random();

	public Spawn(Handler handler, HUD hud) {
		super();
		this.handler = handler;
		this.hud = hud;
	};
	
	public void tick() {
		levelScore++;
		
		if (levelScore >= 100) {
			levelScore = 0;
			hud.setLevel(hud.getLevel() + 1);
			if (hud.getLevel() > hud.getScore()/100) {
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH -100), r.nextInt(Game.HEIGHT -100), ID.BasicEnemy, handler));
			}
		}
	}
}
