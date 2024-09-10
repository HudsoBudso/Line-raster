import java.awt.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Arrays;

import java.awt.image.*;



public class Main extends JComponent{


    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;
    private static volatile boolean spPressed = false;
    private static volatile boolean cPressed = false;
    
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

    private static class Line{
        final int x1; 
        final int y1;
        final int x2;
        final int y2;
        
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }               
    }
    
    private static class Poly{
        final int x1; 
        final int y1;
        final int x2;
        final int y2;
        final int x3;
        final int y3;
        
        final Color color;

        public Poly(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.x3 = x3;
            this.y3 = y3;
            this.color = color;
        }               
    }
    
    private final LinkedList<Line> lines = new LinkedList<Line>();
    private final LinkedList<Poly> polys = new LinkedList<Poly>();
    
    
    public void addpoly(int x1, int x2, int x3, int x4, int x5, int x6, Color color) {
        polys.add(new Poly(x1,x2,x3,x4,x5,x6, color));        
    }
    
    public void clearpolys() {
        polys.clear();
    }
    
    
    
    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        lines.add(new Line(x1,x2,x3,x4, color));        
    }

    public void clearLines() {
        lines.clear();
    }
    public void repa(){
        repaint();
    }
    public BufferedImage img;
    public BufferedImage img2;
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(img, 0, 0, this);
        for (Line line : lines) {
            g.setColor(line.color);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
        

        
        /*
        for (Poly poly : polys){
            int[] ad={poly.x1,poly.x2,poly.x3};
            int[] bd={poly.y1,poly.y2,poly.y3};
            g.setColor(Color.black);
            g.drawPolygon(ad, bd, 3);
            g.setColor(Color.BLUE);
            g.fillPolygon(ad, bd, 3);
        }
        */
    }
    
    
    /*
    private static void createAndShowGUI() {
        JFrame jFrame = new JFrame("Hello World Swing Example");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(500, 360);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello World Swing");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        label.setBorder(border);
        label.setPreferredSize(new Dimension(150, 100));

        label.setText("Hello World Swing");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        jFrame.add(label);
        jFrame.setVisible(true);
    }
    */




    
    public static void main(String[] args) {
        //createAndShowGUI();
        //test();
    
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Main comp = new Main();
        comp.setPreferredSize(new Dimension(600, 600));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        //JPanel buttonsPanel = new JPanel();
        //JButton newLineButton = new JButton("New Line");
        //JButton clearButton = new JButton("Clear");
        //buttonsPanel.add(newLineButton);
        //buttonsPanel.add(clearButton);
        
        // x y z.   then x y z scale
        
        long load1= System.nanoTime();
        
        //Objtools obj = new Objtools("objects/building.txt",0,0,0,1,1,1);
        Objtools obj = new Objtools("objects/mouse1.txt",0,0,0,1,1,1);
        //Objtools obj = new Objtools("teptex.txt",0,0,0,1,1,1);
        //Objtools obj = new Objtools("testing.txt",0,0,0,1,1,1);
        //Objtools obj2 = new Objtools("testing.txt",10,0,0,1,1,1);
        //Objtools obj2 = new Objtools("test.txt",-45,-45,25,20,50,20);
        //Objtools obj3 = new Objtools("test.txt",-140,-45,40,20,50,20);
        
        obj.readFiletex();
        //obj2.readFile();
        //obj3.readFile();
        
        Objtools[] objlist={obj};

        long load2 = System.nanoTime();
        long loadtime = load2-load1;

        double lseconds = (double)loadtime / 1_000_000_000.0;
        System.out.println("loaded in" + lseconds);

        //testFrame.getContentPane().add(buttonsPanel,BorderLayout.SOUTH);
        
        
        


        Mathing math = new Mathing(45,1,30);
        //float easy[][][]={{{1,3,3},{4,0,3},{1,0,3}},{{1,3,3},{1,0,3},{1,0,6}}};
          float easy[][][]={{{1,0,3},{1,0,6},{1,3,6}},{{1,0,3},{1,3,3},{1,3,6}},{{1,0,3},{1,0,6},{4,0,6}},{{1,0,3},{4,0,3},{4,0,6}},{{4,3,3},{4,0,3},{4,0,6}},{{4,3,3},{4,3,6},{4,0,6}},{{4,3,3},{4,3,6},{1,3,3}},{{1,3,6},{4,3,6},{1,3,3}},{{1,3,6},{4,3,6},{4,0,6}},{{1,3,6},{1,0,6},{4,0,6}},{{1,3,3},{1,0,3},{4,0,3}},{{1,3,3},{4,3,3},{4,0,3}}};
    
          
          
          
          
          
        
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
                      break;
                  }
                  return false;
              }
          }
      });
      
      /*
    newLineButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int x1 = (int) (Math.random()*320);
            int x2 = (int) (Math.random()*320);
            int y1 = (int) (Math.random()*200);
            int y2 = (int) (Math.random()*200);
            Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            comp.addLine(x1, y1, x2, y2, randomColor);
        }
    });
    clearButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            comp.clearLines();
        }
    });
      

      int x1 = (int) (Math.random()*320);
      int x2 = (int) (Math.random()*320);
      int y1 = (int) (Math.random()*200);
      int y2 = (int) (Math.random()*200);
    */

      
      /*
    float [][] poly = easy[0];
      
      int[][] solved = math.solvePoly(poly, -30);
      
      Color randomColor = new Color(0,0,0);
      //System.out.print(Arrays.toString(solved[0]));

      
      int x1 =(int)solved[0][0]+10;
      int y1=(int)solved[0][1]+100;
      int x2=(int)solved[1][0]+10;
      int y2=(int)solved[1][1]+100;
      int x3=(int)solved[2][0]+10;
      int y3=(int)solved[2][1]+100;

      
      comp.addLine(x1, y1, x2, y2, randomColor);
      comp.addLine(x2, y2, x3, y3, randomColor);
      comp.addLine(x3, y3, x1, y1, randomColor);
      System.out.println("done");
    */
      float x4 = 0;
      float y4 = 0;
      float z4 = 0;
      int frame = 0;
      double frameav=0;
      
      
    int w = 600;
    int h = 600;
    
    comp.img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    comp.img2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    comp.repaint();
    testFrame.pack();
    testFrame.setVisible(true);
    
    
    /*
    for (each triangle in the scene) { 
    // Project vertices
    ... 
    // Compute bbox of the projected triangle
    ... 
    for (y = ymin; y <= ymax; ++y) { 
        for (x = xmin; x <= xmax; ++x) { 
            // Check if the current pixel is within the triangle
            float z;  // Distance from the camera to the triangle 
            if (pixelContainedIn2DTriangle(v0, v1, v2, x, y, &z)) { 
                // If the distance to that triangle is shorter than what's stored in the
                // z-buffer, update the z-buffer and the image at pixel location (x,y)
                // with the color of that triangle
                if (z < zbuffer(x, y)) { 
                    zbuffer(x, y) = z; 
                    image(x, y) = triangle[i].color; 
                } 
            } 
        } 
    } 
}
    
  */
 /*
 float[][] testn = {{-1,1,-1},{1,1,1},{1,1,-1}};
 float[] norm= math.makenormal(testn);
 */
 
 
        int rot = 0;
        //topdown
        //float[] lightv={0,1,0};

        //side angle
        float[] lightv={.5f,.5f,-.5f};

    while (true){

        
        long a = System.nanoTime();
        comp.clearLines();
        
        float[][] buffer = new float[w][h];
        float[][] bufferlight = new float[w][h];
         for ( int rc = 0; rc < h; rc++ ) {
          for ( int cc = 0; cc < w; cc++ ) {
            // Set the pixel colour of the image n.b. x = cc, y = rc
                comp.img2.setRGB(cc, rc, Color.WHITE.getRGB() );
              buffer[cc][rc]=1000f;
              bufferlight[cc][rc]=1000f;
                //for cols
            }
        
        }//for rows

        
    
        for (Objtools object : objlist) {
           
            /* 
            for (int[] face : object.rface) {
                float xn=object.x;
                float yn=object.y;
                float zn=object.z;
                float[] g = {(float)object.rvert[face[0]-1][0]+xn,(float)object.rvert[face[0]-1][1]+yn,(float)object.rvert[face[0]-1][2]+zn};
            
                float[] hh = {(float)object.rvert[face[1]-1][0]+xn,(float)object.rvert[face[1]-1][1]+yn,(float)object.rvert[face[1]-1][2]+zn};
            
                float[] f = {(float)object.rvert[face[2]-1][0]+xn,(float)object.rvert[face[2]-1][1]+yn,(float)object.rvert[face[2]-1][2]+zn};

                float[][] poly={g,hh,f};
                //float[][] polyr = math.rotateobj(poly,rot,0);
                int[][] solved = math.solvePolyLight(poly,80,90,0);
                float tridist = math.distance(poly, 20,0,0,0,0);

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
                    //for each pixle
                    for ( int rc = miny; rc < maxy; rc++ ) {
                        for ( int cc = minx; cc < maxx; cc++ ) {
                            int[] pa={solved[0][0],solved[0][1]};
                            int[] pb={solved[1][0],solved[1][1]};
                            int[] pc={solved[2][0],solved[2][1]};
                            int abc = math.edge(pa,pb,pc);
                            System.out.println("should draw"+cc+rc);
                            if(abc>0){
                                int[] point = {cc,rc};
                                
                                int abp = math.edge(pa,pb,point);
                                int bcp = math.edge(pb,pc,point);
                                int cap = math.edge(pc,pa,point);
                                
                                float wa = bcp/abc;
                                float wb= cap/abc;
                                float wc = abp/abc;
                                float dist = math.pointdistance(poly, wa, wb, wc);

                                if(abp>=0 && bcp>=0 && cap>=0){
                                    //if(tridist < buffer[cc][rc])
                                    if(tridist < bufferlight[cc][rc]){
                                        //pixle is in triangle and its depth is less than others
                                        System.out.println("should draw"+cc+rc);
                                        bufferlight[cc][rc] = tridist;
                                        int b1 = math.clamp((int)(.2*tridist), 0, 255);
                                        Color wow=new Color(b1,b1,b1);
                                        comp.img2.setRGB(cc, rc, wow.getRGB() );
                                    }
                                }
                            }
                        }
                    }
                    
                    
                }
            }
            */



       for (int i=0;i<object.rface.length;i++){
           
                int [] face = object.rface[i];
                float xn=object.x;
                float yn=object.y;
                float zn=object.z;
                float[] g = {(float)object.rvert[face[0]-1][0]+xn,(float)object.rvert[face[0]-1][1]+yn,(float)object.rvert[face[0]-1][2]+zn};
            
                float[] hh = {(float)object.rvert[face[1]-1][0]+xn,(float)object.rvert[face[1]-1][1]+yn,(float)object.rvert[face[1]-1][2]+zn};
            
                float[] f = {(float)object.rvert[face[2]-1][0]+xn,(float)object.rvert[face[2]-1][1]+yn,(float)object.rvert[face[2]-1][2]+zn};

                float[][] poly={g,hh,f};
                float[][] polyr = math.rotateobj(poly,rot,1);
                int[][] solved = math.solvePoly(polyr,80,x4,y4,z4,0);
                float[] norm= math.makenormal(polyr);
                
                float doted = Math.max(0,math.dotprod(norm,lightv));
                
                //do color change
                float red,green,blue;
                red = object.rmat[i][0]*doted;
                green = object.rmat[i][1]*doted;
                blue = object.rmat[i][2]*doted;
                Color tricolor = new Color((int)red,(int)green,(int)blue);
                

                float tridist = math.distance(polyr, 20,x4,y4,z4,0);
                
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
                    
                
                    
                    
                    for ( int rc = miny; rc < maxy; rc++ ) {
                      for ( int cc = minx; cc < maxx; cc++ ) {
                            int[] pa={solved[0][0],solved[0][1]};
                          int[] pb={solved[1][0],solved[1][1]};
                          int[] pc={solved[2][0],solved[2][1]};
                          int abc = math.edge(pa,pb,pc);
                          
                          if(abc>0){
                          int[] point = {cc,rc};

                          int abp = math.edge(pa,pb,point);
                          int bcp = math.edge(pb,pc,point);
                          int cap = math.edge(pc,pa,point);
                          
                          float wa = bcp/abc;
                          float wb= cap/abc;
                          float wc = abp/abc;
                          //float dist = math.pointdistance(polyr, wa, wb, wc);

                          if(abp>=0 && bcp>=0 && cap>=0){
                            //if(tridist < buffer[cc][rc])
                            if(tridist < buffer[cc][rc]){ 
                                //Color tricolor = new Color(object.rmat[i][0],object.rmat[i][1],object.rmat[i][2]);
                                //buffer[cc][rc] = tridist;
                                buffer[cc][rc] = tridist;
                                comp.img2.setRGB(cc, rc, tricolor.getRGB() );
                                /* 
                                int b1 = math.clamp((int)(.5*tridist), 0, 255);
                                Color wow=new Color(b1,b1,b1);
                                comp.img2.setRGB(cc, rc, wow.getRGB() );
                                */
                            } 
                          }
                          
                          /*
                        if(math.isInside(solved[0][0],solved[0][1],solved[1][0],solved[1][1],solved[2][0],solved[2][1],cc,rc)){
                            if (tridist < buffer[cc][rc]) { 
                                buffer[cc][rc] = tridist;
                                comp.img2.setRGB(cc, rc, tricolor.getRGB() );
                            } 
                            //comp.img2.setRGB(cc, rc, tricolor.getRGB() );
                        
                        }
                        */
                        }
                    }//for rows
                }
                    
                    /*
                    Color randomColor = new Color(255,0,0);
                //System.out.print(Arrays.toString(solved[0]));

                    int x1 =(int)solved[0][0];
                    int y1=(int)solved[0][1];
                    int x2=(int)solved[1][0];
                    int y2=(int)solved[1][1];
                    int x3=(int)solved[2][0];
                    int y3=(int)solved[2][1];
                
                
                    comp.addLine(x1, y1, x2, y2, randomColor);
                    comp.addLine(x2, y2, x3, y3, randomColor);
                    comp.addLine(x3, y3, x1, y1, randomColor);
                */
                
            }
                
                
         }
       
       
       
       

    
    }
    
    
    //Move stuff
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
        
       
        //repaint and sleep so it is visable
        
        comp.img=comp.img2;
        
        comp.repaint();
        testFrame.pack();
        testFrame.setVisible(true);
    
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
        rot=rot+5;
        //obj.z=obj.z+0f;

            
        try {
            // to sleep .2 seconds
            //Thread.sleep(200);
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            //quit thread
            return;
        }
    
    }
    }
}