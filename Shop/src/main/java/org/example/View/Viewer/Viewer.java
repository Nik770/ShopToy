package org.example.View.Viewer;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Класс для обмена сообщениями между программой и пользователем
 */
public class Viewer implements IPrompt, IShow {
    private StringBuilder sb;
    private String[] view;

    public Viewer() {
        this.sb = new StringBuilder();
        this.view = allView();
    }

    /**
     * @return массив со страницами меню
     */
    private String[] allView() {
        String[] positionsView = {menu(), positions(), newItem(), amount(), probability(),
                lottery(), takePrize(), exit()};
        return positionsView;
    }

    /**
     * @return Стартовое меню
     */
    private String menu() {
        sb.append("\nГлавное меню: \n");
        sb.append("1.Посмотреть список позиций\n");
        sb.append("2.Добавить позицию\n");
        sb.append("3.Изменить количество товара\n");
        sb.append("4.Изменить вес (частоту выпадения)\n");
        sb.append("5.Запустить розыгрыш\n");
        sb.append("6.Выдать приз\n");
        sb.append("9.Выйти\n");
        return sb.toString();
    }

    /**
     * @return Шапка таблицы игрушек
     */
    private String positions() {
        return "\nАссортимент игрушек в магазине:\n" +
                "id\t\tНазвание\t\t\tКоличество\t\tВероятность выигрыша";
    }

    /**
     * @return Информация о добавлении новой игрушки
     */
    private String newItem() {
        return "\nДобавление новой игрушки в каталог товаров\n";
    }

    /**
     * @return Информация о внесении изменения вероятности выигрыша
     */
    private String probability() {
        return "\nИзменение вероятности выигрыша\n";
    }

    /**
     * @return Информация о внесении изменения количества игрушек
     */
    private String amount() {
        return "\nИзменение количества товара на складе\n";
    }

    /**
     * @return Информация о начале розыгрыша
     */
    private String lottery() {
        return "\nРозыгрыш игрушек";
    }

    /**
     * @return Шапка для таблицы игрушек на выдачу
     */
    private String takePrize() {
        return "\nВыдача призов\n\nОчередб на выдачу:";
    }

    /**
     * @return Прощальный текст
     */
    private String exit() {
        return "\nДо новых встреч!";
    }

    /**
     * Вывод текста на экран
     *
     * @param text сообщение, выводимое на экран
     */
    @Override
    public void show(String text) {
        System.out.println(text);
    }

    /**
     * Сообщение об ошибке ввода
     */
    @Override
    public void inputError() {
        System.out.println("\nОшибка ввода");
    }

    /**
     * Сообщение о внесении изменений
     */
    @Override
    public void changeComplete() {
        System.out.println("\nИзменения внесены");
    }

    /**
     * Печать таблицы с ассортиментом
     * @param data данные по всем игрушкам
     */
    @Override
    public void table(LinkedList<String> data) {
        StringBuilder sb = new StringBuilder();
        int size = data.size();
        String[][] table = new String[size][4];
        for (int i = 0; i < size; i++) {
            table[i] = data.poll().split("sepor");
            switch (table[i][0].length()) {
                case 1:
                    table[i][0] = "00" + table[i][0];
                    break;
                case 2:
                    table[i][0] = "0" + table[i][0];
                    break;
            }
            sb.append(table[i][0]).append("\t\t").append(table[i][1]);
            for (int j = table[i][1].length(); j < 21; j++)
                sb.append(' ');
            sb.append('\t').append(table[i][2]);
            for (int j = table[i][2].length(); j < 16; j++)
                sb.append(' ');
            sb.append('\t').append(table[i][3]).append('\n');
        }
        show(sb.toString());
    }

    /**
     * Ввод целочисленных данных
     * @param text приглашение ко вводу
     * @return Целочисленное значение
     */
    @Override
    public Integer inputInt(String text) {
        System.out.print(text);
        try (Scanner scan = new Scanner(System.in)) {
            return scan.nextInt();
        }
    }

    /**
     * Ввод строковых данных
     * @param text приглашение ко вводу
     * @return Строка текста
     */
    @Override
    public String inputStr(String text) {
        System.out.print(text);
        try (Scanner scan = new Scanner(System.in)) {
            return scan.nextLine();
        }
    }

    /**
     * Массив со страницами меню
     * @return Массив со страницами меню
     */
    public String[] getView() {
        return view;
    }
}