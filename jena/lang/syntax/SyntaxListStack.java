package jena.lang.syntax;

import java.util.Stack;

public interface SyntaxListStack
{
    void push(SyntaxList list);
    SyntaxList peek();
    SyntaxList pop();
    void postponePop();
    void releasePop();

    static SyntaxListStack make(SyntaxList list)
    {
        Stack<SyntaxList> stack = new Stack<>();
        stack.push(list);
        return new SyntaxListStack()
        {
            int additionalPop;
            @Override
            public void push(SyntaxList list)
            {
                stack.push(list);
            }
            
            @Override
            public SyntaxList pop()
            {
                releasePop();
                return stack.pop();
            }
            @Override
            public SyntaxList peek()
            {
                return stack.peek();
            }
            @Override
            public void postponePop()
            {
                ++additionalPop;
            }

            @Override
            public void releasePop()
            {
                while(additionalPop > 0)
                {
                    --additionalPop;
                    stack.pop();
                }
            }
        };
    }
}