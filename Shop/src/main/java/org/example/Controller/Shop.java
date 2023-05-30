package org.example.Controller;

import org.example.Model.DataBase;
import org.example.Model.Stock;
import org.example.Model.Toy;
import org.example.View.UI.UserInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Магазин игрушек
 */
public class Shop implements IShop {
    private DataBase db;
    private Stock stock;
    private final UserInterface ui;
    private Boolean open;
    private Integer choiceMenu;

    public Shop() throws IOException {
        this.stock = new Stock();
        this.db = new DataBase(stock);
        this.ui = new UserInterface(db.getLogger());
        this.open = (Boolean) true;
        this.choiceMenu = (Integer) 0;
    }

    /**
     * Запуск программы
     * @throws InterruptedException
     */
    @Override
    public void run() throws InterruptedException, FileNotFoundException {
        db.readToys().readGifts();
        while (open) {
            ui.showMenu(choiceMenu);
            switch (choiceMenu) {
                case 0:
                    choiceMenu = ui.choiceMenu();
                    break;
                case 1:
                    ui.showPositions(stock.getAllToys());
                    ui.pressEnter();
                    choiceMenu = Integer.valueOf(0);
                    break;
                case 2:
                    Toy newToy = ui.addItem();
                    if (newToy != null) {
                        stock.add(newToy);
                        ui.newItem();
                    } else
                        ui.fail();
                    TimeUnit.SECONDS.sleep(1);
                    choiceMenu = Integer.valueOf(0);
                    break;
                case 3:
                    ui.amount(stock.getAllToys());
                    TimeUnit.SECONDS.sleep(1);
                    choiceMenu = Integer.valueOf(0);
                    break;
                case 4:
                    ui.probability(stock.getAllToys());
                    TimeUnit.SECONDS.sleep(1);
                    choiceMenu = Integer.valueOf(0);
                    break;
                case 5:
                    ArrayList<Toy> win = ui.lottery(stock.getAllToys());
                    stock.lottery(win);
                    TimeUnit.SECONDS.sleep(1);
                    choiceMenu = Integer.valueOf(0);
                    break;
                case 6:
                    ui.takePrize(stock.getWinners());
                    TimeUnit.SECONDS.sleep(1);
                    choiceMenu = Integer.valueOf(0);
                    break;
                case 7:
                    TimeUnit.SECONDS.sleep(1);
                    db.writeToys().writeGifts();
                    stop();
                    break;
                default:
                    ui.error();
                    choiceMenu = Integer.valueOf(0);
            }
        }
    }

    /**
     * Остановка  программы
     */
    @Override
    public void stop() {
        open = false;
    }
}