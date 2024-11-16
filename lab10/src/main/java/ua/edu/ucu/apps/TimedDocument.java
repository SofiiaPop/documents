package ua.edu.ucu.apps;

public class TimedDocument extends DocumentDecorator{
    public TimedDocument(Document document) {
        super(document);
    }

    
    public String parse(String path) {
        long start = System.nanoTime();

        String result =  super.parse(path);

        long end = System.nanoTime();
        long duration = end - start;
        System.out.println("Time taken in seconds: " + duration + " ns");
        return result;
    }
}
