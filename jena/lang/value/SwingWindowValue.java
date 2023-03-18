package jena.lang.value;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

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
        ArrayList<Component> components = new ArrayList<Component>();

        members = new ObjectValue(EmptyNamespace.instance, new ArrayBuffer<GenericPair<Text, ValueProducer>>(
            new NamePair<ValueProducer>("show", namespace -> new MethodValue(new EmptyBuffer<Text>(), args ->
            {
                EventQueue.invokeLater(() ->
                {
                    JFrame frame = new JFrame();
                    frame.setLayout(null);
                    frame.setSize(width.integer(), height.integer());
                    for(Component c : components) frame.add(c);
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
                components.add(label);
                return this;
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
                components.add(button);
                return this;
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