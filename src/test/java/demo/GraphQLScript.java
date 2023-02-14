package demo;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
public class GraphQLScript {
	public static void main(String[] args) {
		//Query
		RestAssured.baseURI="https://rahulshettyacademy.com";
     String queryResponse=  given().log().all().header("Content-Type","application/json")
       .body("{\"query\":\"query ($locationId: Int!)\\n{\\n  location(locationId: $locationId) {\\n    name\\n    type\\n    id\\n  }\\n}\\n\",\"variables\":{\"locationId\":1147}}")
       .when().post("/gq/graphql")
       .then().log().all().assertThat().statusCode(200).extract().response().asString();
     System.out.println("Responce is:"+queryResponse);
     JsonPath js=new JsonPath(queryResponse);
    String locationName= js.getString("data.location.name");
    System.out.println("Location Name is:"+locationName);
    assertEquals("India", locationName);	
    
    //Mutation
    String mutationResponse=  given().log().all().header("Content-Type","application/json")
      .body("{\"query\":\"\\nmutation\\n{\\n  createLocation(location:{name:\\\"AUSTRALIA\\\",type:\\\"South\\\",dimension:\\\"-125\\\"})\\n  {\\n    id\\n  }\\n  createCharacter(character:{name:\\\"Robbin\\\",type:\\\"Macho\\\",status:\\\"Dead\\\",species:\\\"Human\\\",gender:\\\"Male\\\",\\n  image:\\\"ABCD\\\",originId:1125,locationId:1125})\\n  {\\n    id\\n  }\\n  createEpisode(episode:{name:\\\"Akshay's Show\\\",air_date:\\\"1950-20-06\\\",episode:\\\"Netflix\\\"})\\n  {\\n    id\\n  }\\n}\",\"variables\":{\"locationId\":1124}}")
      .when().post("/gq/graphql").then().log().all().assertThat().statusCode(200).extract().response().asString();
	   System.out.println("Mutation response is:"+mutationResponse);
	  JsonPath js1= new JsonPath(mutationResponse);
	 int locationId=js1.getInt("data.createLocation.id");
	 System.out.println("Created loaction Id is:"+locationId);
	   
	}
}
