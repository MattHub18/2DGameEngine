package com.matthub.engine.graphics.core;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.*;

import com.matthub.engine.Engine;
import com.matthub.engine.graphics.display.DisplaySettings;
import com.matthub.engine.graphics.display.DisplayStrategy;
import com.matthub.engine.graphics.display.strategy.BorderlessStrategy;
import com.matthub.engine.graphics.display.strategy.FullscreenStrategy;
import com.matthub.engine.graphics.display.strategy.WindowedStrategy;
import com.matthub.engine.graphics.display.DisplayMode;

public class Window {
    private int width;
    private int height;
    private float scale;
    private static final Map<DisplayMode, DisplayStrategy> STRATEGIES = Map.of(
        DisplayMode.FULLSCREEN, new FullscreenStrategy(),
        DisplayMode.WINDOWED,   new WindowedStrategy(),
        DisplayMode.BORDERLESS, new BorderlessStrategy()
    );

    private final BufferedImage image;
    private final Canvas canvas;
    private final JFrame frame;
    private final BufferStrategy bufferStrategy;

    public Window(Engine engine, String title, DisplaySettings settings) {            
        this.canvas = new Canvas();

        this.frame = new JFrame(title);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.canvas, BorderLayout.CENTER);

        DisplayStrategy strategy = STRATEGIES.get(settings.getMode());
        strategy.apply(frame, canvas, settings, this);

        this.frame.setResizable(false);
        this.frame.setVisible(true);

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                engine.stop();
            }
        });

        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        this.canvas.createBufferStrategy(2);
        this.bufferStrategy = canvas.getBufferStrategy();
        this.canvas.requestFocus();
    }

    public void render() {
        if(this.bufferStrategy != null) {
            Graphics g = this.bufferStrategy.getDrawGraphics();
            g.drawImage(this.image, 0, 0, (int) (this.image.getWidth() * scale), (int) (this.image.getHeight() * scale), null);
            this.bufferStrategy.show();
            g.dispose();
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getScale() {
        return this.scale;
    }

    public void close() {
        this.frame.dispose();
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
}

