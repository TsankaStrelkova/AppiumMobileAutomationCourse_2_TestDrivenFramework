package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends MenuPage {
    @AndroidFindBy(xpath = "android.widget.TextView[@text=\"Log In\"]")
    private WebElement logIn;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginTV")

    private WebElement loginTitle;


    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement userNameInputField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordET")
    private WebElement passwordInputField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameErrorTV")
    private WebElement userNameError;


    public LoginPage() throws Exception {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    public void onLoginPage() throws Exception {
        waitForVisibility(loginTitle);
    }

    public LoginPage enterUserName(String name) throws Exception {
        sendKeys(userNameInputField, name);
        return this;
    }

    public LoginPage enterPassword(String password) throws Exception {
        sendKeys(passwordInputField, password);
        return this;
    }

    public ProductsPage pressLoginBtn() throws Exception {
        click(loginButton);
        return new ProductsPage();
    }

    public boolean isErrorMessageForUserNameDisplayed() {
        return userNameError.isDisplayed();
    }
}
