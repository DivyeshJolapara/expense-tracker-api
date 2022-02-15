package com.divyesh.expensetracker.controllers;


import com.divyesh.expensetracker.cache.SimpleCache;
import com.divyesh.expensetracker.entities.Expense;
import com.divyesh.expensetracker.entities.User;
import com.divyesh.expensetracker.models.ExpenseListResponse;
import com.divyesh.expensetracker.repos.ExpenseCategoryRepo;
import com.divyesh.expensetracker.repos.ExpenseRepo;
//import org.slf4j.event.LoggingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
public class ExpenseController {

//    private final String TOKEN="token_12345";
    @Autowired
    private ExpenseRepo expenseRepo;

    private SimpleCache sc = SimpleCache.getInstance();
    @Autowired
    private ExpenseCategoryRepo categoryRepo;

    @GetMapping(value = "/all",produces = "Application/JSON")
    public ResponseEntity<ExpenseListResponse> getAllExpenses(@RequestHeader Map<String, String> headers,Pageable pageable){
        String _token = headers.get("token");
        if(sc.getCacheUser(_token)==null){
            return ResponseEntity.status(401).body(new ExpenseListResponse());
        }
        try {
//            Page<Expense> list =  expenseRepo.findAll(pageable);//.getContent();

            Page<Expense> list =  expenseRepo.findAllByUser(pageable,sc.getCacheUser(_token));

            ExpenseListResponse res = new ExpenseListResponse();
            res.setExpenses(list);
            Long userId = sc.getCacheUser(_token).getId();
            res.setGrossExpense(expenseRepo.findAll()
                            .stream()
                            .filter(ex->{
                                if(userId.equals(ex.getUser().getId())) {
                                    return true;
                                }
                                else
                                    return false;
                            })
                            .map(ex->ex.getAmount())
                            .reduce(0.0,(a,b)-> a+b));


            return ResponseEntity.ok(res);

//            return list;
        }catch(Exception e){
            System.out.println("********************djtest error in getAllExpenses:"+e);

        }
        return null;
    }

    @GetMapping("/all/order/{field}/{direction}")
    public ResponseEntity<ExpenseListResponse> getAllExpensesOrdered(@RequestHeader Map<String, String> headers,Pageable pageable){


        return null;
    }


    @PostMapping(value = "/create")
    public ResponseEntity createExpense(@RequestBody Expense expense,@RequestHeader Map<String,String> headers){
        String token = headers.get("token");
        User u = sc.getCacheUser(token);
        if(u==null){
            return ResponseEntity.status(401).build();
        }
        try{
            expense.setUser(u);
            expense.setDate(System.currentTimeMillis());//new Timestamp(System.currentTimeMillis()));
            expenseRepo.save(expense);
            return ResponseEntity.status(201).build();
        }catch(Exception e){}

        return ResponseEntity.internalServerError().build();

    }
    @PostMapping("/delete/{expenseId}")
    public String deleteExpense(@PathVariable("expenseId") String expenseId){
        try{
            if (expenseId == null){
                throw new NullPointerException();
            }
            expenseRepo.deleteById(Long.parseLong(expenseId));
            return "{}";
        }catch(Exception e){
            e.printStackTrace();
        }
        return "{}";

    }


    @PutMapping("edit/{expenseId}")
    public ResponseEntity editExpense(@PathVariable Long expenseId,@RequestBody Expense editedExpense){
        Optional<Expense> expense = expenseRepo.findById(expenseId);
        if(expense.isPresent()){
            Expense expenseObj = expense.get();
            expenseObj.setAmount(editedExpense.getAmount());
            expenseObj.setName(editedExpense.getName());
            expenseRepo.save(expenseObj);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/categories")
    private List getAllCategories(){
//        Map<Long,String> cats = new HashMap<>();
        List l = categoryRepo.findAll();/*.stream().map(cat->{
            return Map.entry(cat.getId(),cat.getCategory());
        }).toList();*/
        return l;

    }




}
