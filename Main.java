import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import javax.swing.JComponent;
import javax.swing.JFrame;



public class Main extends JComponent{

    //Sets up button press stuff
    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;
    private static volatile boolean spPressed = false;
    private static volatile boolean cPressed = false;
    private static volatile boolean tabPressed = false;
    //Same with this
    public static boolean isWPressed() {
        synchronized (Main.class) {
            return wPressed;
        }
    }
    public static boolean isAPressed() {
        synchronized (Main.class) {
            return aPressed;
        }
    }
    public static boolean isSPressed() {
        synchronized (Main.class) {
            return sPressed;
        }
    }
    public static boolean isDPressed() {
        synchronized (Main.class) {
            return dPressed;
        }
    }
    public static boolean isSpPressed() {
        synchronized (Main.class) {
            return spPressed;
        }
    }
    public static boolean iscPressed() {
        synchronized (Main.class) {
            return cPressed;
        }
    }
    public static boolean isTabPressed() {
        synchronized (Main.class) {
            return tabPressed;
        }
    }


    //repaint method
    public void repa(){
        repaint();
    }

    //Creating Bufferd Images, Img is always desplayed, Img2 is renderd in the backround than it is copied to Img 1
    public BufferedImage img;
    public BufferedImage img2;


    //Paint command
    @Override
    protected void paintComponent(Graphics g) {
        //set up Idk really
        super.paintComponent(g);
        //Displays Img onto screen,pos 0,0
        g.drawImage(img, 0, 0, this);

    }



    public static void main(String[] args) {

        //Creates new screen
        JFrame testFrame = new JFrame();
        //Dispose on Close
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Makes comp Main object
        final Main comp = new Main();
        //Sets Screem Size
        comp.setPreferredSize(new Dimension(600, 600));
        //No Clue
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);

        //Starts Loading Time
        long load1= System.nanoTime();


        //Creates New Object Objtools(FilePath,intiial x,initial y,initial z, initial x scale,initial y scale,initial z scale,)

        Objtools obj = new Objtools("teptex.txt",0,0,0,1,1,1);
        Objtools obj2 = new Objtools("testing.txt",0,-2,9,10,10,1);
        Objtools obj3 = new Objtools("testing.txt",0,3,4,1,1,1);

        //Reads the texture file and sets up colors, currently you have to do this
        obj.readFiletex();
        obj2.readFiletex();
        obj3.readFiletex();

        //Sets up a list with all objects to be renderd in the sceen.
        Objtools[] objlist={obj,obj2,obj3};

        //Finishes loading time and prints time
        long load2 = System.nanoTime();
        long loadtime = load2-load1;
        double lseconds = (double)loadtime / 1_000_000_000.0;
        System.out.println("loaded in" + lseconds);



        long testtime1= System.nanoTime();
        long testtime2 = System.nanoTime();
        long  testtime3 = testtime2-testtime1;

        


        //Sets up mathing object with All the uesfull math functions, is initialised as (Fov,Near Plane, Far plane)
        Mathing math = new Mathing(45,1,30);


