package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.ValueText;
import jena.lang.value.NoneValue;
import jena.lang.value.SymbolMapValue;
import jena.lang.value.SymbolValue;

public final class BindingListSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Text open = new SingleCharacterText('{');
        Text close = new SingleCharacterText('}');
        new ExpressionListSyntaxRule(new BindingSyntaxRule(new SingleCharacterText(':')), open, close).match(span, (expressions, expressionsSpan) ->
        {
            action.call(new ExpressionListSyntax(expressions, open, close, ex -> new SymbolMapValue(symbolValueAction ->
                ex.each(e ->
                {
                    var symbolValue = e.decompose();
                    var value = symbolValue.at(1);
                    symbolValueAction.call(((SymbolValue)symbolValue.at(0)).name.string(), () -> value);
                }), args -> NoneValue.instance)), expressionsSpan);
        }, mistakeAction);
    }
}