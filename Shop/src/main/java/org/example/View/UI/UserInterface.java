package org.example.View.UI;

import org.example.Model.Gift;
import org.example.Model.Toy;
import org.example.View.Viewer.Viewer;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ввод команд пользователем
 */
public class UserInterface implements IUserAction, IUserInfo {
    Logger logger;
    private final Viewer viewer;
    private final String[] listsMenu;

    /**
     * @param logger модуль логирования
     */
    public UserInterface(Logger logger) {
        this.logger = logger;
        this.viewer = new Viewer();
        this.listsMenu = viewer.getView();
    }

    /**
     * Показ страницы меню
     * @param index страница меню
     */
    @Override
    public void showMenu(int index) {
        viewer.show(listsMenu[index]);
    }

    /**
     * Логирование и отправка пользователю сообщения о добавлении новой позиции
     */
    @Override
    public void newItem() {
        logger.info("Позиция добавлена");
        viewer.show("\nПозиция добавлена");
    }

    /**
     * Выбор страницы меню
     * @return индекс страницы меню
     */
    @Override
    public Integer choiceMenu() {
        Integer index = 0;
        try {
            index = viewer.inputInt("Выберите пункт меню: ");
        } catch (Exception choiceMenu) {
            logger.log(Level.WARNING, "Ошибка ввода данных");
            viewer.inputError();
        }
        if (index == 9)
            index = 7;
        return index;
    }

    /**
     * Показ ассортимента игрушек
     * @param allToys коллекция, содержащая id игрушек и их экземпляры
     */
    @Override
    public void showPositions(HashMap<Integer, Toy> allToys) {
        LinkedList<String> data = new LinkedList<>();
        for (Map.Entry<Integer, Toy> toy : allToys.entrySet()) {
            data.add(toy.getValue().toString());
        }
        viewer.table(data);
        if (allToys.size() == 0) viewer.show("\nСписок товаров пуст\n");
    }

    /**
     * Ввод данных для новой позиции
     * @return экземпляр игрушки
     * @throws InterruptedException
     */
    @Override
    public Toy addItem() throws InterruptedException {
        try {
            String name = viewer.inputStr("Название игрушки: ");
            return new Toy(name, enterAmount(), enterProbability());
        } catch (Exception addItem) {
            logger.log(Level.WARNING, "Ошибка ввода данных");
            viewer.inputError();
            TimeUnit.SECONDS.sleep(1);
            return null;
        }
    }

    /**
     * Внесение изменений в количество игрушек
     * @param allToys коллекция, содержащая id игрушек и их экземпляры
     * @throws InterruptedException
     */
    @Override
    public void amount(HashMap<Integer, Toy> allToys) throws InterruptedException {
        Integer findId = findId(allToys);
        Integer newAmount = enterAmount();
        allToys.get(findId).setAmount(newAmount);
        logger.info("Изменение количества игрушек");
        viewer.changeComplete();
    }

    /**
     * Внесение изменений в вероятность выигрыша
     * @param allToys коллекция, содержащая id игрушек и их экземпляры
     * @throws InterruptedException
     */
    @Override
    public void probability(HashMap<Integer, Toy> allToys) throws InterruptedException {
        Integer findId = findId(allToys);
        Float newProbability = enterProbability();
        allToys.get(findId).setProbability(newProbability);
        logger.info("Изменение вероятности выигрыша");
        viewer.changeComplete();
    }

    /**
     * Запуск лотереии
     * @param allToys коллекция, содержащая id игрушек и их экземпляры
     * @return коллекция игрушек, выигранных в лотерее
     */
    @Override
    public ArrayList<Toy> lottery(HashMap<Integer, Toy> allToys) {
        Random rand = new Random();
        ArrayList<Toy> winners = new ArrayList<>();
        for (Toy toy : allToys.values())
            if (rand.nextFloat() <= toy.getProbability())
                winners.add(toy);
        if (winners.size() > 0) {
            for (Toy toy : winners) {
                logger.info("Приз разыгран");
                viewer.show(String.format("\nРозыгран приз: %s", toy.getName()));
            }
        } else {
            logger.info("Нет выигрыша");
            viewer.show("\nПобедило казино... магазин");
        }
        pressEnter();
        return winners;
    }

