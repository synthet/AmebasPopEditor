package ru.synthet.AmPopEditor;

import java.util.ArrayList;

public class FormData {
    private ArrayList<Integer> popSizes;

    public FormData(ArrayList<Integer> popSizes) {
        this.popSizes = new ArrayList<Integer>(popSizes);
    }

    public String getPopSize(int i) {
        return String.valueOf(popSizes.get(i));
    }

}