import java.util.*;

class Solution {

    int len;

    public List<List<Integer>> fourSum(int[] nums, int target) {
        len = nums.length;
        Arrays.sort(nums);
        return kSum(nums, (long) target, 4, 0);
    }

    private List<List<Integer>> kSum(int[] nums, long target, int k, int start) {

        List<List<Integer>> res = new ArrayList<>();

        if (start >= len) {
            return res;
        }

        // Base Case: 2Sum
        if (k == 2) {
            int left = start;
            int right = len - 1;

            while (left < right) {

                long sum = (long) nums[left] + nums[right];

                if (sum == target) {

                    List<Integer> pair = new ArrayList<>();
                    pair.add(nums[left]);
                    pair.add(nums[right]);
                    res.add(pair);

                    while (left < right && nums[left] == nums[left + 1])
                        left++;
                    while (left < right && nums[right] == nums[right - 1])
                        right--;

                    left++;
                    right--;

                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }

            return res;
        }

        // Recursive Case
        for (int i = start; i < len - k + 1; i++) {

            if (i > start && nums[i] == nums[i - 1]) {
                continue; // skip duplicates
            }

            List<List<Integer>> temp =
                    kSum(nums, target - nums[i], k - 1, i + 1);

            for (List<Integer> list : temp) {
                list.add(0, nums[i]);
                res.add(list);
            }
        }

        return res;
    }
}