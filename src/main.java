import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        int choice;
        boolean isExit = false;
        BookController bookController = new BookController();
        UserController userController = new UserController();
        LoanController loanController = new LoanController();
        while (!isExit) {

            System.out.println("안녕하세요. 도서관리프로그램을 시작합니다.");
            System.out.println("------------------------------");
            System.out.println("1.회원관리 2.도서관리 3.대출조회 4.종료");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch(choice) {
                case 1:
                    userController.printUserMenu();
                    continue;
                case 2:
                    bookController.printBookMenu();
                    continue;
                case 3:
                    loanController.printLoan();
                    continue;
                case 4: 
                    System.out.println("프로그램을 종료합니다.");
                    isExit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}
