import java.util.Scanner;

public class LoanController {
    private boolean isExit = false;
    private Scanner scanner = new Scanner(System.in);
    int choice;
    OracleConnection oracleConnection = new OracleConnection();
    LoanImpl loanImpl = new LoanImpl();

    public void printLoan() {
        isExit = false;
        while (!isExit) {
            System.out.println("-----------------");
            System.out.println("1. 모든 대출 조회, 2. 유저 대출 조회 0. 뒤로");
            System.out.println("-----------------");
            int target = scanner.nextInt();
            if (target == 1) {
                loanImpl.showLoan();
            } else if (target == 2) {
                loanImpl.showUserLoans();

            } else if (target == 0) {
                isExit = true;
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}

