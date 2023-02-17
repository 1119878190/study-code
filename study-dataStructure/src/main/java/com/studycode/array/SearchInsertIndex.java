package com.studycode.array;

/**
 * @Author: lx
 * @Date: 2022/12/08
 * @Description: 35. 搜索插入位置  https://leetcode.cn/problems/search-insert-position/
 * <p>
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * <p>
 * 请必须使用时间复杂度为 O(log n) 的算法。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 * <p>
 * 输入: nums = [1,3,5,6], target = 2
 * 输出: 1
 * 示例 3:
 * <p>
 * 输入: nums = [1,3,5,6], target = 7
 * 输出: 4
 *
 *
 * 思路： 二分查找
 *
 * target<nums[0] : 在最左侧插入
 * target>nums[length-1] :在最右侧插入
 * target=nums[i] : 和数组中元素相同，插入位置i
 * nums[i]<target<nums[i+1] :在i位置之后插入
 *
 *
 */
public class SearchInsertIndex {


    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,6};

        System.out.println(search(nums, 5));

    }

    public static int search(int[] nums, int target) {


        int left = 0;
        int right = nums.length - 1;


        while (left <= right) {

            int mid = left + ((right - left) >> 1);

            if (nums[left] > target) {
                return left - 1;
            }
            if (nums[right] < target){
                return right +1;
            }

            if (target > nums[mid]) {
                // 右移
                left = mid + 1;

            } else if (target < nums[mid]) {
                // 左移
                right = mid - 1;
            }else {
                return mid;
            }

        }

        // 在某个元素之后插入
        //因为退出条件是left==right，所以返回left或者right都可以
        return left;
    }
}
