package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;
import jena.lang.value.ArrayValue;
import jena.lang.value.MapValue;

public interface SyntaxRule
{
    Optional<SyntaxSpan> match(SourceSpan span);

    static final SyntaxRule entry = new EntrySyntaxRule();
    static final SyntaxRule name = new NameSyntaxRule();
    static final SyntaxRule integer = new IntegerLiteralSyntaxRule();
    static final SyntaxRule single = new FloatLiteralSyntaxRule();
    static final SyntaxRule symbol = new SymbolSyntaxRule();
    static final SyntaxRule text = new TextLiteralSyntaxRule();
    static final SyntaxRule none = new NoneSyntaxRule();

    static final SyntaxRule arrow = new ArrowSyntaxRule();
    static final SyntaxRule assignment = new ArrowSyntaxRule();
    static final SyntaxRule parenthesized = new ArrowSyntaxRule();

    static final SyntaxRule array = new ExpressionListSyntaxRule(Text.of('['), Text.of(']'), ArrayValue::new);
    static final SyntaxRule map = new ExpressionListSyntaxRule(Text.of('{'), Text.of('}'), MapValue::new);

    static SyntaxRule lower()
    {
        return new CompositeSyntaxRule(
            entry,
            arrow,
            assignment,
            name,
            integer,
            single,
            symbol,
            text,
            none,
            parenthesized,
            array,
            map
        );
    }
    static SyntaxRule any()
    {
        return new CompositeSyntaxRule(lower());
    }
}