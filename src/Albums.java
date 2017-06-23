
import static spark.Spark.get;
import static spark.Spark.port;

import java.util.ArrayList;
import java.util.Collections;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.port;

import spark.*;
//import com.google.gson.Gson;

public class Albums {

	public static void main(String[] args) {
		// staticFiles.location("/public")

		// Initial Album Arraylist creation
		ArrayList<Album> albums = new ArrayList<Album>();

		albums.add(new Album(1, "The Dark Side of the Moon", "Pink Floyd", "Thriller", "1973"));
		albums.add(new Album(2, "Unplugged", "Bryan Adams", "rock", "1977"));
		albums.add(new Album(3, "Guardians of the Galaxy", "Blue Swede", "Pop", "2017"));
		albums.add(new Album(4, "Abbey Road", "The Beatles", "rock", "1969"));
		String tempname = albums.get(0).name;

		port(3000);

		// Assignment#1...uses template.twig template and prints the album
		// information
		get("/hello", (req, res) -> {
			JtwigTemplate template = JtwigTemplate.classpathTemplate("template.twig");
			JtwigModel model = JtwigModel.newModel().with("albumstwig", albums);
			return template.render(model);
		});

		// Assignment 2- create Json and render to browser
		get("/albumjson", (req, res) -> {
			ArrayList<Album> albumsjson = new ArrayList<Album>();
			albumsjson.add(new Album(1, "The Dark Side of the Moon", "Pink Floyd", "Thriller", "1973"));
			albumsjson.add(new Album(2, "Unplugged", "Bryan Adams", "rock", "1977"));
			albumsjson.add(new Album(3, "Guardians of the Galaxy", "Blue Swede", "Pop", "2017"));
			albumsjson.add(new Album(4, "Abbey Road", "The Beatles", "rock", "1969"));
			Gson gson = new Gson();
			System.out.println(gson.toJson(albumsjson));
			return gson.toJson(albumsjson);
		});

		// assignment 2 continued
		// Reading Jsonformat file and creating HTML
		get("/parsejson", (req, res) -> {
			System.out.println("request made5");
			// JtwigTemplate template =
			// JtwigTemplate.classpathTemplate("readjson2.twig");
			JtwigTemplate template = JtwigTemplate.classpathTemplate("template.twig");
			JtwigModel model = JtwigModel.newModel().with("albumstwig", albums);
			return template.render(model);
		});

		// Assignment-3: add a new catalog item by getting the parameters passed
		// using query parameter
		// Renders the added album to browser after querying the params passed
		// from the browser
		// http://localhost:3000/create?title=Europe&artist=1115&year=2017&genre=pop
		get("/create", (req, res) -> {
			String title = req.queryParams("title");
			String artist = req.queryParams("artist");
			String year = req.queryParams("year");
			String genre = req.queryParams("genre");
			System.out.println(genre);
			albums.add(new Album(5, title, artist, genre, year));
			System.out.println(year);
			return albums;
			// return albums.get(0).toString();
			// return albums.get(1).toString();
			// return albums.get(2).toString();
			// return albums.get(3).toString();
			// return albums.get(4).toString();
		});

		// Assignment-4: retrieve the list of items and then use DOM methods to
		// display the data on the page.
		// Step 1: Dom renders a template "listUsingDOM.twig".
		// Step 2: Template listUsingDOM.twig calls get("/albumjson") handler to
		// read JSON and build
		// album list using DOM methods
		get("/Dom", (req, res) -> {
			JtwigTemplate template = JtwigTemplate.classpathTemplate("listUsingDOM.twig");
			return template.render(null);
		});

		// Assignment-5: Adding from HTML..steps involved
		// 1) enter // http://localhost:3000/createAlbum, this gets curent album
		// list and gives an option to add new album
		// 2) enter album details and press create to add new album
		//
		//
		ArrayList<Album> albumscur = new ArrayList<Album>();
		get("/createAlbum", (req, res) -> {
			System.out.println("here1");
			// ArrayList<Album> albumscur = new ArrayList<Album>();
			albumscur.add(new Album(1, "The Dark Side of the Moon", "Pink Floyd", "Thriller", "1973"));
			albumscur.add(new Album(2, "Unplugged", "Bryan Adams", "rock", "1977"));
			albumscur.add(new Album(3, "Guardians of the Galaxy", "Blue Swede", "Pop", "2017"));
			albumscur.add(new Album(4, "Abbey Road", "The Beatles", "rock", "1969"));
			JtwigTemplate template = JtwigTemplate.classpathTemplate("AlbumCreate.twig");
			JtwigModel model = JtwigModel.newModel().with("albums", albumscur);
			return template.render(model);
		});

		get("/newAlbumList", (req, res) -> {
			System.out.println("here1");

			JtwigTemplate template = JtwigTemplate.classpathTemplate("newAlbums.jtwig");
			JtwigModel model = JtwigModel.newModel().with("albums", albumscur);
			return template.render(model);
		});

		Spark.post("/addAlbum", (req, res) -> {
			System.out.println("like post call begins");
			String name = req.queryParams("name");
			String artist = req.queryParams("artist");
			String year = req.queryParams("year");
			String genre = req.queryParams("genre");
			albumscur.add(new Album(5, name, artist, year, genre));
			return "success";
		});

		// Additional stuff..not related to assignments
		//

		// this gets album details for the ID provided
		// called from browser using http://localhost:3000/album?id=3
		// result is album Album [name=eagles4, artist=don4, year=1975,
		// genre=classic rock4]
		get("/album", (req, res) -> {
			System.out.println("request made");
			int id = Integer.parseInt(req.queryParams("id"));
			albums.get(id);
			return "album " + albums.get(id).toString();
		});

		get("/", (req, res) -> {
			String body = "";
			for (int i = 0; i < 4; i++) {
				body = body + "<div> Album " + albums.get(i).name + "  Artist: " + albums.get(i).artist + "   Year: "
						+ albums.get(i).year + "   Genre: " + albums.get(i).genre + "</div>";
			}
			String html = "<!DOCTYPE html><html><head></head><body>" + body + "</body></html>";
			return html;
		});

		get("/bye", new MyRoute());

	}

}

class MyRoute2 implements Route {

	@Override
	public Object handle(Request req, Response res) throws Exception {
		System.out.println("request made");
		return "bye world";
	}

}

class Album {
	String name;
	String artist;
	String year;
	int id;
	String genre;

	public Album(int id, String name, String artist, String genre, String year) {
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
	}

	@Override
	public String toString() {
		return "Album [name=" + name + ", artist=" + artist + ", year=" + year + ", genre=" + genre + "]";
	}

}