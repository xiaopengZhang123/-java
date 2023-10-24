package algo4_lab_percolation;

public class UnionFind {
    private int[]arr;
    private int find(int index){
        if(index==arr[index]){
            return index;
        }
        arr[index]=find(arr[index]);
        return arr[index];
    }
}
