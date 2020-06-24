package gui.JPanels;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.UniversalPartialWordsGenerator;

public class UniversalWordsJPanel extends GeneratorJPanel {

	private JTextField characterTextField;
	private JLabel indexLabel;
	private JSpinner indexSpinner;

	public UniversalWordsJPanel() {

		super(null);
		characterTextField = new JTextField("x");
		characterTextField.setColumns(1);
		indexSpinner = new JSpinner();

		characterTextField.setMaximumSize(new Dimension());
		indexSpinner = new JSpinner();

		ChangeListener SpinnerChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateIndexSpinner();
			}
		};
		codeLengthSpinner.addChangeListener(SpinnerChangeListener);
		alphabetSizeSpinner.addChangeListener(SpinnerChangeListener);

		JPanel panel = new JPanel();
		panel.add(new JLabel("Character: "));
		panel.add(characterTextField);
		indexLabel = new JLabel();

		updateIndexSpinner();
		panel.add(indexLabel);
		panel.add(indexSpinner);
		defaultJPanel.add(panel);

		generateDeBruijnWordButton.setText("Generate u-p-word(s)");

	}

	@Override
	protected String getResult() {
		String character = characterTextField.getText();
		if (character.length() != 1) {

			updateConsole("Error: Universal character must be length 1");
		} else {
			ArrayList<String> results = UniversalPartialWordsGenerator.generateUniversalPartialWords(getAlphabetSize(),
					getCodeLength(), (int) indexSpinner.getValue(), character.charAt(0));

			if (results.isEmpty()) {
				updateConsole("No valid De Bruijn word exists");
			} else {
				String result = results.get(0);
				for (int i = 1; i < results.size(); i++) {
					result += "\n" + results.get(i);
				}
				return result;
			}
		}
		return null;

	}

	public void updateIndexSpinner() {

		int max = UniversalPartialWordsGenerator.getMaxIndex(getAlphabetSize(), getCodeLength());
		indexSpinner.setValue(Math.min((int) indexSpinner.getValue(), max));
		indexSpinner.setModel(new SpinnerNumberModel((int) indexSpinner.getValue(), 0, max, 1));
		indexLabel.setText("Index: 0-" + max);

	}

}
