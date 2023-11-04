package prisongame.prisongame.commands.completers;

public class EnderChestComplete extends SubcommandCompleter {
    public EnderChestComplete() {
        super("enderchest", new String[] {
                "inspect",
                "clear"
        });
    }
}
