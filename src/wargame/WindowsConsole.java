package wargame;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class WindowsConsole implements IConsole {
	Runtime runtime;
    InputStream input;
	
	public WindowsConsole() throws IOException, InterruptedException {
		runtime = Runtime.getRuntime();
		Process cmd = runtime.exec("cmd /c start");
		new Thread(new SyncPipe(cmd.getErrorStream(), System.err)).start();
	    new Thread(new SyncPipe(cmd.getInputStream(), System.out)).start();
	    PrintWriter stdin = new PrintWriter(cmd.getOutputStream());
	    stdin.println("dir");
	    stdin.flush();
	    stdin.close();
	    int returnCode = cmd.waitFor();
	    System.out.println("Return code = " + returnCode);

	}

	@Override
	public void print(String string) {
		
	}

	@Override
	public void print(char singleChar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println(String line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void println(char singleChar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearScreen() {
		// TODO Auto-generated method stub
		
	}

}
