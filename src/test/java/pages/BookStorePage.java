package pages;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BookStorePage {
    private final SelenideElement tableWithUserBooks = $(".ReactTable"),
            searchField = $("#searchBox"),
            deleteIcon = $("#delete-record-undefined"),
            modalOkButton = $("#closeSmallModal-ok");

    @Step("Открыть список добавленных книг пользователя")
    public BookStorePage openUserBooksPage(String userId, String expires, String token) {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        open("/profile");

        return this;
    }

    @Step("Проверка, что книга с названием {bookName} отображается в коллекции")
    public BookStorePage findBookByName(String bookName) {
        tableWithUserBooks.shouldHave(text(bookName));

        return this;
    }

    @Step("Удаление книги с названием {bookName} из коллекции")
    public BookStorePage deleteBookByName(String bookName) {
        searchField.setValue(bookName);
        deleteIcon.click();
        modalOkButton.click();

        return this;
    }
    @Step("Проверка, что книга с названием {bookName} отсутствует в коллекции")
    public BookStorePage findNotBookByName(String bookName) {
        tableWithUserBooks.shouldNotHave(text(bookName));

        return this;
    }
}