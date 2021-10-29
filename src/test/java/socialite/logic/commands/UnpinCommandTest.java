package socialite.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.model.CommandHistory;
import socialite.model.Model;
import socialite.model.ModelManager;
import socialite.model.UserPrefs;
import socialite.testutil.TypicalIndexes;
import socialite.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the model) and unit tests for {@code UnpinCommand}.
 */
public class UnpinCommandTest {

    private Model model =
            new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs(), new CommandHistory());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredLIst_throwsCommandException() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    //TODO: execute_unpinAnUnpinnedPerson_throwsCommandException()

    @Test
    public void equals() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(TypicalIndexes.INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(unpinFirstCommand, unpinFirstCommand);

        // same values -> returns true
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        assertEquals(unpinFirstCommand, unpinFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, unpinFirstCommand);

        // null -> returns false
        assertNotEquals(null, unpinFirstCommand);

        // different person -> returns false
        assertNotEquals(unpinSecondCommand, unpinFirstCommand);
    }
}
