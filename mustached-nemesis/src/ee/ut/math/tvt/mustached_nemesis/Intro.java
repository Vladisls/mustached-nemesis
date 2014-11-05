package ee.ut.math.tvt.mustached_nemesis;

import javax.swing.JFrame;
import org.apache.log4j.Logger;

public class Intro {
	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		final IntroUI ui = new IntroUI();
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setVisible(true);
		log.info("Intro window has been opened");
	}
}
