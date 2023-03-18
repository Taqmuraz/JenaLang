package jena.lang.value;

public final class ExpressionIntegerNumber implements IntegerNumber
{
    private IntegerNumber value;

    public ExpressionIntegerNumber(Value value)
    {
        if(value instanceof IntegerNumber) this.value = (IntegerNumber)value;
        else if(value instanceof NoneValue) this.value = new IntegerValue(0);
        else this.value = new IntegerValue(1);
    }

    @Override
    public int integer()
    {
        return value.integer();
    }
}