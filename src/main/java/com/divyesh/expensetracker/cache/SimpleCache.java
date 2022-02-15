package com.divyesh.expensetracker.cache;

import com.divyesh.expensetracker.entities.User;

import java.util.HashMap;
import java.util.Map;

public class SimpleCache {//session cache
    public static Map<String, User> cache;
    private static SimpleCache sc =null;
    private SimpleCache() {
//         sc = new SimpleCache();
        cache = new HashMap<String, User>();
    }


    public static SimpleCache getInstance(){
        if(cache==null)
            sc = new SimpleCache();
//
        return sc;
    }
    public User getCacheUser(String key){
        return cache.get(key);
    }

    public void setCacheUser(String key,User u){
        cache.put(
                key,u
        );
    }

    public void removeCacheUser(String key){
        cache.remove(key);
    }





}
