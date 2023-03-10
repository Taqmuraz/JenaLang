package jena.lang.source;

public final class GroupCharacterKind implements CharacterKind
{
    private char[] group;

    public GroupCharacterKind(char...group)
    {
        this.group = group;
    }

    @Override
    public boolean isKind(char c)
    {
        if(group.length == 0) return false;

        for(int i = 0; i < group.length; i++) if(c == group[i]) return true;
        return false;
    }
}