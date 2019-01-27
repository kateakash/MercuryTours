package common.webdriver.errorReporting;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import common.webdriver.driver.GlobalVariables;
import common.webdriver.driver.TestDriver;

/**
 * Error report class.
 * 
 * @author AKASH.KATE
 * 
 */
public class ErrorReporter {
	// main method for test purpose only
	public static void main(String[] args) throws Exception {
		reportError(new ExceptionInfo(new Exception(), "this is custom message"));
	}

	public static void reportError(Exception e, boolean showFullStackTrace) throws Exception {
		createErrorMapAndThrowException(e, showFullStackTrace);
	}

	public static void reportError(Exception e) throws Exception {
		createErrorMapAndThrowException(e, false);
	}

	private static void createErrorMapAndThrowException(Exception e, boolean showFullStackTrace) throws Exception {
		Map<String, String> errors;
		errors = new LinkedHashMap<String, String>();
		errors.put("", "");
		errors.put("Page Name", TestDriver.getPageName());

		if (e instanceof ExceptionInfo) {
			ExceptionInfo info = (ExceptionInfo) e;
			if (!info.getException().getClass().getSimpleName().toLowerCase().contains("null")) {
				errors.put("Exception Name", info.getException().getClass().getSimpleName());
			}
			if (info.getCustomMessage() != null) {
				errors.put("Custom Message", info.getCustomMessage());
			}
			if (info.getUiElement() != null) {
				errors.put("Locator", info.getUiElement().toString());
			}
			if (info.getAssertionType() != null)
				errors.put("Assertion Type", info.getAssertionType());
			if (info.getActualValue() != null)
				errors.put("Actual Value", info.getActualValue().toString());
			if (info.getExpectedValue() != null)
				errors.put("Expected Value", info.getExpectedValue().toString());
			errors.put("Timeout", GlobalVariables.defaultWaitForTimeout + "");

		}

		String configurations = "";

		String testRunType = System.getProperty("test.run.type");
		String testRunClient = System.getProperty("test.run.client");
		String testRunBrowser = System.getProperty("test.run.browser");
		String testRunBrowserVersion = System.getProperty("test.run.browser.version");
		String nodeName = TestDriver.getHostName();

		if (testRunType != null) {
			configurations += testRunType + ", ";
		}
		if (testRunClient != null) {
			configurations = testRunClient + ", ";
		}
		if (testRunBrowser != null) {
			configurations += testRunBrowser + ", ";
		}
		if (testRunBrowserVersion != null) {
			configurations += testRunBrowserVersion + ", ";
		}
		if (nodeName != null) {
			configurations += nodeName + ", ";
		}
		errors.put("Configurations", configurations);
		if (e instanceof ExceptionInfo) {
			ExceptionInfo info = (ExceptionInfo) e;
			if (!info.getException().getClass().getSimpleName().toLowerCase().contains("null")) {
				errors.put("WebDriver Exception Trace",
						"<xmp>" + getStackTraceAsString(info.getException(), showFullStackTrace) + "</xmp>");
			}
		}

		errors.put("Exception Stack Trace", getStackTraceAsString(e, showFullStackTrace));

		String formattedErrorString = generateFormattedErrorString(errors);
		throw new Exception(formattedErrorString);

	}

	/**
	 * Generated formatted string using error's map.
	 * 
	 * @param errors
	 * @return
	 */
	private static String generateFormattedErrorString(Map<String, String> errors) {
		String formattedErrors = new String();
		Set<String> keys = errors.keySet();
		for (String key : keys) {
			if (errors.get(key) != null)
				formattedErrors = formattedErrors + String.format("\n%-25s%-3s%s", key, ":", errors.get(key));
		}
		return formattedErrors.toString();
	}

	/**
	 * Convert stack trace to string and return full stack trace if
	 * showFullStackTrace is true otherwise 1300 character.
	 * 
	 * @param e
	 * @return
	 * @throws IOException
	 */
	private static String getStackTraceAsString(Exception e, boolean showFullStackTrace) throws IOException {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		sw.close();
		String trace = sw.toString();
		if (showFullStackTrace) {
			return trace;
		}
		if (trace.length() >= 1300) {
			trace = trace.substring(0, 1300);
		}
		return trace;
	}
}
