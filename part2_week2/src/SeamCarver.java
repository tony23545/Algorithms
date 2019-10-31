import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class SeamCarver {
    private Picture curPic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if(picture == null)
            throw new IllegalArgumentException("empty picture!");

        curPic = new Picture(picture);
    }

    // current picture
    public Picture picture(){
        Picture pic = new Picture(curPic);
        return pic;
    }

    // width of current picture
    public int width(){
        return curPic.width();
    }

    // height of current picture
    public int height(){
        return curPic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if(x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException();

        // dual-gradient energy function
        // if the pixel fall on boarder
        if(x == 0 || x == width()-1 || y == 0 || y == height()-1)
            return 1000.0;

        Color left = curPic.get(x-1, y);
        Color right = curPic.get(x+1, y);
        Color up = curPic.get(x, y-1);
        Color down = curPic.get(x, y+1);

        double deltaX = Math.pow(left.getRed() - right.getRed(), 2) + Math.pow(left.getGreen() - right.getGreen(), 2) + Math.pow(left.getBlue() - right.getBlue(), 2);
        double deltaY = Math.pow(up.getRed() - down.getRed(), 2) + Math.pow(up.getGreen() - down.getGreen(), 2) + Math.pow(up.getBlue() - down.getBlue(), 2);

        return Math.sqrt(deltaX + deltaY);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        // do not construct explicit DirectedEdge and EdgeWeightedDigraph objects.
        double[][] costList = new double[width()][height()];
        int[][] from = new int[width()][height()];

        for(int i = 0; i < height(); i++) {
            costList[0][i] = energy(0, i);
            from[0][i] = i;
        }

        double left, middle, right;
        for(int i = 1; i < width(); i++){
            for(int j = 0; j < height(); j++){
                if(j == 0) left = Double.MAX_VALUE;
                else left = costList[i-1][j-1];
                if(j == height()-1) right = Double.MAX_VALUE;
                else right = costList[i-1][j+1];
                middle = costList[i-1][j];

                from[i][j] = j + argMin(left, middle, right);
                costList[i][j] = costList[i-1][from[i][j]] + energy(i, j);
            }
        }

        int[] Seam = new int[width()];
        double minCost = costList[width()-1][0];
        int minH = 0;
        for(int i = 1; i < height(); i++){
            if(costList[width()-1][i] < minCost){
                minCost = costList[width()-1][i];
                minH = i;
            }
        }

        Seam[width()-1] = minH;
        for(int i = width()-1; i > 0; i--)
            Seam[i-1] = from[i][Seam[i]];
        return Seam;
    }

    // sequence of indices for vertical seam{
    public int[] findVerticalSeam(){
        double[][] costList = new double[width()][height()];
        int[][] from = new int[width()][height()];

        for(int i = 0; i < width(); i++) {
            costList[i][0] = energy(i, 0);
            from[i][0] = i;
        }

        double up, middle, down;
        for(int j = 1; j < height(); j++){
            for(int i = 0; i < width(); i++){
                if(i == 0) up = Double.MAX_VALUE;
                else up = costList[i-1][j-1];
                if(i == width() - 1) down = Double.MAX_VALUE;
                else down = costList[i+1][j-1];
                middle = costList[i][j-1];

                from[i][j] = i + argMin(up, middle, down);
                costList[i][j] = costList[from[i][j]][j-1] + energy(i, j);
            }
        }

        int[] Seam = new int[height()];
        double minCost = costList[0][height()-1];
        int minH = 0;
        for(int i = 1; i < width(); i++){
            if(costList[i][height()-1] < minCost){
                minCost = costList[i][height()-1];
                minH = i;
            }
        }

        Seam[height()-1] = minH;
        for(int i = height()-1; i > 0; i--)
            Seam[i-1] = from[Seam[i]][i];
        return Seam;
    }

    private int argMin(double a1, double a2, double a3){
        if(a1 > a2){
            if(a2 > a3) return 1;
            else return 0;
        }else{
            if(a1 > a3) return 1;
            else return -1;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){
        if(seam == null)
            throw new IllegalArgumentException("null array!");
        if(height() <= 1)
            throw new IllegalArgumentException("called removeHorizontal with height less than or equal to 1");
        if(seam.length != width())
            throw new IllegalArgumentException("wrong array length!");
        for(int i = 0; i < width(); i++) {
            if (seam[i] < 0 || seam[i] >= height())
                throw new IllegalArgumentException();
        }
        for(int i = 1; i < width(); i++){
            if(Math.abs(seam[i] - seam[i-1]) > 1)
                throw new IllegalArgumentException();
        }

        Picture newP = new Picture(width(), height()-1);
        for(int w = 0; w < newP.width(); w++){
            int count = 0;
            for(int h = 0; h < curPic.height(); h++){
                if(h == seam[w])
                    continue;
                newP.set(w, count, curPic.get(w, h));
                count++;
            }
        }

        curPic = newP;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        if(seam == null)
            throw new IllegalArgumentException("null array!");
        if(width() <= 1)
            throw new IllegalArgumentException("called removeVertical with width less than or equal to 1");
        if(seam.length != height())
            throw new IllegalArgumentException("wrong array length!");
        for(int i = 0; i < height(); i++) {
            if (seam[i] < 0 || seam[i] >= width())
                throw new IllegalArgumentException();
        }
        for(int i = 1; i < height(); i++){
            if(Math.abs(seam[i] - seam[i-1]) > 1)
                throw new IllegalArgumentException();
        }

        Picture newP = new Picture(width()-1, height());
        for(int h = 0; h < newP.height(); h++){
            int count = 0;
            for(int w = 0; w < curPic.width(); w++){
                if(w == seam[h])
                    continue;
                newP.set(count, h, curPic.get(w, h));
                count++;
            }
        }

        curPic = newP;
    }

    //  unit testing (optional)
    public static void main(String[] args){
        Picture newP = new Picture("test.png");

        SeamCarver sc = new SeamCarver(newP);
        StdOut.printf("width = %d, height = %d\n", sc.width(), sc.height());
        for(int i = 0; i < 50; i++)
            sc.removeVerticalSeam(sc.findVerticalSeam());
        for(int i = 0; i < 50; i++)
            sc.removeHorizontalSeam(sc.findHorizontalSeam());
        StdOut.printf("width = %d, height = %d\n", sc.width(), sc.height());
        sc.picture().show();
    }
}
