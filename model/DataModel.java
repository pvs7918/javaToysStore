package model;

import classes.*;

import java.util.List;

public interface DataModel<T> {
    public void add(T rec);
    public void save();
    public List<T> load();
}
