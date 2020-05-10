package DefaultPackage;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import POJO.API;
import POJO.Courses;
import POJO.GetCourse;
import POJO.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {

		String courseTitle[] = { "Selenium WebDriver Java", "Cypress", "Protractor", "API rest Assured" };

		// Part 1-->
		// Authorization Code ( Part 1 + Part 2 + Part 3)
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\2905p\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.facebook.com");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("", Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type ='password']")).sendKeys("", Keys.ENTER);
		Thread.sleep(2000);

		String url = driver.getCurrentUrl();
		String partialUrl = url.split("code=")[1];
		String codeUrl = partialUrl.split("&scope")[0];

		// Part 2--->
		// Client Credentials ( Part 2 + Part 3)
		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", "codeUrl")
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("client_secret", "").queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.get("access_token").toString();

		// (Part 3)---->
		GetCourse gc = given().queryParam("access_token", "accessToken").expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());

		String title = gc.getCourses().getApi().get(1).getCoursetitle();

		List<API> apiCourses = gc.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCoursetitle().equalsIgnoreCase("SoapUI Webservices Testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		//Get the name of the courses under WebAutomation 
		ArrayList<String> a = new ArrayList<String>();

		List<WebAutomation> w = gc.getCourses().getWebautomation();
		for (int k = 0; k < w.size(); k++) {

			w.get(k).getCoursetitle();
		}
		List<String> expectedList = Arrays.asList(courseTitle);
		Assert.assertTrue(a.equals(expectedList));
	}
}

// Serialization--> ConVerting your Java Object to Json . So
//create one Java Object and you set all the Values with Setter Method and
//you give back to your rest Assured Code so that Rest Assured will converted java Object to Json Body and submit to API.


// DeSerialization---> Deserialization is used after you get response from your Json and you Deserialize to get Values(// Json into Java Object)
//Use getter Method to retrive all the values from the Response 



