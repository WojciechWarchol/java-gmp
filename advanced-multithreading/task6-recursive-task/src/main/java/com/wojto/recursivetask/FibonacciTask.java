package com.wojto.recursivetask;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Integer> {

    final int n;

    FibonacciTask(int n) {
        this.n = n;
    }

    public Integer compute() {
        if (n <= 10)
            return linearCompute(n);
        FibonacciTask f1 = new FibonacciTask(n - 1);
        f1.fork();
        FibonacciTask f2 = new FibonacciTask(n - 2);
        return f2.compute() + f1.join();
    }

    /* Adding granularity of 10, and using this linear method instead of the RecursiveTask one all the way to n <=1
     * decreased the time of computing from around 13-16 sec to just above 1 sec.
     */
    public static int linearCompute(int n) {
        if (n <= 1) {
            return n;
        }
        return linearCompute(n - 1) + linearCompute(n - 2);
    }

}
