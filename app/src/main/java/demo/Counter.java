package demo;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Counter extends JFrame {
    private int value = 0;

    private JTextField display;
    private JButton incrementButton;

    public Counter() {
        setLayout(new FlowLayout());
        display = new JTextField(4);
        display.setEditable(false);
        incrementButton = new JButton("+");
        incrementButton.addActionListener(e -> increment());
        add(display);
        add(incrementButton);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateDisplay();
    }

    private void increment() {
        value++;
        updateDisplay();
    }

    private void updateDisplay() {
        display.setText(Integer.toString(value));
    }
}
