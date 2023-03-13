package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class MemberListPrinter
{
    private GenericBuffer<GenericPair<Text, Value>> members;

    public MemberListPrinter(GenericBuffer<GenericPair<Text, Value>> members)
    {
        this.members = members;
    }

    public void print(TextWriter writer)
    {
        members.flow().read(p -> p.both((n, v) ->
        {
            writer.write(n);
            writer.write(new SingleCharacterText(';'));
        }));
    }
}