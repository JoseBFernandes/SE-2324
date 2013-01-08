package net.sf.freecol.common.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.filechooser.FileSystemView;




public class FreeColDirectories {

    public static final String COPYRIGHT = "Copyright (C) 2003-2012 The FreeCol Team";

    public static final String LICENSE = "http://www.gnu.org/licenses/gpl.html";

    public static final String REVISION = "$Revision: 2763 $";

    private static File saveDirectory;

    /** Directory containing automatically created save games.
     *  At program start, the path of this directory is based on the path
     *  where to store regular save games. If the value of saveGame is
     *  changed by the user during the game, then the value of
     *  autoSaveDirectory will not be effected.
     */
    private static File autoSaveDirectory;

    private static File mainUserDirectory = null;

    private static File tcUserDirectory;

    private static File userModsDirectory;

    private static String tc = FreeColDirectories.DEFAULT_TC;

    private static File savegameFile = null;

    public static final String DEFAULT_TC = "freecol";

    private static File clientOptionsFile = null;

    private static final String HIGH_SCORE_FILE = "HighScores.xml";

    private static final String DIRECTORY = "rules";

    private static final String STRINGS_DIRECTORY = "strings";

    private static final String LOG_FILE_NAME = "FreeCol.log";

    private static final String BASE_DIRECTORY = "base";
    private static final String DATA_DIRECTORY = "data";
    private static final String I18N_DIRECTORY = "strings";
    /**
     * The directory where the standard freecol data is installed.
     * Can be overridden at the command line.
     *
     * TODO: defaults lamely to ./data.  Do something better in the
     * installer.
     */
    private static File dataDirectory = new File(DATA_DIRECTORY);

    /**
     * The path to the log file.
     * Can be overridden at the command line.
     */
    private static String logFilePath = null;

    private static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * Checks/creates the freecol directory structure for the current
     * user.
     *
     * The main user directory is in the current user's home
     * directory.  It used to be called ".freecol" (UNIXes) or
     * "freecol", but now we also use Library/FreeCol under MacOSX and
     * some JFileChooser trickery with Windows.
     *
     * Note: the freecol data directory is set independently.
     *
     * TODO: The default location of the main user and data
     * directories should be determined by the installer.
     *
     * @return True if the directory structure is intact.
     */
    public static boolean createAndSetDirectories() {
        if (FreeColDirectories.mainUserDirectory == null) {
            if (setMainUserDirectory(null) != null) return false;
        }

        if (FreeColDirectories.saveDirectory == null) {
            FreeColDirectories.saveDirectory = new File(FreeColDirectories.getMainUserDirectory(), "save");
        }
        if (!FreeColDirectories.insistDirectory(FreeColDirectories.saveDirectory)) FreeColDirectories.saveDirectory = null;
    
        FreeColDirectories.autoSaveDirectory = new File(FreeColDirectories.saveDirectory, "autosave");
        if (!FreeColDirectories.insistDirectory(FreeColDirectories.autoSaveDirectory)) FreeColDirectories.autoSaveDirectory = null;
    
        FreeColDirectories.tcUserDirectory = new File(FreeColDirectories.getMainUserDirectory(), FreeColDirectories.getTc());
        if (!FreeColDirectories.insistDirectory(FreeColDirectories.tcUserDirectory)) FreeColDirectories.tcUserDirectory = null;
    
        FreeColDirectories.userModsDirectory = new File(FreeColDirectories.getMainUserDirectory(), "mods");
        if (!FreeColDirectories.insistDirectory(FreeColDirectories.userModsDirectory)) FreeColDirectories.userModsDirectory = null;
    
        if (FreeColDirectories.clientOptionsFile == null) {
            FreeColDirectories.clientOptionsFile = (FreeColDirectories.tcUserDirectory == null) ? null
                : new File(FreeColDirectories.tcUserDirectory, "options.xml");
        }

        if (logFilePath == null) {
            logFilePath = mainUserDirectory + SEPARATOR + LOG_FILE_NAME;
        }
        return true;
    }

    /**
     * Returns the directory where the autogenerated savegames
     * should be put.
     *
     * @return The directory.
     */
    public static File getAutosaveDirectory() {
        return autoSaveDirectory;
    }

    public static File getBaseDirectory() {
        return new File(getDataDirectory(), "base");
    }

    /**
     * Returns the file containing the client options.
     * @return The file.
     */
    public static File getClientOptionsFile() {
        return clientOptionsFile;
    }

    /**
     * Returns the data directory.
     * @return The directory where the data files are located.
     */
    public static File getDataDirectory() {
        return dataDirectory;
    }

    public static File getHighScoreFile() {
        return new File(getDataDirectory(), FreeColDirectories.HIGH_SCORE_FILE);
    }

    /**
     * Returns the directory containing language property files.
     *
     * @return a <code>File</code> value
     */
    public static File getI18nDirectory() {
        return new File(getDataDirectory(), FreeColDirectories.STRINGS_DIRECTORY);
    }

    /**
     * Gets the log file path.
     *
     * @return The log file path.
     */
    public static String getLogFilePath() {
        return logFilePath;
    }

    /**
     * Sets the log file path.
     *
     * @param path The new log file path.
     */
    public static void setLogFilePath(String path) {
        logFilePath = path;
    }
 
    public static File getMainUserDirectory() {
        return mainUserDirectory;
    }

    public static File getMapsDirectory() {
        return new File(getDataDirectory(), "maps");
    }

