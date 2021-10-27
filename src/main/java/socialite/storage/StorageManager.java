package socialite.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import socialite.commons.core.LogsCenter;
import socialite.commons.exceptions.DataConversionException;
import socialite.model.ReadOnlyAddressBook;
import socialite.model.ReadOnlyCommandHistory;
import socialite.model.ReadOnlyUserPrefs;
import socialite.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private CommandHistoryStorage commandHistoryStorage;
    private ProfilePictureStorage profilePictureStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage},
     * @code UserPrefStorage}, {@code commandHistoryStorage}, {@code profilePictureStorage}.
     */
    public StorageManager(
            AddressBookStorage addressBookStorage,
            UserPrefsStorage userPrefsStorage,
            CommandHistoryStorage commandHistoryStorage,
            ProfilePictureStorage profilePictureStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.profilePictureStorage = profilePictureStorage;
        this.commandHistoryStorage = commandHistoryStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    // ================ CommandHistory methods ==============================

    @Override
    public Path getCommandHistoryFilePath() {
        return commandHistoryStorage.getCommandHistoryFilePath();
    }

    @Override
    public Optional<ReadOnlyCommandHistory> readCommandHistory() throws DataConversionException, IOException {
        return readCommandHistory(commandHistoryStorage.getCommandHistoryFilePath());
    }

    @Override
    public Optional<ReadOnlyCommandHistory> readCommandHistory(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read command history from file: " + filePath);
        return commandHistoryStorage.readCommandHistory(filePath);
    }

    @Override
    public void saveCommandHistory(ReadOnlyCommandHistory commandHistory) throws IOException {
        saveCommandHistory(commandHistory, commandHistoryStorage.getCommandHistoryFilePath());
    }

    @Override
    public void saveCommandHistory(ReadOnlyCommandHistory commandHistory, Path filePath) throws IOException {
        logger.fine("Attempting to save command history from file: " + filePath);
        commandHistoryStorage.saveCommandHistory(commandHistory, filePath);
    }

    // ================ Profile Picture methods ==============================

    @Override
    public Path getProfilePictureFolderPath() {
        return profilePictureStorage.getProfilePictureFolderPath();
    }

    @Override
    public void deleteProfilePicture(Path name) {
        profilePictureStorage.deleteProfilePicture(name);
    }

    @Override
    public void saveProfilePicture(File file, String prefix) {
        profilePictureStorage.saveProfilePicture(file, prefix);
    }
}
