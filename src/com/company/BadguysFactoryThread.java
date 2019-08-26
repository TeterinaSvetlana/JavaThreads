package com.company;

public class BadguysFactoryThread extends Thread {
    private static final int badguysGenerationDelay = 1000;

    @Override
    public void run() {
        while (true){
            if (Main.getNextRandomInt(100) < (Main.hit.get() + Main.miss.get()) / 25 + 20)
                new BadguyThread().start();
            try {
                sleep(badguysGenerationDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}