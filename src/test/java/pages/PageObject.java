package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PageObject {

    @Step("Проверка, что имя пользователя {userName} отображается на странице профиля")
   public static void checkUserNameOnProfile(String userName) {
        $("#userName-value").shouldBe(visible).shouldHave(text(userName));
    }

    @Step("Проверка, что книга с названием {bookName} отображается в коллекции")
    public static void checkBookInCollection(String bookName) {
        $(".ReactTable").shouldBe(visible).shouldHave(text(bookName));
    }

    @Step("Проверка, что книга с названием {bookName} отсутствует в коллекции")
    public static void checkBookNotInCollection(String bookName) {
        $(".ReactTable").shouldNotHave(text(bookName));
    }

    @Step("Удаление книги с названием {bookName} из коллекции через UI")
    public static void deleteBookFromCollection(String bookName) {
        $(".ReactTable").shouldBe(visible).shouldHave(text(bookName));
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
    }

}
