package jena.lang.source;

import jena.lang.syntax.LocationPrinter;
import jena.lang.text.Text;

public interface SourceLocation
{
    void location(int position, SourceLocationAction action);

    default String string()
    {
        var sb = new StringBuilder();
        new LocationPrinter(this).print(t -> sb.append(t.string()));
        return sb.toString();
    }
    default int line()
    {
        int[] box = { 0 };
        location(0, (text, line, symbol) -> box[0] = line);
        return box[0];
    }
    default SourceLocation struct()
    {
        class SourceLocationStruct implements SourceLocation, SourceLocationAction
        {
            Text origin;
            int line, symbol;

            @Override
            public void call(Text origin, int line, int symbol)
            {
                this.origin = origin;
                this.line = line;
                this.symbol = symbol;
            }

            @Override
            public void location(int position, SourceLocationAction action)
            {
                action.call(origin, line, symbol);
            }
        }
        var struct = new SourceLocationStruct();
        location(0, struct);
        return struct;
    }
}