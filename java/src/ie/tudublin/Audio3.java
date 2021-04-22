package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Audio3 extends PApplet {

    Minim minim; // Connect to minim
    AudioInput ai; // How to connect to mic
    AudioPlayer ap;
    AudioBuffer ab; // Samples


    public void settings() {
        size(512, 512);
    }

    public void setup() {
        minim = new Minim(this);
        ai = minim.getLineIn(Minim.MONO, width, 44100, 16);
        ap = minim.loadFile("heroplanet.mp3", width); // Connect the buffer to the mp3 file
        ap.play();
        ab = ap.mix;

    }

    public void draw() {
        background(0);
        stroke(255);
        float halfHeight = height / 2;
        for (int i = 0; i < ab.size(); i++) {
            line(1, halfHeight, i, halfHeight + ab.get(i) * halfHeight);
        }
    }
}