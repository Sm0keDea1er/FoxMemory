package com.smartfox.foxmemory.data;

/**
 * Created by SmartFox on 01.03.2018.
 */

public class Task {

    private int _id;
    private String _name;
    private String _description;
    private int _priority;

    public Task(){}

    public Task(int _id, String _name, String _description, int _priority) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
        this._priority = _priority;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_priority() {
        return _priority;
    }

    public void set_priority(int _priority) {
        this._priority = _priority;
    }
}
