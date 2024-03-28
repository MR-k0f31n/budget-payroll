package com.budget.model;

import lombok.Getter;

/**
 * @author MR.k0F31n
 */
@Getter
public enum ExpensesItem {
    OTHER("ДРУГОЕ"),
    SPARE_PARTS("ЗАПАСНЫЕ ЧАСТИ"),
    SALARY("ЗАРПЛАТА"),
    GUARANTEE("ГАРАНТИЯ"),
    BANK("КРЕДИТЫ/ДОЛГИ"),
    TRANSFERS("ПЕРЕВОДЫ"),
    THIRD_PARTY_WORK("РАБОТА ДРУГИХ СЕРВИСОВ");

    private final String expensesItem;

    ExpensesItem(String item) {
        this.expensesItem = item;
    }
}
