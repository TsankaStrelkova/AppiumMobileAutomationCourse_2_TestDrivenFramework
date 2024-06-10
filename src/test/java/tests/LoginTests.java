package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.DetailedMenuPage;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginTests extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;

    DetailedMenuPage detailedMenuPage;

    // This test will fail for iOS dur to the fact that there no Log in option in the menu
    @Test
    public void successfulLogin() throws Exception {
        productsPage = new ProductsPage();
        detailedMenuPage = productsPage.openMenu();

        if (testPlatformName.equals("Android")) {
            detailedMenuPage.onDetailedMenuPage();
            loginPage = detailedMenuPage.clickLogin();
            loginPage.onLoginPage();

            loginPage.enterUserName("user");
            loginPage.enterPassword("pass");
            loginPage.pressLoginBtn();

            String title = productsPage.getTitle();
            Assert.assertEquals(title, "Products", "Title is not correct!");
        }
    }

    @Test
    public void unsuccessfulLogin() throws Exception {
        productsPage = new ProductsPage();
        detailedMenuPage = productsPage.openMenu();
        detailedMenuPage.onDetailedMenuPage();
        loginPage = detailedMenuPage.clickLogin();
        loginPage.onLoginPage();

        loginPage.pressLoginBtn();
        Assert.assertTrue(loginPage.isErrorMessageForUserNameDisplayed(), "User name error message is not displayed");
    }

}
