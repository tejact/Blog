
import static spark.Spark.*;




public class Main {
    public static void main(String[] args) {
        System.out.println("Entered Main Method");
        get("/hello", (req, res) -> "Hello World");
    }
}
