package com.mariusz.orlowicz.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Product {

    public enum Constraints {
        VIP_ONLY ("VIP_ONLY"),
        FOR_RICH_PEOPLE("FOR_RICH_PEOPLE"),
        WITH_RELATIONS("WITH_RELATIONS");

        private final String value;

        Constraints(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Logical {
        AND("AND"),
        OR ("OR"),
        NOT ("NOT"),
        END ("END");

        private final String value;

        Logical(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private String id;
    private String constraintText;

    public String getId() {
        return id;
    }

    public String getConstraintText() {
        return constraintText;
    }
}
