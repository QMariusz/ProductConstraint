package com.mariusz.orlowicz.model.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class User {

    public enum UserConstraints {
        VIP,
        RICH,
        HAS_RELATIONS
    }

    private Set<UserConstraints> userConstraintsSet;
}
