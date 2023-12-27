package jena.lang.source;

public interface SourceSpan
{
    Source at(int index);
    SourceSpan skip(int count);
    int code();

    static SourceSpan empty()
    {
        return new SourceSpan()
        {
            @Override
            public Source at(int index)
            {
                return Source.empty();
            }
            @Override
            public SourceSpan skip(int count)
            {
                return this;
            }
            @Override
            public int code()
            {
                return 0;
            }
        };
    }
}