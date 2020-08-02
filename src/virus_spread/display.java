package virus_spread;

import javax.swing.*;

//import transmission_of_virus.main.MyThread;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class display extends JFrame {

	public display(String title) {

		super(title);
		setBackground(Color.black);
		getContentPane().setBackground(new Color(0x444444));
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MyThread my = new MyThread();
		my.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.GREEN);
		g.drawRect(100, 100, 600, 400);

		g.setFont(new Font("楷体", Font.BOLD, 15));
		g.setColor(Color.WHITE);
		g.drawString("城市总人数：" + 400, 750, 100);

		g.setColor(new Color(0xdddddd));
		g.drawString("健康者人数：" + Integer.toString(400 - pl.infected_num - pl.incubation_num), 750, 150);

		g.setColor(new Color(0xffee00));
		g.drawString("潜伏期人数：" + Integer.toString(pl.incubation_num), 750, 200);

		g.setColor(new Color(0xff0000));
		g.drawString("发病者人数：" + Integer.toString(pl.infected_num), 750, 250);

		g.setColor(new Color(0x48FFFC));
		g.drawString("已隔离人数：" + Integer.toString(pl.quarantine_num), 750, 300);

		g.setColor(new Color(0xffffff));
		g.drawString("时间：" + Time, 750, 350);

		for (Person person : pl.pList) {
			switch (person.getState()) {

			case 0: {// 健康者
				g.setColor(new Color(0xdddddd));
				break;
			}
			case 1: {// 潜伏期
				g.setColor(new Color(0xffee00));
				break;
			}
			case 2: {// 发病
				g.setColor(new Color(0xff0000));
				break;
			}
			case 3: {// 隔离
				g.setColor(new Color(0x48FFFC));
				break;
			}
			}
			g.fillOval(person.getX(), person.getY(), 4, 4);
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		repaint();
	}

	public static int Time = 0;// 天数

	public Timer timer = new Timer();

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			Time++;
			System.out.println(Time + " " + pl.incubation_num + " " + pl.infected_num + " " + pl.quarantine_num);
			for (Person p : pl.pList) {
				if (p.getPeriod() == 5 && p.getState() == 1) {
					p.setState(2);
					pl.infected_num++;
					pl.incubation_num--;
				} else if (p.getPeriod() < 5 && p.getState() == 1) {
					p.setPeriod(p.getPeriod() + 1);
				} else if (p.getRespond() == 5 && p.getState() == 2) {
					p.setState(3);
					pl.quarantine_num++;
				} else if (p.getRespond() < 5 && p.getState() == 2) {
					p.setRespond(p.getRespond() + 1);
				}
			} // 遍历结束
		}// run结束
	}// 定时任务结束

	class MyThread extends Thread {
		public void run() {
			timer.schedule(new MyTimerTask(), 0, 1000);
			while (true) {
				for (int i = 0; i < 400; i++) {// 每一次每个人员随机流动
					int x = (int) (Math.random() * 10 - 5);
					int y = (int) (Math.random() * 10 - 5);
					while (pl.pList[i].getX() + x < 100 || pl.pList[i].getX() + x > 700 || pl.pList[i].getY() + y < 100
							|| pl.pList[i].getY() + y > 500) {
						x = (int) (Math.random() * 10 - 5);
						y = (int) (Math.random() * 10 - 5);
					}
					pl.pList[i].move(x, y);
				} // 人员流动结束

				for (int i = 0; i < 400; i++) {// 每次流动后感染情况发生变化
					if (pl.pList[i].getState() == 2) {// 发病者感染健康者
						for (int j = 0; j < 400; j++) {
							if (pl.pList[j].getState() == 0 && pl.pList[j].getX() >= pl.pList[i].getX() - 10
									&& pl.pList[j].getX() <= pl.pList[i].getX() + 10
									&& pl.pList[j].getY() >= pl.pList[i].getY() - 10
									&& pl.pList[j].getY() <= pl.pList[i].getY() + 10) {
								if ((int) (Math.random() * 100) > 50) {
									pl.incubation_num++;
									pl.pList[j].setState(1);
								}
							}
						}
					} else if (pl.pList[i].getState() == 1) {// 潜伏者感染健康者
						for (int j = 0; j < 400; j++) {
							if (pl.pList[j].getState() == 0 && pl.pList[j].getX() >= pl.pList[i].getX() - 10
									&& pl.pList[j].getX() <= pl.pList[i].getX() + 10
									&& pl.pList[j].getY() >= pl.pList[i].getY() - 10
									&& pl.pList[j].getY() <= pl.pList[i].getY() + 10) {
								if ((int) (Math.random() * 100) > 70) {
									pl.incubation_num++;
									pl.pList[j].setState(1);
								}
							}
						}
					}
				} // 感染情况变化结束

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} // while循环结束
		}// run结束
	}// 线程结束

}
