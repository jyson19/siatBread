package sb.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sb.controller.MessageController;
import sb.controller.SiatBreadController;

// 초기 출력창
public class StartView {
	public static void main(String[] args) {
		SiatBreadController ctroller = SiatBreadController.getInstance();
		MessageController mCtroller = MessageController.getInstance();

		Thread thread = new Thread(new Intro());
		thread.start();
		try {
			Thread.sleep(30000);
			thread.isInterrupted();
			thread.interrupt();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}


		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
				System.out.println("     판교 전통 빵집 '씨빵'입니다.");
				System.out.println("           1. 빵 연구소 방문");
				System.out.println("           2. 재고 조회");
				System.out.println("           3. 제품 검색(키워드)");
				System.out.println("           4. 폐기 처리");
				System.out.println("           5. 가게 OPEN");
				System.out.println("           6. 매출 장부 조회");
				System.out.println("           7. 퇴근하기");
				System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
				System.out.printf("\n무엇을 하시겠습니까 ? : ");
				int choice = Integer.parseInt(br.readLine());
				if (choice == 1) {
					while (true) {
						System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
						System.out.println("      SB 리서치 연구소 입니다.");
						System.out.println("           1. 신제품 등록");
						System.out.println("           2. 제품 변경");
						System.out.println("           3. 등록 취소");
						System.out.println("           4. 전체 제품 조회");
						System.out.println("           5. 나가기");
						System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
						System.out.printf("\n무엇을 하시겠습니까 ? : ");
						int choice3 = Integer.parseInt(br.readLine());
						if (choice3 == 1) {
							System.out.printf("빵의 이름을 지어주세요. : ");
							String breadName = br.readLine();
							System.out.printf("유통기한은 며칠간 유효한가요? : ");
							int expDate = Integer.parseInt(br.readLine());
							System.out.printf("가격은 얼마인가요? : ");
							int price = Integer.parseInt(br.readLine());
							ctroller.makeNewBread(breadName, expDate, price);
						} else if (choice3 == 2) {
							ctroller.getAllBread();
							System.out.println("제품 상세 변경을 시작합니다.");
							System.out.printf("변경할 빵의 번호를 입력해주세요 : ");
							int breadId = Integer.parseInt(br.readLine());
							System.out.printf("변경할 빵의 이름을 입력해주세요 : ");
							String breadName = br.readLine();
							System.out.printf("변경할 빵의 유통기한을 입력해주세요(일 수 기준) : ");
							int expDate = Integer.parseInt(br.readLine());
							System.out.printf("변경할 빵의 가격을 입력해주세요 : ");
							int price = Integer.parseInt(br.readLine());
							ctroller.updateBread(breadId, breadName, expDate, price);
						} else if (choice3 == 3) {
							while (true) {
								System.out.println("\n아래 제품 검색 기능을 활용하여");
								System.out.println("원하고자 하는 제품의 번호를 조회할 수 있습니다.");
								System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
								System.out.println("           1. 제품 검색(키워드)");
								System.out.println("           2. 등록 취소(번호)");
								System.out.println("           3. 뒤로가기");
								System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
								System.out.printf("\n무엇을 하시겠습니까 ? : ");
								int choice4 = Integer.parseInt(br.readLine());
								if (choice4 == 1) {
									System.out.printf("검색하고자 하는 빵의 이름은 무엇인가요? : ");
									String breadName = br.readLine();
									ctroller.searchBread(breadName);
								} else if (choice4 == 2) {
									System.out.printf("등록 취소하고자 하는 제품의 번호를 입력해주세요 : ");
									int breadId = Integer.parseInt(br.readLine());
									ctroller.deleteBread(breadId);
								} else if (choice4 == 3) {
									break;
								} else {
									mCtroller.choiceAgain();
								}
							}
						} else if (choice3 == 4) {
							ctroller.getAllBread();
						} else if (choice3 == 5) {
							break;
						} else {
							mCtroller.choiceAgain();
						}
					}
				} else if (choice == 2) {
					ctroller.breadStockIn();
				} else if (choice == 3) {
					System.out.printf("검색하고자 하는 빵의 이름은 무엇인가요? : ");
					String breadName = br.readLine();
					ctroller.searchBread(breadName);
				} else if (choice == 4) {
					ctroller.disposalStockIn();
				} else if (choice == 5) {
					System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
					System.out.println("\n           O P E N !!");
					System.out.println("           장사 시작합니다~!\n");
					System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
					boolean flag = true;
					while (flag) {
						Thread thread2 = new Thread(new OrderThread());
						thread2.start();
						synchronized (thread2) {
							try {
								thread2.wait();
								boolean flag2 = true;
								while (flag2) {
									System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
									System.out.println("           1. 주문 조회");
									System.out.println("           2. 빵 제작");
									System.out.println("           3. 주문 판매 처리");
									System.out.println("           4. 손님 마감");
									System.out.println("           5. 가게 CLOSE");
									System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
									System.out.printf("\n무엇을 하시겠습니까? : ");
									int choice2 = Integer.parseInt(br.readLine());
									if (choice2 == 1) {
										ctroller.selectOrder();
									} else if (choice2 == 2) {
										System.out.printf("만드실 빵의 이름을 입력해주세요 : ");
										String breadName = br.readLine();
										ctroller.createBread(breadName);
									} else if (choice2 == 3) {
										if (ctroller.sellBread() == true) {
											thread2.notify();
										}
									} else if (choice2 == 4) {
										flag = false;
										thread2.notify();
										Thread.sleep(100);
										thread2.interrupt();
										mCtroller.workEnd();
									} else if (choice2 == 5) {
										// 주문 객체가 있으면 true
										if (ctroller.orderCheck() == false) {
											flag2 = false;
										} else {
											mCtroller.hasNextCustomer();
											continue;
										}
									} else {
										mCtroller.choiceAgain();
									}
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (IllegalThreadStateException e) {
								e.printStackTrace();
							}
						}
					}
				} else if (choice == 6) {
					ctroller.saleBooks();
				} else if (choice == 7) {
					System.out.println("\n그리울거에요.. 조심히가요..\n");
					break;
				} else {
					mCtroller.choiceAgain();
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			mCtroller.choiceAgain();
		}
		// 나중에(CustomerDTO 완성 이후)
		// (미구현)매출 top3 상품 조회
		// (미구현)빵 별로 적정 재고수량
		// (미구현)특정 날짜 sale event -> 날짜 변경 옵션 적용 이후
	}
}

class Intro implements Runnable {
	public Intro() {
	}
	SiatBreadController ctroller = SiatBreadController.getInstance();
	@Override
	public synchronized void run() {
		try {
			while (true) {
				String str = "\n판교의 어느 한 동네.. 3대째 내려오는 전설의 빵집이 있다고 한다. . .\n"//
						+ "\n외국에서도 이름을 날려 'Siat Bread' 라고 알려진 전설의 빵집. . .\n"//
						+ "\n이름하여 '씨빵' ..  ..  ..\n"//
						+ "\n\n지금 시작합니다...\n\n";
				String str2 = "Loading .. ";
				ctroller.injectionDB();
				String str3 = "[■ ■ ■ ■ ■ ■ ■ ■ ■ ■] ";
				String str4 = "완료!\n";
				for (int i = 0; i < str.length(); i++) {
					System.out.print(str.charAt(i));
					Thread.sleep(120);
				}

				for (int i = 0; i < str2.length(); i++) {
					System.out.print(str2.charAt(i));
					Thread.sleep(10);
				}
				for (int i = 0; i < str3.length(); i++) {
					System.out.print(str3.charAt(i));
					Thread.sleep(500);
				}
				System.out.println(str4);
				break;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class OrderThread implements Runnable {
	public OrderThread() {
	}

	SiatBreadController ctroller = SiatBreadController.getInstance();
	String[] str = { "\n ▶ 바나나마라빵은 아마도(?) 맛있습니다.\n", //
			"\n ▶ 제작자는 빵을 좋아하지 않습니다.\n", //
			"\n ▶ 일요일은 Sunday입니다.\n", //
			"\n ▶ 신용기 강사님과 '이것이 자바다' 저자 신용권님은 혈연관계가 아닙니다.\n", //
			"\n ▶ 오늘의 추천 빵은 바나나피자머핀입니다.\n", //
			"\n ▶ 오늘은 비가오네요. 비 오는 날에는 마라빵~!\n", //
			"\n ▶ 신용기 강사님, 이진규 매니저님에게 민트초코피자빵 만들어 드리고 싶습니다. -동철- \n", //
			"\n ▶ 숨 쉴 때마다 돈이 입금 되었으면 좋겠습니다.\n", //
			"\n ▶ 속기사님의 오타 확률보다 이 프로젝트의 에러 확률이 더 높습니다.\n", //
//			"\n\n", //
//			"\n\n", //
//			"\n\n", //
//			"\n\n", //
//			"\n\n", //
//			"\n\n", //
			"\ntip. SK는 국내 최고 기업입니다.\n" };

	@Override
	public synchronized void run() {

		try {
			// 10초 ~ 30초
			// 10001 ~ 30000
			int randomSec = (int) (Math.random() * 20000) + 10000;
			int randomMsg = (int) (Math.random() * 10);

			Thread.sleep(3000);
			System.out.println(str[randomMsg]);
			Thread.sleep(randomSec);
			ctroller.createOrder();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
