package com.mariusz.orlowicz.model;

import java.util.Set;

public class User {

    public enum UserConstraints {
        VIP,
        RICH,
        RELATION
    }

    private Set<UserConstraints> userConstraintsSet;

    public Set<UserConstraints> getUserConstraintsSet() {
        return userConstraintsSet;
    }

    public void setUserConstraintsSet(Set<UserConstraints> userConstraintsSet) {
        this.userConstraintsSet = userConstraintsSet;
    }
}
