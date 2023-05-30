package org.example.Model;

/**
 * Игрушки из магазина игрушек
 */
public class Toy extends Product implements IToy {
    Integer amount;
    Float probability;
    private static int id_count = 0; //Счётчик id

    /**
     * Инициализация в момент наполнения пользователем ассортимента магазина
     *
     * @param name        название
     * @param amount      количество на складе
     * @param probability вероятность выигрыша в лотерее
     */
    public Toy(String name, Integer amount, Float probability) {
        super(id_count++, name);
        this.amount = amount;
        this.probability = probability;
    }

    /**
     * Инизиализация при загрузке из файла
     *
     * @param id          id игрушки
     * @param name        нащвание игрушки
     * @param amount      количество на складе
     * @param probability вероятность выигрыша
     */
    public Toy(int id, String name, Integer amount, Float probability) {
        super(id, name);
        this.amount = amount;
        this.probability = probability;
        id_count = id + 1;
    }

    /**
     * Изменение количества игрушек на складе
     *
     * @param amount новое значение количества игрушек
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return вероятность выигрыша
     */
    public Float getProbability() {
        return probability;
    }

    /**
     * Изменение вероятности выигрыша
     *
     * @param probability новое значение вероятности выигрыша
     */
    public void setProbability(Float probability) {
        this.probability = probability;
    }

    /**
     * Уменьшение количества на единицу после того, как игрушка была выиграна в лотерею
     */
    @Override
    public void giftIt() {
        this.amount--;
    }

    /**
     * Подготовка к записи в файл базы данных
     *
     * @return строка для записи
     */
    @Override
    public String toWrite() {
        return id + ";" + name + ";" + amount + ";" + probability + "\n";
    }

    @Override
    public String toString() {
        return id + "sepor" + name + "sepor" + amount + "sepor" + probability;
    }

    /**
     * @return id игрушки
     */
    public int getId() {
        return id;
    }

    /**
     * @return название игрушки
     */
    public String getName() {
        return name;
    }
}
