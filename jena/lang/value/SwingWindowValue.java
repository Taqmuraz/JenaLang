package jena.lang.value;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.text.ValueText;

public final class SwingWindowValue implements Value
{
    private Value members;
    private WindowBuilder builder;

    private interface WindowBuilder
    {
        JFrame build();
    }

    private static class InitialBuilder implements WindowBuilder
    {
        private int width;
        private int height;

        public InitialBuilder(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        public JFrame build()
        {
            JFrame frame = new JFrame();
            frame.setLayout(null);
            frame.setSize(width, height);
            return frame;
        }
    }

    private interface ComponentBuilder
    {
        Component build();
    }


    private class AddComponentBuilder implements WindowBuilder
    {
        public AddComponentBuilder(ComponentBuilder component)
        {
            this.component = component;
        }
        private ComponentBuilder component;
        public JFrame build()
        {
            JFrame frame = builder.build();
            frame.add(component.build());
            return frame;
        }
    }

    private static class ImagePanel extends JPanel
    {
        BufferedImage image;

        public ImagePanel(Text file)
        {
            try
            {
                image = ImageIO.read(new File(file.string()));
            }
            catch(Throwable error)
            {
                image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }
        }

        @Override
        public void paint(Graphics graphics)
        {
            Graphics2D g2 = (Graphics2D)graphics;
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public SwingWindowValue(Single width, Single height)
    {
        this(new InitialBuilder((int)width.single(), (int)height.single()));
    }

    private SwingWindowValue(WindowBuilder builder)
    {
        this.builder = builder;

        members = new SymbolMapValue(action ->
        {
            action.call("show", () -> new MethodValue(arg ->
            {
                EventQueue.invokeLater(() ->
                {
                    JFrame frame = this.builder.build();
                    frame.setVisible(true);
                });
                return NoneValue.instance;
            }));
            action.call("label", () ->
                new MethodValue("text", text ->
                new MethodValue("x", x ->
                new MethodValue("y", y ->
                new MethodValue("width", width ->
                new MethodValue("height", height ->
            {
                return new SwingWindowValue(new AddComponentBuilder(() ->
                {
                    Label label = new Label();
                    label.setText(new ValueText(text).string());
                    label.setBounds(
                        new ExpressionNumber(x).integer(),
                        new ExpressionNumber(y).integer(),
                        new ExpressionNumber(width).integer(),
                        new ExpressionNumber(height).integer());
                    return label;
                }));
            }))))));
            action.call("button", () ->
                    new MethodValue("text", text ->
                    new MethodValue("x", x ->
                    new MethodValue("y", y ->
                    new MethodValue("width", width ->
                    new MethodValue("height", height ->
                    new MethodValue("click", click ->
            {
                return new SwingWindowValue(new AddComponentBuilder(() ->
                {
                    JButton button = new JButton(new ValueText(text).string());
                    button.addActionListener(e -> click.call(NoneValue.instance));
                    button.setLocation(
                        new ExpressionNumber(x).integer(),
                        new ExpressionNumber(y).integer());
                    button.setSize(
                        new ExpressionNumber(width).integer(),
                        new ExpressionNumber(height).integer());
                    return button;
                }));
            })))))));
            action.call("image", () ->
                    new MethodValue("file", file ->
                    new MethodValue("x", x ->
                    new MethodValue("y", y ->
                    new MethodValue("width", width ->
                    new MethodValue("height", height ->
            {
                return new SwingWindowValue(new AddComponentBuilder(() ->
                {
                    ImagePanel panel = new ImagePanel(new ValueText(file));
                    panel.setLocation(
                        new ExpressionNumber(x).integer(),
                        new ExpressionNumber(y).integer());
                    panel.setSize(
                        new ExpressionNumber(width).integer(),
                        new ExpressionNumber(height).integer());
                    return panel;
                }));
            }))))));
        },
        args -> NoneValue.instance);
    }

    @Override
    public void print(TextWriter writer)
    {
        members.print(writer);
    }
    @Override
    public Value call(Value argument)
    {
        return members.call(argument);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
}