package com.example.model;

	import com.example.speed.Speed;

	import android.graphics.Bitmap;
	import android.graphics.Canvas;


public class Ball {

	private Bitmap bitmap;	
	private int x;			
	private int y;			
	private boolean touched;	
	private Speed speed;	
	private int spriteHeight;
	private int spriteWidth;
	
	public Ball(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.speed = new Speed((float)(Math.random()*2 + 1),(float)(Math.random()*2 + 1));
		spriteHeight = bitmap.getHeight();
		spriteWidth = bitmap.getWidth();
	}
	
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	
	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}


	public void update() {
		if (!touched) {
			x += (speed.getXv() * speed.getxDirection()); 
			y += (speed.getYv() * speed.getyDirection());
		}
	}
	

	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}

	}
	
	public int getSpriteHeight() {
		return spriteHeight;
	}
	
	public int getSpriteWidth() {
		return spriteWidth;
	}
	

}
