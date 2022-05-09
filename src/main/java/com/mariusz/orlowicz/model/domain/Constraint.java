package com.mariusz.orlowicz.model.domain;

public interface Constraint {

    boolean validate(User user);
}
