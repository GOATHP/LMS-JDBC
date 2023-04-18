import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserImpl {
    int memberId;
    String name;
    int gender;
    int age;
    String address;
    String contact;
    String birthDate;
    OracleConnection oracleConnection = new OracleConnection();
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    


    public void showUser() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = oracleConnection.connect();
            String sql = "SELECT MEMBER_ID, NAME, " +
                    "CASE WHEN GENDER = 0 THEN '여자' ELSE '남자' END AS GENDER, " +
                    "ADDRESS, CONTACT, BIRTHDATE, JOINDATE, " +
                    "TO_CHAR(AGE) || '세' AS AGE " +
                    "FROM MEMBER";


//            String sql = "SELECT * FROM MEMBER";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-11s | %-6s | %-15s | %-15s | %-15s | %-10s | %-20s |\n", "MEMBER ID", "NAME", "GENDER", "CONTACT", "BIRTHDATE","JOINDATE","AGE", "ADDRESS");
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int MEMBER_ID = rs.getInt("MEMBER_ID");
                String NAME = rs.getString("NAME");
                String GENDER = rs.getString("GENDER");
                String ADDRESS = rs.getString("ADDRESS");
                String CONTACT = rs.getString("CONTACT");
                Date BIRTHDATE = rs.getDate("BIRTHDATE");
                Date JOINDATE = rs.getDate("JOINDATE");
                LocalDate today = java.time.LocalDate.now();
                LocalDate birthday = BIRTHDATE.toLocalDate();
                age = java.time.Period.between(birthday, today).getYears();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDateJoin = dateFormat.format(JOINDATE);
                String formattedDateBirth = dateFormat.format(BIRTHDATE);
                System.out.printf("| %-10d | %-10s | %-6s | %15s | %-15s | %-15s | %-10d | %-20s \n", MEMBER_ID, NAME, GENDER, CONTACT, formattedDateBirth, formattedDateJoin,age, ADDRESS);



            }

            System.out.println("--------------------------------------------------------------------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oracleConnection.close(conn);
        }
    }

    public void insert() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = oracleConnection.connect();
            
            String getMaxIdQuery = "SELECT MAX(MEMBER_ID) AS MAX_ID FROM MEMBER";
            pstmt = conn.prepareStatement(getMaxIdQuery);
            rs = pstmt.executeQuery();
            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt("MAX_ID") + 1;
            }
            
            String sql =
                    "INSERT INTO MEMBER (MEMBER_ID, NAME, GENDER, ADDRESS, CONTACT, BIRTHDATE, AGE) VALUES (?, ?, ?, ?, ?, to_date(?, 'yyyy/MM/dd'), ?)";
            pstmt = conn.prepareStatement(sql);

            System.out.println("Enter Name: ");
            name = scanner.next();

            System.out.println("Enter Gender: ");
            gender = scanner.nextInt();

            System.out.println("Enter Address: ");
            address = scanner.next();
            
            System.out.println("Enter Contact: ");
            contact = scanner.next();
            
            System.out.println("Enter Birthdate (yyyy/mm/dd): ");
            birthDate = scanner.next();
            
            
            LocalDate today = java.time.LocalDate.now();
            LocalDate birthday = LocalDate.parse(birthDate);
            age = java.time.Period.between(birthday, today).getYears();

            pstmt.setInt(1, nextId);
            pstmt.setString(2, name);
            pstmt.setInt(3, gender);
            pstmt.setString(4, address);
            pstmt.setString(5, contact);
            pstmt.setString(6, birthDate);
            pstmt.setInt(7, age);

            
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(nextId + " 회원이 새로 추가되었습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // 롤백 실행
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            oracleConnection.close(conn);
        }
    }

    public void restore() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = oracleConnection.connect();
            String sql = "SELECT * FROM MEMBER_DELETED";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 복원할 ROW의 ID를 입력받음
            System.out.print("복원할 유저의 ID를 입력하세요: ");
            memberId = scanner.nextInt();

            // 복원할 ROW의 데이터를 불러와서 MEMBER 테이블에 INSERT 쿼리 실행
            while (rs.next()) {
                if (rs.getInt("MEMBER_ID") == memberId) {
                    sql = "INSERT INTO MEMBER (MEMBER_ID, NAME, GENDER, ADDRESS, CONTACT, BIRTHDATE) VALUES (?, ?, ?, ?, ?, to_date(?, 'yyyy/MM/dd'))";
                    pstmt = conn.prepareStatement(sql);
                    
                    pstmt.setInt(1, rs.getInt("MEMBER_ID"));
                    pstmt.setString(2, rs.getString("NAME"));
                    pstmt.setString(3, rs.getString("GENDER"));
                    pstmt.setString(4, rs.getString("ADDRESS"));
                    pstmt.setString(5, rs.getString("CONTACT"));
                    pstmt.setString(6, rs.getString("BIRTHDATE").substring(0, 10));
                    pstmt.executeUpdate();
                    
                    sql = "DELETE FROM MEMBER_DELETED WHERE MEMBER_ID = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, memberId);
                    pstmt.executeUpdate();
                    
                    System.out.println("유저 " + memberId + "가 복원되었습니다.");
                    return;
                }
            }
            System.out.println("유저 " + memberId + "를 찾을 수 없습니다.");

        } catch (SQLException e) {
            
        } finally {
            oracleConnection.close(conn);
        }
    }
    
    public void delete() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = oracleConnection.connect();
            String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
            pstmt = conn.prepareStatement(sql);

            System.out.print("삭제할 유저의 ID를 입력하세요: ");
            int memberId = scanner.nextInt();
            pstmt.setInt(1, memberId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                sql = "INSERT INTO MEMBER_DELETED (MEMBER_ID, NAME, GENDER, ADDRESS, CONTACT, BIRTHDATE) VALUES (?, ?, ?, ?, ?, to_date(?, 'yyyy/MM/dd'))";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, rs.getInt("MEMBER_ID"));
                pstmt.setString(2, rs.getString("NAME"));
                pstmt.setString(3, rs.getString("GENDER"));
                pstmt.setString(4, rs.getString("ADDRESS"));
                pstmt.setString(5, rs.getString("CONTACT"));
                pstmt.setString(6, rs.getString("BIRTHDATE").substring(0, 10)); // 올바른 형식의 날짜 문자열을 입력해야 함
                pstmt.executeUpdate();
            }

            sql = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println("유저 " + memberId + "가 삭제되었습니다.");
            } else {
                System.out.println("유저 " + memberId + "를 찾을 수 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            oracleConnection.close(conn);
        }
    }

    //    public void delete() {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            conn = oracleConnection.connect();
