package spring.junit;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_6_latest;

public class JUnitWithMySQL {
    private static EmbeddedMysql mysqld;
    private static final String schema = "psyoblade";
    private static final String defaultUser = "suhyuk";
    private static final String defaultPass = "";

    @BeforeClass
    public static void setUp() throws SQLException {
        MysqldConfig config = aMysqldConfig(v5_6_latest)
                .withPort(3306)
                .withUser(defaultUser, defaultPass)
                .build();
        mysqld = anEmbeddedMysql(config)
                .addSchema(schema)
                .start();
    }

    @AfterClass
    public static void tearDown() {
        mysqld.stop();
    }

    protected Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/" + schema;
        return DriverManager.getConnection(url, defaultUser, defaultPass);
    }

}
