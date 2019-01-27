package common.webdriver.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.webdriver.core.UIElement;
import common.webdriver.core.UILocatorType;
import common.webdriver.errorReporting.ExceptionInfo;
import cucumber.api.Scenario;
import ru.yandex.qatools.ashot.AShot;

/**
 * This have has following functionalities:
 * 
 * @author AKASH.KATE
 * 
 */
public class TestDriver extends AShot {
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(TestDriver.class.getName());

	private static String GRID_HUB;
	// TODO: OS specific machine directory, currently only Windows
	private final static String LOCAL_DIRECTORY_SOURCE = ".//src//test//resources//driver";
	private final static String OS_NAME = System.getProperty("os.name").toLowerCase();
	protected static TestDriver driver = null;
	// TODO this timeout have to be part of the initialization
	static int timeOut = 150;
	private static WebDriver webDriver = null;
	private String browserName;
	private String runType;
	private String browserVersion;
	public static String originalWindowHandle = null;

	protected TestDriver(String browser, String runType, String version) {
		LOGGER.info("Initializing the web driver... ");
		webDriver = TestDriver.createDriver(browser, runType, version);
		browserName = browser;
		this.runType = runType;
		browserVersion = version;
	}

	protected TestDriver(String url, DesiredCapabilities desiredCapabilities) {
		LOGGER.info("Initializing the web driver... ");
		webDriver = TestDriver.createDriver(url, desiredCapabilities);
	}

	protected TestDriver() {

	}

	private static String pageName = null;

	/**
	 * Set page name. <br/>
	 * Should be only used in page classes.
	 * 
	 * @param pageName
	 */
	public static void setPageName(String pageName) {
		TestDriver.pageName = pageName;
	}

	/**
	 * Return page name.
	 * 
	 * @return
	 */
	public static String getPageName() {
		return pageName;
	}

	/**
	 * Return selected browser name.
	 * 
	 * @return
	 */
	protected String getBrowserName() {
		return browserName;
	}

	/**
	 * Return run type. local or hub name.
	 * 
	 * @return
	 */
	protected String getRunType() {
		return runType;
	}

	/**
	 * Return browser version.
	 * 
	 * @return
	 */
	protected String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * This method will return an instance of the TestDriver
	 * 
	 * @param browser
	 *            browser request (firefox, chrome, ie)
	 * @param runType
	 *            run type (local, grid)
	 * @param version
	 *            version of the browser, this is only important when runType is
	 *            grid. Use whole numbers for versions.
	 * @return TestDriver
	 */
	public static TestDriver getDriver(String browser, String runType, String version) {
		if (driver == null) {
			driver = new TestDriver(browser, runType, version);
		}

		return driver;
	}

	public static TestDriver getTestDriverReference() {
		return driver;
	}

	protected static WebDriver getWebDriver() {
		return webDriver;
	}

