package jena.lang.value;

import java.util.HashMap;

import jena.lang.ActionTwo;

public class MembersMap<TMember>
{
    public interface Member<TMember>
    {
        Value call(TMember self);
    }
    public interface StringMemberPair<TMember>
    {
        void call(ActionTwo<String, Member<TMember>> use);
    }

    HashMap<String, Member<TMember>> members = new HashMap<>();

    public MembersMap(StringMemberPair<TMember> pairs)
    {
        pairs.call(members::put);
    }

    public Member<TMember> member(String name, Member<TMember> defaultMember)
    {
        var member = members.get(name);
        if(member == null) return defaultMember;
        return member;
    }
}