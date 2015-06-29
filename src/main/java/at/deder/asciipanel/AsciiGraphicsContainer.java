package at.deder.asciipanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.deder.asciipanel.components.AsciiComponent;

public class AsciiGraphicsContainer extends JPanel implements ChangeListener {
    private List<AsciiCanvas> layers = null;
    private List<List<AsciiComponent>> components = new ArrayList<>();
    private int rows = 0;
    private int cols = 0;
    private int spacing = 2;
    private Font usedFont = new Font("Courier New", Font.PLAIN, 12);
    private boolean adaptFontSize = true;
    private AsciiGraphicsContainerActionHandler actionHandler = new AsciiGraphicsContainerActionHandler(this);
    private boolean keyNavigationEnabled = false;
    private boolean mouseNavigationEnabled = false;
    private AsciiComponent focusedComponent = null;

    public AsciiGraphicsContainer(int rows, int cols) {
	this.rows = rows;
	this.cols = cols;
	layers = new ArrayList<>();
	addLayer();
    }

    public AsciiGraphicsContainer(int layers, int rows, int cols) {
	this(rows, cols);
	this.layers = new ArrayList<>();
	for (int i = 0; i < (layers - 1); ++i) {
	    addLayer();
	}
    }

    public void addLayer() {
	layers.add(new AsciiCanvas(rows, cols));
	components.add(new ArrayList<AsciiComponent>());
    }

    private int getLayerCount() {
	return layers.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);

	if (adaptFontSize) {
	    adaptFontSize();
	}

	g.setFont(usedFont);

	for (int z = 0; z < layers.size(); ++z) {
	    AsciiCanvas currentLayer = layers.get(z);
	    for (int x = 0; x < cols; ++x) {
		for (int y = 0; y < rows; ++y) {
		    int paintPosX = x * (getCharWidth() + spacing);
		    int paintPosY = y * (getCharWidth() + spacing);

		    // draw background
		    if (currentLayer.getBackgroundColor(x, y) != null) {
			g.setColor(currentLayer.getBackgroundColor(x, y));
			g.fillRect(paintPosX, paintPosY, getCharWidth() + spacing, getCharWidth() + spacing);
		    }

		    // draw character
		    g.setColor(currentLayer.getForegroundColor(x, y));
		    g.drawString("" + currentLayer.getCharacter(x, y),
			paintPosX, paintPosY + getCharWidth());
		}
	    }
	}
    }

    @Override
    public int getWidth() {
	if (adaptFontSize) {
	    return super.getWidth();
	} else {
	    return (getCharWidth() * cols) + (cols * spacing);
	}
    }

    @Override
    public int getHeight() {
	if (adaptFontSize) {
	    return super.getWidth();
	} else {
	    return (getCharWidth() * rows) + (rows * spacing);
	}
    }

    @Override
    public Dimension getMinimumSize() {
	if (adaptFontSize) {
	    return super.getMinimumSize();
	} else {
	    return new Dimension(getWidth(), getHeight());
	}
    }

    private int getCharWidth() {
	return getCharWidth(usedFont);
    }

    private int getCharWidth(Font f) {
	return getGraphics().getFontMetrics(f).stringWidth("X");
    }

    private void adaptFontSize() {
	float fSize = 1.0f;
	while (true) {
	    Font f = usedFont.deriveFont(fSize);
	    if (((getCharWidth(f) * cols) + (cols * spacing)) > getWidth() ||
		((getCharWidth(f) * rows) + (rows * spacing)) > getHeight()) {
		break;
	    }
	    usedFont = f;
	    fSize++;
	}
    }

    public boolean isAdaptableFontSize() {
	return adaptFontSize;
    }

    public void setAdaptableFontSize(boolean adaptFontSize) {
	this.adaptFontSize = adaptFontSize;
    }

    public int getSpacing() {
	return spacing;
    }

    public void setSpacing(int spacing) {
	this.spacing = spacing;
    }

    public void setLayer(int z, AsciiCanvas layer) {
	if (z < 0 || z > layers.size()) {
	    throw new IndexOutOfBoundsException("layer not available");
	}

	layers.set(z, layer);
	repaint();
    }

    public AsciiCanvas getLayer(int z) {
	return layers.get(z);
    }

    public void setFont(Font f) {
	usedFont = f;
    }

    public void addComponent(AsciiComponent comp, int x, int y) {
	addComponent(comp, x, y, components.size() - 1);
    }

    public void addComponent(AsciiComponent comp, int x, int y, int z) {
	List<AsciiComponent> componentsAtLayer = components.get(z);
	componentsAtLayer.add(comp);
	comp.addChangeListener(this);
	comp.setX(x);
	comp.setY(y);
	comp.setZ(z);
	paintComponentToBuffer(comp, layers.get(z));
	if (isKeyNavigationEnabled()) {
	    setFocusToFirstComponent();
	}
    }

    private void paintComponentToBuffer(AsciiComponent comp, AsciiCanvas asciiCanvas) {
	AsciiCanvas buffer = new AsciiCanvas(comp.getHeight(), comp.getWidth());
	comp.paint(buffer);
	asciiCanvas.drawCanvas(buffer, comp.getX(), comp.getY());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
	// redraw changed component
	if (e.getSource() instanceof AsciiComponent) {
	    AsciiComponent comp = (AsciiComponent) e.getSource();
	    paintComponentToBuffer((AsciiComponent) e.getSource(), layers.get(comp.getZ()));
	}
    }

    public List<AsciiComponent> getComponentsAt(int x, int y) {
	List<AsciiComponent> returnList = new ArrayList<>();
	for (int i = 0; i < components.size(); ++i) {
	    returnList.addAll(getComponentsAt(x, y, i));
	}
	return returnList;
    }

    public List<AsciiComponent> getComponentsAt(int x, int y, int z) {
	List<AsciiComponent> returnList = new ArrayList<>();
	List<AsciiComponent> componentLayer = components.get(z);
	for (AsciiComponent c : componentLayer) {
	    if (x >= c.getX() && x <= c.getX() + c.getWidth() &&
		y >= c.getY() && y <= c.getY() + c.getHeight()) {
		returnList.add(c);
	    }
	}

	return returnList;
    }

    public int ordinateToGrid(int o) {
	return (o / (getCharWidth() + spacing));
    }

    public boolean isKeyNavigationEnabled() {
	return keyNavigationEnabled;
    }

    public void setKeyNavigationEnabled(boolean keyNavigationEnabled) {
	this.keyNavigationEnabled = keyNavigationEnabled;

	setFocusToFirstComponent();
    }

    public void setFocusToFirstComponent() {
	for (int k = 0; k <= 2 * cols; ++k) {
	    for (int y = 0; y < cols; ++y) {
		int x = k - y;
		if (x < 0 || x >= cols) {
		    continue; // Coordinates are out of bounds; skip.
		} else {
		    List<AsciiComponent> comps = getComponentsAt(x, y);
		    if (!comps.isEmpty()) {
			setFocusedComponent(comps.get(comps.size() - 1));
		    }
		}
	    }
	}
    }

    public boolean isMouseNavigationEnabled() {
	return mouseNavigationEnabled;
    }

    public void setMouseNavigationEnabled(boolean mouseNavigationEnabled) {
	this.mouseNavigationEnabled = mouseNavigationEnabled;
    }

    public AsciiComponent getFocusedComponent() {
	return focusedComponent;
    }

    public void setFocusedComponent(AsciiComponent focusedComponent) {
	this.focusedComponent = focusedComponent;
    }
}
