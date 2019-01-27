package common.webdriver.core;

/**
 * UI Element bean. It should only be used in page object classes. All instance
 * of this class must be private.
 * 
 * @author AKASH.KATE
 * 
 */
public class UIElement {
	private UIType type;
	private UILocatorType locator_type;
	private String locator;

	public UIElement(UIType uiType, UILocatorType uiLocatorType, String locator) {
		type = uiType;
		locator_type = uiLocatorType;
		this.locator = locator;
	}

	public UIType getUIType() {
		return type;
	}

	public UILocatorType getLocatorType() {
		return locator_type;
	}

	public String getLocator() {
		return locator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locator == null) ? 0 : locator.hashCode());
		result = prime * result + ((locator_type == null) ? 0 : locator_type.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UIElement other = (UIElement) obj;
		if (locator == null) {
			if (other.locator != null)
				return false;
		} else if (!locator.equals(other.locator))
			return false;
		if (locator_type != other.locator_type)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UIElement [type=" + type + ", locator_type=" + locator_type + ", locator=" + locator + "]";
	}
}
