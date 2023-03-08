package jena.lang.value;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class IntegerValue implements Value
{
    private int value;

    public IntegerValue(int value)
    {
        this.value = value;
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(new StringSource(String.valueOf(value)));
    }

    @Override
    public Value member(Source name)
    {
        String stringName = name.text().toString();
        return new MethodValue(name, arguments -> arguments.number(1, args ->
        {
            if(args.at(0) instanceof IntegerValue)
            {
                int other = ((IntegerValue)args.at(0)).value;
                if(stringName.equals("add")) return new IntegerValue(value + other);
                if(stringName.equals("sub")) return new IntegerValue(value - other);
                if(stringName.equals("mul")) return new IntegerValue(value * other);
                if(stringName.equals("div")) return new IntegerValue(value / other);
            }
            return NoneValue.instance;
        }, () -> arguments.number(0, args ->
        {
            if(stringName.equals("sqrt")) return new IntegerValue((int)Math.sqrt(value));
            return NoneValue.instance;
        }, () -> NoneValue.instance)));
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}