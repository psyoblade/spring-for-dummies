package spring.ch08.services;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch08.config.Ch08AppContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_6_latest;
import static junit.framework.TestCase.assertEquals;

public class ChangeProfileServiceTest {

    private static AnnotationConfigApplicationContext appContext;
    private static EmbeddedMysql mysqld;
    private static String schema = "psyoblade";
    private static String username = "suhyuk";
    private static String password = "";

    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/" + schema;
    private final String defaultUsername = "suhyuk";
    private final int defaultAge = 0;

    private ChangeProfileService changeProfileService;

    @BeforeClass
    public static void setUp() {
        MysqldConfig config = aMysqldConfig(v5_6_latest)
                .withPort(3306)
                .withUser(username, password)
                .build();
        mysqld = anEmbeddedMysql(config)
                .addSchema(schema)
                .start();
        appContext = new AnnotationConfigApplicationContext(Ch08AppContext.class);
    }

    @AfterClass
    public static void tearDown() {
        mysqld.stop();
    }

    private void createUser() throws SQLException {
        String createTable = "create table ";
        createTable += "users (id int, email varchar(100), username varchar(100), age int)";
        Statement stmt = conn.createStatement();
        stmt.execute(createTable);

        String insertTable = String.format("insert into users values (1, 'park.suhyuk@gmail.com', '%s', %d)",
                defaultUsername, defaultAge);
        stmt.execute(insertTable);
    }

    private void deleteUser() throws SQLException {
        String deleteUser = "delete ";
        deleteUser += "from users";
        Statement stmt = conn.createStatement();
        stmt.execute(deleteUser);
    }

    @Before
    public void start() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
        changeProfileService = appContext.getBean(ChangeProfileService.class);
        createUser();
    }

    @After
    public void end() throws SQLException {
        deleteUser();
        conn.close();
    }

    @Test
    public void 시작() {
        assert(true);
    }

    @Test
    public void 나이가져오기() {
        String email = "park.suhyuk@gmail.com";
        assertEquals(defaultAge, changeProfileService.getAge(email));
    }

    @Test
    public void 이름_패스워드_나이_변경() {
        String email = "park.suhyuk@gmail.com";
        String username = "박수혁";
        int age = 18;
        changeProfileService.changeUsernameAndAge(email, username, age);
        int actual = changeProfileService.getAge(email);
        assertEquals(age, actual);
    }

    @Test
    public void 이름_패스워드_나이_변경_실패 () {
        String email = "park.suhyuk@gmail.com";
        String username = "박수혁";
        int age = 108;
        try {
            changeProfileService.changeUsernameAndAge(email, username, age);
        } catch (IllegalArgumentException e) {
            assert(true);
        }
        String actual = changeProfileService.getName(email);
        assertEquals(defaultUsername, actual);
    }
}
