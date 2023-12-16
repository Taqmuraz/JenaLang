package jena.lang.swing;

import java.awt.Graphics;
import java.util.function.Consumer;

import javax.swing.JPanel;

public class CustomPanel extends JPanel
{
    Consumer<Graphics> paint;
    
    public CustomPanel(Consumer<Graphics> paint)
    {
        this.paint = paint;
    }

    @Override
    public void paint(Graphics graphics)
    {
        paint.accept(graphics);
    }
}