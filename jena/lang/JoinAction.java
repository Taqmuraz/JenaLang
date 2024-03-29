package jena.lang;

public final class JoinAction<Element>
{
    private GenericFlow<Element> flow;
    private GenericAction<Element> elementAction;
    private Action separatorAction;
    private int index;
    
    public JoinAction(GenericFlow<Element> flow, GenericAction<Element> elementAction, Action separatorAction)
    {
        this.flow = flow;
        this.elementAction = elementAction;
        this.separatorAction = separatorAction;
    }

    public void join()
    {
        flow.read(element ->
        {
            if(index++ > 0) separatorAction.call();
            elementAction.call(element);
        });
    }
}