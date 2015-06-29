package at.deder.asciipanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import at.deder.asciipanel.components.AsciiComponent;

public class AsciiGraphicsContainerActionHandler implements MouseListener, KeyListener {
    private AsciiGraphicsContainer container = null;

    public AsciiGraphicsContainerActionHandler(AsciiGraphicsContainer asciiGraphicsContainer) {
	container = asciiGraphicsContainer;
	asciiGraphicsContainer.addMouseListener(this);
	asciiGraphicsContainer.addKeyListener(this);
    }
    
    private AsciiComponent focusComponentByKeyEvent(KeyEvent event) {
	// TODO implement
	return null;
    }

    /**
     * Propagates key presses to the AsciiComponent at this position.
     */
    @Override
    public void keyPressed(KeyEvent event) {	
	for(KeyListener l: container.getFocusedComponent().getKeyListeners()){
	    l.keyPressed(event);
	}
    }

    @Override
    public void keyReleased(KeyEvent event) {
	for(KeyListener l: container.getFocusedComponent().getKeyListeners()){
	    l.keyReleased(event);
	}
    }

    @Override
    public void keyTyped(KeyEvent event) {
	// TODO find next component on <TAB>, UP, DOWN, LEFT or RIGHT
	for(KeyListener l: container.getFocusedComponent().getKeyListeners()){
	    l.keyTyped(event);
	}
    }
    
    private List<AsciiComponent> getComponentsAffectedByMouseEvent(MouseEvent event) {
	if(!container.isMouseNavigationEnabled()) {
	    return new ArrayList<AsciiComponent>();
	}
	
	if(!container.equals(event.getSource())) {
	    return new ArrayList<AsciiComponent>();
	}

	return container.getComponentsAt(container.ordinateToGrid(event.getX()),
	                                 container.ordinateToGrid(event.getY()));
    }

    @Override
    public void mouseClicked(MouseEvent event) {
	container.requestFocus();
	for(AsciiComponent c: getComponentsAffectedByMouseEvent(event)) {
	    for(MouseListener ml: c.getMouseListeners()) {
		ml.mouseClicked(event);
	    }
	}
    }

    @Override
    public void mouseEntered(MouseEvent event) {
	for(AsciiComponent c: getComponentsAffectedByMouseEvent(event)) {
	    for(MouseListener ml: c.getMouseListeners()) {
		ml.mouseEntered(event);
	    }
	}
    }

    @Override
    public void mouseExited(MouseEvent event) {
	for(AsciiComponent c: getComponentsAffectedByMouseEvent(event)) {
	    for(MouseListener ml: c.getMouseListeners()) {
		ml.mouseExited(event);
	    }
	}

    }

    @Override
    public void mousePressed(MouseEvent event) {
	for(AsciiComponent c: getComponentsAffectedByMouseEvent(event)) {
	    for(MouseListener ml: c.getMouseListeners()) {
		ml.mousePressed(event);
	    }
	}
    }

    @Override
    public void mouseReleased(MouseEvent event) {
	for(AsciiComponent c: getComponentsAffectedByMouseEvent(event)) {
	    for(MouseListener ml: c.getMouseListeners()) {
		ml.mouseReleased(event);
	    }
	}
    }

}
