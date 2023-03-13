package jena.lang.syntax;

public final class TextSyntaxMistakePrinter implements SyntaxMistakeAction
{
    @Override
    public void call(SyntaxMistake mistake)
    {
        mistake.print(s ->
        {
            System.out.print(s.text());
            System.out.print(" ");
        });
        System.out.println();
    }
}