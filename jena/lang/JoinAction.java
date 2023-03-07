package jena.lang;

public final class JoinAction<Element>
{
    private GenericFlow<Element> array;
    private GenericAction<Element> elementAction;
    private Action separatorAction;
    
    public JoinAction(GenericFlow<Element> flow, GenericAction<Element> elementAction, Action separatorAction)
    {
        this.array = flow;
        this.elementAction = elementAction;
        this.separatorAction = separatorAction;
    }

    public void join()
    {
        array.read((element, index) ->
        {
            if(index > 0) separatorAction.call();
            elementAction.call(element);
        });
    }
}