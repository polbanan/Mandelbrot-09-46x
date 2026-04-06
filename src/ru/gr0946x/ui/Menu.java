package ru.gr0946x.ui;

import javax.swing.*;

public class Menu {
    private final MainWindow window;

    public Menu(MainWindow window) {
        this.window = window;
        createMenu();
    }

    public void createMenu() {
        JMenuBar menuBar = createMenuBar();
        window.setJMenuBar(menuBar);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createFileMenu();
        JMenu editMenu = createEditMenu();
        JMenu viewMenu = createViewMenu();

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);

        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("Файл");

        JMenu fileSave = new JMenu("Сохранить как...");
        JMenuItem saveAsPNG = new JMenuItem("Файл .png");
        JMenuItem saveAsJPG = new JMenuItem("Файл .jpg");
        JMenuItem saveAsFrac = new JMenuItem("Файл .frac");

        JMenuItem openFile = new JMenuItem("Открыть...");

        fileMenu.add(fileSave);
        fileSave.add(saveAsPNG);
        fileSave.add(saveAsJPG);
        fileSave.add(saveAsFrac);

        fileMenu.add(openFile);

        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Правка");
        JMenuItem undo = new JMenuItem("Отменить [Ctrl + Z]");

        editMenu.add(undo);

        return editMenu;
    }

    private JMenu createViewMenu() {
        JMenu viewMenu  = new JMenu("Вид");
        JMenuItem juliaSet = new JMenuItem("Показать множество Жюлиа");
        JMenu setFractalFunc = new JMenu("Задать функцию построения фрактала");
        JMenu setColorScheme = new JMenu("Задать цветовую схему");

        JMenuItem fractalFuncA = new JMenuItem("Функция 1");
//        fractalFuncA.addActionListener(e -> System.out.println("test"));

        JMenuItem fractalFuncB = new JMenuItem("Функция 2");
        JMenuItem fractalFuncC = new JMenuItem("Функция 3");

        JMenuItem colorSchemeA = new JMenuItem("Схема 1");
        JMenuItem colorSchemeB = new JMenuItem("Схема 2");
        JMenuItem colorSchemeC = new JMenuItem("Схема 3");

        viewMenu.add(juliaSet);
        viewMenu.add(setFractalFunc);
        viewMenu.add(setColorScheme);

        setFractalFunc.add(fractalFuncA);
        setFractalFunc.add(fractalFuncB);
        setFractalFunc.add(fractalFuncC);

        setColorScheme.add(colorSchemeA);
        setColorScheme.add(colorSchemeB);
        setColorScheme.add(colorSchemeC);

        return viewMenu;
    }
}
