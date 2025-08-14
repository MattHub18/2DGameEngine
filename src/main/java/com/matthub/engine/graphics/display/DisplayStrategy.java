package com.matthub.engine.graphics.display;

import javax.swing.JFrame;
import java.awt.Canvas;

import com.matthub.engine.graphics.core.Window;

public interface DisplayStrategy {
    void apply(JFrame frame, Canvas canvas, DisplaySettings settings, Window window);
}
