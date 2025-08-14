package com.matthub.engine.graphics.display.strategy;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Canvas;

import com.matthub.engine.graphics.core.Window;
import com.matthub.engine.graphics.display.DisplaySettings;
import com.matthub.engine.graphics.display.DisplayStrategy;

public class FullscreenStrategy implements DisplayStrategy {
    @Override
    public void apply(JFrame frame, Canvas canvas, DisplaySettings settings, Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setWidth(screenSize.width);
        window.setHeight(screenSize.height);
        window.setScale( 1.0f);

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
