/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import processing.core.PApplet;
import processing.core.PFont;

/**
 *
 * @author Paul <jwarecbt@yahoo.com>
 */
public class SolarSystem extends PApplet {

    PFont label = createFont("Tahoma", 96);

    /**
     * Setup
     */
    float[] x, y, z, s;
    int[] c;
    int numOfPlanets = 1000;
    int camx, camy, camz;
    float theta = 0.0f;
    float rotX, rotY, trotX, trotY;
    float highDist = 10000;
    float lowDist = -10000;

    PFont msg;

    @Override
    public void setup() {
// changing so this compiles...
//        size(screenWidth, screenHeight - 10, P3D);
        size(1024, 768 - 10, P3D);
        x = new float[numOfPlanets];
        y = new float[numOfPlanets];
        z = new float[numOfPlanets];
        c = new int[numOfPlanets];
        s = new float[numOfPlanets];

        msg = createFont("Tahoma", 11, true);
        textFont(msg);

        float tmp;

        for (int i = 0; i < numOfPlanets; i++) {

            tmp = random(lowDist, highDist);
            x[i] = (tmp < 0) ? map(tmp, lowDist, 0, lowDist, -2000) : map(tmp, 0, highDist, 2000, highDist);

            tmp = random(lowDist, highDist);
            y[i] = (tmp < 0) ? map(tmp, lowDist, 0, lowDist, -2000) : map(tmp, 0, highDist, 2000, highDist);

            tmp = random(lowDist, highDist);
            z[i] = (tmp < 0) ? map(tmp, lowDist, 0, lowDist, -2000) : map(tmp, 0, highDist, 2000, highDist);

            c[i] = color(random(50, 200), random(50, 200), random(50, 200));
            s[i] = random(2, 10);
        }
        resetZoom();
        textFont(label, 12);
    }

    @Override
    public void draw() {

        background(0);
        lights();
        drawMessages();
        updateRot();
        drawNavi();
        drawViewport();
        doTranslations();
        drawSystem();
        drawPlanets();

        pointLight(51, 102, 126, 35, 40, 36);
    }

    void resetZoom() {

        camx = width / 2;
        camy = height / 2;
        camz = 0;
    }

    @Override
    public void keyPressed() {

        switch (key) {
            case 'z':
                camz += 15;
                break;
            case 'q':
                camz -= 15;
                break;
            case 'r':
                resetZoom();
                break;
            case 'w':
                camy -= 5;
                break;
            case 'x':
                camy += 5;
                break;
            case 'a':
                camx -= 5;
                break;
            case 'd':
                camx += 5;
                break;
        }
    }

    public void updateRot() {

        rotX += (trotX - rotX) * 0.1;
        rotY += (trotY - rotY) * 0.1;

    }

    @Override
    public void mouseDragged() {

        if (mouseButton == LEFT) {
            trotX += (pmouseY - mouseY) * .01;
            trotY += (pmouseX - mouseX) * .01;
        } else if (mouseButton == RIGHT) {
            camx = mouseX;
            camy = mouseY;
        }
    }

    public void doTranslations() {

        translate(camx, camy, camz);
        rotateX(rotX);
        rotateY(rotY);
    }

    public void drawMessages() {

        stroke(255, 255, 255, 100);
        strokeWeight(3);
        fill(255, 255, 255, 50);
        rect(-25, 50, 100, 150);
        text("Sol System...", 30, 120);
    }

    public void drawViewport() {

        pushMatrix();
        fill(200, 255, 200, 20);
        strokeWeight(5);
        stroke(50, 50, 50);
        //bottom
        //      quad(0, height, (width/2)-250, (height/2)+150, (width/2)+250, (height/2)+150, width, height);
        //left
        //      quad(0, 0, (width/2)-250, (height/2)-214, (width/2)-250, (height/2)+150, 0, height);
        //right
        translate(width / 2, height / 2);
        noFill();
        rect(-350, -250, 700, 450);
        popMatrix();
    }

