package org.example.View.UI;

import org.example.Model.Gift;
import org.example.Model.Toy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public interface IUserAction {
    public Integer choiceMenu() ;
    public void showPositions(HashMap<Integer, Toy> allToys);
    public Toy addItem() throws InterruptedException;
    public void amount(HashMap<Integer, Toy> allToys) throws InterruptedException;
    public void probability(HashMap<Integer, Toy> allToys) throws InterruptedException;
    public ArrayList<Toy> lottery(HashMap<Integer, Toy> allToys);
    public void takePrize(Queue<Gift> gift);
    public void pressEnter();
}