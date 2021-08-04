package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import utils.BaseClass;

public class StepDefs_GetRequest extends BaseClass{

	//Hooks
	
	@Before
	public void beforeHook(Scenario s) {
		this.scn = s;
	}

	@After
	public void afterHook(Scenario s){
		this.scn = s;
		if (_RESP==null) {
			scn.write("Response: No response received.");
		}else {
			scn.write("Response: " + _RESP.asString());
		}
	}
	
	@Given("Best Buy API is up and running")
	public void best_buy_api_is_up_and_running() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
		_REQUEST_SPEC = given().baseUri("http://localhost:3030");
		scn.write("Base URL: http://localhost:3030");
	}

	@When("I hit url with query parameter as {string}")
	public void i_hit_url_with_query_parameter_as(String string) {
		// Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
		if (string.equalsIgnoreCase("all")){
			_RESP = _REQUEST_SPEC.get("/products/");
			scn.write("End Point for all products: /products");
		} else {
			_RESP = _REQUEST_SPEC.get("/products?" +string);
			scn.write("End Point: /products?" + string);
		}
	}
	

	@Then("API returns response with status code as {int}")
	public void api_returns_the_response_with_status_code_as(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
		_RESP.then().assertThat().statusCode(int1);
		scn.write("Status code appearing as: " + int1);
	}

	@Then("all the products will be returned")
	public void all_the_products_will_be_returned() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
		_RESP.then().assertThat().body("total", equalTo(51959));
		scn.write("Scn Ended:" + scn.getName());
	}

	@Then("products prices will be returned in descending order")
	public void products_prices_will_be_returned_in_descending_order() {
	    // Write code here that turns the phrase above into concrete actions
		List<Float> list = _RESP.jsonPath().getList("data.price");
		scn.write("prices returned: " + list.toString());

		//Check list is sorted
		boolean isSortedDescending=true;
		for(int i=1;i < list.size();i++){
			if(list.get(i-1).compareTo(list.get(i)) < 0){
				isSortedDescending= false;
				break;
			}
		}

		if (isSortedDescending) {
			scn.write("Price is returned in sorted in descending order");
			org.hamcrest.MatcherAssert.assertThat(true, is(true));
		}else {
			scn.write("Price is not returned in descending order");
			org.hamcrest.MatcherAssert.assertThat(false, is(true));
		}
	}
	

	@Then("products with price less than or equal to {string} will be displayed")
	public void products_with_price_less_than_or_equal_to_will_be_displayed(String string) {
	    // Write code here that turns the phrase above into concrete actions
		List<Float> list = _RESP.jsonPath().getList("data.price");
		scn.write("prices returned: " + list.toString());
		
		 String salePrice = "$123.45";
		 Locale locale = Locale.US;
		 Number number = null;
		 try {
			 number = NumberFormat.getCurrencyInstance(locale).parse(salePrice);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		float price= number.floatValue();

		//Check list is sorted
		boolean isValid=true;
		for(int i=1;i < list.size();i++){
			if(list.get(i) > price){
				isValid = false;
				break;
			}
		}

		if (isValid) {
			scn.write("Prices of the products are less than equal to $1.00");
			org.hamcrest.MatcherAssert.assertThat(true, is(true));
		}else {
			scn.write("Prices of the products are NOT less than equal to $1.00");
			org.hamcrest.MatcherAssert.assertThat(false, is(true));
		}
	}
	
}
