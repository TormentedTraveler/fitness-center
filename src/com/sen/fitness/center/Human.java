package com.sen.fitness.center;

public interface Human {
    boolean login(String name, String password);
    void exit();
    void showMenu();
    void doMenuCommand(int commandNum);
}