package jena.lang.value;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class RealValue implements Value
{
    private double value;

    public RealValue(double value)
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
            if(args.at(0) instanceof RealValue)
            {
                double other = ((RealValue)args.at(0)).value;
                if(stringName.equals("add")) return new RealValue(value + other);
                if(stringName.equals("sub")) return new RealValue(value - other);
                if(stringName.equals("mul")) return new RealValue(value * other);
                if(stringName.equals("div")) return new RealValue(value / other);
            }
            return NoneValue.instance;
        }, () -> arguments.number(0, args ->
        {
            if(stringName.equals("sqrt")) return new RealValue(Math.sqrt(value));
            return NoneValue.instance;
        }, () -> NoneValue.instance)));
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}