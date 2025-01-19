package specifications;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class UiSpecification {

    public static void checkUserName(String expectedUserName) {
        $("#userName-value").shouldBe(visible).shouldHave(text(expectedUserName));
    }

    public static void checkBookInCollection(String bookTitle) {
        $(".ReactTable").shouldBe(visible).shouldHave(text(bookTitle));
    }

    public static void deleteBookFromCollection() {
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
    }
}
