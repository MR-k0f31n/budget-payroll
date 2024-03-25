package com.budget.model;

import lombok.Getter;

/**
 * @author MR.k0F31n
 */
@Getter
public enum ExpensesItem {
    OTHER("Другое"),
    SPARE_PARTS("Запасные части"),
    SALARY("Зарплата"),
    GUARANTEE("Гарантия"),
    THIRD_PARTY_WORK("Работа других сервисов");

    private final String expensesItem;

    ExpensesItem(String item) {
        this.expensesItem = item;
    }
}
