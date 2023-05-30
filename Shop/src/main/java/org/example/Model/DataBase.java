package org.example.Model;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

/**
 * База данных
 */
public class DataBase implements IDB {
    Logger logger;
    FileHandler fh;
    XMLFormatter xml;
    Stock stock;

    /**
     * @param stock экземпляр склада для наполнения
     * @throws IOException
     */
    public DataBase(Stock stock) throws IOException {
        this.stock = stock;
        loggerInit();
    }

    /**
     * Записать в базу данных ассортимент игрушек
     * @return возвращает сам себя
     */
    @Override
    public DataBase writeToys() {
        try {
            try (BufferedWriter bwt = new BufferedWriter(new FileWriter("toys.csv"))) {
                bwt.write(String.format(stock.allToysToWrite()));
                bwt.flush();
            }
        } catch (IOException writeFile) {
            logger.log(Level.WARNING, "Ошибка записи файла");
        }
        return this;
    }

    /**
     * Считать из файла данные об ассортименте игрушек
     * @return возвращает сам себя
     */
    @Override
    public DataBase readToys() throws FileNotFoundException {
        BufferedReader brt = new BufferedReader(new FileReader("toys.csv"));
        try {
            String line;
            while ((line = brt.readLine()) != null)
                stock.add(parsToys(line));
        } catch (IOException readFile) {
            logger.log(Level.WARNING, "Ошибка чтения файла");
        }
        return this;
    }

    /**
     * Записать в базу данных список игрушек, выигранных в лотерее
     * @return возвращает сам себя
     */
    @Override
    public DataBase writeGifts() {
        try {
            try (BufferedWriter bwg = new BufferedWriter(new FileWriter("gifts.csv"))) {
                bwg.write(String.format(stock.allGiftsToWrite()));
                bwg.flush();
            }
        } catch (IOException writeFile) {
            logger.log(Level.WARNING, "Ошибка записи файла");
        }
        return this;
    }

    /**
     * Считать из файла данные об игрушках, выигранных в лотерею
     * @return возвращает сам себя
     */
    @Override
    public DataBase readGifts() throws FileNotFoundException {
        BufferedReader brg = new BufferedReader(new FileReader("gifts.csv"));
        try {
            String line;
            while ((line = brg.readLine()) != null)
                stock.loadWinners(parsGift(line));
        } catch (IOException readFile) {
            logger.log(Level.WARNING, "Ошибка чтения файла");
        }
        return this;
    }

    /**
     * Преобразование данных об игрушках перед загрузкой информации на склад
     * @param line строка текста из файла
     * @return экземпляр игрушки
     */
    private Toy parsToys(String line) {
        try {
            String[] values = line.split(";");
            int id = Integer.parseInt(values[0]);
            String name = values[1];
            Integer amount = Integer.parseInt(values[2]);
            Float probability = Float.parseFloat(values[3]);
            return new Toy(id, name, amount, probability);
        } catch (Exception pars) {
            logger.log(Level.WARNING, "Ошибка преобразования данных");
            return null;
        }
    }

    /**
     * Преобразование данных о выигранных игрушках перед загрузкой информации на склад
     * @param line строка текста из файла
     * @return экземпляр выигранной игрушки
     */
    private Gift parsGift(String line) {
        try {
            String[] values = line.split(";");
            int id = Integer.parseInt(values[0]);
            String name = values[1];
            return new Gift(id, name);
        } catch (Exception pars) {
            logger.log(Level.WARNING, "Ошибка преобразования данных");
            return null;
        }
    }

    /**
     * Инициализация логера
     * @throws IOException
     */
    private void loggerInit() throws IOException {
        this.logger = Logger.getLogger(DataBase.class.getName());
        this.fh = new FileHandler("logs.xml");
        logger.addHandler(fh);
        this.xml = new XMLFormatter();
        fh.setFormatter(xml);
    }

    /**
     * @return логер
     */
    public Logger getLogger() {
        return logger;
    }
}
