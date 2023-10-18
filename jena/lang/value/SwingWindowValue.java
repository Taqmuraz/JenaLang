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
            action.call("show", () -> new MethodValue(new EmptyTuple(), arg ->
            {
                EventQueue.invokeLater(() ->
                {
                    JFrame frame = this.builder.build();
                    frame.setVisible(true);
                });
                return NoneValue.instance;
            }));
            action.call("label", () -> new MethodValue(new TupleValue(
                    new TextValue("text"),
                    new TextValue("x"),
                    new TextValue("y"),
                    new TextValue("width"),
                    new TextValue("height")), arg ->
            {
                var args = arg.decompose();
                return new SwingWindowValue(new AddComponentBuilder(() ->
                {
                    Label label = new Label();
                    label.setText(new ValueText(args.at(0)).string());
                    label.setBounds(
                        new ExpressionNumber(args.at(1)).integer(),
                        new ExpressionNumber(args.at(2)).integer(),
                        new ExpressionNumber(args.at(3)).integer(),
                        new ExpressionNumber(args.at(4)).integer());
                    return label;
                }));
            }));
            action.call("button", () -> new MethodValue(new TupleValue(
                    new TextValue("text"),
                    new TextValue("x"),
                    new TextValue("y"),
                    new TextValue("width"),
                    new TextValue("height"),
                    new TextValue("click")), arg ->
            {
                var args = arg.decompose();
                return new SwingWindowValue(new AddComponentBuilder(() ->
                {
                    JButton button = new JButton(new ValueText(args.at(0)).string());
                    button.addActionListener(e -> args.at(5).call(new EmptyTuple()));
                    button.setLocation(
                        new ExpressionNumber(args.at(1)).integer(),
                        new ExpressionNumber(args.at(2)).integer());
                    button.setSize(
                        new ExpressionNumber(args.at(3)).integer(),
                        new ExpressionNumber(args.at(4)).integer());
                    return button;
                }));
            }));
            action.call("image", () -> new MethodValue(new TupleValue(
                    new TextValue("file"),
                    new TextValue("x"),
                    new TextValue("y"),
                    new TextValue("width"),
                    new TextValue("height")), arg ->
            {
                var args = arg.decompose();
                return new SwingWindowValue(new AddComponentBuilder(() ->
                {
                    ImagePanel panel = new ImagePanel(new ValueText(args.at(0)));
                    panel.setLocation(
                        new ExpressionNumber(args.at(1)).integer(),
                        new ExpressionNumber(args.at(2)).integer());
                    panel.setSize(
                        new ExpressionNumber(args.at(3)).integer(),
                        new ExpressionNumber(args.at(4)).integer());
                    return panel;
                }));
            }));
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