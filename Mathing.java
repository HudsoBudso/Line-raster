
/**
 * Write a description of class Mathing here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Mathing
{
    // instance variables - replace the example below with your own
    public int dorender=1;
    private float[][] persmat;


    /*fov=45
s=1/(np.tan((fov/2)*(np.pi/180)))
n=1
f=20
planes=-(10/(f-n))
perspectivemat=np.array([[s, 0, 0, 0],[0 ,s, 0, 0],[0, 0, planes, -1],[0, 0, planes*n, 0]])
     */

    /**
     * Constructor for objects of class Mathing
     */

    public Mathing(int fov,int near,int far)
    {

        // initialise instance variablea

        float s = (1/((float)Math.tan(((float)fov/2)*((float)Math.PI/180))));

        float why = (10/((float)far-near));

        this.persmat= new float[][] {{s,0,0,0},{0,s,0,0},{0,0,why,-1},{0,0,why*near,0}};
    }

    public double area(int x1, int y1, int x2, int y2,
                                        int x3, int y3)
    {
       return Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                                    x3*(y1-y2))/2.0);
    }    

    public boolean isInside(int x1, int y1, int x2,
                int y2, int x3, int y3, int x, int y)
    {   
       /* Calculate area of triangle ABC */
        double A = area(x1, y1, x2, y2, x3, y3);

       /* Calculate area of triangle PBC */ 
        double A1 = area(x, y, x2, y2, x3, y3);

       /* Calculate area of triangle PAC */ 
        double A2 = area(x1, y1, x, y, x3, y3);

       /* Calculate area of triangle PAB */  
        double A3 = area(x1, y1, x2, y2, x, y);

       /* Check if sum of A1, A2 and A3 is same as A */
        return (A == A1 + A2 + A3);
    }

    public boolean isInside2(int x1, int y1, int x2,
                int y2, int x3, int y3, int x, int y)
    {   
       // Calculate the barycentric coordinates
        // of point P with respect to triangle ABC
        double denominator = ((y2 - y3) * (x1 - x3) +
                          (x3- x2) * (y1 - y3));
        double a = ((y2 - y3) * (x - x3) +
                (x3 - x2) * (y - y3)) / denominator;
        double b = ((y3 - y1) * (x - x3) +
                (x1 - x3) * (y - y3)) / denominator;
        double c = 1 - a - b;

        // Check if all barycentric coordinates
        // are non-negative
        return a >= 0 && b >= 0 && c >= 0;
    }

     public float edge(int[] a, int[] b,int[] c)
    {   
        float hi=((b[0]-a[0])*(c[1]-a[1]))-((b[1]-a[1])*(c[0]-a[0]));
       return hi;
    }



    int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }



    public int[][] bb(int[][] triangle, int buff,int w,int h){

        int bbminx=Math.min(Math.min(triangle[0][0],triangle[1][0]),triangle[2][0]);
        int bbmaxx=Math.max(Math.max(triangle[0][0],triangle[1][0]),triangle[2][0]);
        int bbminy=Math.min(Math.min(triangle[0][1],triangle[1][1]),triangle[2][1]);
        int bbmaxy=Math.max(Math.max(triangle[0][1],triangle[1][1]),triangle[2][1]);

        int[][] help ={{bbminx-buff,bbmaxx+buff},{bbminy-buff,bbmaxy+buff}};
        return help;
    }




    public int[] solvepoint(float[] point, float scale,float x4, float y4, float z4,float r1){
        float x,y,z;
        x=point[0]+x4;
        y=point[1]-3+y4;
        z=point[2]+8+z4;
        //prob transform point here

        float[] pointmat = {x,y,z,1};
        /*
        float[][] rotx;
        float[][] roty={{1,0,0,0},{0,Math.cos(),-Math.sin(),0},{0,Math.sin(),Math.cos(),0},{0,0,0,1}};
        */


        float[] inter = matmult(persmat,pointmat);


        double ab = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        double b = Math.sqrt(Math.pow(ab,2)+Math.pow(z,2));
        z=(float)b;

        if(inter[3]<=0){
            z=0;
            x=0;
            y=0;
            dorender=0;
        }else{
            dorender=1;
            x=(inter[0]/inter[3])*scale;
            y=(inter[1]/inter[3])*-scale;
        }

        int[] a = {(int)x+300,(int)y+300,(int)z};

        return a;
    }

    public float[] matmult(float[][] one, float[] two){
        float a,b,c,d;
        a=one[0][0]*two[0]+one[0][1]*two[1]+one[0][2]*two[2]+one[0][3]*two[3];
        b=one[1][0]*two[0]+one[1][1]*two[1]+one[1][2]*two[2]+one[1][3]*two[3];
        c=one[2][0]*two[0]+one[2][1]*two[1]+one[2][2]*two[2]+one[2][3]*two[3];
        d=one[3][0]*two[0]+one[3][1]*two[1]+one[3][2]*two[2]+one[3][3]*two[3];
        float[] hi = {a,b,c,d};
        return hi;
    }



    //axis 0=x
    //axis 1=y
    //axis 2=z
    public float[][] rotateobj(float[][] poly, int rot, int axis){
        float[] a = rotatepoint(poly[0], rot, axis);   
        float[] b = rotatepoint(poly[1], rot, axis);   
        float[] c = rotatepoint(poly[2], rot, axis);   
        float[][] d = {a,b,c};
        return d;

    }
    public float[] rotatepoint (float[] poly, int rot, int axis){

        float x= poly[0];
        float y= poly[1];
        float z= poly[2];

        float[] pointmat = {x,y,z,0};
        float[][] rotx={{1,0,0,0},{0,(float)Math.cos(Math.toRadians(rot)),-(float)Math.sin(Math.toRadians(rot)),0},{0,(float)Math.sin(Math.toRadians(rot)),(float)Math.cos(Math.toRadians(rot)),0},{0,0,0,0}};

        float[] inter = matmult(rotx,pointmat);
        x=inter[0];
        y=inter[1];
        z=inter[2];
            float[] ad= {x,y,z};
            return ad;

    }



    public int[][] solvePoly(float[][] poly, float scale, float x4, float y4, float z4,float r1){
        int[] a = solvepoint(poly[0], scale,x4,y4,z4,r1);
        if(dorender==0){
            int[][] e = {{0},{0},{0}};
            return e;
        }
        int[] b = solvepoint(poly[1], scale,x4,y4,z4,r1);   
        if(dorender==0){
            int[][] e = {{0},{0},{0}};
            return e;
        }
        int[] c = solvepoint(poly[2], scale,x4,y4,z4,r1);
        if(dorender==0){
            int[][] e = {{0},{0},{0}};
            return e;
        }
        int[][] e = {a,b,c};
        return e;
    }


    public int[][] solvePolyLight(float[][] poly, float scale,float r1,float r2){
        int[] a = solvepointLight(poly[0], scale,r1,r2);
        if(dorender==0){
            int[][] e = {{0},{0},{0}};
            return e;
        }
        int[] b = solvepointLight(poly[1], scale,r1,r2);   
        if(dorender==0){
            int[][] e = {{0},{0},{0}};
            return e;
        }
        int[] c = solvepointLight(poly[2], scale,r1,r2);
        if(dorender==0){
            int[][] e = {{0},{0},{0}};
            return e;
        }
        int[][] e = {a,b,c};
        return e;
    }

    public int[] solvepointLight(float[] point, float scale,float r1,float r2){
        float x,y,z;
        x=point[0];
        y=point[1]-3;
        z=point[2]+8;
        //prob transform point here

        float[] pointmat = {x,y,z,1};
        float[][] orthomat = new float[][] {{1,0,0,0},{0,1,0,0},{0,0,0,0},{0,0,0,1}};

        //float[][] rotx;

        float[][] roty={{1,0,0,0},{0,(float)Math.cos(r1),(float)-Math.sin(r1),0},{0,(float)Math.sin(r1),(float)Math.cos(r1),0},{0,0,0,1}};

        float[] inter2 = matmult(roty,pointmat);
        float[] inter = matmult(orthomat,inter2);



        if(inter[3]<=0){
            z=0;
            x=0;
            y=0;
            dorender=1;
        }else{
            dorender=1;
            z=0;
            x=inter[0]*scale;
            y=inter[1]*-scale;
        }

        int[] a = {(int)x+300,(int)y+300,(int)z};

        return a;
    }




    public float distance(float[][] poly,float scale,float x4, float y4, float z4,float r1){
        float a = distance1(poly[0],scale,x4,y4,z4,r1);
        float b = distance1(poly[1],scale,x4,y4,z4,r1);
        float c = distance1(poly[2],scale,x4,y4,z4,r1);
        float all=(a+b+c)/3;
        return all;
    }

    public float distance1(float[] point, float scale,float x4, float y4, float z4,float r1){
        float x,y,z;
        x=point[0]+x4;
        y=point[1]-3+y4;
        z=point[2]+8+z4;
        /*
        float[] pointmat = {x,y,z,1};
        float[][] rotx;
        float[][] roty={{1,0,0,0},{0,Math.cos(),-Math.sin(),0},{0,Math.sin(),Math.cos(),0},{0,0,0,1}};
        */

        double ab = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        double b = Math.sqrt(Math.pow(ab,2)+Math.pow(z,2));
        float z2 =(float)(b*scale);
        return z2;
    }



    public float pointdistance(float[][]poly, float w1,float w2,float w3,int s,float x4,float y4, float z4){
         /*
        float x,y,z;
        
        x=((poly[0][0]+x4)*w1)+((poly[1][0]+x4)*w2)+((poly[2][0]+x4)*w3);
        y=((poly[0][1]+y4)*w1)+((poly[1][1]+y4)*w2)+((poly[2][1]+y4)*w3);
        z=((poly[0][2]+z4)*w1)+((poly[1][2]+z4)*w2)+((poly[2][2]+z4)*w3);
        float l= (float)Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
        return l*s;
        */
        
        
        float l1= (float)Math.sqrt(Math.pow(poly[0][0]+x4,2) + Math.pow(poly[0][1]+y4,2) + Math.pow(poly[0][2]+z4,2));
        float l2= (float)Math.sqrt(Math.pow(poly[1][0]+x4,2) + Math.pow(poly[1][1]+y4,2) + Math.pow(poly[1][2]+z4,2));
        float l3= (float)Math.sqrt(Math.pow(poly[2][0]+x4,2) + Math.pow(poly[2][1]+y4,2) + Math.pow(poly[2][2]+z4,2));
        float a = distance(poly,s,x4,y4,z4,0);
        l1=l1*s;
        l2=l2*s;
        l3=l3*s;
        float better = ((l1*w1)+(l2*w2)+(l3*w3));
        if(a>better){

        return a;
    }else{
        return better;
    }
    

    }

    
    public float[] pointfinder(float[][]poly, float w1,float w2,float w3){
        float x,y,z;
        x=Math.abs(((poly[0][0])*w1)+((poly[1][0])*w2)+((poly[2][0])*w3));
        y=Math.abs(((poly[0][1])*w1)+((poly[1][1])*w2)+((poly[2][1])*w3));
        z=Math.abs(((poly[0][2])*w1)+((poly[1][2])*w2)+((poly[2][2])*w3));
        float[] why = {x,y,z};
        return why;
    }

    public float[] makenormal(float[][] points){
        float[] a=points[0];
        float[] b=points[1];
        float[] c=points[2];
        float[] ab = vectorsub(b,a);
        float[] ac = vectorsub(c,a);
        float[] fin = xprod(ab,ac);
        return fin;
    }
    public float[] vectorsub(float[] sub,float [] thiss){
        float a1=sub[0]-thiss[0];
        float a2=sub[1]-thiss[1];
        float a3=sub[2]-thiss[2];
        float[] a ={a1,a2,a3};
        return a;
    }
    public float[] xprod(float[] a,float [] b){
        float cx=(a[1]*b[2])-(a[2]*b[1]);
        float cy=(a[2]*b[0])-(a[0]*b[2]);
        float cz=(a[0]*b[1])-(a[1]*b[0]);

        float l= (float)Math.sqrt(Math.pow(cx,2) + Math.pow(cy,2) + Math.pow(cz,2));

        float cxn=cx/l;
        float cyn=cy/l;
        float czn=cz/l;

        float[] help = {cxn,cyn,czn};
        return help;
    }

    public float dotprod(float[] a,float[] b){
        float ad=(a[0]*b[0])+(a[1]*b[1])+(a[2]*b[2]);
        return ad;
    }








    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    /*
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
    */
}
