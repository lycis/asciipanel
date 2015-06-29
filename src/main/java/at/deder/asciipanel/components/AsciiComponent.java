package at.deder.asciipanel.components;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.deder.asciipanel.AsciiCanvas;

public abstract class AsciiComponent {
    
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private List<ChangeListener> changeListeners = new ArrayList<>();
    private Color bgColor = null; // null = none
    private Color fgColor = null; // null = none
    private List<MouseListener> mouseListeners = new ArrayList<>();
    private List<KeyListener> keyboardListeners = new ArrayList<>();
    
    public AsciiComponent() {
	
    }
    
    public int getWidth() {
	return 0;
    }
    
    public int getHeight() {
	return 0;
    }
    
    public void paint(AsciiCanvas graphics) {
	graphics.setBackgroundColor(bgColor);
	graphics.setForegroundColor(fgColor);
    }
    
    public int getX() {
	return x;
    }
    
    public int getY() {
	return y;
    }
    
    public int getZ() {
	return z;
    }
    
    public void setX(int x) {
	this.x = x;
    }
    
    public void setY(int y) {
	this.y = y;
    }
    
    public void setZ(int z) {
	this.z = z;
    }
    
    public void addChangeListener(ChangeListener l) {
	if(!changeListeners.contains(l)) {
	    changeListeners.add(l);
	}
    }
    
    public void removeChangeListener(ChangeListener l) {
	if(changeListeners.contains(l)) {
	    changeListeners.remove(l);
	}
    }
    
    public void fireChange() {
	for(ChangeListener l: changeListeners) {
	    l.stateChanged(new ChangeEvent(this));
	}
    }
    
    public Color getBackgroundColor() {
	return bgColor;
    }
    
    public void setBackgroundColor(Color bgColor) {
	this.bgColor = bgColor;
    }
    
    public Color getForegroundColor() {
	return fgColor;
    }
    
    public void setForegroundColor(Color fgColor) {
	this.fgColor = fgColor;
    }
    
    public void addMouseListener(MouseListener l) {
	mouseListeners.add(l);
    }
    
    public void removeMouseListener(MouseListener l) {
	mouseListeners.remove(l);
    }
    
    public void addKeyListener(KeyListener l) {
	keyboardListeners.add(l);
    }
    
    public void removeKeyListener(KeyListener l) {
	keyboardListeners.remove(l);
    }
    
    public List<MouseListener> getMouseListeners() {
	return mouseListeners;
    }
    
    public List<KeyListener> getKeyListeners() {
	return keyboardListeners;
    }
}
