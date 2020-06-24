package gui.JPanels;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import model.Generators.Generator;

public class GeneratorJPanel extends JPanel {
	protected JSpinner alphabetSizeSpinner;
	private JCheckBox clipboardSaveCheckBox;
	protected JSpinner codeLengthSpinner;
	private JTextArea consoleTextArea;
	protected JPanel defaultJPanel;
	private JCheckBox fileSaveCheckBox;
	protected JButton generateDeBruijnWordButton;
	private Generator generator;
	private JRadioButton horizontalPrintRadioButton;
	private JLabel timeDisplayLabel;
	private JRadioButton verticalPrintRadioButton;

	public GeneratorJPanel(Generator g) {
		generator = g;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		defaultJPanel = new JPanel();
		add(defaultJPanel);
		defaultJPanel.setLayout(new GridLayout(0, 1, 0, 0));

		addDataOptionsJPanel();

		JPanel inputOptionsJPanel = new JPanel();
		defaultJPanel.add(inputOptionsJPanel);

		inputOptionsJPanel.add(new JLabel("Code Length (2-10):"));

		codeLengthSpinner = new JSpinner();
		codeLengthSpinner.setModel(new SpinnerNumberModel(2, 2, 10, 1));
		inputOptionsJPanel.add(codeLengthSpinner);
		inputOptionsJPanel.add(new JLabel("Alphabet Size (2-10):"));

		alphabetSizeSpinner = new JSpinner();
		alphabetSizeSpinner.setModel(new SpinnerNumberModel(2, 2, 10, 1));
		inputOptionsJPanel.add(alphabetSizeSpinner);

		JPanel consoleJPanel = new JPanel();
		add(consoleJPanel);
		consoleJPanel.setLayout(new BorderLayout(0, 0));

		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);
		consoleJPanel.add(consoleTextArea);

		JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea);
		consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		consoleJPanel.add(consoleScrollPane);
		JPanel consoleButtonsJPanel = new JPanel();
		add(consoleButtonsJPanel);
		consoleButtonsJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton clearConsoleButton = new JButton("Clear Console");

		clearConsoleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearConsole();
			}
		});

		generateDeBruijnWordButton = new JButton("Generate De Bruijn word");

		generateDeBruijnWordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateDeBruijnWord();
			}
		});

		consoleButtonsJPanel.add(clearConsoleButton);
		consoleButtonsJPanel.add(generateDeBruijnWordButton);
		timeDisplayLabel = new JLabel();
		timeDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeDisplayLabel.setVerticalAlignment(SwingConstants.TOP);
		consoleButtonsJPanel.add(timeDisplayLabel);
	}

	private void addDataOptionsJPanel() {
		JPanel dataOptionsJPanel = new JPanel();
		defaultJPanel.add(dataOptionsJPanel);

		fileSaveCheckBox = new JCheckBox("Save to File");
		fileSaveCheckBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		dataOptionsJPanel.add(fileSaveCheckBox);

		clipboardSaveCheckBox = new JCheckBox("Save to Clipboard");
		clipboardSaveCheckBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		dataOptionsJPanel.add(clipboardSaveCheckBox);

		dataOptionsJPanel.add(new JLabel("Print:"));

		ButtonGroup buttonGroup = new ButtonGroup();
		horizontalPrintRadioButton = new JRadioButton("Horizontal");
		horizontalPrintRadioButton.setSelected(true);
		dataOptionsJPanel.add(horizontalPrintRadioButton);

		verticalPrintRadioButton = new JRadioButton("Vertical");
		dataOptionsJPanel.add(verticalPrintRadioButton);

		buttonGroup.add(horizontalPrintRadioButton);
		buttonGroup.add(verticalPrintRadioButton);
	}

	private void clearConsole() {
		consoleTextArea.setText("");
	}

	public void generateDeBruijnWord() {
		long time = System.nanoTime();
		String result = getResult();
		updateTime(time);
		if (result != null) {
			if (clipboardSaveCheckBox.isSelected()) {
				StringSelection resultSelection = new StringSelection(result);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(resultSelection, resultSelection);

			}
			if (verticalPrintRadioButton.isSelected()) {
				char[] s = result.toCharArray();
				result = "";
				for (int i = 0; i < s.length - 1; i++) {
					if (s[i] != '\n') {
						result += s[i] + "\n";
					} else {
						result += "\n";
					}
				}
				result += s[s.length - 1];

			}

			if (fileSaveCheckBox.isSelected()) {
				PrintWriter out = null;
				try {
					out = new PrintWriter("output.txt");
				} catch (FileNotFoundException e) {
					updateConsole("Error: Could not write to file");
				}
				out.println(result);
				out.close();

			}

			updateConsole(result);

		}

	}

	protected int getAlphabetSize() {
		return (int) alphabetSizeSpinner.getValue();

	}

	protected int getCodeLength() {
		return (int) codeLengthSpinner.getValue();

	}

	protected Generator getGenerator() {
		return generator;
	}

	protected String getResult() {
		return getGenerator().generateDeBruijnWord(getAlphabetSize(), getCodeLength());
	}

	protected void updateConsole(String s) {

		consoleTextArea.setText(consoleTextArea.getText() + s + "\n");
	}

	protected void updateTime(long time) {
		timeDisplayLabel.setText("Time taken: " + (System.nanoTime() - time) / 1000000.0 + "ms");

	}

}
