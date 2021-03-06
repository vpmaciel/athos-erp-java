package erp.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

@SuppressWarnings("serial")
class SplashScreen extends JWindow {
	private int duration;

	public SplashScreen(int d) {
		duration = d;

		JPanel content = (JPanel) getContentPane();
		content.setBackground(Color.BLUE);
		int width = 450;
		int height = 115;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		JLabel labelSistema = new JLabel("ATHOS");
		labelSistema.setFont(new Font("Sans-Serif", Font.BOLD, 24));
		labelSistema.setForeground(Color.WHITE);
		content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		content.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		content.add(labelSistema, gbc);

		setVisible(true);
		try {
			Thread.sleep(duration);
		} catch (Exception e) {
		}
		setVisible(false);
	}
}