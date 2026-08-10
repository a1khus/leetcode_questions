class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer>pq=new PriorityQueue<>((a,b)->b-a);
        for(int val:nums)pq.offer(val);
        while(k-->1&&!pq.isEmpty())pq.poll();
        return pq.peek();
    }
}