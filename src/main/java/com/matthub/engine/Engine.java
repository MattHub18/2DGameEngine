package com.matthub.engine;

import com.matthub.engine.graphics.core.Render;
import com.matthub.engine.graphics.core.Window;
import com.matthub.engine.graphics.gfx.Font;
import com.matthub.engine.graphics.gfx.Text;
import com.matthub.engine.input.Controller;
import com.matthub.engine.io.filesystem.Archive;
import com.matthub.engine.io.filesystem.Filesystem;
import com.matthub.engine.io.filesystem.ResourceType;
import com.matthub.engine.settings.GraphicSettings;
import com.matthub.engine.settings.WindowSettings;
import com.matthub.engine.util.ResourceLoadException;

import java.awt.*;

public class Engine implements Runnable{
    private volatile boolean running;
    private Window window;
    private Render render;
    private Controller controller;

    public Engine(String title) {
        this.running = false;
        try {
            Filesystem.load();
            WindowSettings settings = new WindowSettings(Archive.get(ResourceType.CONFIG, "window"));
            this.window = new Window(this, title, settings);
            this.render = new Render(window, new GraphicSettings(Archive.get(ResourceType.CONFIG, "graphic")));
            this.controller = new Controller(window);
        }catch(IllegalStateException e) {
            System.err.println("[Engine] Failed to load: " + e.getMessage());
            Filesystem.clear();
            System.exit(1);
        }

    }

    //start engine
    public synchronized void start() {
        if (this.running)
            return;

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    //stop engine
    public synchronized void stop() {
        if (!this.running)
            return;

        this.running = false;
    }

    @Override
    public void run() {
        this.running = true;

        final double TARGET_UPS = 60.0;
        final double TARGET_FPS = 144.0;

        final double NS_PER_UPDATE = 1_000_000_000.0 / TARGET_UPS;
        final double NS_PER_FRAME = 1_000_000_000.0 / TARGET_FPS;

        long lastTime = System.nanoTime();
        long lastFpsTime = System.currentTimeMillis();
        double delta = 0;

        int frames = 0;
        int fps = 0;

        long renderAccumulator = 0;

        while (this.running) {
            long now = System.nanoTime();
            long elapsed = now - lastTime;
            lastTime = now;
        
            delta += elapsed / NS_PER_UPDATE;
            renderAccumulator += elapsed;
        
            // Fixed-timestep update
            while (delta >= 1) {
                update(this, (float)(1.0 / TARGET_UPS));
                delta--;
            }
        
            // Render with capped FPS
            if (renderAccumulator >= NS_PER_FRAME) {
                try {
                    render(fps);
                }catch (ResourceLoadException e){
                    stop();
                }

                frames++;
                renderAccumulator = 0; // reset after rendering
            }
        
            // FPS calculation every second
            if (System.currentTimeMillis() - lastFpsTime >= 1000) {
                fps = frames;
                frames = 0;
                lastFpsTime += 1000;
            }
        
            // Reduce CPU usage
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                stop();
            }
        }
        //shutdown
        Filesystem.clear();
        this.render.clear();
        this.window.close();
    }

    //update main logic
    private void update(Engine engine, float dt) {
        this.controller.update();
    }

    //render main logic
    private void render(int fps) {
        this.render.clear();
        Font font = new Font(Archive.get(ResourceType.FONT, "texgyrepagella"), 7, Color.WHITE.getRGB());
        this.render.addText(new Text("FPS: " + fps, font), 0, 0);
        this.render.process();
        this.window.render();
    }
}
