package com.matthub.engine.io.filesystem;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Archive {

    private static final Map<ResourceType, Map<String, String>> resources = new HashMap<>();
    private enum ResourceLocation{
        INTERNAL, EXTERNAL
    }

    private Archive() {}

    public static void load(ResourceType type, String rootDir) {
        _load(type, rootDir, ResourceLocation.EXTERNAL);
    }

    private static void _load(ResourceType type, String rootDir, ResourceLocation location) {
        Map<String, String> map = new HashMap<>();
        if(location == ResourceLocation.INTERNAL)
            loadInternal(rootDir, map);
        else
            loadExternal(rootDir, map);
        resources.put(type, map);
    }

    private static void loadExternal(String rootDir, Map<String, String> map) {
        File dir = new File(rootDir);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalStateException("[Archive] Missing external resource folder: " + rootDir);
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    String key = toKey(f.getName());
                    map.putIfAbsent(key, f.getAbsolutePath());
                }
            }
        }
    }

    private static void loadInternal(String rootDir, Map<String, String> map) {
        URL path = Archive.class.getClassLoader().getResource(rootDir);
        if (path == null)
            throw new IllegalStateException("[Archive] Missing internal resource folder: " + rootDir);
        try {
            File dir = new File(path.toURI());
            if (!dir.exists() || !dir.isDirectory()) {
                throw new IllegalStateException("[Archive] Missing internal resource folder: " + rootDir);
            }
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        String key = toKey(f.getName());
                        map.putIfAbsent(key, rootDir + "/" + f.getName());
                    }
                }
            }
        }catch(URISyntaxException e){
            throw new IllegalStateException("[Archive] Missing internal resource folder: " + rootDir);
        }
    }

    public static void loadAll() {
        _load(ResourceType.CONFIG,  "config", ResourceLocation.INTERNAL);
    }

    public static String get(ResourceType type, String name) {
        Map<String, String> map = resources.get(type);
        return (map != null) ? map.get(name.toLowerCase()) : null;
    }

    public static void clear() {
        resources.clear();
    }

    private static String toKey(String filename) {
        int dot = filename.lastIndexOf('.');
        return (dot > 0 ? filename.substring(0, dot) : filename).toLowerCase();
    }
}