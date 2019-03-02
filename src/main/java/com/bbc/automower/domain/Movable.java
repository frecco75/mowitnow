package com.bbc.automower.domain;

public interface Movable<T extends Movable<T>> {

    T turnRight();

    T turnLeft();

    T moveForward();
    
}
