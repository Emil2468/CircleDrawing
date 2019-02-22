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

    public void DrawPoint(color c) {
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
