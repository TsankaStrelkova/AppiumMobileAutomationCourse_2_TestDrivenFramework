package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends MenuPage {

    public ProductsPage() throws Exception {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    @iOSXCUITFindBy(accessibility = "More-tab-item")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/titleTV\"]")
    @iOSXCUITFindBy(accessibility = "Sauce Labs Backpack")
    private WebElement SLBTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/priceTV\"]")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"$ 29.99\"`]")
    private WebElement SLBPrice;

    @AndroidFindBy(accessibility = "Sauce Labs Backpack")
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"BagBlack Image\"")
    private WebElement SLBImage;

    public String getTitle() throws Exception {
        return getAttribute(title, "text");
    }

    public String getProductName() throws Exception {
        return getText(SLBTitle);
    }

    public String getProductPrice() throws Exception {
        return getText(SLBPrice);
    }

    public ProductDetailsPage clickProduct() throws Exception {
        click(SLBImage);
        return new ProductDetailsPage();
    }


}
