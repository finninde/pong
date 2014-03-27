package com.example.pong;

import com.example.model.Ball;
import com.example.model.Padel;
import com.example.model.Score;
import com.example.speed.Speed;

import net.obviam.droidz.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Padel cpu;
	private Padel player;
	private Ball ball;
	private Score score;
	
	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);

		cpu = new Padel(BitmapFactory.decodeResource(getResources(), R.drawable.pong_bar), 50, 50);
		player = new Padel(BitmapFactory.decodeResource(getResources(), R.drawable.pong_bar), 50, 450);
		ball = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.pong_ball), 100 , 250);
		score = new Score();
		
		thread = MainThread.getInstance();
		thread.setup(getHolder(), this);
		
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		boolean retry = true;
		thread.setRunning(false);
		thread.interrupt(); // helgardering
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			player.handleActionDown((int)event.getX(), (int)event.getY());
			
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (player.isTouched()) {
				player.setX((int)event.getX());
			}
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			if (player.isTouched()) {
				player.setTouched(false);
			}
		}
		return true;
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		cpu.draw(canvas);
		player.draw(canvas);
		ball.draw(canvas);
		displayScore(canvas);
	}

	private boolean intersect(){
		if (ball.getY() + 20 >= player.getY() && ball.getSpeed().getyDirection() == Speed.DIRECTION_DOWN ){
			if (player.getX() - ball.getX() < ball.getSpriteWidth() && player.getX() - ball.getX() > 0){
				Log.e("player: ", "" + player.getX() );
				Log.e("ball: ", "" + ball.getX() );
				
				return true;
			}
			else if (ball.getX() -player.getX() < player.getSpriteWidth() && ball.getX() -player.getX() > 0 ){
				Log.e("player: left ", "" + player.getX() );
				Log.e("ball: left", "" + ball.getX() );
				
				return true;
				
			}
		}
		if (ball.getY() <= cpu.getY() + cpu.getSpriteHeight() && ball.getSpeed().getyDirection() == Speed.DIRECTION_UP){
			
			if (cpu.getX() - ball.getX() < ball.getSpriteWidth() && cpu.getX() - ball.getX() > 0){
				Log.e("cpu: ", "" + player.getX() );
				Log.e("ball: ", "" + ball.getX() );
				
				return true;
			}
			else if (ball.getX() - cpu.getX() < cpu.getSpriteWidth() && ball.getX() -player.getX() > 0 ){
				Log.e("cpu: left ", "" + player.getX() );
				Log.e("ball: left", "" + ball.getX() );
				
				return true;
			}
		}
		return false;
	}

	public void update() {
		ball.update();
		cpu.update();
		if (cpu.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& cpu.getX() + cpu.getBitmap().getWidth() / 2 >= getWidth()) {
			cpu.getSpeed().toggleXDirection();
		}
		if (cpu.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& cpu.getX() - cpu.getBitmap().getWidth() / 2 <= 0) {
			cpu.getSpeed().toggleXDirection();
		}
		
		if (ball.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& ball.getX() + ball.getBitmap().getWidth() / 2 >= getWidth()) {
			ball.getSpeed().toggleXDirection();
		}
		if (ball.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& ball.getX() - ball.getBitmap().getWidth() / 2 <= 0) {
			ball.getSpeed().toggleXDirection();
		}
		
		
		if(ball.getY()>500) {
			ball.setX(100);
			ball.setY(250);
			score.incremeentCpuScore();
		}
		
		if(ball.getY()<20) {
			ball.setX(100);
			ball.setY(250);
			score.incrementPlayerScore();
		}
		
		score.reachedWin(score.getPlayerScore());
		score.reachedWin(score.getCpuScore());
		
		if(intersect()) {
			ball.getSpeed().toggleYDirection();
		}
		
	}
	
	private void displayScore(Canvas canvas) {
		if(canvas!=null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			String string = "Cpu: " + score.getCpuScore() + "  Player: " 
			+ score.getPlayerScore();
			canvas.drawText(string, this.getWidth() - 110, 20, paint);
		}
	}

}
