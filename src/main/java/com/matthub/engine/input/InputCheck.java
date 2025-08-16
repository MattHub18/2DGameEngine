package com.matthub.engine.input;

@FunctionalInterface
public interface InputCheck {
    boolean check(Controller controller, int key);
}
