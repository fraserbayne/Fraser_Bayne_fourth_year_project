package gui.JPanels;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import model.DeBruijnWordChecker;
import model.DeBruijnWordCheckerResults;

public class DeBruijnWordCheckerJPanel extends GeneratorJPanel {

	private JTextField characterTextField;
	private JTextField wordTextField;

	public DeBruijnWordCheckerJPanel() {
		super(null);

		defaultJPanel.removeAll();

		JPanel deBruijnWordCheckerJPanel = new JPanel();
		deBruijnWordCheckerJPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel checkerOptionsJPanel = new JPanel();
		checkerOptionsJPanel.add(new JLabel("Code Length (2-10):"));

		codeLengthSpinner = new JSpinner();
		checkerOptionsJPanel.add(codeLengthSpinner);

		codeLengthSpinner.setModel(new SpinnerNumberModel(2, 2, 10, 1));

		checkerOptionsJPanel.add(new JLabel("Universal Character (Optional):"));

		characterTextField = new JTextField();
		checkerOptionsJPanel.add(characterTextField);
		characterTextField.setColumns(1);

		deBruijnWordCheckerJPanel.add(checkerOptionsJPanel);

		JPanel checkerInputJPanel = new JPanel();

		checkerInputJPanel.add(new JLabel("De Bruijn word:"));

		wordTextField = new JTextField();
		checkerInputJPanel.add(wordTextField);
		wordTextField.setColumns(40);

		deBruijnWordCheckerJPanel.add(checkerInputJPanel);
		defaultJPanel.add(deBruijnWordCheckerJPanel);
		generateDeBruijnWordButton.setText("Check De Bruijn word");
	}

	public void checkDeBruijnWord() {

		String character = characterTextField.getText();
		String word = wordTextField.getText();
		if (word.length() > 0) {
			if (character.length() > 1) {

				updateConsole("Error: Universal character must be length 1");
			} else {
				DeBruijnWordCheckerResults deBruijnWordCheckerResults;
				if (character.length() == 0) {
					deBruijnWordCheckerResults = DeBruijnWordChecker.checkDeBruijnWord(word, getCodeLength());

				} else {
					deBruijnWordCheckerResults = DeBruijnWordChecker.checkUniversalPartialWord(word, getCodeLength(),
							character.charAt(0));
				}
				ArrayList<String> missing = deBruijnWordCheckerResults.getMissing();
				ArrayList<String> duplicates = deBruijnWordCheckerResults.getDuplicates();
				if (missing.isEmpty() && duplicates.isEmpty()) {
					updateConsole("This is a valid De Bruijn word");
				} else {
					if (!missing.isEmpty()) {
						updateConsole("Missing codes: ");
						updateConsole(missing.toString());
					}

					if (!duplicates.isEmpty()) {
						updateConsole("Duplicate codes: ");
						updateConsole(duplicates.toString());
					}
				}
			}
		} else {
			updateConsole("Error: Please enter a De Bruijn word");
		}
	}

	@Override
	protected String getResult() {
		checkDeBruijnWord();
		return null;
	}
}
