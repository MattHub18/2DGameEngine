package com.matthub.engine.graphics.display;

import javax.swing.JFrame;
import java.awt.Canvas;

import com.matthub.engine.graphics.core.Window;
import com.matthub.engine.settings.WindowSettings;

public interface DisplayStrategy {
    void apply(JFrame frame, Canvas canvas, WindowSettings settings, Window window);
}
