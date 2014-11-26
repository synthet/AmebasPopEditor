package ru.synthet.AmPopEditor;

import ru.synthet.AmPopEditor.Utils.ByteUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bragin_va on 24.11.14.
 */
public class MainForm extends JFrame {
    private JButton genButton;
    private JPanel rootPanel;
    private JTextField textField1;
    private JTable table1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JFileChooser openFile;
    private FormData formData;
    private ByteBuffer buffer;

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
        if (buffer != null)
            buffer.clear();

        if (populationFile.canRead()) {
            byte[] result = null;
            try {
                InputStream input =  new BufferedInputStream(new FileInputStream(populationFile));
                result = readAndClose(input);
                ByteBuffer buffer = ByteBuffer.wrap(result);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                ArrayList<Integer> popSizes = new ArrayList<Integer>();
                for(int i=0; i<6; i++) {
                    if (buffer.hasRemaining()) {
                        int size = buffer.getInt();
                        popSizes.add(size);
                        Main.log("Size " + i + ": " + size);
                    }
                }
                formData = new FormData(popSizes);
                setData(formData);
                popSizes.clear();

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

    public void setData(FormData data) {
        textField2.setText(data.getPopSize(0));
        textField3.setText(data.getPopSize(1));
        textField4.setText(data.getPopSize(2));
        textField5.setText(data.getPopSize(3));
        textField6.setText(data.getPopSize(4));
        textField7.setText(data.getPopSize(5));
    }


}