//            String sql = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";
//            pstmt = conn.prepareStatement(sql);
//            
//            System.out.print("삭제할 유저의 ID를 입력하세요: ");
//            memberId = scanner.nextInt();
//            pstmt.setInt(1, memberId);
//            
//            int count = pstmt.executeUpdate();
//            if (count > 0) {
//                sql = "INSERT INTO MEMBER_DELETED SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
//                pstmt = conn.prepareStatement(sql);
//                pstmt.setInt(1, memberId);
//                pstmt.executeUpdate();
//                System.out.println("유저 " + memberId + "가 삭제되었습니다.");
//            } else {
//                System.out.println("유저 " + memberId + "를 찾을 수 없습니다.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            oracleConnection.close(conn);
//        }
//    }

    public void update() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = oracleConnection.connect();
            Scanner scanner = new Scanner(System.in);
            System.out.print("수정할 유저의 ID를 입력하세요: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("어떤 정보를 수정하시겠습니까?");
            System.out.println("1. 이름");
            System.out.println("2. 성별");
            System.out.println("3. 주소");
            System.out.println("4. 연락처");
            System.out.println("5. 생년월일");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the remaining newline character

            String column;
            switch (choice) {
                case 1:
                    column = "NAME";
                    break;
                case 2:
                    column = "GENDER";
                    break;
                case 3:
                    column = "ADDRESS";
                    break;
                case 4:
                    column = "CONTACT";
                    break;
                case 5:
                    column = "BIRTHDATE";
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    return;
            }

            System.out.print("새로운 값을 입력하세요: ");
            String value = scanner.nextLine();

            String sql = "UPDATE MEMBER SET " + column + " = ? WHERE MEMBER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);
            pstmt.setInt(2, memberId);

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println("유저 " + memberId + "의 정보가 수정되었습니다.");
            } else {
                System.out.println("유저 " + memberId + "를 찾을 수 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // 롤백 실행
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            oracleConnection.close(conn);
        }
    }
}

