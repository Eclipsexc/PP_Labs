package Commands;

public class ExitCommand implements Command {

    public ExitCommand() { }

    @Override
    public void execute() {
        System.out.println("Вихід з програми...");
    }
}
