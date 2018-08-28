package com.jphy.lottery.plugins.Sort;

import java.util.ArrayList;
import java.util.List;


public class Arrange {
    List<String> numberOfPK10 = new ArrayList<String>();

    /**
     * 计算A(n,k)
     *
     * @param data
     * @param target
     * @param k
     */
    public <E> List<String> arrangeSelect(List<E> data, List<E> target, int k) {
        String str = "";
        List<E> copyData;
        List<E> copyTarget;
        if (target.size() == k) {
            for (E i : target) {
                str = str + i;
            }
            numberOfPK10.add(str);
        }
        for (int i = 0; i < data.size(); i++) {
            copyData = new ArrayList<E>(data);
            copyTarget = new ArrayList<E>(target);

            copyTarget.add(copyData.get(i));
            copyData.remove(i);

            arrangeSelect(copyData, copyTarget, k);
        }
        return numberOfPK10;
    }
}

