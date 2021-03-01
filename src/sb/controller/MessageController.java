package sb.controller;

import sb.view.FailView;
import sb.view.MessageView;

// BufferedReader 출력을 위한 controller
public class MessageController {
	private static MessageController instance = new MessageController();

	public MessageController() {
	}

	public static MessageController getInstance() {
		return instance;
	}

	public void beginMenu() {
		MessageView.beginMenu();
	}

	public void choiceAgain() {
		FailView.failMessage("\n잘못된 입력값입니다.\n");
	}

	public void makeNewBread() {
		MessageView.makeNewBread();
	}

	public void hasNextCustomer() {
		MessageView.messageView("\n아직 처리되지 않은 주문이 있습니다.");
	}

	public void workEnd() {
		MessageView.messageView("\n이제 더 이상 손님을 받지 않습니다.");
	}
}
