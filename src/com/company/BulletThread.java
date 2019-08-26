package com.company;

public class BulletThread extends Thread {
    private static final int bulletSleepPeriod = 100;
    static final char bullet = '*';

    @Override
    public void run() {
        if (!Main.bulletSemaphore.tryAcquire()) return;

        int bulletX = Main.gunX, bulletY = Main.gunY - 1;

        while(bulletY >= 0) {
            if (!Main.window.isNotFree(bulletX, bulletY)) {
                Main.window.setSymbol(bulletX, bulletY, bullet);
            } else {
                Main.IncHit();
                Main.window.removeSymbol(bulletX, bulletY);
                break;
            }
            try {
                sleep(bulletSleepPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.window.removeSymbol(bulletX, bulletY);
            bulletY--;
        }

        Main.bulletSemaphore.release();
    }
}
