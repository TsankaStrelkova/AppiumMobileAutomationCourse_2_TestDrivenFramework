package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MenuPage extends BasePage {
    @AndroidFindBy( accessibility = "View menu")
    @iOSXCUITFindBy(accessibility = "More-tab-item")
    private WebElement menu;


    public MenuPage() throws Exception {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    public DetailedMenuPage openMenu() throws Exception {
        click(menu);
        Thread.sleep(1000);
        return new DetailedMenuPage();
    }
}
