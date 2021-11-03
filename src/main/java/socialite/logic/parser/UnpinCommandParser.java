package socialite.logic.parser;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.UnpinCommand;
import socialite.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PinCommand object
 */
public class UnpinCommandParser implements Parser<UnpinCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnpinCommand
     * and returns a UnpinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnpinCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnpinCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_HELP_GUIDE), pe);
        }
    }
}