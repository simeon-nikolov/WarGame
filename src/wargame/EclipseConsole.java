package wargame;

import java.util.Locale;

public class EclipseConsole implements IConsole {
	private static final int CONSOLE_ROWS = 35;
	
	public EclipseConsole() {
		Locale.setDefault(Locale.US);
	}

	@Override
	public void print(String string) {
		System.out.print(string);
	}
	
	@Override
	public void print(char singleChar) {
		System.out.print(singleChar);
	}
	
	@Override
	public void println() {
		System.out.println();
	}

	@Override
	public void println(String line) {
		System.out.println(line);
	}
	
	@Override
	public void println(char singleChar) {
		System.out.println(singleChar);
	}

	@Override
	public void clearScreen() {
		for (int i = 0; i < CONSOLE_ROWS; i++) {
			System.out.println();
		}
	}
}
