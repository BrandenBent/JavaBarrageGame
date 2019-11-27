package com.barrage.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
//import java.util.Random;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1550691097823471818L;
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private Random r;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	public enum STATE{
		Menu,
		Game,
		GameOver
	};
	
	public STATE gameState = STATE.Menu;

	public Game() {
		this.requestFocus(true);
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(handler));
		
		new Window(WIDTH, HEIGHT, "testing build", this);
		spawner = new Spawn(handler, hud);
		r = new Random();
		
		if (gameState == STATE.Game) {
			handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler));
			for (int i = 0; i < 1; i++) {
				// subtracting 50 from width and height to make sure no enemies spawn outside of game
				handler.addObject(new BasicEnemy(r.nextInt(WIDTH - 100), r.nextInt(HEIGHT -100), ID.BasicEnemy, handler));
			} 
		} else if (gameState == STATE.Menu) {
			menu.tick();
		}

	}

	public synchronized void start() {
		this.requestFocus();
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		handler.tick();
		if (gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			
			if (HUD.HEALTH <= 0) {
				HUD.HEALTH = 100;
//				}
				handler.objects.clear();
				gameState = STATE.GameOver;
			}
		}
		
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);
		if (gameState == STATE.Game) {
			hud.render(g);
		} else {
			menu.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	public static int clamp(int var, int min, int max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}

	//////////////////// MAIN////////////////////////////
	public static void main(String[] args) {
		new Game();
	}
}
