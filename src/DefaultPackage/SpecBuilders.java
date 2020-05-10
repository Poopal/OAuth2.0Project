package DefaultPackage;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import Files.Payload;
import POJO.AddPlace;
import POJO.Location;

public class SpecBuilders {

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
		
		
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		RequestSpecification resq = given().spec(reqSpec).body(ap);
		
		ResponseSpecification respSpec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		
		Response response = resq.when().post("/maps/api/place/add/json").then().spec(respSpec).extract().response();

		
		String resString = response.asString();
		System.out.println(resString);
	}

}
