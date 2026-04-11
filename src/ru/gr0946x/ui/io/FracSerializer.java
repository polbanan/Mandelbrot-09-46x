package ru.gr0946x.ui.io;

import ru.gr0946x.Converter;
import ru.gr0946x.ui.fractals.FractalState;
import ru.gr0946x.ui.fractals.Mandelbrot;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class FracSerializer implements FractalSerializer {

    @Override
    public void save(Component parent, Converter conv, Mandelbrot mandelbrot) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Файлы фракталов (*.frac)", "frac"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".frac")) {
                file = new File(file.getParentFile(), file.getName() + ".frac");
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                FractalState state = new FractalState(
                        conv.getXMin(),
                        conv.getXMax(),
                        conv.getYMin(),
                        conv.getYMax(),
                        mandelbrot.getMaxIterations()
                );
                oos.writeObject(state);
                JOptionPane.showMessageDialog(parent, "Фрактал успешно сохранен!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Ошибка сохранения: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void open(Component parent, Converter conv, Mandelbrot mandelbrot, Runnable onSuccess) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Файлы фракталов (*.frac)", "frac"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                FractalState state = (FractalState) ois.readObject();

                conv.setXShape(state.xMin(), state.xMax());
                conv.setYShape(state.yMin(), state.yMax());
                mandelbrot.setMaxIterations(state.maxIterations());

                onSuccess.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Ошибка загрузки файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void saveWithFormatChoice(Component parent, Converter conv, Mandelbrot mandelbrot, JPanel paintPanel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить фрактал");

        FileNameExtensionFilter fracFilter = new FileNameExtensionFilter("Файлы фракталов (*.frac)", "frac");
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG изображения (*.png)", "png");
        FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPEG изображения (*.jpg)", "jpg");

        fileChooser.addChoosableFileFilter(fracFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.setFileFilter(fracFilter);

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
            String extension = selectedFilter.getExtensions()[0];

            if (extension.equals("frac")) {
                if (!file.getName().toLowerCase().endsWith(".frac")) {
                    file = new File(file.getParentFile(), file.getName() + ".frac");
                }
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    FractalState state = new FractalState(
                            conv.getXMin(),
                            conv.getXMax(),
                            conv.getYMin(),
                            conv.getYMax(),
                            mandelbrot.getMaxIterations()
                    );
                    oos.writeObject(state);
                    JOptionPane.showMessageDialog(parent, "Фрактал сохранён в .frac!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "Ошибка сохранения: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else if (extension.equals("png")) {
                saveAsImage(parent, conv, paintPanel, file, "png");
            } else if (extension.equals("jpg")) {
                saveAsImage(parent, conv, paintPanel, file, "jpg");
            }
        }
    }

    private void saveAsImage(Component parent, Converter conv, JPanel paintPanel, File file, String format) {
        String path = file.getAbsolutePath();

        if (!path.toLowerCase().endsWith("." + format)) {
            path += "." + format;
            file = new File(path);
        }

        try {
            BufferedImage image = new BufferedImage(
                    paintPanel.getWidth(),
                    paintPanel.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2d = image.createGraphics();
            paintPanel.paint(g2d);

            g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
            String coords = String.format("Re: [%.5f, %.5f]  Im: [%.5f, %.5f]",
                    conv.getXMin(), conv.getXMax(),
                    conv.getYMin(), conv.getYMax());

            g2d.setColor(Color.BLACK);
            g2d.drawString(coords, 11, 21);
            g2d.setColor(Color.WHITE);
            g2d.drawString(coords, 10, 20);

            g2d.dispose();

            ImageIO.write(image, format.toUpperCase(), file);

            JOptionPane.showMessageDialog(parent,
                    "Фрактал сохранён в " + format.toUpperCase() + "!\n" + path,
                    "Успех", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Ошибка сохранения: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}