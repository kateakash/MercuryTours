package mercuryTours.Steps;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import common.webdriver.errorReporting.ErrorReporter;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import mercuryTours.Pages.LoginPage;
import mercuryTours.Util.TestContext;

public class LoginSteps {

	public LoginSteps(TestContext context) {
		this.testContext = context;
		try {
			FileInputStream file = new FileInputStream(
					new File("./src/test/resources/properties/CommonConfig.properties"));
			prop.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TestContext testContext;
	LoginPage loginPage = new LoginPage();
	static Properties prop = new Properties();

	@Given("I launch website \"([^\"]*)\"$")
	public void launch_website(String website) {
		loginPage.openPage(website);
	}

	@Given("I launch website for Mercury Tours$")
	public void launch_website_for_mercury_tours() {
		loginPage.openPage(prop.getProperty("APPLICATIONURL"));
	}

	@Then("I click on SIGN ON link$")
	public void click_sign_on() {
		loginPage.clickSignOnLink();
	}

	@And("I enter username as \"([^\"]*)\" and password as \"([^\"]*)\" and click submit$")
	public void enter_username_password(String userName, String passWord) throws Exception {
		try {
			loginPage.doLogin(userName, passWord);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("I enter username and password and click on continue button$")
	public void enter_username_password_continue_with_login_withDataTable(DataTable usercredentials) throws Exception {
		try {
			List<Map<String, String>> data = usercredentials.asMaps(String.class, String.class);
			loginPage.doLogin(data.get(0).get("Username"), data.get(0).get("Password"));
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

}
