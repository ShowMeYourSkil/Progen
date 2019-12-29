package de.progen_bot.command;

import de.progen_bot.db.dao.config.ConfigDaoImpl;
import de.progen_bot.db.entities.config.GuildConfiguration;
import de.progen_bot.db.entities.config.GuildConfigurationBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * The Class CommandManager.
 */
public class CommandManager extends ListenerAdapter {

    /**
     * The Constant commandAssociations.
     */
    private final static HashMap<String, CommandHandler> commandAssociations = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        GuildConfiguration guildConfiguration = new ConfigDaoImpl().loadConfig(event.getGuild());

        if (guildConfiguration == null) {
            guildConfiguration = new GuildConfigurationBuilder()
                    .setLogChannelID(null)
                    .setPrefix("test!")
                    .setTempChannelCategoryID(null)
                    .build();

            new ConfigDaoImpl().writeConfig(guildConfiguration, event.getGuild());
        }
        ParsedCommandString parsedMessage = parse(event.getMessage().getContentRaw(), guildConfiguration.prefix);

        if (!event.getAuthor().isBot() && !event.getAuthor().isFake() && parsedMessage != null
                && event.getChannelType().isGuild()) {
            CommandHandler commandHandler = commandAssociations.get(parsedMessage.getCommand());

            if (commandHandler != null) {
                commandHandler.execute(parsedMessage, event, guildConfiguration);
            }
        }
    }

    /**
     * Sets the up de.progen_bot.command handlers.
     *
     * @param commandHandler the new up de.progen_bot.command handlers
     */
    public void setupCommandHandlers(CommandHandler commandHandler) {
        if (!commandAssociations.containsKey(commandHandler.getInvokeString().toLowerCase())) {
            commandAssociations.put(commandHandler.getInvokeString(), commandHandler);
        }

    }

    /**
     * Gets the de.progen_bot.command handler.
     *
     * @param invocationAlias the invocation alias
     * @return the de.progen_bot.command handler
     */
    public CommandHandler getCommandHandler(String invocationAlias) {
        return commandAssociations.get(invocationAlias.toLowerCase());
    }

    public HashMap<String, CommandHandler> getCommandassociations() {
        return commandAssociations;
    }

    /**
     * Parses the.
     *
     * @param message the message
     * @return the parsed de.progen_bot.command string
     */
    private ParsedCommandString parse(String message, String prefix) {
        if (message.startsWith(prefix)) {
            String beheaded = message.replaceFirst(Pattern.quote(prefix), "");
            String[] args = beheaded.split("\\s+");
            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            return new ParsedCommandString(args[0], newArgs);
        }
        return null;
    }

    /**
     * The Class ParsedCommandString.
     */
    public static final class ParsedCommandString {

        /**
         * The de.progen_bot.command.
         */
        private final String command;

        /**
         * The args.
         */
        private final String[] args;
        public int lenght;

        /**
         * Instantiates a new parsed de.progen_bot.command string.
         *
         * @param command the de.progen_bot.command
         * @param args    the args
         */
        public ParsedCommandString(String command, String[] args) {
            this.command = command;
            this.args = args;
        }

        /**
         * Gets the de.progen_bot.command.
         *
         * @return the de.progen_bot.command
         */
        public String getCommand() {
            return command;
        }

        /**
         * Gets the args.
         *
         * @return the args
         */
        public String[] getArgs() {
            return args;
        }

        public ArrayList<String> getArgsAsList() {

            return new ArrayList<>(Arrays.asList(args));
        }
    }
}