    /**
     * Выдача призов
     * @param gift очередь с игрушками на выдачу
     */
    @Override
    public void takePrize(Queue<Gift> gift) {
        if (gift.size() > 0) {
            for (Gift next : gift)
                viewer.show(next.toString());
            String choice;
            do {
                choice = viewer.inputStr("\nВыдать игрушку (y/n)?) ");
                if (choice.equals("y")) {
                    Gift toy = gift.poll();
                    logger.info("Игрушка выдана");
                    viewer.show(String.format("\n%s выдано",
                            toy.toString()));
                    writeFile(toy);
                }
                if (choice.equals("n"))
                    viewer.show("\nНе выдано");
                if (!choice.equals("y") && !choice.equals("n"))
                    viewer.show("\n Введите буквы 'y' или 'n'" );
            } while (!choice.equals("n"));
        }
        else viewer.show("\nВыдавать нечего");
    }

    /**
     * Остановка цикла с ожиданием ввода клавиши
     */
    @Override
    public void pressEnter() {
        viewer.inputStr("\nНажмите Enter для выхода ");
    }

    /**
     * Логирование и информирование об ошибке ввода данных
     */
    @Override
    public void error() {
        logger.log(Level.WARNING, "Ошибка ввода данных");
        viewer.inputError();
    }

    /**
     * Логирование и информирование об ошибке создания новой игрушки
     */
    @Override
    public void fail() {
        logger.log(Level.WARNING, "Ошибка при создании новой позиции");
        viewer.show("\nПозиция не создана");
    }

    /**
     * Проверка id
     * @param checkingId проверяемый id
     * @param toysId список id всех игрушек
     * @return результат поиска совпадений
     */
    private boolean checkId(Integer checkingId, Set<Integer> toysId) {
        for (Integer id : toysId) {
            if (checkingId.equals(id))
                return true;
        }
        return false;
    }

    /**
     * Ввод и обработка id игрушки
     * @param allId список всех id
     * @return валидный id
     * @throws InterruptedException
     */
    private Integer findById(Set<Integer> allId) throws InterruptedException {
        Integer findId = -1;
        boolean check = true;
        do {
            try {
                findId = viewer.inputInt("\nУкажите id игрушки: ");
                if (findId < 0) {
                    viewer.show("\nid не может быть меньше 0");
                    check = false;
                }
                if (!checkId(findId, allId)) {
                    viewer.show("\nid не найден");
                    check = false;
                }
            } catch (Exception amount) {
                logger.log(Level.WARNING, "Ошибка ввода данных");
                viewer.inputError();
                TimeUnit.SECONDS.sleep(1);
            }
        } while (!check);
        return findId;
    }

    /**
     * Ввод и обработка количества игрушек
     * @return валидное количество
     * @throws InterruptedException
     */
    private Integer enterAmount() throws InterruptedException {
        Integer amount = -1;
        do {
            try {
                amount = viewer.inputInt("Объём партии: ");
                if (amount < 0) viewer.show("Объём не может быть меньше 0");
            } catch (Exception enterAmount) {
                logger.log(Level.WARNING, "Ошибка ввода данных");
                viewer.inputError();
                TimeUnit.SECONDS.sleep(1);
            }
        } while (amount < 0);
        return amount;
    }

    /**
     * Ввод и обработка вероятности выигрыша
     * @return валидная вероятность
     * @throws InterruptedException
     */
    private Float enterProbability() throws InterruptedException {
        Integer probability = -1;
        do {
            try {
                probability = viewer.inputInt("Вероятность выигрыша в лотерее (0-100%): ");
                if (probability < 0) viewer.show("Вероятность не может быть меньше 0");
                if (probability > 100) viewer.show("Вероятность не может быть больше 100");
            } catch (Exception enterProbability) {
                logger.log(Level.WARNING, "Ошибка ввода данных");
                viewer.inputError();
                TimeUnit.SECONDS.sleep(1);
            }
        } while (probability < 0 || probability > 100);
        return probability.floatValue() / 100;
    }

    /**
     * Модуль вывода позиций перед запросом id
     * @param allToys коллекция, содержащая id игрушек и их экземпляры
     * @return id всех игрушек
     * @throws InterruptedException
     */
    private Integer findId(HashMap<Integer, Toy> allToys) throws InterruptedException {
        showPositions(allToys);
        return findById(allToys.keySet());
    }

    /**
     * Запись в файл информации о выдачи игрушки победителю лотереи
     * @param gift
     */
    private void writeFile(Gift gift) {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("win.txt", true))) {
                bw.append(String.format("Выдан %s", gift.toString())).append("\n");
                bw.flush();
            }
        }
        catch (IOException writeFile){
            logger.log(Level.WARNING, "Ошибка записи файла");
        }
    }
}
