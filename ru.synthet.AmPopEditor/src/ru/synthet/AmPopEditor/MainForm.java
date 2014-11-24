package ru.synthet.AmPopEditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by bragin_va on 24.11.14.
 */
public class MainForm extends JFrame {
    private JButton genButton;
    private JPanel rootPanel;
    private JTextField textField1;
    private JTable table1;

    public MainForm() {
        super("Synth.AmPopEditor");


        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        //setAlwaysOnTop(true);
        setMinimumSize(new Dimension(512, 256));

        genButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser openFile = new JFileChooser();
                openFile.addChoosableFileFilter(new FileNameExtensionFilter("amebaspopulations.dat","dat"));
                int ret = openFile.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    openPopulation(openFile.getSelectedFile());


                }
            }
        });

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click();
            }
        });

    }

    private void click() {
        textField1.setText(this.getTitle());

    }

    private void openPopulation(File populationFile) {
        textField1.setText(populationFile.getName()+" Length: "+populationFile.length());

    }

}
