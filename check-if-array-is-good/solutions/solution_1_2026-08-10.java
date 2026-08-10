class Solution {
    public boolean isGood(int[] nums) {
        int n=nums.length;
        int base=n-1;
        int bcount=0;
        Set<Integer> set =new HashSet<>();
        for(int i : nums){
            if(i>base) return false;
            else if(i==base){
                bcount++;
            }
            else{
                if(set.contains(i)) return false;
                set.add(i);
            }
        }
        return set.size() == base-1 && bcount==2;
    }
}