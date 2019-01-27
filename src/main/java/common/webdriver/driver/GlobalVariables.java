package common.webdriver.driver;

import cucumber.api.Scenario;

/**
 * Application and selenium related different different timeouts.<br/>
 * If required change this value from ApplicationConfiguration class. <br/>
 * Ex. *
 * 
 * <pre>
 * if (configuration == null) {
 * 	GlobalVariables.defaultWaitForTimeout = 90;
 * 	GlobalVariables.defaultMinElementTimeout = 10;
 * 	configuration = new ApplicationConfiguration();
 * }
 * </pre>
 * 
 * 
 * @author AKASH.KATE
 * 
 */
public class GlobalVariables {

	public static int pageLoadTimeout = 120;
	/**
	 * Should only be used in test driver and set from AutomationHooks or
	 * property file
	 */
	public static int defaultWaitForTimeout = 30;
	/**
	 * Will be used in condition checking methods <br/>
	 * 
	 * isElementPresent(UiElement) <br/>
	 * isElementVisible(UiElement) <br/>
	 * isElementEnabled(UiElement) <br/>
	 * 
	 * This should be used when: Example: When you have to click on any element
	 * 
	 * Should only be used in test driver and set from AutomationHooks or
	 * property file
	 */
	public static int defaultMinElementTimeout = 15;
	/**
	 * Need to set from AutomationHooks class or from inside of before
	 * annotated(cucumber annotation) method
	 */
	public static Scenario currentScenario = null;
}
