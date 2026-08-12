/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        int [] arr =new int [2];
        depth(root,arr);
        return arr[1];
    }
    public  int [] depth(TreeNode node, int [] arr){
        if(node==null){
            return new int [] {0,0};
        }
        int[] left=depth(node.left,arr);
        int [] right=depth(node.right,arr);

        arr[1]=Math.max(arr[1],right[0]+left[0]);
        return new int[]{Math.max(left[0],right[0])+1, arr[1]};

    }
}