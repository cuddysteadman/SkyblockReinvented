package thecudster.sre.util.sbutil

import net.minecraft.command.CommandBase
import thecudster.sre.util.sbutil.SimpleCommand.ProcessCommandRunnable
import thecudster.sre.util.sbutil.SimpleCommand.TabCompleteRunnable
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.command.CommandException

/**
 * Modified from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 * @author Moulberry
 */
class SimpleCommand : CommandBase {
    private var commandName: String
    private var runnable: ProcessCommandRunnable
    private var tabRunnable: TabCompleteRunnable? = null
    private var aliases: List<String>? = null

    constructor(commandName: String, runnable: ProcessCommandRunnable) {
        this.commandName = commandName
        this.runnable = runnable
    }

    constructor(commandName: String, alias: List<String>?, runnable: ProcessCommandRunnable) {
        this.commandName = commandName
        this.runnable = runnable
        aliases = alias
    }

    constructor(commandName: String, runnable: ProcessCommandRunnable, tabRunnable: TabCompleteRunnable?) {
        this.commandName = commandName
        this.runnable = runnable
        this.tabRunnable = tabRunnable
    }

    abstract class ProcessCommandRunnable {
        abstract fun processCommand(sender: ICommandSender?, args: Array<String>?)
    }

    abstract class TabCompleteRunnable {
        abstract fun tabComplete(sender: ICommandSender?, args: Array<String>?, pos: BlockPos?): List<String>
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }

    override fun getCommandName(): String {
        return commandName
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/$commandName"
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        runnable.processCommand(sender, args)
    }

    override fun addTabCompletionOptions(sender: ICommandSender, args: Array<String>, pos: BlockPos): List<String>? {
        return if (tabRunnable != null) tabRunnable!!.tabComplete(sender, args, pos) else null
    }

    override fun getCommandAliases(): List<String>? {
        return if (aliases != null) aliases!! else null
    }
}