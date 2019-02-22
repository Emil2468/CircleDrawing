Circle[] circles = new Circle[6];
void setup() {
    size(600, 600);
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

void draw() {
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
