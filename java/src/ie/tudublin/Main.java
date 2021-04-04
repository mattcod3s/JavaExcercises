package ie.tudublin;

public class Main {

    public void vizualizer()
    {
        String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Vizualizer());
    }
    
        public static void main(String[] args)
    {
        Main main = new Main();
        main.vizualizer();
    }
} 