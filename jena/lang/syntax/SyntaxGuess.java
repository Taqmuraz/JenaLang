package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class SyntaxGuess
{
    private SourceSpan span;
    private SyntaxRule rule;

    private boolean hasGuess;
    private Syntax guess;
    private SourceSpan guessSpan;

    public SyntaxGuess(SourceSpan span, SyntaxRule rule)
    {
        this.span = span;
        this.rule = rule;
    }

    public void guess(SyntaxSpanAction action)
    {
        rule.match(span, (syntax, span) -> 
        {
            System.out.print("\nsyntax : ");
            syntax.source(s -> System.out.print(s.text().toString()));
            System.out.println(" span : " + span.code());

            if(hasGuess)
            {
                if(guessSpan.code() < span.code())
                {
                    guessSpan = span;
                    guess = syntax;
                }
            }
            else
            {
                hasGuess = true;
                guess = syntax;
                guessSpan = span;
            }
        });
        if(hasGuess)
        {
            action.call(guess, guessSpan);
        }
    }
}