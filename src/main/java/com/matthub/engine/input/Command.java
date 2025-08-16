package com.matthub.engine.input;

public abstract class Command<T> {

    private final CommandType type;
    private final CommandMode mode;
    private final int key;

    protected Command(CommandType type, CommandMode mode, int key) {
        this.type = type;
        this.mode = mode;
        this.key = key;
    }

    public CommandType getType() {
        return type;
    }

    public CommandMode getMode() {
        return mode;
    }

    public int getKey() {
        return key;
    }

    public abstract void execute(T element);
}
