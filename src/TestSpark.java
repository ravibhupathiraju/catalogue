

import static spark.Spark.get;
import static spark.Spark.port;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.FileOutputStream;
import java.util.ArrayList;

import spark.Request;
import spark.Response;
import spark.Route;

public class TestSpark {

    public static void main(String[] args) {
        port(3000);

        get("/hello", (req, res) -> {
            System.out.println("request made");
            return "hi world1";
        });
        
        get("/bye", new MyRoute());

    }

}

class MyRoute implements Route {

	@Override
	public Object handle(Request req, Response res) throws Exception {
		System.out.println("request made");
		return "bye world";
	}
	
}