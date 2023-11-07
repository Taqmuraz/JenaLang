package jena.lang.syntax;

import java.util.Stack;
import java.util.function.Function;

import jena.lang.GenericBuffer;
import jena.lang.GenericList;
import jena.lang.IterableFlow;
import jena.lang.text.Text;

public interface SyntaxList extends SyntaxUnit
{
    void push(SyntaxUnit unit);
    SyntaxUnit pop();
    int size();
    SyntaxStackRule arrowRule();

    static SyntaxList of(Function<GenericList<Syntax>, Syntax> complete)
    {
        return of(complete, SyntaxStackRule.arrowRuleCommon());
    }

    static SyntaxList of(Function<GenericList<Syntax>, Syntax> complete, SyntaxStackRule arrowRule)
    {
        Stack<SyntaxUnit> stack = new Stack<>();
        return new SyntaxList()
        {
            @Override
            public void push(SyntaxUnit unit)
            {
                stack.push(unit);
            }
            @Override
            public Syntax complete()
            {
                return complete.apply(GenericList.of(stack.stream().map(l -> l.complete()).toList()));
            }
            @Override
            public SyntaxUnit pop()
            {
                return stack.pop();
            }
            @Override
            public int size()
            {
                return stack.size();
            }
            @Override
            public SyntaxStackRule arrowRule()
            {
                return arrowRule;
            }
            @Override
            public String toString()
            {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                new IterableFlow<>(stack).join(s -> sb.append(s.toString()), () -> sb.append(" "));
                sb.append("]");
                return sb.toString();
            }
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeInvocationList()
    {
        return list ->
        {
            Syntax[] result = { new NoneValueSyntax() };
            list.read((first, rest) ->
            {
                result[0] = first;
                rest.iterate(f ->
                {
                    result[0] = new InvocationExpressionSyntax(result[0], f);
                });
            });
            return result[0];
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeArrayList()
    {
        return list ->
        {
            return ExpressionListSyntax.arraySyntax(GenericBuffer.of(list.collect()));
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeMapList()
    {
        return list ->
        {
            return ExpressionListSyntax.mapSyntax(GenericBuffer.of(list.collect()));
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeArrowList()
    {
        return list ->
        {
            Syntax[] result = { new NoneValueSyntax() };
            list.read((left, rest) ->
            {
                var right = completeInvocationList().apply(rest);
                result[0] = new ArrowExpressionSyntax(left, right);
            });
            return result[0];
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeAssignmentList()
    {
        return list ->
        {
            Syntax[] result = { new NoneValueSyntax() };
            list.read((left, rest) ->
            {
                rest.reverse().read((last, rev) ->
                {
                    var right = completeInvocationList().apply(rev.reverse());
                    result[0] = new AssignmentExpressionSyntax(right, Text.of(left), last);
                });
            });
            return result[0];
        };
    }
    static SyntaxList invocationList()
    {
        return of(completeInvocationList());
    }
    static SyntaxList arrayList()
    {
        return of(completeArrayList());
    }
    static SyntaxList mapList()
    {
        return of(completeMapList());
    }
    static SyntaxList arrowList()
    {
        return of(completeArrowList());
    }
    static SyntaxList assignmentList()
    {
        return of(completeAssignmentList(), SyntaxStackRule.arrowRuleAssignment());
    }
}