package org.example;

// https://docs.google.com/document/d/17gCUybj3ZSgC0sWCAu9gYdzlhfMZeMaXt9R__N9-nkw/edit

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {

        ConcurrentHashMap<Thread, Optional<ArrayList<Dollar>>> theifsNotebook = new ConcurrentHashMap<>();
        ArrayList<Thread> allTheifs = new ArrayList<>();
        Safe safe = Safe.createSafe();
        Runnable r = () -> {
            try {
                Thread.sleep(101);
            } catch (InterruptedException e) {
                System.out.printf("     %s was caught by the police officer! \n", Thread.currentThread().getName());
                return;
            }
            Optional<ArrayList<Dollar>> money = safe.getMoney();
            theifsNotebook.put(Thread.currentThread(), money);
        };



        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(r);
            t.setName("Thief " + (i + 1));
            allTheifs.add(t);
            t.start();
        }

        Thread officer = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            allTheifs.forEach(Thread::interrupt); //allTheifs.forEach(x -> x.interrupt());

        });
        officer.start();

        //this code only for waiting till the "death" of all the threads
        allTheifs.forEach(x -> {
            while(x.isAlive()) {}
        });

        for(var entry : theifsNotebook.entrySet()) {
            Thread theif = entry.getKey();
            Optional<ArrayList<Dollar>> isMoney = entry.getValue();
            try{
                System.out.printf("%s got %s dollars \n", theif.getName(), isMoney.get().size());
            } catch (NoSuchElementException ex) {
                System.out.printf("%s got nothing! \n", theif.getName());
            }

        }




    }
}