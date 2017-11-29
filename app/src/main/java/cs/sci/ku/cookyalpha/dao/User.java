package cs.sci.ku.cookyalpha.dao;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

public class User {
    private String name;
    private String id;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
