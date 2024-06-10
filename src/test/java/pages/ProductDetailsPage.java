package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.DriverManager;

public class ProductDetailsPage extends MenuPage {
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    @iOSXCUITFindBy(accessibility = "Sauce Labs Backpack")
    private WebElement titleOfTheProduct;
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    @iOSXCUITFindBy(accessibility = "Price")
    private WebElement priceOfTheProduct;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productHeightLightsTV")
    @iOSXCUITFindBy(accessibility = "Product Highlights")
    private WebElement productHighlightsTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/descTV")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Product Highlights\"]/following-sibling::XCUIElementTypeTextView")
    private WebElement productHighlightsDetails;

    public ProductDetailsPage() throws Exception {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    public String getPriceOfTheProduct() throws Exception {
        return getText(priceOfTheProduct);
    }

    public String getTitleOfTheProduct() throws Exception {
        return getText(titleOfTheProduct);
    }

    public boolean isProductDetailsElementDisplayed() throws Exception {
        return isElementDisplayed(productHighlightsDetails);
    }

    public void scrollToProductDetailsElement() throws Exception {
        scrollToElement(DriverManager.getDriver(), true, productHighlightsDetails);
    }
}
