package jena.lang.syntax;

import jena.lang.GenericPair;
import jena.lang.MutableOptional;
import jena.lang.StructPair;
import jena.lang.source.SourceSpan;

public class SyntaxGuess
{
    private SourceSpan span;
    private SyntaxRule rule;

    MutableOptional<GenericPair<Syntax, SourceSpan>> guess = new MutableOptional<>();
    MutableOptional<GenericPair<SyntaxMistake, SourceSpan>> mistake = new MutableOptional<>();

    public SyntaxGuess(SourceSpan span, SyntaxRule rule)
    {
        this.span = span;
        this.rule = rule;
    }

    private <Item> void guessAction(Item item, SourceSpan span, MutableOptional<GenericPair<Item, SourceSpan>> optional)
    {
        optional.ifPresentElse(pair -> pair.both((lastItem, lastSpan) ->
        {
            if (lastSpan.code() <= span.code())
            {
                optional.apply(new StructPair<>(item, span));
            }
        }), () ->
        {
            optional.apply(new StructPair<>(item, span));
        });
    }

    public void guess(SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, (syntax, span) -> 
        {
            guessAction(syntax, span, guess);
        }, (mistake, span) ->
        {
            /*new LocatedSyntaxMistake(mistake, span.at(0).location(0)).print(s ->
            {
                System.out.print(s.text() + " ");
            });
            System.out.println();*/
            guessAction(mistake, span, this.mistake);
        });
        guess.ifPresent(guess -> guess.both(action::call));
        mistake.ifPresent(mistake -> mistake.both(mistakeAction::call));
    }
}