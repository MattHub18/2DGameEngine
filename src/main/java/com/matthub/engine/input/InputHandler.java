package com.matthub.engine.input;

import java.util.ArrayList;
import java.util.List;

public abstract class InputHandler<T> {

    private final List<Command<T>> commands = new ArrayList<>();
    protected Controller controller;

    public InputHandler() {
        insertCommands();
    }

    public void handleInput(T element) {
        for (Command<T> command : commands) {
            InputCheck check = getCheck(command);
            if (check.check(controller, command.getKey())) {
                command.execute(element);
            }
        }
    }

    private InputCheck getCheck(Command<T> command) {
        switch (command.getType()) {
            case KEYBOARD:
                switch (command.getMode()) {
                    case TYPED:
                        return Controller::isKey;
                    case DOWN:
                        return Controller::isKeyDown;
                    case UP:
                        return Controller::isKeyUp;
                    default:
                        throw new IllegalArgumentException("Unsupported mode: " + command.getMode());
                }

            case MOUSE:
                switch (command.getMode()) {
                    case TYPED:
                        return Controller::isButton;
                    case DOWN:
                        return Controller::isButtonDown;
                    case UP:
                        return Controller::isButtonUp;
                    default:
                        throw new IllegalArgumentException("Unsupported mode: " + command.getMode());
                }

            default:
                throw new IllegalArgumentException("Unsupported type: " + command.getType());
        }
    }

    protected void addCommand(Command<T> c) {
        commands.add(c);
    }

    protected abstract void insertCommands();

    public void registerController(Controller controller) {
        this.controller = controller;
    }
}
