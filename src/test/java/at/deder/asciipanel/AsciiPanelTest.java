package at.deder.asciipanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.charset.CharacterCodingException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import at.deder.asciipanel.components.AsciiLabel;

public class AsciiPanelTest {

    public static void main(String... args) throws CharacterCodingException {
	JFrame frame = new JFrame("AsciiPanel Test");
	
	AsciiGraphicsContainer panel = new AsciiGraphicsContainer(20, 20);
	panel.setBackground(Color.black);
	panel.setFont(new Font("monospaced", Font.PLAIN, 12));
	panel.setKeyNavigationEnabled(true);
	
	frame.setLayout(new BorderLayout());
	frame.add(panel, BorderLayout.CENTER);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
	/*panel.addLayer();
	panel.getLayer(0).drawString("Hello,\nWorld!", 0, 0);
	panel.getLayer(1).drawString("\u00A5", 0, 0);
	panel.getLayer(0).setBackgroundColor(0, 0, Color.green);
	panel.getLayer(1).setForegroundColor(0, 0, Color.blue);*/
	
	AsciiLabel lbl = new AsciiLabel("Label!");
	lbl.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		System.out.println("label clicked");		
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	    }
	    
	});
	lbl.addKeyListener(new KeyListener(){

	    @Override
	    public void keyPressed(KeyEvent e) {
		System.out.println("key pressed");
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	    }

	    @Override
	    public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	    }
	    
	});
	lbl.setBackgroundColor(Color.lightGray);
	lbl.setForegroundColor(Color.black);
	panel.addComponent(lbl, 10, 5);
	//panel.requestFocus();
    }
}
