package com.matthub.engine.io.filesystem;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
        loadFiles(dir, map, f -> toKey(f.getName()), File::getAbsolutePath);
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
            loadFiles(dir, map, f -> toKey(f.getName()), f -> rootDir + "/" + f.getName());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("[Archive] Missing internal resource folder: " + rootDir);
        }
    }

    private static void loadFiles(File dir, Map<String, String> map, Function<File, String> keyMapper, Function<File, String> valueMapper) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    String key = keyMapper.apply(f);
                    map.putIfAbsent(key, valueMapper.apply(f));
                } else if (f.isDirectory()) {
                    loadFiles(f, map, keyMapper, valueMapper);
                }
            }
        }
    }

    public static void loadAll() {
        _load(ResourceType.CONFIG,  "config", ResourceLocation.INTERNAL);
        _load(ResourceType.TEXTURE,  "resources/textures", ResourceLocation.EXTERNAL);
        _load(ResourceType.FONT,  "resources/fonts", ResourceLocation.EXTERNAL);
        _load(ResourceType.FONT,  "fonts", ResourceLocation.INTERNAL);
        _load(ResourceType.MAP,  "resources/map", ResourceLocation.EXTERNAL);
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