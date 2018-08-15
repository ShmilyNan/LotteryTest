package com.jphy.lottery.plugins.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 求在M中找出N的排列数
 * 算法思想：递归
 *  eg:abcd的全排列结果分别为：a,b,c,d,ab,ac,ad,bc,bd,cd,abc,abd,acd,bcd,abcd
 *  可以看出，当求N位的组合数时，可以先固定前N-1位，然后在匹配最后一位可行值；以此类推可用递归的方法求出所有可能的值。
 *
 * @author YHYR
 *
 */
public class Combine {
    //public static void main(String[] args) {
    //    List<Character> data = new ArrayList<Character>();
    //    data.add('a');
    //    data.add('b');
    //    data.add('c');
    //    data.add('d');
    //    Combine t = new Combine();
    //
    //    for (int i = 1; i < data.size(); i++)
    //        t.combinerSelect(data, new ArrayList<Character>(), data.size(), i);
    //}

    /**
     * 步骤：：每次递归时，把原始数据和满足条件的工作空间复制一份，所有的操作均在复制文件中进行，目的就是保证不破坏原始数据，
     * 从而可以让一轮递归结束后可以正常进行下一轮。
     * 其次，把数据的第一个元素添加到工作空间中，判断工作空间的大小，如果小于k,则需要继续递归，但此时，传入递归函数的
     * 参数需要注意：假设当前插入的节点的下标是i,因为是顺序插入的,所以i之前的所有数据都应该舍去，只传入i之后的未使用过的数据。
     * 因此在传参之前，应该对copydata作以处理；当大于k的时候，则表明已经找到满足条件的第一种情况，然后只需修改该情况的最后一个结果即可。
     * 如：找到abc时，则只需替换c为d即可完成该轮递归。
     *
     * @param data      原始数据
     * @param workSpace 自定义一个临时空间，用来存储每次符合条件的值
     * @param k         C(n,k)中的k
     */
    public <E> void combinerSelect(List<E> data, List<E> workSpace, int n, int k) {
        List<E> copyData;
        List<E> copyWorkSpace;

        if (workSpace.size() == k) {
            for (Object c : workSpace)
                System.out.print(c);
            System.out.println();
        }

        for (int i = 0; i < data.size(); i++) {
            copyData = new ArrayList<E>(data);
            copyWorkSpace = new ArrayList<E>(workSpace);

            copyWorkSpace.add(copyData.get(i));
            for (int j = i; j >= 0; j--)
                copyData.remove(j);
            combinerSelect(copyData, copyWorkSpace, n, k);
        }

    }
}
