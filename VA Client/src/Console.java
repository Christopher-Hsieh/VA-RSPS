/**
 * 
 * @author Zion
 *
 */
public class Console {
	
	public String consoleInput;
	public final String[] inputConsoleMessages;
	public boolean openConsole;
	
	public void drawConsole(int width, int height) {
		if (openConsole) {
			DrawingArea.fillRectangle(0, 0, width, height, ColorUtility.getHexColors(), 125);
			DrawingArea.drawPixels(1, height - 21, 0, 0xffffff, width);
			Client.newBoldFont.drawBasicString(consoleInput + (Client.loopCycle % 20 < 10 ? "|" : ""), 35, height - 6, 0xffffff, 0);
			Client.newBoldFont.drawBasicString("-->", 5, height - 6, 0xffffff, 0);
			for (int index = 0, messageY = 308; index < 17; index++, messageY -= 18) {
				if (inputConsoleMessages[index] != null) {
					Client.newRegularFont.drawBasicString(inputConsoleMessages[index], 5, messageY, 0xffffff, 0);
				}
			}
			if (inputConsoleMessages[0].length() <= 0) {
				sendConsoleMessage("Type 'console_commands' for a list of commands or 'clear_console' to clear the console.", false);
			}
		}
	}
	
	public void sendConsoleCommands(String consoleCommand) {
		if (consoleCommand.equals("clear_console")) {
			for (int index = 0; index < 17; index++) {
				inputConsoleMessages[index] = null;
			}
			sendConsoleMessage("Type 'console_commands' for a list of commands or 'clear_console' to clear the console.", false);
		}
		String[] args = consoleCommand.split(" ");
		if (args == null || args.length <= 0) {
			return;
		}
		String getCommands = args[0].toLowerCase();
		switch (getCommands) {
		default:
			Client.stream.createFrame(103);
			Client.stream.writeWordBigEndian(consoleCommand.length() + 1);
			Client.stream.writeString(consoleCommand);
			break;
		}
	}
	
	public void sendConsoleMessage(String message, boolean sendMessage) {
		if (Client.backDialogID == -1) {
			Client.inputTaken = true;
		}
		for (int messagePos = 16; messagePos > 0; messagePos--) {
			inputConsoleMessages[messagePos] = inputConsoleMessages[messagePos - 1];
		}
		if (sendMessage) {
			inputConsoleMessages[0] = "--> " + message;
		} else {
			inputConsoleMessages[0] = message;
		}
	}
	
	public Console() {
		openConsole = false;
		inputConsoleMessages = new String[17];
		consoleInput = "";
	}
}