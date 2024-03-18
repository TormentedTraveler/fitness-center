package com.sen.fitness.local;

import com.sen.fitness.data.Database;

public abstract class Person {
    boolean loggedIn = false;
    private Database data;
    public abstract boolean login(String name, String password);

    public void exit() {
        this.loggedIn = false;
    }

    public abstract void showMenu();

    public abstract void doMenuCommand(int commandNum);

    public Person() {
        this.data = new Database();
    }
}