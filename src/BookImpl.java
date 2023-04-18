import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

public class BookImpl {
    OracleConnection oracleConnection = new OracleConnection();

    Scanner scanner = new Scanner(System.in);

    public void showBook() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = oracleConnection.connect();
            
            String sql = "SELECT BOOK_ID, ISBN, TITLE, PUBLISHDATE, AUTHOR, PUBLISHER, " +
                    "CASE WHEN AVAILABLE = 0 THEN '대출불가' ELSE '대출가능' END AS AVAILABLE, " +
                    "MEMBER_ID FROM BOOK ORDER BY PUBLISHDATE DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println(String.format("%-10s\t%-20s\t%-10s\t%-20s\t%-20s\t%-10s\t%-10s\t%-10s",
                    "BOOK_ID", "ISBN", "P_DATE", "TITLE", "AUTHOR", "PUBLISHER", "AVAILABLE", "MEMBER_ID"));
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int BOOK_ID = rs.getInt("BOOK_ID");
                String ISBN = rs.getString("ISBN");
                String TITLE = rs.getString("TITLE");
                Date PUBLISHDATE = rs.getDate("PUBLISHDATE");
                String AUTHOR = rs.getString("AUTHOR");
                String PUBLISHER = rs.getString("PUBLISHER");
                String AVAILABLE = rs.getString("AVAILABLE");
                int MEMBER_ID = rs.getInt("MEMBER_ID");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = dateFormat.format(PUBLISHDATE);
                int TITLE_MAX_LENGTH = 10;
                int AUTHOR_MAX_LENGTH = 20;
                int ISBN_LENGTH = 20;
                int PUBLISHER_MAX_LENGTH = 10;
                int AVAILABLE_MAX_LENGTH = 10;

                String truncatedTitle = (TITLE.length() > TITLE_MAX_LENGTH) ? TITLE.substring(0, TITLE_MAX_LENGTH-3) + "..." : String.format("%-" + (TITLE_MAX_LENGTH+1) + "s", TITLE);
                String truncatedAuthor = (AUTHOR.length() > AUTHOR_MAX_LENGTH) ? AUTHOR.substring(0, AUTHOR_MAX_LENGTH-2) + "..." : String.format("%-" + (AUTHOR_MAX_LENGTH+1) + "s", AUTHOR);
                String truncatedISBN = (ISBN.length() > ISBN_LENGTH) ? ISBN.substring(0, ISBN_LENGTH-2) + "..." : String.format("%-" + (ISBN_LENGTH+1) + "s", ISBN);
                String truncatedPublisher = (PUBLISHER.length() > PUBLISHER_MAX_LENGTH) ? PUBLISHER.substring(0, PUBLISHER_MAX_LENGTH-2) + "..." : String.format("%-" + (PUBLISHER_MAX_LENGTH+1) + "s", PUBLISHER);
                String truncatedAvailable = (AVAILABLE.length() > AVAILABLE_MAX_LENGTH) ? AVAILABLE.substring(0, AVAILABLE_MAX_LENGTH-2) + "..." : String.format("%-" + (AVAILABLE_MAX_LENGTH+1) + "s", AVAILABLE);

                System.out.println(String.format("%-10s\t%-20s\t%-10s\t%-20s\t%-20s\t%-10s\t%-10s\t%-10s",
                        BOOK_ID, truncatedISBN, formattedDate, truncatedTitle, truncatedAuthor, truncatedPublisher, truncatedAvailable, MEMBER_ID));
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oracleConnection.close(conn);
        }
    }
    public void showAvailableBooks() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = oracleConnection.connect();
            String sql = "SELECT BOOK_ID, ISBN, TITLE, PUBLISHDATE, AUTHOR, PUBLISHER, " +
                    "CASE WHEN AVAILABLE = 0 THEN '대출불가' ELSE '대출가능' END AS AVAILABLE, " +
                    "MEMBER_ID FROM BOOK WHERE AVAILABLE = 1 ORDER BY PUBLISHDATE DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println(String.format("%-10s\t%-20s\t%-10s\t%-20s\t%-20s\t%-10s\t%-10s\t%-10s",
                    "BOOK_ID", "ISBN", "P_DATE", "TITLE", "AUTHOR", "PUBLISHER", "AVAILABLE", "MEMBER_ID"));
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int BOOK_ID = rs.getInt("BOOK_ID");
                String ISBN = rs.getString("ISBN");
                String TITLE = rs.getString("TITLE");
                Date PUBLISHDATE = rs.getDate("PUBLISHDATE");
                String AUTHOR = rs.getString("AUTHOR");
                String PUBLISHER = rs.getString("PUBLISHER");
                String AVAILABLE = rs.getString("AVAILABLE");
                int MEMBER_ID = rs.getInt("MEMBER_ID");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = dateFormat.format(PUBLISHDATE);
                int TITLE_MAX_LENGTH = 10;
                int AUTHOR_MAX_LENGTH = 20;
                int ISBN_LENGTH = 20;
                int PUBLISHER_MAX_LENGTH = 10;
                int AVAILABLE_MAX_LENGTH = 10;

                String truncatedTitle = (TITLE.length() > TITLE_MAX_LENGTH) ? TITLE.substring(0, TITLE_MAX_LENGTH-3) + "..." : String.format("%-" + (TITLE_MAX_LENGTH+1) + "s", TITLE);
                String truncatedAuthor = (AUTHOR.length() > AUTHOR_MAX_LENGTH) ? AUTHOR.substring(0, AUTHOR_MAX_LENGTH-2) + "..." : String.format("%-" + (AUTHOR_MAX_LENGTH+1) + "s", AUTHOR);
                String truncatedISBN = (ISBN.length() > ISBN_LENGTH) ? ISBN.substring(0, ISBN_LENGTH-2) + "..." : String.format("%-" + (ISBN_LENGTH+1) + "s", ISBN);
                String truncatedPublisher = (PUBLISHER.length() > PUBLISHER_MAX_LENGTH) ? PUBLISHER.substring(0, PUBLISHER_MAX_LENGTH-2) + "..." : String.format("%-" + (PUBLISHER_MAX_LENGTH+1) + "s", PUBLISHER);
                String truncatedAvailable = (AVAILABLE.length() > AVAILABLE_MAX_LENGTH) ? AVAILABLE.substring(0, AVAILABLE_MAX_LENGTH-2) + "..." : String.format("%-" + (AVAILABLE_MAX_LENGTH+1) + "s", AVAILABLE);

                System.out.println(String.format("%-10s\t%-20s\t%-10s\t%-20s\t%-20s\t%-10s\t%-10s\t%-10s",
                        BOOK_ID, truncatedISBN, formattedDate, truncatedTitle, truncatedAuthor, truncatedPublisher, truncatedAvailable, MEMBER_ID));
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oracleConnection.close(conn);
        }
    }
    public void borrowBook() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Scanner scanner = new Scanner(System.in);
        int bookId, memberId;
        try {
            conn = oracleConnection.connect();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 사용자로부터 bookId와 memberId 입력 받기
            System.out.print("대출할 책의 ID를 입력하세요: ");
            bookId = scanner.nextInt();
            System.out.print("대출할 회원의 ID를 입력하세요: ");
            memberId = scanner.nextInt();
            String sql =
                    "UPDATE BOOK SET AVAILABLE = 0, MEMBER_ID = ? WHERE BOOK_ID = ? AND AVAILABLE = 1";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, bookId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) { // 대출 실패
                System.out.println("책 대출에 실패했습니다. 대출 가능한 책이 아니거나 대출 중인 책입니다.");
                conn.rollback(); // 롤백
            } else { // 대출 성공
                // LOAN 테이블에 대출 정보 추가
                int loanId = 1;
                String loanSql = "SELECT MAX(LOAN_ID) AS MAX_ID FROM LOAN";
                PreparedStatement loanPstmt = conn.prepareStatement(loanSql);
                ResultSet loanRs = loanPstmt.executeQuery();
                if (loanRs.next()) {
                    loanId = loanRs.getInt("MAX_ID") + 1;
                }
                loanRs.close();
                loanPstmt.close();

                String insertSql = "INSERT INTO LOAN (LOAN_ID, BOOK_ID, MEMBER_ID, EXTENDED, LOANDATE) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertPstmt = conn.prepareStatement(insertSql);
                insertPstmt.setInt(1, loanId);
                insertPstmt.setInt(2, bookId);
                insertPstmt.setInt(3, memberId);
                insertPstmt.setInt(4, 1);
                LocalDate loanDate = LocalDate.now();
                insertPstmt.setDate(5, java.sql.Date.valueOf(loanDate));
                
                insertPstmt.executeUpdate();
                insertPstmt.close();

                System.out.println("책 대출이 완료되었습니다.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnBook() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int bookId;

        System.out.print("반납할 책의 ID를 입력하세요: ");
        bookId = scanner.nextInt();
        try {
            conn = oracleConnection.connect();
            conn.setAutoCommit(false); // 트랜잭션 시작
            String sql = "UPDATE BOOK SET AVAILABLE = 1, MEMBER_ID = NULL WHERE BOOK_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) { // 반납 실패
                System.out.println("책 반납에 실패했습니다. 잘못된 책 ID입니다.");
                conn.rollback(); // 롤백
            } else { // 반납 성공
                System.out.println("책 반납이 완료되었습니다.");
                sql = "DELETE FROM LOAN WHERE BOOK_ID = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, bookId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void extendBorrow() {
        int bookId;
        int userId;
        Connection conn = null;
        PreparedStatement pstmt = null;
        System.out.print("연장할 책의 ID를 입력하세요: ");
        bookId = scanner.nextInt();
        System.out.print("대출한 유저의 ID를 입력하세요: ");
        userId = scanner.nextInt();

        try {
            conn = oracleConnection.connect();
            conn.setAutoCommit(false); // 트랜잭션 시작
            String sql = "UPDATE LOAN SET RETURNDATE = RETURNDATE + 7, EXTENDED = 0 WHERE BOOK_ID = ? AND EXTENDED = 1";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) { // 대출 연장 실패
                System.out.println("책 대출 연장에 실패했습니다. 잘못된 책 ID이거나 대출 중인 책이 아니거나 연장 기간이 만료되었거나 이미 연장한 책입니다.");
                conn.rollback(); // 롤백
            } else { // 대출 연장 성공
                System.out.println("책 대출 연장이 완료되었습니다.");

            } 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

