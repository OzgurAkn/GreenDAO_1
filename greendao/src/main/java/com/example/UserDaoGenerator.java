package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class UserDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "de.greenrobot.daoexample");
        addNote(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("User");
        note.addIdProperty().autoincrement();
        note.addStringProperty("name");
        note.addIntProperty("age");
        note.addStringProperty("address");
        note.addIntProperty("zipcode");
        note.addStringProperty("city");
    }
}
