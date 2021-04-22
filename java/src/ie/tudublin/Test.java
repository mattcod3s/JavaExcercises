package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class Test extends PApplet {

    Minim minim;
    AudioPlayer ap;
    AudioBuffer ab;
    FFT fft;

    float log2(float f) {
        return log(f) / log(2.0f);
    }

    float[] lerpedBuffer;

    float[] bands;
    float[] smoothedBands;

    void calculateFrequencyBands() {
        for(int i = 0; i < bands.length; i++){
            int start  = (int) pow(2, i) - 1;
            int w = (int) pow(2, i);
            int end = start + w;
            float average = 0;
            for(int j = start; j < end; j++) {
                average += fft.getBand(j) * (j + 1);
            }
            average /= (float) w;
            bands[i] = average * 5.0f;
            smoothedBands[i] = lerp(smoothedBands[i], bands[i], 0.05f);
        }
    }

    public void settings() {
        size(512, 512);
    }

    public void setup() {
        minim = new Minim(this);
        ap = minim.loadFile("heroplanet.mp3", width);
        ap.play();
        ab = ap.mix;
        colorMode(HSB);
        lerpedBuffer = new float[width];

        fft = new FFT(width, 44100);

        bands = new float [(int) log2(width)];
        smoothedBands = new float [bands.length];
    }

    float lerpedAverage = 0;
    int highestBand =  0;

    public void draw() {
        background(0);
        stroke(255);
        float halfHeight = height / 2;
        float average = 0;
        float sum = 0;
        for (int i = 0; i < ab.size(); i++) {
            float c = map(i, 0, ab.size(), 180, 180);
            stroke(c, 255, 255);
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.1f);
            line(i, halfHeight - lerpedBuffer[i] * halfHeight * 3, i, halfHeight + lerpedBuffer[i] * halfHeight * 3);
            sum += abs(ab.get(i));

        }
        average = sum / (float) ab.size();
        lerpedAverage = lerp(lerpedAverage, average, 0.1f);

        fft.window(FFT.HAMMING);
        fft.forward(ab);

        
        for(int i = 0; i < fft.specSize(); i++) {
            float c = map(i, 0, fft.specSize(), 100, 100);
            stroke(c, 255, 255);
            line(i*2, 0, i*2, (fft.getBand(i) * 6));
            if (fft.getBand(i) >  highestBand) {
                highestBand = (int) fft.getBand(i);
            } else {
                highestBand = highestBand;
            }
        }
        float freq  = fft.indexToFreq(highestBand);
        textSize(20);
        fill(255);
        text("Frequency : "  + freq, 10, 50);

        calculateFrequencyBands();

        float w = width / (float) bands.length;
        for(int i = 0; i < bands.length; i++) {
            float x = map(i, 0, bands.length, 0, width);
            float c = map(i, 0, bands.length, 0, 255);
            noStroke();
            fill(c, 255, 255);
            rect(x, height, w, -smoothedBands[i] / 2);
        }


        ellipse(width / 2, 100 , (lerpedAverage * 500), (lerpedAverage * 500));
    }
}