package com.mariusz.orlowicz.domain;

import com.mariusz.orlowicz.model.User;

public interface Constraint {

    boolean validate(User user);
}
