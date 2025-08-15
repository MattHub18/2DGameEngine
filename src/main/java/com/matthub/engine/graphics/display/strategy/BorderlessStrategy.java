package com.matthub.engine.graphics.display.strategy;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Canvas;

import com.matthub.engine.graphics.core.Window;
import com.matthub.engine.graphics.display.DisplayStrategy;
import com.matthub.engine.settings.WindowSettings;

public class BorderlessStrategy implements DisplayStrategy {
    @Override
    public void apply(JFrame frame, Canvas canvas, WindowSettings settings, Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setWidth(screenSize.width);
        window.setHeight(screenSize.height);
        window.setScale(1.0f);

        frame.setUndecorated(true);
        frame.setBounds(0, 0, screenSize.width, screenSize.height);
    }
}
