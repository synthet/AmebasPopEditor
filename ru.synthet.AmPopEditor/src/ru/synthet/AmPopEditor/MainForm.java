package ru.synthet.AmPopEditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by bragin_va on 24.11.14.
 */
public class MainForm extends JFrame {
    private JButton genButton;
    private JPanel rootPanel;
    private JTextField textField1;
    private JTable table1;
    private JProgressBar progressBar1;
    JFileChooser openFile;

    public MainForm() {
        super("Synth.AmPopEditor");

        openFile = new JFileChooser();
        openFile.addChoosableFileFilter(new FileNameExtensionFilter("amebaspopulations.dat","dat"));

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        //setAlwaysOnTop(true);
        setMinimumSize(new Dimension(512, 256));

        genButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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


        if (populationFile.canRead()) {
            byte[] result = null;
            try {
                InputStream input =  new BufferedInputStream(new FileInputStream(populationFile));
                result = readAndClose(input);
                Main.log(result[0]);
            }
            catch (FileNotFoundException ex){
                Main.log(ex);
            }

            textField1.setText(populationFile.getName()+" Length: "+ result.length);
        }

    }

    /**
     Read an input stream, and return it as a byte array.
     Sometimes the source of bytes is an input stream instead of a file.
     This implementation closes aInput after it's read.
     */
    byte[] readAndClose(InputStream aInput){
        //carries the data from input to output :

        byte[] bucket = new byte[32*1024];
        ByteArrayOutputStream result = null;
        try  {
            try {
                //Use buffering? No. Buffering avoids costly access to disk or network;
                //buffering to an in-memory stream makes no sense.
                result = new ByteArrayOutputStream(bucket.length);
                int bytesRead = 0;
                while(bytesRead != -1){
                    //aInput.read() returns -1, 0, or more :
                    bytesRead = aInput.read(bucket);
                    if(bytesRead > 0){
                        result.write(bucket, 0, bytesRead);
                        Main.log("."+bytesRead);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                aInput.close();
                //result.close(); this is a no-operation for ByteArrayOutputStream
            }
        }
        catch (IOException ex){
            Main.log(ex);
        }
        return result.toByteArray();
    }

}
