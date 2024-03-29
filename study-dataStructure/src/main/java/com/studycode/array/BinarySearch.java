package com.studycode.array;

/**
 * @Author: lx
 * @Date: 2022/12/08
 * @Description: 704. 二分查找  https://leetcode.cn/problems/binary-search/
 * <p>
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 */
public class BinarySearch {


    public static void main(String[] args) {

        int[] nums = {-1, 0, 3, 5, 9, 12};

        System.out.println(search(nums, 9));
    }

    public static int search(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;


        while (left <= right) {

            int mid = left + ((right - left) >> 1);
            if (nums[mid] > target) {
                // 目标小于中间数
                right = mid - 1;
            } else if (nums[mid] < target) {
                // 目标大于中间数
                left = mid + 1;
            } else {
                // 相等
                return mid;
            }

        }

        return -1;

    }
}