	private static WebDriver createDriver(String url, DesiredCapabilities caps) {
		try {
			webDriver = new RemoteWebDriver(new URL(url), caps);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return webDriver;
	}

	private static WebDriver createDriver(String browser, String runType, String version) {
		DesiredCapabilities desiredCap;
		WebDriver webDriver;

		// Sets grid type to use Spoon or Regular Grid
		setGridHub(runType);

		switch (browser.toLowerCase()) {
		case "firefox":
			if (!runType.equalsIgnoreCase("local")) {
				LOGGER.info("Remote execution; sending test to the Grid");
				desiredCap = DesiredCapabilities.firefox();
				desiredCap.setCapability("acceptInsecureCerts", true);
				desiredCap.setCapability("marionette", true);
				webDriver = getRemoteWebDriver(desiredCap);
			} else {
				LOGGER.info("Local execution");
				ProfilesIni allProfiles = new ProfilesIni();
				FirefoxProfile profile = allProfiles.getProfile("default");
				webDriver = new FirefoxDriver(profile);
				// webDriver = new FirefoxDriver();
			}
			break;

		case "chrome":
			LOGGER.info("chrome selected");
			desiredCap = DesiredCapabilities.chrome();
			if (!runType.equalsIgnoreCase("local")) {
				if (runType.equalsIgnoreCase("mac")) {
					webDriver = new ChromeDriver(DesiredCapabilities.chrome());
				} else {
					desiredCap.setVersion(version);
					webDriver = getRemoteWebDriver(desiredCap);
				}
			} else {
				// Should just pick up default location for Mac, with no need
				// /users/<username>/bin
				if (OS_NAME.contains("windows")) {
					System.setProperty("webdriver.chrome.driver", LOCAL_DIRECTORY_SOURCE + "\\chromedriver.exe");
					desiredCap.setCapability("chrome.binary", LOCAL_DIRECTORY_SOURCE + "\\chromedriver.exe");
					webDriver = new ChromeDriver(DesiredCapabilities.chrome());
				} else {
					webDriver = new ChromeDriver(DesiredCapabilities.chrome());
				}

			}
			break;
		case "ie":
			desiredCap = DesiredCapabilities.internetExplorer();
			desiredCap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			if (!runType.equalsIgnoreCase("local")) {
				desiredCap.setVersion(version);
				webDriver = getRemoteWebDriver(desiredCap);
			} else {
				System.setProperty("webdriver.ie.driver", LOCAL_DIRECTORY_SOURCE + "\\IEDriverServer.exe");
				webDriver = new InternetExplorerDriver(desiredCap);
			}

			break;

		default:
			webDriver = new FirefoxDriver();
		}

		webDriver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();

		return webDriver;
	}

	private static String hostName;

	public static String getHostName() {
		return hostName;
	}

	private static RemoteWebDriver getRemoteWebDriver(DesiredCapabilities desiredCap) {
		RemoteWebDriver gridWebDriver;
		String nodeHostName;

		// get RemoteWebDriver
		try {
			if (GRID_HUB.toLowerCase().contains("http"))
				gridWebDriver = new RemoteWebDriver(new URL(GRID_HUB), desiredCap);
			else
				gridWebDriver = new RemoteWebDriver(new URL("http://" + GRID_HUB + "/wd/hub"), desiredCap);
			nodeHostName = getHostNameOfNode(gridWebDriver);
		} catch (MalformedURLException e) {
			throw new IllegalStateException("The Selenium Hub " + GRID_HUB + " might be down. Contact Automation team");
		}

		Capabilities caps = gridWebDriver.getCapabilities();
		hostName = nodeHostName;
		LOGGER.info("The Host Name of the node is " + nodeHostName);
		LOGGER.info("Browser for current execution and version are " + caps.getBrowserName() + " " + caps.getVersion());

		String actualVersion = caps.getVersion();
		int indexDot = actualVersion.indexOf(".");

		if (indexDot == -1)
			indexDot = actualVersion.length();

		String trimmedVersion = actualVersion.substring(0, indexDot).trim();

		if (!desiredCap.getVersion().equals(trimmedVersion))
			throw new IllegalStateException("Requested browser version " + desiredCap.getVersion()
					+ " does not match actual version " + trimmedVersion);

		return gridWebDriver;
	}

	private static String getHostNameOfNode(RemoteWebDriver remoteDriver) {
		String hostFound = "Unable to retrieve";

		try {
			// set-up calls to get Host Name or HUB
			HttpCommandExecutor ce = (HttpCommandExecutor) remoteDriver.getCommandExecutor();
			String hostName = ce.getAddressOfRemoteServer().getHost();
			int port = ce.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, port);
			@SuppressWarnings({ "resource", "deprecation" })
			DefaultHttpClient client = new DefaultHttpClient();

			URL sessionURL = new URL(
					"http://" + hostName + ":" + port + "/grid/api/testsession?session=" + remoteDriver.getSessionId());

			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
					sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));

