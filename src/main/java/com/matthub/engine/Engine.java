package com.matthub.engine;

import com.matthub.engine.graphics.core.Window;
import com.matthub.engine.graphics.display.DisplaySettings;
import com.matthub.engine.io.filesystem.Archive;
import com.matthub.engine.io.filesystem.Filesystem;
import com.matthub.engine.io.filesystem.ResourceType;

public class Engine implements Runnable{
    private volatile boolean running;
    private Window window;

    public Engine(String title) {
        this.running = false;
        try {
            Filesystem.load();
            DisplaySettings settings = new DisplaySettings(Archive.get(ResourceType.CONFIG, "window"));
            window = new Window(this, title, settings);
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
        // Close/clear components
        Filesystem.clear();
        window.close();
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

        while (running) {
            long now = System.nanoTime();
            long elapsed = now - lastTime;
            lastTime = now;
        
            delta += elapsed / NS_PER_UPDATE;
            renderAccumulator += elapsed;
        
            // Fixed-timestep update
            while (delta >= 1) {
                update();
                delta--;
            }
        
            // Render with capped FPS
            if (renderAccumulator >= NS_PER_FRAME) {
                render(fps);
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
                Thread.currentThread().interrupt();
            }
        }
    }

    //update main logic
    private void update() {

    }

    //render main logic
    private void render(int fps) {
        System.out.println("FPS: " + fps);
    }
}
