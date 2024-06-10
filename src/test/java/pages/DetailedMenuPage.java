package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DetailedMenuPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/action_bar_root")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Webview-menu-item\"]/parent::XCUIElementTypeOther/parent::XCUIElementTypeOther")
    private WebElement actionBar;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Log In\"]")
    private WebElement logIn;

    public DetailedMenuPage() throws Exception {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    public void onDetailedMenuPage() throws Exception {
        waitForVisibility(actionBar);
    }

    public LoginPage clickLogin() throws Exception {
        click(logIn);
        return new LoginPage();
    }
}
