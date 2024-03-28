package com.budget.util;

import com.budget.model.ExpensesItem;
import javafx.util.StringConverter;

/**
 * @author MR.k0F31n
 */
public class ExpenseItemStringConverter extends StringConverter<ExpensesItem>{
    @Override
    public String toString(ExpensesItem object) {
        return object != null ? object.getExpensesItem() : "";
    }

    @Override
    public ExpensesItem fromString(String string) {
        // Метод fromString() не используется
        return null;
    }
}
