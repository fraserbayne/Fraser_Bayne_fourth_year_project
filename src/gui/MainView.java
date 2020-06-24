package gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import gui.JPanels.DeBruijnWordCheckerJPanel;
import gui.JPanels.GeneratorJPanel;
import gui.JPanels.GraphTheoryJPanel;
import gui.JPanels.LyndonWordsJPanel;
import gui.JPanels.MartinsAlgorithmJPanel;
import gui.JPanels.UniversalWordsJPanel;

public class MainView {

	public MainView() {
		JFrame frame;
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(870, 800);

		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		GeneratorJPanel panel = new MartinsAlgorithmJPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		tabbedPane.addTab("Martins' Algorithm", null, panel, null);

		panel = new LyndonWordsJPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		tabbedPane.addTab("Lyndon Words", null, panel, null);

		panel = new GraphTheoryJPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		tabbedPane.addTab("Graph Theory", null, panel, null);

		panel = new UniversalWordsJPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		tabbedPane.addTab("Universal partial words", null, panel, null);

		panel = new DeBruijnWordCheckerJPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		tabbedPane.addTab("De Bruijn Word Checker", null, panel, null);

		frame.setVisible(true);
	}

}