			// get Host Name
			if ((myURL.getHost() != null) && (myURL.getPort() != -1))
				hostFound = myURL.getHost();
		} catch (Exception e) {
			// Issue getting hostname; ignore it and continue
		}

		return hostFound;
	}

	private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
		InputStream contents = resp.getEntity().getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy(contents, writer, "UTF8");

		return new JSONObject(writer.toString());
	}

	private static void setGridHub(String runType) {
		if (runType.equalsIgnoreCase("grid"))
			GRID_HUB = "mercuryTours:4444";
		else {
			GRID_HUB = runType;
		}
	}

	private By findBy(UIElement uiElement) {
		By locator = null;

		if (uiElement.getLocatorType() == UILocatorType.ID) {
			locator = By.id(uiElement.getLocator());
		} else if (uiElement.getLocatorType() == UILocatorType.CSS) {
			locator = By.cssSelector(uiElement.getLocator());
		} else if (uiElement.getLocatorType() == UILocatorType.Xpath) {
			locator = By.xpath(uiElement.getLocator());
		} else if (uiElement.getLocatorType() == UILocatorType.Name) {
			locator = By.name(uiElement.getLocator());
		} else if (uiElement.getLocatorType() == UILocatorType.Text) {
			locator = By.partialLinkText(uiElement.getLocator());
		} else if (uiElement.getLocatorType() == UILocatorType.Link) {
			locator = By.linkText(uiElement.getLocator());
		} else if (uiElement.getLocatorType() == UILocatorType.CLASSNAME) {
			locator = By.className(uiElement.getLocator());
		}

		return locator;
	}

	// Changes starts Form here

	// *******************************************************************************************************************
	// *******************************************************************************************************************
	// ********** Other Methods methods ******
	// *******************************************************************************************************************
	// *******************************************************************************************************************

	private final int defaultWaitForTimeout = GlobalVariables.defaultWaitForTimeout;
	private final int dafaultMinimumWaitTimeout = GlobalVariables.defaultMinElementTimeout;
	private long currentMills;

	private void setCurrentMillseconds() {
		currentMills = System.currentTimeMillis();
	}

	private void throwErrorOnDefaultTimeout(Exception e) {
		if (System.currentTimeMillis() - currentMills > (defaultWaitForTimeout * 1000)) {
			throw new ExceptionInfo(e);
		}
	}

	private void throwErrorOnDefaultTimeout(Exception e, UIElement uiElement) {
		if (System.currentTimeMillis() - currentMills > (defaultWaitForTimeout * 1000)) {
			throw new ExceptionInfo(e, uiElement);
		}
	}

	private void throwErrorOnDefaultTimeout(Exception e, String customMessage) {
		if (System.currentTimeMillis() - currentMills > (defaultWaitForTimeout * 1000)) {
			throw new ExceptionInfo(e, customMessage);
		}
	}

	private void throwErrorOnDefaultTimeout(Exception e, UIElement uiElement, String assertionType, Object actual,
			Object expected) {
		if (System.currentTimeMillis() - currentMills > (defaultWaitForTimeout * 1000)) {
			throw new ExceptionInfo(e, uiElement, assertionType, actual, expected);
		}
	}

	private void throwErrorOnMinimumTimeout(Exception e, UIElement uiElement) {
		if (System.currentTimeMillis() - currentMills > (dafaultMinimumWaitTimeout * 1000)) {
			throw new ExceptionInfo(e, uiElement);
		}
	}

	private boolean isDefaultMinimumTimeoutOver() {
		if (System.currentTimeMillis() - currentMills > (dafaultMinimumWaitTimeout * 1000)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isGivenTimeoutOver(int timeout) {
		if (System.currentTimeMillis() - currentMills > (timeout * 1000)) {
			return true;
		} else {
			return false;
		}
	}

	// *******************************************************************
	// *******************************************************************
	// ********** Action Methods ******
	// *******************************************************************
	// *******************************************************************

	/**
	 * Execute given javascript on browser
	 * 
	 * @param script
	 */
	public Object executeJavaScript(String script) {
		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		Object result = jse.executeScript(script);
		return result;
	}

	/**
	 * Return all cookies.
	 * 
	 * @return
	 */
	public Set<Cookie> getCookies() {
		return webDriver.manage().getCookies();
	}

	/**
	 * Delete all cookies.
	 */
	public void deleteAllCookies() {
		webDriver.manage().deleteAllCookies();
	}

	/**
	 * Add given cookie.
	 * 
	 * @param cookie
	 */
	public void addCookie(Cookie cookie) {
		webDriver.manage().addCookie(cookie);
	}

	/**
	 * Open given URL
	 */
	public void openURL(String url) {
		webDriver.get(url);
	}

	/**
	 * Open given URL retry mechanism for agent
	 * 
	 * @param url
	 */
	public void openURLAgent(String url) {
		setCurrentMillseconds();
		while (true) {
			try {
				webDriver.get(url);
				if (webDriver.getCurrentUrl().startsWith("data:"))
					continue;
				else
					break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, "Unknown Error: WebDriver not able to open URL!");
			}
		}
	}

	/**
	 * Close browser.<br/>
	 * Will close every associated window or tab.
	 */
	public void quit() {
		webDriver.quit();
		driver = null;
	}

	/**
	 * Close current tab or window of browser.
	 */
	public void close() {
		webDriver.close();
	}

	/**
	 * Return current page/window/tab URL.
	 * 
	 * @return
	 */
	public String getCurrentURL() {
		return webDriver.getCurrentUrl();
	}

	/**
	 * Get page source.
	 * 
	 * @return
	 */
	public String getPageSource() {
		return webDriver.getPageSource();

	}

	/**
	 * Get current window handle.
	 * 
	 * @return
	 */
	public String getCurrentWindowHandle() {
		return webDriver.getWindowHandle();
	}

	/**
	 * switch to given window handle
	 */
	public void switchTo(String windowHandle) {
		webDriver.switchTo().window(windowHandle);
	}

	/**
	 * Get main or original window handle.
	 * 
	 * @return
	 */
	public String getMainWindowHandle() {
		return originalWindowHandle;
	}

	/**
	 * Get window handles.
	 * 
	 * @return
	 */
	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	/**
	 * Get page title.
	 * 
	 * @return
	 */
	public String getPageTitle() {
		return webDriver.getTitle();
	}

	// 90-Second methods

	/**
	 * Scroll using JavaScript and webdriver click.
	 * 
	 * @param uiElement
	 */
	public void javaScriptScrollAndClick(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				JavascriptExecutor jse = (JavascriptExecutor) webDriver;
				jse.executeScript("window.scrollTo(0,2000);");
				Thread.sleep(500);
				findElement(uiElement).click();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Scroll using JavaScript and javascript click.
	 * 
	 * @param uiElement
	 */
	public void javaScriptScrollAndJavaScriptClick(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				JavascriptExecutor jse = (JavascriptExecutor) webDriver;
				jse.executeScript("window.scrollTo(0,2000);");
				Thread.sleep(500);
				javaScriptClick(uiElement);
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Click on given element using JavaScript
	 * 
	 * @param uiElement
	 */
	public void javaScriptClick(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", findElement(uiElement));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Return given element text. <br/>
	 * It will return only innerHTML for html element.
	 * 
	 * @param uiElement
	 * @return
	 */
	public String getText(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).getText();
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}

	}

	/**
	 * Get value attribute of given element.
	 * 
	 * @param uiElement
	 * @return
	 */
	public String getValueAttribute(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).getAttribute("value");
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Get value of given attribute of given element.
	 * 
	 * @param attribute
	 * @param uiElement
	 * @return
	 */
	public String getAttribute(UIElement uiElement, String attribute) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).getAttribute(attribute);
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Get CSS value of given propertyName of element.
	 * 
	 * @param propertyName
	 * @param uiElement
	 * @return
	 */
	public String getCssValue(UIElement uiElement, String propertyName) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).getCssValue(propertyName);
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Get selected option's text from dropdown/weblist.
	 * 
	 * @param uiElement
	 * @return
	 */
	public String getSelectedOption(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				Select select = new Select(findElement(uiElement));
				return select.getFirstSelectedOption().getText();
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Get all option's text from dropdown/weblist.
	 * 
	 * @param uiElement
	 * @return
	 */
	public List<String> getDropdownElements(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			List<String> allOptionsText = new ArrayList<String>();
			try {
				Select select = new Select(findElement(uiElement));
				List<WebElement> allOptions = select.getOptions();
				// getting all option's text into list
				for (WebElement option : allOptions) {
					String optionText = option.getText();
					allOptionsText.add(optionText);
				}
				return allOptionsText;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}

	}

	/**
	 * Click given element.
	 * 
	 * @param uiElement
	 * @throws Exception
	 */
	public void click(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement).click();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Type given text in element.
	 * 
	 * @param uiElement
	 * @param text
	 * @throws Exception
	 */
	public void type(UIElement uiElement, String text) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement).clear();
				findElement(uiElement).sendKeys(text);
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Type given text in element without clear existing text.
	 * 
	 * @param uiElement
	 * @param text
	 * @throws Exception
	 */
	public void typeWithoutClear(UIElement uiElement, String text) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement).sendKeys(text);
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Select given option in dropdown
	 * 
	 * @param uiElement
	 * @param text
	 * @throws Exception
	 */
	public void select(UIElement uiElement, String text) {
		setCurrentMillseconds();
		while (true) {
			try {
				Select select = new Select(findElement(uiElement));
				select.selectByVisibleText(text);
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Accept alert.
	 * 
	 * @throws Exception
	 */
	public void acceptAlert() {
		setCurrentMillseconds();
		while (true) {
			try {
				webDriver.switchTo().alert().accept();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e);
			}
		}
	}

	/**
	 * type on alert and accept it.
	 * 
	 * @throws Exception
	 */
	public void typeOnAlertAndAccept(String text) {
		setCurrentMillseconds();
		while (true) {
			try {
				Alert alert = webDriver.switchTo().alert();
				alert.sendKeys(text);
				alert.accept();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e);
			}
		}
	}

	/**
	 * Dismiss alert.
	 */
	public void dismissAlert() {
		setCurrentMillseconds();
		while (true) {
			try {
				webDriver.switchTo().alert().dismiss();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e);
			}
		}
	}

	/**
	 * Get alert text
	 */
	public String getAlertText() {
		setCurrentMillseconds();
		while (true) {
			try {
				return webDriver.switchTo().alert().getText();
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e);
			}
		}

	}

	/**
	 * Double click on given element
	 * 
	 * @param uiElement
	 */
	public void doubleClick(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				Actions action = new Actions(webDriver);
				action.moveToElement(findElement(uiElement)).doubleClick().build().perform();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Check check box as checked if checkedAs parameter value is true and unchecked
	 * otherwise
	 * 
	 * @param uiElement
	 * @param checkedAs
	 * @throws Exception
	 */
	public void checkCheckboxAs(UIElement uiElement, boolean checkedAs) {
		setCurrentMillseconds();
		while (true) {
			try {
				if (findElement(uiElement).isSelected()) {
					if (checkedAs == false) {
						findElement(uiElement).click();
					}
				} else {
					if (checkedAs == true) {
						findElement(uiElement).click();
					}
				}
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Hover on element
	 * 
	 * @param uiElement
	 * @throws Exception
	 */
	public void hoverOnElement(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				Actions e = new Actions(webDriver);
				WebElement element = webDriver.findElement(this.findBy(uiElement));
				e.moveToElement(element).build().perform();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Send TAB key on element
	 * 
	 * @param uiElement
	 */
	public void tabOutOf(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				new Actions(webDriver).moveToElement(this.findElement(uiElement)).sendKeys(Keys.TAB).build().perform();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	// 90-second methods

	public boolean checkWebList(String sProperty, String sExpectedValue, UIElement uiElement) {
		boolean result = false;
		WebElement object = webDriver.findElement(this.findBy(uiElement));
		if (!sProperty.equalsIgnoreCase("VALUE") && !sProperty.equalsIgnoreCase("SELECTION")
				&& !sProperty.equalsIgnoreCase("DEFAULTVALUE")) {
			System.out.println(sProperty + " property is not supported");
			return result;
		} else {
			Select itemList = new Select(object);
			if (!sExpectedValue.equalsIgnoreCase("null") && !sExpectedValue.isEmpty() && sExpectedValue != null) {
				WebElement actualSelected2 = itemList.getFirstSelectedOption();
				if (!actualSelected2.getText().equalsIgnoreCase(sExpectedValue)) {
					System.out.println(actualSelected2.getText() + " is selected which was not expected");
					return result;
				}

				System.out
						.println("Item value :: " + actualSelected2.getText() + " is selected by default as expected");
				result = true;
			} else {
				List<WebElement> actualSelected = itemList.getAllSelectedOptions();
				if (actualSelected.size() != 0 && !itemList.getFirstSelectedOption().getText().isEmpty()) {
					WebElement actualSelected1 = itemList.getFirstSelectedOption();
					System.out.println(actualSelected1.getText() + " is selected by default which was not expected");
					return result;
				}

				System.out.println("No Item is selected by default as expected");
				result = true;
			}

			return result;
		}
	}

	/**
	 * This method will usually be invoked by the @After hook, and will take an
	 * screenshot of the of the driver if the scenario has failed.
	 * 
	 * @param scenario
	 *            the current scenario that cucumber is running
	 */
	public void takeScreenShot(Scenario scenario) {
		final byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
		scenario.embed(screenshot, "image/png");

	}

	/**
	 * Take full screen shot
	 * 
	 * @param scenario
	 * @throws IOException
	 */
	public void takeWebScreenshot(Scenario scenario) throws IOException {
		/*
		 * final Screenshot screenshot = new AShot().shootingStrategy(new
		 * ViewportPastingStrategy(500)) .takeScreenshot(webDriver);
		 * 
		 * final BufferedImage image = screenshot.getImage(); ByteArrayOutputStream baos
		 * = new ByteArrayOutputStream(); ImageIO.write(image, "PNG", baos); //
		 * ImageIO.write(image, "PNG", new File("C:\\1Softwares\\Sprint\\" + //
		 * "AShot_BBC_Entire12.png")); byte[] imageInByte = baos.toByteArray();
		 * scenario.embed(imageInByte, "image/png"); baos.flush(); baos.close();
		 */
	}

	// *******************************************************************
	// *******************************************************************
	// ********** Information Retrieval 'Is' Methods ******
	// *******************************************************************
	// *******************************************************************
	/**
	 * return true if checkbox is checked and false if unchecked.<br/>
	 * 
	 * @param uiElement
	 * @return
	 */
	public boolean isCheckboxChecked(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).isSelected();
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Return true if element enabled.
	 * 
	 * @param uiElement
	 * @return
	 */
	public boolean isElementEnabled(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).isEnabled();
			} catch (Exception e) {
				throwErrorOnMinimumTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Return true if element selected. only applicable for radio button and
	 * checkboxes.
	 * 
	 * @param uiElement
	 * @return
	 */
	public boolean isElementSelected(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).isSelected();
			} catch (Exception e) {
				throwErrorOnMinimumTimeout(e, uiElement);
			}
		}
	}

	/**
	 * Return true if element visible and false if not present or not visible.<br/>
	 * This method will not fail and take minimum default time to exit.
	 * 
	 * @param uiElement
	 * @return
	 */
	public boolean isElementVisible(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				return findElement(uiElement).isDisplayed();
			} catch (Exception e) {
				if (isDefaultMinimumTimeoutOver()) {
					return false;
				}
			}
		}
	}

	public boolean isElementPresent(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				// Just checking whether element present or not
				findElement(uiElement).getTagName();
				return true;
			} catch (Exception e) {
				if (isDefaultMinimumTimeoutOver()) {
					return false;
				}
			}
		}
	}

	public boolean isAlertPresent() {
		boolean isAlertPresent = false;
		try {
			waitForAlertIsPresent(dafaultMinimumWaitTimeout);
			isAlertPresent = true;
		} catch (Exception e) {

		}
		return isAlertPresent;
	}

	// overloaded versions: kind of fail safe versions

	public boolean isElementVisible(UIElement uiElement, int timeout) {
		setCurrentMillseconds();
		for (int i = 0; i < 100; i++) {
			try {
				return findElement(uiElement).isDisplayed();
			} catch (Exception e) {
				if (isGivenTimeoutOver(timeout)) {
					return false;
				}
			}
		}
		return false;
	}

	public boolean isElementPresent(UIElement uiElement, int timeout) {
		setCurrentMillseconds();
		for (int i = 0; i < 100; i++) {
			try {
				// Just checking whether element present or not
				findElement(uiElement).getTagName();
				return true;
			} catch (Exception e) {
				if (isGivenTimeoutOver(timeout)) {
					return false;
				}
			}
		}
		return false;
	}

	public boolean isAlertPresent(int timeout) {
		boolean isAlertPresent = false;
		try {
			waitForAlertIsPresent(timeout);
			isAlertPresent = true;
		} catch (Exception e) {

		}
		return isAlertPresent;
	}

	// *******************************************************************
	// *******************************************************************
	// ********** Assert Event Methods ******
	// *******************************************************************
	// *******************************************************************

	/**
	 * Assert that given attribute contains given value.<br/>
	 * 
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param attribute
	 * @param value
	 */
	public void assertAttributeContains(UIElement uiElement, String attribute, String value) {
		setCurrentMillseconds();
		String actualAttributeValue = null;
		while (true) {
			try {
				actualAttributeValue = findElement(uiElement).getAttribute(attribute);
				if (actualAttributeValue.contains(value)) {
					break;
				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Attribute '" + attribute + "' contains ='" + value + "'", actualAttributeValue,
							"contains: " + value);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Attribute '" + attribute + "' contains ='" + value + "'",
						actualAttributeValue, "contains: " + value);
			}
		}
	}

	/**
	 * Assert that given attribute same is as given.<br/>
	 * 
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param attribute
	 * @param value
	 */
	public void assertAttributeToBe(UIElement uiElement, String attribute, String value) {
		setCurrentMillseconds();
		String actualAttributeValue = null;
		while (true) {
			try {
				actualAttributeValue = findElement(uiElement).getAttribute(attribute);
				if (actualAttributeValue.equals(value)) {
					break;
				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Attribute '" + attribute + "' value to be ='" + value + "'", actualAttributeValue, value);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Attribute '" + attribute + "' value to be ='" + value + "'",
						actualAttributeValue, value);

			}
		}
	}

	/**
	 * Assert that given attribute value is not empty.<br/>
	 * 
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param attribute
	 */
	public void assertAttributeToBeNotEmpty(UIElement uiElement, String attribute) {
		setCurrentMillseconds();
		String actualAttributeValue = null;
		while (true) {
			try {
				actualAttributeValue = findElement(uiElement).getAttribute(attribute);
				if (actualAttributeValue != null && !actualAttributeValue.isEmpty()) {
					break;
				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Attribute '" + attribute + "' Not be empty", null, null);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Attribute '" + attribute + "' Not be empty", null, null);
			}
		}
	}

	/**
	 * Assert that alert is present.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 */
	public void assertAlertIsPresent() {
		setCurrentMillseconds();
		while (true) {
			try {
				webDriver.switchTo().alert();
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, null, "Alert is present", null, null);
			}
		}
	}

	/**
	 * Assert that given element selection state to be selected or not based on
	 * value of @param isSelected .<br/>
	 * Example: if isSelected value is true then it will assert that given
	 * element(checkboxes, radio buttons) is checked/selected and for false it will
	 * check for unchecked/not selected.
	 * 
	 * This operation only applies to input elements such as checkboxes, options in
	 * a select and radio buttons.<br/>
	 * 
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param isSelected
	 */
	public void assertElementSelectionStateToBe(UIElement uiElement, boolean isSelected) {
		setCurrentMillseconds();
		while (true) {
			try {
				new WebDriverWait(webDriver, 1)
						.until(ExpectedConditions.elementSelectionStateToBe(findElement(uiElement), isSelected));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element selection state to be " + isSelected, isSelected,
						null);
			}
		}
	}

	/**
	 * Assert that given element is clickable.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertElementToBeClickable(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				new WebDriverWait(webDriver, 1).until(ExpectedConditions.elementToBeClickable(findElement(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element to be clickable", null, null);
			}
		}
	}

	/**
	 * Assert that element is selected.<br/>
	 * This operation only applies to input elements such as checkboxes, options in
	 * a select and radio buttons.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertElementToBeSelected(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				new WebDriverWait(webDriver, 1).until(ExpectedConditions.elementToBeSelected(findElement(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element to be selected", null, null);
			}
		}
	}

	/**
	 * Assert that invisibility/disappear of all element found by given
	 * element.<br/>
	 * 
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertInvisibilityOfAllElements(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				new WebDriverWait(webDriver, 1)
						.until(ExpectedConditions.invisibilityOfAllElements(findElements_private(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Invisibility Of All Elements", null, null);
			}
		}
	}

	/**
	 * Assert that invisibility of given element.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertInvisibilityOfElementLocated(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement);
				new WebDriverWait(webDriver, 1)
						.until(ExpectedConditions.invisibilityOfElementLocated(findBy(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Invisibility Of All Elements", null, null);
			}
		}
	}

	/**
	 * Assert that invisibility of element with text.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param text
	 */
	public void assertInvisibilityOfElementWithText(UIElement uiElement, String text) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement);
				new WebDriverWait(webDriver, 1)
						.until(ExpectedConditions.invisibilityOfElementWithText(this.findBy(uiElement), text));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Invisibility Of Element with text ='" + text + "'", null,
						null);
			}
		}
	}

	/**
	 * Assert that given java script return any value.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param javaScript
	 */
	public void assertJsReturnsValue(String javaScript) {
		setCurrentMillseconds();
		while (true) {
			try {
				new WebDriverWait(webDriver, 1).until(ExpectedConditions.jsReturnsValue(javaScript));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, null, "JS returns value", null, null);
			}
		}
	}

	/**
	 * Assert that presence of all element found by given element.<br/>
	 * presence of element refer to element is present in HTML DOM. It can be
	 * visible or not.<br/>
	 * For visibility of element use element visible methods.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertPresenceOfAllElementsLocatedBy(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement);
				new WebDriverWait(webDriver, 1)
						.until(ExpectedConditions.presenceOfAllElementsLocatedBy(findBy(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Presence Of All Elements", null, null);
			}
		}
	}

	/**
	 * Assert that presence element found by given element.<br/>
	 * presence of element refer to element is present in HTML DOM. It can be
	 * visible or not.<br/>
	 * For visibility of element use element visible methods.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertPresenceOfElementLocated(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement);
				new WebDriverWait(webDriver, 1).until(ExpectedConditions.presenceOfElementLocated(findBy(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Presence Of Element", null, null);
			}
		}
	}

	/**
	 * Assert that element is stale.<br/>
	 * Stale element means it is no longer present in browser HTML DOM.<br/>
	 * Note: timeout will be depend on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertStalenessOf(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement);
				new WebDriverWait(webDriver, 1).until(ExpectedConditions.stalenessOf(findElement(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Statleness Of Element", null, null);
			}
		}
	}

	/**
	 * Assert that given @param text is present on element.<br/>
	 * Note: timeout will be depend on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param text
	 */
	public void assertTextToBePresentInElement(UIElement uiElement, String text) {
		setCurrentMillseconds();
		String actualText = null;
		while (true) {
			try {
				actualText = findElement(uiElement).getText();
				if (actualText.equals(text)) {
					break;
				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Element text '" + text + "'to be present in element ", actualText, text);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element text '" + text + "'to be present in element ",
						actualText, text);
			}
		}
	}

	/**
	 * Assert that given @param text is present on element.<br/>
	 * Ignore case version.<br/>
	 * Note: timeout will be depend on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param text
	 */
	public void assertTextToBePresentInElementIgnoreCase(UIElement uiElement, String text) {
		setCurrentMillseconds();
		String actualText = null;
		while (true) {
			try {
				actualText = findElement(uiElement).getText();
				if (actualText.equalsIgnoreCase(text)) {
					break;
				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Element text '" + text + "'to be present in element ", actualText, text);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element text '" + text + "'to be present in element ",
						actualText, text);
			}
		}
	}

	/**
	 * Assert that given @param text is contains in element.<br/>
	 * Note: timeout will be depend on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param text
	 */
	public void assertTextToBeContainsInElement(UIElement uiElement, String text) {
		setCurrentMillseconds();
		String actualText = null;
		while (true) {
			try {
				actualText = findElement(uiElement).getText();
				if (actualText.contains(text)) {
					break;
				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Element text '" + text + "'to be contains in element ", actualText, text);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element text '" + text + "'to be contains in element ",
						actualText, text);
			}
		}
	}

	/**
	 * Assert that given @param text is contains in element.<br/>
	 * Ignore case version.<br/>
	 * Note: timeout will be depend on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param text
	 */
	public void assertTextToBeContainsInElementIgnoreCase(UIElement uiElement, String text) {
		setCurrentMillseconds();
		String actualText = null;
		while (true) {
			try {
				actualText = findElement(uiElement).getText();
				if (actualText.toLowerCase().contains(text.toLowerCase())) {
					break;
				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Element text '" + text + "'to be contains in element ", actualText, text);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element text '" + text + "'to be contains in element ",
						actualText, text);
			}
		}
	}

	/**
	 * Assert that given @param text is present is element value attribute.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 * @param text
	 */
	public void assertTextToBePresentInElementValue(UIElement uiElement, String text) {
		setCurrentMillseconds();
		String actualValue = null;
		while (true) {
			try {
				actualValue = findElement(uiElement).getAttribute("value");
				if (actualValue.equals(text)) {
					break;

				} else {
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), uiElement,
							"Element value attribute text '" + text + "'to be present in element value", actualValue,
							text);
				}
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement,
						"Element value attribute text '" + text + "'to be present in element value", actualValue, text);
			}
		}
	}

	/**
	 * Assert that title contains given text.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param title
	 */
	public void assertTitleContains(String title) {
		setCurrentMillseconds();
		while (true) {
			try {
				new WebDriverWait(webDriver, 1).until(ExpectedConditions.titleContains(title));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, null, "Title contains", null, "contains:" + title);
			}
		}
	}

	/**
	 * Assert that page title is same as given.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param title
	 */
	public void assertTitleIs(String title) {
		setCurrentMillseconds();
		String actualValue = null;
		while (true) {
			try {
				actualValue = getPageTitle();
				if (actualValue.equals(title))
					break;
				else
					throwErrorOnDefaultTimeout(new Exception("Assertion Fail"), null, "Page title", actualValue, title);
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, null, "Page title", actualValue, title);
			}
		}
	}

	/**
	 * Assert that url is same as given.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param url
	 */
	public void assertUrlToBe(String url) {
		setCurrentMillseconds();
		while (true) {
			try {
				new WebDriverWait(webDriver, 1).until(ExpectedConditions.urlToBe(url));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, null, "URL", null, url);
			}
		}
	}

	/**
	 * Assert that given element is visible.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertVisibilityOf(UIElement uiElement) {
		setCurrentMillseconds();
		while (true) {
			try {
				findElement(uiElement);
				new WebDriverWait(webDriver, 1).ignoring(NoSuchElementException.class)
						.until(ExpectedConditions.visibilityOf(findElement(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Element visibility", null, null);
			}
		}
	}

	/**
	 * Assert that all element visible found by given element.<br/>
	 * Note: Timeout will be depends on value of 'waitForElementTimeout' in
	 * ApplicationConfiguration class.
	 * 
	 * @param uiElement
	 */
	public void assertVisibilityOfAllElementsLocatedBy(UIElement uiElement) {
		while (true) {
			try {
				findElement(uiElement);
				new WebDriverWait(webDriver, 1)
						.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.findBy(uiElement)));
				break;
			} catch (Exception e) {
				throwErrorOnDefaultTimeout(e, uiElement, "Visibility of all elements", null, null);
			}
		}
	}

	// *******************************************************************
	// *******************************************************************
	// ********** Frame Methods ******
	// *******************************************************************
	// *******************************************************************

	private WebElement findElement(UIElement uiElement) {
		// long current = System.currentTimeMillis();
		System.out.println("finding " + uiElement);
		try {
			try {
				Thread.sleep(700);
			} catch (InterruptedException e1) {
			}
			WebElement element = webDriver.findElement(findBy(uiElement));
			return element;
		} catch (Exception e) {
			return webDriver.findElement(findBy(uiElement));
		}
	}

	private List<WebElement> findElements_private(UIElement uiElement) {
		findElement(uiElement);
		return webDriver.findElements(findBy(uiElement));
	}

	/**
	 * Get total occurrence count of given element
	 * 
	 * @param uiElement
	 * @return
	 */
	public int getElementCount(UIElement uiElement) {
		return findElements_private(uiElement).size();
	}

	/*
	 * Wait alert is present.<br/> Note: Timeout will be depends on value of
	 * 'waitForElementTimeout' in ApplicationConfiguration class.
	 */
	private void waitForAlertIsPresent(int timeout) {
		new WebDriverWait(webDriver, timeout).until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * waitForAjaxRequestToComplete till default global wait.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Boolean waitForAjaxRequestToComplete() throws Exception {
		return waitForAjaxRequestToComplete(GlobalVariables.defaultWaitForTimeout);
	}

	/**
	 * Overloaded version of waitForAjaxRequestToComplete() method
	 * 
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public Boolean waitForAjaxRequestToComplete(int timeout) throws Exception {
		WebDriverWait wait = new WebDriverWait(getWebDriver(), timeout);

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) getWebDriver()).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) getWebDriver()).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		ExpectedCondition<Boolean> jsPegaAsync = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) getWebDriver())
							.executeScript("return pega.u.d.activeAsyncRequests") == 0);
				} catch (Exception ex) {
					return true;
				}

			}
		};

		try {
			return wait.until(jQueryLoad) && wait.until(jsLoad) && wait.until(jsPegaAsync);
		} catch (Exception ex) {
			String msg = "\nAJAX REQUESTS NEVER COMPLETED AFTER " + timeout + "s\n";
			msg += "jQueryLoad: " + jQueryLoad.apply(getWebDriver()) + "\n";
			msg += "jsLoad: " + jsLoad.apply(getWebDriver()) + "\n";
			msg += "jsPegaAsync: " + jsPegaAsync.apply(getWebDriver()) + "\n";

			throw new RuntimeException(msg, ex);
		}
	}

	public static TestDriver getDriver(String url, DesiredCapabilities desiredCapabilities) {
		return new TestDriver(url, desiredCapabilities);
	}
}
