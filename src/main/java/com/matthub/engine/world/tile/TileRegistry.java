package com.matthub.engine.world.tile;

import java.util.HashMap;
import java.util.Map;

public class TileRegistry {
    private static final Map<String, TileProperties> registry = new HashMap<>();

    // Prevent instantiation
    private TileRegistry() {}

    public static void register(String name, TileProperties props) {
        registry.put(name, props);
    }

    public static TileProperties get(String name) {
        return registry.get(name);
    }
}
