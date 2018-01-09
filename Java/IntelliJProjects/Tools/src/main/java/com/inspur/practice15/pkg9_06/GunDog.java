package com.inspur.practice15.pkg9_06;

public class GunDog implements Dog {

    @Override
    public void info() {
        System.out.println("I'm a gun dog!");
    }

    @Override
    public void run() {
        System.out.println("I'm running!");
    }

    @Override
    public boolean catchBayGuy(String name) {
        int x = (int) (Math.random() * 100);
        if (x > 50) {
            System.out.println("catch you! " + name);
            return true;
        } else {
            System.out.println(name + "! I won't let you run away next time!");
            return false;
        }
    }

}
