package wargame;


public interface IConsole {
	public abstract void print(String string);
	public abstract void print(char singleChar);
	public abstract void println();
	public abstract void println(String line);
	public abstract void println(char singleChar);
	public abstract void clearScreen();
}
