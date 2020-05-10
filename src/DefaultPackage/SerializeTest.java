package DefaultPackage;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import Files.Payload;
import POJO.AddPlace;
import POJO.Location;

public class SerializeTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setLaunguage("French-IN");
		ap.setName("Amdocs Taylor");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setWebsite("(+91) 983 893 3937");

		List<String> ls = new ArrayList<String>();
		ls.add("shoe park");
		ls.add("shop");
		ap.setTypes(ls);

		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ap.setLocation(l);

		String response = given().body(ap).queryParam("key", "qaclick123").log().all().when()
				.post("/maps/api/place/add/json").then().extract().response().toString();

		System.out.println(response);
	}

}
