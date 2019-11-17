package org.karamja.editor;

import java.awt.image.BufferedImage;

public class Image {

	private BufferedImage image;
	private int startX;
	private int startY;

	public Image(BufferedImage img, int startX, int startY){
		this.image = img;
		this.startX = startX;
		this.startY = startY;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

}
