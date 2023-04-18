import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class BookListImpl {
    OracleConnection oracleConnection = new OracleConnection();

    public void showBook() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = oracleConnection.connect();
            String sql = "SELECT TITLE, PUBLISHDATE, AUTHER, PUBLISHER FROM BOOKLIST";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println(String.format("%-20s\t%-20s\t%-20s\t%-20s",
                    "TITLE", "PUBLISH DATE", "AUTHOR", "PUBLISHER"));
            System.out.println("--------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String title = rs.getString("TITLE");
                Date publishDate = rs.getDate("PUBLISHDATE");
                String author = rs.getString("AUTHER");
                String publisher = rs.getString("PUBLISHER");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = dateFormat.format(publishDate);

                int MAX_LENGTH_TITLE = 20;
                int MAX_LENGTH_AUTHOR = 20;
                int MAX_LENGTH_PUBLISHER = 20;

                String truncatedTitle = (title.length() > MAX_LENGTH_TITLE) ? title.substring(0, MAX_LENGTH_TITLE-3) + "..." : String.format("%-" + (MAX_LENGTH_TITLE+1) + "s", title);
                String truncatedAuthor = (author.length() > MAX_LENGTH_AUTHOR) ? author.substring(0, MAX_LENGTH_AUTHOR-2) + "..." : String.format("%-" + (MAX_LENGTH_AUTHOR+1) + "s", author);
                String truncatedPublisher = (publisher.length() > MAX_LENGTH_PUBLISHER) ? publisher.substring(0, MAX_LENGTH_PUBLISHER-2) + "..." : String.format("%-" + (MAX_LENGTH_PUBLISHER+1) + "s", publisher);

                System.out.println(String.format("%-20s\t%-20s\t%-20s\t%-20s",
                        truncatedTitle, formattedDate, truncatedAuthor, truncatedPublisher));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oracleConnection.close(conn);
        }
    }
}
