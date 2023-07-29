package com.wojto.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {

    private final List<Integer> list;
    private final int left;
    private final int right;

    public MergeSortTask(List<Integer> list, int left, int right) {
        this.list = list;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int middle = (left + right) / 2;
            invokeAll(new MergeSortTask(list, left, middle),
                    new MergeSortTask(list, middle + 1, right));
            merge(list, left, middle, right);
        }
    }

    private void merge(List<Integer> list, int left, int middle, int right) {
        List<Integer> result = new ArrayList<>(right - left + 1);
        int i = left;
        int j = middle + 1;
        while (i <= middle && j <= right) {
            if (list.get(i) < list.get(j)) {
                result.add(list.get(i++));
            } else {
                result.add(list.get(j++));
            }
        }
        while (i <= middle) {
            result.add(list.get(i++));
        }
        while (j <= right) {
            result.add(list.get(j++));
        }
        for (int k = 0; k < result.size(); k++) {
            list.set(left + k, result.get(k));
        }
    }
}