        //This just does Keyboard managment stuff
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
        @Override
              public boolean dispatchKeyEvent(KeyEvent ke) {
              synchronized (Main.class) {
                  switch (ke.getID()) {
                  case KeyEvent.KEY_PRESSED:
                      if (ke.getKeyCode() == KeyEvent.VK_W) {
                          wPressed = true;
                      }
                          if (ke.getKeyCode() == KeyEvent.VK_A) {
                                aPressed = true;
                            }
                          if (ke.getKeyCode() == KeyEvent.VK_S) {
                                sPressed = true;
                            }
                          if (ke.getKeyCode() == KeyEvent.VK_D) {
                                dPressed = true;
                            }
                          if (ke.getKeyCode() == KeyEvent.VK_SPACE){
                              spPressed = true;
                          }
                          if (ke.getKeyCode() == KeyEvent.VK_C){
                                cPressed = true;
                            }
                            if (ke.getKeyCode() == KeyEvent.VK_TAB){
                                tabPressed = true;
                            }
                      break;


                  case KeyEvent.KEY_RELEASED:
                      if (ke.getKeyCode() == KeyEvent.VK_W) {
                          wPressed = false;
                      }
                          if (ke.getKeyCode() == KeyEvent.VK_A) {
                              aPressed = false;
                          }
                          if (ke.getKeyCode() == KeyEvent.VK_S) {
                              sPressed = false;
                          }
                          if (ke.getKeyCode() == KeyEvent.VK_D) {
                              dPressed = false;
                          }
                          if (ke.getKeyCode() == KeyEvent.VK_SPACE){
                                spPressed = false;
                            }
                            if (ke.getKeyCode() == KeyEvent.VK_C){
                                  cPressed = false;
                              }
                              if (ke.getKeyCode() == KeyEvent.VK_TAB){
                                tabPressed = false;
                            }
                      break;
                  }
                  return false;
              }
          }
        });

        //Sets up camera position x4,y4,and z4. frame and frameav are for measuring FPS
        float x4 = 0;
        float y4 = 0;
        float z4 = 0;
        int frame = 0;
        double frameav=0;

        //Sets up size of screen
        int w = 600;
        int h = 600;

        //Sets up Bufferd images and starts screen
        comp.img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        comp.img2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        comp.repaint();
        testFrame.pack();
        testFrame.setVisible(true);

        //Rotation
        int rot = 0;

        //These are light views currently only change Flat shading, not Shadows yet
        //topdown
        //float[] lightv={0,1,0};

        //side angle
        //float[] lightv={.5f,.5f,-.5f};

        //strait on
        float[] lighta={0,0,-1,0};


        //float reto = (float)Math.PI/2;
        float reto = 0;
        //Sets scale of shadow map
        int shadowscale = 10;

        boolean doshadows =true;



        //While true to make it a "video"
        while (true){
            //Starts rendering time
            long a = System.nanoTime();

            float[] lightv= math.rotate(lighta,-reto,0);
            //Creates new buffers for Light and Normal rendering Passes
            float[][] buffer = new float[w][h];
            float[][] bufferlight = new float[w][h];


            //clears image
            comp.img2=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            //loops through every pixel
            for ( int rc = 0; rc < h; rc++ ) {
                for ( int cc = 0; cc < w; cc++ ) {
                    //set depth in buffers for this pixel
                    buffer[cc][rc]=Float.POSITIVE_INFINITY;
                    bufferlight[cc][rc]=Float.POSITIVE_INFINITY;
                }
            }

            
            //gets first object in the object list
            Objtools lobject= objlist[0];
            //gets the first face of said obkect
            int[] lface = lobject.rface[0];
            //sets translations from the initial values in Obj Object
            float lxn=lobject.x;
            float lyn=lobject.y;
            float lzn=lobject.z;
            //Creates point using The numbers in the Face and the verticeis in Rvert from the OBJ object
            float[] lpoint = {(float)lobject.rvert[lface[0]-1][0]+lxn,(float)lobject.rvert[lface[0]-1][1]+lyn,(float)lobject.rvert[lface[0]-1][2]+lzn};


            //Solves distance diffrence, still not entirely sure why this works
            float lwhy = math.distance1(lpoint,20,x4,y4,z4,0)-math.distance1(lpoint,20,0,0,-1.8f,0);


            float[] lightloc = {x4,y4,-z4,0};

            float[][] lightv2 = {{lightv[0],0,0,0},{0,lightv[1],0,0},{0,0,lightv[2],0},{0,0,0,0}};

            float[] lightss = math.matmult(lightv2,lightloc);

            //float lightss = math.dotprod(lightloc,lightv);

            //float lwhy = math.distance1(lpoint,20,x4,y4,z4,0)-math.distance1(lpoint,20,lighttrans[0],lighttrans[1]+1.8f,lighttrans[2],0);

            //going to need to calculate thing along the axis if you know what i mean
            //float lwhy = math.distance1(lpoint,20,x4,y4,z4,0)-math.distance1(lpoint,20,lightss[0],lightss[1],lightss[2],0);

            if (!Main.isTabPressed()) {
                reto = 0;
            }else{
                reto = (float)Math.PI/2;
            }

            if(doshadows){
                //start of the first pass for Lighting, loops through every object in the Obj list
                for (Objtools object : objlist) {

                //Loops through every face in the object
                    for (int i=0;i<object.rface.length;i++){
                        //gets the face we are currently on
                        int [] face = object.rface[i];
                        //sets translations from the initial values in Obj Object
                        float xn=object.x;
                        float yn=object.y;
                        float zn=object.z;
                        //Creates points using The numbers in the Face and the verticeis in Rvert from the OBJ object adding inital translation values
                        float[] g = {(float)object.rvert[face[0]-1][0]+xn,(float)object.rvert[face[0]-1][1]+yn,(float)object.rvert[face[0]-1][2]+zn};
                        float[] hh = {(float)object.rvert[face[1]-1][0]+xn,(float)object.rvert[face[1]-1][1]+yn,(float)object.rvert[face[1]-1][2]+zn};
                        float[] f = {(float)object.rvert[face[2]-1][0]+xn,(float)object.rvert[face[2]-1][1]+yn,(float)object.rvert[face[2]-1][2]+zn};

                        //creates a polygon with these 3 triangles
                        float[][] poly={g,hh,f};
                        //float[][] polyr = math.rotateobj(poly,rot,0);

                        //Solves location and distance of polygon
                        int[][] solved = math.solvePolyLight(poly,shadowscale,reto,0);

                        float tridist = lwhy + math.distance(poly,20,0,0,0,0);

                        //do if the point is able to be renderd
                        if(math.dorender==1){
                            //Calculates Bounding box
                            int hi[][] = math.bb(solved,0,1,1);
                            int minx=hi[0][0];                
                            int miny=hi[1][0];
                            int maxx=hi[0][1];
                            int maxy=hi[1][1];
                            minx=math.clamp(minx,0,(w-1));
                            miny=math.clamp(miny,0,(h-1));
                            maxx=math.clamp(maxx,0,(w-1));
                            maxy=math.clamp(maxy,0,(h-1));

                            //for each pixel
                            for ( int rc = miny; rc < maxy; rc++ ) {
                                for ( int cc = minx; cc < maxx; cc++ ) {
                                    //Find Sined stuff. https://jtsorlinis.github.io/rendering-tutorial/ Really helped
                                    int[] pa={solved[0][0],solved[0][1]};
                                    int[] pb={solved[1][0],solved[1][1]};
                                    int[] pc={solved[2][0],solved[2][1]};
                                    float abc = math.edge(pa,pb,pc);
                                    if(abc>0){
                                        int[] point = {cc,rc};
                                        float abp = math.edge(pa,pb,point);
                                        float bcp = math.edge(pb,pc,point);
                                        float cap = math.edge(pc,pa,point);
                                        //Calculate the wieghts
                                        float wa = bcp/abc;
                                        float wb= cap/abc;
                                        float wc = abp/abc;

                                        //weird distance function
                                        //float disty= math.pointdistance(poly, wa, wb, wc,20,0,0,0);

                                        //checks if point is in triangle
                                        if(abp>=0 && bcp>=0 && cap>=0){

                                            if(tridist < bufferlight[cc][rc]){
                                                //this happens if pixel is in triangle and its depth is less than others
                                                bufferlight[cc][rc] = tridist;
                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
            //Most stuff is the same as Lighting pass. This is the pass for actual rendering

            long stesttime3=0;
            for (Objtools object : objlist) {
                for (int i=0;i<object.rface.length;i++){

                    int [] face = object.rface[i];
                    float xn=object.x;
                    float yn=object.y;
                    float zn=object.z;

                    float[] g = {(float)object.rvert[face[0]-1][0]+xn,(float)object.rvert[face[0]-1][1]+yn,(float)object.rvert[face[0]-1][2]+zn};
                    float[] hh = {(float)object.rvert[face[1]-1][0]+xn,(float)object.rvert[face[1]-1][1]+yn,(float)object.rvert[face[1]-1][2]+zn};
                    float[] f = {(float)object.rvert[face[2]-1][0]+xn,(float)object.rvert[face[2]-1][1]+yn,(float)object.rvert[face[2]-1][2]+zn};

                    float[][] poly={g,hh,f};
                    //float[][] polyr = math.rotateobj(poly,rot,0);
                    


                    

                    int[][] solved = math.solvePoly(poly,80,x4,y4,z4,0);

                    


                    //Calculates The normal of the polygon and then finds the dot product in relation to the light direction
                    float[] norm= math.makenormal(poly);
                    float doted = Math.max(0,math.dotprod(norm,lightv));


                    //do color change based on normal
                    float red,green,blue;
                    red = object.rmat[i][0]*doted;
                    green = object.rmat[i][1]*doted;
                    blue = object.rmat[i][2]*doted;
                    Color tricolor = new Color((int)red,(int)green,(int)blue);

                    //calculates distance
                    float tridist = math.distance(poly, 20,x4,y4,z4,0);

                    if(math.dorender==1){

                        int hi[][] = math.bb(solved,0,1,1);
                        int minx=hi[0][0];
                        int miny=hi[1][0];
                        int maxx=hi[0][1];
                        int maxy=hi[1][1];
                        minx=math.clamp(minx,0,(w-1));
                        miny=math.clamp(miny,0,(h-1));
                        maxx=math.clamp(maxx,0,(w-1));
                        maxy=math.clamp(maxy,0,(h-1));
                        
                        long stesttime1= System.nanoTime();
                        for ( int rc = miny; rc < maxy; rc++ ) {
                            for ( int cc = minx; cc < maxx; cc++ ) {
                                int[] pa={solved[0][0],solved[0][1]};
                                int[] pb={solved[1][0],solved[1][1]};
                                int[] pc={solved[2][0],solved[2][1]};
                                float abc = math.edge(pa,pb,pc);

                                if(abc>0&& tridist < buffer[cc][rc]){
                                    int[] point = {cc,rc};

                                    float abp = math.edge(pa,pb,point);
                                    float bcp = math.edge(pb,pc,point);
                                    float cap = math.edge(pc,pa,point);

                                    float wa = bcp/abc;
                                    float wb= cap/abc;
                                    float wc = abp/abc;

                                    //float disty= math.pointdistance(poly, wa, wb, wc,20,x4,y4,z4);
                                    if(abp>=0 && bcp>=0 && cap>=0){
                                        buffer[cc][rc] = tridist;
                                        //finds what point the pixel is loking at and then transforms that to the lights view

                                        if(doshadows){
                                            float[] pointontri=math.pointfinder(poly,wa,wb,wc);
                                            int[] light = math.solvepointLight(pointontri,shadowscale,reto,0);
                                            /*
                                            int fhgh = math.clamp(light[0],0,599);
                                            int fhgf = math.clamp(light[1],0,599);
                                             */
                                            int Lightx =light[0];
                                            int Lighty =light[1];

                                            //if is on screen and within Light depth buffer size
                                            if((Lightx<600&&Lightx>=0)&&(Lighty<600&&Lighty>=0)&&(math.dorender ==1)){
                                                //find distance from lights view
                                                float lightdist = bufferlight[Lightx][Lighty];
                                                //If it is less it is in shadow if not it is not in shadow and should be renderd normaly
                                                if(lightdist<=tridist){
                                                    //shadow
                                                    comp.img2.setRGB(cc, rc, Color.red.getRGB() );
                                                    //comp.img2.setRGB(cc, rc, Color.black.getRGB() );
                                                }else{
                                                    //normal
                                                    comp.img2.setRGB(cc, rc, tricolor.getRGB() );
                                                }
                                            }else{
                                                //index out of bounds or dorender says dont render
                                                comp.img2.setRGB(cc, rc, Color.green.getRGB() );
                                            }
                                        }else{
                                            comp.img2.setRGB(cc, rc, tricolor.getRGB() );
                                        }
                                    }

                                }

                            }
                        }
                        long stesttime2 = System.nanoTime();
                        stesttime3 += stesttime2-stesttime1;
                    }
                }


            }

            //Add to movements if a key is pressed
            float movespeed=.4f;
            if (Main.isWPressed()) {
                z4=z4-movespeed;
            }
            if (Main.isSPressed()) {
                z4=z4+movespeed;
            }
            if (Main.isAPressed()) {
                x4=x4+movespeed;
            }
            if (Main.isDPressed()) {
                x4=x4-movespeed;
            }
            if (Main.isSpPressed()) {
                y4=y4-movespeed;
            }
            if (Main.iscPressed()) {
                y4=y4+movespeed;
            }

            //System.out.println("total solved time" + stesttime3);
            //change displayed image to the new renderd image and display
            comp.img=comp.img2;
            comp.repaint();
            testFrame.pack();
            testFrame.setVisible(true);


            //finish time calculations and calculate FPS
            long b = System.nanoTime();
            long elapsedTime = b-a;
            double seconds = (double)elapsedTime / 1_000_000_000.0; 
            //System.out.println(1/seconds);

            if(frame<60){
                frame=frame+1;
                frameav=frameav+seconds;
            }else{
                frame=0;
                System.out.println(1/(frameav/60));
                frameav=0;
            }

            //animation stuff
            //rot=rot+5;
            //obj.z=obj.z+0f;

            /*
            reto=reto-0.025f;
            if(reto<-1){
                reto=.5f;
            }
            */


            //try to sleep so that image looks good updating
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                //quit thread
                return;
            }

        }
    }
}