    /**
     * Returns the directory for saving options.
     *
     * @return The directory.
     */
    public static File getOptionsDirectory() {
        return tcUserDirectory;
    }

    public static File getRulesClassicDirectory() {
        return new File(getDataDirectory(), "rules/classic");
    }

    public static File getRulesDirectory() {
        return new File(getDataDirectory(), FreeColDirectories.DIRECTORY);
    }

    /**
     * Returns the directory where the savegames should be put.
     * @return The directory where the savegames should be put.
     */
    public static File getSaveDirectory() {
        return saveDirectory;
    }

    public static File getSavegameFile() {
        return savegameFile;
    }

    /**
     * Gets the mods directory.
     *
     * @return The directory where the standard mods are located.
     */
    public static File getStandardModsDirectory() {
        return new File(FreeColDirectories.getDataDirectory(), "mods");
    }

    public static String getTc() {
        return tc;
    }

    /**
     * Gets the user mods directory.
     *
     * @return The directory where user mods are located, or null if none.
     */
    public static File getUserModsDirectory() {
        return userModsDirectory;
    }

    /**
     * Try to make a directory.
     *
     * @param file A <code>File</code> specifying where to make the directory.
     * @return True if the directory is there after the call.
     */
    public static boolean insistDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) return true;
            System.out.println("Could not create directory " + file.getName()
                + " under " + file.getParentFile().getName()
                + " because a non-directory with that name is already there.");
            return false;
        }
        return file.mkdir();
    }

    /**
     * Sets the client options file.
     *
     * @param path The new client options file.
     * @return True if the file was set successfully.
     */
    public static boolean setClientOptionsFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile() && file.canRead()) {
            clientOptionsFile = file;
            return true;
        }
        return false;
    }

    /**
     * Sets the data directory.
     *
     * Insist that the base resources and i18n subdirectories are present.
     *
     * @param dir The new value for the data directory, or null to
     *     apply the default.
     * @return A (non-i18n) error message on failure, null on success.
     */
    public static String setDataDirectory(String path) {
        if (path == null) path = DATA_DIRECTORY;
        File dir = new File(path);
        if (!dir.isDirectory()) return "Not a directory: " + path;
        if (!dir.canRead()) return "Can not read directory: " + path;
        dataDirectory = dir;
        if (getBaseDirectory() == null) {
            return "Can not find base resources directory: " + path
                + SEPARATOR + BASE_DIRECTORY;
        }
        if (getI18nDirectory() == null) {
            return "Can not find I18n resources directory: " + path
                + SEPARATOR + I18N_DIRECTORY;
        }
        return null;
    }

    /**
     * Sets the main user directory, creating it if necessary.
     * If pre-existing, it must be a directory, readable and writable.
     *
     * @param path The path to the new main user directory, or null to apply
     *     the default.
     * @return Null on success, an error message key on failure.
     */
    public static String setMainUserDirectory(String path) {
        String ret = null;
        File dir = (path == null) ? getDefaultMainUserDirectory()
            : new File(path);
        if (!dir.exists()) {
            ret = "cli.error.home.notExists";
            try {
                if (dir.mkdir()) {
                    mainUserDirectory = dir;
                    ret = null;
                }
            } catch (Exception e) {}
        } else if (!dir.isDirectory()) {
            ret = "cli.error.home.notExists";
        } else if (!dir.canRead()) {
            ret = "cli.error.home.noRead";
        } else if (!dir.canWrite()) {
            ret = "cli.error.home.noWrite";
        } else {
            mainUserDirectory = dir;
        }
        return ret;
    }

    /**
     * Gets the default main user directory under their home.
     *
     * @return The default main user directory.
     */
    public static File getDefaultMainUserDirectory() {
        String freeColDirectoryName = "/".equals(SEPARATOR) ? ".freecol"
            : "freecol";
        File userHome = FileSystemView.getFileSystemView()
            .getDefaultDirectory();
        if (userHome == null) return null;

        // Checks for OS specific paths, however if the old
        // {home}/.freecol exists that overrides OS-specifics for
        // backwards compatibility.  TODO: remove compatibility code
        if (System.getProperty("os.name").equals("Mac OS X")) {
            // We are running on a Mac and should use {home}/Library/FreeCol
            if (!new File(userHome, freeColDirectoryName).isDirectory()) {
                userHome = new File(userHome, "Library");
                freeColDirectoryName = "FreeCol";
            }
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            // We are running on Windows and should use "My Documents"
            // (or localized equivalent)
            if (!new File(userHome, freeColDirectoryName).isDirectory()) {
                freeColDirectoryName = "FreeCol";
            }
        }
        
        return new File(userHome, freeColDirectoryName);
    }

    /**
     * Set the directory where the savegames should be put.
     * @param saveDirectory a <code>File</code> value for the savegame directory
     */
    public static void setSaveDirectory(File saveDirectory) {
        FreeColDirectories.saveDirectory = saveDirectory;
    }

    public static void setSavegameFile(File savegameFile) {
        FreeColDirectories.savegameFile = savegameFile;
    }

    /**
     * Sets the save game file.
     *
     * @param path The path to the new save game file.
     * @return True if the setting succeeds.
     */
    public static boolean setSavegameFile(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            file = new File(getSaveDirectory(), path);
            if (!file.exists() || !file.isFile()
                || !file.canRead()) return false;
        }
        setSavegameFile(file);
        setSaveDirectory(file.getParentFile());
        return true;
    }

    public static void setTc(String tc) {
        FreeColDirectories.tc = tc;
    }
}
