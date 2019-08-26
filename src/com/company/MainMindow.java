package com.company;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.locks.ReentrantLock;

class MainWindow extends Frame{
    private static final int
            screenOffset = 8,
            baseX = 8, baseY = 31,
            symbolWidth = 16, symbolHeight = 16;
    private static Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, symbolHeight);

    private static ReentrantLock screenLock = new ReentrantLock();

    MainWindow(KeyListener keyListener, int screenWidth, int screenHeight){
        setSize(baseX + screenOffset + screenWidth * symbolWidth, baseY + screenOffset + screenHeight * symbolHeight);
        setLayout(null);
        setVisible(true);
        addKeyListener(keyListener);
        setTitle("Война потоков - попаданий: 0, промахов: 0");
    }

    private int toPixelCoordinateX(int x){
        return x * symbolWidth + baseX;
    }

    private int toPixelCoordinateY(int y){
        return y * symbolHeight + baseY;
    }

    void setSymbol(int x, int y, char symbol){
        Label label = new Label(String.valueOf(symbol));
        label.setFont(defaultFont);
        label.setBounds(toPixelCoordinateX(x), toPixelCoordinateY(y), symbolWidth, symbolHeight);
        screenLock.lock();
        add(label);
        screenLock.unlock();
    }

    void removeSymbol(int x, int y){
        Component component = getComponentAt(toPixelCoordinateX(x),toPixelCoordinateY(y));
        screenLock.lock();
        remove(component);
        screenLock.unlock();
    }

    boolean isNotFree(int x, int y){
        return !getComponentAt(toPixelCoordinateX(x),toPixelCoordinateY(y)).getName().equals("frame0");
    }

    void updateTitle(){
        setTitle(String.format("Война потоков - попаданий: %d, промахов: %d", Main.hit.get(), Main.miss.get()));
    }
}
