/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author adrianmj
 */
public class ActividadAlumno extends JInternalFrame implements ActionListener {

    JButton[] arrBtn;//arreglo de botones
    JButton botonEscucha = new JButton("ESCUCHAR");
    JPanel jp1;
    List<String> listaPa;
    File carpeta = new File("C:\\Users\\usuario\\Desktop\\UPS\\9no CICLO ING SISTEMAS\\INTELIGENCIA ARTIFICIAL\\Modulo1-4\\src\\main\\java\\ec\\edu\\ups\\actividades\\");
    JFrame frMain;

    public ActividadAlumno() {//constructor de la clase

        frMain = new JFrame("Actividad Alumno");
        frMain.setLayout(new BorderLayout(10, 20));
        listarFicherosPorCarpeta(carpeta);
        System.out.println("recibe " + PalabraEnseñar.txtPalabraAbuscar.getText());        
        Font fuente2 = new Font("Dialog", Font.BOLD, 14);
        botonEscucha.setFont(fuente2);
        botonEscucha.setBackground(Color.WHITE);
        botonEscucha.setSize(10, 10);
        ImageIcon icon = new ImageIcon("C:\\Users\\usuario\\Desktop\\UPS\\9no CICLO ING SISTEMAS\\INTELIGENCIA ARTIFICIAL\\Modulo1-4\\src\\main\\java\\ec\\edu\\ups\\imagenes\\escucha.jpg");
        botonEscucha.setIcon(icon);
        botonEscucha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    play("Actividad1.mp3");
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(ActividadAlumno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        frMain.add(botonEscucha, BorderLayout.CENTER);
        frMain.add(jp1, BorderLayout.SOUTH);
        frMain.setSize(800, 650);
        frMain.setLocation(300, 90);
        frMain.setVisible(true);
        frMain.setResizable(false);
        frMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Color getRandomColor() {
        Random r = new Random();
        Color randomColor = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        return randomColor;
    }

    public void play(String filePath) throws UnsupportedAudioFileException {
        String ruta = "C:\\Users\\usuario\\Desktop\\UPS\\9no CICLO ING SISTEMAS\\INTELIGENCIA ARTIFICIAL\\Modulo1-4\\src\\main\\java\\sonidosLetras\\";
        final File file = new File(ruta+filePath);

        try (final AudioInputStream in = getAudioInputStream(file)) {

            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final DataLine.Info info = new DataLine.Info(SourceDataLine.class, outFormat);

            try (final SourceDataLine line
                    = (SourceDataLine) AudioSystem.getLine(info)) {

                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }

        } catch (LineUnavailableException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();

        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }

    public void listarFicherosPorCarpeta(File carpeta) {
        int indice = 0;
        String[] ficheros2 = null;
        for (File ficheroEntrada : carpeta.listFiles()) {
            if (ficheroEntrada.isDirectory()) {
                listarFicherosPorCarpeta(ficheroEntrada);
            } else if (ficheroEntrada.isFile()) {
                indice = indice + 1;
            }
        }
        ficheros2 = new String[indice];
        arrBtn = new JButton[indice];
        int cont = 0;
        jp1 = new JPanel(new GridLayout(5, 5, 30, 30));
        jp1.setBackground(Color.WHITE);

        for (File ficheroEntrada : carpeta.listFiles()) {
            if (ficheroEntrada.isDirectory()) {
                listarFicherosPorCarpeta(ficheroEntrada);
            } else if (ficheroEntrada.isFile()) {
                String readFiles = ficheroEntrada.getName();
                if (readFiles.indexOf(".") > 0) {
                    readFiles = readFiles.substring(0, readFiles.lastIndexOf("."));
                }
                ficheros2[cont] = readFiles;
                cont += 1;
            }
        }

        for (int j = 0; j < ficheros2.length; j++) {
            // int aux =(int)(Math.random()*ficheros2.length);
            String titulo = ficheros2[j];
            arrBtn[j] = new JButton();
            jp1.add(arrBtn[j]);
            Font fuente = new Font("Dialog", Font.BOLD, 24);
            arrBtn[j].setFont(fuente);
            arrBtn[j].setPreferredSize(new Dimension(110, 80));
            arrBtn[j].setMargin(new Insets(1, 1, 1, 1));
            arrBtn[j].setText(titulo);
            arrBtn[j].setBackground(getRandomColor());
            arrBtn[j].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String recibe = PalabraEnseñar.txtPalabraAbuscar.getText();
        if (recibe.equalsIgnoreCase(e.getActionCommand())) {
            try {
                play("efec.mp3");
                ((JButton) e.getSource()).setBackground(Color.GREEN);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ActividadAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                 play("fallo.mp3");
                 ((JButton) e.getSource()).setBackground(Color.RED);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ActividadAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
