package com.studycode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: lx
 * @Date: 2022/11/15
 * @Description: 三数之和  题号 #15
 * <p>
 * 给定一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * <p>
 * 示例:
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 * <p>
 * 解法 1.暴力 三层循环  O(n^3)
 * 2.双指针
 * <p>
 * 当 nums[i] > 0 时直接break跳出：因为 nums[R] >= nums[L] >= nums[i] > 0，即 3 个数字都大于 0 ，在此固定指针 i 之后不可能再找到结果了。
 * 当 i > 0且nums[i] == nums[i - 1]时，即遇到重复元素时，跳过此元素nums[i]：因为已经将 nums[i - 1] 的所有组合加入到结果中，本次双指针搜索只会得到重复组合。
 */
public class ThreeSumAdd {

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = threeSum(nums);

        System.out.println(result);

    }

    private static List<List<Integer>> threeSum(int[] nums) {
        // 排序
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            // 如果nums[i]大于0,则直接结束  因为 nums[R] >= nums[L] >= nums[i] > 0，即 3 个数字都大于 0
            int currentNum = nums[i];
            if (currentNum > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                // i > 0且nums[i] == nums[i - 1]时，即遇到重复元素时，跳过此元素nums[i]：因为已经将 nums[i - 1] 的所有组合加入到结果中，本次双指针搜索只会得到重复组合。
                continue;
            }

            // 左右两个指针
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {

                // 三数相加判断  等于零两个指针都移动，<0 左指针移动 >0右指针移动
                int sumResult = nums[left] + nums[right] + currentNum;
                if (sumResult == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 移动两个指针
                    left++;
                    right--;

                    // 判断两个指针的前后值是否一样，一样的话继续移动指针
                    while (left < right && nums[left - 1] == nums[left]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else if (sumResult < 0) {
                    left++;
                } else {
                    right--;
                }
            }


        }
        return result;
    }


}
