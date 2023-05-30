package org.example.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Склад игрушек
 */
public class Stock implements IStock{
    private HashMap<Integer, Toy> allToys; //Коллекция игрушек на складе
    private Queue<Gift> winners; //Игрушки, ожидающие вручения победителям лотереи

    public Stock() {
        this.allToys = new HashMap<>();
        this.winners = new LinkedList<>();
    }

    /**
     * @return коллекция, содержащая id игрушек и их экземпляры
     */
    public HashMap<Integer, Toy> getAllToys() {
        return allToys;
    }

    /**
     * Добавление новой игрушки
     * @param newToy новая игрушка
     */
    @Override
    public void add(Toy newToy) {
        allToys.put(newToy.getId(), newToy);
    }

    /**
     * Добавление игрушки в очередь на выдачу и уменьшение количества на остатках
     * @param win коллекция игрушек, выигранных в лотерее
     */
    @Override
    public void lottery(ArrayList<Toy> win) {
        if (win.size() > 0)
            for (Toy toy: win){
                winners.add(new Gift(toy.getId(), toy.getName()));
                toy.giftIt();
            }
    }

    /**
     * Подготовка ассортимента игрушек к записи в базу данных
     * @return строка для записи в файл
     */
    @Override
    public String allToysToWrite() {
        StringBuilder sb = new StringBuilder();
        for (Toy toy: allToys.values())
            sb.append(toy.toWrite());
        return sb.toString();
    }

    /**
     * Подготовка списка игрушек, выигранных в лотерею, для записи в базу данных
     * @return строка для записи в файл
     */
    @Override
    public String allGiftsToWrite() {
        StringBuilder sb = new StringBuilder();
        for (Gift gift: winners)
            sb.append(gift.toWrite());
        return sb.toString();
    }

    /**
     * Наполнение сипска выигранных игрушек после загрузки базы данных
     * @param gift выигранная игрушка
     */
    @Override
    public void loadWinners(Gift gift) {
        winners.add(gift);
    }

    /**
     * @return очередь на выдачу выигранных игрушек
     */
    public Queue<Gift> getWinners() {
        return winners;
    }
}
