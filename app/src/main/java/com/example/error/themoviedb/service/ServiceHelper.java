package com.example.error.themoviedb.service;

public class ServiceHelper {
    private static ServiceHelper instance = null;

    private ServiceHelper() {
    }
    public static synchronized ServiceHelper getInstance() {
        if (instance == null) {
            instance = new ServiceHelper();
        }
        return instance;
    }

    public void doSomething() {

    }
}
