package jena.lang.source;

public final class JenaSourceFlow implements SourceFlow
{
    private SourceFlow flow;

    public JenaSourceFlow(Source source)
    {
        this.flow = new StringLiteralFlow(source)
            .split(new SingleCharacterKind('('))
            .split(new SingleCharacterKind(')'))
            .split(new SingleCharacterKind('{'))
            .split(new SingleCharacterKind('}'))
            .split(new SingleCharacterKind('['))
            .split(new SingleCharacterKind(']'))
            .split(new SingleCharacterKind('.'))
            .split(new SingleCharacterKind(','))
            .split(new SingleCharacterKind(':'))
            .flatMap(src -> src.flow(s -> new GroupCharacterSeparatedSourceFlow(s, new GroupCharacterKind('+', '-', '*', '/', '=', '!', '<', '>'))))
            .notFilter(new EmptySourceFilter())
            ;//.map(s -> new CharacterBufferSource(s.text()));
    }

    @Override
    public void read(SourceAction reader) 
    {
        flow.read(reader);
    }
}