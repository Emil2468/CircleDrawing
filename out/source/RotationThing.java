import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class RotationThing extends PApplet {

Circle[] circles = new Circle[6];
public void setup() {
    
    background(0);
    stroke(255);
    noFill();
    translate(width/2, height / 2);

    //Load pixels so we have something to update in the first frame
    loadPixels();
    circles[0] = new Circle(null, 100, 0, 0);
    circles[1] = new Circle(circles[0], 30, 0, PI/256);
    circles[2] = new Circle(circles[1], 30, 0, -PI/256);
    circles[3] = new Circle(circles[2], 30, 0, -PI/512);
    circles[4] = new Circle(circles[3], 30, 0, PI/1024);
    circles[5] = new Circle(circles[4], 30, 0, PI/128);
}

public void draw() {
    println("frameRate: "+frameRate);
    translate(width/2, height / 2);

    //Update pixels to remove old circles, but keep points
    updatePixels();

    //Update circle position now so drawing point is correct
    for(Circle c : circles) {
        c.Update();
    }
    
    //Draw one point from the outer circle
    circles[circles.length - 1].DrawPoint(color(255,0,0));

    //Load pixels to load newly drawn line
    loadPixels();
    //Draw all circles
    for(Circle c : circles) {
        c.Draw();
    }
}
public class Circle {

    public PVector pos = new PVector();
    public float diameter;

    private float angle;
    private Circle parrent;
    private float inc;
    //Keep prev drawing point so we can draw a line from the previous to the current
    private PVector prevDrawingPoint = new PVector();
    private PVector currDrawingPoint = new PVector();

    //This is set to true after first update, so we dont draw a line from un-initialized PVector
    private boolean drawPoint = false;

    //parrent: The circle this circle should rotate around
    //inc: how much to increment angle by pr. frame
    public Circle (Circle parrent, float diameter, float angle, float inc) {
        this.angle = angle;
        this.diameter = diameter;
        this.parrent = parrent;
        this.inc = inc;
        pos = new PVector(0,0);
    }

    public void Update() {
        angle += inc;

        //compute position based on parrent's position if it has one
        //else it shuold stay in the middle
        if(this.parrent != null) {
            pos.x = ((diameter + this.parrent.diameter)/2) * cos(angle) + this.parrent.pos.x;
            pos.y = ((diameter + this.parrent.diameter)/2) * sin(angle) + this.parrent.pos.y;
        }
    }

    public void Draw() {
        strokeWeight(1);
        stroke(255);
        ellipse(pos.x, pos.y, diameter, diameter);
    }

    public void DrawPoint(int c) {
        prevDrawingPoint = currDrawingPoint.copy();
        currDrawingPoint.x = this.pos.x + this.diameter/2 * cos(angle * 2);
        currDrawingPoint.y = this.pos.y + this.diameter/2 * sin(angle * 2);
        strokeWeight(1);
        stroke(c);
        if(drawPoint) {
            line(prevDrawingPoint.x, prevDrawingPoint.y, currDrawingPoint.x, currDrawingPoint.y);
        }
        drawPoint = true;
    }

}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "RotationThing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
