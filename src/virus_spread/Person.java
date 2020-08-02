package virus_spread;

public class Person {
	private int state;// 0-正常 1-潜伏期 2-感染 3-隔离
	private int x;
	private int y;
	private int incubation_period;
	private int respond_time;

	public Person() {
		x = 0;
		y = 0;
		state = 0;
	}

	public Person(int ix, int iy) {
		x = ix;
		y = iy;
		state = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getState() {
		return state;
	}

	public int getPeriod() {
		return incubation_period;
	}

	public int getRespond() {
		return respond_time;
	}

	public void setX(int ix) {
		x = ix;
	}

	public void setY(int iy) {
		y = iy;
	}

	public void setState(int is) {
		state = is;
	}

	public void setPeriod(int t) {
		incubation_period = t;
	}

	public void setRespond(int t) {
		respond_time = t;
	}

	public void move(int dx, int dy) {
		x = x + dx;
		y = y + dy;
	}

}
