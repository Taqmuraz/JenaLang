package jena.lang.value;

import jena.lang.GenericPair;
import jena.lang.GenericPairBothAction;
import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class NamePair<Element> implements GenericPair<Text, Element>
{
    private Text text;
    private Element element;

    public NamePair(String name, Element element)
    {
        this.text = new StringText(name);
        this.element = element;
    }

    @Override
    public void both(GenericPairBothAction<Text, Element> action)
    {
        action.call(text, element);
    }
}