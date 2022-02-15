package com.divyesh.expensetracker.repos;

import com.divyesh.expensetracker.entities.Expense;
import com.divyesh.expensetracker.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends PagingAndSortingRepository<Expense,Long> {
    List<Expense> findAllByOrderByDateDesc();
    List<Expense> findAll();
    Page<Expense> findAllByUser(Pageable pageable, User u);
}
