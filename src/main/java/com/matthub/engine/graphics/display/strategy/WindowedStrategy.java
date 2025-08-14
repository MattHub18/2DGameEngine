package com.matthub.engine.graphics.display.strategy;

import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Canvas;

import com.matthub.engine.graphics.core.Window;
import com.matthub.engine.graphics.display.DisplaySettings;
import com.matthub.engine.graphics.display.DisplayStrategy;

public class WindowedStrategy implements DisplayStrategy {
    @Override
    public void apply(JFrame frame, Canvas canvas, DisplaySettings settings, Window window) {
        window.setScale(settings.getScale());
        window.setWidth(settings.getWidth());
        window.setHeight(settings.getHeight());

        Dimension size = new Dimension((int)(window.getWidth() * window.getScale()), (int)(window.getHeight() * window.getScale()));
        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);

        frame.pack();
    }
}