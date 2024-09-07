
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Arrays; // Import the Scanner class to read text files
import java.util.Scanner;

/**
 * Write a description of class Objtools here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Objtools {
    // instance variables - replace the example below with your own
    /**
     * Constructor for objects of class Objtools
     */
    final private String file;
    // teapot.txt
    public int[][] rface;
    public double[][] rvert;
    public String matfile;
    public int[][] rmat;
    public int[][] mattemp;
    
    public float x;
    public float y;
    public float z;
    
    public int sx;
    public int sy;
    public int sz;
    public Mathing math;

    public void readFilenotex() {
        int[][] face = {};
        double[][] vert = {};
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.charAt(0) == 'v') {
                    double[] ve = vert(data,sx,sy,sz);
                    vert = Arrays.copyOf(vert, vert.length + 1);
                    vert[vert.length - 1] = ve;
                } else if (data.charAt(0) == 'f') {
                    int[] a = face(data);
                    face = Arrays.copyOf(face, face.length + 1);
                    face[face.length - 1] = a;
                } else {
                    System.out.println("nothing");
                }
            }
            System.out.println("done");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        rface = face;
        rvert = vert;
    }

    
    
    public void readFiletex() {
        int[][] face = {};
        double[][] vert = {};
        int[][] matmortemp = {};
        int curmat=-1;
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.charAt(0) == 'v') {
                    double[] ve = vert(data,sx,sy,sz);
                    vert = Arrays.copyOf(vert, vert.length + 1);
                    vert[vert.length - 1] = ve;
                } else if (data.charAt(0) == 'f') {
                    int[] a = face(data);
                    face = Arrays.copyOf(face, face.length + 1);
                    face[face.length - 1] = a;

                    
                    int[] help = mattemp[curmat];
                    matmortemp= Arrays.copyOf(matmortemp, matmortemp.length + 1);
                    matmortemp[matmortemp.length - 1] = help;
                    
                } else if (data.charAt(0) == '#'){
                    System.out.println("coment");
                }else if (data.charAt(0) == 'o'){
                    System.out.println("o");
                }else if (data.charAt(0) == 's'){
                    System.out.println("s");
                }else if (data.charAt(0) == 'm'){
                    String[] parts = data.split(" ");
                    matfile = parts[1];
                    readmtl(matfile);
                }else if (data.charAt(0) == 'u'){
                    curmat = curmat+1;
                
                }else {
                    //uhoh
                }
            }
            System.out.println("done");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        rface = face;
        rvert = vert;
        rmat = matmortemp;
    }
    
    
    public void readmtl(String mtl){
        String newmat;
        int[][] materialprop = {};
        try {
            File myObj = new File(mtl);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data==null){
                    //skip line
                }else if (data.charAt(0) == 'n') {
                    String[] parts = data.split(" ");
                    newmat = parts[1];
                } else if (data.charAt(1) == 'd') {
                    int[] rgbdat = mat(data,1,1,1);
                    materialprop = Arrays.copyOf(materialprop, materialprop.length + 1);
                    materialprop[materialprop.length - 1] = rgbdat;
                }else {
                    //System.out.println("nothing");
                }
            }
            System.out.println("done");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        mattemp=materialprop;
    }
    
    
    
    
    
    
    
    
    
    // Integer.parseInt(parts[1])
    // Double.parseDouble(parts[1])

    public int[] face(String face) {
        String[] parts = face.split(" ");
        int[] a = { Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]) };
        return a;
    }
    
    public double[] vert(String vert,int c1, int c2, int c3) {
        String[] parts = vert.split(" ");
        double why = Double.parseDouble(parts[1])*c1;
        double why2 = Double.parseDouble(parts[2])*c2;
        double why3 = Double.parseDouble(parts[3])*c3;
        double[] a = {why,why2,why3};
        return a;
    }

    public int[] mat(String vert,int c1, int c2, int c3) {
        String[] parts = vert.split(" ");
        double why = Double.parseDouble(parts[1])*c1;
        double why2 = Double.parseDouble(parts[2])*c2;
        double why3 = Double.parseDouble(parts[3])*c3;
        double a2=why*255;
        double b=why2*255;
        double c=why3*255;
        int a1 = (int)a2;
        int b1 = (int)b;
        int c5 = (int)c;
        int[] a = {a1,b1,c5};
        return a;
    }

    public Objtools(String a,int x,int y,int z, int sx1, int sy1, int sz1) {
        // initialise instance variables
        this.x = x;
        this.y = y;
        this.z = z;
        this.sx = sx1;
        this.sy = sy1;
        this.sz = sz1;
        this.file=a;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param y a sample parameter for a method
     * @return the sum of x and y
     */
}
