package wargame;

import java.io.IOException;


public class GameMain {
	public static void main(String[] args) throws InterruptedException, IOException {
		IConsole console = new EclipseConsole();
		//IConsole cmd = new WindowsConsole();
		Game warGame = new Game(console);
		warGame.run();
	}

}
