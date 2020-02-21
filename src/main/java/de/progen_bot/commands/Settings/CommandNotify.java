package de.progen_bot.commands.Settings;

import de.progen_bot.command.CommandHandler;
import de.progen_bot.command.CommandManager;
import de.progen_bot.core.PermissionCore;
import de.progen_bot.db.entities.config.GuildConfiguration;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandNotify extends CommandHandler {
    public CommandNotify() {super("notify", "notify", "Returns the role Notify. This is a kind of newsletter with which you can be pinned for news.");
    }

    @Override
    public void execute(CommandManager.ParsedCommandString parsedCommand, MessageReceivedEvent event, GuildConfiguration configuration) {
        if (PermissionCore.check(0, event));
        Member member = event.getMember();
        Guild guild = event.getGuild();

        Role Notify = guild.getRolesByName("Notify", true).get(0);

        event.getGuild().addRoleToMember(event.getMember(), Notify).complete();

        if(parsedCommand.getArgs().equals("Notify")) guild.addRoleToMember(event.getMember(), Notify).queue();
    }

    @Override
    public String help() {
        return null;
    }
}
