package com.xwdz.site.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    private static final ExecutorService sExecutors = Executors.newFixedThreadPool(20);

    public static void execute(Runnable runnable) {
        sExecutors.execute(runnable);
    }
}
