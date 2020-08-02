package virus_spread;

public class main {

	public static void main(String[] args) {
		init_pl();
		new display("病毒传播");
	}

	private static void init_pl() {
		for (int i = 0; i < 400; i++) {
			pl.pList[i] = new Person();
			pl.pList[i].setState(0);
			pl.pList[i].setX((int) (Math.random() * 600 + 100));
			pl.pList[i].setY((int) (Math.random() * 400 + 100));
		}
		for (int i = 0; i < 10; i++) {
			pl.pList[(int) (Math.random() * 400)].setState(2);
		}
	}

}
