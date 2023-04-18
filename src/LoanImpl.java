import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class LoanImpl {
    OracleConnection oracleConnection = new OracleConnection();

    Scanner scanner = new Scanner(System.in);

    public void showLoan() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = oracleConnection.connect();
            String sql = "SELECT * FROM LOAN";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                
                int LOAN_ID = rs.getInt("LOAN_ID");
                int BOOK_ID = rs.getInt("BOOK_ID");
                int MEMBER_ID = rs.getInt("MEMBER_ID");
                String EXTENDED = rs.getString("EXTENDED");
                Date LOANDATE = rs.getDate("LOANDATE");
                Date RETURNDATE = rs.getDate("RETURNDATE");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedLoanDate = dateFormat.format(LOANDATE);
                String formattedReturnDate = dateFormat.format(RETURNDATE);

                System.out.println("LOAN_ID: " + LOAN_ID + ", BOOK_ID: " + BOOK_ID + ", MEMBER_ID: " + MEMBER_ID
                        + ", EXTENDED: " + EXTENDED + ", LOANDATE: " + formattedLoanDate + ", RETURNDATE: "
                        + formattedReturnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void showUserLoans() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        System.out.print("조회할 회원의 ID를 입력하세요: ");
        int memberId;
        memberId = scanner.nextInt();
        try {
            conn = oracleConnection.connect();
            String sql = "SELECT LOAN.LOAN_ID, BOOK.TITLE, LOAN.LOANDATE, LOAN.RETURNDATE " +
                         "FROM LOAN INNER JOIN BOOK ON LOAN.BOOK_ID = BOOK.BOOK_ID " +
                         "WHERE LOAN.MEMBER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();

            // Print table header
            System.out.println("----------------------------------------------------");
            System.out.printf("| %-10s | %-30s | %-12s | %-12s |\n", "LOAN ID", "TITLE", "LOAN DATE", "RETURN DATE");
            System.out.println("----------------------------------------------------");

            // Print table rows
            while (rs.next()) {
                int loanId = rs.getInt("LOAN_ID");
                String title = rs.getString("TITLE");
                Date loanDate = rs.getDate("LOANDATE");
                Date returnDate = rs.getDate("RETURNDATE");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedLoanDate = dateFormat.format(loanDate);
                String formattedReturnDate = dateFormat.format(returnDate);

                System.out.printf("| %-10d | %-30s | %-12s | %-12s |\n", loanId, title, formattedLoanDate, formattedReturnDate);
            }

            System.out.println("----------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}