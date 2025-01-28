package data;
import io.github.cdimascio.dotenv.Dotenv;
public class TestData {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    public static final String bookStoreLogin = dotenv.get("bookStoreLogin");
    public static final String bookStorePassword = dotenv.get("bookStorePassword");
    public static final String testBookIsbn = "9781449365035";

    public static final String bookName = "Speaking JavaScript";
}