package ua.edu.ucu.apps;

public class Main {
    public static void main(String[] args) {
        Document document = new SmartDocument();
        document = new TimedDocument(new CachedDocument(document));
        document.parse("~/Downloads/Famine-death-rate-since-1860s-revised.png");
    }
}
