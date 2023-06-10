package com.itlang.util;

import java.util.Collections;
import java.util.List;

public class ListUtils {
    public static <T> List<T> shuffleList(List<T> list) {
        Collections.shuffle(list);
        return list;
    }
}
