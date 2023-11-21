package com.in28minutes.rest.webservices.restfulwebservices.versioning;

public class PersonV2 {
    private Name name;

    public Name getName () {
        return name;
    }

    public PersonV2 (Name name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return "PersonV2{" +
                "name=" + name +
                '}';
    }
}
