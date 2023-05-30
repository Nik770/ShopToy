package org.example.Model;

/**
 * Игрушки, выигранные в лотерее и ожидающие вручения
 */
public class Gift extends Product {
    /**
     * @param id id игрушки
     * @param name название игрушки
     */
    public Gift(int id, String name) {
        super(id, name);
    }

    /**
     * @return строка для записи в базу данных
     */
    @Override
    public String toWrite() {
        return id + ";" + name + "\n";
    }

    @Override
    public String toString() {
        return name;
    }


}
