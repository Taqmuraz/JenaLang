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
        return new MethodValue(name, arguments -> arguments.number(1, args ->
        {
            if(args.at(0) instanceof IntegerValue)
            {
                int other = ((IntegerValue)args.at(0)).value;
                String stringName = name.text().toString();
                if(stringName.equals("add")) return new IntegerValue(value + other);
                if(stringName.equals("sub")) return new IntegerValue(value - other);
                if(stringName.equals("mul")) return new IntegerValue(value * other);
                if(stringName.equals("div")) return new IntegerValue(value / other);
            }
            return NoneValue.instance;
        }, () -> NoneValue.instance));
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}