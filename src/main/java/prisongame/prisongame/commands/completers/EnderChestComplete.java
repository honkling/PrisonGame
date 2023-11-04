package prisongame.prisongame.commands.completers;

public class EnderChestComplete extends SubcommandCompleter {
    public EnderChestComplete() {
        super(new String[] {
                "inspect",
                "clear"
        });
    }
}
