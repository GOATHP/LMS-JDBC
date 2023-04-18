import java.util.Scanner;

public class BookController {

    private boolean isExit = false;
    private Scanner scanner = new Scanner(System.in);
    OracleConnection oracleConnection = new OracleConnection();
    BookImpl bookImpl = new BookImpl();

    public void printBookMenu() {
        oracleConnection.connect();
        isExit = false;
        while (!isExit) {
            System.out.println("---------------------------------------------------");
            System.out.println("1. 책 조회, 2. 대출 가능 책 조회, 3. 책 대출  4. 책 반납, 5. 책 대출 연장, 0. 뒤로");
            System.out.println("---------------------------------------------------");
            int target = scanner.nextInt();
            if (target == 1) {
                bookImpl.showBook();
            } else if (target == 2) {
                bookImpl.showAvailableBooks();
            } else if (target == 3) {
                bookImpl.borrowBook();
            } else if (target == 0) {
                isExit = true;
            } else if (target == 4) {
                bookImpl.returnBook();
            } // 업데이트
            else if (target == 5) {
                bookImpl.extendBorrow();
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");

            }
        }
    }
}

// } else if (target == 2) {
// bookImpl.findAll();
//
// break;
// } else if (target == 0) {
//
// isExit = true;
// break;
// } else if (target == 3) {
// bookRepositoryImpl.updateBook();
// break;
// } else if (target == 4) {
// bookRepositoryImpl.removeBook();
// break;
// } else if (target == 5) {
// bookRepositoryImpl.restoreBook();
// break;
// }
