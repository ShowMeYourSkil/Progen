package de.progen_bot.core;

import javax.security.auth.login.LoginException;

import de.mtorials.commands.Stats;
import de.mtorials.db.MySQLConnection;
import de.mtorials.db.dao.DAOWarnList;
import de.mtorials.fortnite.core.Fortnite;
import de.mtorials.webinterface.httpapi.API;
import de.progen_bot.command.CommandManager;
import de.progen_bot.commands.*;
import de.progen_bot.commands.Administartor.Stop;
import de.progen_bot.commands.Fun.ConnectFour;
import de.progen_bot.commands.Moderator.AddUserToPrivateVoiceChannel;
import de.progen_bot.commands.Moderator.Clear;
import de.progen_bot.commands.Moderator.Mute;
import de.progen_bot.commands.Moderator.UnMute;
import de.progen_bot.commands.User.GuildInfo;
import de.progen_bot.commands.User.Ping;
import de.progen_bot.commands.User.Say;
import de.progen_bot.commands.music.Music;
import de.progen_bot.commands.xp.XP;
import de.progen_bot.commands.xp.XPNotify;
import de.progen_bot.commands.xp.XPrank;
import de.progen_bot.db.MySQL;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import de.progen_bot.util.Settings;

import java.io.IOException;

/**
 * The Class Main.
 */
public class Main {

	/** The jda. */
	private static JDA jda;

	private static MySQL sql;
	private static MySQLConnection mySQLConnection = new MySQLConnection("localhost", "test", "root", "");

	private static Fortnite fortnite;

	private static API httpapi;

	private static CommandManager commandManager;

	//DAOs

	private static DAOWarnList warnList = new DAOWarnList();

	/**
	 * Instantiates a new main.
	 */
	public Main() throws IOException {
		Settings.loadSettings();

		//httpapi = new API(80);
		//httpapi.start();

		fortnite = new Fortnite();

		MySQL.connect();

		initJDA();

		commandManager = new CommandManager();
		initCommandHandlers(commandManager);

		MySQL.loadPollTimer();

		//DAOs

		warnList.generateTables();
	}

	/**
	 * Inits the de.progen_bot.command handlers.
	 *
	 * @param commandManager
	 *            the de.progen_bot.command manager
	 */
	private void initCommandHandlers(CommandManager commandManager) {
		commandManager.setupCommandHandlers(new Clear());
		commandManager.setupCommandHandlers(new Stop());
		commandManager.setupCommandHandlers(new GuildInfo());
		commandManager.setupCommandHandlers(new Ping());
		commandManager.setupCommandHandlers(new Say());
		commandManager.setupCommandHandlers(new CommandUserInfo());
		commandManager.setupCommandHandlers(new Warn());
		commandManager.setupCommandHandlers(new Mute());
		commandManager.setupCommandHandlers(new UnMute());
		commandManager.setupCommandHandlers(new AddUserToPrivateVoiceChannel());
		//commandManager.setupCommandHandlers(new VierGewinnt());
		commandManager.setupCommandHandlers(new Help());
		commandManager.setupCommandHandlers(new ConnectFour());
		commandManager.setupCommandHandlers(new XPrank());
		commandManager.setupCommandHandlers(new XP());
		commandManager.setupCommandHandlers(new XPNotify());
		commandManager.setupCommandHandlers(new Music());
		commandManager.setupCommandHandlers(new Stats());
		commandManager.setupCommandHandlers(new CommandRegisterAPI());
		commandManager.setupCommandHandlers(new WarnList());
		commandManager.setupCommandHandlers(new WarnBoard());
	}

	/**
	 * Inits the JDA.
	 */
	private void initJDA() {
		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(Settings.TOKEN);

		new BuildManager(builder);

		try {
			jda = builder.buildBlocking();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the jda.
	 *
	 * @return the jda
	 */
	public static JDA getJda() {
		return jda;
	}

	public static MySQL getSQL() {
		return sql;
	}

	public static Fortnite getFortnite() { return fortnite; }
	
	public static CommandManager getCommandManager() {
		return commandManager;
	}

	public static MySQLConnection getMysqlConnection() {

		return mySQLConnection;
	}

	public static DAOWarnList getDAOWarnList() {

		return warnList;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) throws IOException{
		new Main();
	}
}
