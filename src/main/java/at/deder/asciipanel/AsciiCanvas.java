package at.deder.asciipanel;

import java.awt.Color;

public class AsciiCanvas {
    private char[][] layer = null;
    private Color[][] fgColorInformation = null;
    private Color[][] bgColorInformation = null;
    
    private static Color DEFAULT_FG_COLOR = Color.lightGray;
    
    public AsciiCanvas(int rows, int cols) {
	layer = initialiseBuffer(new char[cols][rows]);
	fgColorInformation = initialiseColorBuffer(new Color[cols][rows], DEFAULT_FG_COLOR);
	bgColorInformation = initialiseColorBuffer(new Color[cols][rows], null);
    }
    
    private char[][] initialiseBuffer(char[][] buffer) {
	for (int i = 0; i < buffer.length; ++i) {
	    for (int j = 0; j < buffer[i].length; ++j) {
		buffer[i][j] = 32;
	    }
	}
	return buffer;
    }
    
    private Color[][] initialiseColorBuffer(Color[][] buffer, Color defaultColor) {
	for (int i = 0; i < buffer.length; ++i) {
	    for (int j = 0; j < buffer[i].length; ++j) {
		buffer[i][j] = defaultColor;
	    }
	}
	return buffer;
    }
    
    public Color getBackgroundColor(int x, int y) {
	return bgColorInformation[x][y];
    }
    
    public void setBackgroundColor(int x, int y, Color c) {
	bgColorInformation[x][y] = c;
    }
    
    public Color getForegroundColor(int x, int y) {
	return fgColorInformation[x][y];
    }
    
    public void setForegroundColor(int x, int y, Color c) {
	fgColorInformation[x][y] = c;
    }
    
    public char getCharacter(int x, int y) {
	return layer[x][y];
    }
    
    public void setCharacter(char c, int x, int y) {
	layer[x][y] = c;
    }
    
    public void drawString(String str, int x, int y) {
	String[] lines = str.split("\n");
	int startY = y;
	int startX = x;
	for (; (y - startY) < lines.length && y < getRows(); ++y) {
	    for (x=startX; x < lines[y - startY].length() && x < getCols(); ++x) {
		layer[x][y] = lines[y - startY].charAt(x - startX);
	    }
	}
    }
    
    private int getRows() {
	return layer[1].length;
    }
    
    private int getCols() {
	return layer.length;
    }

    /**
     * Draw another canvas on this canvas
     * @param canvas
     * @param x
     * @param y
     */
    public void drawCanvas(AsciiCanvas canvas, int x, int y) {
	for(int otherY=0; otherY < canvas.getRows() && otherY < getRows(); ++otherY) {
	    for(int otherX=0; otherX < canvas.getCols() && otherX < getCols(); ++otherX) {
		setCharacter(canvas.getCharacter(otherX, otherY), x+otherX, y+otherY);
		setBackgroundColor(x+otherX, y+otherY, canvas.getBackgroundColor(otherX, otherY));
		setForegroundColor(x+otherX, y+otherY, canvas.getForegroundColor(otherX, otherY));
	    }
	}
    }
    
    /**
     * Set background color for an area.
     * @param c
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void setBackgroundColor(Color c, int x, int y, int width, int height) {
	for(;x<width;++x) {
	    for(;y<height;++y) {
		setBackgroundColor(x, y, c);
	    }
	}
    }
    
    /**
     * Set background color for the whole canvas.
     * @param c
     */
    public void setBackgroundColor(Color c) {
	for(int x=0; x < getCols(); ++x) {
	    for(int y=0; y < getRows(); ++y) {
		setBackgroundColor(x, y, c);
	    }
	}
    }
    
    /**
     * Set background color for an area.
     * @param c
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void setForegroundColor(Color c, int x, int y, int width, int height) {
	for(;x<width;++x) {
	    for(;y<height;++y) {
		setForegroundColor(x, y, c);
	    }
	}
    }
    
    /**
     * Set background color for the whole canvas.
     * @param c
     */
    public void setForegroundColor(Color c) {
	for(int x=0; x < getCols(); ++x) {
	    for(int y=0; y < getRows(); ++y) {
		setForegroundColor(x, y, c);
	    }
	}
    }
}
