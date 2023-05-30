package org.example.Model;

import java.io.FileNotFoundException;

public interface IDB {
    public DataBase writeToys();
    public DataBase readToys() throws FileNotFoundException;
    public DataBase writeGifts();
    public DataBase readGifts() throws FileNotFoundException;
}
