package tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import utils.DriverManager;

public class ProductTests extends BaseTest {
    ProductsPage productsPage;
    ProductDetailsPage productDetailsPage;

    @Test
    public void validateProductNameAndPrice() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        productsPage = new ProductsPage();
        String titleOfProduct = productsPage.getProductName();
        String priceOfProduct = productsPage.getProductPrice();

        softAssert.assertEquals(titleOfProduct, "Sauce Labs Backpack", "Title is not correct!");
        softAssert.assertEquals(priceOfProduct, "$ 29.99", "Price of the product is not correct!");
        softAssert.assertAll();
    }

    @Test
    public void validateProductDetails() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        productsPage = new ProductsPage();
        productDetailsPage = productsPage.clickProduct();

        String titleOfProduct = productDetailsPage.getTitleOfTheProduct();
        String priceOfProduct = productDetailsPage.getPriceOfTheProduct();

        boolean areDetailsVisible = false;
        // Scroll to product details element
        if (DriverManager.getTestedPlatformName().equals("Android")) {
            productDetailsPage.scrollToElement("Product Highlights");
            areDetailsVisible = productDetailsPage.isProductDetailsElementDisplayed();
        } else if (DriverManager.getTestedPlatformName().equals("iOS")) {
            productDetailsPage.scrollToProductDetailsElement();
            areDetailsVisible = productDetailsPage.isProductDetailsElementDisplayed();
        }

        softAssert.assertEquals(titleOfProduct, "Sauce Labs Backpack", "Title is not correct!");
        softAssert.assertEquals(priceOfProduct, "$ 29.99", "Price of the product is not correct!");
        softAssert.assertTrue(areDetailsVisible, "Details are not visible");
        softAssert.assertAll();
    }


}
