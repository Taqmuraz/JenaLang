package jena.lang.source;

public final class JenaSourceFlow implements SourceFlow
{
    private SourceFlow flow;

    public JenaSourceFlow(SourceFlow flow)
    {
        this.flow = flow
            .split(new SingleCharacterKind('('))
            .split(new SingleCharacterKind(')'))
            .split(new SingleCharacterKind('{'))
            .split(new SingleCharacterKind('}'))
            .split(new SingleCharacterKind('['))
            .split(new SingleCharacterKind(']'))
            .split(new SingleCharacterKind('+'))
            .split(new SingleCharacterKind('-'))
            .split(new SingleCharacterKind('*'))
            .split(new SingleCharacterKind('/'))
            .split(new SingleCharacterKind('.'))
            .split(new SingleCharacterKind(','))
            .split(new SingleCharacterKind('>'))
            .split(new SingleCharacterKind('<'))
            .split(new SingleCharacterKind(':'))
            .notFilter(new EmptySourceFilter())
            .notKindFilter(SpaceCharacterKind.instance);
    }

    @Override
    public void read(SourceAction reader) 
    {
        flow.read(reader);
    }
}