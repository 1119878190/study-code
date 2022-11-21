package com.studycode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lx
 * @Date: 2022/11/15
 * @Description: 两数之和   题号
 * <p>
 * 给定一个整数数组nums和一个目标值target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 *
 *  思路: 通过hashmap的特性，key为数组的值，value为值在数组中的下标，通过containsKey判断
 *
 */
public class TwoSumAdd {


    public static void main(String[] args) {

        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] ints = mapResolve(nums, target);


        System.out.println(Arrays.toString(ints));


    }

    private static int[] mapResolve(int[] nums, int target) {
        // map 解法


        Map<Integer, Integer> map = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            boolean b = map.containsKey(target - num);
            if (b) {
                // 存在
                return new int[]{map.get(target - num),i };
            } else {
                // 不存在
                map.put(num, i);
            }
        }
        return new int[]{};
    }
}
