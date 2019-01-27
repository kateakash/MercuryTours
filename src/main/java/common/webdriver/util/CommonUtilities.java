package common.webdriver.util;

public class CommonUtilities {
	public static void waitTime(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ie) {

		}
	}
}
