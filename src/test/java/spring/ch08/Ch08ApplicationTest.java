package spring.ch08;

import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch08.config.Ch08AppContext;
import spring.ch08.repositories.UserAccount;
import spring.junit.JUnitWithMySQL;

import java.sql.*;
import static org.junit.Assert.assertEquals;

public class Ch08ApplicationTest extends JUnitWithMySQL {

    private static final String table = "users";
    private Connection conn;
    private static AnnotationConfigApplicationContext appContext;

    @BeforeClass
    public static void setUp() throws SQLException {
        JUnitWithMySQL.setUp();
        appContext = new AnnotationConfigApplicationContext(Ch08AppContext.class);
    }

    @AfterClass
    public static void tearDown() {
        JUnitWithMySQL.tearDown();
    }

    private void dropUser() throws SQLException {
        String dropTable = "drop table if exists ";
        dropTable += table;
        Statement stmt = conn.createStatement();
        stmt.execute(dropTable);
    }

    @Before
    public void initialize() throws SQLException {
        conn = getConnection();
        dropUser();
    }

    @After
    public void destroy() throws SQLException {
        conn.close();
    }

    @Test
    public void 테이블생성_데이터입력() throws SQLException {
        String createTable = "create table ";
        createTable += "users (id int, name varchar(100))";
        Statement stmt = conn.createStatement();
        stmt.execute(createTable);

        String insertTable = "insert into users values (1, 'suhyuk')";
        stmt.execute(insertTable);

        String selectTable = "select * from users";
        ResultSet rs = stmt.executeQuery(selectTable);
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println(String.format("%d:%s", id, name));
        }

        String countTable = "select count(*) from users";
        rs = stmt.executeQuery(countTable);
        rs.next();
        int actual = rs.getInt("count(*)");
        int expected = 1;
        assertEquals(expected, actual);

        UserAccount userAccount = appContext.getBean(UserAccount.class);
        assertEquals(1, userAccount.selectCountAll());
    }

    @Test
    public void KeyHolder_이용해서_데이터입력() throws SQLException {

        String createTable = "create table ";
        createTable += "users (id int auto_increment primary key, name varchar(100))";
        Statement stmt = conn.createStatement();
        stmt.execute(createTable);

        UserAccount userAccount = appContext.getBean(UserAccount.class);
        assertEquals(1, userAccount.insertUser("suhyuk"));
        assertEquals(2, userAccount.insertUser("chiyoung"));
        assertEquals(3, userAccount.insertUser("jh9310s"));
        assertEquals(4, userAccount.insertUser("shryu"));
        assertEquals(5, userAccount.insertUser("ajg0716"));
    }

}
