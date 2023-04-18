import java.util.Scanner;

public class UserController {
    private boolean isExit = false;
    private Scanner scanner = new Scanner(System.in);
    int choice;
    OracleConnection oracleConnection = new OracleConnection();
    UserImpl userImpl = new UserImpl();

    public void printUserMenu() {
        isExit = false;
        while (!isExit) {
            System.out.println("---------------------------------------------------------");
            System.out.println("1. 유저등록, 2. 유저목록, 3. 유저수정, 4. 유저삭제, 5. 삭제취소 0. 뒤로");
            System.out.println("---------------------------------------------------------");
            int target = scanner.nextInt();
            if (target == 1) {
                userImpl.insert();
            } else if (target == 2) {
                userImpl.showUser();
            } else if (target == 0) {
                isExit = true;

            } else if (target == 3) {
                userImpl.update();
            } else if (target == 4) {
                userImpl.delete();
            } else if (target == 5) {
                userImpl.restore();
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }

        }
    }
}
