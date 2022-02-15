package com.divyesh.expensetracker.models;

import com.divyesh.expensetracker.entities.Expense;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ExpenseListResponse {
    private Double grossExpense;
    private Page<Expense> expenses;
}
