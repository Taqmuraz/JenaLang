package jena.lang.syntax;

import java.util.Stack;

public interface SyntaxListStack
{
    void push(SyntaxList list);
    SyntaxList peek();
    SyntaxList pop();

    static SyntaxListStack make(SyntaxList list)
    {
        Stack<SyntaxList> stack = new Stack<>();
        stack.push(list);
        return new SyntaxListStack()
        {
            @Override
            public void push(SyntaxList list)
            {
                stack.push(list);
            }
            @Override
            public SyntaxList pop()
            {
                return stack.pop();
            }
            @Override
            public SyntaxList peek()
            {
                return stack.peek();
            }
        };
    }
}