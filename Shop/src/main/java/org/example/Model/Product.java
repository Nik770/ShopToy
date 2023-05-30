package org.example.Model;

/**
 * Абстрактный класс для какого-то там товара
 */
public abstract class Product implements IWrite{
    int id;
    String name;

    /**
     * @param id id игрушки
     * @param name название игрушки
     */
    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
