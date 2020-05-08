package com.te3.main.objects;

import com.te3.main.exceptions.IllegalInputException;

/**
 * The object used to store all the settings.
 */
public class Settings {
	/** The save path */
	private String savePath;

	/** The currently selected yoda */
	private String currentYoda;

	/** The save timer */
	private int saveTimer;

	/** If baby yoda should be shown or not */
	private boolean shouldShowYoda;

	/** If it should save the log or not */
	private boolean shouldSaveLog;

	/**
	 * Used for saving
	 */
	public Settings() {
	}

	/**
	 * Sets up all the settings
	 *
	 * @param savePath       the save path
	 * @param saveTimer      the auto save interval
	 * @param shouldShowYoda if baby yoda should be shown or not
	 * @param currentYoda    which yoda that's currently selected.
	 * @param shouldSaveLog if it should save the log or not.
	 */
	public Settings(String savePath, int saveTimer, boolean shouldShowYoda, String currentYoda, boolean shouldSaveLog) {
		this.savePath = savePath;
		this.saveTimer = saveTimer;
		this.shouldShowYoda = shouldShowYoda;
		this.currentYoda = currentYoda;
	}

	/**
	 * A getter for the save path.
	 *
	 * @return the save path
	 */
	public String getSavePath() {
		return this.savePath;
	}

	/**
	 * @param savePath the save path
	 * @throws IllegalInputException if the path is empty (trimmed)
	 */
	public void setSavePath(String savePath) throws IllegalInputException {
		/*
		 * Checks if the trimmed string is empty
		 *
		 * if true then it will throw an exception else it will set the path
		 */
		if (savePath.trim().equals("")) {
			throw new IllegalInputException("Du måste skriva något i rutan.");
		} else {
			this.savePath = savePath;
		}
	}

	/**
	 * A getter for the auto save timer interval.
	 *
	 * @return the save timer interval
	 */
	public int getSaveTimer() {
		return this.saveTimer;
	}

	/**
	 * A setter for the save timer
	 *
	 * @param saveTimer the new save timer
	 * @throws IllegalInputException if the time isn't in the interval: 60 < timer <
	 *                               3600
	 */
	public void setSaveTimer(int saveTimer) throws IllegalInputException {
		/*
		 * Checks for the interval: 60 < timer < 3600
		 *
		 * if true then it will throw an exception else it will set the timer.
		 */
		if (saveTimer < 60) {
			throw new IllegalInputException("Du måste skriva in en tid som är minst 60s");
		} else if (saveTimer > 3600) {
			throw new IllegalInputException("Du måste skriva in en tid som är max 3600s");
		} else {
			this.saveTimer = saveTimer;
		}
	}

	/**
	 * A getter for the current yoda
	 *
	 * @return the current yoda
	 */
	public String getCurrentYoda() {
		return currentYoda;
	}

	/**
	 * A setter for the current yoda
	 *
	 * @param currentYoda the new yoda
	 */
	public void setCurrentYoda(String currentYoda) {
		this.currentYoda = currentYoda;
	}

	/**
	 * A getter for shouldShowYoda
	 *
	 * @return {@code true} if baby yoda should be shown else it will return
	 *         {@code false}
	 */
	public boolean isShouldShowYoda() {
		return shouldShowYoda;
	}

	/**
	 * A setter for shouldShowYoda
	 *
	 * @param shouldShowYoda If baby yoda should be shown or not
	 */
	public void setShouldShowYoda(boolean shouldShowYoda) {
		this.shouldShowYoda = shouldShowYoda;
	}

	/**
	 * A getter for shouldSaveLog
	 *
	 * @return if it should save the log or not.
	 */
	public boolean isShouldSaveLog() {
		return shouldSaveLog;
	}

	/**
	 * A setter for shouldSaveLog
	 *
	 * @param shouldSaveLog if it should save the log or not
	 */
	public void setShouldSaveLog(boolean shouldSaveLog) {
		this.shouldSaveLog = shouldSaveLog;
	}
}
