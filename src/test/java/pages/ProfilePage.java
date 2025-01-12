package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

    private SelenideElement booksList = $(By.id("booksList"));
    private SelenideElement deleteButton = $(By.cssSelector(".delete-button"));

    public ProfilePage openProfile() {
        open("https://demoqa.com/profile");
        return this;
    }

    public ProfilePage checkBooksInUI(String isbn) {
        $(By.xpath("//span[text()='" + isbn + "']")).should(exist);
        return this;
    }

    public ProfilePage deleteBookFromUI(String isbn) {
        $(By.xpath("//span[text()='" + isbn + "']/ancestor::div[contains(@class, 'book')]//button[contains(text(), 'Delete')]")).click();
        return this;
    }

    public ProfilePage checkBookNotInUI(String isbn) {
        $(By.xpath("//span[text()='" + isbn + "']")).shouldNot(exist);
        return this;
    }
}
