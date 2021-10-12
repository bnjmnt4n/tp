package socialite.logic.parser;

import static socialite.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static socialite.logic.parser.CliSyntax.PREFIX_EMAIL;
import static socialite.logic.parser.CliSyntax.PREFIX_NAME;
import static socialite.logic.parser.CliSyntax.PREFIX_PHONE;
import static socialite.logic.parser.CliSyntax.PREFIX_TAG;
import static socialite.logic.parser.CliSyntax.PREFIX_TIKTOK;
import static socialite.logic.parser.CliSyntax.PREFIX_TWITTER;

import java.util.Set;
import java.util.stream.Stream;

import socialite.commons.core.Messages;
import socialite.logic.commands.AddCommand;
import socialite.logic.parser.exceptions.ParseException;
import socialite.model.handle.TikTok;
import socialite.model.handle.Twitter;
import socialite.model.person.Address;
import socialite.model.person.Email;
import socialite.model.person.Name;
import socialite.model.person.Person;
import socialite.model.person.Phone;
import socialite.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_TIKTOK, PREFIX_TWITTER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TIKTOK, PREFIX_TWITTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        TikTok tiktok = ParserUtil.parseTikTok(argMultimap.getValue(PREFIX_TIKTOK).get());
        Twitter twitter = ParserUtil.parseTwitter(argMultimap.getValue(PREFIX_TWITTER).get());

        Person person = new Person(name, phone, email, address, tagList, tiktok, twitter);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