    public void drawNavi() {

        noFill();
        // Base bearing view
        stroke(255, 0, 0, 50);
        strokeWeight(2);
        ellipse(width / 2, height - 100, 100, 100);
        // Rotate with mouse
        pushMatrix();
        translate(width / 2, height - 100, 0);
        rotateX(rotX);
        rotateY(rotY);
        // Verticle bearing view
        stroke(0, 0, 255, 50);
        strokeWeight(2);
        ellipse(0, 0, 100, 100);
        // Horizontal bearing view, rotated 90 degrees
        pushMatrix();
        rotateX(radians(90));
        ellipse(0, 0, 100, 100);
        popMatrix();

        stroke(255, 0, 0);
        line(0, 0, 0, 25, 0, 0);
        stroke(0, 255, 0);
        line(0, 0, 0, 0, -25, 0);
        stroke(0, 0, 255);
        line(0, 0, 0, 0, 0, 25);
        popMatrix();
        sphereDetail(30);
        fill(0, 0, 255);
    }

    public void drawSystem() {

        //      translate(0,0,-250);
        noStroke();
        // Sun.
        fill(255, 255, 0);
        sphere(50);

        // 1st planet ----------------------------
        pushMatrix();
        rotateY(theta * 5);
        translate(75, 0);
        fill(255, 100, 0);
        text("Mercury", 5, 0);
        sphere(5);
        popMatrix();
        //----------------------------------------
        // 2nd planet-----------------------------
        pushMatrix();
        rotateY(theta * 2);
        translate(175, 0);
        fill(0, 200, 0);
        sphere(12);
        popMatrix();
        //----------------------------------------
        // Earth ---------------------------------
        pushMatrix();
        rotateY(theta);
        translate(350, 0);
        fill(50, 100, 255);
        sphere(15);
        text("Earth", 25, 0);

        // Moon #1 rotates around the earth
        pushMatrix();
        rotateY(-theta * 4);
        translate(45, 0);
        fill(50, 255, 200);
        sphere(5);
        popMatrix();

        popMatrix();
        //-------------------------------------------
        // 4th planet--------------------------------
        pushMatrix();
        rotateY(theta / 1.2f);
        translate(575, 0);
        fill(255, 50, 0);
        sphere(8);
        // Moon #1 rotates around the 4th
        pushMatrix();
        rotateY(-(theta / 1.2f) * 4);
        translate(25, 0);
        fill(50, 255, 200);
        sphere(2);
        popMatrix();

        popMatrix();
        //--------------------------------------------
        // 5th planet---------------------------------
        pushMatrix();
        rotateY(theta / 1.8f);
        translate(875, 0);
        fill(250, 200, 80);
        sphere(45);
        popMatrix();
        //--------------------------------------------

        // 6th planet---------------------------------
        pushMatrix();
        rotateY(theta / 2f);
        translate(975, 0);
        fill(250, 200, 0);
        sphere(32);
        rotateX(40);
        fill(255, 255, 0, 50);
        ellipse(0, 0, 170, 170);
//        fill(0);
//        ellipse(0,0,100,100);
//        noStroke();
        popMatrix();
        //--------------------------------------------

        // 7th planet---------------------------------
        pushMatrix();
        rotateY(theta / 2.4f);
        translate(1100, 0);
        fill(50, 100, 250);
        sphere(30);
        popMatrix();
        //--------------------------------------------

        // 8th planet---------------------------------
        pushMatrix();
        rotateY(theta / 2.6f);
        translate(1200, 0);
        fill(40, 200, 255);
        sphere(30);
        popMatrix();
        //--------------------------------------------

        theta += 0.02;
    }

    public void drawPlanets() {

        noStroke();
        sphereDetail(1);
        for (int i = 0; i < numOfPlanets; i++) {
            pushMatrix();
            translate(x[i], y[i], z[i]);
            fill(c[i]);
            sphere(s[i]);
            popMatrix();
        }
        sphereDetail(30);
    }

    public static void main(String _args[]) {
        PApplet.main(new String[] {SolarSystem.class.getName()});
    }
}
