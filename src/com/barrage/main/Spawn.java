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
		
		if (levelScore >= 1000) {
			levelScore = 0;
			hud.setLevel(hud.getLevel() + 1);
			if (hud.getLevel() > hud.getScore()/1000) {
//				hud.setLevel(hud.getLevel() + 1);;
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
			}
		}
	}
}
