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

import jena.lang.ArrayBuffer;
import jena.lang.EmptyBuffer;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.GenericPairBothAction;
import jena.lang.text.StringText;
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
            frame.setSize(width, height);
            return frame;
        }
    }

    private class AddComponentBuilder implements WindowBuilder
    {
        public AddComponentBuilder(Component component)
        {
            this.component = component;
        }
        private Component component;
        public JFrame build()
        {
            JFrame frame = builder.build();
            frame.add(component);
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

    private static class SwingWindowMember implements GenericPair<Text, ValueProducer>
    {
        private Text name;
        private ValueProducer value;

        public SwingWindowMember(String name, GenericBuffer<Text> arguments, ValueListFunction function)
        {
            this.name = new StringText(name);
            value = n -> new MethodValue(arguments, function);
        }

        @Override
        public void both(GenericPairBothAction<Text, ValueProducer> action)
        {
            action.call(name, value);
        }
    }

    public SwingWindowValue(IntegerNumber width, IntegerNumber height)
    {
        this(new InitialBuilder(width.integer(), height.integer()));
    }

    private SwingWindowValue(WindowBuilder builder)
    {
        this.builder = builder;

        members = new ObjectValue(EmptyNamespace.instance, new ArrayBuffer<GenericPair<Text, ValueProducer>>(
            new NamePair<ValueProducer>("show", namespace -> new MethodValue(new EmptyBuffer<Text>(), args ->
            {
                EventQueue.invokeLater(() ->
                {
                    JFrame frame = builder.build();
                    frame.setVisible(true);
                });
                return NoneValue.instance;
            })),
            new SwingWindowMember("label",
                new ArrayBuffer<Text>(
                    new StringText("text"),
                    new StringText("x"),
                    new StringText("y"),
                    new StringText("width"),
                    new StringText("height")), args ->
            {
                Label label = new Label();
                label.setText(new ValueText(args.at(0)).string());
                label.setBounds(
                    new ExpressionIntegerNumber(args.at(1)).integer(),
                    new ExpressionIntegerNumber(args.at(2)).integer(),
                    new ExpressionIntegerNumber(args.at(3)).integer(),
                    new ExpressionIntegerNumber(args.at(4)).integer());
                return new SwingWindowValue(new AddComponentBuilder(label));
            }),
            new SwingWindowMember("button",
                new ArrayBuffer<Text>(
                    new StringText("text"),
                    new StringText("x"),
                    new StringText("y"),
                    new StringText("width"),
                    new StringText("height"),
                    new StringText("click")), args ->
            {
                JButton button = new JButton(new ValueText(args.at(0)).string());
                button.doLayout();
                button.addActionListener(e -> args.at(5).call(new EmptyArgumentList()));
                button.setLocation(
                    new ExpressionIntegerNumber(args.at(1)).integer(),
                    new ExpressionIntegerNumber(args.at(2)).integer());
                button.setSize(
                    new ExpressionIntegerNumber(args.at(3)).integer(),
                    new ExpressionIntegerNumber(args.at(4)).integer());
                return new SwingWindowValue(new AddComponentBuilder(button));
            }),
            new SwingWindowMember("image",
                new ArrayBuffer<Text>(
                    new StringText("file"),
                    new StringText("x"),
                    new StringText("y"),
                    new StringText("width"),
                    new StringText("height")), args ->
            {
                ImagePanel panel = new ImagePanel(new ValueText(args.at(0)));
                panel.setLocation(
                    new ExpressionIntegerNumber(args.at(1)).integer(),
                    new ExpressionIntegerNumber(args.at(2)).integer());
                panel.setSize(
                    new ExpressionIntegerNumber(args.at(3)).integer(),
                    new ExpressionIntegerNumber(args.at(4)).integer());
                return new SwingWindowValue(new AddComponentBuilder(panel));
            })
        ));
    }

    @Override
    public void print(TextWriter writer)
    {
        members.print(writer);
    }

    @Override
    public Value member(Text name)
    {
        return members.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return members.call(arguments);
    }
}