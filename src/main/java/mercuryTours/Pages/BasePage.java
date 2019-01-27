package mercuryTours.Pages;

import java.util.Properties;

import common.webdriver.driver.TestDriver;

public class BasePage extends TestDriver {
	public static Properties prop = new Properties();

	public BasePage() {
		getDriver(prop.get("browser").toString(), prop.get("runtype").toString(), prop.get("version").toString());
	}

}
