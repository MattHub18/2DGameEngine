package com.matthub.engine.settings;

import com.matthub.engine.io.IO;
import com.matthub.engine.util.ResourceLoadException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GraphicSettings {
    private final int brightPercentage;

    public GraphicSettings(String confFilename) {
        try (InputStream in = IO.loadStream(confFilename)) {
            Properties props = new Properties();
            props.load(in);
            this.brightPercentage = Integer.parseInt(props.getProperty("brightness"));
        } catch (IOException | NullPointerException e) {
            throw new ResourceLoadException("[DisplaySettings] Incorrect config file: " + confFilename);
        }
    }

    public int getBrightPercentage() {
        return brightPercentage;
    }
}
