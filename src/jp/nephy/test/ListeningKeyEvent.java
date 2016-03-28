package jp.nephy.test;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ListeningKeyEvent extends Applet implements KeyListener{
	String typekey;
	String presskey;

	public void init(){
		typekey = "";
		presskey = "";
		addKeyListener(this);
	}

	public void start(){
	}

	public void paint(Graphics g){
		g.drawString(typekey, 10, 20);
		g.drawString(presskey, 10, 50);

		requestFocusInWindow();
	}

	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		presskey = e.getKeyText(keycode);

		int mod = e.getModifiersEx();

		if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
			presskey += " +SHIFT";
		}

		if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
			presskey += " +ALT";
		}

		if ((mod & InputEvent.CTRL_DOWN_MASK) != 0){
			presskey += " +CTRL";
		}

		repaint();
	}

	public void keyReleased(KeyEvent e){
	}

	public void keyTyped(KeyEvent e){
		char key = e.getKeyChar();
		typekey += key;

		repaint();
	}
}