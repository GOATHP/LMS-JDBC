import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.data.category.*;

public class OracleConnection {
    private static final String URL = "jdbc:oracle:thin:@192.168.119.119:1521/dink";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger1";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
// Connection conn = null;
//// Font font = new Font("돋움", Font.PLAIN, 12); ChartFactory.setChartFont(font);
// ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
// try {
// Class.forName("oracle.jdbc.driver.OracleDriver");
// String url = "jdbc:oracle:thin:@192.168.119.119:1521/dink";
// String user = "scott";
// String password = "tiger1";
// conn = DriverManager.getConnection(url, user, password);
// Statement stmt = conn.createStatement();
// ResultSet rs = stmt.executeQuery(
// "SELECT c.GENDER, e.JOB, COUNT(e.EMPNO) AS NUM_EMPLOYEES\r\n"
// + "FROM CUSTOMER c\r\n"
// + "INNER JOIN (\r\n"
// + " SELECT EMPNO, JOB\r\n"
// + " FROM EMP_LARGE\r\n"
// + ") e ON c.ACCOUNT_MGR = e.EMPNO\r\n"
// + "GROUP BY c.GENDER, e.JOB\r\n"
// + "ORDER BY NUM_EMPLOYEES DESC");
//// 오라클 커넥트 연결 및 SQL 문을 통해 결과 데이터 rs변수에 저장 서브쿼리 활용
// DefaultCategoryDataset dataset = new DefaultCategoryDataset();
// while (rs.next()) {
// String gender = rs.getString(1);
// String job = rs.getString(2);
// double numEmployees = rs.getDouble(3);
//// rs 변수의 값들을 dataset이라는 클래스에 하나씩 저장
//
// dataset.addValue(numEmployees, "직업", job);
// }
//
//// JFreeChart를 통해 차트 제목, 범례, 데이터 등 삽입
// JFreeChart chart = ChartFactory.createStackedBarChart3D("","",
// "(명)", dataset);
// chart.getCategoryPlot().getDomainAxis()
// .setCategoryLabelPositions(CategoryLabelPositions.UP_45);
//
// ChartPanel chartPanel = new ChartPanel(chart);
//
//// Jframe을 통해 이미지 파일을 띄울 창 제목 설정
// JFrame frame = new JFrame("여성 고객 담당 직원의 직업");
// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// frame.getContentPane().add(chartPanel);
// frame.setSize(1000, 600); // 크기 설정
// frame.setVisible(true);
//
//
//// 예외처리
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// } catch (SQLException e) {
// e.printStackTrace();
// } finally {
// try {
// conn.close();
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }
// }
// }
