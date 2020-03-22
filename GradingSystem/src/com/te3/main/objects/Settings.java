package com.te3.main.objects;

import com.te3.main.exceptions.IllegalInputException;

public class Settings {
    String savePath;

    int saveTimer;

    public Settings() {}

    public Settings(String savePath, int saveTimer) {
        this.savePath = savePath;
        this.saveTimer = saveTimer;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath) throws IllegalInputException {
        if (savePath.trim().equals("")) {
            throw new IllegalInputException("Du måste skriva något i rutan.");
        } else {
            this.savePath = savePath;
        }
    }

    public int getSaveTimer() {
        return this.saveTimer;
    }

    public void setSaveTimer(int saveTimer) throws IllegalInputException {
        if (saveTimer < 60) {
            throw new IllegalInputException("Du måste skriva in en tid som är minst 60s");
        } else if (saveTimer > 3600) {
            throw new IllegalInputException("Du måste skriva in en tid som är max 3600s");
        } else {
            this.saveTimer = saveTimer;
        }
    }
}
