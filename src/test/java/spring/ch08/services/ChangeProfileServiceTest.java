package spring.ch08.services;

import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch08.config.Ch08AppContext;
import spring.junit.JUnitWithMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;

public class ChangeProfileServiceTest extends JUnitWithMySQL {

    private static AnnotationConfigApplicationContext appContext;
    private static final String defaultUsername = "suhyuk";
    private static final int defaultAge = 0;
    private ChangeProfileService changeProfileService;
    private Connection conn;

    @BeforeClass
    public static void setUp() throws SQLException {
        JUnitWithMySQL.setUp();
        appContext = new AnnotationConfigApplicationContext(Ch08AppContext.class);
    }

    @AfterClass
    public static void tearDown() {
        JUnitWithMySQL.tearDown();
    }

    @Before
    public void start() throws SQLException {
        conn = getConnection();
        changeProfileService = appContext.getBean(ChangeProfileService.class);
        dropUser();
        createUser();
    }

    @After
    public void end() throws SQLException {
        deleteUser();
        conn.close();
    }

    private void dropUser() throws SQLException {
        String dropTable = "drop table if exists ";
        dropTable += "users";
        Statement stmt = conn.createStatement();
        stmt.execute(dropTable);
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
