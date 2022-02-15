package com.divyesh.expensetracker.repos;

import com.divyesh.expensetracker.entities.LExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepo extends JpaRepository<LExpenseCategory,Long> {

}
