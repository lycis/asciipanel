package at.deder.asciipanel.components;

import at.deder.asciipanel.AsciiCanvas;

public class AsciiLabel extends AsciiComponent {
    private String text = "";
    
    public AsciiLabel() {
	super();
    }
    
    public AsciiLabel(String text) {
	this();
	setText(text);
    }
    
    public void setText(String text) {
	this.text = text;
	fireChange();
    }
    
    public String getText() {
	return text;
    }

    @Override
    public int getWidth() {
	if(text.contains("\n")) {
	    // multi-line string
	    String[] lines = text.split("\n");
	    int maxWidth = 0;
	    for(String s: lines) {
		if(s.length() > maxWidth) {
		    maxWidth = s.length();
		}
	    }
	    return super.getWidth()+maxWidth;
	}
	
	return super.getWidth()+text.length();
    }

    @Override
    public int getHeight() {
	if(text.contains("\n")) {
	    return super.getHeight()+text.split("\n").length;
	}
	
	return super.getHeight()+1;
    }

    @Override
    public void paint(AsciiCanvas g) {
	super.paint(g);
	g.drawString(getText(), 0, 0);
    }

}
