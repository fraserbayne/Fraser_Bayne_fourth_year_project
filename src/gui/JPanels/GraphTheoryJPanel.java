package gui.JPanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Generators.GraphTheory.GraphTheory;

public class GraphTheoryJPanel extends GeneratorJPanel {

	private JButton addButton;
	private DefaultListModel<String> listModel;
	private JRadioButton prohibitedCodeRadioButton;
	private JTextField prohibitedCodeTextField;
	private JButton removeButton;
	private JRadioButton startCodeRadioButton;
	private JTextField startCodeTextField;

	public GraphTheoryJPanel() {

		super(new GraphTheory());

		JPanel graphTheoryOptionsJPanel = new JPanel();
		graphTheoryOptionsJPanel.setLayout(new GridLayout(1, 3));
		defaultJPanel.setLayout(new GridLayout(3, 1));

		ChangeListener spinnerListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				clearList();
			}
		};

		alphabetSizeSpinner.addChangeListener(spinnerListener);
		codeLengthSpinner.addChangeListener(spinnerListener);
		codeLengthSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateStartCodeLength();
				updateProhibitedCodeLength();
			}
		});

		defaultJPanel.add(graphTheoryOptionsJPanel);

		JPanel startCodeJPanel = new JPanel();
		startCodeJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		graphTheoryOptionsJPanel.add(startCodeJPanel);

		startCodeRadioButton = new JRadioButton("Start code (Length: 1) (Optional)");
		startCodeJPanel.add(startCodeRadioButton);
		startCodeTextField = new JTextField();
		startCodeJPanel.add(startCodeTextField);
		startCodeTextField.setColumns(10);

		startCodeRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (startCodeRadioButton.isSelected()) {
					prohibitedCodeRadioButton.setSelected(false);
				}
			}
		});

		JPanel prohibitedCodesInputJPanel = new JPanel();
		graphTheoryOptionsJPanel.add(prohibitedCodesInputJPanel);
		prohibitedCodesInputJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		prohibitedCodeRadioButton = new JRadioButton("Prohibited code(s): (Length: 2)");
		prohibitedCodeRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (prohibitedCodeRadioButton.isSelected()) {
					startCodeRadioButton.setSelected(false);
				}
			}
		});

		prohibitedCodesInputJPanel.add(prohibitedCodeRadioButton);

		prohibitedCodeTextField = new JTextField();
		prohibitedCodesInputJPanel.add(prohibitedCodeTextField);
		prohibitedCodeTextField.setColumns(10);

		JPanel prohbitedCodesOptionsJPanel = new JPanel();
		graphTheoryOptionsJPanel.add(prohbitedCodesOptionsJPanel);

		listModel = new DefaultListModel<>();
		prohbitedCodesOptionsJPanel.setLayout(new GridLayout(1, 1, 0, 0));

		JPanel prohibitedCodesButtonsJPanel = new JPanel();
		prohbitedCodesOptionsJPanel.add(prohibitedCodesButtonsJPanel);
		prohibitedCodesButtonsJPanel.setLayout(new GridLayout(0, 1, 0, 0));

		addButton = new JButton("Add");
		prohibitedCodesButtonsJPanel.add(addButton);

		removeButton = new JButton("Remove");
		prohibitedCodesButtonsJPanel.add(removeButton);
		removeButton.setEnabled(false);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String code = prohibitedCodeTextField.getText();

				if (code.length() != getCodeLength()) {
					updateConsole("Error: Code must be length " + getCodeLength());

				} else {
					ArrayList<Character> invalidCharacters = inAlphabet(code, getAlphabetSize());
					if (!invalidCharacters.isEmpty()) {
						updateConsole("Error: Character(s) not in alphabet: " + invalidCharacters.toString());

					} else {
						if (!listModel.contains(code)) {
							listModel.addElement(code);
							prohibitedCodeTextField.setText("");
						} else {
							updateConsole("Error: Code already in list");
						}
					}
				}

			}

		});

		JList<String> list = new JList(listModel);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int modelSize = listModel.getSize();
				listModel.remove(list.getSelectedIndex());
				if (modelSize > 0) {
					list.setSelectedIndex(modelSize - 2);
				} else {
					removeButton.setEnabled(false);
				}
			}
		});

		JPanel prohibitedCodesListJPanel = new JPanel();
		prohbitedCodesOptionsJPanel.add(prohibitedCodesListJPanel);

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(list);
		prohibitedCodesListJPanel.add(listScroller);
		listScroller.setPreferredSize(new Dimension(100, 40));
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedIndex() >= 0) {
					removeButton.setEnabled(true);
				} else {
					removeButton.setEnabled(false);
				}
			}
		});

		updateProhibitedCodeLength();
		updateStartCodeLength();

	}

	private void clearList() {
		listModel.clear();
		removeButton.setEnabled(false);

	}

	@Override
	protected String getResult() {

		if (startCodeRadioButton.isSelected()) {
			String startCode = getStartCode();

			if (startCode.length() != getCodeLength() - 1) {

				updateConsole("Error: Start code must be of length " + (getCodeLength() - 1));
				return null;
			} else {
				ArrayList<Character> invalidCharacters = inAlphabet(startCode, getAlphabetSize());
				if (invalidCharacters.isEmpty()) {
					String result = ((GraphTheory) getGenerator()).generateDeBruijnWord(getAlphabetSize(),
							getCodeLength(), startCode);
					if (result != null) {
						return result;
					}
				} else {
					updateConsole(
							"Error: Start code contains character(s) not in alphabet: " + invalidCharacters.toString());

					return null;
				}
			}
		} else if (prohibitedCodeRadioButton.isSelected() && !listModel.isEmpty()) {

			String result = ((GraphTheory) getGenerator()).generateDeBruijnWord(getAlphabetSize(), getCodeLength(),
					Collections.list(listModel.elements()));
			if (result != null) {
				return result;
			}
		} else {
			return getGenerator().generateDeBruijnWord(getAlphabetSize(), getCodeLength());
		}

		updateConsole("Error: no valid De Bruijn word found");
		return null;
	}

	private String getStartCode() {
		return startCodeTextField.getText();
	}

	private ArrayList<Character> inAlphabet(String code, int alphabetSize) {
		ArrayList<Character> invalidCharacters = new ArrayList<Character>();
		for (int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			try {
				if (Integer.parseInt(c + "") > getAlphabetSize() - 1) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException ex) {
				if (!invalidCharacters.contains(c)) {
					invalidCharacters.add(c);
				}
			}

		}
		return invalidCharacters;
	}

	private void updateProhibitedCodeLength() {
		prohibitedCodeRadioButton.setText("Prohibited code(s): (Length: " + getCodeLength() + ")");
	}

	private void updateStartCodeLength() {
		startCodeRadioButton.setText("Start code (Length: " + (getCodeLength() - 1) + ") (Optional)");
	}
}
