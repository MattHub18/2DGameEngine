package com.matthub.engine.io.filesystem;

public class Filesystem {

    private Filesystem() {}

    public static void load() {
        Archive.loadAll();
    }

    public static void clear() {
        Archive.clear();
    }
}