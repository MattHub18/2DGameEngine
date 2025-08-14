package com.matthub.engine;

public class Engine implements Runnable{
    private volatile boolean running;
    private Thread gameThread;

    public Engine(String title) {
        this.running = false;
    }

    //start engine
    public synchronized void start() {
        if (this.running)
            return;

        this.gameThread = new Thread(this);
        this.gameThread.start();
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
                Thread.currentThread().interrupt();
                stop();
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
