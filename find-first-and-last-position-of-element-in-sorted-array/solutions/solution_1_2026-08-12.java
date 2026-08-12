class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first=findfirst(nums,target);
        int last= findlast(nums,target);
        return new int []{first,last};

    }
    public int findfirst(int [] nums, int target){
        int n=nums.length;
        int lo=0;
        int hi=n-1;
        int first=-1;
        while(lo<=hi){
            int mid=lo+(hi-lo)/2;
            if(nums[mid]==target){
                first=mid;
                hi=mid-1;
            }
            else if(nums[mid]>target){
                hi=mid-1;
            }
            else{
                lo=mid+1;
            }
        }
        return first;
    }
    public int findlast(int [] nums, int target){
        int n=nums.length;
        int lo=0;
        int hi=n-1;
        int last=-1;
        while(lo<=hi){
            int mid=lo+(hi-lo)/2;
            if(nums[mid]==target){
                last=mid;
                lo=mid+1;
            }
            else if(nums[mid]>target){
                hi=mid-1;
            }
            else{
                lo=mid+1;
            }
        }
        return last;
            
        }
    }
