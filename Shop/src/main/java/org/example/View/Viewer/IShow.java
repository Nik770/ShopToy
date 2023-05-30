package org.example.View.Viewer;

import java.util.LinkedList;

public interface IShow {
    public void show(String text);
    public void inputError();
    public void changeComplete();
    public void table(LinkedList<String> data);
}