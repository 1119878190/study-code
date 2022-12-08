package com.studycode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: lx
 * @Date: 2022/12/08
 * @Description: 18. 四数之和  https://leetcode.cn/problems/4sum/
 * <p>
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 * <p>
 * 我们延续三数之和的思路，在三数之和外面再套一层循环。
 */
public class FourSumAdd {


    public static void main(String[] args) {
        int[] nums = new int[]{1, 0, -1, 0, -2, 2};
        int target = 0;
        List<List<Integer>> result = forSum(nums, target);

        System.out.println(result);


    }

    private static List<List<Integer>> forSum(int[] nums, int target) {

        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {

            int currentNum = nums[i];

            // 去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < nums.length - 2; j++) {


                // 去重
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }


                int secondNum = nums[j];

                int left = i + 2;
                int right = nums.length - 1;


                while (left < right) {

                    int sum = currentNum + secondNum + nums[left] + nums[right];
                    if (sum == target) {

                        result.add(new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[left], nums[right])));


                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }


                        left++;
                        right--;

                    } else if (sum > target) {
                        right--;

                    } else {
                        left++;
                    }

                }


            }


        }
        return result;

    }
}
