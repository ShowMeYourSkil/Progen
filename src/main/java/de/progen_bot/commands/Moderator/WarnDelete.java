package de.progen_bot.commands.Moderator;

import de.mtorials.config.GuildConfiguration;
import de.progen_bot.command.CommandHandler;
import de.progen_bot.command.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public class WarnDelete extends CommandHandler {
    public WarnDelete() {super("warndelete","warndelete <user> <reason>","warned a user");
}

    @Override
    public void execute(CommandManager.ParsedCommandString parsedCommand, MessageReceivedEvent event, GuildConfiguration configuration) {
        System.out.println("[INFO] Command pb!WarnDelete wurde ausgeführt!");

        if (event.getMessage().getMentionedMembers().size() == 0) {
            event.getTextChannel().sendMessage(super.messageGenerators.generateErrorMsg("No user found")).queue();
            return;
        }

        super.getDAOs().getWarnList().deleteWarnForMember(event.getMessage().getMentionedMembers().get(0));

        event.getTextChannel().sendMessage(super.messageGenerators.generateSuccessfulMsg()).queue();
    }

    @Override
    public String help() {
        return null;
    }
}