package ru.gr0946x.ui;

import javax.swing.*;

public class Menu extends JMenuBar {
    private final SelectablePanel mainPanel;

    public Menu(SelectablePanel mainPanel) {
        this.mainPanel = mainPanel;
        createMenu();
    }

    public void createMenu() {
        JMenu menu = new JMenu("Меню");

        JMenuItem fileMenu = new JMenuItem("Файл");

        JMenuItem fileSave = new JMenuItem("Сохранить как...");

        JMenuItem saveAsPNG = new JMenuItem("Файл png");
        JMenuItem saveAsJPG = new JMenuItem("Файл jpg");
        JMenuItem saveAsFrac = new JMenuItem("Файл frac");

        JMenuItem openFile = new JMenuItem("Открыть...");

        JMenuItem undo = new JMenuItem("Отменить [Ctrl + Z]");

        JMenuItem juliaSet = new JMenuItem("Сохранить в png...");

        JMenuItem changeFractalFunc = new JMenuItem("Задать функцию построения фрактала...");

        JMenuItem changeColorize = new JMenuItem("Задать цветовую схему...");

        menu.add(fileMenu);
        menu.addSeparator();
        menu.add(fileSave);
        menu.add(saveAsPNG);
        menu.add(saveAsJPG);
        menu.add(saveAsFrac);
        menu.add(openFile);
        menu.add(undo);
        menu.add(juliaSet);
        menu.add(changeFractalFunc);
        menu.add(changeColorize);

        add(menu);
    }
}
