package org.example;


import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Safe {
    private static Safe _instance;
    private static Random _rnd = new Random();

    private static ArrayList<Dollar> _value = new ArrayList<>();


    private Safe() {
        int treasureSize = _rnd.nextInt(10000);
        for(int i = 0; i < treasureSize; i++) {
            _value.add(new Dollar());
        }
        System.out.printf("Safe created, it has %s dollars \n", _value.size());
        System.out.println("----------------------------------\n");
    }

    public static Safe createSafe() {
        if(_instance == null) {
            synchronized (Safe.class) {
                if(_instance == null) {
                    _instance = new Safe();
                }
            }
        }
        return _instance;
    }

    public Optional<ArrayList<Dollar>> getMoney() {
        synchronized (Safe.class) {
            ArrayList<Dollar> m = _value;
            if (_value != null) {
                _value = null;
                System.out.printf("The safe with %s dollars was acquired by %s\n", m.size(), Thread.currentThread().getName());
                System.out.println("----------------------------------\n");
            }
            return Optional.ofNullable(m);
        }
    }
}
