package com.company;

import java.util.Iterator;

public class BadguyBodyGenerator implements Iterable<Character> {
    @Override
    public Iterator<Character> iterator() {
        return new Iterator<Character>() {
            Character[] views = {'/', '-', '\\', '|'};
            int current = 0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Character next() {
                return views[current++ % views.length];
            }
        };
    }
}
