package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RightLeftSpaceListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getExtendedKeyCode()){
            case KeyEvent.VK_RIGHT: Main.stepRight();
                break;
            case KeyEvent.VK_LEFT: Main.stepLeft();
                break;
            case KeyEvent.VK_SPACE: Main.fire();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}
