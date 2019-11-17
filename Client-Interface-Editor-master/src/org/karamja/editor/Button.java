package org.karamja.editor;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Button extends Rectangle {
	
	public boolean action = false;
	
	public String name;
	
	public String text;
	
	public Button(String name, String text,int startX, int startY, int width, int height, boolean action){
		this.name = name;
		this.text = text;
		this.x = startX;
		this.y = startY;
		this.width = width;
		this.height = height;
		this.action = action;
		System.out.println(action);
    }
	
	public void draw(Graphics g){
        g.drawRect(x, y, width, height);
        g.fillRect(x, y, width, height);
	}
	
	public String toString(){
		return "\n" + name + "= new Box(new Rectangle(" + x + ", " + y + ", " + width + ", " + height + "));\n"
				+ name +".setText(\""+ text + "\", 0xFFFFFF);\n"
						+ name + ".setAction(new Action(){\n"
								+ "@Override\n"
								+ "public void action(int x, int y, int button){\n"
								+ "\n"
								+ "}\n"
								+ "});\n";
	}

}
