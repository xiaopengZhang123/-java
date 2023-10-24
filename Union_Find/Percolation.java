package algo4_lab_percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] open;
    private WeightedQuickUnionUF qf, testFull;
    private int sz;
    private int virtualTop;
    private int virtualBottom;
    private int openGrids;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.sz = n;
        this.open = new boolean[sz * sz + 2];
        qf = new WeightedQuickUnionUF(sz * sz + 2);
        testFull = new WeightedQuickUnionUF(sz * sz + 2);
        open[0] = true;
        open[n * n + 1] = true;
        this.virtualTop = 0;
        this.virtualBottom = sz * sz + 1;
        this.openGrids = 0;
    }

    public void open(int row, int col) {
        int index = getIndex(row, col);//本坐标
        if (!open[index]) {
            open[index] = true;
            this.openGrids++;
            //如果打开的是第一行的点
            if (row == 1) {
                qf.union(index, virtualTop);
                testFull.union(index, virtualTop);
            }
            //看上一行有打开的吗
            if (row > 1 && open[index - sz]) {
                int index2 = index - sz;//上一行坐标
                qf.union(index, index2);
                testFull.union(index, index2);
            }
            //看下一行
            if (row < sz && open[index + sz]) {
                int index2 = index + sz;
                qf.union(index, index2);
                testFull.union(index, index2);
            }
            //看左边
            if (col > 1 && open[index - 1]) {
                int index2 = index - 1;
                qf.union(index, index2);
                testFull.union(index, index2);
            }
            if (col < sz && open[index + 1]) {
                int index2 = index + 1;
                qf.union(index, index2);
                testFull.union(index, index2);
            }
            if (row == sz) {
                qf.union(index, virtualBottom);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        if (open[index]) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        if (open[index] && (testFull.find(index) == testFull.find(virtualTop))) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return this.openGrids;
    }

    public boolean percolates() {
        if (qf.find(virtualBottom) == qf.find(virtualTop)) {
            return true;
        }
        return false;
    }

    private int getIndex(int row, int col) {
        if (row < 1 || col < 1 || row > sz || col > sz) {
            throw new IllegalArgumentException();
        }
        return (row - 1) * sz + col;
    }

    public static void main(String[] args) {

    }

}