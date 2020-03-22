package com.te3.main.objects;

import com.te3.main.exceptions.IllegalInputException;

public class Settings {
    String savePath;

    public Settings() {}

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) throws IllegalInputException {
        if (savePath.trim().equals("")) {
            throw new IllegalInputException("Du måste skriva något i rutan.");
        } else {
            this.savePath = savePath;
        }
    }
}
