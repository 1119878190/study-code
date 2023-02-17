package com.studycode.array;

import java.util.Arrays;

/**
 * @Author: lx
 * @Date: 2022/12/09
 * @Description: 34. 在排序数组中查找元素的第一个和最后一个位置
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * <p>
 * 如果数组中不存在目标值 target，返回[-1, -1]。
 * <p>
 * 你必须设计并实现时间复杂度为O(log n)的算法解决此问题。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * <p>
 * 示例2：
 * <p>
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * <p>
 * 示例 3：
 * <p>
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 */
public class SearchRange {


    public static void main(String[] args) {
        //int[] nums = {5, 7, 7, 8, 8, 10};
        int[] nums = {1,5};
        System.out.println(Arrays.toString(searchRange(nums, 4)));
    }


    public static int[] searchRange(int[] nums, int target) {

        if (nums.length == 0) {
            return new int[]{-1, -1};
        }

        if (nums.length == 1){
            if (nums[0] == target){
                return new int[]{0,0};
            }else {
                return new int[]{-1, -1};
            }
        }

        int left = 0;
        int right = nums.length - 1;


        if (nums[left] < target && nums[right] < target){
            return new int[]{-1, -1};
        }


        while (left < right) {
            int leftNUm = nums[left];
            int rightNum = nums[right];
            if (nums[left] > target) {
                return new int[]{-1, -1};
            }
            if (nums[right] < target) {
                return new int[]{-1, 1};
            }

            if (leftNUm < target) {
                left++;
            }
            if (rightNum > target) {
                right--;
            }

            if (leftNUm == rightNum) {
                return new int[]{left, right};
            }
        }

        return new int[]{left, right};

    }
}
