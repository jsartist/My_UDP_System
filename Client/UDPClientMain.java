package client;

public class UDPClientMain {

	private void eclipseManual() {
		System.out.println("eclipse에서 실행 시");
		System.out.println("  1. 메뉴창에 Run - Run Configurations... 클릭");
		System.out.println("  2. Arguments 클릭 후 빈칸에 주소와 포트 입력");
		System.out.println("    예) 175.242.111.123 12345");
		System.out.println("  3. Apply 누르고 창 닫은 후 다시 실행");
	}

	private void cmdManual() {
		System.out.println("cmd에서 실행 시");
		System.out.println("  명령어 마지막에 주소와 포트 입력");
		System.out.println("    예) java UDPClientMain 175.242.111.123 12345");
	}

	public void Manuals() {
		eclipseManual();
		System.out.println();
		cmdManual();
	}

	public static void main(String[] args) {
		UDPClientMain manuals = new UDPClientMain();
		try {
			UDPClient showTime = new UDPClient(args[0], args[1]);
			showTime.UDPHandler();
		}catch(Exception e) {
			System.out.println("인자값이 부족합니다.");
			manuals.Manuals();
		}
	}
}
