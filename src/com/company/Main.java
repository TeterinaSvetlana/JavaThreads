package com.company;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static Random rnd = new Random();
    static final int screenWidth = 80, screenHeight = 25;
    static MainWindow window = new MainWindow(new RightLeftSpaceListener(), screenWidth, screenHeight);
    static int gunX = screenWidth / 2, gunY = screenHeight - 1;
    private static final char gunSymbol = '|';
    private static final int maxBulletsPerQueue = 3, maxMiss = 30;

    static AtomicInteger
            hit = new AtomicInteger(0),
            miss = new AtomicInteger(0);
    public static Semaphore bulletSemaphore = new Semaphore(maxBulletsPerQueue, true);
    private static ReentrantLock randomLock = new ReentrantLock();
    private static ReentrantLock gameOver = new ReentrantLock();

    public static void main(String[] args) {
        window.setSymbol(gunX, gunY, gunSymbol);
        new BadguysFactoryThread().start();
    }

    public static void stepLeft() {
        if (gunX - 1 > 0) {
            window.removeSymbol(gunX, gunY);
            window.setSymbol(--gunX, gunY, gunSymbol);
        }
    }

    public static void stepRight() {
        if (gunX + 1 < screenWidth) {
            window.removeSymbol(gunX, gunY);
            window.setSymbol(++gunX, gunY, gunSymbol);
        }
    }

    public static void fire(){
        new BulletThread().start();
    }

    public static int getNextRandomInt(int limit){
        randomLock.lock();
        int randomInt = rnd.nextInt(limit);
        randomLock.unlock();
        return randomInt;
    }

    public static void IncHit(){
        hit.addAndGet(1);
        window.updateTitle();
    }

    public static void IncMiss(){
        miss.addAndGet(1);
        window.updateTitle();
        if (miss.get() >= maxMiss) {
            gameOver();
        }
    }

    private static void gameOver(){
        gameOver.lock();
        Frame finalFrame = new Frame();
        finalFrame.setTitle("The War Of Threads");
        finalFrame.setSize(300,200);
        Label text = new Label("Game over!");
        text.setBounds(150,50,100,100);
        finalFrame.add(text);
        finalFrame.setVisible(true);
        //gameOver.unlock();
    }
}
