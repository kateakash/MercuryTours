package mercuryTours.TestRunner;

import java.io.File;
import java.io.FileInputStream;

import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.webdriver.driver.TestDriver;
import common.webdriver.errorReporting.GenerateReport;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import mercuryTours.Pages.BasePage;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "src//test//resources//features", glue = "mercuryTours.Steps", plugin = {
		"pretty", "html:test-report/cucumber", "json:test-report/cucumber.json" }, tags = { "@Registration,@EndToEnd" })
public class CucumberTestRunner  extends AbstractTestNGCucumberTests{
	private TestNGCucumberRunner testNGCucumberRunner;
	private static String scenarioName = null;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		FileInputStream file;
		try {
			file = new FileInputStream(new File("./src/test/resources/properties/CommonConfig.properties"));
			BasePage.prop.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups="cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		scenarioName=cucumberFeature.getCucumberFeature().getPath();
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());

	}
	
	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
		TestDriver.getTestDriverReference().close();
		GenerateReport.generateCucumberReport("MercuryTours","test-report");
	}

	public static String getScenarioName() {
		return scenarioName;
	}
}