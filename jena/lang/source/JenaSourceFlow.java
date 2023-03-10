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
            .split(new SingleCharacterKind('.'))
            .split(new SingleCharacterKind(','))
            .split(new SingleCharacterKind(':'))
            .flatMap(s -> new GroupCharacterSeparatedSourceFlow(s, new GroupCharacterKind('+', '-', '*', '/', '=', '!', '<', '>')))
            .notFilter(new EmptySourceFilter())
            .map(s -> new CharacterBufferSource(s.text()));
    }

    @Override
    public void read(SourceAction reader) 
    {
        flow.read(reader);
    }
}