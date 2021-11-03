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
 * Contains integration tests (interaction with the model) and unit tests for {@code ShareCommand}.
 */
public class ShareCommandTest {

    private Model model =
            new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs(), new CommandHistory());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ShareCommand shareCommand = new ShareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(shareCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredLIst_throwsCommandException() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ShareCommand shareCommand = new ShareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(shareCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void equals() {
        ShareCommand shareFirstCommand = new ShareCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        ShareCommand shareSecondCommand = new ShareCommand(TypicalIndexes.INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(shareFirstCommand, shareFirstCommand);

        // same values -> returns true
        ShareCommand shareFirstCommandCopy = new ShareCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        assertEquals(shareFirstCommand, shareFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, shareFirstCommand);

        // null -> returns false
        assertNotEquals(null, shareFirstCommand);

        // different person -> returns false
        assertNotEquals(shareSecondCommand, shareFirstCommand);
    }
}