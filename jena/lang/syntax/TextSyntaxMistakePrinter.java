package jena.lang.syntax;

public final class TextSyntaxMistakePrinter implements SyntaxMistakeAction
{
    @Override
    public void call(SyntaxMistake mistake)
    {
        mistake.print(text ->
        {
            System.out.print(text);
            System.out.print(" ");
        });
        System.out.println();
    }
}