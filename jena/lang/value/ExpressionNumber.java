package jena.lang.value;

public final class ExpressionNumber implements Single
{
    private Single value;

    public ExpressionNumber(Value value)
    {
        if(value instanceof Single) this.value = (Single)value;
        else if(value instanceof NoneValue) this.value = new NumberValue(0);
        else this.value = new NumberValue(1);
    }

    @Override
    public double single()
    {
        return value.single();
    }
    public int integer()
    {
        return (int)value.single();
    }
}