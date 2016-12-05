import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Handles the main client class
 * 
 * @author Vencillio Team
 */
public class Client extends ClientEngine {

	private static boolean filterGrayScale = false;

	private WindowFlasher windowFlasher;
	private FogHandler fogHandler = new FogHandler();

	/* Shop */
	private int currencies = 11;
	private Sprite[] currencyImage = new Sprite[currencies];
	private Sprite stock;

	/* Clan Chat */
	private String clanUsername;
	private String clanMessage;
	private String clanTitle;
	private final String[] clanTitles;
	public int channelRights;

	int playerIndex = 0;
	public static boolean controlIsDown = false;

	private void teleport(int x, int z) {
		String text = "::tele " + x + " " + z;
		stream.createFrame(103);
		stream.writeWordBigEndian(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	/* Chat colors */
	public static String chatColorHex = "00FFFF";

	private int getChatColor() {
		int convertHexCode = Integer.parseInt(chatColorHex, 16);
		return convertHexCode;
	}

	private void changeChat(String color, String name) {
		chatColorHex = color;
		sendFrame126("Color chosen: @or2@" + name, 37506);
		SettingHandler.save();
	}

	public enum ScreenMode {
		FIXED,
		RESIZABLE,
		FULLSCREEN;
	}

	public static ScreenMode frameMode = ScreenMode.FIXED;
	public static int frameWidth = 765;
	public static int frameHeight = 503;
	public static int screenAreaWidth = 512;
	public static int screenAreaHeight = 334;
	public static int cameraZoom = 600;
	public static boolean showChatComponents = true;
	public static boolean showTabComponents = true;
	public static boolean changeTabArea = frameMode == ScreenMode.FIXED ? false : true;
	public static boolean changeChatArea = frameMode == ScreenMode.FIXED ? false : true;
	public static boolean transparentTabArea = false;

	public static void frameMode(ScreenMode screenMode) {
		if (frameMode != screenMode) {
			frameMode = screenMode;
			if (screenMode == ScreenMode.FIXED) {
				frameWidth = 765;
				frameHeight = 503;
				cameraZoom = 600;
				WorldController.viewDistance = 9;
				changeChatArea = false;
				changeTabArea = false;
			} else if (screenMode == ScreenMode.RESIZABLE) {
				frameWidth = 766;
				frameHeight = 555;
				WorldController.viewDistance = 10;
			} else if (screenMode == ScreenMode.FULLSCREEN) {
				frameWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
				frameHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
				WorldController.viewDistance = 10;
			}
			rebuildFrameSize(screenMode, frameWidth, frameHeight);
			setBounds();
			System.out.println("ScreenMode: " + screenMode.toString());
		}
		showChatComponents = screenMode == ScreenMode.FIXED ? true : showChatComponents;
		showTabComponents = screenMode == ScreenMode.FIXED ? true : showTabComponents;
	}

	public static void rebuildFrameSize(ScreenMode screenMode, int screenWidth, int screenHeight) {
		try {
			screenAreaWidth = (screenMode == ScreenMode.FIXED) ? 512 : screenWidth;
			screenAreaHeight = (screenMode == ScreenMode.FIXED) ? 334 : screenHeight;
			frameWidth = screenWidth;
			frameHeight = screenHeight;
			instance.refreshFrameSize(screenMode == ScreenMode.FULLSCREEN, screenWidth, screenHeight, screenMode == ScreenMode.RESIZABLE, screenMode != ScreenMode.FIXED);
			setBounds();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshFrameSize() {
		if (frameMode == ScreenMode.RESIZABLE) {
			if (frameWidth != (appletClient() ? getGameComponent().getWidth() : gameFrame.getFrameWidth())) {
				frameWidth = (appletClient() ? getGameComponent().getWidth() : gameFrame.getFrameWidth());
				screenAreaWidth = frameWidth;
				setBounds();
			}
			if (frameHeight != (appletClient() ? getGameComponent().getHeight() : gameFrame.getFrameHeight())) {
				frameHeight = (appletClient() ? getGameComponent().getHeight() : gameFrame.getFrameHeight());
				screenAreaHeight = frameHeight;
				setBounds();
			}
		}
	}

	private static void setBounds() {
		Rasterizer.method365(frameWidth, frameHeight);
		fullScreenTextureArray = Rasterizer.anIntArray1472;
		Rasterizer.method365(frameMode == ScreenMode.FIXED ? (aRSImageProducer_1166 != null ? aRSImageProducer_1166.canvasWidth : 519) : frameWidth, frameMode == ScreenMode.FIXED ? (aRSImageProducer_1166 != null ? aRSImageProducer_1166.canvasHeight : 165) : frameHeight);
		anIntArray1180 = Rasterizer.anIntArray1472;
		Rasterizer.method365(frameMode == ScreenMode.FIXED ? (aRSImageProducer_1163 != null ? aRSImageProducer_1163.canvasWidth : 250) : frameWidth, frameMode == ScreenMode.FIXED ? (aRSImageProducer_1163 != null ? aRSImageProducer_1163.canvasHeight : 335) : frameHeight);
		anIntArray1181 = Rasterizer.anIntArray1472;
		Rasterizer.method365(screenAreaWidth, screenAreaHeight);
		anIntArray1182 = Rasterizer.anIntArray1472;
		int ai[] = new int[9];
		for (int i8 = 0; i8 < 9; i8++) {
			int k8 = 128 + i8 * 32 + 15;
			int l8 = 600 + k8 * 3;
			int i9 = Rasterizer.anIntArray1470[k8];
			ai[i8] = l8 * i9 >> 16;
		}
		if (frameMode == ScreenMode.RESIZABLE && (frameWidth >= 766) && (frameWidth <= 1025) && (frameHeight >= 504) && (frameHeight <= 850)) {
			WorldController.viewDistance = 9;
			cameraZoom = 375;
		} else if (frameMode == ScreenMode.FIXED) {
			WorldController.viewDistance = 9;
			cameraZoom = 600;
		} else if (frameMode == ScreenMode.RESIZABLE) {
			WorldController.viewDistance = 10;
			cameraZoom = 600;
		} else if (frameMode == ScreenMode.FULLSCREEN) {
			WorldController.viewDistance = 10;
			cameraZoom = 550;
		}
		if (extendChatArea > frameHeight - 170) {
			extendChatArea = frameHeight - 170;
		}
		WorldController.method310(500, 800, frameMode == ScreenMode.FIXED ? 512 : frameWidth, frameMode == ScreenMode.FIXED ? 334 : frameHeight, ai);
		if (loggedIn) {
			aRSImageProducer_1165 = new ImageProducer(frameMode == ScreenMode.FIXED ? 512 : frameWidth, frameMode == ScreenMode.FIXED ? 334 : frameHeight);
		}
	}

	public boolean getMousePositions() {
		if (mouseInRegion(frameWidth - (frameWidth <= 1000 ? 240 : 452), frameHeight - (frameWidth <= 1000 ? 90 : 37), frameWidth, frameHeight)) {
			return false;
		}
		if (showChatComponents) {
			if (changeChatArea) {
				if (super.mouseX > 0 && super.mouseX < 494 && super.mouseY > frameHeight - 175 - extendChatArea && super.mouseY < frameHeight) {
					return true;
				} else {
					if (super.mouseX > 494 && super.mouseX < 515 && super.mouseY > frameHeight - 175 - extendChatArea && super.mouseY < frameHeight) {
						return false;
					}
				}
			} else if (!changeChatArea) {
				if (super.mouseX > 0 && super.mouseX < 519 && super.mouseY > frameHeight - 175 && super.mouseY < frameHeight) {
					return false;
				}
			}
		}
		if (super.mouseX > frameWidth - 216 && super.mouseX < frameWidth && super.mouseY > 0 && super.mouseY < 172) {
			return false;
		}
		if (!changeTabArea) {
			if (super.mouseX > 0 && super.mouseY > 0 && super.mouseY < frameWidth && super.mouseY < frameHeight) {
				if (super.mouseX >= frameWidth - 242 && super.mouseY >= frameHeight - 335) {
					return false;
				}
				return true;
			}
			return false;
		}
		if (showTabComponents) {
			if (frameWidth > 1000) {
				if (super.mouseX >= frameWidth - 420 && super.mouseX <= frameWidth && super.mouseY >= frameHeight - 37 && super.mouseY <= frameHeight || super.mouseX > frameWidth - 225 && super.mouseX < frameWidth && super.mouseY > frameHeight - 37 - 274 && super.mouseY < frameHeight) {
					return false;
				}
			} else {
				if (super.mouseX >= frameWidth - 210 && super.mouseX <= frameWidth && super.mouseY >= frameHeight - 74 && super.mouseY <= frameHeight || super.mouseX > frameWidth - 225 && super.mouseX < frameWidth && super.mouseY > frameHeight - 74 - 274 && super.mouseY < frameHeight) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean mouseInRegion(int x1, int y1, int x2, int y2) {
		if (super.mouseX >= x1 && super.mouseX <= x2 && super.mouseY >= y1 && super.mouseY <= y2) {
			return true;
		}
		return false;
	}

	public boolean clickInRegion(int x1, int y1, int x2, int y2) {
		if (super.saveClickX >= x1 && super.saveClickX <= x2 && super.saveClickY >= y1 && super.saveClickY <= y2) {
			return true;
		}
		return false;
	}

	public boolean mouseMapPosition() {
		if (super.mouseX >= frameWidth - 21 && super.mouseX <= frameWidth && super.mouseY >= 0 && super.mouseY <= 21) {
			return false;
		}
		return true;
	}

	private void drawLoadingMessages(int used, String s, String s1) {
		int width = regularText.getTextWidth(used == 1 ? s : s1);
		int height = s1 == null ? 25 : 38;
		DrawingArea.drawPixels(height, 1, 1, 0, width + 6);
		DrawingArea.drawPixels(1, 1, 1, 0xffffff, width + 6);
		DrawingArea.drawPixels(height, 1, 1, 0xffffff, 1);
		DrawingArea.drawPixels(1, height, 1, 0xffffff, width + 6);
		DrawingArea.drawPixels(height, 1, width + 6, 0xffffff, 1);
		regularText.drawText(0xffffff, s, 18, width / 2 + 5);
		if (s1 != null) {
			regularText.drawText(0xffffff, s1, 31, width / 2 + 5);
		}
	}

	private int[][] statsSkillGoal = new int[Skills.SKILLS_COUNT + 1][3];
	private int[] tabAmounts = new int[] { 350, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private int[] bankInvTemp = new int[352];
	private int[] bankStackTemp = new int[352];

	private boolean canGainXP = true;
	private LinkedList<XPGain> gains = new LinkedList<XPGain>();

	public void addXP(int skillID, int xp) {
		if (xp != 0 && canGainXP) {
			gains.add(new XPGain(skillID, xp));
		}
	}

	public class XPGain {
		private int skill;
		private int xp;
		private int y;
		private int alpha = 0;

		public XPGain(int skill, int xp) {
			this.skill = skill;
			this.xp = xp;
		}

		public void increaseY() {
			y++;
		}

		public int getSkill() {
			return skill;
		}

		public int getXP() {
			return xp;
		}

		public int getY() {
			return y;
		}

		public int getAlpha() {
			return alpha;
		}

		public void increaseAlpha() {
			alpha += alpha < 256 ? 30 : 0;
			alpha = alpha > 256 ? 256 : alpha;
		}

		public void decreaseAlpha() {
			alpha -= alpha > 0 ? 30 : 0;
			alpha = alpha > 256 ? 256 : alpha;
		}
	}

	private static final long serialVersionUID = 5707517957054703648L;

	/**
	 * npcBits can be changed to what your server's bits are set to.
	 */
	public static int npcBits = 16;

	/**
	 * Client Preferences
	 * 
	 * hitMarks554 can be enabled or disabled between 554 and 317. hitBar554 can
	 * be enabled or disabled between 554 and 317. sumOrb can be changed to
	 * runEnergy can be set to true to enable run energy support.
	 */
	public boolean runEnergy = false;

	private static String intToKOrMilLongName(int i) {
		String s = String.valueOf(i);
		for (int k = s.length() - 3; k > 0; k -= 3)
			s = s.substring(0, k) + "," + s.substring(k);
		if (s.length() > 8)
			s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
		else if (s.length() > 4)
			s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
		return " " + s;
	}

	public final String methodR(int j) {
		if (j >= 0 && j < 10000)
			return String.valueOf(j);
		if (j >= 10000 && j < 10000000)
			return j / 1000 + "K";
		if (j >= 10000000 && j < 999999999)
			return j / 1000000 + "M";
		if (j >= 999999999)
			return "*";
		else
			return "?";
	}

	public void sendString(String str, int i) {
		RSInterface.interfaceCache[i].disabledMessage = str;
		// needDrawTabArea = true;
		inputTaken = true;
	}

	public void sendStringAsLong(String string) {
		stream.createFrame(60);
		stream.writeQWord(TextClass.longForName(string));
	}

	public void sendString(int identifier, String text) {
		text = identifier + "," + text;
		stream.createFrame(127);
		stream.writeWordBigEndian(text.length() + 1);
		stream.writeString(text);
	}

	private void stopMidi() {
		Signlink.midifade = 0;
		Signlink.midi = "stop";
	}

	private boolean menuHasAddFriend(int j) {
		if (j < 0)
			return false;
		int k = menuActionID[j];
		if (k >= 2000)
			k -= 2000;
		return k == 337;
	}

	private final int[] modeX = { 164, 230, 296, 362 }, modeNamesX = { 26, 86, 150, 212, 286, 349, 427 }, modeNamesY = { 158, 158, 153, 153, 153, 153, 158 }, channelButtonsX = { 5, 71, 137, 203, 269, 335, 404 };

	private final String[] modeNames = { "All", "Game", "Public", "Private", "Clan", "Trade", "Report Abuse" };

	public void drawChannelButtons() {
		final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 165;
		fixedGameComponents[3].drawSprite(0, 143 + yOffset);
		String text[] = { "On", "Friends", "Off", "Hide" };
		int textColor[] = { 65280, 0xffff00, 0xff0000, 65535 };
		switch (cButtonCPos) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			channelButtons[1].drawSprite(channelButtonsX[cButtonCPos], 143 + yOffset);
			break;
		}
		if (cButtonHPos == cButtonCPos) {
			switch (cButtonHPos) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				channelButtons[2].drawSprite(channelButtonsX[cButtonHPos], 143 + yOffset);
				break;
			}
		} else {
			switch (cButtonHPos) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				channelButtons[0].drawSprite(channelButtonsX[cButtonHPos], 143 + yOffset);
				break;
			case 6:
				channelButtons[3].drawSprite(channelButtonsX[cButtonHPos], 143 + yOffset);
				break;
			}
		}
		int[] modes = { publicChatMode, privateChatMode, clanChatMode, tradeMode };
		for (int i = 0; i < modeNamesX.length; i++) {
			smallText.method389(true, modeNamesX[i], 0xffffff, modeNames[i], modeNamesY[i] + yOffset);
		}
		for (int i = 0; i < modeX.length; i++) {
			smallText.method382(textColor[modes[i]], modeX[i], text[modes[i]], 164 + yOffset, true);
		}
	}

	public boolean isExtendingChatArea;
	public static int extendChatArea = 0;

	public void extendChatArea() {
		if (frameMode == ScreenMode.FIXED) {
			extendChatArea = 0;
			return;
		}
		int offsetY = frameHeight - 160;
		int x = 256;
		int y = offsetY - 10 - extendChatArea;
		if (super.clickMode2 == 1 && super.mouseX >= x && super.mouseX <= x + 8 && super.mouseY >= y && super.mouseY <= y + 9) {
			isExtendingChatArea = true;
		}
		if (isExtendingChatArea) {
			int height = offsetY - super.mouseY;
			if (height < frameHeight - 170) {
				extendChatArea = height > 0 ? height : 0;
			}
		}
	}

	private boolean chatStateCheck() {
		return messagePromptRaised || inputDialogState != 0 || aString844 != null || backDialogID != -1 || dialogID != -1;
	}

	public static String getTime() {
		Calendar calendar = new GregorianCalendar();
		String meridiem;
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		if (calendar.get(Calendar.AM_PM) == 0) {
			meridiem = "AM";
		} else {
			meridiem = "PM";
		}
		return "["+ hour + ":" + minute + " " + meridiem +"]";
	}

	private void drawChatArea() {
		int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 165;
		if (frameMode == ScreenMode.FIXED) {
			aRSImageProducer_1166.initDrawingArea();
		}
		Rasterizer.anIntArray1472 = anIntArray1180;
		if (chatStateCheck()) {
			showChatComponents = true;
			fixedGameComponents[2].drawSprite(0, yOffset);
		}
		if (showChatComponents) {
			if (changeChatArea && !chatStateCheck()) {
				orbComponents3[6].drawTransparentSprite(256, yOffset - extendChatArea - 3, 112);
				DrawingArea.drawAlphaGradient(7, 7 + yOffset - extendChatArea, 505, 130 + extendChatArea, 0, 0, 40);
				DrawingArea.drawAlphaHorizontalLine(7, 6 + yOffset - extendChatArea, 405, 0x6d6a57, 256);
			} else {
				fixedGameComponents[2].drawSprite(0, yOffset);
			}
		}
		if (!showChatComponents || changeChatArea) {
			DrawingArea.drawAlphaPixels(7, frameHeight - 23, 506, 24, 0, 100);
		}
		drawChannelButtons();
		TextDrawingArea textDrawingArea = regularText;
		if (messagePromptRaised) {
			extendChatArea = 0;
			newBoldFont.drawCenteredString(aString1121, 259, 60 + yOffset, 0, -1);
			newBoldFont.drawCenteredString(promptInput + "*", 259, 80 + yOffset, 128, -1);
		} else if (inputDialogState == 1) {
			extendChatArea = 0;
			newBoldFont.drawCenteredString("Enter amount:", 259, yOffset + 60, 0, -1);
			newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
		} else if (inputDialogState == 2) {
			extendChatArea = 0;
			newBoldFont.drawCenteredString("Enter Name:", 259, 60 + yOffset, 0, -1);
			newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
		} else if (aString844 != null) {
			extendChatArea = 0;
			newBoldFont.drawCenteredString(aString844, 259, 60 + yOffset, 0, -1);
			newBoldFont.drawCenteredString("Click to continue", 259, 80 + yOffset, 128, -1);
		} else if (backDialogID != -1) {
			extendChatArea = 0;
			drawInterface(0, 20, RSInterface.interfaceCache[backDialogID], 20 + yOffset);
		} else if (dialogID != -1) {
			extendChatArea = 0;
			drawInterface(0, 20, RSInterface.interfaceCache[dialogID], 20 + yOffset);
		} else if (showChatComponents) {
			int j77 = -3;
			int j = 0;
			int shadow = changeChatArea ? 0 : -1;
			DrawingArea.setDrawingArea(121 + yOffset, 7, 498, 7 + yOffset - extendChatArea);
			for (int k = 0; k < 500; k++) {
				if (chatMessages[k] != null) {
					String title;
					if (chatTitles[k] != null) {
						title = "<col=" + chatColors[k] + ">" + chatTitles[k] + "</col> ";
					} else {
						title = "";
					}
					int chatType = chatTypes[k];
					int yPos = (70 - j77 * 14) + anInt1089 + 5;
					String s1 = chatNames[k];
					String timeStamp = getTime();
					byte playerRights = 0;
					if (s1 != null && s1.startsWith("@cr")) {
						int test1 = Integer.parseInt("" + s1.charAt(3));
						if (s1.charAt(4) != '@') {
							test1 = Integer.parseInt(s1.charAt(3) + "" + s1.charAt(4));
							s1 = s1.substring(6);
						} else {
							s1 = s1.substring(5);
						}
						playerRights = (byte) test1;
					}
					if (chatType == 0) {
						if (chatTypeView == 5 || chatTypeView == 0) {
							newRegularFont.drawBasicString(chatMessages[k], 11, yPos + yOffset, changeChatArea ? 0xFFFFFF : 0, shadow);
							j++;
							j77++;
						}
					}
					if ((chatType == 1 || chatType == 2) && (chatType == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 1 || chatTypeView == 0) {
							int xPos = 11;
							if (Configuration.enableTimeStamps) {
								newRegularFont.drawBasicString(timeStamp, xPos, yPos + yOffset, changeChatArea ? 0xFFFFFF : 0, shadow);
								xPos += newRegularFont.getTextWidth(timeStamp);
							}
							if (playerRights >= 1) {
								modIcons[playerRights - 1].drawSprite(xPos, yPos - 12 + yOffset);
								xPos += 16;
							}
							newRegularFont.drawBasicString(title + s1 + ":", xPos - 3, yPos + yOffset, changeChatArea ? 0xFFFFFF : 0, shadow);
							xPos += newRegularFont.getTextWidth(title + s1) + 8;
							newRegularFont.drawBasicString(chatMessages[k], xPos - 8, yPos + yOffset, changeChatArea ? 0x7FA9FF : 255, shadow);
							j++;
							j77++;
						}
					}
					if ((chatType == 3 || chatType == 7) && (splitPrivateChat == 0 || chatTypeView == 2) && (chatType == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							int k1 = 11;
							newRegularFont.drawBasicString("From", k1, yPos + yOffset, changeChatArea ? 0xFFFFFF : 0, shadow);
							k1 += textDrawingArea.getTextWidth("From ");
							if (playerRights >= 1) {
								modIcons[playerRights - 1].drawSprite(k1, yPos - 12 + yOffset);
								k1 += 12;
							}
							newRegularFont.drawBasicString(s1 + ":", k1, yPos + yOffset, changeChatArea ? 0xFFFFFF : 0, shadow);
							k1 += textDrawingArea.getTextWidth(s1) + 8;
							newRegularFont.drawBasicString(chatMessages[k], k1, yPos + yOffset, changeChatArea ? 0xFF5256 : 0x800000, shadow);
							j++;
							j77++;
						}
					}
					if (chatType == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 3 || chatTypeView == 0) {
							newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 11, yPos + yOffset, changeChatArea ? 0xFF5256 : 0x800080, shadow);
							j++;
							j77++;
						}
					}
					if (chatType == 5 && splitPrivateChat == 0 && privateChatMode < 2) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 8, yPos + yOffset, changeChatArea ? 0xFF5256 : 0x800000, shadow);
							j++;
							j77++;
						}
					}
					if (chatType == 6 && (splitPrivateChat == 0 || chatTypeView == 2) && privateChatMode < 2) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							newRegularFont.drawBasicString("To " + s1 + ":", 11, yPos + yOffset, changeChatArea ? 0xFFFFFF : 0, shadow);
							newRegularFont.drawBasicString(chatMessages[k], 15 + textDrawingArea.getTextWidth("To :" + s1), yPos + yOffset, changeChatArea ? 0xFF5256 : 0x800000, shadow);
							j++;
							j77++;
						}
					}
					if (chatType == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 3 || chatTypeView == 0) {
							newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 11, yPos + yOffset, changeChatArea ? 0xFF5256 : 0x800000, shadow);
							j++;
							j77++;
						}
					}
					if (chatType == 11) {
						if (chatTypeView == 11) {
							newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 8, yPos + yOffset, changeChatArea ? 0xFF5256 : 0x800000, shadow);
							j++;
							j77++;
						}
					}
				}
			}
			DrawingArea.defaultDrawingAreaSize();
			anInt1211 = j * 14 + 7 + 5;
			if (anInt1211 < 111) {
				anInt1211 = 111;
			}
			drawScrollbar(114 + extendChatArea, anInt1211 - anInt1089 - 113, 7 + yOffset - extendChatArea, 496, anInt1211 + extendChatArea, changeChatArea);
			String title;
			if (myPlayer != null && myPlayer.title != null) {
				title = "<col=" + myPlayer.titleColor + ">" + myPlayer.title + " </col>";
			} else {
				title = "";
			}
			String playerName;
			if (myPlayer != null && myPlayer.name != null) {
				playerName = myPlayer.name;
			} else {
				playerName = TextClass.fixName(myUsername);
			}
			DrawingArea.setDrawingArea(140 + yOffset, 8, 509, 120 + yOffset);
			int xPos = 0;
			int yPos = 0;
			if (myPrivilege == 0) {
				cacheSprite[347].drawSprite(textDrawingArea.getTextWidth(myPlayer.title + (title.equals(null) ? "" : " ") + playerName + ": ") + 4, 124 + yOffset);
				newRegularFont.drawBasicString(title + "" + playerName + "", 8, 136 + yOffset - 2, changeChatArea ? 0xFFFFFF : 0, changeChatArea ? 0 : -1);
				textDrawingArea.method385(changeChatArea ? 0xFFFFFF : 0, ": ", 136 + yOffset - 2, 17 + textDrawingArea.getTextWidth(myPlayer.title + (title.equals(null) ? "" : " ") + 8 + playerName));
				newRegularFont.drawBasicString(inputString + "*", 22 + textDrawingArea.getTextWidth(myPlayer.title + (title.equals(null) ? "" : " ") + playerName + ": "), 136 + yOffset - 2, changeChatArea ? 0x7FA9FF : 255, changeChatArea ? 0 : -1);
			} else if (myPrivilege >= 1) {
				modIcons[myPrivilege - 1].drawSprite(10 + xPos, 122 + yPos + yOffset);
				cacheSprite[347].drawSprite(textDrawingArea.getTextWidth(myPlayer.title + (title.equals(null) ? "" : " ") + playerName + ": ") + 18, 124 + yOffset);
				xPos += 15;
				newRegularFont.drawBasicString(title + playerName + "", 23, 136 + yOffset - 2, changeChatArea ? 0xFFFFFF : 0, changeChatArea ? 0 : -1);
				textDrawingArea.method385(changeChatArea ? 0xFFFFFF : 0, ": ", 136 + yOffset - 2, 38 + textDrawingArea.getTextWidth(myPlayer.title + (title.equals(null) ? "" : " ") + playerName));
				newRegularFont.drawBasicString(inputString + "*", 23 + textDrawingArea.getTextWidth(myPlayer.title + (title.equals(null) ? "" : " ") + playerName + ": ") + 13, 136 + yOffset - 2, changeChatArea ? 0x7FA9FF : 255, changeChatArea ? 0 : -1);
			}
			DrawingArea.defaultDrawingAreaSize();
			for (int i = 0; i < 505; i++) {
				int opacity = 100 - (int) (i / 5.05);
				DrawingArea.method340(0, 1, 121 + yOffset, opacity + 5, 7 + i);
			}
		}
		if (menuOpen) {
			drawMenu(0, frameMode == ScreenMode.FIXED ? 338 : 0);
		}
		if (frameMode == ScreenMode.FIXED) {
			aRSImageProducer_1166.drawGraphics(338, super.graphics, 0);
		}
		aRSImageProducer_1165.initDrawingArea();
		Rasterizer.anIntArray1472 = anIntArray1182;
	}

	public void init() {
		try {
			nodeID = 10;
			portOff = 0;
			setHighMem();
			isMembers = true;
			Signlink.storeid = 32;
			Signlink.startpriv(InetAddress.getLocalHost());
			initClientFrame(frameWidth, frameHeight);
			instance = this;
			SettingHandler.load();
		} catch (Exception exception) {
			return;
		}
	}

	public void startRunnable(Runnable runnable, int i) {
		if (i > 10) {
			i = 10;
		}
		if (Signlink.mainapp != null) {
			Signlink.startthread(runnable, i);
		} else {
			super.startRunnable(runnable, i);
		}
	}

	public Socket openSocket(int port) throws IOException {
		return new Socket(InetAddress.getByName(ClientConstants.LOCALHOST ? "localhost" : server), port);
	}

	private void processMenuClick() {
		if (activeInterfaceType != 0)
			return;
		int j = super.clickMode3;
		if (spellSelected == 1 && super.saveClickX >= 516 && super.saveClickY >= 160 && super.saveClickX <= 765 && super.saveClickY <= 205)
			j = 0;
		if (menuOpen) {
			if (j != 1) {
				int k = super.mouseX;
				int j1 = super.mouseY;
				if (menuScreenArea == 0) {
					k -= 4;
					j1 -= 4;
				}
				if (menuScreenArea == 1) {
					k -= 519;
					j1 -= 168;
				}
				if (menuScreenArea == 2) {
					k -= 17;
					j1 -= 338;
				}
				if (menuScreenArea == 3) {
					k -= 519;
					j1 -= 0;
				}
				if (k < menuOffsetX - 10 || k > menuOffsetX + menuWidth + 10 || j1 < menuOffsetY - 10 || j1 > menuOffsetY + menuHeight + 10) {
					menuOpen = false;
					if (menuScreenArea == 1) {
					}
					if (menuScreenArea == 2)
						inputTaken = true;
				}
			}
			if (j == 1) {
				int l = menuOffsetX;
				int k1 = menuOffsetY;
				int i2 = menuWidth;
				int k2 = super.saveClickX;
				int l2 = super.saveClickY;
				switch (menuScreenArea) {
				case 0:
					k2 -= 4;
					l2 -= 4;
					break;
				case 1:
					k2 -= 519;
					l2 -= 168;
					break;
				case 2:
					k2 -= 5;
					l2 -= 338;
					break;
				case 3:
					k2 -= 519;
					l2 -= 0;
					break;
				}
				int i3 = -1;
				for (int j3 = 0; j3 < menuActionRow; j3++) {
					int k3 = k1 + 31 + (menuActionRow - 1 - j3) * 15;
					if (k2 > l && k2 < l + i2 && l2 > k3 - 13 && l2 < k3 + 3)
						i3 = j3;
				}
				if (i3 != -1)
					doAction(i3);
				menuOpen = false;
				if (menuScreenArea == 1) {
				}
				if (menuScreenArea == 2) {
					inputTaken = true;
				}
			}

			if (RSInterface.currentInputField != null) {
				if (RSInterface.currentInputField.onlyNumbers) {
					long amount = 0;

					try {
						amount = Long.parseLong(message.replaceAll(",", ""));

						// overflow concious code
						if (amount < -Integer.MAX_VALUE) {
							amount = -Integer.MAX_VALUE;
						} else if (amount > Integer.MAX_VALUE) {
							amount = Integer.MAX_VALUE;
						}
					} catch (Exception ignored) {
					}

					if (amount > 0) {
						stream.createFrame(208);
						stream.writeDWord((int) amount);
					}
				} else {
					stream.createFrame(150);
					stream.writeWordBigEndian(RSInterface.currentInputField.disabledMessage.length() + 3);
					stream.writeWord(RSInterface.currentInputField.id);
					stream.writeString(RSInterface.currentInputField.disabledMessage);
				}

				
				RSInterface.currentInputField.disabledMessage = "";
			}
			RSInterface.currentInputField = null;
		} else {
			if (j == 1 && menuActionRow > 0) {
				int i1 = menuActionID[menuActionRow - 1];
				if (i1 == 632 || i1 == 78 || i1 == 867 || i1 == 431 || i1 == 53 || i1 == 74 || i1 == 454 || i1 == 539 || i1 == 493 || i1 == 847 || i1 == 447 || i1 == 1125) {
					int l1 = menuActionCmd2[menuActionRow - 1];
					int j2 = menuActionCmd3[menuActionRow - 1];
					RSInterface class9 = RSInterface.interfaceCache[j2];
					if (class9.aBoolean259 || class9.aBoolean235) {
						aBoolean1242 = false;
						dragCycle = 0;
						focusedDragWidget = j2;
						dragFromSlot = l1;
						activeInterfaceType = 2;
						pressX = super.saveClickX;
						pressY = super.saveClickY;
						if (RSInterface.interfaceCache[j2].parentID == openInterfaceID)
							activeInterfaceType = 1;
						if (RSInterface.interfaceCache[j2].parentID == backDialogID)
							activeInterfaceType = 3;
						return;
					}
				}
			}
			if (j == 1 && (anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2)
				j = 2;
			if (j == 1 && menuActionRow > 0)
				doAction(menuActionRow - 1);
			if (j == 2 && menuActionRow > 0)
				determineMenuSize();
			processMainScreenClick();
			processTabClick();
			processChatModeClick();
			minimapHovers();
		}
	}

	public static String getFileNameWithoutExtension(String fileName) {
		File tmpFile = new File(fileName);
		tmpFile.getName();
		int whereDot = tmpFile.getName().lastIndexOf('.');
		if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2) {
			return tmpFile.getName().substring(0, whereDot);
		}
		return "";
	}

	private void saveMidi(boolean flag, byte abyte0[]) {
		Signlink.midifade = flag ? 1 : 0;
		Signlink.midisave(abyte0, abyte0.length);
	}

	private void method22() {
		try {
			anInt985 = -1;
			aClass19_1056.removeAll();
			aClass19_1013.removeAll();
			Rasterizer.method366();
			unlinkMRUNodes();
			worldController.initToNull();
			System.gc();
			for (int i = 0; i < 4; i++)
				aClass11Array1230[i].method210();
			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++)
						byteGroundArray[l][k1][j2] = 0;
				}
			}

			ObjectManager objectManager = new ObjectManager(byteGroundArray, intGroundArray);
			int k2 = aByteArrayArray1183.length;
			stream.createFrame(0);
			if (!aBoolean1159) {
				for (int i3 = 0; i3 < k2; i3++) {
					int i4 = (anIntArray1234[i3] >> 8) * 64 - baseX;
					int k5 = (anIntArray1234[i3] & 0xff) * 64 - baseY;
					byte abyte0[] = aByteArrayArray1183[i3];
					if (abyte0 != null)
						objectManager.method180(abyte0, k5, i4, (anInt1069 - 6) * 8, (anInt1070 - 6) * 8, aClass11Array1230);
				}
				for (int j4 = 0; j4 < k2; j4++) {
					int l5 = (anIntArray1234[j4] >> 8) * 64 - baseX;
					int k7 = (anIntArray1234[j4] & 0xff) * 64 - baseY;
					byte abyte2[] = aByteArrayArray1183[j4];
					if (abyte2 == null && anInt1070 < 800)
						objectManager.method174(k7, 64, 64, l5);
				}
				anInt1097++;
				if (anInt1097 > 160) {
					anInt1097 = 0;
					stream.createFrame(238);
					stream.writeWordBigEndian(96);
				}
				stream.createFrame(0);
				for (int i6 = 0; i6 < k2; i6++) {
					byte abyte1[] = aByteArrayArray1247[i6];
					if (abyte1 != null) {
						int l8 = (anIntArray1234[i6] >> 8) * 64 - baseX;
						int k9 = (anIntArray1234[i6] & 0xff) * 64 - baseY;
						objectManager.method190(l8, aClass11Array1230, k9, worldController, abyte1);
					}
				}

			}
			if (aBoolean1159) {
				for (int j3 = 0; j3 < 4; j3++) {
					for (int k4 = 0; k4 < 13; k4++) {
						for (int j6 = 0; j6 < 13; j6++) {
							int l7 = anIntArrayArrayArray1129[j3][k4][j6];
							if (l7 != -1) {
								int i9 = l7 >> 24 & 3;
								int l9 = l7 >> 1 & 3;
								int j10 = l7 >> 14 & 0x3ff;
								int l10 = l7 >> 3 & 0x7ff;
								int j11 = (j10 / 8 << 8) + l10 / 8;
								for (int l11 = 0; l11 < anIntArray1234.length; l11++) {
									if (anIntArray1234[l11] != j11 || aByteArrayArray1183[l11] == null)
										continue;
									objectManager.method179(i9, l9, aClass11Array1230, k4 * 8, (j10 & 7) * 8, aByteArrayArray1183[l11], (l10 & 7) * 8, j3, j6 * 8);
									break;
								}

							}
						}
					}
				}
				for (int l4 = 0; l4 < 13; l4++) {
					for (int k6 = 0; k6 < 13; k6++) {
						int i8 = anIntArrayArrayArray1129[0][l4][k6];
						if (i8 == -1)
							objectManager.method174(k6 * 8, 8, 8, l4 * 8);
					}
				}

				stream.createFrame(0);
				for (int l6 = 0; l6 < 4; l6++) {
					for (int j8 = 0; j8 < 13; j8++) {
						for (int j9 = 0; j9 < 13; j9++) {
							int i10 = anIntArrayArrayArray1129[l6][j8][j9];
							if (i10 != -1) {
								int k10 = i10 >> 24 & 3;
								int i11 = i10 >> 1 & 3;
								int k11 = i10 >> 14 & 0x3ff;
								int i12 = i10 >> 3 & 0x7ff;
								int j12 = (k11 / 8 << 8) + i12 / 8;
								for (int k12 = 0; k12 < anIntArray1234.length; k12++) {
									if (anIntArray1234[k12] != j12 || aByteArrayArray1247[k12] == null)
										continue;
									objectManager.method183(aClass11Array1230, worldController, k10, j8 * 8, (i12 & 7) * 8, l6, aByteArrayArray1247[k12], (k11 & 7) * 8, i11, j9 * 8);
									break;
								}

							}
						}

					}

				}

			}
			stream.createFrame(0);
			objectManager.method171(aClass11Array1230, worldController);
			ColorUtility.fadingToColor = getNextInteger(objectManager.colors).getKey();
			aRSImageProducer_1165.initDrawingArea();
			stream.createFrame(0);
			int k3 = ObjectManager.anInt145;
			if (k3 > plane)
				k3 = plane;
			if (k3 < plane - 1)
				k3 = plane - 1;
			if (lowMem)
				worldController.method275(ObjectManager.anInt145);
			else
				worldController.method275(0);
			for (int i5 = 0; i5 < 104; i5++) {
				for (int i7 = 0; i7 < 104; i7++)
					spawnGroundItem(i5, i7);

			}

			anInt1051++;
			if (anInt1051 > 98) {
				anInt1051 = 0;
			}
			method63();
		} catch (Exception exception) {
		}
		ObjectDef.mruNodes1.unlinkAll();
		if (super.gameFrame != null) {
			stream.createFrame(210);
			stream.writeDWord(0x3f008edd);
		}
		if (lowMem && Signlink.cache_dat != null) {
			int j = onDemandFetcher.getVersionCount(0);
			for (int i1 = 0; i1 < j; i1++) {
				int l1 = onDemandFetcher.getModelIndex(i1);
				if ((l1 & 0x79) == 0)
					Model.method461(i1);
			}

		}
		System.gc();
		Rasterizer.method367();
		onDemandFetcher.method566();
		int k = (anInt1069 - 6) / 8 - 1;
		int j1 = (anInt1069 + 6) / 8 + 1;
		int i2 = (anInt1070 - 6) / 8 - 1;
		int l2 = (anInt1070 + 6) / 8 + 1;
		if (aBoolean1141) {
			k = 49;
			j1 = 50;
			i2 = 49;
			l2 = 50;
		}
		for (int l3 = k; l3 <= j1; l3++) {
			for (int j5 = i2; j5 <= l2; j5++)
				if (l3 == k || l3 == j1 || j5 == i2 || j5 == l2) {
					int j7 = onDemandFetcher.method562(0, j5, l3);
					if (j7 != -1)
						onDemandFetcher.method560(j7, 3);
					int k8 = onDemandFetcher.method562(1, j5, l3);
					if (k8 != -1)
						onDemandFetcher.method560(k8, 3);
				}

		}

	}

	public static AbstractMap.SimpleEntry<Integer, Integer> getNextInteger(ArrayList<Integer> values) {
		ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> frequencies = new ArrayList<>();
		int maxIndex = 0;
		main: for (int i = 0; i < values.size(); ++i) {
			int value = values.get(i);
			for (int j = 0; j < frequencies.size(); ++j) {
				if (frequencies.get(j).getKey() == value) {
					frequencies.get(j).setValue(frequencies.get(j).getValue() + 1);
					if (frequencies.get(maxIndex).getValue() < frequencies.get(j).getValue()) {
						maxIndex = j;
					}
					continue main;
				}
			}
			frequencies.add(new AbstractMap.SimpleEntry<Integer, Integer>(value, 1));
		}
		return frequencies.get(maxIndex);
	}

	private void unlinkMRUNodes() {
		ObjectDef.mruNodes1.unlinkAll();
		ObjectDef.mruNodes2.unlinkAll();
		EntityDef.mruNodes.unlinkAll();
		ItemDef.mruNodes2.unlinkAll();
		ItemDef.mruNodes1.unlinkAll();
		Player.mruNodes.unlinkAll();
		SpotAnim.aMRUNodes_415.unlinkAll();
	}

	private void method24(int i) {
		int ai[] = minimapImage.myPixels;
		int j = ai.length;
		for (int k = 0; k < j; k++)
			ai[k] = 0;

		for (int l = 1; l < 103; l++) {
			int i1 = 24628 + (103 - l) * 512 * 4;
			for (int k1 = 1; k1 < 103; k1++) {
				if ((byteGroundArray[i][k1][l] & 0x18) == 0)
					worldController.method309(ai, i1, i, k1, l);
				if (i < 3 && (byteGroundArray[i + 1][k1][l] & 8) != 0)
					worldController.method309(ai, i1, i + 1, k1, l);
				i1 += 4;
			}

		}

		int j1 = 0xFFFFFF;
		int l1 = 0xEE0000;
		minimapImage.method343();
		for (int i2 = 1; i2 < 103; i2++) {
			for (int j2 = 1; j2 < 103; j2++) {
				if ((byteGroundArray[i][j2][i2] & 0x18) == 0)
					method50(i2, j1, j2, l1, i);
				if (i < 3 && (byteGroundArray[i + 1][j2][i2] & 8) != 0)
					method50(i2, j1, j2, l1, i + 1);
			}

		}

		aRSImageProducer_1165.initDrawingArea();
		anInt1071 = 0;
		for (int k2 = 0; k2 < 104; k2++) {
			for (int l2 = 0; l2 < 104; l2++) {
				int i3 = worldController.method303(plane, k2, l2);
				if (i3 != 0) {
					i3 = i3 >> 14 & 0x7fff;
					int j3 = ObjectDef.forID(i3).anInt746;
					if (j3 >= 0) {
						int k3 = k2;
						int l3 = l2;
						aClass30_Sub2_Sub1_Sub1Array1140[anInt1071] = mapFunctions[j3];
						anIntArray1072[anInt1071] = k3;
						anIntArray1073[anInt1071] = l3;
						anInt1071++;
					}
				}
			}

		}

	}

	private void spawnGroundItem(int i, int j) {
		NodeList class19 = groundArray[plane][i][j];
		if (class19 == null) {
			worldController.method295(plane, i, j);
			return;
		}
		int k = 0xfa0a1f01;
		Object obj = null;
		for (Item item = (Item) class19.reverseGetFirst(); item != null; item = (Item) class19.reverseGetNext()) {
			ItemDef itemDef = ItemDef.forID(item.ID);
			int l = itemDef.value;
			if (itemDef.stackable) {
				l *= item.anInt1559 + 1;
			}
			if (l > k) {
				k = l;
				obj = item;
			}
		}
		class19.insertTail(((Node) (obj)));
		Object obj1 = null;
		Object obj2 = null;
		for (Item class30_sub2_sub4_sub2_1 = (Item) class19.reverseGetFirst(); class30_sub2_sub4_sub2_1 != null; class30_sub2_sub4_sub2_1 = (Item) class19.reverseGetNext()) {
			if (class30_sub2_sub4_sub2_1.ID != ((Item) (obj)).ID && obj1 == null) {
				obj1 = class30_sub2_sub4_sub2_1;
			}
			if (class30_sub2_sub4_sub2_1.ID != ((Item) (obj)).ID && class30_sub2_sub4_sub2_1.ID != ((Item) (obj1)).ID && obj2 == null) {
				obj2 = class30_sub2_sub4_sub2_1;
			}
		}
		int i1 = i + (j << 7) + 0x60000000;
		worldController.method281(i, i1, ((Animable) (obj1)), method42(plane, j * 128 + 64, i * 128 + 64), ((Animable) (obj2)), ((Animable) (obj)), plane, j);
	}

	private void method26(boolean flag) {
		for (int j = 0; j < npcCount; j++) {
			Npc npc = npcArray[npcIndices[j]];
			int k = 0x20000000 + (npcIndices[j] << 14);
			if (npc == null || !npc.isVisible() || npc.desc.aBoolean93 != flag)
				continue;
			int l = npc.x >> 7;
			int i1 = npc.y >> 7;
			if (l < 0 || l >= 104 || i1 < 0 || i1 >= 104)
				continue;
			if (npc.anInt1540 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
				if (anIntArrayArray929[l][i1] == anInt1265)
					continue;
				anIntArrayArray929[l][i1] = anInt1265;
			}
			if (!npc.desc.aBoolean84)
				k += 0x80000000;
			worldController.method285(plane, npc.anInt1552, method42(plane, npc.y, npc.x), k, npc.y, (npc.anInt1540 - 1) * 64 + 60, npc.x, npc, npc.aBoolean1541);
		}
	}

	public void drawTooltip(int xPos, int yPos, String text) {
		String[] results = text.split("\n");
		int height = (results.length * 16) + 6;
		int width;
		width = regularText.getTextWidth(results[0]) + 6;
		for (int i = 1; i < results.length; i++) {
			if (width <= regularText.getTextWidth(results[i]) + 6) {
				width = regularText.getTextWidth(results[i]) + 6;
			}
		}
		DrawingArea.drawPixels(height, yPos, xPos, 0xFFFFA0, width);
		DrawingArea.fillPixels(xPos, width, height, 0, yPos);
		yPos += 14;
		for (int i = 0; i < results.length; i++) {
			regularText.method389(false, xPos + 3, 0, results[i], yPos);
			yPos += 16;
		}
	}

	private void buildInterfaceMenu(int y, RSInterface rsinterface, int mouseX, int x, int mouseY, int scrollPosition) {
		if (rsinterface == null)
			rsinterface = RSInterface.interfaceCache[21356];
		if (rsinterface.type != 0 || rsinterface.children == null || rsinterface.isMouseoverTriggered)
			return;
		if (mouseX < y || mouseY < x || mouseX > y + rsinterface.width || mouseY > x + rsinterface.height)
			return;
		for (int childIndex = 0; childIndex < rsinterface.children.length; childIndex++) {
			int childX = rsinterface.childX[childIndex] + y;
			int childY = (rsinterface.childY[childIndex] + x) - scrollPosition;
			RSInterface child = RSInterface.interfaceCache[rsinterface.children[childIndex]];
			childX += child.anInt263;
			childY += child.anInt265;
			if ((child.hoverType >= 0 || child.textHoverColor != 0) && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height)
				if (child.hoverType >= 0)
					anInt886 = child.hoverType;
				else
					anInt886 = child.id;
			if (child.type == 8 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
				anInt1315 = child.id;
			}
			if (child.type == 0) {
				buildInterfaceMenu(childX, child, mouseX, childY, mouseY, child.scrollPosition);
				if (child.scrollMax > child.height)
					method65(childX + child.width, child.height, mouseX, mouseY, child, childY, true, child.scrollMax);
			} else {
				if (child.atActionType == 1 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
					boolean flag = false;
					if (child.contentType != 0)
						flag = buildFriendsListMenu(child);
					if (!flag) {
						menuActionName[menuActionRow] = child.tooltip;
						menuActionID[menuActionRow] = 315;
						menuActionCmd3[menuActionRow] = child.id;
						menuActionRow++;
					}
				}
				if (child.atActionType == 2 && spellSelected == 0 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
					String s = child.selectedActionName;
					if (s.indexOf(" ") != -1)
						s = s.substring(0, s.indexOf(" "));
					menuActionName[menuActionRow] = s + " @gre@" + child.spellName;
					menuActionID[menuActionRow] = 626;
					menuActionCmd3[menuActionRow] = child.id;
					menuActionRow++;
				}
				if (child.atActionType == 3 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
					menuActionName[menuActionRow] = "Close";
					menuActionID[menuActionRow] = 200;
					menuActionCmd3[menuActionRow] = child.id;
					menuActionRow++;
				}
				if (child.atActionType == 4 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
					// System.out.println("2"+class9_1.tooltip + ", " +
					// class9_1.interfaceID);
					// menuActionName[menuActionRow] = class9_1.tooltip + ", " +
					// class9_1.id;
					menuActionName[menuActionRow] = child.tooltip;
					menuActionID[menuActionRow] = 169;
					menuActionCmd3[menuActionRow] = child.id;
					menuActionRow++;
					if (child.hoverText != null) {
						// drawHoverBox(k, l, class9_1.hoverText);
						// System.out.println("DRAWING INTERFACE: " +
						// class9_1.hoverText);
					}
				}
				if (child.atActionType == 5 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
					// System.out.println("3"+class9_1.tooltip + ", " +
					// class9_1.interfaceID);
					// menuActionName[menuActionRow] = class9_1.tooltip + ", " +
					// class9_1.id;
					menuActionName[menuActionRow] = child.tooltip;
					menuActionID[menuActionRow] = 646;
					menuActionCmd3[menuActionRow] = child.id;
					menuActionRow++;
				}
				if (child.atActionType == 6 && !aBoolean1149 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
					// System.out.println("4"+class9_1.tooltip + ", " +
					// class9_1.interfaceID);
					// menuActionName[menuActionRow] = class9_1.tooltip + ", " +
					// class9_1.id;
					menuActionName[menuActionRow] = child.tooltip;
					menuActionID[menuActionRow] = 679;
					menuActionCmd3[menuActionRow] = child.id;
					menuActionRow++;
				}
				if (child.atActionType == 8 && !aBoolean1149 && mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
					for (int i = 0; i < child.actions.length; i++) {
						menuActionName[menuActionRow] = child.actions[i];
						menuActionID[menuActionRow] = 1700 + i;
						menuActionCmd3[menuActionRow] = child.id;
						menuActionRow++;
					}
				}
				if (mouseX >= childX && mouseY >= childY && mouseX < childX + (child.type == 4 ? 100 : child.width) && mouseY < childY + child.height) {
					if (child.actions != null) {
						if ((child.type == 4 && child.disabledMessage.length() > 0) || child.type == 5) {
							for (int action = child.actions.length - 1; action >= 0; action--) {
								if (child.actions[action] != null) {
									menuActionName[menuActionRow] = child.actions[action] + (child.type == 4 ? " " + child.disabledMessage : "");
									menuActionID[menuActionRow] = 647;
									menuActionCmd2[menuActionRow] = action;
									menuActionCmd3[menuActionRow] = child.id;
									menuActionRow++;
								}
							}
						}
					}
				}
				if (child.type == 2) {
					int k2 = 0;
					int tabAm = tabAmounts[0];
					int tabSlot = 0;
					int hh = 0;

					int newSlot = 0;
					if (child.contentType == 206 && variousSettings[1000] != 0 && variousSettings[1012] == 0) {
						for (int tab = 0; tab < tabAmounts.length; tab++) {
							if (tab == variousSettings[1000]) {
								break;
							}
							newSlot += tabAmounts[tab];
						}
						k2 = newSlot;
					}

					heightLoop: for (int l2 = 0; l2 < child.height; l2++) {
						for (int i3 = 0; i3 < child.width; i3++) {
							int j3 = childX + i3 * (32 + child.invSpritePadX);
							int k3 = childY + l2 * (32 + child.invSpritePadY) + hh;
							if (child.contentType == 206 && variousSettings[1012] == 0) {
								if (variousSettings[1000] == 0) {
									if (k2 >= tabAm) {
										if (tabSlot + 1 < tabAmounts.length) {
											tabAm += tabAmounts[++tabSlot];
											if (tabSlot > 0 && tabAmounts[tabSlot - 1] % 8 == 0) {
												l2--;
											}
											hh += 8;
										}
										break;
									}
								} else if (variousSettings[1000] <= 9) {
									if (k2 >= tabAmounts[variousSettings[1000]] + newSlot) {
										break heightLoop;
									}
								}
							}
							if (k2 < 20) {
								j3 += child.spritesX[k2];
								k3 += child.spritesY[k2];
							}
							if (mouseX >= j3 && mouseY >= k3 && mouseX < j3 + 32 && mouseY < k3 + 32) {
								mouseInvInterfaceIndex = k2;
								lastActiveInvInterface = child.id;

								int itemId = child.inv[k2] - 1;
								if (variousSettings[1012] == 1 && child.contentType == 206) {
									itemId = bankInvTemp[k2] - 1;
								}
								if (itemId + 1 > 0) {
									ItemDef itemDef = ItemDef.forID(itemId);
									if (itemSelected == 1 && child.isInventoryInterface) {
										if (child.id != anInt1284 || k2 != anInt1283) {
											menuActionName[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDef.name;
											menuActionID[menuActionRow] = 870;
											menuActionCmd1[menuActionRow] = itemDef.id;
											menuActionCmd2[menuActionRow] = k2;
											menuActionCmd3[menuActionRow] = child.id;
											menuActionRow++;
										}
									} else if (spellSelected == 1 && child.isInventoryInterface) {
										if ((spellUsableOn & 0x10) == 16) {
											menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDef.name;
											menuActionID[menuActionRow] = 543;
											menuActionCmd1[menuActionRow] = itemDef.id;
											menuActionCmd2[menuActionRow] = k2;
											menuActionCmd3[menuActionRow] = child.id;
											menuActionRow++;
										}
									} else {
										if (child.isInventoryInterface) {
											for (int l3 = 4; l3 >= 3; l3--)
												if (itemDef.itemActions != null && itemDef.itemActions[l3] != null) {
													menuActionName[menuActionRow] = itemDef.itemActions[l3] + " @lre@" + itemDef.name;
													if (l3 == 3)
														menuActionID[menuActionRow] = 493;
													if (l3 == 4)
														menuActionID[menuActionRow] = 847;
													menuActionCmd1[menuActionRow] = itemDef.id;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = child.id;
													menuActionRow++;
												} else if (l3 == 4) {
													menuActionName[menuActionRow] = "Drop @lre@" + itemDef.name;
													menuActionID[menuActionRow] = 847;
													menuActionCmd1[menuActionRow] = itemDef.id;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = child.id;
													menuActionRow++;
												}
										}
										if (child.usableItemInterface) {
											menuActionName[menuActionRow] = "Use @lre@" + itemDef.name;
											menuActionID[menuActionRow] = 447;
											menuActionCmd1[menuActionRow] = itemDef.id;
											menuActionCmd2[menuActionRow] = k2;
											menuActionCmd3[menuActionRow] = child.id;
											menuActionRow++;
										}
										if (child.isInventoryInterface && itemDef.itemActions != null) {
											for (int i4 = 2; i4 >= 0; i4--)
												if (itemDef.itemActions[i4] != null) {
													menuActionName[menuActionRow] = itemDef.itemActions[i4] + " @lre@" + itemDef.name;
													if (i4 == 0)
														menuActionID[menuActionRow] = 74;
													if (i4 == 1)
														menuActionID[menuActionRow] = 454;
													if (i4 == 2)
														menuActionID[menuActionRow] = 539;
													menuActionCmd1[menuActionRow] = itemDef.id;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = child.id;
													menuActionRow++;
												}

										}
										if (child.actions != null) {
											if (rsinterface.parentID == 5292) {
												if (modifiableXValue > 0) {
													child.actions[5] = "Withdraw-" + modifiableXValue;
												} else {
													child.actions[5] = null;
												}
											}

											for (int j4 = 6; j4 >= 0; j4--) {
												if (j4 > child.actions.length - 1)
													continue;
												if (child.actions[j4] != null) {
													menuActionName[menuActionRow] = child.actions[j4] + " @lre@" + itemDef.name;
													if (j4 == 0)
														menuActionID[menuActionRow] = 632;
													if (j4 == 1)
														menuActionID[menuActionRow] = 78;
													if (j4 == 2)
														menuActionID[menuActionRow] = 867;
													if (j4 == 3)
														menuActionID[menuActionRow] = 431;
													if (j4 == 4)
														menuActionID[menuActionRow] = 53;
													if (rsinterface.parentID == 5292) {
														if (child.actions[j4] == null) {
															if (j4 == 5)
																menuActionID[menuActionRow] = 291;
														} else {
															if (j4 == 5)
																menuActionID[menuActionRow] = 300;
															if (j4 == 6)
																menuActionID[menuActionRow] = 291;
														}
													}

													menuActionCmd1[menuActionRow] = itemDef.id;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = child.id;
													menuActionRow++;
												}
											}
										}
										if (ClientConstants.DEBUG_MODE) {
											menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name + " @gre@(@whi@" + itemDef.id + "@gre@)";
										} else {
											menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name;
										}
										menuActionID[menuActionRow] = 1125;
										menuActionCmd1[menuActionRow] = itemDef.id;
										menuActionCmd2[menuActionRow] = k2;
										menuActionCmd3[menuActionRow] = child.id;
										menuActionRow++;
									}
								}
							}
							k2++;
						}
					}
				}
			}
		}
	}

	public void drawTransparentScrollBar(int x, int y, int height, int maxScroll, int pos) {
		orbComponents3[7].drawTransparentSprite(x, y, 120);
		orbComponents3[8].drawTransparentSprite(x, y + height - 16, 120);
		DrawingArea.drawVerticalLine(x, y + 16, height - 32, 0xffffff, 64);
		DrawingArea.drawVerticalLine(x + 15, y + 16, height - 32, 0xffffff, 64);
		int barHeight = (height - 32) * height / maxScroll;
		if (barHeight < 10) {
			barHeight = 10;
		}
		int barPos = 0;
		if (maxScroll != height) {
			barPos = (height - 32 - barHeight) * pos / (maxScroll - height);
		}
		DrawingArea.drawRectangle(x, y + 16 + barPos, 16, 5 + y + 16 + barPos + barHeight - 5 - (y + 16 + barPos), 0xffffff, 32);
	}

	public void drawScrollbar(int height, int pos, int y, int x, int maxScroll, boolean transparent) {
		if (transparent) {
			drawTransparentScrollBar(x, y, height, maxScroll, pos);
		} else {
			scrollBar1.drawSprite(x, y);
			scrollBar2.drawSprite(x, (y + height) - 16);
			DrawingArea.drawPixels(height - 32, y + 16, x, 0x000001, 16);
			DrawingArea.drawPixels(height - 32, y + 16, x, 0x3d3426, 15);
			DrawingArea.drawPixels(height - 32, y + 16, x, 0x342d21, 13);
			DrawingArea.drawPixels(height - 32, y + 16, x, 0x2e281d, 11);
			DrawingArea.drawPixels(height - 32, y + 16, x, 0x29241b, 10);
			DrawingArea.drawPixels(height - 32, y + 16, x, 0x252019, 9);
			DrawingArea.drawPixels(height - 32, y + 16, x, 0x000001, 1);
			int k1 = ((height - 32) * height) / maxScroll;
			if (k1 < 8) {
				k1 = 8;
			}
			int l1 = ((height - 32 - k1) * pos) / (maxScroll - height);
			DrawingArea.drawPixels(k1, y + 16 + l1, x, barFillColor, 16);
			DrawingArea.method341(y + 16 + l1, 0x000001, k1, x);
			DrawingArea.method341(y + 16 + l1, 0x817051, k1, x + 1);
			DrawingArea.method341(y + 16 + l1, 0x73654a, k1, x + 2);
			DrawingArea.method341(y + 16 + l1, 0x6a5c43, k1, x + 3);
			DrawingArea.method341(y + 16 + l1, 0x6a5c43, k1, x + 4);
			DrawingArea.method341(y + 16 + l1, 0x655841, k1, x + 5);
			DrawingArea.method341(y + 16 + l1, 0x655841, k1, x + 6);
			DrawingArea.method341(y + 16 + l1, 0x61553e, k1, x + 7);
			DrawingArea.method341(y + 16 + l1, 0x61553e, k1, x + 8);
			DrawingArea.method341(y + 16 + l1, 0x5d513c, k1, x + 9);
			DrawingArea.method341(y + 16 + l1, 0x5d513c, k1, x + 10);
			DrawingArea.method341(y + 16 + l1, 0x594e3a, k1, x + 11);
			DrawingArea.method341(y + 16 + l1, 0x594e3a, k1, x + 12);
			DrawingArea.method341(y + 16 + l1, 0x514635, k1, x + 13);
			DrawingArea.method341(y + 16 + l1, 0x4b4131, k1, x + 14);
			DrawingArea.method339(y + 16 + l1, 0x000001, 15, x);
			DrawingArea.method339(y + 17 + l1, 0x000001, 15, x);
			DrawingArea.method339(y + 17 + l1, 0x655841, 14, x);
			DrawingArea.method339(y + 17 + l1, 0x6a5c43, 13, x);
			DrawingArea.method339(y + 17 + l1, 0x6d5f48, 11, x);
			DrawingArea.method339(y + 17 + l1, 0x73654a, 10, x);
			DrawingArea.method339(y + 17 + l1, 0x76684b, 7, x);
			DrawingArea.method339(y + 17 + l1, 0x7b6a4d, 5, x);
			DrawingArea.method339(y + 17 + l1, 0x7e6e50, 4, x);
			DrawingArea.method339(y + 17 + l1, 0x817051, 3, x);
			DrawingArea.method339(y + 17 + l1, 0x000001, 2, x);
			DrawingArea.method339(y + 18 + l1, 0x000001, 16, x);
			DrawingArea.method339(y + 18 + l1, 0x564b38, 15, x);
			DrawingArea.method339(y + 18 + l1, 0x5d513c, 14, x);
			DrawingArea.method339(y + 18 + l1, 0x625640, 11, x);
			DrawingArea.method339(y + 18 + l1, 0x655841, 10, x);
			DrawingArea.method339(y + 18 + l1, 0x6a5c43, 7, x);
			DrawingArea.method339(y + 18 + l1, 0x6e6046, 5, x);
			DrawingArea.method339(y + 18 + l1, 0x716247, 4, x);
			DrawingArea.method339(y + 18 + l1, 0x7b6a4d, 3, x);
			DrawingArea.method339(y + 18 + l1, 0x817051, 2, x);
			DrawingArea.method339(y + 18 + l1, 0x000001, 1, x);
			DrawingArea.method339(y + 19 + l1, 0x000001, 16, x);
			DrawingArea.method339(y + 19 + l1, 0x514635, 15, x);
			DrawingArea.method339(y + 19 + l1, 0x564b38, 14, x);
			DrawingArea.method339(y + 19 + l1, 0x5d513c, 11, x);
			DrawingArea.method339(y + 19 + l1, 0x61553e, 9, x);
			DrawingArea.method339(y + 19 + l1, 0x655841, 7, x);
			DrawingArea.method339(y + 19 + l1, 0x6a5c43, 5, x);
			DrawingArea.method339(y + 19 + l1, 0x6e6046, 4, x);
			DrawingArea.method339(y + 19 + l1, 0x73654a, 3, x);
			DrawingArea.method339(y + 19 + l1, 0x817051, 2, x);
			DrawingArea.method339(y + 19 + l1, 0x000001, 1, x);
			DrawingArea.method339(y + 20 + l1, 0x000001, 16, x);
			DrawingArea.method339(y + 20 + l1, 0x4b4131, 15, x);
			DrawingArea.method339(y + 20 + l1, 0x544936, 14, x);
			DrawingArea.method339(y + 20 + l1, 0x594e3a, 13, x);
			DrawingArea.method339(y + 20 + l1, 0x5d513c, 10, x);
			DrawingArea.method339(y + 20 + l1, 0x61553e, 8, x);
			DrawingArea.method339(y + 20 + l1, 0x655841, 6, x);
			DrawingArea.method339(y + 20 + l1, 0x6a5c43, 4, x);
			DrawingArea.method339(y + 20 + l1, 0x73654a, 3, x);
			DrawingArea.method339(y + 20 + l1, 0x817051, 2, x);
			DrawingArea.method339(y + 20 + l1, 0x000001, 1, x);
			DrawingArea.method341(y + 16 + l1, 0x000001, k1, x + 15);
			DrawingArea.method339(y + 15 + l1 + k1, 0x000001, 16, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x000001, 15, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x3f372a, 14, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x443c2d, 10, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x483e2f, 9, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x4a402f, 7, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x4b4131, 4, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x564b38, 3, x);
			DrawingArea.method339(y + 14 + l1 + k1, 0x000001, 2, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x000001, 16, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x443c2d, 15, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x4b4131, 11, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x514635, 9, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x544936, 7, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x564b38, 6, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x594e3a, 4, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x625640, 3, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x6a5c43, 2, x);
			DrawingArea.method339(y + 13 + l1 + k1, 0x000001, 1, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x000001, 16, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x443c2d, 15, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x4b4131, 14, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x544936, 12, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x564b38, 11, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x594e3a, 10, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x5d513c, 7, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x61553e, 4, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x6e6046, 3, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x7b6a4d, 2, x);
			DrawingArea.method339(y + 12 + l1 + k1, 0x000001, 1, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x000001, 16, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x4b4131, 15, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x514635, 14, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x564b38, 13, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x594e3a, 11, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x5d513c, 9, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x61553e, 7, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x655841, 5, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x6a5c43, 4, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x73654a, 3, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x7b6a4d, 2, x);
			DrawingArea.method339(y + 11 + l1 + k1, 0x000001, 1, x);
		}
	}

	private void updateNPCs(Stream stream, int i) {
		anInt839 = 0;
		anInt893 = 0;
		method139(stream);
		method46(i, stream);
		method86(stream);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (npcArray[l].anInt1537 != loopCycle) {
				npcArray[l].desc = null;
				npcArray[l] = null;
			}
		}

		if (stream.currentOffset != i) {
			Signlink.reporterror(myUsername + " size mismatch in getnpcpos - pos:" + stream.currentOffset + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < npcCount; i1++)
			if (npcArray[npcIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
				throw new RuntimeException("eek");
			}

	}

	private int cButtonHPos;
	private int cButtonCPos;
	private int setChannel;

	public void processChatModeClick() {
		final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 503;
		if (super.mouseX >= 5 && super.mouseX <= 61 && super.mouseY >= yOffset + 482 && super.mouseY <= yOffset + 503) {
			cButtonHPos = 0;
			inputTaken = true;
		} else if (super.mouseX >= 71 && super.mouseX <= 127 && super.mouseY >= yOffset + 482 && super.mouseY <= yOffset + 503) {
			cButtonHPos = 1;
			inputTaken = true;
		} else if (super.mouseX >= 137 && super.mouseX <= 193 && super.mouseY >= yOffset + 482 && super.mouseY <= yOffset + 503) {
			cButtonHPos = 2;
			inputTaken = true;
		} else if (super.mouseX >= 203 && super.mouseX <= 259 && super.mouseY >= yOffset + 482 && super.mouseY <= yOffset + 503) {
			cButtonHPos = 3;
			inputTaken = true;
		} else if (super.mouseX >= 269 && super.mouseX <= 325 && super.mouseY >= yOffset + 482 && super.mouseY <= yOffset + 503) {
			cButtonHPos = 4;
			inputTaken = true;
		} else if (super.mouseX >= 335 && super.mouseX <= 391 && super.mouseY >= yOffset + 482 && super.mouseY <= yOffset + 503) {
			cButtonHPos = 5;
			inputTaken = true;
		} else if (super.mouseX >= 404 && super.mouseX <= 515 && super.mouseY >= yOffset + 482 && super.mouseY <= yOffset + 503) {
			cButtonHPos = 6;
			inputTaken = true;
		} else {
			cButtonHPos = -1;
			inputTaken = true;
		}
		if (super.clickMode3 == 1) {
			if (RSInterface.currentInputField != null) {
				if (RSInterface.currentInputField.onlyNumbers) {
					long amount = 0;

					try {
						amount = Long.parseLong(message.replaceAll(",", ""));

						// overflow concious code
						if (amount < -Integer.MAX_VALUE) {
							amount = -Integer.MAX_VALUE;
						} else if (amount > Integer.MAX_VALUE) {
							amount = Integer.MAX_VALUE;
						}
					} catch (Exception ignored) {}

					if (amount > 0) {
						stream.createFrame(208);
						stream.writeDWord((int) amount);
					}
				} else {
					stream.createFrame(150);
					stream.writeWordBigEndian(RSInterface.currentInputField.disabledMessage.length() + 3);
					stream.writeWord(RSInterface.currentInputField.id);
					stream.writeString(RSInterface.currentInputField.disabledMessage);
				}
				
				RSInterface.currentInputField.disabledMessage = "";
			}
			RSInterface.currentInputField = null;
			if (super.saveClickX >= 5 && super.saveClickX <= 61 && super.saveClickY >= yOffset + 482 && super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 0) {
						cButtonCPos = 0;
						chatTypeView = 0;
						inputTaken = true;
						setChannel = 0;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 0;
					chatTypeView = 0;
					inputTaken = true;
					setChannel = 0;
				}
				stream.createFrame(95);
				stream.writeWordBigEndian(publicChatMode);
				stream.writeWordBigEndian(privateChatMode);
				stream.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= 71 && super.saveClickX <= 127 && super.saveClickY >= yOffset + 482 && super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 1 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 1;
						chatTypeView = 5;
						inputTaken = true;
						setChannel = 1;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 1;
					chatTypeView = 5;
					inputTaken = true;
					setChannel = 1;
				}
				stream.createFrame(95);
				stream.writeWordBigEndian(publicChatMode);
				stream.writeWordBigEndian(privateChatMode);
				stream.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= 137 && super.saveClickX <= 193 && super.saveClickY >= yOffset + 482 && super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 2 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 2;
						chatTypeView = 1;
						inputTaken = true;
						setChannel = 2;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 2;
					chatTypeView = 1;
					inputTaken = true;
					setChannel = 2;
				}
				stream.createFrame(95);
				stream.writeWordBigEndian(publicChatMode);
				stream.writeWordBigEndian(privateChatMode);
				stream.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= 203 && super.saveClickX <= 259 && super.saveClickY >= yOffset + 482 && super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 3 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 3;
						chatTypeView = 2;
						inputTaken = true;
						setChannel = 3;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 3;
					chatTypeView = 2;
					inputTaken = true;
					setChannel = 3;
				}
				stream.createFrame(95);
				stream.writeWordBigEndian(publicChatMode);
				stream.writeWordBigEndian(privateChatMode);
				stream.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= 269 && super.saveClickX <= 325 && super.saveClickY >= yOffset + 482 && super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 4 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 4;
						chatTypeView = 5;
						inputTaken = true;
						setChannel = 4;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 4;
					chatTypeView = 5;
					inputTaken = true;
					setChannel = 4;
				}
				stream.createFrame(95);
				stream.writeWordBigEndian(publicChatMode);
				stream.writeWordBigEndian(privateChatMode);
				stream.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= 335 && super.saveClickX <= 391 && super.saveClickY >= yOffset + 482 && super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 5 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 5;
						chatTypeView = 3;
						inputTaken = true;
						setChannel = 5;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 5;
					chatTypeView = 3;
					inputTaken = true;
					setChannel = 5;
				}
				stream.createFrame(95);
				stream.writeWordBigEndian(publicChatMode);
				stream.writeWordBigEndian(privateChatMode);
				stream.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= 404 && super.saveClickX <= 515 && super.saveClickY >= yOffset + 482 && super.saveClickY <= yOffset + 505) {
				if (openInterfaceID == -1) {
					clearTopInterfaces();
					reportAbuseInput = "";
					canMute = false;
					for (int i = 0; i < RSInterface.interfaceCache.length; i++) {
						if (RSInterface.interfaceCache[i] == null || RSInterface.interfaceCache[i].parentID != 41750) {
							continue;
						}
						reportAbuseInterfaceID = openInterfaceID = RSInterface.interfaceCache[i].parentID;
						break;
					}
				} else {
					pushMessage("Please close the interface you have open before using 'report abuse'", 0, "");
				}
			}
		}
	}

	private void updateConfigValues(int i) {
		try {
			
			int k = variousSettings[i];
			
			
			if (i == 1050) {
				switch (k) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					break;
				}
			}
			int j = Varp.cache[i].anInt709;
			if (j == 0)
				return;
			if (j == 1) {
				if (k == 1)
					Rasterizer.method372(0.90000000000000002D);
				if (k == 2)
					Rasterizer.method372(0.80000000000000004D);
				if (k == 3)
					Rasterizer.method372(0.69999999999999996D);
				if (k == 4)
					Rasterizer.method372(0.59999999999999998D);
				ItemDef.mruNodes1.unlinkAll();
				welcomeScreenRaised = true;
			}
			if (j == 3) {
				boolean flag1 = musicEnabled;
				if (k == 0) {
					adjustVolume(musicEnabled, 0);
					musicEnabled = true;
				}
				if (k == 1) {
					adjustVolume(musicEnabled, -400);
					musicEnabled = true;
				}
				if (k == 2) {
					adjustVolume(musicEnabled, -800);
					musicEnabled = true;
				}
				if (k == 3) {
					adjustVolume(musicEnabled, -1200);
					musicEnabled = true;
				}
				if (k == 4)
					musicEnabled = false;
				if (musicEnabled != flag1 && !lowMem) {
					if (musicEnabled) {
						nextSong = currentSong;
						songChanging = true;
						onDemandFetcher.method558(2, nextSong);
					} else {
						stopMidi();
					}
					prevSong = 0;
				}
			}
			if (j == 4) {
				if (k == 0) {
					aBoolean848 = true;
					setWaveVolume(0);
				}
				if (k == 1) {
					aBoolean848 = true;
					setWaveVolume(-400);
				}
				if (k == 2) {
					aBoolean848 = true;
					setWaveVolume(-800);
				}
				if (k == 3) {
					aBoolean848 = true;
					setWaveVolume(-1200);
				}
				if (k == 4)
					aBoolean848 = false;
			}
			if (j == 5)
				anInt1253 = k;
			if (j == 6)
				anInt1249 = k;
			if (j == 8) {
				splitPrivateChat = k;
				inputTaken = true;
			}
			if (j == 9)
				anInt913 = k;
		} catch (Exception e) {
		}
	}

	public StreamLoader mediaStreamLoader;

	public void updateEntities() {
		try {
			int anInt974 = 0;
			for (int j = -1; j < playerCount + npcCount; j++) {
				Object obj;
				if (j == -1)
					obj = myPlayer;
				else if (j < playerCount)
					obj = playerArray[playerIndices[j]];
				else
					obj = npcArray[npcIndices[j - playerCount]];
				if (obj == null || !((Entity) (obj)).isVisible())
					continue;
				if (obj instanceof Npc) {
					EntityDef entityDef = ((Npc) obj).desc;
					if (entityDef.childrenIDs != null)
						entityDef = entityDef.method161();
					if (entityDef == null)
						continue;
				}
				if (j < playerCount) {
					int l = 30;
					Player player = (Player) obj;
					if (player.headIcon >= 0) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1) {
							if (player.skullIcon < 2) {
								skullIcons[player.skullIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
								l += 25;
							}
							if (player.headIcon < 13) {
								headIcons[player.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
								l += 18;
							}
						}
					}
					if (j >= 0 && anInt855 == 10 && anInt933 == playerIndices[j]) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIconsHint[player.hintIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
					}
				} else {
					EntityDef entityDef_1 = ((Npc) obj).desc;
					if (entityDef_1.anInt75 >= 0 && entityDef_1.anInt75 < headIcons.length) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIcons[entityDef_1.anInt75].drawSprite(spriteDrawX - 12, spriteDrawY - 30);
					}
					if (anInt855 == 1 && anInt1222 == npcIndices[j - playerCount] && loopCycle % 20 < 10) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
					}
				}
				if (((Entity) (obj)).textSpoken != null && (j >= playerCount || publicChatMode == 0 || publicChatMode == 3 || publicChatMode == 1 && isFriendOrSelf(((Player) obj).name))) {
					npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height);
					if (spriteDrawX > -1 && anInt974 < anInt975) {
						anIntArray979[anInt974] = boldText.method384(((Entity) (obj)).textSpoken) / 2;
						anIntArray978[anInt974] = boldText.anInt1497;
						anIntArray976[anInt974] = spriteDrawX;
						anIntArray977[anInt974] = spriteDrawY;
						anIntArray980[anInt974] = ((Entity) (obj)).anInt1513;
						anIntArray981[anInt974] = ((Entity) (obj)).anInt1531;
						anIntArray982[anInt974] = ((Entity) (obj)).textCycle;
						aStringArray983[anInt974++] = ((Entity) (obj)).textSpoken;
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 >= 1 && ((Entity) (obj)).anInt1531 <= 3) {
							anIntArray978[anInt974] += 10;
							anIntArray977[anInt974] += 5;
						}
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 4)
							anIntArray979[anInt974] = 60;
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 5)
							anIntArray978[anInt974] += 5;
					}
				}
				if (((Entity) (obj)).loopCycleStatus > loopCycle) {
					try {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1) {
							int i1 = (((Entity) (obj)).currentHealth * 30) / ((Entity) (obj)).maxHealth;
							if (i1 > 30) {
								i1 = 30;
							}
							int barWidth = hpBars[0].cropWidth;
							int percent = (((Entity) (obj)).currentHealth * barWidth) / ((Entity) (obj)).maxHealth;
							if (percent > barWidth) {
								percent = barWidth;
							}
							if (!Configuration.enableNewHpBars) {
								DrawingArea.drawPixels(5, spriteDrawY - 3, spriteDrawX - 15, 65280, i1);
								DrawingArea.drawPixels(5, spriteDrawY - 3, (spriteDrawX - 15) + i1, 0xff0000, 30 - i1);
							} else {
								hpBars[1].drawSprite(spriteDrawX - (barWidth / 2), spriteDrawY - 3);
								if (percent > 0) {
									Sprite fullBar = new Sprite(hpBars[0], 0, 0, percent, 7);
									fullBar.drawSprite(spriteDrawX - (barWidth / 2), spriteDrawY - 3);
								}
							}
							if (Configuration.drawEntityFeed) {							
								/*String monster_name = ((Npc) obj).desc.name;
								pushFeed(monster_name, ((Entity) obj).currentHealth, ((Entity) obj).maxHealth);
								displayEntityFeed();*/
							}
						}
					} catch (Exception e) {
					}
				}
				if (Configuration.enableNewHitmarks) {
					for (int j1 = 0; j1 < 4; j1++) {
						if (((Entity) (obj)).hitsLoopCycle[j1] > loopCycle) {
							npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height / 2);
							if (spriteDrawX > -1) {
								switch (j1) {
								case 1:
									spriteDrawY += 20;
									break;
								case 2:
									spriteDrawY += 40;
									break;
								case 3:
									spriteDrawY += 60;
									break;
								case 4:
									spriteDrawY += 80;
									break;
								case 5:
									spriteDrawY += 100;
									break;
								case 6:
									spriteDrawY += 120;
									break;
								}
								Entity e = ((Entity) (obj));
								if (e.hitmarkMove[j1] > -30)
									e.hitmarkMove[j1]--;
								if (e.hitmarkMove[j1] < -26)
									e.hitmarkTrans[j1] -= 5;
								hitmarkDraw(String.valueOf(e.hitArray[j1]).length(), e.hitMarkTypes[j1], e.hitIcon[j1], e.hitArray[j1], e.hitmarkMove[j1], e.hitmarkTrans[j1]);
							}
						}
					}
				} else {
					for (int j1 = 0; j1 < 4; j1++) {
						if (((Entity) (obj)).hitsLoopCycle[j1] > loopCycle) {
							npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height / 2);
							if (spriteDrawX > -1) {
								if (j1 == 1) {
									spriteDrawY -= 20;
								}
								if (j1 == 2) {
									spriteDrawX -= 15;
									spriteDrawY -= 10;
								}
								if (j1 == 3) {
									spriteDrawX += 15;
									spriteDrawY -= 10;
								}
								hitMarks[((Entity) (obj)).hitMarkTypes[j1]].drawSprite(spriteDrawX - 12, spriteDrawY - 12);
								smallText.drawText(0, String.valueOf(((Entity) (obj)).hitArray[j1]), spriteDrawY + 4, spriteDrawX);
								smallText.drawText(0xffffff, String.valueOf(((Entity) (obj)).hitArray[j1]), spriteDrawY + 3, spriteDrawX - 1);
							}
						}
					}
				}
			}
			for (int k = 0; k < anInt974; k++) {
				int k1 = anIntArray976[k];
				int l1 = anIntArray977[k];
				int j2 = anIntArray979[k];
				int k2 = anIntArray978[k];
				boolean flag = true;
				while (flag) {
					flag = false;
					for (int l2 = 0; l2 < k; l2++)
						if (l1 + 2 > anIntArray977[l2] - anIntArray978[l2] && l1 - k2 < anIntArray977[l2] + 2 && k1 - j2 < anIntArray976[l2] + anIntArray979[l2] && k1 + j2 > anIntArray976[l2] - anIntArray979[l2] && anIntArray977[l2] - anIntArray978[l2] < l1) {
							l1 = anIntArray977[l2] - anIntArray978[l2];
							flag = true;
						}

				}
				spriteDrawX = anIntArray976[k];
				spriteDrawY = anIntArray977[k] = l1;
				String s = aStringArray983[k];
				if (anInt1249 == 0) {
					int i3 = 0xffff00;
					if (anIntArray980[k] < 6)
						i3 = anIntArray965[anIntArray980[k]];
					if (anIntArray980[k] == 6)
						i3 = anInt1265 % 20 >= 10 ? 0xffff00 : 0xff0000;
					if (anIntArray980[k] == 7)
						i3 = anInt1265 % 20 >= 10 ? 65535 : 255;
					if (anIntArray980[k] == 8)
						i3 = anInt1265 % 20 >= 10 ? 0x80ff80 : 45056;
					if (anIntArray980[k] == 9) {
						int j3 = 150 - anIntArray982[k];
						if (j3 < 50)
							i3 = 0xff0000 + 1280 * j3;
						else if (j3 < 100)
							i3 = 0xffff00 - 0x50000 * (j3 - 50);
						else if (j3 < 150)
							i3 = 65280 + 5 * (j3 - 100);
					}
					if (anIntArray980[k] == 10) {
						int k3 = 150 - anIntArray982[k];
						if (k3 < 50)
							i3 = 0xff0000 + 5 * k3;
						else if (k3 < 100)
							i3 = 0xff00ff - 0x50000 * (k3 - 50);
						else if (k3 < 150)
							i3 = (255 + 0x50000 * (k3 - 100)) - 5 * (k3 - 100);
					}
					if (anIntArray980[k] == 11) {
						int l3 = 150 - anIntArray982[k];
						if (l3 < 50)
							i3 = 0xffffff - 0x50005 * l3;
						else if (l3 < 100)
							i3 = 65280 + 0x50005 * (l3 - 50);
						else if (l3 < 150)
							i3 = 0xffffff - 0x50000 * (l3 - 100);
					}
					if (anIntArray981[k] == 0) {
						boldText.drawText(0, s, spriteDrawY + 1, spriteDrawX);
						boldText.drawText(i3, s, spriteDrawY, spriteDrawX);
					}
					if (anIntArray981[k] == 1) {
						boldText.method386(0, s, spriteDrawX, anInt1265, spriteDrawY + 1);
						boldText.method386(i3, s, spriteDrawX, anInt1265, spriteDrawY);
					}
					if (anIntArray981[k] == 2) {
						boldText.method387(spriteDrawX, s, anInt1265, spriteDrawY + 1, 0);
						boldText.method387(spriteDrawX, s, anInt1265, spriteDrawY, i3);
					}
					if (anIntArray981[k] == 3) {
						boldText.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY + 1, spriteDrawX, 0);
						boldText.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY, spriteDrawX, i3);
					}
					if (anIntArray981[k] == 4) {
						int i4 = boldText.method384(s);
						int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
						DrawingArea.setDrawingArea(334, spriteDrawX - 50, spriteDrawX + 50, 0);
						boldText.method385(0, s, spriteDrawY + 1, (spriteDrawX + 50) - k4);
						boldText.method385(i3, s, spriteDrawY, (spriteDrawX + 50) - k4);
						DrawingArea.defaultDrawingAreaSize();
					}
					if (anIntArray981[k] == 5) {
						int j4 = 150 - anIntArray982[k];
						int l4 = 0;
						if (j4 < 25)
							l4 = j4 - 25;
						else if (j4 > 125)
							l4 = j4 - 125;
						DrawingArea.setDrawingArea(spriteDrawY + 5, 0, 512, spriteDrawY - boldText.anInt1497 - 1);
						boldText.drawText(0, s, spriteDrawY + 1 + l4, spriteDrawX);
						boldText.drawText(i3, s, spriteDrawY + l4, spriteDrawX);
						DrawingArea.defaultDrawingAreaSize();
					}
				} else {
					boldText.drawText(0, s, spriteDrawY + 1, spriteDrawX);
					boldText.drawText(0xffff00, s, spriteDrawY, spriteDrawX);
				}
			}
		} catch (Exception e) {
		}
	}

	private void delFriend(long l) {
		try {
			if (l == 0L)
				return;
			for (int i = 0; i < friendsCount; i++) {
				if (friendsListAsLongs[i] != l)
					continue;
				friendsCount--;
				for (int j = i; j < friendsCount; j++) {
					friendsList[j] = friendsList[j + 1];
					friendsNodeIDs[j] = friendsNodeIDs[j + 1];
					friendsListAsLongs[j] = friendsListAsLongs[j + 1];
				}

				stream.createFrame(215);
				stream.writeQWord(l);
				break;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("18622, " + false + ", " + l + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	private final int[] sideIconsX = { 17, 49, 83, 114, 146, 180, 214, 16, 49, 82, 116, 148, 184, 216 }, sideIconsY = { 9, 7, 7, 5, 2, 3, 7, 306, 306, 306, 302, 305, 303, 303, 303 }, sideIconsId = { 0, 1, 2, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, -1 }, sideIconsTab = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

	public void drawSideIcons() {
		int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 247;
		int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 336;
		if (frameMode == ScreenMode.FIXED || frameMode != ScreenMode.FIXED && !changeTabArea) {
			cacheSprite[370].drawSprite(sideIconsX[8] + xOffset + 168, sideIconsY[7] + yOffset);
			for (int i = 0; i < sideIconsTab.length; i++) {
				if (tabInterfaceIDs[sideIconsTab[i]] != -1) {
					if (sideIconsId[i] != -1) {
						sideIcons[sideIconsId[i]].drawSprite(sideIconsX[i] + xOffset, sideIconsY[i] + yOffset);
					}
				}
			}
		} else if (changeTabArea && frameWidth < 1000) {
			int[] iconId = { 0, 1, 2, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, -1 };
			int[] iconX = { 219, 189, 156, 126, 93, 62, 30, 219, 189, 156, 124, 92, 59, 28 };
			int[] iconY = { 67, 69, 67, 69, 72, 72, 69, 28, 29, 29, 32, 30, 33, 31, 32 };
			cacheSprite[370].drawSprite(frameWidth - 29, frameHeight - 29);
			for (int i = 0; i < sideIconsTab.length; i++) {
				if (tabInterfaceIDs[sideIconsTab[i]] != -1) {
					if (iconId[i] != -1) {
						sideIcons[iconId[i]].drawSprite(frameWidth - iconX[i], frameHeight - iconY[i]);
					}
				}
			}
		} else if (changeTabArea && frameWidth >= 1000) {
			int[] iconId = { 0, 1, 2, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, -1 };
			int[] iconX = { 19, 50, 82, 111, 144, 176, 208, 242, 273, 306, 338, 370, 404, 433 };
			int[] iconY = { 30, 32, 30, 32, 34, 34, 32, 28, 29, 29, 32, 31, 32, 32, 32 };
			cacheSprite[370].drawSprite(frameWidth - 27, frameHeight - 29);
			for (int i = 0; i < sideIconsTab.length; i++) {
				if (tabInterfaceIDs[sideIconsTab[i]] != -1) {
					if (iconId[i] != -1) {
						sideIcons[iconId[i]].drawSprite(frameWidth - 461 + iconX[i], frameHeight - iconY[i]);
					}
				}
			}
		}
	}

	private final int[] redStonesX = { 6, 44, 77, 110, 143, 176, 209, 6, 44, 77, 110, 143, 176, 209 }, redStonesY = { 0, 0, 0, 0, 0, 0, 0, 298, 298, 298, 298, 298, 298, 298 }, redStonesId = { 0, 4, 4, 4, 4, 4, 1, 2, 4, 4, 4, 4, 4, 3 };

	private void drawRedStones() {
		int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 247;
		int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 336;
		if (frameMode == ScreenMode.FIXED || frameMode != ScreenMode.FIXED && !changeTabArea) {
			if (tabInterfaceIDs[tabID] != -1 && tabID != 14) {
				redStones[redStonesId[tabID]].drawSprite(redStonesX[tabID] + xOffset, redStonesY[tabID] + yOffset);
			}
		} else if (changeTabArea && frameWidth < 1000) {
			int[] stoneX = { 226, 194, 162, 130, 99, 65, 34, 219, 195, 161, 130, 98, 65, 33 };
			int[] stoneY = { 73, 73, 73, 73, 73, 73, 73, -1, 37, 37, 37, 37, 37, 37, 37 };
			if (tabInterfaceIDs[tabID] != -1 && tabID != 14 && showTabComponents) {
				if (tabID == 7) {
					redStones[4].drawSprite(frameWidth - 226, frameHeight - 37);
				}
				redStones[4].drawSprite(frameWidth - stoneX[tabID], frameHeight - stoneY[tabID]);
			}
		} else if (changeTabArea && frameWidth >= 1000) {
			int[] stoneX = { 449, 417, 385, 353, 321, 289, 257, 225, 193, 161, 130, 98, 65, 33 };
			if (tabInterfaceIDs[tabID] != -1 && tabID != 14 && showTabComponents) {
				redStones[4].drawSprite(frameWidth - stoneX[tabID], frameHeight - 37);
			}
		}
	}

	private void drawTabArea() {
		final int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 241;
		final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 336;
		if (frameMode == ScreenMode.FIXED) {
			aRSImageProducer_1163.initDrawingArea();
		}
		Rasterizer.anIntArray1472 = anIntArray1181;
		if (frameMode == ScreenMode.FIXED) {
			fixedGameComponents[1].drawSprite(0, 0);
		} else if (frameMode != ScreenMode.FIXED && !changeTabArea) {
			DrawingArea.method335(0x3E3529, frameHeight - 304, 195, 270, transparentTabArea ? 80 : 256, frameWidth - 217);
			gameComponents[2].drawSprite(xOffset, yOffset);
		} else {
			if (frameWidth >= 1000) {
				if (showTabComponents) {
					DrawingArea.method335(0x3E3529, frameHeight - 304, 197, 265, transparentTabArea ? 80 : 256, frameWidth - 197);
					gameComponents[4].drawSprite(frameWidth - 204, frameHeight - 311);
				}
				for (int x = frameWidth - 449, y = frameHeight - 37, index = 0; x <= frameWidth - 30 && index < 14; x += 32, index++) {
					redStones[5].drawSprite(x, y);
				}
			} else if (frameWidth < 1000) {
				if (showTabComponents) {
					DrawingArea.method335(0x3E3529, frameHeight - 341, 195, 265, transparentTabArea ? 80 : 256, frameWidth - 197);
					gameComponents[4].drawSprite(frameWidth - 204, frameHeight - 348);
				}
				for (int x = frameWidth - 226, y = frameHeight - 73, index = 0; x <= frameWidth - 32 && index < 7; x += 32, index++) {
					redStones[5].drawSprite(x, y);
				}
				for (int x = frameWidth - 226, y = frameHeight - 37, index = 0; x <= frameWidth - 32 && index < 7; x += 32, index++) {
					redStones[5].drawSprite(x, y);
				}
			}
		}
		if (invOverlayInterfaceID == -1) {
			drawRedStones();
			drawSideIcons();
		}
		if (showTabComponents) {
			int x = frameMode == ScreenMode.FIXED ? 31 : frameWidth - 215;
			int y = frameMode == ScreenMode.FIXED ? 37 : frameHeight - 299;
			if (changeTabArea) {
				x = frameWidth - 197;
				y = frameWidth >= 1000 ? frameHeight - 303 : frameHeight - 340;
			}
			if (invOverlayInterfaceID != -1) {
				drawInterface(0, x, RSInterface.interfaceCache[invOverlayInterfaceID], y);
			} else if (tabInterfaceIDs[tabID] != -1) {
				drawInterface(0, x, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], y);
			}
		}
		if (menuOpen) {
			drawMenu(frameMode == ScreenMode.FIXED ? 516 : 0, frameMode == ScreenMode.FIXED ? 168 : 0);
		}
		if (frameMode == ScreenMode.FIXED) {
			aRSImageProducer_1163.drawGraphics(168, super.graphics, 516);
			aRSImageProducer_1165.initDrawingArea();
		}
		Rasterizer.anIntArray1472 = anIntArray1182;
	}

	private void method37(int j) {
		if (Configuration.enableMovingTextures) {
			if (Rasterizer.anIntArray1480[17] >= j) {
				Background background = Rasterizer.aBackgroundArray1474s[17];
				int k = background.anInt1452 * background.anInt1453 - 1;
				int j1 = background.anInt1452 * anInt945 * 2;
				byte abyte0[] = background.aByteArray1450;
				byte abyte3[] = aByteArray912;
				for (int i2 = 0; i2 <= k; i2++)
					abyte3[i2] = abyte0[i2 - j1 & k];

				background.aByteArray1450 = abyte3;
				aByteArray912 = abyte0;
				Rasterizer.method370(17);
				anInt854++;
				if (anInt854 > 1235) {
					anInt854 = 0;
					stream.createFrame(226);
					stream.writeWordBigEndian(0);
					int l2 = stream.currentOffset;
					stream.writeWord(58722);
					stream.writeWordBigEndian(240);
					stream.writeWord((int) (Math.random() * 65536D));
					stream.writeWordBigEndian((int) (Math.random() * 256D));
					if ((int) (Math.random() * 2D) == 0)
						stream.writeWord(51825);
					stream.writeWordBigEndian((int) (Math.random() * 256D));
					stream.writeWord((int) (Math.random() * 65536D));
					stream.writeWord(7130);
					stream.writeWord((int) (Math.random() * 65536D));
					stream.writeWord(61657);
					stream.writeBytes(stream.currentOffset - l2);
				}
			}
			if (Rasterizer.anIntArray1480[24] >= j) {
				Background background_1 = Rasterizer.aBackgroundArray1474s[24];
				int l = background_1.anInt1452 * background_1.anInt1453 - 1;
				int k1 = background_1.anInt1452 * anInt945 * 2;
				byte abyte1[] = background_1.aByteArray1450;
				byte abyte4[] = aByteArray912;
				for (int j2 = 0; j2 <= l; j2++)
					abyte4[j2] = abyte1[j2 - k1 & l];

				background_1.aByteArray1450 = abyte4;
				aByteArray912 = abyte1;
				Rasterizer.method370(24);
			}
			if (Rasterizer.anIntArray1480[34] >= j) {
				Background background_2 = Rasterizer.aBackgroundArray1474s[34];
				int i1 = background_2.anInt1452 * background_2.anInt1453 - 1;
				int l1 = background_2.anInt1452 * anInt945 * 2;
				byte abyte2[] = background_2.aByteArray1450;
				byte abyte5[] = aByteArray912;
				for (int k2 = 0; k2 <= i1; k2++)
					abyte5[k2] = abyte2[k2 - l1 & i1];

				background_2.aByteArray1450 = abyte5;
				aByteArray912 = abyte2;
				Rasterizer.method370(34);
			}
			if (Rasterizer.anIntArray1480[40] >= j) {
				Background background_2 = Rasterizer.aBackgroundArray1474s[40];
				int i1 = background_2.anInt1452 * background_2.anInt1453 - 1;
				int l1 = background_2.anInt1452 * anInt945 * 2;
				byte abyte2[] = background_2.aByteArray1450;
				byte abyte5[] = aByteArray912;
				for (int k2 = 0; k2 <= i1; k2++)
					abyte5[k2] = abyte2[k2 - l1 & i1];

				background_2.aByteArray1450 = abyte5;
				aByteArray912 = abyte2;
				Rasterizer.method370(40);
			}
		}
	}

	private void method38() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = myPlayerIndex;
			else
				j = playerIndices[i];
			Player player = playerArray[j];
			if (player != null && player.textCycle > 0) {
				player.textCycle--;
				if (player.textCycle == 0)
					player.textSpoken = null;
			}
		}
		for (int k = 0; k < npcCount; k++) {
			int l = npcIndices[k];
			Npc npc = npcArray[l];
			if (npc != null && npc.textCycle > 0) {
				npc.textCycle--;
				if (npc.textCycle == 0)
					npc.textSpoken = null;
			}
		}
	}

	private void calcCameraPos() {
		int i = anInt1098 * 128 + 64;
		int j = anInt1099 * 128 + 64;
		int k = method42(plane, j, i) - anInt1100;
		if (xCameraPos < i) {
			xCameraPos += anInt1101 + ((i - xCameraPos) * anInt1102) / 1000;
			if (xCameraPos > i)
				xCameraPos = i;
		}
		if (xCameraPos > i) {
			xCameraPos -= anInt1101 + ((xCameraPos - i) * anInt1102) / 1000;
			if (xCameraPos < i)
				xCameraPos = i;
		}
		if (zCameraPos < k) {
			zCameraPos += anInt1101 + ((k - zCameraPos) * anInt1102) / 1000;
			if (zCameraPos > k)
				zCameraPos = k;
		}
		if (zCameraPos > k) {
			zCameraPos -= anInt1101 + ((zCameraPos - k) * anInt1102) / 1000;
			if (zCameraPos < k)
				zCameraPos = k;
		}
		if (yCameraPos < j) {
			yCameraPos += anInt1101 + ((j - yCameraPos) * anInt1102) / 1000;
			if (yCameraPos > j)
				yCameraPos = j;
		}
		if (yCameraPos > j) {
			yCameraPos -= anInt1101 + ((yCameraPos - j) * anInt1102) / 1000;
			if (yCameraPos < j)
				yCameraPos = j;
		}
		i = anInt995 * 128 + 64;
		j = anInt996 * 128 + 64;
		k = method42(plane, j, i) - anInt997;
		int l = i - xCameraPos;
		int i1 = k - zCameraPos;
		int j1 = j - yCameraPos;
		int k1 = (int) Math.sqrt(l * l + j1 * j1);
		int l1 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
		int i2 = (int) (Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
		if (l1 < 128)
			l1 = 128;
		if (l1 > 383)
			l1 = 383;
		if (yCameraCurve < l1) {
			yCameraCurve += anInt998 + ((l1 - yCameraCurve) * anInt999) / 1000;
			if (yCameraCurve > l1)
				yCameraCurve = l1;
		}
		if (yCameraCurve > l1) {
			yCameraCurve -= anInt998 + ((yCameraCurve - l1) * anInt999) / 1000;
			if (yCameraCurve < l1)
				yCameraCurve = l1;
		}
		int j2 = i2 - xCameraCurve;
		if (j2 > 1024)
			j2 -= 2048;
		if (j2 < -1024)
			j2 += 2048;
		if (j2 > 0) {
			xCameraCurve += anInt998 + (j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		if (j2 < 0) {
			xCameraCurve -= anInt998 + (-j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		int k2 = i2 - xCameraCurve;
		if (k2 > 1024)
			k2 -= 2048;
		if (k2 < -1024)
			k2 += 2048;
		if (k2 < 0 && j2 > 0 || k2 > 0 && j2 < 0)
			xCameraCurve = i2;
	}

	private void drawMenu(int xOffSet, int yOffSet) {
		int xPos = menuOffsetX - (xOffSet - 4);
		int yPos = (-yOffSet + 4) + menuOffsetY;
		int menuW = menuWidth;
		int menuH = menuHeight + 1;
		inputTaken = true;
		tabAreaAltered = true;
		if (!Configuration.enableNewMenus) {
			int menuColor = 0x5d5447;
			DrawingArea.drawPixels(menuH, yPos, xPos, menuColor, menuW);
			DrawingArea.drawPixels(16, yPos + 1, xPos + 1, 0, menuW - 2);
			DrawingArea.fillPixels(xPos + 1, menuW - 2, menuH - 19, 0, yPos + 18);
			newBoldFont.drawBasicString("Choose Option", xPos + 3, yPos + 14, menuColor, 1);
			int mouseX = super.mouseX - (xOffSet);
			int mouseY = (-yOffSet) + super.mouseY;
			for (int i = 0; i < menuActionRow; i++) {
				int textY = yPos + 31 + (menuActionRow - 1 - i) * 15;
				int textColor = 0xffffff;
				if (mouseX > xPos && mouseX < xPos + menuW && mouseY > textY - 13 && mouseY < textY + 3) {
					DrawingArea.drawPixels(15, textY - 11, xPos + 3, 0x6f695d, menuWidth - 6);
					textColor = 0xffff00;
				}
				newBoldFont.drawBasicString(menuActionName[i], xPos + 3, textY, textColor, 1);
			}
		} else {
			DrawingArea.drawPixels(menuH - 4, yPos + 2, xPos, 0x706a5e, menuW);
			DrawingArea.drawPixels(menuH - 2, yPos + 1, xPos + 1, 0x706a5e, menuW - 2);
			DrawingArea.drawPixels(menuH, yPos, xPos + 2, 0x706a5e, menuW - 4);
			DrawingArea.drawPixels(menuH - 2, yPos + 1, xPos + 3, 0x2d2822, menuW - 6);
			DrawingArea.drawPixels(menuH - 4, yPos + 2, xPos + 2, 0x2d2822, menuW - 4);
			DrawingArea.drawPixels(menuH - 6, yPos + 3, xPos + 1, 0x2d2822, menuW - 2);
			DrawingArea.drawPixels(menuH - 22, yPos + 19, xPos + 2, 0x524a3d, menuW - 4);
			DrawingArea.drawPixels(menuH - 22, yPos + 20, xPos + 3, 0x524a3d, menuW - 6);
			DrawingArea.drawPixels(menuH - 23, yPos + 20, xPos + 3, 0x2b271c, menuW - 6);
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x2a291b, yPos + 2);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a261b, yPos + 3);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x252116, yPos + 4);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x211e15, yPos + 5);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1e1b12, yPos + 6);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1a170e, yPos + 7);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 2, 0x15120b, yPos + 8);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x100d08, yPos + 10);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 11);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x080703, yPos + 12);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 13);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 14);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 15);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 16);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 17);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a291b, yPos + 18);
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x564943, yPos + 19);
			newBoldFont.drawBasicString("Choose Option", xPos + 3, yPos + 14, 0xc6b895, 1);
		}
		int mouseX = super.mouseX - (xOffSet);
		int mouseY = (-yOffSet) + super.mouseY;
		for (int l1 = 0; l1 < menuActionRow; l1++) {
			int textY = yPos + 31 + (menuActionRow - 1 - l1) * 15;
			int disColor = 0xc6b895;
			if (mouseX > xPos && mouseX < xPos + menuW && mouseY > textY - 13 && mouseY < textY + 3) {
				DrawingArea.drawPixels(15, textY - 11, xPos + 3, 0x6f695d, menuWidth - 6);
				disColor = 0xeee5c6;
			}
			newBoldFont.drawBasicString(menuActionName[l1], xPos + 3, textY, disColor, 1);
		}
	}

	private void addFriend(long l) {
		try {
			if (l == 0L)
				return;
			if (friendsCount >= 100 && anInt1046 != 1) {
				pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
				return;
			}
			if (friendsCount >= 200) {
				pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
				return;
			}
			String s = TextClass.fixName(TextClass.nameForLong(l));
			for (int i = 0; i < friendsCount; i++)
				if (friendsListAsLongs[i] == l) {
					pushMessage(s + " is already on your friend list", 0, "");
					return;
				}
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage("Please remove " + s + " from your ignore list first", 0, "");
					return;
				}

			if (s.equals(myPlayer.name)) {
				pushMessage("You may not add yourself!", 0, "");
				return;
			} else {
				friendsList[friendsCount] = s;
				friendsListAsLongs[friendsCount] = l;
				friendsNodeIDs[friendsCount] = 0;
				friendsCount++;
				stream.createFrame(188);
				stream.writeQWord(l);
				return;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("15283, " + (byte) 68 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private int method42(int i, int j, int k) {
		int l = k >> 7;
		int i1 = j >> 7;
		if (l < 0 || i1 < 0 || l > 103 || i1 > 103)
			return 0;
		int j1 = i;
		if (j1 < 3 && (byteGroundArray[1][l][i1] & 2) == 2)
			j1++;
		int k1 = k & 0x7f;
		int l1 = j & 0x7f;
		int i2 = intGroundArray[j1][l][i1] * (128 - k1) + intGroundArray[j1][l + 1][i1] * k1 >> 7;
		int j2 = intGroundArray[j1][l][i1 + 1] * (128 - k1) + intGroundArray[j1][l + 1][i1 + 1] * k1 >> 7;
		return i2 * (128 - l1) + j2 * l1 >> 7;
	}

	private static String intToKOrMil(int j) {
		if (j < 0x186a0)
			return String.valueOf(j);
		if (j < 0x989680)
			return j / 1000 + "K";
		else
			return j / 0xf4240 + "M";
	}

	public void resetLogout() {
		try {
			if (socketStream != null)
				socketStream.close();
		} catch (Exception _ex) {
		}
		if (rememberMe) {
			AccountManager.saveAccount();
		}
		setBounds();
		socketStream = null;
		loggedIn = false;
		loginScreenState = 0;
		loginMessage1 = "Welcome to Vencillo";
		loginMessage2 = "Enter in your account details and begin playing!";
		if (!rememberMe) {
			myUsername = "";
			myPassword = "";
		}
		unlinkMRUNodes();
		worldController.initToNull();
		for (int i = 0; i < 4; i++)
			aClass11Array1230[i].method210();
		System.gc();
		stopMidi();
		currentSong = -1;
		nextSong = -1;
		prevSong = 0;
		SettingHandler.save();
		console.openConsole = false;
	}

	private void method45() {
		aBoolean1031 = true;
		for (int j = 0; j < 7; j++) {
			anIntArray1065[j] = -1;
			for (int k = 0; k < IdentityKit.length; k++) {
				if (IdentityKit.cache[k].aBoolean662 || IdentityKit.cache[k].anInt657 != j + (aBoolean1047 ? 0 : 7))
					continue;
				anIntArray1065[j] = k;
				break;
			}
		}
	}

	private void method46(int i, Stream stream) {
		while (stream.bitPosition + 21 < i * 8) {
			int k = stream.readBits(14);
			if (k == 16383)
				break;
			if (npcArray[k] == null)
				npcArray[k] = new Npc();
			Npc npc = npcArray[k];
			npcIndices[npcCount++] = k;
			npc.anInt1537 = loopCycle;
			int l = stream.readBits(5);
			if (l > 15)
				l -= 32;
			int i1 = stream.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = stream.readBits(1);
			npc.desc = EntityDef.forID(stream.readBits(npcBits));
			int k1 = stream.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = k;
			npc.anInt1540 = npc.desc.aByte68;
			npc.anInt1504 = npc.desc.anInt79;
			npc.anInt1554 = npc.desc.walkAnim;
			npc.anInt1555 = npc.desc.anInt58;
			npc.anInt1556 = npc.desc.anInt83;
			npc.anInt1557 = npc.desc.anInt55;
			npc.anInt1511 = npc.desc.standAnim;
			npc.setPos(myPlayer.smallX[0] + i1, myPlayer.smallY[0] + l, j1 == 1);
		}
		stream.finishBitAccess();
	}

	public void processGameLoop() {
		if (rsAlreadyLoaded || loadingError || genericLoadingError)
			return;
		loopCycle++;
		if (!loggedIn)
			loginRenderer.processLoginScreen();
		else
			mainGameProcessor();
		processOnDemandQueue();
	}

	private void method47(boolean flag) {
		if (myPlayer.x >> 7 == destX && myPlayer.y >> 7 == destY) {
			destX = 0;
		}
		int j = playerCount;
		if (flag) {
			j = 1;
		}
		for (int l = 0; l < j; l++) {
			Player player;
			int i1;
			if (flag) {
				player = myPlayer;
				i1 = myPlayerIndex << 14;
			} else {
				player = playerArray[playerIndices[l]];
				i1 = playerIndices[l] << 14;
			}
			if (player == null || !player.isVisible()) {
				continue;
			}
			player.aBoolean1699 = (lowMem && playerCount > 50 || playerCount > 200) && !flag && player.anInt1517 == player.anInt1511;
			int j1 = player.x >> 7;
			int k1 = player.y >> 7;
			if (j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104) {
				continue;
			}
			if (player.aModel_1714 != null && loopCycle >= player.anInt1707 && loopCycle < player.anInt1708) {
				player.aBoolean1699 = false;
				player.anInt1709 = method42(plane, player.y, player.x);
				worldController.method286(plane, player.y, player, player.anInt1552, player.anInt1722, player.x, player.anInt1709, player.anInt1719, player.anInt1721, i1, player.anInt1720);
				continue;
			}
			if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
				if (anIntArrayArray929[j1][k1] == anInt1265) {
					continue;
				}
				anIntArrayArray929[j1][k1] = anInt1265;
			}
			player.anInt1709 = method42(plane, player.y, player.x);
			worldController.method285(plane, player.anInt1552, player.anInt1709, i1, player.y, 60, player.x, player, player.aBoolean1541);
		}
	}

	private boolean promptUserForInput(RSInterface class9) {
		int j = class9.contentType;
		System.out.println(anInt900);
		if (anInt900 == 2) {
			if (j == 51504) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 51504;
				aString1121 = "Enter the player's profile you want to view.";
			}
			if (j == 201) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 1;
				aString1121 = "Enter name of friend to add to list";
			}
			if (j == 59800) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 59800;
				aString1121 = "Enter the item name you are looking for";
			}
			if (j == 202) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 2;
				aString1121 = "Enter name of friend to delete from list";
			}
		}
		if (j == 0xBABE) {
			int subShopIndex = (8 + (CustomInterfaces.shopCategories.length * 3)) - 1;
			for (int index = 0, frame = 0; index < (CustomInterfaces.shopCategories.length * 3); index += 3) {
				if (class9.id == (55000 + frame + 9)) {
					// if ((class9.id != 55009) && (class9.id != 55237)) {
					// pushMessage("Title category coming soon!", 0, "");
					// return true;
					// }
					RSInterface.interfaceCache[55000].children[subShopIndex] = 55000 + frame + 13;
					return true;
				}
				frame += (CustomInterfaces.shopContent[index / 3].length * 8) + 5;
			}
			return true;
		}
		if (j == 205) {
			anInt1011 = 250;
			return true;
		}
		if (j == 501) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 4;
			aString1121 = "Enter name of player to add to list";
		}
		if (j == 502) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 5;
			aString1121 = "Enter name of player to delete from list";
		}
		if (j == 550) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 6;
			aString1121 = "Enter the name of the chat you wish to join";
		}
		if (j >= 300 && j <= 313) {
			int k = (j - 300) / 2;
			int j1 = j & 1;
			int i2 = anIntArray1065[k];
			if (i2 != -1) {
				do {
					if (j1 == 0 && --i2 < 0)
						i2 = IdentityKit.length - 1;
					if (j1 == 1 && ++i2 >= IdentityKit.length)
						i2 = 0;
				} while (IdentityKit.cache[i2].aBoolean662 || IdentityKit.cache[i2].anInt657 != k + (aBoolean1047 ? 0 : 7));
				anIntArray1065[k] = i2;
				aBoolean1031 = true;
			}
		}
		if (j >= 314 && j <= 323) {
			int l = (j - 314) / 2;
			int k1 = j & 1;
			int j2 = anIntArray990[l];
			if (k1 == 0 && --j2 < 0)
				j2 = anIntArrayArray1003[l].length - 1;
			if (k1 == 1 && ++j2 >= anIntArrayArray1003[l].length)
				j2 = 0;
			anIntArray990[l] = j2;
			aBoolean1031 = true;
		}
		if (j == 324 && !aBoolean1047) {
			aBoolean1047 = true;
			method45();
		}
		if (j == 325 && aBoolean1047) {
			aBoolean1047 = false;
			method45();
		}
		if (j == 326) {
			stream.createFrame(101);
			stream.writeWordBigEndian(aBoolean1047 ? 0 : 1);
			for (int i1 = 0; i1 < 7; i1++)
				stream.writeWordBigEndian(anIntArray1065[i1]);

			for (int l1 = 0; l1 < 5; l1++)
				stream.writeWordBigEndian(anIntArray990[l1]);

			return true;
		}
		if (j == 613)
			canMute = !canMute;
		if (j >= 601 && j <= 612) {
			clearTopInterfaces();
			if (reportAbuseInput.length() > 0) {
				stream.createFrame(218);
				stream.writeQWord(TextClass.longForName(reportAbuseInput));
				stream.writeWordBigEndian(j - 601);
				stream.writeWordBigEndian(canMute ? 1 : 0);
			}
		}
		return false;
	}

	private void method49(Stream stream) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Player player = playerArray[k];
			int l = stream.readUnsignedByte();
			if ((l & 0x40) != 0)
				l += stream.readUnsignedByte() << 8;
			appendPlayerUpdateMask(l, k, stream, player);
		}
	}

	private void method50(int i, int k, int l, int i1, int j1) {
		int k1 = worldController.method300(j1, l, i);
		if (k1 != 0) {
			int l1 = worldController.method304(j1, l, i, k1);
			int k2 = l1 >> 6 & 3;
			int i3 = l1 & 0x1f;
			int k3 = k;
			if (k1 > 0)
				k3 = i1;
			int ai[] = minimapImage.myPixels;
			int k4 = 24624 + l * 4 + (103 - i) * 512 * 4;
			int i5 = k1 >> 14 & 0x7fff;
			ObjectDef class46_2 = ObjectDef.forID(i5);
			if (class46_2.anInt758 != -1) {
				Background background_2 = mapScenes[class46_2.anInt758];
				if (background_2 != null) {
					int i6 = (class46_2.anInt744 * 4 - background_2.anInt1452) / 2;
					int j6 = (class46_2.anInt761 * 4 - background_2.anInt1453) / 2;
					background_2.drawBackground(48 + l * 4 + i6, 48 + (104 - i - class46_2.anInt761) * 4 + j6);
				}
			} else {
				if (i3 == 0 || i3 == 2)
					if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 1) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 2) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 3) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
				if (i3 == 3)
					if (k2 == 0)
						ai[k4] = k3;
					else if (k2 == 1)
						ai[k4 + 3] = k3;
					else if (k2 == 2)
						ai[k4 + 3 + 1536] = k3;
					else if (k2 == 3)
						ai[k4 + 1536] = k3;
				if (i3 == 2)
					if (k2 == 3) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 1) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 2) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
			}
		}
		k1 = worldController.method302(j1, l, i);
		if (k1 != 0) {
			int i2 = worldController.method304(j1, l, i, k1);
			int l2 = i2 >> 6 & 3;
			int j3 = i2 & 0x1f;
			int l3 = k1 >> 14 & 0x7fff;
			ObjectDef class46_1 = ObjectDef.forID(l3);
			if (class46_1.anInt758 != -1) {
				Background background_1 = mapScenes[class46_1.anInt758];
				if (background_1 != null) {
					int j5 = (class46_1.anInt744 * 4 - background_1.anInt1452) / 2;
					int k5 = (class46_1.anInt761 * 4 - background_1.anInt1453) / 2;
					background_1.drawBackground(48 + l * 4 + j5, 48 + (104 - i - class46_1.anInt761) * 4 + k5);
				}
			} else if (j3 == 9) {
				int l4 = 0xeeeeee;
				if (k1 > 0)
					l4 = 0xee0000;
				int ai1[] = minimapImage.myPixels;
				int l5 = 24624 + l * 4 + (103 - i) * 512 * 4;
				if (l2 == 0 || l2 == 2) {
					ai1[l5 + 1536] = l4;
					ai1[l5 + 1024 + 1] = l4;
					ai1[l5 + 512 + 2] = l4;
					ai1[l5 + 3] = l4;
				} else {
					ai1[l5] = l4;
					ai1[l5 + 512 + 1] = l4;
					ai1[l5 + 1024 + 2] = l4;
					ai1[l5 + 1536 + 3] = l4;
				}
			}
		}
		k1 = worldController.method303(j1, l, i);
		if (k1 != 0) {
			int j2 = k1 >> 14 & 0x7fff;
			ObjectDef class46 = ObjectDef.forID(j2);
			if (class46.anInt758 != -1) {
				Background background = mapScenes[class46.anInt758];
				if (background != null) {
					int i4 = (class46.anInt744 * 4 - background.anInt1452) / 2;
					int j4 = (class46.anInt761 * 4 - background.anInt1453) / 2;
					background.drawBackground(48 + l * 4 + i4, 48 + (104 - i - class46.anInt761) * 4 + j4);
				}
			}
		}
	}

	private void loadTitleScreen() {
		aBackground_966 = new Background(titleStreamLoader, "titlebox", 0);
		aBackground_967 = new Background(titleStreamLoader, "titlebutton", 0);
		aBackgroundArray1152s = new Background[12];
		int j = 0;
		try {
			j = Integer.parseInt(getParameter("fl_icon"));
		} catch (Exception _ex) {
		}
		if (j == 0) {
			for (int k = 0; k < 12; k++)
				aBackgroundArray1152s[k] = new Background(titleStreamLoader, "runes", k);

		} else {
			for (int l = 0; l < 12; l++)
				aBackgroundArray1152s[l] = new Background(titleStreamLoader, "runes", 12 + (l & 3));

		}
		aClass30_Sub2_Sub1_Sub1_1201 = new Sprite(128, 265);
		aClass30_Sub2_Sub1_Sub1_1202 = new Sprite(128, 265);
		System.arraycopy(aRSImageProducer_1110.canvasRaster, 0, aClass30_Sub2_Sub1_Sub1_1201.myPixels, 0, 33920);

		System.arraycopy(aRSImageProducer_1111.canvasRaster, 0, aClass30_Sub2_Sub1_Sub1_1202.myPixels, 0, 33920);

		anIntArray851 = new int[256];
		for (int k1 = 0; k1 < 64; k1++)
			anIntArray851[k1] = k1 * 0x40000;

		for (int l1 = 0; l1 < 64; l1++)
			anIntArray851[l1 + 64] = 0xff0000 + 1024 * l1;

		for (int i2 = 0; i2 < 64; i2++)
			anIntArray851[i2 + 128] = 0xffff00 + 4 * i2;

		for (int j2 = 0; j2 < 64; j2++)
			anIntArray851[j2 + 192] = 0xffffff;

		anIntArray852 = new int[256];
		for (int k2 = 0; k2 < 64; k2++)
			anIntArray852[k2] = k2 * 1024;

		for (int l2 = 0; l2 < 64; l2++)
			anIntArray852[l2 + 64] = 65280 + 4 * l2;

		for (int i3 = 0; i3 < 64; i3++)
			anIntArray852[i3 + 128] = 65535 + 0x40000 * i3;

		for (int j3 = 0; j3 < 64; j3++)
			anIntArray852[j3 + 192] = 0xffffff;

		anIntArray853 = new int[256];
		for (int k3 = 0; k3 < 64; k3++)
			anIntArray853[k3] = k3 * 4;

		for (int l3 = 0; l3 < 64; l3++)
			anIntArray853[l3 + 64] = 255 + 0x40000 * l3;

		for (int i4 = 0; i4 < 64; i4++)
			anIntArray853[i4 + 128] = 0xff00ff + 1024 * i4;

		for (int j4 = 0; j4 < 64; j4++)
			anIntArray853[j4 + 192] = 0xffffff;

		anIntArray850 = new int[256];
		anIntArray1190 = new int[32768];
		anIntArray1191 = new int[32768];
		randomizeBackground(null);
		anIntArray828 = new int[32768];
		anIntArray829 = new int[32768];
		drawLoadingText(10, "Connecting to fileserver");
		if (!aBoolean831) {
			drawFlames = true;
			aBoolean831 = true;
			startRunnable(this, 2);
		}
	}

	private static void setHighMem() {
		WorldController.lowMem = false;
		Rasterizer.lowMem = false;
		lowMem = false;
		ObjectManager.lowMem = false;
		ObjectDef.lowMem = false;
	}

	public static void main(String args[]) {
		try {
			if (ClientConstants.LOCALHOST) {
				server = "localhost";
			} else if (args != null && args.length == 1) {
				server = args[0];
			}
			nodeID = 10;
			portOff = 0;
			setHighMem();
			isMembers = true;
			Signlink.storeid = 32;
			Signlink.startpriv(InetAddress.getLocalHost());
			frameMode(ScreenMode.FIXED);
			instance = new Client();
			instance.createClientFrame(frameWidth, frameHeight);
			SettingHandler.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Client instance;

	private void loadingStages() {
		if (lowMem && loadingStage == 2 && ObjectManager.anInt131 != plane) {
			aRSImageProducer_1165.initDrawingArea();
			drawLoadingMessages(1, "Loading - please wait.", null);
			aRSImageProducer_1165.drawGraphics(frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics, frameMode == ScreenMode.FIXED ? 4 : 0);
			loadingStage = 1;
			aLong824 = System.currentTimeMillis();
		}
		if (loadingStage == 1) {
			int j = method54();
			if (j != 0 && System.currentTimeMillis() - aLong824 > 0x57e40L) {
				Signlink.reporterror(myUsername + " glcfb " + aLong1215 + "," + j + "," + lowMem + "," + decompressors[0] + "," + onDemandFetcher.getNodeCount() + "," + plane + "," + anInt1069 + "," + anInt1070);
				aLong824 = System.currentTimeMillis();
			}
		}
		if (loadingStage == 2 && plane != anInt985) {
			anInt985 = plane;
			method24(plane);
		}
	}

	private int method54() {
		for (int i = 0; i < aByteArrayArray1183.length; i++) {
			if (aByteArrayArray1183[i] == null && anIntArray1235[i] != -1)
				return -1;
			if (aByteArrayArray1247[i] == null && anIntArray1236[i] != -1)
				return -2;
		}
		boolean flag = true;
		for (int j = 0; j < aByteArrayArray1183.length; j++) {
			byte abyte0[] = aByteArrayArray1247[j];
			if (abyte0 != null) {
				int k = (anIntArray1234[j] >> 8) * 64 - baseX;
				int l = (anIntArray1234[j] & 0xff) * 64 - baseY;
				if (aBoolean1159) {
					k = 10;
					l = 10;
				}
				flag &= ObjectManager.method189(k, abyte0, l);
			}
		}
		if (!flag)
			return -3;
		if (aBoolean1080) {
			return -4;
		} else {
			loadingStage = 2;
			ObjectManager.anInt131 = plane;
			method22();
			stream.createFrame(121);
			return 0;
		}
	}

	private void method55() {
		for (Animable_Sub4 class30_sub2_sub4_sub4 = (Animable_Sub4) aClass19_1013.reverseGetFirst(); class30_sub2_sub4_sub4 != null; class30_sub2_sub4_sub4 = (Animable_Sub4) aClass19_1013.reverseGetNext())
			if (class30_sub2_sub4_sub4.anInt1597 != plane || loopCycle > class30_sub2_sub4_sub4.anInt1572)
				class30_sub2_sub4_sub4.unlink();
			else if (loopCycle >= class30_sub2_sub4_sub4.anInt1571) {
				if (class30_sub2_sub4_sub4.anInt1590 > 0) {
					Npc npc = npcArray[class30_sub2_sub4_sub4.anInt1590 - 1];
					if (npc != null && npc.x >= 0 && npc.x < 13312 && npc.y >= 0 && npc.y < 13312)
						class30_sub2_sub4_sub4.method455(loopCycle, npc.y, method42(class30_sub2_sub4_sub4.anInt1597, npc.y, npc.x) - class30_sub2_sub4_sub4.anInt1583, npc.x);
				}
				if (class30_sub2_sub4_sub4.anInt1590 < 0) {
					int j = -class30_sub2_sub4_sub4.anInt1590 - 1;
					Player player;
					if (j == unknownInt10)
						player = myPlayer;
					else
						player = playerArray[j];
					if (player != null && player.x >= 0 && player.x < 13312 && player.y >= 0 && player.y < 13312)
						class30_sub2_sub4_sub4.method455(loopCycle, player.y, method42(class30_sub2_sub4_sub4.anInt1597, player.y, player.x) - class30_sub2_sub4_sub4.anInt1583, player.x);
				}
				class30_sub2_sub4_sub4.method456(anInt945);
				worldController.method285(plane, class30_sub2_sub4_sub4.anInt1595, (int) class30_sub2_sub4_sub4.aDouble1587, -1, (int) class30_sub2_sub4_sub4.aDouble1586, 60, (int) class30_sub2_sub4_sub4.aDouble1585, class30_sub2_sub4_sub4, false);
			}

	}

	public AppletContext getAppletContext() {
		if (Signlink.mainapp != null)
			return Signlink.mainapp.getAppletContext();
		else
			return super.getAppletContext();
	}

	private void processOnDemandQueue() {
		do {
			OnDemandData onDemandData;
			do {
				onDemandData = onDemandFetcher.getNextNode();
				if (onDemandData == null)
					return;
				if (onDemandData.dataType == 0) {
					Model.method460(onDemandData.buffer, onDemandData.ID);
					if (backDialogID != -1)
						inputTaken = true;
				}
				if (onDemandData.dataType == 1) {
					SequenceFrame.load(onDemandData.ID, onDemandData.buffer);
				}
				if (onDemandData.dataType == 2 && onDemandData.ID == nextSong && onDemandData.buffer != null)
					saveMidi(songChanging, onDemandData.buffer);
				/*
				 * if (onDemandData.dataType == 4) {
				 * Texture.decode(onDemandData.ID, onDemandData.buffer); }
				 */
				if (onDemandData.dataType == 3 && loadingStage == 1) {
					for (int i = 0; i < aByteArrayArray1183.length; i++) {
						if (anIntArray1235[i] == onDemandData.ID) {
							aByteArrayArray1183[i] = onDemandData.buffer;
							if (onDemandData.buffer == null)
								anIntArray1235[i] = -1;
							break;
						}
						if (anIntArray1236[i] != onDemandData.ID)
							continue;
						aByteArrayArray1247[i] = onDemandData.buffer;
						if (onDemandData.buffer == null)
							anIntArray1236[i] = -1;
						break;
					}

				}
			} while (onDemandData.dataType != 93 || !onDemandFetcher.method564(onDemandData.ID));
			ObjectManager.method173(new Stream(onDemandData.buffer), onDemandFetcher);
		} while (true);
	}

	private void method60(int i) {
		RSInterface class9 = RSInterface.interfaceCache[i];
		if (class9 == null || class9.children == null) {
			return;
		}
		for (int j = 0; j < class9.children.length; j++) {
			if (class9.children[j] == -1)
				break;
			RSInterface class9_1 = RSInterface.interfaceCache[class9.children[j]];
			if (class9_1.type == 1)
				method60(class9_1.id);
			class9_1.anInt246 = 0;
			class9_1.anInt208 = 0;
		}
	}

	private void drawHeadIcon() {
		if (anInt855 != 2) {
			return;
		}
		calcEntityScreenPos((anInt934 - baseX << 7) + anInt937, anInt936 * 2, (anInt935 - baseY << 7) + anInt938);
		if (spriteDrawX > -1 && loopCycle % 20 < 10) {
			headIconsHint[1].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
		}
	}

	private void mainGameProcessor() {
		refreshFrameSize();
		if (anInt1104 > 1)
			anInt1104--;
		if (anInt1011 > 0)
			anInt1011--;
		for (int j = 0; j < 5; j++)
			if (!parsePacket())
				break;

		if (!loggedIn)
			return;
		synchronized (mouseDetection.syncObject) {
			if (flagged) {
				if (super.clickMode3 != 0 || mouseDetection.coordsIndex >= 40) {
					stream.createFrame(45);
					stream.writeWordBigEndian(0);
					int j2 = stream.currentOffset;
					int j3 = 0;
					for (int j4 = 0; j4 < mouseDetection.coordsIndex; j4++) {
						if (j2 - stream.currentOffset >= 240)
							break;
						j3++;
						int l4 = mouseDetection.coordsY[j4];
						if (l4 < 0)
							l4 = 0;
						else if (l4 > 502)
							l4 = 502;
						int k5 = mouseDetection.coordsX[j4];
						if (k5 < 0)
							k5 = 0;
						else if (k5 > 764)
							k5 = 764;
						int i6 = l4 * 765 + k5;
						if (mouseDetection.coordsY[j4] == -1 && mouseDetection.coordsX[j4] == -1) {
							k5 = -1;
							l4 = -1;
							i6 = 0x7ffff;
						}
						if (k5 == anInt1237 && l4 == anInt1238) {
							if (anInt1022 < 2047)
								anInt1022++;
						} else {
							int j6 = k5 - anInt1237;
							anInt1237 = k5;
							int k6 = l4 - anInt1238;
							anInt1238 = l4;
							if (anInt1022 < 8 && j6 >= -32 && j6 <= 31 && k6 >= -32 && k6 <= 31) {
								j6 += 32;
								k6 += 32;
								stream.writeWord((anInt1022 << 12) + (j6 << 6) + k6);
								anInt1022 = 0;
							} else if (anInt1022 < 8) {
								stream.writeDWordBigEndian(0x800000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							} else {
								stream.writeDWord(0xc0000000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							}
						}
					}

					stream.writeBytes(stream.currentOffset - j2);
					if (j3 >= mouseDetection.coordsIndex) {
						mouseDetection.coordsIndex = 0;
					} else {
						mouseDetection.coordsIndex -= j3;
						for (int i5 = 0; i5 < mouseDetection.coordsIndex; i5++) {
							mouseDetection.coordsX[i5] = mouseDetection.coordsX[i5 + j3];
							mouseDetection.coordsY[i5] = mouseDetection.coordsY[i5 + j3];
						}

					}
				}
			} else {
				mouseDetection.coordsIndex = 0;
			}
		}
		if (super.clickMode3 != 0) {
			long l = (super.aLong29 - aLong1220) / 50L;
			if (l > 4095L)
				l = 4095L;
			aLong1220 = super.aLong29;
			int k2 = super.saveClickY;
			if (k2 < 0)
				k2 = 0;
			else if (k2 > 502)
				k2 = 502;
			int k3 = super.saveClickX;
			if (k3 < 0)
				k3 = 0;
			else if (k3 > 764)
				k3 = 764;
			int k4 = k2 * 765 + k3;
			int j5 = 0;
			if (super.clickMode3 == 2)
				j5 = 1;
			int l5 = (int) l;
			stream.createFrame(241);
			stream.writeDWord((l5 << 20) + (j5 << 19) + k4);
		}
		if (anInt1016 > 0)
			anInt1016--;
		if (super.keyArray[1] == 1 || super.keyArray[2] == 1 || super.keyArray[3] == 1 || super.keyArray[4] == 1)
			aBoolean1017 = true;
		if (aBoolean1017 && anInt1016 <= 0) {
			anInt1016 = 20;
			aBoolean1017 = false;
			stream.createFrame(86);
			stream.writeWord(anInt1184);
			stream.method432(minimapInt1);
		}
		if (super.awtFocus && !aBoolean954) {
			aBoolean954 = true;
			stream.createFrame(3);
			stream.writeWordBigEndian(1);
		}
		if (!super.awtFocus && aBoolean954) {
			aBoolean954 = false;
			stream.createFrame(3);
			stream.writeWordBigEndian(0);
		}
		loadingStages();
		method115();
		anInt1009++;
		if (anInt1009 > 750)
			dropClient();
		method114();
		method95();
		method38();
		anInt945++;
		if (crossType != 0) {
			crossIndex += 20;
			if (crossIndex >= 400)
				crossType = 0;
		}
		if (atInventoryInterfaceType != 0) {
			atInventoryLoopCycle++;
			if (atInventoryLoopCycle >= 15) {
				if (atInventoryInterfaceType == 2) {
				}
				if (atInventoryInterfaceType == 3)
					inputTaken = true;
				atInventoryInterfaceType = 0;
			}
		}
		if (activeInterfaceType != 0) {
			dragCycle++;
			if (super.mouseX > pressX + 5 || super.mouseX < pressX - 5 || super.mouseY > pressY + 5 || super.mouseY < pressY - 5)
				aBoolean1242 = true;
			if (super.clickMode2 == 0) {
				if (activeInterfaceType == 2) {
				}
				if (activeInterfaceType == 3)
					inputTaken = true;
				activeInterfaceType = 0;
				if (aBoolean1242 && dragCycle >= 15) {
					lastActiveInvInterface = -1;
					processRightClick();
					if (focusedDragWidget == 5382) {
						Point southWest, northEast;

						if (frameMode == ScreenMode.FIXED) {
							southWest = new Point(56, 81);
							northEast = new Point(101, 41);
						} else {
							int xOffset = (frameWidth - 237 - RSInterface.interfaceCache[5292].width) / 2;
							int yOffset = 36 + ((frameHeight - 503) / 2);
							southWest = new Point(xOffset + 76, yOffset + 62);
							northEast = new Point(xOffset + 117, yOffset + 22);
						}

						int[] slots = new int[10];

						for (int i = 0; i < slots.length; i++) {
							slots[i] = (40 * i) + (int) southWest.getX();
						}

						for (int i = 0; i < slots.length; i++) {
							if ((super.mouseX >= slots[i]) && (super.mouseX <= (slots[i] + 41)) && (super.mouseY >= northEast.getY()) && (super.mouseY <= southWest.getY())) {
								stream.createFrame(214);
								stream.method433(focusedDragWidget);
								stream.method424(2);
								stream.method433(dragFromSlot);
								stream.method431(i);
								return;
							}
						}
					}

					if (lastActiveInvInterface == -1 && focusedDragWidget == 3214 && frameMode == ScreenMode.FIXED) {
						if (super.mouseX <= 516 && super.mouseY <= 338 && super.mouseX >= 0 && super.mouseY >= 0) {
							stream.createFrame(87);
							stream.method432(RSInterface.interfaceCache[3214].inv[dragFromSlot] - 1);
							stream.writeWord(focusedDragWidget);
							stream.method432(dragFromSlot);
						}
					} else if (lastActiveInvInterface == focusedDragWidget && mouseInvInterfaceIndex != dragFromSlot) {
						RSInterface class9 = RSInterface.interfaceCache[focusedDragWidget];
						int j1 = 0;
						if (anInt913 == 1 && class9.contentType == 206)
							j1 = 1;
						if (class9.inv[mouseInvInterfaceIndex] <= 0)
							j1 = 0;
						if (class9.aBoolean235) {
							int l2 = dragFromSlot;
							int l3 = mouseInvInterfaceIndex;
							class9.inv[l3] = class9.inv[l2];
							class9.invStackSizes[l3] = class9.invStackSizes[l2];
							class9.inv[l2] = -1;
							class9.invStackSizes[l2] = 0;
						} else if (j1 == 0) {
							class9.swapInventoryItems(dragFromSlot, mouseInvInterfaceIndex);
						}

						stream.createFrame(214);
						stream.method433(focusedDragWidget);
						stream.method424(j1);
						stream.method433(dragFromSlot);
						stream.method431(mouseInvInterfaceIndex);
					}
				} else if ((anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2)
					determineMenuSize();
				else if (menuActionRow > 0)
					doAction(menuActionRow - 1);
				atInventoryLoopCycle = 10;
				super.clickMode3 = 0;
			}
		}
		if (WorldController.anInt470 != -1) {
			int k = WorldController.anInt470;
			int k1 = WorldController.anInt471;
			boolean flag = doWalkTo(0, 0, 0, 0, myPlayer.smallY[0], 0, 0, k1, myPlayer.smallX[0], true, k);
			WorldController.anInt470 = -1;
			if (flag) {
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 1;
				crossIndex = 0;
			}
		}
		if (super.clickMode3 == 1 && aString844 != null) {
			aString844 = null;
			inputTaken = true;
			super.clickMode3 = 0;
		}
		processMenuClick();
		if (super.clickMode2 == 1 || super.clickMode3 == 1)
			anInt1213++;
		if (anInt1500 != 0 || anInt1044 != 0 || anInt1129 != 0) {
			if (anInt1501 < 0 && !menuOpen) {
				anInt1501++;
				if (anInt1501 == 0) {
					if (anInt1500 != 0) {
						inputTaken = true;
					}
					if (anInt1044 != 0) {
					}
				}
			}
		} else if (anInt1501 > 0) {
			anInt1501--;
		}
		if (loadingStage == 2)
			method108();
		if (loadingStage == 2 && aBoolean1160)
			calcCameraPos();
		for (int i1 = 0; i1 < 5; i1++)
			anIntArray1030[i1]++;

		method73();
		super.idleTime++;
		if (super.idleTime > 4500) {
			anInt1011 = 250;
			super.idleTime -= 500;
			stream.createFrame(202);
		}
		anInt1010++;
		if (anInt1010 > 50)
			stream.createFrame(0);
		try {
			if (socketStream != null && stream.currentOffset > 0) {
				socketStream.queueBytes(stream.currentOffset, stream.buffer);
				stream.currentOffset = 0;
				anInt1010 = 0;
			}
		} catch (IOException _ex) {
			dropClient();
		} catch (Exception exception) {
			resetLogout();
		}
	}

	private void method63() {
		Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst();
		for (; class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext())
			if (class30_sub1.anInt1294 == -1) {
				class30_sub1.anInt1302 = 0;
				method89(class30_sub1);
			} else {
				class30_sub1.unlink();
			}

	}

	void resetImageProducers() {
		if (aRSImageProducer_1107 != null)
			return;
		super.fullGameScreen = null;
		aRSImageProducer_1166 = null;
		aRSImageProducer_1164 = null;
		aRSImageProducer_1163 = null;
		aRSImageProducer_1165 = null;
		aRSImageProducer_1125 = null;
		aRSImageProducer_1110 = new ImageProducer(128, 265);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1111 = new ImageProducer(128, 265);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1107 = new ImageProducer(509, 171);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1108 = new ImageProducer(360, 132);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1109 = new ImageProducer(frameWidth, frameHeight);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1112 = new ImageProducer(202, 238);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1113 = new ImageProducer(203, 238);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1114 = new ImageProducer(74, 94);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1115 = new ImageProducer(75, 94);
		DrawingArea.setAllPixelsToZero();
		welcomeScreenRaised = true;
	}

	public float loadPercent = 0.0F;

	public void drawSmoothLoading(int percent, String message) {
		for (float perc = loadPercent; perc < (float) percent; perc = (float) ((double) perc + 0.29999999999999999D)) {
			drawLoadingText((int) perc, message);
		}
		loadPercent = percent;
	}

	private static Sprite BACKGROUND;
	private static Sprite LOADING_BAR;

	static {
		try {
			BACKGROUND = new Sprite(new URL("http://www.vencillio.com/Daniel/11.png"));
			LOADING_BAR = new Sprite(new URL("http://www.vencillio.com/Daniel/12.png"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void drawLoadingText(int percent, String message) {
		anInt1079 = percent;
		aString1049 = message;
		resetImageProducers();
		if (titleStreamLoader == null) {
			super.drawLoadingText(percent, message);
			return;
		}
		refreshFrameSize();
		aRSImageProducer_1109.initDrawingArea();

		BACKGROUND.drawSprite((frameWidth / 2) - (BACKGROUND.myWidth / 2), (frameHeight / 2) - (BACKGROUND.myHeight / 2));
		LOADING_BAR.drawSprite((frameWidth / 2) - 274, (frameHeight / 2) - 10);
		DrawingArea.drawPixels(36, (frameHeight / 2) - 10, ((frameWidth / 2) - 274 + percent), 0x302e2c, (530 - percent));
		regularText.method382(0xffffff, (frameWidth / 2), message, (frameHeight / 2) + 12, true);
		aRSImageProducer_1109.drawGraphics(0, super.graphics, 0);
		if (welcomeScreenRaised) {
			welcomeScreenRaised = false;
		}
	}

	private void method65(int i, int j, int k, int l, RSInterface class9, int i1, boolean flag, int j1) {
		int anInt992;
		if (aBoolean972)
			anInt992 = 32;
		else
			anInt992 = 0;
		aBoolean972 = false;
		if (k >= i && k < i + 16 && l >= i1 && l < i1 + 16) {
			class9.scrollPosition -= anInt1213 * 4;
			if (flag) {
			}
		} else if (k >= i && k < i + 16 && l >= (i1 + j) - 16 && l < i1 + j) {
			class9.scrollPosition += anInt1213 * 4;
			if (flag) {
			}
		} else if (k >= i - anInt992 && k < i + 16 + anInt992 && l >= i1 + 16 && l < (i1 + j) - 16 && anInt1213 > 0) {
			int l1 = ((j - 32) * j) / j1;
			if (l1 < 8)
				l1 = 8;
			int i2 = l - i1 - 16 - l1 / 2;
			int j2 = j - 32 - l1;
			class9.scrollPosition = ((j1 - j) * i2) / j2;
			if (flag) {
			}
			aBoolean972 = true;
		}
	}

	private boolean method66(int i, int j, int k) {
		int i1 = i >> 14 & 0x7fff;
		int j1 = worldController.method304(plane, k, j, i);
		if (j1 == -1)
			return false;
		int k1 = j1 & 0x1f;
		int l1 = j1 >> 6 & 3;
		if (k1 == 10 || k1 == 11 || k1 == 22) {
			ObjectDef class46 = ObjectDef.forID(i1);
			int i2;
			int j2;
			if (l1 == 0 || l1 == 2) {
				i2 = class46.anInt744;
				j2 = class46.anInt761;
			} else {
				i2 = class46.anInt761;
				j2 = class46.anInt744;
			}
			int k2 = class46.anInt768;
			if (l1 != 0)
				k2 = (k2 << l1 & 0xf) + (k2 >> 4 - l1);
			doWalkTo(2, 0, j2, 0, myPlayer.smallY[0], i2, k2, j, myPlayer.smallX[0], false, k);
		} else {
			doWalkTo(2, l1, 0, k1 + 1, myPlayer.smallY[0], 0, 0, j, myPlayer.smallX[0], false, k);
		}
		crossX = super.saveClickX;
		crossY = super.saveClickY;
		crossType = 2;
		crossIndex = 0;
		return true;
	}

	private StreamLoader streamLoaderForName(int i, String s, String s1, int j, int k) {
		byte abyte0[] = null;
		int l = 5;
		try {
			if (decompressors[0] != null)
				abyte0 = decompressors[0].decompress(i);
		} catch (Exception _ex) {
		}
		if (abyte0 != null) {
			// aCRC32_930.reset();
			// aCRC32_930.update(abyte0);
			// int i1 = (int)aCRC32_930.getValue();
			// if(i1 != j)
		}
		if (abyte0 != null) {
			StreamLoader streamLoader = new StreamLoader(abyte0);
			return streamLoader;
		}
		int j1 = 0;
		while (abyte0 == null) {
			String s2 = "Unknown error";
			drawLoadingText(k, "Requesting " + s);
			try {
				int k1 = 0;
				DataInputStream datainputstream = openJagGrabInputStream(s1 + j);
				byte abyte1[] = new byte[6];
				datainputstream.readFully(abyte1, 0, 6);
				Stream stream = new Stream(abyte1);
				stream.currentOffset = 3;
				int i2 = stream.read3Bytes() + 6;
				int j2 = 6;
				abyte0 = new byte[i2];
				System.arraycopy(abyte1, 0, abyte0, 0, 6);

				while (j2 < i2) {
					int l2 = i2 - j2;
					if (l2 > 1000)
						l2 = 1000;
					int j3 = datainputstream.read(abyte0, j2, l2);
					if (j3 < 0) {
						s2 = "Length error: " + j2 + "/" + i2;
						throw new IOException("EOF");
					}
					j2 += j3;
					int k3 = (j2 * 100) / i2;
					if (k3 != k1)
						drawLoadingText(k, "Loading " + s + " - " + k3 + "%");
					k1 = k3;
				}
				datainputstream.close();
				try {
					if (decompressors[0] != null)
						decompressors[0].method234(abyte0.length, abyte0, i);
				} catch (Exception _ex) {
					decompressors[0] = null;
				}
				/*
				 * if(abyte0 != null) { aCRC32_930.reset();
				 * aCRC32_930.update(abyte0); int i3 =
				 * (int)aCRC32_930.getValue(); if(i3 != j) { abyte0 = null;
				 * j1++; s2 = "Checksum error: " + i3; } }
				 */
			} catch (IOException ioexception) {
				if (s2.equals("Unknown error"))
					s2 = "Connection error";
				abyte0 = null;
			} catch (NullPointerException _ex) {
				s2 = "Null error";
				abyte0 = null;
				if (!Signlink.reporterror)
					return null;
			} catch (ArrayIndexOutOfBoundsException _ex) {
				s2 = "Bounds error";
				abyte0 = null;
				if (!Signlink.reporterror)
					return null;
			} catch (Exception _ex) {
				s2 = "Unexpected error";
				abyte0 = null;
				if (!Signlink.reporterror)
					return null;
			}
			if (abyte0 == null) {
				for (int l1 = l; l1 > 0; l1--) {
					if (j1 >= 3) {
						drawLoadingText(k, "Game updated - please reload page");
						l1 = 10;
					} else {
						drawLoadingText(k, s2 + " - Retrying in " + l1);
					}
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				l *= 2;
				if (l > 60)
					l = 60;
				aBoolean872 = !aBoolean872;
			}

		}

		StreamLoader streamLoader_1 = new StreamLoader(abyte0);
		return streamLoader_1;
	}

	private void dropClient() {
		if (anInt1011 > 0) {
			resetLogout();
			return;
		}
		DrawingArea.fillPixels(2, 229, 39, 0xffffff, 2); // white box around
		DrawingArea.drawPixels(37, 3, 3, 0, 227); // black fill
		regularText.drawText(0, "Connection lost.", 19, 120);
		regularText.drawText(0xffffff, "Connection lost.", 18, 119);
		regularText.drawText(0, "Please wait - attempting to reestablish.", 34, 117);
		regularText.drawText(0xffffff, "Please wait - attempting to reestablish.", 34, 116);
		aRSImageProducer_1165.drawGraphics(frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics, frameMode == ScreenMode.FIXED ? 4 : 0);
		anInt1021 = 0;
		destX = 0;
		if (rememberMe) {
			AccountManager.saveAccount();
		}
		RSSocket rsSocket = socketStream;
		loggedIn = false;
		loginFailures = 0;
		setBounds();
		login(myUsername, myPassword, true);
		SettingHandler.save();
		console.openConsole = false;
		if (!loggedIn)
			resetLogout();
		try {
			rsSocket.close();
		} catch (Exception _ex) {
		}
	}

	public void setNorth() {
		anInt1278 = 0;
		anInt1131 = 0;
		anInt896 = 0;
		minimapInt1 = 0;
		minimapInt2 = 0;
		minimapInt3 = 0;
	}

	private void doAction(int i) {
		if (i < 0)
			return;
		if (inputDialogState != 0) {
			inputDialogState = 0;
			inputTaken = true;
		}
		int j = menuActionCmd2[i];
		int k = menuActionCmd3[i];
		int l = menuActionID[i];
		int i1 = menuActionCmd1[i];

		if (l >= 2000)
			l -= 2000;
		if (l == 701) {
			extendChatArea();
		}
		if (l == 713) {
			inputTaken = true;
			messagePromptRaised = true;
			amountOrNameInput = "";
			promptInput = "";
			inputDialogState = 0;
			friendsListAction = 557;
			aString1121 = "Enter amount to withdraw";
		}
		if (l == 714) {
			stream.createFrame(185);
			stream.writeWord(714);
		}
		if (l == 715) {
			stream.createFrame(185);
			stream.writeWord(715);
		}
		if (l == 850) {
			stream.createFrame(185);
			stream.writeWord(1507);
		}
		if (l == 291) {
			stream.createFrame(140);
			stream.method432(j);
			stream.writeWord(k);
			stream.method432(i1);
		}

		if (l == 300) {
			stream.createFrame(141);
			stream.method432(j);
			stream.writeWord(k);
			stream.method432(i1);
			stream.writeDWord(modifiableXValue);
		}
		if (l == 474) {
			counterOn = !counterOn;
		}
		if (l == 475) {
			xpCounter = 0;
			stream.createFrame(148);
		}
		if (l == 476) {
			openInterfaceID = 32800;
		}
		if (l == 696) {
			setNorth();
		}
		if (l == 1506) { // Select quick prayers
			stream.createFrame(185);
			stream.writeWord(5001);
		}
		if (l == 1500) { // Toggle quick prayers
			prayClicked = !prayClicked;
			stream.createFrame(185);
			stream.writeWord(5000);
		}
		if (l == 104) {
			RSInterface class9_1 = RSInterface.interfaceCache[k];
			spellID = class9_1.id;
		}
		if (l == 582) {
			Npc npc = npcArray[i1];
			if (npc != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(57);
				stream.method432(anInt1285);
				stream.method432(i1);
				stream.method431(anInt1283);
				stream.method432(anInt1284);
			}
		}
		if (l == 234) {
			boolean flag1 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag1)
				flag1 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(236);
			stream.method431(k + baseY);
			stream.writeWord(i1);
			stream.method431(j + baseX);
		}
		if (l == 62 && method66(i1, k, j)) {
			stream.createFrame(192);
			stream.writeWord(anInt1284);
			stream.method431(i1 >> 14 & 0x7fff);
			stream.method433(k + baseY);
			stream.method431(anInt1283);
			stream.method433(j + baseX);
			stream.writeWord(anInt1285);
		}
		if (l == 511) {
			boolean flag2 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag2)
				flag2 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(25);
			stream.method431(anInt1284);
			stream.method432(anInt1285);
			stream.writeWord(i1);
			stream.method432(k + baseY);
			stream.method433(anInt1283);
			stream.writeWord(j + baseX);
		}
		if (l == 74) {
			stream.createFrame(122);
			stream.method433(k);
			stream.method432(j);
			stream.method431(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 315) {
			RSInterface class9 = RSInterface.interfaceCache[k];
			boolean flag8 = true;
			if (class9.contentType > 0)
				flag8 = promptUserForInput(class9);
			if (flag8) {
		
				if (SettingHandler.handle(k)) {
					return;
				}

				switch (k) {
				// Colors
				case 37510:// White
					changeChat("FFFFFF", "white");
					break;
				case 37513:// Black
					changeChat("000000", "black");
					break;
				case 37516:// Grey
					changeChat("94968F", "grey");
					break;
				case 37519:// Red
					changeChat("ED0C0C", "red");
					break;
				case 37522:// Orange
					changeChat("FF700A", "orange");
					break;
				case 37525:// Yellow
					changeChat("FFF700", "yellow");
					break;
				case 37528:// Green
					changeChat("4AD143", "green");
					break;
				case 37531:// Blue
					changeChat("25B8F7", "blue");
					break;
				case 37534:// Purple
					changeChat("DD0AF0", "purple");
					break;
				case 37537:// Pink
					changeChat("FF21D6", "pink");
					break;
				case 37540:// Cyan
					changeChat("00FFFF", "cyan");
					break;
				case 37543:// Turquoise
					changeChat("1F9C9C", "turquoise");
					break;
				case 36004:
					frameMode(ScreenMode.FIXED);
					transparentTabArea = false;
					changeChatArea = false;
					changeTabArea = false;
					break;
				case 36007:
					frameMode(ScreenMode.RESIZABLE);
					break;
				case 36010:
					frameMode(ScreenMode.FULLSCREEN);
					break;
					
					
					
				case 19144:
					sendFrame248(15106, 3213);
					method60(15106);
					inputTaken = true;
					break;

				default:
					stream.createFrame(185);
					stream.writeWord(k);
					if (k >= 61101 && k <= 61200) {
						int selected = k - 61101;
						for (int ii = 0, slot = -1; ii < ItemDef.totalItems && slot < 100; ii++) {
							ItemDef def = ItemDef.forID(ii);

							if (def.name == null || def.certTemplateID == ii - 1 || def.certID == ii - 1 || RSInterface.interfaceCache[61254].disabledMessage.length() == 0) {
								continue;
							}

							if (def.name.toLowerCase().contains(RSInterface.interfaceCache[61254].disabledMessage.toLowerCase())) {
								slot++;
							}

							if (slot != selected) {
								continue;
							}

							int id = def.id;
							long num = Long.valueOf(RSInterface.interfaceCache[61255].disabledMessage.replaceAll(",", ""));

							if (num > Integer.MAX_VALUE) {
								num = Integer.MAX_VALUE;
							}

							stream.createFrame(149);
							stream.writeWord(id);
							stream.writeDWord((int) num);
							stream.writeWordBigEndian(variousSettings[1075]);
							break;
						}
					}
					break;

				}
			}
		}
		if (l == 561) {
			Player player = playerArray[i1];
			if (player != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1188 += i1;
				if (anInt1188 >= 90) {
					stream.createFrame(136);
					anInt1188 = 0;
				}
				stream.createFrame(128);
				stream.writeWord(i1);
			}
		}
		if (l == 20) {
			Npc class30_sub2_sub4_sub1_sub1_1 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_1 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_1.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_1.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(155);
				stream.method431(i1);
			}
		}
		if (l == 779) {
			Player class30_sub2_sub4_sub1_sub2_1 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_1 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_1.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_1.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(153);
				stream.method431(i1);
			}
		}
		if (l == 519)
			if (!menuOpen)
				worldController.method312(super.saveClickY - 4, super.saveClickX - 4);
			else
				worldController.method312(k - 4, j - 4);
		if (l == 1062) {
			anInt924 += baseX;
			if (anInt924 >= 113) {
				stream.createFrame(183);
				stream.writeDWordBigEndian(0xe63271);
				anInt924 = 0;
			}
			method66(i1, k, j);
			stream.createFrame(228);
			stream.method432(i1 >> 14 & 0x7fff);
			stream.method432(k + baseY);
			stream.writeWord(j + baseX);
		}
		if (l == 679 && !aBoolean1149) {
			stream.createFrame(40);
			stream.writeWord(k);
			aBoolean1149 = true;
		}
		if (l == 431) {
			stream.createFrame(129);
			stream.method432(j);
			stream.writeWord(k);
			stream.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 337 || l == 42 || l == 792 || l == 322) {
			String s = menuActionName[i];
			int k1 = s.indexOf("@whi@");
			if (k1 != -1) {
				long l3 = TextClass.longForName(s.substring(k1 + 5).trim());
				if (l == 337)
					addFriend(l3);
				if (l == 42)
					addIgnore(l3);
				if (l == 792)
					delFriend(l3);
				if (l == 322)
					delIgnore(l3);
			}
		}
		if (l == 53) {
			stream.createFrame(135);
			stream.method431(j);
			stream.method432(k);
			stream.method431(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 539) {
			stream.createFrame(16);
			stream.method432(i1);
			stream.method433(j);
			stream.method433(k);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 484 || l == 6) {
			String s1 = menuActionName[i];
			int l1 = s1.indexOf("@whi@");
			if (l1 != -1) {
				s1 = s1.substring(l1 + 5).trim();
				String s7 = TextClass.fixName(TextClass.nameForLong(TextClass.longForName(s1)));
				boolean flag9 = false;
				for (int j3 = 0; j3 < playerCount; j3++) {
					Player class30_sub2_sub4_sub1_sub2_7 = playerArray[playerIndices[j3]];
					if (class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.name == null || !class30_sub2_sub4_sub1_sub2_7.name.equalsIgnoreCase(s7))
						continue;
					doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_7.smallX[0]);
					if (l == 484) {
						stream.createFrame(139);
						stream.method431(playerIndices[j3]);
					}
					if (l == 6) {
						anInt1188 += i1;
						if (anInt1188 >= 90) {
							stream.createFrame(136);
							anInt1188 = 0;
						}
						stream.createFrame(128);
						stream.writeWord(playerIndices[j3]);
					}
					flag9 = true;
					break;
				}

				if (!flag9)
					pushMessage("Unable to find " + s7, 0, "");
			}
		}
		if (l == 870) {
			stream.createFrame(53);
			stream.writeWord(j);
			stream.method432(anInt1283);
			stream.method433(i1);
			stream.writeWord(anInt1284);
			stream.method431(anInt1285);
			stream.writeWord(k);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 847) {
			stream.createFrame(87);
			stream.method432(i1);
			stream.writeWord(k);
			stream.method432(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 626) {
			RSInterface class9_1 = RSInterface.interfaceCache[k];
			spellSelected = 1;
			spellID = class9_1.id;
			anInt1137 = k;
			spellUsableOn = class9_1.spellUsableOn;
			itemSelected = 0;
			String s4 = class9_1.selectedActionName;
			if (s4.indexOf(" ") != -1)
				s4 = s4.substring(0, s4.indexOf(" "));
			String s8 = class9_1.selectedActionName;
			if (s8.indexOf(" ") != -1)
				s8 = s8.substring(s8.indexOf(" ") + 1);
			spellTooltip = s4 + " " + class9_1.spellName + " " + s8;
			// class9_1.sprite1.drawSprite(class9_1.anInt263, class9_1.anInt265,
			// 0xffffff);
			// class9_1.sprite1.drawSprite(200,200);
			// System.out.println("Sprite: " + class9_1.sprite1.toString());
			if (spellUsableOn == 16) {
				tabID = 3;
				tabAreaAltered = true;
			}
			return;
		}
		if (l == 78) {
			stream.createFrame(117);
			stream.method433(k);
			stream.method433(i1);
			stream.method431(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 27) {
			Player class30_sub2_sub4_sub1_sub2_2 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_2 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_2.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_2.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt986 += i1;
				if (anInt986 >= 54) {
					stream.createFrame(189);
					stream.writeWordBigEndian(234);
					anInt986 = 0;
				}
				stream.createFrame(73);
				stream.method431(i1);
			}
		}
		if (l == 213) {
			boolean flag3 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag3)
				flag3 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(79);
			stream.method431(k + baseY);
			stream.writeWord(i1);
			stream.method432(j + baseX);
		}
		if (l == 632) {
			stream.createFrame(145);
			stream.method432(k);
			stream.method432(j);
			stream.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 1050) {
			stream.createFrame(185);
			stream.writeWord(152);
		}
		if (l == 1004) {
			if (tabInterfaceIDs[14] != -1) {
				if (frameMode != ScreenMode.FIXED && changeTabArea) {
					if (tabID == 14) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
				}
				tabID = 14;
				tabAreaAltered = true;
			}
		}
		if (l == 1003) {
			clanChatMode = 2;
			inputTaken = true;
		}
		if (l == 1002) {
			clanChatMode = 1;
			inputTaken = true;
		}
		if (l == 1001) {
			clanChatMode = 0;
			inputTaken = true;
		}
		if (l == 1000) {
			cButtonCPos = 4;
			chatTypeView = 11;
			inputTaken = true;
		}
		if (l == 999) {
			cButtonCPos = 0;
			chatTypeView = 0;
			inputTaken = true;
		}
		if (l == 998) {
			cButtonCPos = 1;
			chatTypeView = 5;
			inputTaken = true;
		}
		if (l == 997) {
			publicChatMode = 3;
			inputTaken = true;
		}
		if (l == 996) {
			publicChatMode = 2;
			inputTaken = true;
		}
		if (l == 995) {
			publicChatMode = 1;
			inputTaken = true;
		}
		if (l == 994) {
			publicChatMode = 0;
			inputTaken = true;
		}
		if (l == 993) {
			cButtonCPos = 2;
			chatTypeView = 1;
			inputTaken = true;
		}
		if (l == 992) {
			privateChatMode = 2;
			inputTaken = true;
		}
		if (l == 991) {
			privateChatMode = 1;
			inputTaken = true;
		}
		if (l == 990) {
			privateChatMode = 0;
			inputTaken = true;
		}
		if (l == 989) {
			cButtonCPos = 3;
			chatTypeView = 2;
			inputTaken = true;
		}
		if (l == 987) {
			tradeMode = 2;
			inputTaken = true;
		}
		if (l == 986) {
			tradeMode = 1;
			inputTaken = true;
		}
		if (l == 985) {
			tradeMode = 0;
			inputTaken = true;
		}
		if (l == 984) {
			cButtonCPos = 5;
			chatTypeView = 3;
			inputTaken = true;
		}
		if (l == 980) {
			cButtonCPos = 6;
			chatTypeView = 4;
			inputTaken = true;
		}
		if (l == 647) {
			stream.createFrame(213);
			stream.writeWord(k);
			stream.writeWord(j);
			switch (k) {
			case 43704:
				if (j == 0) {
					inputTaken = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 8;
					aString1121 = "Enter your clan chat title";
				}
				break;
			}
		}
		if (l == 493) {
			stream.createFrame(75);
			stream.method433(k);
			stream.method431(j);
			stream.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 652) {
			boolean flag4 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag4)
				flag4 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(156);
			stream.method432(j + baseX);
			stream.method431(k + baseY);
			stream.method433(i1);
		}
		if (l == 647) {
			stream.createFrame(213);
			stream.writeWord(k);
			stream.writeWord(j);
			switch (k) {
			case 43704:
				if (j == 0) {
					inputTaken = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 8;
					aString1121 = "Enter your clan chat title";
				}
				break;
			}
		}
		if (l == 94) {
			boolean flag5 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag5)
				flag5 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(181);
			stream.method431(k + baseY);
			stream.writeWord(i1);
			stream.method431(j + baseX);
			stream.method432(anInt1137);
		}
		if (l == 646) {
			stream.createFrame(185);
			stream.writeWord(k);
			RSInterface class9_2 = RSInterface.interfaceCache[k];
			if (class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
				int i2 = class9_2.valueIndexArray[0][1];
				if (variousSettings[i2] != class9_2.anIntArray212[0]) {
					variousSettings[i2] = class9_2.anIntArray212[0];
					updateConfigValues(i2);
				}
			}
			switch (k) {
			// clan chat
			case 18129:
				if (RSInterface.interfaceCache[18135].disabledMessage.toLowerCase().contains("join")) {
					inputTaken = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 6;
					aString1121 = "Enter the name of the chat you wish to join";
				} else {
					sendString(0, "");
				}
				break;
			case 18132:
				openInterfaceID = 43700;
				break;
			case 43926:
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 9;
				aString1121 = "Enter a name to add";
				break;
			case 43927:
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 10;
				aString1121 = "Enter a name to add";
				break;
			}
		}
		if (l == 225) {
			Npc class30_sub2_sub4_sub1_sub1_2 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_2 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_2.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_2.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1226 += i1;
				if (anInt1226 >= 85) {
					stream.createFrame(230);
					stream.writeWordBigEndian(239);
					anInt1226 = 0;
				}
				stream.createFrame(17);
				stream.method433(i1);
			}
		}
		if (l == 965) {
			Npc class30_sub2_sub4_sub1_sub1_3 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_3 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_3.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_3.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1134++;
				if (anInt1134 >= 96) {
					stream.createFrame(152);
					stream.writeWordBigEndian(88);
					anInt1134 = 0;
				}
				stream.createFrame(21);
				stream.writeWord(i1);
			}
		}
		if (l == 413) {
			Npc class30_sub2_sub4_sub1_sub1_4 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_4 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_4.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_4.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(131);
				stream.method433(i1);
				stream.method432(anInt1137);
			}
		}
		if (l == 200)
			clearTopInterfaces();
		if (l == 1025) {
			Npc class30_sub2_sub4_sub1_sub1_5 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_5 != null) {
				EntityDef entityDef = class30_sub2_sub4_sub1_sub1_5.desc;
				if (entityDef.childrenIDs != null)
					entityDef = entityDef.method161();
				if (entityDef != null) {
					String s9;
					if (entityDef.description != null)
						s9 = new String(entityDef.description);
					else
						s9 = "It's a " + entityDef.name + ".";
					pushMessage(s9, 0, "");
				}
			}
		}
		if (l == 900) {
			method66(i1, k, j);
			stream.createFrame(252);
			stream.method433(i1 >> 14 & 0x7fff);
			stream.method431(k + baseY);
			stream.method432(j + baseX);
		}
		if (l == 412) {
			Npc class30_sub2_sub4_sub1_sub1_6 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_6 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_6.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_6.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(72);
				stream.method432(i1);
			}
		}
		if (l == 365) {
			Player class30_sub2_sub4_sub1_sub2_3 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_3 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_3.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_3.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(249);
				stream.method432(i1);
				stream.method431(anInt1137);
			}
		}
		if (l == 729) {
			Player class30_sub2_sub4_sub1_sub2_4 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_4 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_4.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_4.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(39);
				stream.method431(i1);
			}
		}
		if (l == 577) {
			Player class30_sub2_sub4_sub1_sub2_5 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_5 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_5.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_5.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(139);
				stream.method431(i1);
			}
		}
		if (l == 956 && method66(i1, k, j)) {
			stream.createFrame(35);
			stream.method431(j + baseX);
			stream.method432(anInt1137);
			stream.method432(k + baseY);
			stream.method431(i1 >> 14 & 0x7fff);
		}
		if (l == 567) {
			boolean flag6 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag6)
				flag6 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(23);
			stream.method431(k + baseY);
			stream.method431(i1);
			stream.method431(j + baseX);
		}
		if (l == 867) {
			if ((i1 & 3) == 0)
				anInt1175++;
			if (anInt1175 >= 59) {
				stream.createFrame(200);
				stream.writeWord(25501);
				anInt1175 = 0;
			}
			stream.createFrame(43);
			stream.method431(k);
			stream.method432(i1);
			stream.method432(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 543) {
			stream.createFrame(237);
			stream.writeWord(j);
			stream.method432(i1);
			stream.writeWord(k);
			stream.method432(anInt1137);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 606) {
			stream.createFrame(185);
			stream.writeWord(606);
		}
		if (l == 491) {
			Player class30_sub2_sub4_sub1_sub2_6 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_6 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_6.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_6.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				stream.createFrame(14);
				stream.method432(anInt1284);
				stream.writeWord(i1);
				stream.writeWord(anInt1285);
				stream.method431(anInt1283);
			}
		}
		if (l == 639) {
			String s3 = menuActionName[i];
			int k2 = s3.indexOf("@whi@");
			if (k2 != -1) {
				long l4 = TextClass.longForName(s3.substring(k2 + 5).trim());
				int k3 = -1;
				for (int i4 = 0; i4 < friendsCount; i4++) {
					if (friendsListAsLongs[i4] != l4)
						continue;
					k3 = i4;
					break;
				}

				if (k3 != -1 && friendsNodeIDs[k3] > 0) {
					inputTaken = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 3;
					aLong953 = friendsListAsLongs[k3];
					aString1121 = "Enter message to send to " + friendsList[k3];
				}
			}
		}
		if (l == 454) {
			stream.createFrame(41);
			stream.writeWord(i1);
			stream.method432(j);
			stream.method432(k);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 478) {
			Npc class30_sub2_sub4_sub1_sub1_7 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_7 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_7.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				if ((i1 & 3) == 0)
					anInt1155++;
				if (anInt1155 >= 53) {
					stream.createFrame(85);
					stream.writeWordBigEndian(66);
					anInt1155 = 0;
				}
				stream.createFrame(18);
				stream.method431(i1);
			}
		}
		if (l == 113) {
			method66(i1, k, j);
			stream.createFrame(70);
			stream.method431(j + baseX);
			stream.writeWord(k + baseY);
			stream.method433(i1 >> 14 & 0x7fff);
		}
		if (l == 872) {
			method66(i1, k, j);
			stream.createFrame(234);
			stream.method433(j + baseX);
			stream.method432(i1 >> 14 & 0x7fff);
			stream.method433(k + baseY);
		}
		if (l == 502) {
			method66(i1, k, j);
			stream.createFrame(132);
			stream.method433(j + baseX);
			stream.writeWord(i1 >> 14 & 0x7fff);
			stream.method432(k + baseY);
		}
		if (l == 1125) {
			ItemDef itemDef = ItemDef.forID(i1);
			RSInterface class9_4 = RSInterface.interfaceCache[k];
			String s5;
			if (class9_4 == null) {
				return;
			}
			if (class9_4 != null && class9_4.invStackSizes != null && class9_4.invStackSizes[j] >= 0x186a0) {
				DecimalFormatSymbols separator = new DecimalFormatSymbols();
				separator.setGroupingSeparator(',');
				DecimalFormat formatter = new DecimalFormat("#,###,###,###", separator);
				s5 = formatter.format(class9_4.invStackSizes[j]) + " x " + itemDef.name;
			} else if (itemDef.description != null)
				s5 = new String(itemDef.description);
			else
				s5 = "It's a " + itemDef.name + ".";
			pushMessage(s5, 0, "");
		}
		if (l == 169) {
			stream.createFrame(185);
			stream.writeWord(k);
			RSInterface class9_3 = RSInterface.interfaceCache[k];
			if (class9_3.valueIndexArray != null && class9_3.valueIndexArray[0][0] == 5) {
				int l2 = class9_3.valueIndexArray[0][1];
				variousSettings[l2] = 1 - variousSettings[l2];
				updateConfigValues(l2);
			}
		}
		if (l == 447) {
			itemSelected = 1;
			anInt1283 = j;
			anInt1284 = k;
			anInt1285 = i1;
			selectedItemName = ItemDef.forID(i1).name;
			spellSelected = 0;
			return;
		}
		if (l == 1226) {
			int j1 = i1 >> 14 & 0x7fff;
			ObjectDef class46 = ObjectDef.forID(j1);
			String s10;
			if (class46.description != null)
				s10 = new String(class46.description);
			else
				s10 = "It's a " + class46.name + ".";
			pushMessage(s10, 0, "");
		}
		if (l == 244) {
			boolean flag7 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag7)
				flag7 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			stream.createFrame(253);
			stream.method431(j + baseX);
			stream.method433(k + baseY);
			stream.method432(i1);
		}
		if (l == 1448) {
			ItemDef itemDef_1 = ItemDef.forID(i1);
			String s6;
			if (itemDef_1.description != null) {
				s6 = new String(itemDef_1.description);
			} else {
				s6 = "It's a " + itemDef_1.name + ".";
			}
			pushMessage(s6, 0, "");
		}
		itemSelected = 0;
		spellSelected = 0;

	}

	@SuppressWarnings("unused")
	private void method70() {
		anInt1251 = 0;
		int j = (myPlayer.x >> 7) + baseX;
		int k = (myPlayer.y >> 7) + baseY;
		if (j >= 3053 && j <= 3156 && k >= 3056 && k <= 3136)
			anInt1251 = 1;
		if (j >= 3072 && j <= 3118 && k >= 9492 && k <= 9535)
			anInt1251 = 1;
		if (anInt1251 == 1 && j >= 3139 && j <= 3199 && k >= 3008 && k <= 3062)
			anInt1251 = 0;
	}

	public void run() {
		if (drawFlames) {
			drawFlames();
		} else {
			super.run();
		}
	}

	private void build3dScreenMenu() {
		if (itemSelected == 0 && spellSelected == 0) {
			menuActionName[menuActionRow] = "Walk here";
			menuActionID[menuActionRow] = 519;
			menuActionCmd2[menuActionRow] = super.mouseX;
			menuActionCmd3[menuActionRow] = super.mouseY;
			menuActionRow++;
		}
		int j = -1;
		for (int k = 0; k < Model.anInt1687; k++) {
			int l = Model.anIntArray1688[k];
			int i1 = l & 0x7f;
			int j1 = l >> 7 & 0x7f;
			int k1 = l >> 29 & 3;
			int l1 = l >> 14 & 0x7fff;
			if (l == j)
				continue;
			j = l;
			if (k1 == 2) {
			}
			if (k1 == 2 && worldController.method304(plane, i1, j1, l) >= 0) {
				ObjectDef class46 = ObjectDef.forID(l1);
				if (class46.childrenIDs != null)
					class46 = class46.method580();
				if (class46 == null) {
					continue;
				}
				if (itemSelected == 1) {
					menuActionName[menuActionRow] = "Use " + selectedItemName + " with @cya@" + class46.name;
					menuActionID[menuActionRow] = 62;
					menuActionCmd1[menuActionRow] = l;
					menuActionCmd2[menuActionRow] = i1;
					menuActionCmd3[menuActionRow] = j1;
					menuActionRow++;
				} else if (spellSelected == 1) {
					if ((spellUsableOn & 4) == 4) {
						menuActionName[menuActionRow] = spellTooltip + " @cya@" + class46.name;
						menuActionID[menuActionRow] = 956;
						menuActionCmd1[menuActionRow] = l;
						menuActionCmd2[menuActionRow] = i1;
						menuActionCmd3[menuActionRow] = j1;
						menuActionRow++;
					}
				} else {
					if (class46.actions != null) {
						for (int i2 = 4; i2 >= 0; i2--)
							if (class46.actions[i2] != null) {
								menuActionName[menuActionRow] = class46.actions[i2] + " @cya@" + class46.name;
								if (i2 == 0)
									menuActionID[menuActionRow] = 502;
								if (i2 == 1)
									menuActionID[menuActionRow] = 900;
								if (i2 == 2)
									menuActionID[menuActionRow] = 113;
								if (i2 == 3)
									menuActionID[menuActionRow] = 872;
								if (i2 == 4)
									menuActionID[menuActionRow] = 1062;
								menuActionCmd1[menuActionRow] = l;
								menuActionCmd2[menuActionRow] = i1;
								menuActionCmd3[menuActionRow] = j1;
								menuActionRow++;
							}

					}
					if (ClientConstants.DEBUG_MODE) {
						menuActionName[menuActionRow] = "Examine @cya@" + class46.name + " @gre@(@whi@" + l1 + "@gre@) (@whi@" + (i1 + baseX) + "," + (j1 + baseY) + "@gre@)";
					} else {
						menuActionName[menuActionRow] = "Examine @cya@" + class46.name;
					}
					menuActionID[menuActionRow] = 1226;
					menuActionCmd1[menuActionRow] = class46.type << 14;
					menuActionCmd2[menuActionRow] = i1;
					menuActionCmd3[menuActionRow] = j1;
					menuActionRow++;
				}
			}
			if (k1 == 1) {
				Npc npc = npcArray[l1];
				try {
					if (npc.desc.aByte68 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
						for (int j2 = 0; j2 < npcCount; j2++) {
							Npc npc2 = npcArray[npcIndices[j2]];
							if (npc2 != null && npc2 != npc && npc2.desc.aByte68 == 1 && npc2.x == npc.x && npc2.y == npc.y)
								buildAtNPCMenu(npc2.desc, npcIndices[j2], j1, i1);
						}
						for (int l2 = 0; l2 < playerCount; l2++) {
							Player player = playerArray[playerIndices[l2]];
							if (player != null && player.x == npc.x && player.y == npc.y)
								buildAtPlayerMenu(i1, playerIndices[l2], player, j1);
						}
					}
					buildAtNPCMenu(npc.desc, l1, j1, i1);
				} catch (Exception e) {
				}
			}
			if (k1 == 0) {
				Player player = playerArray[l1];
				if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
					for (int k2 = 0; k2 < npcCount; k2++) {
						Npc class30_sub2_sub4_sub1_sub1_2 = npcArray[npcIndices[k2]];
						if (class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.desc.aByte68 == 1 && class30_sub2_sub4_sub1_sub1_2.x == player.x && class30_sub2_sub4_sub1_sub1_2.y == player.y)
							buildAtNPCMenu(class30_sub2_sub4_sub1_sub1_2.desc, npcIndices[k2], j1, i1);
					}

					for (int i3 = 0; i3 < playerCount; i3++) {
						Player class30_sub2_sub4_sub1_sub2_2 = playerArray[playerIndices[i3]];
						if (class30_sub2_sub4_sub1_sub2_2 != null && class30_sub2_sub4_sub1_sub2_2 != player && class30_sub2_sub4_sub1_sub2_2.x == player.x && class30_sub2_sub4_sub1_sub2_2.y == player.y)
							buildAtPlayerMenu(i1, playerIndices[i3], class30_sub2_sub4_sub1_sub2_2, j1);
					}

				}
				buildAtPlayerMenu(i1, l1, player, j1);
			}
			if (k1 == 3) {
				NodeList class19 = groundArray[plane][i1][j1];
				if (class19 != null) {
					for (Item item = (Item) class19.getFirst(); item != null; item = (Item) class19.getNext()) {
						ItemDef itemDef = ItemDef.forID(item.ID);
						if (itemSelected == 1) {
							menuActionName[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDef.name;
							menuActionID[menuActionRow] = 511;
							menuActionCmd1[menuActionRow] = item.ID;
							menuActionCmd2[menuActionRow] = i1;
							menuActionCmd3[menuActionRow] = j1;
							menuActionRow++;
						} else if (spellSelected == 1) {
							if ((spellUsableOn & 1) == 1) {
								menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDef.name;
								menuActionID[menuActionRow] = 94;
								menuActionCmd1[menuActionRow] = item.ID;
								menuActionCmd2[menuActionRow] = i1;
								menuActionCmd3[menuActionRow] = j1;
								menuActionRow++;
							}
						} else {
							for (int j3 = 4; j3 >= 0; j3--)
								if (itemDef.groundActions != null && itemDef.groundActions[j3] != null) {
									menuActionName[menuActionRow] = itemDef.groundActions[j3] + " @lre@" + itemDef.name;
									if (j3 == 0)
										menuActionID[menuActionRow] = 652;
									if (j3 == 1)
										menuActionID[menuActionRow] = 567;
									if (j3 == 2)
										menuActionID[menuActionRow] = 234;
									if (j3 == 3)
										menuActionID[menuActionRow] = 244;
									if (j3 == 4)
										menuActionID[menuActionRow] = 213;
									menuActionCmd1[menuActionRow] = item.ID;
									menuActionCmd2[menuActionRow] = i1;
									menuActionCmd3[menuActionRow] = j1;
									menuActionRow++;
								} else if (j3 == 2) {
									menuActionName[menuActionRow] = "Take @lre@" + itemDef.name;
									menuActionID[menuActionRow] = 234;
									menuActionCmd1[menuActionRow] = item.ID;
									menuActionCmd2[menuActionRow] = i1;
									menuActionCmd3[menuActionRow] = j1;
									menuActionRow++;
								}
							menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name;
							menuActionID[menuActionRow] = 1448;
							menuActionCmd1[menuActionRow] = item.ID;
							menuActionCmd2[menuActionRow] = i1;
							menuActionCmd3[menuActionRow] = j1;
							menuActionRow++;
						}
					}

				}
			}
		}
	}

	public void cleanUpForQuit() {
		Signlink.reporterror = false;
		try {
			if (socketStream != null) {
				socketStream.close();
			}
		} catch (Exception _ex) {
		}
		socketStream = null;
		stopMidi();
		if (mouseDetection != null)
			mouseDetection.running = false;
		mouseDetection = null;
		onDemandFetcher.disable();
		onDemandFetcher = null;
		aStream_834 = null;
		stream = null;
		aStream_847 = null;
		inStream = null;
		anIntArray1234 = null;
		aByteArrayArray1183 = null;
		aByteArrayArray1247 = null;
		anIntArray1235 = null;
		anIntArray1236 = null;
		intGroundArray = null;
		byteGroundArray = null;
		worldController = null;
		aClass11Array1230 = null;
		anIntArrayArray901 = null;
		anIntArrayArray825 = null;
		bigX = null;
		bigY = null;
		aByteArray912 = null;
		aRSImageProducer_1163 = null;
		leftFrame = null;
		topFrame = null;
		aRSImageProducer_1164 = null;
		aRSImageProducer_1165 = null;
		aRSImageProducer_1166 = null;
		aRSImageProducer_1125 = null;
		/* Null pointers for custom sprites */
		cacheSprite = null;
		mapBack = null;
		sideIcons = null;
		compass = null;
		hitMarks = null;
		skillIcons = null;
		newHitMarks = null;
		channelButtons = null;
		fixedGameComponents = null;
		skillIcons = null;
		gameComponents = null;
		orbComponents = null;
		orbComponents2 = null;
		orbComponents3 = null;
		redStones = null;
		hpBars = null;
		headIcons = null;
		skullIcons = null;
		headIconsHint = null;
		crosses = null;
		mapDotItem = null;
		mapDotNPC = null;
		mapDotPlayer = null;
		mapDotFriend = null;
		mapDotTeam = null;
		mapScenes = null;
		mapFunctions = null;
		anIntArrayArray929 = null;
		playerArray = null;
		playerIndices = null;
		anIntArray894 = null;
		aStreamArray895s = null;
		anIntArray840 = null;
		npcArray = null;
		npcIndices = null;
		groundArray = null;
		aClass19_1179 = null;
		aClass19_1013 = null;
		aClass19_1056 = null;
		menuActionCmd2 = null;
		menuActionCmd3 = null;
		menuActionID = null;
		menuActionCmd1 = null;
		menuActionName = null;
		variousSettings = null;
		anIntArray1072 = null;
		anIntArray1073 = null;
		aClass30_Sub2_Sub1_Sub1Array1140 = null;
		minimapImage = null;
		friendsList = null;
		friendsListAsLongs = null;
		friendsNodeIDs = null;
		aRSImageProducer_1110 = null;
		aRSImageProducer_1111 = null;
		aRSImageProducer_1107 = null;
		aRSImageProducer_1108 = null;
		aRSImageProducer_1109 = null;
		aRSImageProducer_1112 = null;
		aRSImageProducer_1113 = null;
		aRSImageProducer_1114 = null;
		aRSImageProducer_1115 = null;
		multiOverlay = null;
		nullLoader();
		ObjectDef.nullLoader();
		EntityDef.nullLoader();
		ItemDef.nullLoader();
		Floor.cache = null;
		// OverlayFloor.overlayFloor = null;
		IdentityKit.cache = null;
		RSInterface.interfaceCache = null;
		Animation.anims = null;
		SpotAnim.cache = null;
		SpotAnim.aMRUNodes_415 = null;
		Varp.cache = null;
		super.fullGameScreen = null;
		Player.mruNodes = null;
		Rasterizer.nullLoader();
		WorldController.nullLoader();
		Model.nullLoader();
		SequenceFrame.nullLoader();
		// Texture.reset();
		System.gc();
	}

	Component getGameComponent() {
		if (Signlink.mainapp != null)
			return Signlink.mainapp;
		if (super.gameFrame != null)
			return super.gameFrame;
		else
			return this;
	}

	private void doTextField(RSInterface rsint) {

		int rsid = rsint.parentID;

		if (rsid == 6142) {
			for (int slot = 0; slot < 100; slot++) {
				RSInterface.interfaceCache[61266 + slot].disabledSprite = new Sprite(32, 32);
				RSInterface.interfaceCache[61101 + slot].disabledMessage = "";
			}

			int found = 0;

			for (int i = 0, slot = 0; i < ItemDef.totalItems && slot < 100; i++) {
				if (ItemDef.forID(i).name == null || ItemDef.forID(i).certTemplateID == i - 1 || ItemDef.forID(i).certID == i - 1) {
					continue;
				}

				if (ItemDef.forID(i).name.toLowerCase().contains(RSInterface.interfaceCache[RSInterface.currentInputField.id].disabledMessage.toLowerCase())) {
					RSInterface.interfaceCache[61266 + slot].enabledSprite = ItemDef.getSprite(i, 1, 0);
					RSInterface.interfaceCache[61266 + slot].disabledSprite = ItemDef.getSprite(i, 1, 0);
					RSInterface.interfaceCache[61101 + slot++].disabledMessage = ItemDef.forID(i).name;
					found++;
				}
			}

			RSInterface.interfaceCache[61100].scrollMax = 40 * found + 12;
		}
	}

	private void method73() {
		do {
			int j = readChar(-796);
			if (j == -1)
				break;
			if (j == 96)
				break;
			if (RSInterface.currentInputField != null) {
				boolean update = false;

				RSInterface rsTextField = RSInterface.interfaceCache[RSInterface.currentInputField.id];

				String message = TextClass.capitalize(rsTextField.disabledMessage);

				if (j == 8 && message.length() > 0) {
					message = message.substring(0, message.length() - 1);
					if (message.length() > 0 && RSInterface.currentInputField.onlyNumbers && !RSInterface.currentInputField.displayAsterisks) {
						long num = Long.valueOf(message.replaceAll(",", ""));

						if (num > Integer.MAX_VALUE) {
							num = Integer.MAX_VALUE;
							rsTextField.disabledMessage = num + "";
						}

						message = NumberFormat.getInstance(Locale.US).format(num);
					}
					update = true;
				}

				if ((RSInterface.currentInputField.onlyNumbers ? (j >= 48 && j <= 57) : (j >= 32 && j <= 122)) && message.length() < RSInterface.currentInputField.characterLimit) {
					message += (char) j;
					if (RSInterface.currentInputField.onlyNumbers && !RSInterface.currentInputField.displayAsterisks) {
						long num = Long.valueOf(message.replaceAll(",", ""));

						if (num > Integer.MAX_VALUE) {
							num = Integer.MAX_VALUE;
							rsTextField.disabledMessage = num + "";
						}

						message = NumberFormat.getInstance(Locale.US).format(num);
					}
					update = true;
				}

				rsTextField.disabledMessage = message;

				if ((j == 13 || j == 10) && rsTextField.disabledMessage.length() > 0) {
					if (RSInterface.currentInputField.onlyNumbers) {
						long amount = 0;

						try {
							amount = Long.parseLong(message.replaceAll(",", ""));

							// overflow concious code
							if (amount < -Integer.MAX_VALUE) {
								amount = -Integer.MAX_VALUE;
							} else if (amount > Integer.MAX_VALUE) {
								amount = Integer.MAX_VALUE;
							}
						} catch (Exception ignored) {
						}

						if (amount > 0) {
							stream.createFrame(208);
							stream.writeDWord((int) amount);
						}
					} else {
						stream.createFrame(150);
						stream.writeWordBigEndian(RSInterface.currentInputField.disabledMessage.length() + 3);
						stream.writeWord(RSInterface.currentInputField.id);
						stream.writeString(RSInterface.currentInputField.disabledMessage);
					}
					RSInterface.currentInputField.disabledMessage = "";
					RSInterface.currentInputField = null;
				}

				if (update) {
					doTextField(rsTextField);
				}
			} else if (console.openConsole) {
				if (j == 8 && console.consoleInput.length() > 0) {
					console.consoleInput = console.consoleInput.substring(0, console.consoleInput.length() - 1);
				}
				if (j >= 32 && j <= 122 && console.consoleInput.length() < 80) {
					console.consoleInput += (char) j;
				}
				if ((j == 13 || j == 10) && console.consoleInput.length() > 0) {
					console.sendConsoleMessage(console.consoleInput, true);
					console.sendConsoleCommands(console.consoleInput);
					console.consoleInput = "";
					inputTaken = true;
				}
			} else if (openInterfaceID != -1 && openInterfaceID == reportAbuseInterfaceID) {
				if (j == 8 && reportAbuseInput.length() > 0)
					reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
				if ((j >= 97 && j <= 122 || j >= 65 && j <= 90 || j >= 48 && j <= 57 || j == 32) && reportAbuseInput.length() < 12)
					reportAbuseInput += (char) j;
			} else if (messagePromptRaised) {
				if (j >= 32 && j <= 122 && promptInput.length() < 80) {
					promptInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && promptInput.length() > 0) {
					promptInput = promptInput.substring(0, promptInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					messagePromptRaised = false;
					inputTaken = true;
					if (friendsListAction == 1) {
						long l = TextClass.longForName(promptInput);
						addFriend(l);
					}
					if (friendsListAction == 2 && friendsCount > 0) {
						long l1 = TextClass.longForName(promptInput);
						delFriend(l1);
					}
					if (friendsListAction == 557 && promptInput.length() > 0) {
						inputString = "::withdrawmp " + promptInput;
						sendPacket(103);
					}
					if (friendsListAction == 51504 && promptInput.length() > 0) {
						inputString = "::find " + promptInput;
						sendPacket(103);
					}
					if (friendsListAction == 59800 && promptInput.length() > 0) {
						inputString = "::droptablesearch " + promptInput;
						sendPacket(103);
					}
					if (friendsListAction == 3 && promptInput.length() > 0) {
						stream.createFrame(126);
						stream.writeWordBigEndian(0);
						int k = stream.currentOffset;
						stream.writeQWord(aLong953);
						TextInput.method526(promptInput, stream);
						stream.writeBytes(stream.currentOffset - k);
						promptInput = TextInput.processText(promptInput);
						// promptInput = Censor.doCensor(promptInput);
						pushMessage(promptInput, 6, TextClass.fixName(TextClass.nameForLong(aLong953)));
						if (privateChatMode == 2) {
							privateChatMode = 1;
							stream.createFrame(95);
							stream.writeWordBigEndian(publicChatMode);
							stream.writeWordBigEndian(privateChatMode);
							stream.writeWordBigEndian(tradeMode);
						}
					}
					if (friendsListAction == 4 && ignoreCount < 100) {
						long l2 = TextClass.longForName(promptInput);
						addIgnore(l2);
					}
					if (friendsListAction == 5 && ignoreCount > 0) {
						long l3 = TextClass.longForName(promptInput);
						delIgnore(l3);
					}
					if (friendsListAction == 6) {
						sendStringAsLong(promptInput);
					} else if (friendsListAction == 8) {
						sendString(1, promptInput);
					} else if (friendsListAction == 9) {
						sendString(2, promptInput);
					} else if (friendsListAction == 10) {
						sendString(3, promptInput);
					}
				}
			} else if (inputDialogState == 1) {
				if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m") && !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						int length = amountOrNameInput.length();
						char lastChar = amountOrNameInput.charAt(length - 1);

						if (lastChar == 'k') {
							amountOrNameInput = amountOrNameInput.substring(0, length - 1) + "000";
						} else if (lastChar == 'm') {
							amountOrNameInput = amountOrNameInput.substring(0, length - 1) + "000000";
						} else if (lastChar == 'b') {
							amountOrNameInput = amountOrNameInput.substring(0, length - 1) + "000000000";
						}

						long amount = 0;

						try {
							amount = Long.parseLong(amountOrNameInput);

							// overflow concious code
							if (amount < 0) {
								amount = 0;
							} else if (amount > Integer.MAX_VALUE) {
								amount = Integer.MAX_VALUE;
							}
						} catch (Exception ignored) {
						}

						if (amount > 0) {
							stream.createFrame(208);
							stream.writeDWord((int) amount);
							if (openInterfaceID == 5292) {
								modifiableXValue = (int) amount;
							}
						}
					}

					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (inputDialogState == 2) {
				if (j >= 32 && j <= 122 && amountOrNameInput.length() < 12) {
					amountOrNameInput += (char) j;
					inputTaken = true;
					if (openInterfaceID == 5292 && variousSettings[1012] == 1) {
						RSInterface bank = RSInterface.interfaceCache[5382];
						Arrays.fill(bankInvTemp, 0);
						Arrays.fill(bankStackTemp, 0);
						for (int slot = 0, bankSlot = 0; slot < bank.inv.length; slot++) {
							if (bank.inv[slot] - 1 > 0) {
								if (ItemDef.forID(bank.inv[slot] - 1).name.toLowerCase().contains(amountOrNameInput.toLowerCase())) {
									bankInvTemp[bankSlot] = bank.inv[slot];
									bankStackTemp[bankSlot++] = bank.invStackSizes[slot];
								}
							}
						}
					}
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
					if (openInterfaceID == 5292 && variousSettings[1012] == 1) {
						RSInterface bank = RSInterface.interfaceCache[5382];
						Arrays.fill(bankInvTemp, 0);
						Arrays.fill(bankStackTemp, 0);
						for (int slot = 0, bankSlot = 0; slot < bank.inv.length; slot++) {
							if (bank.inv[slot] - 1 > 0) {
								if (ItemDef.forID(bank.inv[slot] - 1).name.toLowerCase().contains(amountOrNameInput.toLowerCase())) {
									bankInvTemp[bankSlot] = bank.inv[slot];
									bankStackTemp[bankSlot++] = bank.invStackSizes[slot];
								}
							}
						}
					}
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						stream.createFrame(60);
						stream.writeQWord(TextClass.longForName(amountOrNameInput));
					}
					if (openInterfaceID == 5292 && variousSettings[1012] == 1) {
						RSInterface bank = RSInterface.interfaceCache[5382];
						Arrays.fill(bankInvTemp, 0);
						Arrays.fill(bankStackTemp, 0);
						int results = 0;
						for (int slot = 0, bankSlot = 0; slot < bank.inv.length; slot++) {
							if (bank.inv[slot] - 1 > 0) {
								if (ItemDef.forID(bank.inv[slot] - 1).name.toLowerCase().contains(amountOrNameInput.toLowerCase())) {
									bankInvTemp[bankSlot] = bank.inv[slot];
									bankStackTemp[bankSlot++] = bank.invStackSizes[slot];
									results++;
								}
							}
						}

						pushMessage("Found " + results + " result" + (results > 1 ? "s" : "") + " for '" + amountOrNameInput + "'.", 0, "");
					}
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (backDialogID == -1) {
				if (j >= 32 && j <= 122 && inputString.length() < 80) {
					inputString += (char) j;
					inputTaken = true;
				}
				if (j == 8 && inputString.length() > 0) {
					inputString = inputString.substring(0, inputString.length() - 1);
					inputTaken = true;
				}
				if (j == 9) {
					replyToPM();
				}
				if ((j == 13 || j == 10) && inputString.length() > 0) {
					if ((myPrivilege == 2 || myPrivilege == 3 || myPrivilege == 4) || server.equals("127.0.0.1")) {
						if (inputString.startsWith("//setspecto")) {
							int amt = Integer.parseInt(inputString.substring(12));
							anIntArray1045[300] = amt;
							if (variousSettings[300] != amt) {
								variousSettings[300] = amt;
								updateConfigValues(300);
								if (dialogID != -1) {
									inputTaken = true;
								}
							}
						}
						if (inputString.equals("::reint") || inputString.equals("::Reint")) {
							SpriteLoader.loadSprites();
							TextDrawingArea aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full", titleStreamLoader);
							TextDrawingArea aclass30_sub2_sub1_sub4s[] = { smallText, regularText, boldText, aTextDrawingArea_1273 };
							StreamLoader streamLoader_1 = streamLoaderForName(3, "interface", "interface", expectedCRCs[3], 35);
							StreamLoader streamLoader_2 = streamLoaderForName(4, "2d graphics", "media", expectedCRCs[4], 40);
							RSInterface.unpack(streamLoader_1, aclass30_sub2_sub1_sub4s, streamLoader_2);
							sendFrame126("0", 8135);
							
						}
						if (inputString.equals("::objs")) {
							for (int i = 0; i < ObjectDef.streamIndices.length; i++) {
								ObjectDef def = ObjectDef.forID(i);

								if (def == null) {
									continue;
								}

								System.out.println(i + " " + def.anInt781);
							}
						}
						if (inputString.equals("::debug")) {
							ClientConstants.DEBUG_MODE = !ClientConstants.DEBUG_MODE;
							loadingStage = 1;
						}
						if (inputString.equals("::textids")) {
							for (int index = 0; index < RSInterface.interfaceCache.length; index++) {
								if (RSInterface.interfaceCache[index] != null) {
									RSInterface.interfaceCache[index].disabledMessage = String.valueOf(index);
								}
							}
						}
						if (inputString.equals("::filtergray")) {
							filterGrayScale = !filterGrayScale;
						}
						if (inputString.equals("::textures")) {
							Configuration.enableHDTextures = !Configuration.enableHDTextures;
						}
						if (inputString.equals("::fps")) {
							fpsOn = !fpsOn;
						}
						if (inputString.equals("::data")) {
							clientData = !clientData;
						}
						if (inputString.equals("::hovers")) {
							Configuration.menuHovers = !Configuration.menuHovers;
						}
					}
					if (inputString.startsWith("/"))
						inputString = "::" + inputString;
					if (inputString.startsWith("::")) {
						stream.createFrame(103);
						stream.writeWordBigEndian(inputString.length() - 1);
						stream.writeString(inputString.substring(2));
					} else {
						String s = inputString.toLowerCase();
						int j2 = 0;
						if (s.startsWith("yellow:")) {
							j2 = 0;
							inputString = inputString.substring(7);
						} else if (s.startsWith("red:")) {
							j2 = 1;
							inputString = inputString.substring(4);
						} else if (s.startsWith("green:")) {
							j2 = 2;
							inputString = inputString.substring(6);
						} else if (s.startsWith("cyan:")) {
							j2 = 3;
							inputString = inputString.substring(5);
						} else if (s.startsWith("purple:")) {
							j2 = 4;
							inputString = inputString.substring(7);
						} else if (s.startsWith("white:")) {
							j2 = 5;
							inputString = inputString.substring(6);
						} else if (s.startsWith("flash1:")) {
							j2 = 6;
							inputString = inputString.substring(7);
						} else if (s.startsWith("flash2:")) {
							j2 = 7;
							inputString = inputString.substring(7);
						} else if (s.startsWith("flash3:")) {
							j2 = 8;
							inputString = inputString.substring(7);
						} else if (s.startsWith("glow1:")) {
							j2 = 9;
							inputString = inputString.substring(6);
						} else if (s.startsWith("glow2:")) {
							j2 = 10;
							inputString = inputString.substring(6);
						} else if (s.startsWith("glow3:")) {
							j2 = 11;
							inputString = inputString.substring(6);
						}
						s = inputString.toLowerCase();
						int i3 = 0;
						if (s.startsWith("wave:")) {
							i3 = 1;
							inputString = inputString.substring(5);
						} else if (s.startsWith("wave2:")) {
							i3 = 2;
							inputString = inputString.substring(6);
						} else if (s.startsWith("shake:")) {
							i3 = 3;
							inputString = inputString.substring(6);
						} else if (s.startsWith("scroll:")) {
							i3 = 4;
							inputString = inputString.substring(7);
						} else if (s.startsWith("slide:")) {
							i3 = 5;
							inputString = inputString.substring(6);
						}
						stream.createFrame(4);
						stream.writeWordBigEndian(0);
						int j3 = stream.currentOffset;
						stream.method425(i3);
						stream.method425(j2);
						aStream_834.currentOffset = 0;
						TextInput.method526(inputString, aStream_834);
						stream.method441(0, aStream_834.buffer, aStream_834.currentOffset);
						stream.writeBytes(stream.currentOffset - j3);
						inputString = TextInput.processText(inputString);
						// inputString = Censor.doCensor(inputString);
						myPlayer.textSpoken = inputString;
						myPlayer.anInt1513 = j2;
						myPlayer.anInt1531 = i3;
						myPlayer.textCycle = 150;
						if (myPrivilege >= 1)
							pushMessage(myPlayer.textSpoken, 2, "@cr" + myPrivilege + "@" + myPlayer.name, myPlayer.title, myPlayer.titleColor);
						else
							pushMessage(myPlayer.textSpoken, 2, myPlayer.name, myPlayer.title, myPlayer.titleColor);
						if (publicChatMode == 2) {
							publicChatMode = 3;
							stream.createFrame(95);
							stream.writeWordBigEndian(publicChatMode);
							stream.writeWordBigEndian(privateChatMode);
							stream.writeWordBigEndian(tradeMode);
						}
					}
					inputString = "";
					inputTaken = true;
				}
			}
		} while (true);
	}

	void sendPacket(int packet) {
		if (packet == 103) {
			stream.createFrame(103);
			stream.writeWordBigEndian(inputString.length() - 1);
			stream.writeString(inputString.substring(2));
			inputString = "";
			promptInput = "";
			// interfaceButtonAction = 0;
		}

		if (packet == 1003) {
			stream.createFrame(103);
			inputString = "::" + inputString;
			stream.writeWordBigEndian(inputString.length() - 1);
			stream.writeString(inputString.substring(2));
			inputString = "";
			promptInput = "";
			// interfaceButtonAction = 0;
		}
	}

	private void buildPublicChat(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 1)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (s != null && s.startsWith("@cr")) {
				if (s.charAt(4) != '@') {
					s = s.substring(6);
				} else {
					s = s.substring(5);
				}
			}
			if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1 && !s.equals(myPlayer.name)) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse @whi@" + s;
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore @whi@" + s;
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend @whi@" + s;
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void buildFriendChat(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 2)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (s != null && s.startsWith("@cr")) {
				if (s.charAt(4) != '@') {
					s = s.substring(6);
				} else {
					s = s.substring(5);
				}
			}
			if ((j1 == 5 || j1 == 6) && (splitPrivateChat == 0 || chatTypeView == 2) && (j1 == 6 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s)))
				l++;
			if ((j1 == 3 || j1 == 7) && (splitPrivateChat == 0 || chatTypeView == 2) && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse @whi@" + s;
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore @whi@" + s;
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend @whi@" + s;
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void buildDuelorTrade(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 3 && chatTypeView != 4)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (s != null && s.startsWith("@cr")) {
				if (s.charAt(4) != '@') {
					s = s.substring(6);
				} else {
					s = s.substring(5);
				}
			}
			if (chatTypeView == 3 && j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept trade @whi@" + s;
					menuActionID[menuActionRow] = 484;
					menuActionRow++;
				}
				l++;
			}
			if (chatTypeView == 4 && j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept challenge @whi@" + s;
					menuActionID[menuActionRow] = 6;
					menuActionRow++;
				}
				l++;
			}
			if (j1 == 12) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Go-to @blu@" + s;
					menuActionID[menuActionRow] = 915;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void buildChatAreaMenu(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			int j1 = chatTypes[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			String s = chatNames[i1];
			if (chatTypeView == 1) {
				buildPublicChat(j);
				break;
			}
			if (chatTypeView == 2) {
				buildFriendChat(j);
				break;
			}
			if (chatTypeView == 3 || chatTypeView == 4) {
				buildDuelorTrade(j);
				break;
			}
			if (chatTypeView == 5) {
				break;
			}
			if (s != null && s.startsWith("@cr")) {
				if (s.charAt(4) != '@') {
					s = s.substring(6);
				} else {
					s = s.substring(5);
				}
			}
			if (j1 == 0)
				l++;
			if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1 && !s.equals(myPlayer.name)) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse @whi@" + s;
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore @whi@" + s;
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend @whi@" + s;
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
			if ((j1 == 3 || j1 == 7) && splitPrivateChat == 0 && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse @whi@" + s;
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore @whi@" + s;
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend @whi@" + s;
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
			if (j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept trade @whi@" + s;
					menuActionID[menuActionRow] = 484;
					menuActionRow++;
				}
				l++;
			}
			if ((j1 == 5 || j1 == 6) && splitPrivateChat == 0 && privateChatMode < 2)
				l++;
			if (j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept challenge @whi@" + s;
					menuActionID[menuActionRow] = 6;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void drawFriendsListOrWelcomeScreen(RSInterface class9) {
		int j = class9.contentType;
		if (j >= 1 && j <= 100 || j >= 701 && j <= 800) {
			if (j == 1 && anInt900 == 0) {
				class9.disabledMessage = "Loading friend list";
				class9.atActionType = 0;
				return;
			}
			if (j == 1 && anInt900 == 1) {
				class9.disabledMessage = "Connecting to friendserver";
				class9.atActionType = 0;
				return;
			}
			if (j == 2 && anInt900 != 2) {
				class9.disabledMessage = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int k = friendsCount;
			if (anInt900 != 2)
				k = 0;
			if (j > 700)
				j -= 601;
			else
				j--;
			if (j >= k) {
				class9.disabledMessage = "";
				class9.atActionType = 0;
				return;
			} else {
				class9.disabledMessage = friendsList[j];
				class9.atActionType = 1;
				return;
			}
		}
		if (j >= 101 && j <= 200 || j >= 801 && j <= 900) {
			int l = friendsCount;
			if (anInt900 != 2)
				l = 0;
			if (j > 800)
				j -= 701;
			else
				j -= 101;
			if (j >= l) {
				class9.disabledMessage = "";
				class9.atActionType = 0;
				return;
			}
			if (friendsNodeIDs[j] == 0)
				class9.disabledMessage = "@red@Offline";
			else if (friendsNodeIDs[j] == nodeID)
				class9.disabledMessage = "@gre@Online"/*
														 * + (friendsNodeIDs[j] -
														 * 9)
														 */;
			else
				class9.disabledMessage = "@red@Offline"/*
														 * + (friendsNodeIDs[j] -
														 * 9)
														 */;
			class9.atActionType = 1;
			return;
		}
		if (j == 203) {
			int i1 = friendsCount;
			if (anInt900 != 2)
				i1 = 0;
			class9.scrollMax = i1 * 15 + 20;
			if (class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if (j >= 401 && j <= 500) {
			if ((j -= 401) == 0 && anInt900 == 0) {
				class9.disabledMessage = "Loading ignore list";
				class9.atActionType = 0;
				return;
			}
			if (j == 1 && anInt900 == 0) {
				class9.disabledMessage = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int j1 = ignoreCount;
			if (anInt900 == 0)
				j1 = 0;
			if (j >= j1) {
				class9.disabledMessage = "";
				class9.atActionType = 0;
				return;
			} else {
				class9.disabledMessage = TextClass.fixName(TextClass.nameForLong(ignoreListAsLongs[j]));
				class9.atActionType = 1;
				return;
			}
		}
		if (j == 901) {
			class9.disabledMessage = friendsCount + " / 200";
			return;
		}
		if (j == 902) {
			class9.disabledMessage = ignoreCount + " / 100";
			return;
		}
		if (j == 503) {
			class9.scrollMax = ignoreCount * 15 + 20;
			if (class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if (j == 327) {
			class9.modelRotation1 = 150;
			class9.modelRotation2 = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
			if (aBoolean1031) {
				for (int k1 = 0; k1 < 7; k1++) {
					int l1 = anIntArray1065[k1];
					if (l1 >= 0 && !IdentityKit.cache[l1].method537())
						return;
				}

				aBoolean1031 = false;
				Model aclass30_sub2_sub4_sub6s[] = new Model[7];
				int i2 = 0;
				for (int j2 = 0; j2 < 7; j2++) {
					int k2 = anIntArray1065[j2];
					if (k2 >= 0)
						aclass30_sub2_sub4_sub6s[i2++] = IdentityKit.cache[k2].method538();
				}

				Model model = new Model(i2, aclass30_sub2_sub4_sub6s);
				for (int l2 = 0; l2 < 5; l2++)
					if (anIntArray990[l2] != 0) {
						model.method476(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1)
							model.method476(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}

				model.method469();
				model.method470(Animation.anims[myPlayer.anInt1511].anIntArray353[0]);
				model.method479(64, 850, -30, -50, -30, true);
				class9.anInt233 = 5;
				class9.mediaID = 0;
				RSInterface.method208(aBoolean994, model);
			}
			return;
		}
		if (j == 328) {
			RSInterface rsInterface = class9;
			int verticleTilt = 150;
			int animationSpeed = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
			rsInterface.modelRotation1 = verticleTilt;
			rsInterface.modelRotation2 = animationSpeed;
			if (aBoolean1031) {
				Model characterDisplay = myPlayer.method452();
				for (int l2 = 0; l2 < 5; l2++)
					if (anIntArray990[l2] != 0) {
						characterDisplay.method476(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1)
							characterDisplay.method476(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}
				int staticFrame = myPlayer.anInt1511;
				characterDisplay.method469();
				characterDisplay.interpolateFrames(Animation.anims[staticFrame].anIntArray353[0], -1, -1, -1);
				rsInterface.anInt233 = 5;
				rsInterface.mediaID = 0;
				RSInterface.method208(aBoolean994, characterDisplay);
			}
			return;
		}
		if (j == 330) {
			if (playerIndex >= playerArray.length || playerIndex < 0) {
				return;
			}

			Player player = playerArray[playerIndex];

			if (player == null) {
				return;
			}

			RSInterface rsInterface = class9;

			int verticleTilt = 150;
			int animationSpeed = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
			rsInterface.modelRotation1 = verticleTilt;
			rsInterface.modelRotation2 = animationSpeed;

			if (aBoolean1031) {
				Model characterDisplay = player.method452();
				for (int l2 = 0; l2 < 5; l2++)
					if (anIntArray990[l2] != 0) {
						characterDisplay.method476(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1)
							characterDisplay.method476(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}
				int staticFrame = player.anInt1511;
				characterDisplay.method469();
				characterDisplay.interpolateFrames(Animation.anims[staticFrame].anIntArray353[0], -1, -1, -1);
				rsInterface.anInt233 = 5;
				rsInterface.mediaID = 0;
				RSInterface.method208(aBoolean994, characterDisplay);
			}
			return;
		}
		if (j == 329) {
			if (npcDisplay == null) {
				return;
			}
			RSInterface rsInterface = class9;
			int verticleTilt = 150;
			int animationSpeed = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
			rsInterface.modelRotation1 = verticleTilt;
			rsInterface.modelRotation2 = animationSpeed;
			if (aBoolean1031) {
				Model characterDisplay = npcDisplay.getRotatedModel();

				if (characterDisplay == null) {
					return;
				}

				for (int l2 = 0; l2 < 5; l2++)
					if (anIntArray990[l2] != 0) {
						characterDisplay.method476(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1)
							characterDisplay.method476(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}
				int staticFrame = npcDisplay.anInt1511;
				characterDisplay.method469();
				characterDisplay.method470(Animation.anims[staticFrame].anIntArray353[0]);
				// characterDisplay.method479(64, 850, -30, -50, -30, true);
				rsInterface.anInt233 = 5;
				rsInterface.mediaID = 0;
				RSInterface.method208(aBoolean994, characterDisplay);
			}
			return;
		}
		if (j == 324) {
			if (aClass30_Sub2_Sub1_Sub1_931 == null) {
				aClass30_Sub2_Sub1_Sub1_931 = class9.disabledSprite;
				aClass30_Sub2_Sub1_Sub1_932 = class9.enabledSprite;
			}
			if (aBoolean1047) {
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_932;
				return;
			} else {
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_931;
				return;
			}
		}
		if (j == 325) {
			if (aClass30_Sub2_Sub1_Sub1_931 == null) {
				aClass30_Sub2_Sub1_Sub1_931 = class9.disabledSprite;
				aClass30_Sub2_Sub1_Sub1_932 = class9.enabledSprite;
			}
			if (aBoolean1047) {
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_931;
				return;
			} else {
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_932;
				return;
			}
		}
		if (j == 600) {
			class9.disabledMessage = reportAbuseInput;
			if (loopCycle % 20 < 10) {
				class9.disabledMessage += "|";
				return;
			} else {
				class9.disabledMessage += " ";
				return;
			}
		}
		if (j == 613)
			if (myPrivilege >= 1) {
				if (canMute) {
					class9.textColor = 0xff0000;
					class9.disabledMessage = "Moderator option: Mute player for 48 hours: <ON>";
				} else {
					class9.textColor = 0xffffff;
					class9.disabledMessage = "Moderator option: Mute player for 48 hours: <OFF>";
				}
			} else {
				class9.disabledMessage = "";
			}
		if (j == 650 || j == 655)
			if (anInt1193 != 0) {
				String s;
				if (daysSinceLastLogin == 0)
					s = "earlier today";
				else if (daysSinceLastLogin == 1)
					s = "yesterday";
				else
					s = daysSinceLastLogin + " days ago";
				class9.disabledMessage = "You last logged in " + s + " from: " + Signlink.dns;
			} else {
				class9.disabledMessage = "";
			}
		if (j == 651) {
			if (unreadMessages == 0) {
				class9.disabledMessage = "0 unread messages";
				class9.textColor = 0xffff00;
			}
			if (unreadMessages == 1) {
				class9.disabledMessage = "1 unread message";
				class9.textColor = 65280;
			}
			if (unreadMessages > 1) {
				class9.disabledMessage = unreadMessages + " unread messages";
				class9.textColor = 65280;
			}
		}
		if (j == 652)
			if (daysSinceRecovChange == 201) {
				if (membersInt == 1)
					class9.disabledMessage = "@yel@This is a non-members world: @whi@Since you are a member we";
				else
					class9.disabledMessage = "";
			} else if (daysSinceRecovChange == 200) {
				class9.disabledMessage = "You have not yet set any password recovery questions.";
			} else {
				String s1;
				if (daysSinceRecovChange == 0)
					s1 = "Earlier today";
				else if (daysSinceRecovChange == 1)
					s1 = "Yesterday";
				else
					s1 = daysSinceRecovChange + " days ago";
				class9.disabledMessage = s1 + " you changed your recovery questions";
			}
		if (j == 653)
			if (daysSinceRecovChange == 201) {
				if (membersInt == 1)
					class9.disabledMessage = "@whi@recommend you use a members world instead. You may use";
				else
					class9.disabledMessage = "";
			} else if (daysSinceRecovChange == 200)
				class9.disabledMessage = "We strongly recommend you do so now to secure your account.";
			else
				class9.disabledMessage = "If you do not remember making this change then cancel it immediately";
		if (j == 654) {
			if (daysSinceRecovChange == 201)
				if (membersInt == 1) {
					class9.disabledMessage = "@whi@this world but member benefits are unavailable whilst here.";
					return;
				} else {
					class9.disabledMessage = "";
					return;
				}
			if (daysSinceRecovChange == 200) {
				class9.disabledMessage = "Do this from the 'account management' area on our front webpage";
				return;
			}
			class9.disabledMessage = "Do this from the 'account management' area on our front webpage";
		}
	}

	private void drawSplitPrivateChat() {
		if (splitPrivateChat == 0) {
			return;
		}
		TextDrawingArea textDrawingArea = regularText;
		int i = 0;
		if (anInt1104 != 0) {
			i = 1;
		}
		for (int j = 0; j < 100; j++) {
			if (chatMessages[j] != null) {
				int k = chatTypes[j];
				String s = chatNames[j];
				byte byte1 = 0;
				if (s != null && s.startsWith("@cr1@")) {
					s = s.substring(5);
					byte1 = 1;
				}
				if (s != null && s.startsWith("@cr2@")) {
					s = s.substring(5);
					byte1 = 2;
				}
				if (s != null && s.startsWith("@cr3@")) {
					s = s.substring(5);
					byte1 = 3;
				}
				if (s != null && s.startsWith("@cr4@")) {
					s = s.substring(5);
					byte1 = 4;
				}
				if (s != null && s.startsWith("@cr5@")) {
					s = s.substring(5);
					byte1 = 5;
				}
				if (s != null && s.startsWith("@cr6@")) {
					s = s.substring(5);
					byte1 = 6;
				}
				if (s != null && s.startsWith("@cr7@")) {
					s = s.substring(5);
					byte1 = 7;
				}
				if (s != null && s.startsWith("@cr8@")) {
					s = s.substring(5);
					byte1 = 8;
				}
				if (s != null && s.startsWith("@cr9@")) {
					s = s.substring(5);
					byte1 = 9;
				}
				if (s != null && s.startsWith("@cr10@")) {
					s = s.substring(6);
					byte1 = 10;
				}
				if (s != null && s.startsWith("@cr11@")) {
					s = s.substring(6);
					byte1 = 11;
				}
				if (s != null && s.startsWith("@cr12@")) {
					s = s.substring(6);
					byte1 = 12;
				}
				if (s != null && s.startsWith("@cr13@")) {
					s = s.substring(6);
					byte1 = 13;
				}
				if (s != null && s.startsWith("@cr14@")) {
					s = s.substring(6);
					byte1 = 14;
				}
				if ((k == 3 || k == 7) && (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
					int l = 329 - i * 13;
					if (frameMode != ScreenMode.FIXED) {
						l = frameHeight - 170 - i * 13 - extendChatArea;
					}
					int k1 = 4;
					textDrawingArea.method385(0, "From", l, k1);
					textDrawingArea.method385(getChatColor(), "From", l - 1, k1);
					k1 += textDrawingArea.getTextWidth("From ");
					if (byte1 >= 1) {
						modIcons[byte1 - 1].drawSprite(k1 - 3, l - 15);
						k1 += 12;
					}
					textDrawingArea.method385(0, s + ": " + chatMessages[j], l, k1);
					textDrawingArea.method385(getChatColor(), s + ": " + chatMessages[j], l - 1, k1);
					if (++i >= 5) {
						return;
					}
				}
				if (k == 5 && privateChatMode < 2) {
					int i1 = 329 - i * 13;
					if (frameMode != ScreenMode.FIXED) {
						i1 = frameHeight - 170 - i * 13 - extendChatArea;
					}
					textDrawingArea.method385(0, chatMessages[j], i1, 4);
					textDrawingArea.method385(getChatColor(), chatMessages[j], i1 - 1, 4);
					if (++i >= 5) {
						return;
					}
				}
				if (k == 6 && privateChatMode < 2) {
					int j1 = 329 - i * 13;
					if (frameMode != ScreenMode.FIXED) {
						j1 = frameHeight - 170 - i * 13 - extendChatArea;
					}
					textDrawingArea.method385(0, "To " + s + ": " + chatMessages[j], j1, 4);
					textDrawingArea.method385(getChatColor(), "To " + s + ": " + chatMessages[j], j1 - 1, 4);
					if (++i >= 5) {
						return;
					}
				}
			}
		}
	}

	public static void setTab(int id) {
		tabID = id;
		tabAreaAltered = true;
	}

	private final void minimapHovers() {
		final boolean fixed = frameMode == ScreenMode.FIXED;
		hpHover = fixed ? super.mouseX >= 690 && super.mouseX <= 745 && super.mouseY >= 13 && super.mouseY < 47 : super.mouseX >= frameWidth - 216 && super.mouseX <= 159 && super.mouseY >= 13 && super.mouseY < 47;
		prayHover = fixed ? super.mouseX >= 518 && super.mouseX <= 574 && super.mouseY >= 86 && super.mouseY < 118 : super.mouseX >= frameWidth - 211 && super.mouseX <= frameWidth - 154 && super.mouseY >= 86 && super.mouseY < 120;
		runHover = fixed ? super.mouseX >= 543 && super.mouseX <= 600 && super.mouseY >= 123 && super.mouseY < 154 : super.mouseX >= frameWidth - 187 && super.mouseX <= frameWidth - 128 && super.mouseY >= 121 && super.mouseY < 156;
		counterHover = fixed ? super.mouseX >= 517 && super.mouseX <= 545 && super.mouseY >= 27 && super.mouseY <= 54 : super.mouseX >= frameWidth - 211 && super.mouseX <= frameWidth - 183 && super.mouseY >= 23 && super.mouseY <= 50;
		worldHover = fixed ? super.mouseX >= 718 && super.mouseX <= 748 && super.mouseY >= 22 && super.mouseY <= 50 : super.mouseX >= frameWidth - 135 && super.mouseX <= frameWidth - 102 && super.mouseY >= 152 && super.mouseY <= 185;
		pouchHover = fixed ? super.mouseX >= 678 && super.mouseX <= 739 && super.mouseY >= 129 && super.mouseY <= 157 : super.mouseX >= frameWidth - 65 && super.mouseX <= frameWidth && super.mouseY >= 154 && super.mouseY <= 185;

		// specialHover = fixed ? super.mouseX >= 670 && super.mouseX <= 727 &&
		// super.mouseY >= 133 && super.mouseY <= 164 : super.mouseX >=
		// frameWidth - 62 && super.mouseX <= frameWidth - 5 && super.mouseY >=
		// 151 && super.mouseY <= 184;
	}

	private final int[] tabClickX = { 38, 33, 33, 33, 33, 33, 38, 38, 33, 33, 33, 33, 33, 38 }, tabClickStart = { 522, 560, 593, 625, 659, 692, 724, 522, 560, 593, 625, 659, 692, 724 }, tabClickY = { 169, 169, 169, 169, 169, 169, 169, 466, 466, 466, 466, 466, 466, 466 };

	private void processTabClick() {
		if (super.clickMode3 == 1) {
			if (frameMode == ScreenMode.FIXED || frameMode != ScreenMode.FIXED && !changeTabArea) {
				int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 765;
				int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 503;
				for (int i = 0; i < tabClickX.length; i++) {
					if (super.mouseX >= tabClickStart[i] + xOffset && super.mouseX <= tabClickStart[i] + tabClickX[i] + xOffset && super.mouseY >= tabClickY[i] + yOffset && super.mouseY < tabClickY[i] + 37 + yOffset && tabInterfaceIDs[i] != -1) {
						tabID = i;
						tabAreaAltered = true;
						break;
					}
				}
			} else if (changeTabArea && frameWidth < 1000) {
				if (super.saveClickX >= frameWidth - 226 && super.saveClickX <= frameWidth - 195 && super.saveClickY >= frameHeight - 72 && super.saveClickY < frameHeight - 40 && tabInterfaceIDs[0] != -1) {
					if (tabID == 0) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 0;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 194 && super.saveClickX <= frameWidth - 163 && super.saveClickY >= frameHeight - 72 && super.saveClickY < frameHeight - 40 && tabInterfaceIDs[1] != -1) {
					if (tabID == 1) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 1;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 162 && super.saveClickX <= frameWidth - 131 && super.saveClickY >= frameHeight - 72 && super.saveClickY < frameHeight - 40 && tabInterfaceIDs[2] != -1) {
					if (tabID == 2) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 2;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 129 && super.saveClickX <= frameWidth - 98 && super.saveClickY >= frameHeight - 72 && super.saveClickY < frameHeight - 40 && tabInterfaceIDs[3] != -1) {
					if (tabID == 3) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 3;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 97 && super.saveClickX <= frameWidth - 66 && super.saveClickY >= frameHeight - 72 && super.saveClickY < frameHeight - 40 && tabInterfaceIDs[4] != -1) {
					if (tabID == 4) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 4;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 65 && super.saveClickX <= frameWidth - 34 && super.saveClickY >= frameHeight - 72 && super.saveClickY < frameHeight - 40 && tabInterfaceIDs[5] != -1) {
					if (tabID == 5) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 5;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 33 && super.saveClickX <= frameWidth && super.saveClickY >= frameHeight - 72 && super.saveClickY < frameHeight - 40 && tabInterfaceIDs[6] != -1) {
					if (tabID == 6) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 6;
					tabAreaAltered = true;

				}

				if (super.saveClickX >= frameWidth - 226 && super.saveClickX <= frameWidth - 195 && super.saveClickY >= frameHeight - 37 && super.saveClickY < frameHeight - 0 && tabInterfaceIDs[7] != -1) {
					if (tabID == 7) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 7;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 194 && super.saveClickX <= frameWidth - 163 && super.saveClickY >= frameHeight - 37 && super.saveClickY < frameHeight - 0 && tabInterfaceIDs[8] != -1) {
					if (tabID == 8) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 8;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 162 && super.saveClickX <= frameWidth - 131 && super.saveClickY >= frameHeight - 37 && super.saveClickY < frameHeight - 0 && tabInterfaceIDs[9] != -1) {
					if (tabID == 9) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 9;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 129 && super.saveClickX <= frameWidth - 98 && super.saveClickY >= frameHeight - 37 && super.saveClickY < frameHeight - 0 && tabInterfaceIDs[10] != -1) {
					if (tabID == 10) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 10;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 97 && super.saveClickX <= frameWidth - 66 && super.saveClickY >= frameHeight - 37 && super.saveClickY < frameHeight - 0 && tabInterfaceIDs[11] != -1) {
					if (tabID == 11) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 11;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 65 && super.saveClickX <= frameWidth - 34 && super.saveClickY >= frameHeight - 37 && super.saveClickY < frameHeight - 0 && tabInterfaceIDs[12] != -1) {
					if (tabID == 12) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 12;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 33 && super.saveClickX <= frameWidth && super.saveClickY >= frameHeight - 37 && super.saveClickY < frameHeight - 0 && tabInterfaceIDs[13] != -1) {
					if (tabID == 13) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabID = 13;
					tabAreaAltered = true;

				}
			} else if (changeTabArea && frameWidth >= 1000) {
				if (super.mouseY >= frameHeight - 37 && super.mouseY <= frameHeight) {
					if (super.mouseX >= frameWidth - 449 && super.mouseX <= frameWidth - 418) {
						if (tabID == 0) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 0;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 417 && super.mouseX <= frameWidth - 386) {
						if (tabID == 1) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 1;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 385 && super.mouseX <= frameWidth - 354) {
						if (tabID == 2) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 2;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 353 && super.mouseX <= frameWidth - 322) {
						if (tabID == 3) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 3;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 321 && super.mouseX <= frameWidth - 290) {
						if (tabID == 4) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 4;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 289 && super.mouseX <= frameWidth - 258) {
						if (tabID == 5) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 5;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 257 && super.mouseX <= frameWidth - 226) {
						if (tabID == 6) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 6;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 225 && super.mouseX <= frameWidth - 196) {
						if (tabID == 7) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 7;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 195 && super.mouseX <= frameWidth - 164) {
						if (tabID == 8) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 8;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 163 && super.mouseX <= frameWidth - 132) {
						if (tabID == 9) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 9;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 131 && super.mouseX <= frameWidth - 100) {
						if (tabID == 10) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 10;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 99 && super.mouseX <= frameWidth - 68) {
						if (tabID == 11) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 11;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 67 && super.mouseX <= frameWidth - 36) {
						if (tabID == 12) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 12;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 32 && super.mouseX <= frameWidth) {
						if (tabID == 13) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabID = 13;
						tabAreaAltered = true;
					}
				}
			}
		}
	}

	private void resetImageProducers2() {
		if (aRSImageProducer_1166 != null)
			return;
		nullLoader();
		super.fullGameScreen = null;
		aRSImageProducer_1107 = null;
		aRSImageProducer_1108 = null;
		aRSImageProducer_1109 = null;
		aRSImageProducer_1110 = null;
		aRSImageProducer_1111 = null;
		aRSImageProducer_1112 = null;
		aRSImageProducer_1113 = null;
		aRSImageProducer_1114 = null;
		aRSImageProducer_1115 = null;
		aRSImageProducer_1166 = new ImageProducer(519, 165);// chatback
		aRSImageProducer_1164 = new ImageProducer(249, 168);// mapback
		DrawingArea.setAllPixelsToZero();
		fixedGameComponents[0].drawSprite(0, 0);
		aRSImageProducer_1163 = new ImageProducer(249, 335);// inventory
		aRSImageProducer_1165 = new ImageProducer(frameMode == ScreenMode.FIXED ? 512 : frameWidth, frameMode == ScreenMode.FIXED ? 334 : frameHeight);// gamescreen
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1125 = new ImageProducer(249, 45);
		welcomeScreenRaised = true;
	}

	private void method81(Sprite sprite, int j, int k) {
		int l = k * k + j * j;
		if (l > 4225 && l < 0x15f90) {
			int i1 = minimapInt1 + minimapInt2 & 0x7ff;
			int j1 = Model.modelIntArray1[i1];
			int k1 = Model.modelIntArray2[i1];
			j1 = (j1 * 256) / (minimapInt3 + 256);
			k1 = (k1 * 256) / (minimapInt3 + 256);
		} else {
			markMinimap(sprite, k, j);
		}
	}

	public void rightClickChatButtons() {
		if (mouseY >= frameHeight - 22 && mouseY <= frameHeight) {
			if (super.mouseX >= 5 && super.mouseX <= 61) {
				menuActionName[1] = "View All";
				menuActionID[1] = 999;
				menuActionRow = 2;
			} else if (super.mouseX >= 71 && super.mouseX <= 127) {
				menuActionName[1] = "View Game";
				menuActionID[1] = 998;
				menuActionRow = 2;
			} else if (super.mouseX >= 137 && super.mouseX <= 193) {
				menuActionName[1] = "Hide public";
				menuActionID[1] = 997;
				menuActionName[2] = "Off public";
				menuActionID[2] = 996;
				menuActionName[3] = "Friends public";
				menuActionID[3] = 995;
				menuActionName[4] = "On public";
				menuActionID[4] = 994;
				menuActionName[5] = "View public";
				menuActionID[5] = 993;
				menuActionRow = 6;
			} else if (super.mouseX >= 203 && super.mouseX <= 259) {
				menuActionName[1] = "Off private";
				menuActionID[1] = 992;
				menuActionName[2] = "Friends private";
				menuActionID[2] = 991;
				menuActionName[3] = "On private";
				menuActionID[3] = 990;
				menuActionName[4] = "View private";
				menuActionID[4] = 989;
				menuActionRow = 5;
			} else if (super.mouseX >= 269 && super.mouseX <= 325) {
				menuActionName[1] = "Off clan chat";
				menuActionID[1] = 1003;
				menuActionName[2] = "Friends clan chat";
				menuActionID[2] = 1002;
				menuActionName[3] = "On clan chat";
				menuActionID[3] = 1001;
				menuActionName[4] = "View clan chat";
				menuActionID[4] = 1000;
				menuActionRow = 5;
			} else if (super.mouseX >= 335 && super.mouseX <= 391) {
				menuActionName[1] = "Off trade";
				menuActionID[1] = 987;
				menuActionName[2] = "Friends trade";
				menuActionID[2] = 986;
				menuActionName[3] = "On trade";
				menuActionID[3] = 985;
				menuActionName[4] = "View trade";
				menuActionID[4] = 984;
				menuActionRow = 5;
			} else if (super.mouseX >= 404 && super.mouseX <= 515) {
				menuActionName[1] = "Report Abuse";
				menuActionID[1] = 606;
				menuActionRow = 2;
			}
		}
	}

	public void processRightClick() {
		if (activeInterfaceType != 0) {
			return;
		}
		menuActionName[0] = "Cancel";
		menuActionID[0] = 1107;
		menuActionRow = 1;
		if (showChatComponents) {
			buildSplitPrivateChatMenu();
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (frameMode == ScreenMode.FIXED) {
			if (super.mouseX > 4 && super.mouseY > 4 && super.mouseX < 516 && super.mouseY < 338) {
				if (openInterfaceID != -1) {
					buildInterfaceMenu(4, RSInterface.interfaceCache[openInterfaceID], super.mouseX, 4, super.mouseY, 0);
				} else {
					build3dScreenMenu();
				}
			}
		} else if (frameMode != ScreenMode.FIXED) {
			if (getMousePositions()) {
				int w = 512, h = 334;
				int x = (frameWidth / 2) - 256, y = (frameHeight / 2) - 167;
				int x2 = (frameWidth / 2) + 256, y2 = (frameHeight / 2) + 167;
				int count = !changeTabArea ? 4 : 3;
				if (frameMode != ScreenMode.FIXED) {
					for (int i = 0; i < count; i++) {
						if (x + w > (frameWidth - 225)) {
							x = x - 30;
							x2 = x2 - 30;
							if (x < 0) {
								x = 0;
							}
						}
						if (y + h > (frameHeight - 182)) {
							y = y - 30;
							y2 = y2 - 30;
							if (y < 0) {
								y = 0;
							}
						}
					}
				}
				if (openInterfaceID == 5292) {
					if (super.mouseX > (frameWidth / 2) - 356 && super.mouseY > (frameHeight / 2) - 230 && super.mouseX < ((frameWidth / 2) + 356) && super.mouseY < (frameHeight / 2) + 230) {
						buildInterfaceMenu((frameWidth / 2) - 356, RSInterface.interfaceCache[openInterfaceID], super.mouseX, (frameHeight / 2) - 230, super.mouseY, 0);
					} else {
						build3dScreenMenu();
					}
				} else if (openInterfaceID != -1 && openInterfaceID != 5292 && super.mouseX > x && super.mouseY > y && super.mouseX < x2 && super.mouseY < y2) {
					buildInterfaceMenu(x, RSInterface.interfaceCache[openInterfaceID], super.mouseX, y, super.mouseY, 0);
				} else {
					build3dScreenMenu();
				}
			}
		}
		if (anInt886 != anInt1026) {
			anInt1026 = anInt886;
		}
		if (anInt1315 != anInt1129) {
			anInt1129 = anInt1315;
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (!changeTabArea) {
			final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 503;
			final int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 765;
			if (super.mouseX > 548 + xOffset && super.mouseX < 740 + xOffset && super.mouseY > 207 + yOffset && super.mouseY < 468 + yOffset) {
				if (invOverlayInterfaceID != -1) {
					buildInterfaceMenu(548 + xOffset, RSInterface.interfaceCache[invOverlayInterfaceID], super.mouseX, 207 + yOffset, super.mouseY, 0);
				} else if (tabInterfaceIDs[tabID] != -1) {
					buildInterfaceMenu(548 + xOffset, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], super.mouseX, 207 + yOffset, super.mouseY, 0);
				}
			}
		} else if (changeTabArea) {
			final int yOffset = frameWidth >= 1000 ? 37 : 74;
			if (super.mouseX > frameWidth - 197 && super.mouseY > frameHeight - yOffset - 267 && super.mouseX < frameWidth - 7 && super.mouseY < frameHeight - yOffset - 7 && showTabComponents) {
				if (invOverlayInterfaceID != -1) {
					buildInterfaceMenu(frameWidth - 197, RSInterface.interfaceCache[invOverlayInterfaceID], super.mouseX, frameHeight - yOffset - 267, super.mouseY, 0);
				} else if (tabInterfaceIDs[tabID] != -1) {
					buildInterfaceMenu(frameWidth - 197, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], super.mouseX, frameHeight - yOffset - 267, super.mouseY, 0);
				}
			}
		}
		if (anInt886 != anInt1048) {
			tabAreaAltered = true;
			anInt1048 = anInt886;
		}
		if (anInt1315 != anInt1044) {
			tabAreaAltered = true;
			anInt1044 = anInt1315;
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (super.mouseX > 0 && super.mouseY > (frameMode == ScreenMode.FIXED ? 338 : frameHeight - (165 + extendChatArea)) && super.mouseX < 490 && super.mouseY < (frameMode == ScreenMode.FIXED ? 463 : frameHeight - 40) && showChatComponents) {
			if (backDialogID != -1) {
				buildInterfaceMenu(20, RSInterface.interfaceCache[backDialogID], super.mouseX, (frameMode == ScreenMode.FIXED ? 358 : frameHeight - 145), super.mouseY, 0);
			} else if (super.mouseY < (frameMode == ScreenMode.FIXED ? 463 : frameHeight - 40) && super.mouseX < 490) {
				buildChatAreaMenu(super.mouseY - (frameMode == ScreenMode.FIXED ? 338 : frameHeight - 165));
			}
		}
		if (backDialogID != -1 && anInt886 != anInt1039) {
			inputTaken = true;
			anInt1039 = anInt886;
		}
		if (backDialogID != -1 && anInt1315 != anInt1500) {
			inputTaken = true;
			anInt1500 = anInt1315;
		}
		if (super.mouseX > 4 && super.mouseY > 480 && super.mouseX < 516 && super.mouseY < frameHeight) {
			rightClickChatButtons();
		}
		processMinimapActions();
		boolean flag = false;
		while (!flag) {
			flag = true;
			for (int j = 0; j < menuActionRow - 1; j++) {
				if (menuActionID[j] < 1000 && menuActionID[j + 1] > 1000) {
					String s = menuActionName[j];
					menuActionName[j] = menuActionName[j + 1];
					menuActionName[j + 1] = s;
					int k = menuActionID[j];
					menuActionID[j] = menuActionID[j + 1];
					menuActionID[j + 1] = k;
					k = menuActionCmd2[j];
					menuActionCmd2[j] = menuActionCmd2[j + 1];
					menuActionCmd2[j + 1] = k;
					k = menuActionCmd3[j];
					menuActionCmd3[j] = menuActionCmd3[j + 1];
					menuActionCmd3[j + 1] = k;
					k = menuActionCmd1[j];
					menuActionCmd1[j] = menuActionCmd1[j + 1];
					menuActionCmd1[j + 1] = k;
					flag = false;
				}
			}
		}
	}

	private AccountData currentAccount;

	public void login(String username, String password, boolean flag) {
		Signlink.errorname = username;
		try {
			if (rememberMe && username != null && password != null) {			
				SettingHandler.save();
			}
			if (!flag) {
				loginMessage1 = "";
				loginMessage2 = "Connecting to server...";
				loginRenderer.displayLoginScreen();
			}
			server = ClientConstants.SERVER_IPS[ClientConstants.worldSelected - 1];
			socketStream = new RSSocket(this, openSocket(ClientConstants.SERVER_PORT + portOff));
			long l = TextClass.longForName(username);
			int i = (int) (l >> 16 & 31L);
			stream.currentOffset = 0;
			stream.writeWordBigEndian(14);
			stream.writeWordBigEndian(i);
			socketStream.queueBytes(2, stream.buffer);
			for (int j = 0; j < 8; j++)
				socketStream.read();

			int k = socketStream.read();
			int i1 = k;
			if (k == 0) {
				socketStream.flushInputStream(inStream.buffer, 8);
				inStream.currentOffset = 0;
				aLong1215 = inStream.readQWord();
				int ai[] = new int[4];
				ai[0] = (int) (Math.random() * 99999999D);
				ai[1] = (int) (Math.random() * 99999999D);
				ai[2] = (int) (aLong1215 >> 32);
				ai[3] = (int) aLong1215;
				stream.currentOffset = 0;
				stream.writeWordBigEndian(100);
				stream.writeDWord(ai[0]);
				stream.writeDWord(ai[1]);
				stream.writeDWord(ai[2]);
				stream.writeDWord(ai[3]);
				stream.writeString(String.valueOf(IdentityResolver.resolve()));
				stream.writeString(username);
				stream.writeString(password);
				stream.doKeys();
				aStream_847.currentOffset = 0;
				if (flag)
					aStream_847.writeWordBigEndian(18);
				else
					aStream_847.writeWordBigEndian(16);
				aStream_847.writeWordBigEndian(stream.currentOffset + 36 + 1 + 1 + 2);
				aStream_847.writeWordBigEndian(255);
				aStream_847.writeWord(217 + ClientConstants.CLIENT_VERSION_INT);
				aStream_847.writeWordBigEndian(lowMem ? 1 : 0);
				for (int l1 = 0; l1 < 9; l1++)
					aStream_847.writeDWord(expectedCRCs[l1]);

				aStream_847.writeBytes(stream.buffer, stream.currentOffset, 0);
				stream.encryption = new ISAACRandomGen(ai);
				for (int j2 = 0; j2 < 4; j2++)
					ai[j2] += 50;

				encryption = new ISAACRandomGen(ai);
				socketStream.queueBytes(aStream_847.currentOffset, aStream_847.buffer);
				k = socketStream.read();
			}
			if (k == 1) {
				try {
					Thread.sleep(2000L);
				} catch (Exception _ex) {
				}
				login(username, password, flag);
				return;
			}
			if (k == 2) {
				myUsername = username;
				myPassword = password;
				myPrivilege = socketStream.read();
				final AccountData account = new AccountData(myPrivilege, username, password);
				if (rememberMe) {
					AccountManager.addAccount(account);
				}
				currentAccount = AccountManager.getAccount(username);
				if (currentAccount == null) {
					currentAccount = account;
				}
				if (rememberMe) {
					AccountManager.saveAccount();
				}
				flagged = socketStream.read() == 1;
				aLong1220 = 0L;
				anInt1022 = 0;
				mouseDetection.coordsIndex = 0;
				super.awtFocus = true;
				aBoolean954 = true;
				loggedIn = true;
				stream.currentOffset = 0;
				inStream.currentOffset = 0;
				pktType = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				pktSize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				anInt1011 = 0;
				anInt855 = 0;
				menuActionRow = 0;
				menuOpen = false;
				super.idleTime = 0;
				for (int j1 = 0; j1 < 500; j1++)
					chatMessages[j1] = null;
				itemSelected = 0;
				spellSelected = 0;
				loadingStage = 0;
				anInt1062 = 0;
				setNorth();
				anInt1021 = 0;
				anInt985 = -1;
				destX = 0;
				destY = 0;
				playerCount = 0;
				npcCount = 0;
				for (int i2 = 0; i2 < maxPlayers; i2++) {
					playerArray[i2] = null;
					aStreamArray895s[i2] = null;
				}
				for (int index = 0; index < 17; index++) {
					console.inputConsoleMessages[index] = "";
				}
				for (int k2 = 0; k2 < 16384; k2++)
					npcArray[k2] = null;
				myPlayer = playerArray[myPlayerIndex] = new Player();
				aClass19_1013.removeAll();
				aClass19_1056.removeAll();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++)
							groundArray[l2][i3][k3] = null;
					}
				}
				aClass19_1179 = new NodeList();
				fullscreenInterfaceID = -1;
				anInt900 = 0;
				friendsCount = 0;
				dialogID = -1;
				backDialogID = -1;
				openInterfaceID = -1;
				invOverlayInterfaceID = -1;
				anInt1018 = -1;
				aBoolean1149 = false;
				tabID = 3;
				inputDialogState = 0;
				menuOpen = false;
				messagePromptRaised = false;
				aString844 = null;
				anInt1055 = 0;
				anInt1054 = -1;
				aBoolean1047 = true;
				method45();
				for (int j3 = 0; j3 < 5; j3++)
					anIntArray990[j3] = 0;
				for (int l3 = 0; l3 < 5; l3++) {
					atPlayerActions[l3] = null;
					atPlayerArray[l3] = false;
				}
				anInt1175 = 0;
				anInt1134 = 0;
				anInt986 = 0;
				anInt1288 = 0;
				anInt924 = 0;
				anInt1188 = 0;
				anInt1155 = 0;
				anInt1226 = 0;
				sendFrame36(429, 1);
				resetImageProducers2();
				setBounds();
				return;
			}
			if (k == 3) {
				loginMessage1 = "Invalid username or password.";
				loginMessage2 = "Please try again.";
				return;
			}
			if (k == 4) {
				loginMessage1 = "Your account has been disabled.";
				loginMessage2 = "Please check your message-center for details.";
				return;
			}
			if (k == 5) {
				loginMessage1 = "Your account is already logged in.";
				loginMessage2 = "Try again in 60 secs...";
				return;
			}
			if (k == 6) {
				loginMessage1 = "Vencillio has been updated!";
				loginMessage2 = "Please download the newest client.";
				openURL("http://www.vencillio.com/downloads/Client.jar");
				return;
			}
			if (k == 7) {
				loginMessage1 = "This world is full.";
				loginMessage2 = "Please use a different world.";
				return;
			}
			if (k == 8) {
				loginMessage1 = "Unable to connect.";
				loginMessage2 = "Login server offline.";
				return;
			}
			if (k == 9) {
				loginMessage1 = "Login limit exceeded.";
				loginMessage2 = "Too many connections from your address.";
				return;
			}
			if (k == 10) {
				loginMessage1 = "Unable to connect.";
				loginMessage2 = "Bad session id.";
				return;
			}
			if (k == 11) {
				loginMessage1 = "Login server rejected session.";
				loginMessage2 = "Please try again.";
				return;
			}
			if (k == 12) {
				loginMessage1 = "You need a members account to login to this world.";
				loginMessage2 = "Please subscribe, or use a different world.";
				return;
			}
			if (k == 13) {
				loginMessage1 = "Could not complete login.";
				loginMessage2 = "Please try using a different world.";
				return;
			}
			if (k == 14) {
				loginMessage1 = "The server is being updated.";
				loginMessage2 = "Please wait 1 minute and try again.";
				return;
			}
			if (k == 15) {
				loggedIn = true;
				stream.currentOffset = 0;
				inStream.currentOffset = 0;
				pktType = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				pktSize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				menuActionRow = 0;
				menuOpen = false;
				aLong824 = System.currentTimeMillis();
				return;
			}
			if (k == 16) {
				loginMessage1 = "Login attempts exceeded.";
				loginMessage2 = "Please wait 1 minute and try again.";
				return;
			}
			if (k == 17) {
				loginMessage1 = "You are standing in a members-only area.";
				loginMessage2 = "To play on this world move to a free area first";
				return;
			}
			if (k == 20) {
				loginMessage1 = "Invalid loginserver requested";
				loginMessage2 = "Please try using a different world.";
				return;
			}
			if (k == 21) {
				for (int k1 = socketStream.read(); k1 >= 0; k1--) {
					loginMessage1 = "You have only just left another world";
					loginMessage2 = "Your profile will be transferred in: " + k1 + " seconds";
					loginRenderer.displayLoginScreen();
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}
				login(username, password, flag);
				return;
			}
			if (k == 22) {
				loginMessage1 = "The username '" + TextClass.capitalize(myUsername) + "' is restricted!";
				loginMessage2 = "Please use a different one.";
				return;
			}
			if (k == 23) {
				loginMessage1 = "You do not have permission to do this!";
				loginMessage2 = "Please try a different world.";
				return;
			}
			if (k == -1) {
				if (i1 == 0) {
					if (loginFailures < 2) {
						try {
							Thread.sleep(2000L);
						} catch (Exception _ex) {
						}
						loginFailures++;
						login(username, password, flag);
						return;
					} else {
						loginMessage1 = "No response from loginserver";
						loginMessage2 = "Please wait 1 minute and try again.";
						return;
					}
				} else {
					loginMessage1 = "No response from server";
					loginMessage2 = "Please try using a different world.";
					return;
				}
			} else {
				System.out.println("response:" + k);
				loginMessage1 = "Unexpected server response";
				loginMessage2 = "Please try using a different world.";
				return;
			}
		} catch (IOException _ex) {
			loginMessage1 = "";
		}
		loginMessage2 = "Error connecting to server.";
	}

	private boolean doWalkTo(int i, int j, int k, int i1, int j1, int k1, int l1, int i2, int j2, boolean flag, int k2) {
		byte byte0 = 104;
		byte byte1 = 104;
		for (int l2 = 0; l2 < byte0; l2++) {
			for (int i3 = 0; i3 < byte1; i3++) {
				anIntArrayArray901[l2][i3] = 0;
				anIntArrayArray825[l2][i3] = 0x5f5e0ff;
			}
		}
		int j3 = j2;
		int k3 = j1;
		anIntArrayArray901[j2][j1] = 99;
		anIntArrayArray825[j2][j1] = 0;
		int l3 = 0;
		int i4 = 0;
		bigX[l3] = j2;
		bigY[l3++] = j1;
		boolean flag1 = false;
		int j4 = bigX.length;
		int ai[][] = aClass11Array1230[plane].anIntArrayArray294;
		while (i4 != l3) {
			j3 = bigX[i4];
			k3 = bigY[i4];
			i4 = (i4 + 1) % j4;
			if (j3 == k2 && k3 == i2) {
				flag1 = true;
				break;
			}
			if (i1 != 0) {
				if ((i1 < 5 || i1 == 10) && aClass11Array1230[plane].method219(k2, j3, k3, j, i1 - 1, i2)) {
					flag1 = true;
					break;
				}
				if (i1 < 10 && aClass11Array1230[plane].method220(k2, i2, k3, i1 - 1, j, j3)) {
					flag1 = true;
					break;
				}
			}
			if (k1 != 0 && k != 0 && aClass11Array1230[plane].method221(i2, k2, j3, k, l1, k1, k3)) {
				flag1 = true;
				break;
			}
			int l4 = anIntArrayArray825[j3][k3] + 1;
			if (j3 > 0 && anIntArrayArray901[j3 - 1][k3] == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3] = 2;
				anIntArrayArray825[j3 - 1][k3] = l4;
			}
			if (j3 < byte0 - 1 && anIntArrayArray901[j3 + 1][k3] == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3] = 8;
				anIntArrayArray825[j3 + 1][k3] = l4;
			}
			if (k3 > 0 && anIntArrayArray901[j3][k3 - 1] == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 - 1] = 1;
				anIntArrayArray825[j3][k3 - 1] = l4;
			}
			if (k3 < byte1 - 1 && anIntArrayArray901[j3][k3 + 1] == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 + 1] = 4;
				anIntArrayArray825[j3][k3 + 1] = l4;
			}
			if (j3 > 0 && k3 > 0 && anIntArrayArray901[j3 - 1][k3 - 1] == 0 && (ai[j3 - 1][k3 - 1] & 0x128010e) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 - 1] = 3;
				anIntArrayArray825[j3 - 1][k3 - 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 > 0 && anIntArrayArray901[j3 + 1][k3 - 1] == 0 && (ai[j3 + 1][k3 - 1] & 0x1280183) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 - 1] = 9;
				anIntArrayArray825[j3 + 1][k3 - 1] = l4;
			}
			if (j3 > 0 && k3 < byte1 - 1 && anIntArrayArray901[j3 - 1][k3 + 1] == 0 && (ai[j3 - 1][k3 + 1] & 0x1280138) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 + 1] = 6;
				anIntArrayArray825[j3 - 1][k3 + 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 < byte1 - 1 && anIntArrayArray901[j3 + 1][k3 + 1] == 0 && (ai[j3 + 1][k3 + 1] & 0x12801e0) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 + 1] = 12;
				anIntArrayArray825[j3 + 1][k3 + 1] = l4;
			}
		}
		anInt1264 = 0;
		if (!flag1) {
			if (flag) {
				int i5 = 100;
				for (int k5 = 1; k5 < 2; k5++) {
					for (int i6 = k2 - k5; i6 <= k2 + k5; i6++) {
						for (int l6 = i2 - k5; l6 <= i2 + k5; l6++) {
							if (i6 >= 0 && l6 >= 0 && i6 < 104 && l6 < 104 && anIntArrayArray825[i6][l6] < i5) {
								i5 = anIntArrayArray825[i6][l6];
								j3 = i6;
								k3 = l6;
								anInt1264 = 1;
								flag1 = true;
							}
						}
					}
					if (flag1)
						break;
				}
			}
			if (!flag1)
				return false;
		}
		i4 = 0;
		bigX[i4] = j3;
		bigY[i4++] = k3;
		int l5;
		for (int j5 = l5 = anIntArrayArray901[j3][k3]; j3 != j2 || k3 != j1; j5 = anIntArrayArray901[j3][k3]) {
			if (j5 != l5) {
				l5 = j5;
				bigX[i4] = j3;
				bigY[i4++] = k3;
			}
			if ((j5 & 2) != 0)
				j3++;
			else if ((j5 & 8) != 0)
				j3--;
			if ((j5 & 1) != 0)
				k3++;
			else if ((j5 & 4) != 0)
				k3--;
		}
		if (i4 > 0) {
			int k4 = i4;
			if (k4 > 25)
				k4 = 25;
			i4--;
			int k6 = bigX[i4];
			int i7 = bigY[i4];
			anInt1288 += k4;
			if (anInt1288 >= 92) {
				stream.createFrame(36);
				stream.writeDWord(0);
				anInt1288 = 0;
			}
			if (i == 0) {
				stream.createFrame(164);
				stream.writeWordBigEndian(k4 + k4 + 3);
			}
			if (i == 1) {
				stream.createFrame(248);
				stream.writeWordBigEndian(k4 + k4 + 3 + 14);
			}
			if (i == 2) {
				stream.createFrame(98);
				stream.writeWordBigEndian(k4 + k4 + 3);
			}
			stream.method433(k6 + baseX);
			destX = bigX[0];
			destY = bigY[0];
			for (int j7 = 1; j7 < k4; j7++) {
				i4--;
				stream.writeWordBigEndian(bigX[i4] - k6);
				stream.writeWordBigEndian(bigY[i4] - i7);
			}
			stream.method431(i7 + baseY);
			stream.method424(super.keyArray[5] != 1 ? 0 : 1);
			return true;
		}
		return i != 1;
	}

	private void method86(Stream stream) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Npc npc = npcArray[k];
			int l = stream.readUnsignedByte();
			if ((l & 0x10) != 0) {
				int i1 = stream.method434();
				if (i1 == 65535)
					i1 = -1;
				int i2 = stream.readUnsignedByte();
				if (i1 == npc.anim && i1 != -1) {
					int l2 = Animation.anims[i1].anInt365;
					if (l2 == 1) {
						npc.anInt1527 = 0;
						npc.anInt1528 = 0;
						npc.anInt1529 = i2;
						npc.anInt1530 = 0;
					}
					if (l2 == 2)
						npc.anInt1530 = 0;
				} else if (i1 == -1 || npc.anim == -1 || Animation.anims[i1].anInt359 >= Animation.anims[npc.anim].anInt359) {
					npc.anim = i1;
					npc.anInt1527 = 0;
					npc.anInt1528 = 0;
					npc.anInt1529 = i2;
					npc.anInt1530 = 0;
					npc.anInt1542 = npc.smallXYIndex;
				}
			}
			if ((l & 8) != 0) {
				int j1 = stream.method426();
				int j2 = stream.method427();
				int icon = stream.readUnsignedByte();
				npc.updateHitData(j2, j1, loopCycle, icon);
				npc.loopCycleStatus = loopCycle + 300;
				npc.currentHealth = stream.method426();
				npc.maxHealth = stream.readUnsignedByte();
			}
			if ((l & 0x80) != 0) {
				npc.anInt1520 = stream.readUnsignedWord();
				int k1 = stream.readDWord();
				npc.anInt1524 = k1 >> 16;
				npc.anInt1523 = loopCycle + (k1 & 0xffff);
				npc.anInt1521 = 0;
				npc.anInt1522 = 0;
				if (npc.anInt1523 > loopCycle)
					npc.anInt1521 = -1;
				if (npc.anInt1520 == 65535)
					npc.anInt1520 = -1;
			}
			if ((l & 0x20) != 0) {
				npc.interactingEntity = stream.readUnsignedWord();
				if (npc.interactingEntity == 65535)
					npc.interactingEntity = -1;
			}
			if ((l & 1) != 0) {
				npc.textSpoken = stream.readString();
				npc.textCycle = 100;
			}
			if ((l & 0x40) != 0) {
				int l1 = stream.method427();
				int k2 = stream.method428();
				int icon = stream.readUnsignedByte();
				npc.updateHitData(k2, l1, loopCycle, icon);
				npc.loopCycleStatus = loopCycle + 300;
				npc.currentHealth = stream.method428();
				npc.maxHealth = stream.method427();
			}
			if ((l & 2) != 0) {
				npc.desc = EntityDef.forID(stream.method436());
				npc.anInt1540 = npc.desc.aByte68;
				npc.anInt1504 = npc.desc.anInt79;
				npc.anInt1554 = npc.desc.walkAnim;
				npc.anInt1555 = npc.desc.anInt58;
				npc.anInt1556 = npc.desc.anInt83;
				npc.anInt1557 = npc.desc.anInt55;
				npc.anInt1511 = npc.desc.standAnim;
			}
			if ((l & 4) != 0) {
				npc.anInt1538 = stream.method434();
				npc.anInt1539 = stream.method434();
			}
		}
	}

	private void buildAtNPCMenu(EntityDef entityDef, int i, int j, int k) {
		if (menuActionRow >= 400)
			return;
		if (entityDef.childrenIDs != null)
			entityDef = entityDef.method161();
		if (entityDef == null)
			return;
		if (!entityDef.aBoolean84)
			return;
		String s = entityDef.name;
		if (entityDef.combatLevel != 0)
			s = s + combatDiffColor(myPlayer.combatLevel, entityDef.combatLevel) + " (level-" + entityDef.combatLevel + ")";
		if (itemSelected == 1) {
			menuActionName[menuActionRow] = "Use " + selectedItemName + " with @yel@" + s;
			menuActionID[menuActionRow] = 582;
			menuActionCmd1[menuActionRow] = i;
			menuActionCmd2[menuActionRow] = k;
			menuActionCmd3[menuActionRow] = j;
			menuActionRow++;
			return;
		}
		if (spellSelected == 1) {
			if ((spellUsableOn & 2) == 2) {
				menuActionName[menuActionRow] = spellTooltip + " @yel@" + s;
				menuActionID[menuActionRow] = 413;
				menuActionCmd1[menuActionRow] = i;
				menuActionCmd2[menuActionRow] = k;
				menuActionCmd3[menuActionRow] = j;
				menuActionRow++;
			}
		} else {
			if (entityDef.actions != null) {
				for (int l = 4; l >= 0; l--)
					if (entityDef.actions[l] != null && !entityDef.actions[l].equalsIgnoreCase("attack")) {
						menuActionName[menuActionRow] = entityDef.actions[l] + " @yel@" + s;
						if (l == 0)
							menuActionID[menuActionRow] = 20;
						if (l == 1)
							menuActionID[menuActionRow] = 412;
						if (l == 2)
							menuActionID[menuActionRow] = 225;
						if (l == 3)
							menuActionID[menuActionRow] = 965;
						if (l == 4)
							menuActionID[menuActionRow] = 478;
						menuActionCmd1[menuActionRow] = i;
						menuActionCmd2[menuActionRow] = k;
						menuActionCmd3[menuActionRow] = j;
						menuActionRow++;
					}

			}
			if (entityDef.actions != null) {
				for (int i1 = 4; i1 >= 0; i1--)
					if (entityDef.actions[i1] != null && entityDef.actions[i1].equalsIgnoreCase("attack")) {
						char c = '\0';
						if (Configuration.entityAttackPriority && entityDef.combatLevel > myPlayer.combatLevel) {
							c = '\u07D0';
						}
						menuActionName[menuActionRow] = entityDef.actions[i1] + " @yel@" + s;
						if (i1 == 0)
							menuActionID[menuActionRow] = 20 + c;
						if (i1 == 1)
							menuActionID[menuActionRow] = 412 + c;
						if (i1 == 2)
							menuActionID[menuActionRow] = 225 + c;
						if (i1 == 3)
							menuActionID[menuActionRow] = 965 + c;
						if (i1 == 4)
							menuActionID[menuActionRow] = 478 + c;
						menuActionCmd1[menuActionRow] = i;
						menuActionCmd2[menuActionRow] = k;
						menuActionCmd3[menuActionRow] = j;
						menuActionRow++;
					}

			}
			if (ClientConstants.DEBUG_MODE) {
				menuActionName[menuActionRow] = "Examine @yel@" + s + "@whi@(ID: @yel@" + entityDef.interfaceType + "@whi@)";
			} else {
				menuActionName[menuActionRow] = "Examine @yel@" + s;
			}
			menuActionID[menuActionRow] = 1025;
			menuActionCmd1[menuActionRow] = i;
			menuActionCmd2[menuActionRow] = k;
			menuActionCmd3[menuActionRow] = j;
			menuActionRow++;
		}
	}

	private void buildAtPlayerMenu(int i, int j, Player player, int k) {
		if (player == myPlayer)
			return;
		if (menuActionRow >= 400)
			return;
		String s;
		String title = player.title.length() > 0 ? (player.titlePrefix ? " " : "") + "<col=" + player.titleColor + ">" + player.title + "</col>" + (player.titlePrefix ? "" : " ") : "";
		if (player.skill == 0) {
			if (!player.titlePrefix) {
				s = title + "<col=ffffff>" + player.name + "</col>" + combatDiffColor(myPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
			} else {
				s = "</col>" + player.name + combatDiffColor(myPlayer.combatLevel, player.combatLevel) + title + " (level-" + player.combatLevel + ")";
			}
		} else {
			if (!player.titlePrefix) {
				s = title + player.name + " (skill-" + player.skill + ")";
			} else {
				s = player.name + title + " (skill-" + player.skill + ")";
			}
		}
		if (itemSelected == 1) {
			menuActionName[menuActionRow] = "Use " + selectedItemName + " with @whi@" + s;
			menuActionID[menuActionRow] = 491;
			menuActionCmd1[menuActionRow] = j;
			menuActionCmd2[menuActionRow] = i;
			menuActionCmd3[menuActionRow] = k;
			menuActionRow++;
		} else if (spellSelected == 1) {
			if ((spellUsableOn & 8) == 8) {
				menuActionName[menuActionRow] = spellTooltip + " @whi@" + s;
				menuActionID[menuActionRow] = 365;
				menuActionCmd1[menuActionRow] = j;
				menuActionCmd2[menuActionRow] = i;
				menuActionCmd3[menuActionRow] = k;
				menuActionRow++;
			}
		} else {
			for (int l = 4; l >= 0; l--)
				if (atPlayerActions[l] != null) {
					menuActionName[menuActionRow] = atPlayerActions[l] + " @whi@" + s;
					char c = '\0';
					if (atPlayerActions[l].equalsIgnoreCase("attack")) {
						if (Configuration.entityAttackPriority && player.combatLevel > myPlayer.combatLevel)
							c = '\u07D0';
						if (myPlayer.team != 0 && player.team != 0)
							if (myPlayer.team == player.team)
								c = '\u07D0';
							else
								c = '\0';
					} else if (atPlayerArray[l])
						c = '\u07D0';
					if (l == 0)
						menuActionID[menuActionRow] = 561 + c;
					if (l == 1)
						menuActionID[menuActionRow] = 779 + c;
					if (l == 2)
						menuActionID[menuActionRow] = 27 + c;
					if (l == 3)
						menuActionID[menuActionRow] = 577 + c;
					if (l == 4)
						menuActionID[menuActionRow] = 729 + c;
					menuActionCmd1[menuActionRow] = j;
					menuActionCmd2[menuActionRow] = i;
					menuActionCmd3[menuActionRow] = k;
					menuActionRow++;
				}

		}
		for (int i1 = 0; i1 < menuActionRow; i1++) {
			if (menuActionID[i1] == 519) {
				menuActionName[i1] = "Walk here @whi@" + s;
				return;
			}
		}
	}

	private void method89(Class30_Sub1 class30_sub1) {
		int i = 0;
		int j = -1;
		int k = 0;
		int l = 0;
		if (class30_sub1.anInt1296 == 0)
			i = worldController.method300(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (class30_sub1.anInt1296 == 1)
			i = worldController.method301(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (class30_sub1.anInt1296 == 2)
			i = worldController.method302(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (class30_sub1.anInt1296 == 3)
			i = worldController.method303(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (i != 0) {
			int i1 = worldController.method304(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298, i);
			j = i >> 14 & 0x7fff;
			k = i1 & 0x1f;
			l = i1 >> 6;
		}
		class30_sub1.anInt1299 = j;
		class30_sub1.anInt1301 = k;
		class30_sub1.anInt1300 = l;
	}

	void startUp() {
		rebuildFrameSize(frameMode, frameWidth, frameHeight);
//		/new CacheDownloader(this).run();

		SpriteLoader.loadSprites();
		cacheSprite = SpriteLoader.sprites;
		loginRenderer = new LoginRenderer(this);
		drawSmoothLoading(50, "Starting up");
		if (Signlink.cache_dat != null) {
			for (int i = 0; i < 6; i++)
				decompressors[i] = new Decompressor(Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
		}
		try {
			titleStreamLoader = streamLoaderForName(1, "title screen", "title", expectedCRCs[1], 25);
			smallText = new TextDrawingArea(false, "p11_full", titleStreamLoader);
			regularText = new TextDrawingArea(false, "p12_full", titleStreamLoader);
			boldText = new TextDrawingArea(false, "b12_full", titleStreamLoader);
			newSmallFont = new RSFont(false, "p11_full", titleStreamLoader);
			newRegularFont = new RSFont(false, "p12_full", titleStreamLoader);
			newBoldFont = new RSFont(false, "b12_full", titleStreamLoader);
			newFancyFont = new RSFont(true, "q8_full", titleStreamLoader);
			loadTitleScreen();
			TextDrawingArea aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full", titleStreamLoader);
			StreamLoader streamLoader = streamLoaderForName(2, "config", "config", expectedCRCs[2], 30);
			StreamLoader streamLoader_1 = streamLoaderForName(3, "interface", "interface", expectedCRCs[3], 35);
			StreamLoader streamLoader_2 = streamLoaderForName(4, "2d graphics", "media", expectedCRCs[4], 40);
			this.mediaStreamLoader = streamLoader_2;
			StreamLoader streamLoader_3 = streamLoaderForName(6, "textures", "textures", expectedCRCs[6], 45);
			StreamLoader streamLoader_4 = streamLoaderForName(7, "chat system", "wordenc", expectedCRCs[7], 50);
			streamLoaderForName(8, "sound effects", "sounds", expectedCRCs[8], 55);
			byteGroundArray = new byte[4][104][104];
			intGroundArray = new int[4][105][105];
			worldController = new WorldController(intGroundArray);
			for (int j = 0; j < 4; j++)
				aClass11Array1230[j] = new Class11();

			minimapImage = new Sprite(512, 512);
			StreamLoader streamLoader_6 = streamLoaderForName(5, "update list", "versionlist", expectedCRCs[5], 60);
			drawSmoothLoading(100, "Connecting to update server");
			onDemandFetcher = new OnDemandFetcher();
			onDemandFetcher.start(streamLoader_6, this);
			SequenceFrame.animationlist = new SequenceFrame[2500][0];
			Model.method459(onDemandFetcher.getModelCount(), onDemandFetcher);
			//repackCacheIndex(1);
			drawSmoothLoading(175, "Unpacking media");
			Sprite[] clanIcons = new Sprite[10];
			for (int index = 0; index < newHitMarks.length; index++) {
				newHitMarks[index] = new Sprite(streamLoader_2, "newhitmarks", index);
			}
			for (int index = 0; index < channelButtons.length; index++) {
				channelButtons[index] = new Sprite(streamLoader_2, "cbuttons", index);
			}
			for (int index = 0; index < fixedGameComponents.length; index++) {
				fixedGameComponents[index] = new Sprite(streamLoader_2, "fixed", index);
			}
			for (int index = 0; index < skillIcons.length; index++) {
				skillIcons[index] = new Sprite(streamLoader_2, "skillicons", index);
			}
			for (int index = 0; index < gameComponents.length; index++) {
				gameComponents[index] = new Sprite(streamLoader_2, "fullscreen", index);
			}
			for (int index = 0; index < orbComponents.length; index++) {
				orbComponents[index] = new Sprite(streamLoader_2, "orbs3", index);
			}
			for (int index = 0; index < orbComponents2.length; index++) {
				orbComponents2[index] = new Sprite(streamLoader_2, "orbs4", index);
			}
			for (int index = 0; index < orbComponents3.length; index++) {
				orbComponents3[index] = new Sprite(streamLoader_2, "orbs5", index);
			}
			for (int index = 0; index < redStones.length; index++) {
				redStones[index] = new Sprite(streamLoader_2, "redstone1", index);
			}
			for (int index = 0; index < hpBars.length; index++) {
				hpBars[index] = new Sprite(streamLoader_2, "hpbars", index);
			}
			for (int index = 0; index < clanIcons.length; index++) {
				clanIcons[index] = new Sprite(streamLoader_2, "clanicons", index);
			}
			for (int index = 0; index < currencies; index++) {
				currencyImage[index] = cacheSprite[407 + index];
			}
			newSmallFont.unpackImages(modIcons, clanIcons);
			newRegularFont.unpackImages(modIcons, clanIcons);
			newBoldFont.unpackImages(modIcons, clanIcons);
			newFancyFont.unpackImages(modIcons, clanIcons);
			multiOverlay = new Sprite(streamLoader_2, "overlay_multiway", 0);
			mapBack = new Background(streamLoader_2, "mapback", 0);
			for (int j3 = 0; j3 <= 16; j3++) {
				sideIcons[j3] = new Sprite(streamLoader_2, "sideicons", j3);
			}

			for (int i4 = 475; i4 <= 483; i4++) {
				hitMark[i4 - 475] = cacheSprite[i4];
			}

			for (int i4 = 484; i4 <= 489; i4++) {
				hitIcon[i4 - 484] = cacheSprite[i4];
			}
			compass = new Sprite(streamLoader_2, "compass", 0);
			try {
				for (int k3 = 0; k3 < 100; k3++)
					mapScenes[k3] = new Background(streamLoader_2, "mapscene", k3);
			} catch (Exception _ex) {
			}
			try {
				for (int l3 = 0; l3 < 100; l3++)
					mapFunctions[l3] = new Sprite(streamLoader_2, "mapfunction", l3);
			} catch (Exception _ex) {
			}
			try {
				for (int i4 = 0; i4 < 20; i4++)
					hitMarks[i4] = new Sprite(streamLoader_2, "hitmarks", i4);
			} catch (Exception _ex) {
			}
			try {
				for (int h1 = 0; h1 < 6; h1++)
					headIconsHint[h1] = new Sprite(streamLoader_2, "headicons_hint", h1);
			} catch (Exception _ex) {
			}
			try {
				for (int j4 = 0; j4 < 8; j4++)
					headIcons[j4] = new Sprite(streamLoader_2, "headicons_prayer", j4);
				for (int j45 = 0; j45 < 3; j45++)
					skullIcons[j45] = new Sprite(streamLoader_2, "headicons_pk", j45);
			} catch (Exception _ex) {
			}
			mapFlag = new Sprite(streamLoader_2, "mapmarker", 0);
			mapMarker = new Sprite(streamLoader_2, "mapmarker", 1);
			for (int k4 = 0; k4 < 8; k4++)
				crosses[k4] = new Sprite(streamLoader_2, "cross", k4);
			mapDotItem = new Sprite(streamLoader_2, "mapdots", 0);
			mapDotNPC = new Sprite(streamLoader_2, "mapdots", 1);
			mapDotPlayer = new Sprite(streamLoader_2, "mapdots", 2);
			mapDotFriend = new Sprite(streamLoader_2, "mapdots", 3);
			mapDotTeam = new Sprite(streamLoader_2, "mapdots", 4);
			mapDotClan = new Sprite(streamLoader_2, "mapdots", 5);
			scrollBar1 = new Sprite(streamLoader_2, "scrollbar", 0);
			scrollBar2 = new Sprite(streamLoader_2, "scrollbar", 1);
			try {
				for (int l4 = 0; l4 < ClientConstants.ICON_AMOUNT; l4++) {
					modIcons[l4] = new Sprite(streamLoader_2, "mod_icons", l4);
				}
			} catch (Exception e) {
			}
			Sprite sprite = new Sprite(streamLoader_2, "screenframe", 0);
			leftFrame = new ImageProducer(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);
			sprite = new Sprite(streamLoader_2, "screenframe", 1);
			topFrame = new ImageProducer(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);
			int i5 = (int) (Math.random() * 21D) - 10;
			int j5 = (int) (Math.random() * 21D) - 10;
			int k5 = (int) (Math.random() * 21D) - 10;
			int l5 = (int) (Math.random() * 41D) - 20;
			for (int i6 = 0; i6 < 100; i6++) {
				if (mapFunctions[i6] != null)
					mapFunctions[i6].method344(i5 + l5, j5 + l5, k5 + l5);
				if (mapScenes[i6] != null)
					mapScenes[i6].method360(i5 + l5, j5 + l5, k5 + l5);
			}
			drawSmoothLoading(275, "Unpacking textures");
			Rasterizer.method368(streamLoader_3);
			Rasterizer.method372(0.80000000000000004D);
			Rasterizer.method367();
			drawSmoothLoading(325, "Unpacking config");
			Animation.unpackConfig(streamLoader);
			ObjectDef.unpackConfig(streamLoader);
			Floor.unpackConfig(streamLoader);
			OverlayFloor.unpackConfig(streamLoader);
			ItemDef.unpackConfig(streamLoader);
			EntityDef.unpackConfig(streamLoader);
			IdentityKit.unpackConfig(streamLoader);
			SpotAnim.unpackConfig(streamLoader);
			Varp.unpackConfig(streamLoader);
			VarBit.unpackConfig(streamLoader);
			ItemDef.isMembers = isMembers;
			drawSmoothLoading(450, "Unpacking interfaces");
			TextDrawingArea aclass30_sub2_sub1_sub4s[] = { smallText, regularText, boldText, aTextDrawingArea_1273 };
			RSInterface.unpack(streamLoader_1, aclass30_sub2_sub1_sub4s, streamLoader_2);
			drawSmoothLoading(550, "Preparing game engine");
			for (int j6 = 0; j6 < 33; j6++) {
				int k6 = 999;
				int i7 = 0;
				for (int k7 = 0; k7 < 34; k7++) {
					if (mapBack.aByteArray1450[k7 + j6 * mapBack.anInt1452] == 0) {
						if (k6 == 999)
							k6 = k7;
						continue;
					}
					if (k6 == 999)
						continue;
					i7 = k7;
					break;
				}
				anIntArray968[j6] = k6;
				anIntArray1057[j6] = i7 - k6;
			}
			for (int l6 = 1; l6 < 153; l6++) {
				int j7 = 999;
				int l7 = 0;
				for (int j8 = 24; j8 < 177; j8++) {
					if (mapBack.aByteArray1450[j8 + l6 * mapBack.anInt1452] == 0 && (j8 > 34 || l6 > 34)) {
						if (j7 == 999) {
							j7 = j8;
						}
						continue;
					}
					if (j7 == 999) {
						continue;
					}
					l7 = j8;
					break;
				}
				anIntArray1052[l6 - 1] = j7 - 24;
				anIntArray1229[l6 - 1] = l7 - j7;
			}
			setBounds();
			Censor.loadConfig(streamLoader_4);
			mouseDetection = new MouseDetection(this);
			startRunnable(mouseDetection, 10);
			Animable_Sub5.clientInstance = this;
			ObjectDef.clientInstance = this;
			EntityDef.clientInstance = this;
			AccountManager.loadAccount();
			return;
		} catch (Exception exception) {
			exception.printStackTrace();
			Signlink.reporterror("loaderror " + aString1049 + " " + anInt1079);
		}
		loadingError = true;
	}

	private void method91(Stream stream, int i) {
		while (stream.bitPosition + 10 < i * 8) {
			int j = stream.readBits(11);
			if (j == 2047)
				break;
			if (playerArray[j] == null) {
				playerArray[j] = new Player();
				if (aStreamArray895s[j] != null)
					playerArray[j].updatePlayer(aStreamArray895s[j]);
			}
			playerIndices[playerCount++] = j;
			Player player = playerArray[j];
			player.anInt1537 = loopCycle;
			int k = stream.readBits(1);
			if (k == 1)
				anIntArray894[anInt893++] = j;
			int l = stream.readBits(1);
			int i1 = stream.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = stream.readBits(5);
			if (j1 > 15)
				j1 -= 32;
			player.setPos(myPlayer.smallX[0] + j1, myPlayer.smallY[0] + i1, l == 1);
		}
		stream.finishBitAccess();
	}

	public String indexLocation(int cacheIndex, int index) {
		return "C:/Users/evan__000/Desktop/index" + cacheIndex + "/" + (index != -1 ? index + ".dat" : "");
	}

	public void repackCacheIndex(int cacheIndex) {
		System.out.println("Started repacking index " + cacheIndex + ".");
		int indexLength = new File(indexLocation(cacheIndex, -1)).listFiles().length;
		File[] file = new File(indexLocation(cacheIndex, -1)).listFiles();
		try {
			for (int index = 0; index < indexLength; index++) {
				int fileIndex = Integer.parseInt(getFileNameWithoutExtension(file[index].toString()));
				byte[] data = fileToByteArray(cacheIndex, fileIndex);
				if (data != null && data.length > 0) {
					decompressors[cacheIndex].method234(data.length, data, fileIndex);
					System.out.println("Repacked " + fileIndex + ".");
				} else {
					System.out.println("Unable to locate index " + fileIndex + ".");
				}
			}
		} catch (Exception e) {
			System.out.println("Error packing cache index " + cacheIndex + ".");
		}
		System.out.println("Finished repacking " + cacheIndex + ".");
	}

	public byte[] fileToByteArray(int cacheIndex, int index) {
		try {
			if (indexLocation(cacheIndex, index).length() <= 0 || indexLocation(cacheIndex, index) == null) {
				return null;
			}
			File file = new File(indexLocation(cacheIndex, index));
			byte[] fileData = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(fileData);
			fis.close();
			return fileData;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius) {
		return java.lang.Math.pow((circleX + radius - clickX), 2) + java.lang.Math.pow((circleY + radius - clickY), 2) < java.lang.Math.pow(radius, 2);
	}

	private void processMainScreenClick() {
		if (anInt1021 != 0)
			return;
		if (super.clickMode3 == 1) {
			int i = super.saveClickX - 25 - 547;
			int j = super.saveClickY - 5 - 3;
			if (frameMode != ScreenMode.FIXED) {
				i = super.saveClickX - (frameWidth - 182 + 24);
				j = super.saveClickY - 8;
			}

			if (inCircle(0, 0, i, j, 76) && mouseMapPosition() && !runHover) {
				i -= 73;
				j -= 75;
				int k = minimapInt1 + minimapInt2 & 0x7ff;
				int i1 = Rasterizer.anIntArray1470[k];
				int j1 = Rasterizer.anIntArray1471[k];
				i1 = i1 * (minimapInt3 + 256) >> 8;
				j1 = j1 * (minimapInt3 + 256) >> 8;
				int k1 = j * i1 + i * j1 >> 11;
				int l1 = j * j1 - i * i1 >> 11;
				int i2 = myPlayer.x + k1 >> 7;
				int j2 = myPlayer.y - l1 >> 7;
				if ((myPrivilege == 2 || myPrivilege == 3 || myPrivilege == 4) && controlIsDown) {
					teleport(baseX + i2, baseY + j2);
				} else {
					boolean flag1 = doWalkTo(1, 0, 0, 0, myPlayer.smallY[0], 0, 0, j2, myPlayer.smallX[0], true, i2);
					if (flag1) {
						stream.writeWordBigEndian(i);
						stream.writeWordBigEndian(j);
						stream.writeWord(minimapInt1);
						stream.writeWordBigEndian(57);
						stream.writeWordBigEndian(minimapInt2);
						stream.writeWordBigEndian(minimapInt3);
						stream.writeWordBigEndian(89);
						stream.writeWord(myPlayer.x);
						stream.writeWord(myPlayer.y);
						stream.writeWordBigEndian(anInt1264);
						stream.writeWordBigEndian(63);
					}
				}
				anInt1117++;
				if (anInt1117 > 1151) {
					anInt1117 = 0;
					stream.createFrame(246);
					stream.writeWordBigEndian(0);
					int l = stream.currentOffset;
					if ((int) (Math.random() * 2D) == 0)
						stream.writeWordBigEndian(101);
					stream.writeWordBigEndian(197);
					stream.writeWord((int) (Math.random() * 65536D));
					stream.writeWordBigEndian((int) (Math.random() * 256D));
					stream.writeWordBigEndian(67);
					stream.writeWord(14214);
					if ((int) (Math.random() * 2D) == 0)
						stream.writeWord(29487);
					stream.writeWord((int) (Math.random() * 65536D));
					if ((int) (Math.random() * 2D) == 0)
						stream.writeWordBigEndian(220);
					stream.writeWordBigEndian(180);
					stream.writeBytes(stream.currentOffset - l);
				}
			}
		}
	}

	private String interfaceIntToString(int j) {
		if (j < 0x3b9ac9ff)
			return String.valueOf(j);
		else
			return "*";
	}

	private void showErrorScreen() {
		Graphics g = getGameComponent().getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 765, 503);
		method4(1);
		if (loadingError) {
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 16));
			g.setColor(Color.yellow);
			int k = 35;
			g.drawString("Sorry, an error has occured whilst loading " + ClientConstants.CLIENT_NAME, 30, k);
			k += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
			k += 30;
			g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, k);
			k += 30;
			g.drawString("3: Try using a different game-world", 30, k);
			k += 30;
			g.drawString("4: Try rebooting your computer", 30, k);
			k += 30;
			g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, k);
		}
		if (genericLoadingError) {
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 20));
			g.setColor(Color.white);
			g.drawString("Error - unable to load game!", 50, 50);
			g.drawString("To play " + ClientConstants.CLIENT_NAME + " make sure you play from", 50, 100);
			g.drawString("http://www.UrlHere.com", 50, 150);
		}
		if (rsAlreadyLoaded) {
			aBoolean831 = false;
			g.setColor(Color.yellow);
			int l = 35;
			g.drawString("Error a copy of " + ClientConstants.CLIENT_NAME + " already appears to be loaded", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
			l += 30;
			g.drawString("2: Try rebooting your computer, and reloading", 30, l);
			l += 30;
		}
	}

	public URL getCodeBase() {
		try {
			return new URL(server + ":" + (80 + portOff));
		} catch (Exception _ex) {
		}
		return null;
	}

	private void method95() {
		for (int j = 0; j < npcCount; j++) {
			int k = npcIndices[j];
			Npc npc = npcArray[k];
			if (npc != null)
				method96(npc);
		}
	}

	private void method96(Entity entity) {
		if (entity.x < 128 || entity.y < 128 || entity.x >= 13184 || entity.y >= 13184) {
			entity.anim = -1;
			entity.anInt1520 = -1;
			entity.anInt1547 = 0;
			entity.anInt1548 = 0;
			entity.x = entity.smallX[0] * 128 + entity.anInt1540 * 64;
			entity.y = entity.smallY[0] * 128 + entity.anInt1540 * 64;
			entity.method446();
		}
		if (entity == myPlayer && (entity.x < 1536 || entity.y < 1536 || entity.x >= 11776 || entity.y >= 11776)) {
			entity.anim = -1;
			entity.anInt1520 = -1;
			entity.anInt1547 = 0;
			entity.anInt1548 = 0;
			entity.x = entity.smallX[0] * 128 + entity.anInt1540 * 64;
			entity.y = entity.smallY[0] * 128 + entity.anInt1540 * 64;
			entity.method446();
		}
		if (entity.anInt1547 > loopCycle) {
			method97(entity);
		} else if (entity.anInt1548 >= loopCycle) {
			method98(entity);
		} else {
			method99(entity);
		}
		method100(entity);
		method101(entity);
	}

	private void method97(Entity entity) {
		int i = entity.anInt1547 - loopCycle;
		int j = entity.anInt1543 * 128 + entity.anInt1540 * 64;
		int k = entity.anInt1545 * 128 + entity.anInt1540 * 64;
		entity.x += (j - entity.x) / i;
		entity.y += (k - entity.y) / i;
		entity.anInt1503 = 0;
		if (entity.anInt1549 == 0)
			entity.turnDirection = 1024;
		if (entity.anInt1549 == 1)
			entity.turnDirection = 1536;
		if (entity.anInt1549 == 2)
			entity.turnDirection = 0;
		if (entity.anInt1549 == 3)
			entity.turnDirection = 512;
	}

	private void method98(Entity entity) {
		if (entity.anInt1548 == loopCycle || entity.anim == -1 || entity.anInt1529 != 0 || entity.anInt1528 + 1 > Animation.anims[entity.anim].method258(entity.anInt1527)) {
			int i = entity.anInt1548 - entity.anInt1547;
			int j = loopCycle - entity.anInt1547;
			int k = entity.anInt1543 * 128 + entity.anInt1540 * 64;
			int l = entity.anInt1545 * 128 + entity.anInt1540 * 64;
			int i1 = entity.anInt1544 * 128 + entity.anInt1540 * 64;
			int j1 = entity.anInt1546 * 128 + entity.anInt1540 * 64;
			entity.x = (k * (i - j) + i1 * j) / i;
			entity.y = (l * (i - j) + j1 * j) / i;
		}
		entity.anInt1503 = 0;
		if (entity.anInt1549 == 0)
			entity.turnDirection = 1024;
		if (entity.anInt1549 == 1)
			entity.turnDirection = 1536;
		if (entity.anInt1549 == 2)
			entity.turnDirection = 0;
		if (entity.anInt1549 == 3)
			entity.turnDirection = 512;
		entity.anInt1552 = entity.turnDirection;
	}

	private void method99(Entity entity) {
		try {
			entity.anInt1517 = entity.anInt1511;
			if (entity.smallXYIndex == 0) {
				entity.anInt1503 = 0;
				return;
			}
			if (entity.anim != -1 && entity.anInt1529 == 0) {
				Animation animation = Animation.anims[entity.anim];
				if (entity.anInt1542 > 0 && animation.anInt363 == 0) {
					entity.anInt1503++;
					return;
				}
				if (entity.anInt1542 <= 0 && animation.anInt364 == 0) {
					entity.anInt1503++;
					return;
				}
			}
			int i = entity.x;
			int j = entity.y;
			int k = entity.smallX[entity.smallXYIndex - 1] * 128 + entity.anInt1540 * 64;
			int l = entity.smallY[entity.smallXYIndex - 1] * 128 + entity.anInt1540 * 64;
			if (k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256) {
				entity.x = k;
				entity.y = l;
				return;
			}
			if (i < k) {
				if (j < l)
					entity.turnDirection = 1280;
				else if (j > l)
					entity.turnDirection = 1792;
				else
					entity.turnDirection = 1536;
			} else if (i > k) {
				if (j < l)
					entity.turnDirection = 768;
				else if (j > l)
					entity.turnDirection = 256;
				else
					entity.turnDirection = 512;
			} else if (j < l)
				entity.turnDirection = 1024;
			else
				entity.turnDirection = 0;
			int i1 = entity.turnDirection - entity.anInt1552 & 0x7ff;
			if (i1 > 1024)
				i1 -= 2048;
			int j1 = entity.anInt1555;
			if (i1 >= -256 && i1 <= 256)
				j1 = entity.anInt1554;
			else if (i1 >= 256 && i1 < 768)
				j1 = entity.anInt1557;
			else if (i1 >= -768 && i1 <= -256)
				j1 = entity.anInt1556;
			if (j1 == -1)
				j1 = entity.anInt1554;
			entity.anInt1517 = j1;
			int k1 = 4;
			if (entity.anInt1552 != entity.turnDirection && entity.interactingEntity == -1 && entity.anInt1504 != 0)
				k1 = 2;
			if (entity.smallXYIndex > 2)
				k1 = 6;
			if (entity.smallXYIndex > 3)
				k1 = 8;
			if (entity.anInt1503 > 0 && entity.smallXYIndex > 1) {
				k1 = 8;
				entity.anInt1503--;
			}
			if (entity.aBooleanArray1553[entity.smallXYIndex - 1])
				k1 <<= 1;
			if (k1 >= 8 && entity.anInt1517 == entity.anInt1554 && entity.anInt1505 != -1)
				entity.anInt1517 = entity.anInt1505;
			if (i < k) {
				entity.x += k1;
				if (entity.x > k)
					entity.x = k;
			} else if (i > k) {
				entity.x -= k1;
				if (entity.x < k)
					entity.x = k;
			}
			if (j < l) {
				entity.y += k1;
				if (entity.y > l)
					entity.y = l;
			} else if (j > l) {
				entity.y -= k1;
				if (entity.y < l)
					entity.y = l;
			}
			if (entity.x == k && entity.y == l) {
				entity.smallXYIndex--;
				if (entity.anInt1542 > 0)
					entity.anInt1542--;
			}
		} catch(Exception e) {
			
		}
	}

	private void method100(Entity entity) {
		if (entity.anInt1504 == 0)
			return;
		if (entity.interactingEntity != -1 && entity.interactingEntity < 32768) {
			Npc npc = npcArray[entity.interactingEntity];
			if (npc != null) {
				int i1 = entity.x - npc.x;
				int k1 = entity.y - npc.y;
				if (i1 != 0 || k1 != 0)
					entity.turnDirection = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
			}
		}
		if (entity.interactingEntity >= 32768) {
			int j = entity.interactingEntity - 32768;
			if (j == unknownInt10)
				j = myPlayerIndex;
			Player player = playerArray[j];
			if (player != null) {
				int l1 = entity.x - player.x;
				int i2 = entity.y - player.y;
				if (l1 != 0 || i2 != 0)
					entity.turnDirection = (int) (Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff;
			}
		}
		if ((entity.anInt1538 != 0 || entity.anInt1539 != 0) && (entity.smallXYIndex == 0 || entity.anInt1503 > 0)) {
			int k = entity.x - (entity.anInt1538 - baseX - baseX) * 64;
			int j1 = entity.y - (entity.anInt1539 - baseY - baseY) * 64;
			if (k != 0 || j1 != 0)
				entity.turnDirection = (int) (Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff;
			entity.anInt1538 = 0;
			entity.anInt1539 = 0;
		}
		int l = entity.turnDirection - entity.anInt1552 & 0x7ff;
		if (l != 0) {
			if (l < entity.anInt1504 || l > 2048 - entity.anInt1504)
				entity.anInt1552 = entity.turnDirection;
			else if (l > 1024)
				entity.anInt1552 -= entity.anInt1504;
			else
				entity.anInt1552 += entity.anInt1504;
			entity.anInt1552 &= 0x7ff;
			if (entity.anInt1517 == entity.anInt1511 && entity.anInt1552 != entity.turnDirection) {
				if (entity.anInt1512 != -1) {
					entity.anInt1517 = entity.anInt1512;
					return;
				}
				entity.anInt1517 = entity.anInt1554;
			}
		}
	}

	private void method101(Entity entity) {
		try {
			if (entity.anInt1517 > 13798) {
				entity.anInt1517 = -1;
			}
			entity.aBoolean1541 = false;
			if (entity.anInt1517 != -1) {
				Animation animation = Animation.anims[entity.anInt1517];
				entity.anInt1519++;
				if (entity.anInt1518 < animation.anInt352 && entity.anInt1519 > animation.method258(entity.anInt1518)) {
					entity.anInt1519 = 1;
					entity.anInt1518++;
					entity.nextIdleAnimFrame++;
				}
				entity.nextIdleAnimFrame = entity.anInt1518 + 1;
				if (entity.nextIdleAnimFrame >= animation.anInt352) {
					if (entity.nextIdleAnimFrame >= animation.anInt352) {
						entity.nextIdleAnimFrame = 0;
					}
				}
				if (entity.anInt1518 >= animation.anInt352) {
					entity.anInt1519 = 1;
					entity.anInt1518 = 0;
				}
			}
			if (entity.anInt1520 != -1 && loopCycle >= entity.anInt1523) {
				if (entity.anInt1521 < 0)
					entity.anInt1521 = 0;
				Animation animation_1 = SpotAnim.cache[entity.anInt1520].aAnimation_407;
				for (entity.anInt1522++; entity.anInt1521 < animation_1.anInt352 && entity.anInt1522 > animation_1.method258(entity.anInt1521); entity.anInt1521++)
					entity.anInt1522 -= animation_1.method258(entity.anInt1521);

				if (entity.anInt1521 >= animation_1.anInt352 && (entity.anInt1521 < 0 || entity.anInt1521 >= animation_1.anInt352)) {
					entity.anInt1520 = -1;
				}
				if (Configuration.enableTweening) {
					entity.nextIdleAnimFrame = entity.anInt1518 + 1;
				}
				if (entity.nextSpotAnimFrame >= animation_1.anInt352) {
					entity.nextSpotAnimFrame = -1;
				}
			}
			if (entity.anim != -1 && entity.anInt1529 <= 1) {
				Animation animation_2 = Animation.anims[entity.anim];
				if (animation_2.anInt363 == 1 && entity.anInt1542 > 0 && entity.anInt1547 <= loopCycle && entity.anInt1548 < loopCycle) {
					entity.anInt1529 = 1;
					return;
				}
			}
			if (entity.anim != -1 && entity.anInt1529 == 0) {
				Animation animation_3 = Animation.anims[entity.anim];
				for (entity.anInt1528++; entity.anInt1527 < animation_3.anInt352 && entity.anInt1528 > animation_3.method258(entity.anInt1527); entity.anInt1527++)
					entity.anInt1528 -= animation_3.method258(entity.anInt1527);

				if (entity.anInt1527 >= animation_3.anInt352) {
					entity.anInt1527 -= animation_3.anInt356;
					entity.anInt1530++;
					if (entity.anInt1530 >= animation_3.anInt362)
						entity.anim = -1;
					if (entity.anInt1527 < 0 || entity.anInt1527 >= animation_3.anInt352)
						entity.anim = -1;
				}
				if (Configuration.enableTweening) {
					entity.nextAnimFrame = entity.anInt1527 + 1;
				}
				if (entity.nextAnimFrame >= animation_3.anInt352) {
					entity.nextAnimFrame = -1;
				}
				entity.aBoolean1541 = animation_3.aBoolean358;
			}
			if (entity.anInt1529 > 0)
				entity.anInt1529--;
		} catch (Exception e) {

		}
	}

	private void drawGameScreen() {
		if (fullscreenInterfaceID != -1 && (loadingStage == 2 || super.fullGameScreen != null)) {
			if (loadingStage == 2) {
				method119(anInt945, fullscreenInterfaceID);
				if (openInterfaceID != -1) {
					method119(anInt945, openInterfaceID);
				}
				anInt945 = 0;
				resetAllImageProducers();
				super.fullGameScreen.initDrawingArea();
				Rasterizer.anIntArray1472 = fullScreenTextureArray;
				DrawingArea.setAllPixelsToZero();
				welcomeScreenRaised = true;
				if (openInterfaceID != -1) {
					RSInterface rsInterface_1 = RSInterface.interfaceCache[openInterfaceID];
					if (rsInterface_1.width == 512 && rsInterface_1.height == 334 && rsInterface_1.type == 0) {
						rsInterface_1.width = 765;
						rsInterface_1.height = 503;
					}
					drawInterface(0, 0, rsInterface_1, 8);
				}
				RSInterface rsInterface = RSInterface.interfaceCache[fullscreenInterfaceID];
				if (rsInterface.width == 512 && rsInterface.height == 334 && rsInterface.type == 0) {
					rsInterface.width = 765;
					rsInterface.height = 503;
				}
				drawInterface(0, 0, rsInterface, 8);
				if (!menuOpen) {
					processRightClick();
					drawTooltip();
				} else {
					drawMenu(frameMode == ScreenMode.FIXED ? 4 : 0, frameMode == ScreenMode.FIXED ? 4 : 0);
				}
			}
			drawCount++;
			super.fullGameScreen.drawGraphics(0, super.graphics, 0);
			return;
		} else {
			if (drawCount != 0) {
				resetImageProducers2();
			}
		}
		if (welcomeScreenRaised) {
			welcomeScreenRaised = false;
			if (frameMode == ScreenMode.FIXED) {
				topFrame.drawGraphics(0, super.graphics, 0);
				leftFrame.drawGraphics(4, super.graphics, 0);
			}
			inputTaken = true;
			tabAreaAltered = true;
			if (loadingStage != 2) {
				if (frameMode == ScreenMode.FIXED) {
					aRSImageProducer_1165.drawGraphics(frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics, frameMode == ScreenMode.FIXED ? 4 : 0);
					aRSImageProducer_1164.drawGraphics(0, super.graphics, 516);
				}
			}
		}
		if (invOverlayInterfaceID != -1) {
			method119(anInt945, invOverlayInterfaceID);
		}
		drawTabArea();
		if (backDialogID == -1) {
			aClass9_1059.scrollPosition = anInt1211 - anInt1089 - 110;
			if (super.mouseX >= 496 && super.mouseX <= 511 && super.mouseY > (frameMode == ScreenMode.FIXED ? 345 : frameHeight - 158))
				method65(494, 110, super.mouseX, super.mouseY - (frameMode == ScreenMode.FIXED ? 345 : frameHeight - 158), aClass9_1059, 0, false, anInt1211);
			int i = anInt1211 - 110 - aClass9_1059.scrollPosition;
			if (i < 0) {
				i = 0;
			}
			if (i > anInt1211 - 110) {
				i = anInt1211 - 110;
			}
			if (anInt1089 != i) {
				anInt1089 = i;
				inputTaken = true;
			}
		}
		if (backDialogID != -1) {
			boolean flag2 = method119(anInt945, backDialogID);
			if (flag2)
				inputTaken = true;
		}
		if (atInventoryInterfaceType == 3)
			inputTaken = true;
		if (activeInterfaceType == 3)
			inputTaken = true;
		if (aString844 != null)
			inputTaken = true;
		if (menuOpen && menuScreenArea == 2)
			inputTaken = true;
		extendChatArea();
		if (inputTaken) {
			drawChatArea();
			inputTaken = false;
		}
		if (loadingStage == 2)
			method146();
		if (loadingStage == 2) {
			if (frameMode == ScreenMode.FIXED) {
				drawMinimap();
				aRSImageProducer_1164.drawGraphics(0, super.graphics, 516);
			}
		}
		if (anInt1054 != -1)
			tabAreaAltered = true;
		if (tabAreaAltered) {
			if (anInt1054 != -1 && anInt1054 == tabID) {
				anInt1054 = -1;
				stream.createFrame(120);
				stream.writeWordBigEndian(tabID);
			}
			tabAreaAltered = false;
			aRSImageProducer_1125.initDrawingArea();
			aRSImageProducer_1165.initDrawingArea();
		}
		anInt945 = 0;
	}

	private boolean buildFriendsListMenu(RSInterface class9) {
		int i = class9.contentType;
		if (i >= 1 && i <= 200 || i >= 701 && i <= 900) {
			if (i >= 801)
				i -= 701;
			else if (i >= 701)
				i -= 601;
			else if (i >= 101)
				i -= 101;
			else
				i--;
			menuActionName[menuActionRow] = "Remove @whi@" + friendsList[i];
			menuActionID[menuActionRow] = 792;
			menuActionRow++;
			menuActionName[menuActionRow] = "Message @whi@" + friendsList[i];
			menuActionID[menuActionRow] = 639;
			menuActionRow++;
			return true;
		}
		if (i >= 401 && i <= 500) {
			menuActionName[menuActionRow] = "Remove @whi@" + class9.disabledMessage;
			menuActionID[menuActionRow] = 322;
			menuActionRow++;
			return true;
		} else {
			return false;
		}
	}

	private void method104() {
		Animable_Sub3 class30_sub2_sub4_sub3 = (Animable_Sub3) aClass19_1056.reverseGetFirst();
		for (; class30_sub2_sub4_sub3 != null; class30_sub2_sub4_sub3 = (Animable_Sub3) aClass19_1056.reverseGetNext())
			if (class30_sub2_sub4_sub3.anInt1560 != plane || class30_sub2_sub4_sub3.aBoolean1567)
				class30_sub2_sub4_sub3.unlink();
			else if (loopCycle >= class30_sub2_sub4_sub3.anInt1564) {
				class30_sub2_sub4_sub3.method454(anInt945);
				if (class30_sub2_sub4_sub3.aBoolean1567)
					class30_sub2_sub4_sub3.unlink();
				else
					worldController.method285(class30_sub2_sub4_sub3.anInt1560, 0, class30_sub2_sub4_sub3.anInt1563, -1, class30_sub2_sub4_sub3.anInt1562, 60, class30_sub2_sub4_sub3.anInt1561, class30_sub2_sub4_sub3, false);
			}

	}

	public void drawBlackBox(int xPos, int yPos) {
		DrawingArea.drawPixels(71, yPos - 1, xPos - 2, 0x726451, 1);
		DrawingArea.drawPixels(69, yPos, xPos + 174, 0x726451, 1);
		DrawingArea.drawPixels(1, yPos - 2, xPos - 2, 0x726451, 178);
		DrawingArea.drawPixels(1, yPos + 68, xPos, 0x726451, 174);
		DrawingArea.drawPixels(71, yPos - 1, xPos - 1, 0x2E2B23, 1);
		DrawingArea.drawPixels(71, yPos - 1, xPos + 175, 0x2E2B23, 1);
		DrawingArea.drawPixels(1, yPos - 1, xPos, 0x2E2B23, 175);
		DrawingArea.drawPixels(1, yPos + 69, xPos, 0x2E2B23, 175);
		DrawingArea.method335(0, yPos, 174, 68, 220, xPos);
	}

	public void refreshScreenOptions() {
		int childIds[] = { 36004, 36007, 36010 };
		int enabledIds[] = { 1, 3, 5 };
		int disabledIds[] = { 0, 2, 4 };
		ScreenMode modes[] = { ScreenMode.FIXED, ScreenMode.RESIZABLE, ScreenMode.FULLSCREEN };
		for (int index = 0; index < modes.length; index++) {
			RSInterface.interfaceCache[childIds[index]].setSprite(Client.cacheSprite[frameMode == modes[index] ? enabledIds[index] : disabledIds[index]]);
		}
	}

	@SuppressWarnings("unused")
	private void drawInterface(int j, int k, RSInterface class9, int l) {
		refreshScreenOptions();
		if (class9.parentID == 197 && frameMode != ScreenMode.FIXED) {
			k = frameWidth - 120;
			l = 170;
		}
		if (class9.type != 0 || class9.children == null) {
			return;
		}
		if (class9.isMouseoverTriggered && anInt1026 != class9.id && anInt1048 != class9.id && anInt1039 != class9.id)
			return;
		int i1 = DrawingArea.topX;
		int j1 = DrawingArea.topY;
		int k1 = DrawingArea.bottomX;
		int l1 = DrawingArea.bottomY;
		DrawingArea.setDrawingArea(l + class9.height, k, k + class9.width, l);
		int i2 = class9.children.length;
		int alpha = class9.transparency;
		for (int j2 = 0; j2 < i2; j2++) {
			int k2 = class9.childX[j2] + k;
			int l2 = (class9.childY[j2] + l) - j;
			RSInterface child = RSInterface.interfaceCache[class9.children[j2]];
			k2 += child.anInt263;
			l2 += child.anInt265;
			if (child.contentType > 0)
				drawFriendsListOrWelcomeScreen(child);
			// here
			int[] IDs = { 1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274, 1283, 1573, 1290, 1299, 1308, 1315, 1324, 1333, 1340, 1349, 1358, 1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414, 1421, 1430, 1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478, 1485, 1494, 1503, 1512, 1521, 1530, 1544, 1553, 1563, 1593, 1635, 12426, 12436, 12446, 12456, 6004, 18471,
					/* Ancients */
					12940, 12988, 13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920, 12882, 13062, 12952, 13000, 13070, 12912, 12872, 13080, 12976, 13024, 13088, 12930, 12892, 13096 };
			for (int m5 = 0; m5 < IDs.length; m5++) {
				if (child.id == IDs[m5] + 1) {
					if (m5 > 61)
						drawBlackBox(k2 + 1, l2);
					else
						drawBlackBox(k2, l2 + 1);
				}
			}
			int[] runeChildren = { 1202, 1203, 1209, 1210, 1211, 1218, 1219, 1220, 1227, 1228, 1234, 1235, 1236, 1243, 1244, 1245, 1252, 1253, 1254, 1261, 1262, 1263, 1270, 1271, 1277, 1278, 1279, 1286, 1287, 1293, 1294, 1295, 1302, 1303, 1304, 1311, 1312, 1318, 1319, 1320, 1327, 1328, 1329, 1336, 1337, 1343, 1344, 1345, 1352, 1353, 1354, 1361, 1362, 1363, 1370, 1371, 1377, 1378, 1384, 1385, 1391, 1392, 1393, 1400, 1401, 1407, 1408, 1410, 1417, 1418, 1424, 1425, 1426, 1433, 1434, 1440, 1441, 1442, 1449, 1450, 1456, 1457, 1463, 1464, 1465, 1472, 1473, 1474, 1481, 1482, 1488, 1489, 1490, 1497, 1498, 1499, 1506, 1507, 1508, 1515, 1516, 1517, 1524, 1525, 1526, 1533, 1534, 1535, 1547, 1548, 1549, 1556, 1557, 1558, 1566, 1567, 1568, 1576, 1577, 1578, 1586, 1587, 1588, 1596, 1597, 1598, 1605,
					1606, 1607, 1616, 1617, 1618, 1627, 1628, 1629, 1638, 1639, 1640, 6007, 6008, 6011, 8673, 8674, 12041, 12042, 12429, 12430, 12431, 12439, 12440, 12441, 12449, 12450, 12451, 12459, 12460, 15881, 15882, 15885, 18474, 18475, 18478 };
			for (int r = 0; r < runeChildren.length; r++)
				if (child.id == runeChildren[r])
					child.modelZoom = 775;
			if (child.type == 0) {
				if (child.scrollPosition > child.scrollMax - child.height)
					child.scrollPosition = child.scrollMax - child.height;
				if (child.scrollPosition < 0)
					child.scrollPosition = 0;
				drawInterface(child.scrollPosition, k2, child, l2);
				if (child.id == 18143) {
					int clanMates = 0;
					for (int i = 18155; i < 18244; i++) {
						RSInterface line = RSInterface.interfaceCache[i];
						if (line.disabledMessage.length() > 0) {
							clanMates++;
						}
					}
					child.scrollMax = (clanMates * 14) + child.height + 1;
				}
				if (child.scrollMax > child.height)
					drawScrollbar(child.height, child.scrollPosition, l2, k2 + child.width, child.scrollMax, false);
			} else if (child.type != 1)
				if (child.type == 2) {
					int slot = 0;
					int tabAm = 0;
					int tabSlot = -1;
					int hh = 2;
					if (child.contentType == 206) {
						int tabHeight = 0;
						for (int i = 0; i < tabAmounts.length; i++) {
							if (tabSlot + 1 < tabAmounts.length && tabAmounts[tabSlot + 1] > 0) {
								tabAm += tabAmounts[++tabSlot];
								tabHeight += (tabAmounts[tabSlot] >> 3) + (tabAmounts[tabSlot] % 8 == 0 ? 0 : 1);
								if (tabSlot + 1 < tabAmounts.length && tabAmounts[tabSlot + 1] > 0 && variousSettings[1000] == 0 && variousSettings[1012] == 0) {
									DrawingArea.method339((l2 + tabHeight * (32 + child.invSpritePadY) + hh) - 1, 0x3E3529, ((32 + child.invSpritePadX) << 3) - 10, k2);
									DrawingArea.method339((l2 + tabHeight * (32 + child.invSpritePadY) + hh), 0x3E3529, ((32 + child.invSpritePadX) << 3) - 10, k2);
								}
								hh += 8;
							}

							if (i > 0) {
								int itemSlot = tabAm - tabAmounts[i];
								int xOffset = (frameWidth - 237 - RSInterface.interfaceCache[5292].width) / 2;
								int yOffset = 36 + ((frameHeight - 503) / 2);
								int x = xOffset + 77;
								int y = yOffset + 25;
								try {
									int item = RSInterface.interfaceCache[5382].inv[itemSlot];
									if (tabAmounts[i] > 0 && item > 0) {
										Sprite icon = null;
										if (variousSettings[1011] == 0) {
											icon = ItemDef.getSprite(item - 1, child.invStackSizes[itemSlot], 0);
										}
										if (variousSettings[1011] == 1) {
											icon = cacheSprite[118 + i];
										}
										if (variousSettings[1011] == 2) {
											icon = cacheSprite[127 + i];
										}
										if (icon != null) {
											icon.drawSprite1((frameMode == ScreenMode.FIXED ? 60 : x + 4) + 40 * i, (frameMode == ScreenMode.FIXED ? 41 : y + 2), 255, true);
										}
										RSInterface.interfaceCache[50013 + i * 4].anInt265 = 0;
										RSInterface.interfaceCache[50014 + i * 4].anInt265 = 0;
										RSInterface.interfaceCache[50014 + i * 4].tooltip = "View tab @or2@" + i;
										RSInterface.interfaceCache[50014 + i * 4].disabledSprite = cacheSprite[109];
									} else if (tabAmounts[i - 1] <= 0) {
										RSInterface.interfaceCache[50013 + i * 4].anInt265 = -500;
										if (i > 1) {
											RSInterface.interfaceCache[50014 + i * 4].anInt265 = -500;
										} else {
											cacheSprite[114].drawSprite1((frameMode == ScreenMode.FIXED ? 59 : x) + 40 * i, (frameMode == ScreenMode.FIXED ? 41 : y), 255, true);
										}
										RSInterface.interfaceCache[50014 + i * 4].tooltip = "New tab";
									} else {
										RSInterface.interfaceCache[50013 + i * 4].anInt265 = -500;
										RSInterface.interfaceCache[50014 + i * 4].anInt265 = 0;
										RSInterface.interfaceCache[50014 + i * 4].tooltip = "New tab";
										RSInterface.interfaceCache[50014 + i * 4].disabledSprite = cacheSprite[112];
										cacheSprite[114].drawSprite1((frameMode == ScreenMode.FIXED ? 59 : x) + 40 * i, (frameMode == ScreenMode.FIXED ? 41 : y), 255, true);
									}
								} catch (Exception e) {
									System.out.println("Bank tab icon error: tab [" + i + "], amount [" + tabAm + "], tabAmount [" + tabAmounts[i] + "], itemSlot [" + itemSlot + "]");
								}
							}
						}
						DrawingArea.bottomY += 3;
					}

					tabAm = tabAmounts[0];
					tabSlot = 0;
					hh = 0;

					int dragX = 0, dragY = 0;
					Sprite draggedItem = null;

					int newSlot = 0;
					if (child.contentType == 206 && variousSettings[1000] != 0 && variousSettings[1012] == 0) {
						for (int i = 0; i < tabAmounts.length; i++) {
							if (i == variousSettings[1000]) {
								break;
							}
							newSlot += tabAmounts[i];
						}
						slot = newSlot;
					}

					heightLoop: for (int height = 0; height < child.height; height++) {
						for (int width = 0; width < child.width; width++) {
							int w = k2 + width * (32 + child.invSpritePadX);
							int h = l2 + height * (32 + child.invSpritePadY) + hh;
							if (child.contentType == 206 && variousSettings[1012] == 0) {
								if (variousSettings[1000] == 0) {
									if (slot == tabAm) {
										if (tabSlot + 1 < tabAmounts.length) {
											tabAm += tabAmounts[++tabSlot];
											if (tabSlot > 0 && tabAmounts[tabSlot - 1] % 8 == 0) {
												height--;
											}
											hh += 8;
										}
										break;
									}
								} else if (variousSettings[1000] <= 9) {
									if (slot >= tabAmounts[variousSettings[1000]] + newSlot) {
										break heightLoop;
									}
								}
							}
							if (slot < 20) {
								w += child.spritesX[slot];
								h += child.spritesY[slot];
							}
							int itemId = child.inv[slot] - 1;
							if (variousSettings[1012] == 1 && child.contentType == 206) {
								itemId = bankInvTemp[slot] - 1;
							}
							if (itemId + 1 > 0) {
								if (child.id == 3900) {
									if (stock == null) {
										stock = cacheSprite[76];
									}
									stock.drawSprite(w - 7, h - 4);
								}
								int x = 0;
								int y = 0;
								if (w > DrawingArea.topX - 32 && w < DrawingArea.bottomX && h > DrawingArea.topY - 32 && h < DrawingArea.bottomY || activeInterfaceType != 0 && dragFromSlot == slot) {
									int color = 0;
									if (itemSelected == 1 && anInt1283 == slot && anInt1284 == child.id) {
										color = 0xffffff;
									}
									Sprite itemSprite = ItemDef.getSprite(itemId, variousSettings[1012] == 1 && child.contentType == 206 ? bankStackTemp[slot] : child.invStackSizes[slot], color);

									if (itemSprite != null) {
										if (activeInterfaceType != 0 && dragFromSlot == slot && focusedDragWidget == child.id) {
											draggedItem = itemSprite;
											x = super.mouseX - pressX;
											y = super.mouseY - pressY;
											if (x < 5 && x > -5)
												x = 0;
											if (y < 5 && y > -5)
												y = 0;
											if (dragCycle < 10) {
												x = 0;
												y = 0;
											}
											dragX = w + x;
											dragY = h + y;
											if (h + y < DrawingArea.topY && class9.scrollPosition > 0) {
												int i10 = (anInt945 * (DrawingArea.topY - h - y)) / 3;
												if (i10 > anInt945 * 10)
													i10 = anInt945 * 10;
												if (i10 > class9.scrollPosition)
													i10 = class9.scrollPosition;
												class9.scrollPosition -= i10;
												pressY += i10;
											}

											if (h + y + 32 > DrawingArea.bottomY && class9.scrollPosition < class9.scrollMax - class9.height) {
												int j10 = (anInt945 * ((h + y + 32) - DrawingArea.bottomY)) / 3;
												if (j10 > anInt945 * 10)
													j10 = anInt945 * 10;
												if (j10 > class9.scrollMax - class9.height - class9.scrollPosition)
													j10 = class9.scrollMax - class9.height - class9.scrollPosition;
												class9.scrollPosition += j10;
												pressY -= j10;
											}
										} else if (atInventoryInterfaceType != 0 && atInventoryIndex == slot && atInventoryInterface == child.id) {
											itemSprite.drawSprite1(w, h);
										} else {
											itemSprite.drawSprite(w, h);
										}
										if (child.parentID != 42752) {
											if (itemSprite.cropWidth == 33 || child.invStackSizes[slot] != 1) {
												int amount = child.invStackSizes[slot];

												if (amount == 0) {
													smallText.method385(0, "EMPTY", h + 45 + y, w + 3 + x);
													smallText.method385(0xFFFFFF, "EMPTY", h + 44 + y, w + 2 + x);
												} else if (amount >= 1000000000) {
													smallText.method385(0, intToKOrMil(amount), h + 10 + y, w + x + 1);
													smallText.method385(0x00FF80, intToKOrMil(amount), h + 9 + y, w + x);
												} else if (amount >= 10000000) {
													smallText.method385(0, intToKOrMil(amount), h + 10 + y, w + x + 1);
													smallText.method385(0x00FF80, intToKOrMil(amount), h + 9 + y, w + x);
												} else if (amount >= 100000) {
													smallText.method385(0, intToKOrMil(amount), h + 10 + y, w + x + 1);
													smallText.method385(0xFFFFFF, intToKOrMil(amount), h + 9 + y, w + x);
												} else if (amount >= 1) {
													smallText.method385(0, intToKOrMil(amount), h + 10 + y, w + x + 1);
													smallText.method385(0xFFFF00, intToKOrMil(amount), h + 9 + y, w + x);
												} else {
													smallText.method385(0, intToKOrMil(amount), h + 10 + y, w + 1 + x);
													smallText.method385(0xFFFF00, intToKOrMil(amount), h + 9 + y, w + x);
												}
											}
										}
									}
								}
							} else if (child.sprites != null && slot < 20) {
								Sprite childSprite = child.sprites[slot];
								if (childSprite != null)
									childSprite.drawSprite(w, h);
							}
							slot++;
						}
					}
					if (draggedItem != null) {
						draggedItem.drawSprite1(dragX, dragY, 200 + (int) (50 * Math.sin(loopCycle / 10.0)), child.contentType == 206);
					}
				} else if (child.type == 3) {
					boolean flag = false;
					if (anInt1039 == child.id || anInt1048 == child.id || anInt1026 == child.id)
						flag = true;
					int color;
					if (interfaceIsSelected(child)) {
						color = child.anInt219;
						if (flag && child.anInt239 != 0)
							color = child.anInt239;
					} else {
						color = child.textColor;
						if (flag && child.textHoverColor != 0)
							color = child.textHoverColor;
					}
					if (child.opacity == 0) {
						if (child.aBoolean227)
							DrawingArea.drawPixels(child.height, l2, k2, color, child.width);
						else
							DrawingArea.fillPixels(k2, child.width, child.height, color, l2);
					} else if (child.aBoolean227)
						DrawingArea.method335(color, l2, child.width, child.height, 256 - (child.opacity & 0xff), k2);
					else
						DrawingArea.method338(l2, child.height, 256 - (child.opacity & 0xff), color, child.width, k2);
				} else if (child.type == 4) {
					TextDrawingArea textDrawingArea = child.textDrawingAreas;
					String message = child.disabledMessage;
					boolean hovered = false;
					if (anInt1039 == child.id || anInt1048 == child.id || anInt1026 == child.id)
						hovered = true;
					int color;
					if (interfaceIsSelected(child)) {
						color = child.anInt219;
						if (hovered && child.anInt239 != 0)
							color = child.anInt239;
						if (child.enabledMessage.length() > 0)
							message = child.enabledMessage;
					} else {
						color = child.textColor;
						if (hovered && child.textHoverColor != 0)
							color = child.textHoverColor;
					}
					if (child.atActionType == 6 && aBoolean1149) {
						message = "Please wait...";
						color = child.textColor;
					}
					if (child.id >= 28000 && child.id < 28036) {
						if (RSInterface.interfaceCache[3900].invStackSizes[child.id - 28000] > 0) {
							String[] data = message.split(",");
							int currency = 0;
							if (data.length > 1) {
								currency = Integer.parseInt(data[1]);
							}
							if (currencyImage[currency] == null) {
								currencyImage[currency] = cacheSprite[407 + currency];
							}
							currencyImage[currency].drawSprite(k2 - 5, l2);
							int value = Integer.parseInt(data[0]);
							if (value >= 10_000_000) {
								smallText.drawRightAlignedString((value / 1_000_000) + "M", k2 + 36, l2 + 1 + DrawingArea.height, 0);
								smallText.drawRightAlignedString((value / 1_000_000) + "M", k2 + 35, l2 + DrawingArea.height, 0x00ff80);
							} else if (value >= 1_000_000) {
								smallText.drawRightAlignedString(String.valueOf(String.format("%.1f", (value / 1_000_000.0))).replace(".0", "") + "M", k2 + 36, l2 + 1 + DrawingArea.height, 0);
								smallText.drawRightAlignedString(String.valueOf(String.format("%.1f", (value / 1_000_000.0))).replace(".0", "") + "M", k2 + 35, l2 + DrawingArea.height, 0xFFFFFF);
							} else if (value >= 100_000) {
								smallText.drawRightAlignedString((value / 1_000) + "K", k2 + 36, l2 + 1 + DrawingArea.height, 0);
								smallText.drawRightAlignedString((value / 1_000) + "K", k2 + 35, l2 + DrawingArea.height, 0xFFFFFF);
							} else if (value >= 10_000) {
								smallText.drawRightAlignedString((value / 1_000) + "K", k2 + 36, l2 + 1 + DrawingArea.height, 0);
								smallText.drawRightAlignedString((value / 1_000) + "K", k2 + 35, l2 + DrawingArea.height, 0xffff00);
							} else if (value <= 0) {
								smallText.drawRightAlignedString("FREE", k2 + 34, l2 + 1 + DrawingArea.height, 0);
								smallText.drawRightAlignedString("FREE", k2 + 33, l2 + DrawingArea.height, 0xffff00);
							} else {
								smallText.drawRightAlignedString(value + "", k2 + 36, l2 + 1 + DrawingArea.height, 0);
								smallText.drawRightAlignedString(value + "", k2 + 35, l2 + DrawingArea.height, 0xFFFF00);
							}
						}
						continue;
					}
					if ((backDialogID != -1 || dialogID != -1 || child.disabledMessage.contains("Click here to continue")) && (class9.id == backDialogID || class9.id == dialogID)) {
						if (color == 0xffff00) {
							color = 255;
						}
						if (color == 49152) {
							color = 0xffffff;
						}
					}
					if((child.parentID == 1151) || (child.parentID == 12855)) {
						switch (color) {
							case 16773120: 
								color = 0xFE981F;
								break;
							case 7040819: 
								color = 0xAF6A1A; 
								break;
						}
					}
					for (int l6 = l2 + textDrawingArea.anInt1497; message.length() > 0; l6 += textDrawingArea.anInt1497) {
						if (message.indexOf("%") != -1) {
							do {
								int k7 = message.indexOf("%1");
								if (k7 == -1)
									break;
								if (child.id < 4000 || child.id > 5000 && child.id != 13921 && child.id != 13922 && child.id != 12171 && child.id != 12172)
									message = message.substring(0, k7) + methodR(extractInterfaceValues(child, 0)) + message.substring(k7 + 2);
								else
									message = message.substring(0, k7) + interfaceIntToString(extractInterfaceValues(child, 0)) + message.substring(k7 + 2);
							} while (true);
							do {
								int l7 = message.indexOf("%2");
								if (l7 == -1)
									break;
								message = message.substring(0, l7) + interfaceIntToString(extractInterfaceValues(child, 1)) + message.substring(l7 + 2);
							} while (true);
							do {
								int i8 = message.indexOf("%3");
								if (i8 == -1)
									break;
								message = message.substring(0, i8) + interfaceIntToString(extractInterfaceValues(child, 2)) + message.substring(i8 + 2);
							} while (true);
							do {
								int j8 = message.indexOf("%4");
								if (j8 == -1)
									break;
								message = message.substring(0, j8) + interfaceIntToString(extractInterfaceValues(child, 3)) + message.substring(j8 + 2);
							} while (true);
							do {
								int k8 = message.indexOf("%5");
								if (k8 == -1)
									break;
								message = message.substring(0, k8) + interfaceIntToString(extractInterfaceValues(child, 4)) + message.substring(k8 + 2);
							} while (true);
						}
						int l8 = message.indexOf("\\n");
						String s1;
						if (l8 != -1) {
							s1 = message.substring(0, l8);
							message = message.substring(l8 + 2);
						} else {
							s1 = message;
							message = "";
						}
						RSFont font;
						if (textDrawingArea == smallText) {
							font = newSmallFont;
						} else if (textDrawingArea == regularText) {
							font = newRegularFont;
						} else if (textDrawingArea == boldText) {
							font = newBoldFont;
						} else {
							font = newFancyFont;
						}

						if (child.centerText) {
							font.drawCenteredString(s1, k2 + child.width / 2, l6, color, child.textShadow ? 0 : -1);
						} else {
							font.drawBasicString(s1, k2, l6, color, child.textShadow ? 0 : -1);
						}
					}
				} else if (child.type == 5) {
					Sprite sprite;
					if (interfaceIsSelected(child))
						sprite = child.enabledSprite;
					else
						sprite = child.disabledSprite;
					if (spellSelected == 1 && child.id == spellID && spellID != 0 && sprite != null) {
						sprite.drawSprite(k2, l2, 0xffffff);
						if (child.drawsTransparent) {
							sprite.drawTransparentSprite(k2, l2, 170);
						} else {
							sprite.drawSprite(k2, l2);
						}
					} else {
						if (sprite != null)
							if (child.drawsTransparent) {
								sprite.drawTransparentSprite(k2, l2, 170);
							} else {
								// System.out.println("Hi");
								sprite.drawSprite(k2, l2);
							}
					}
				} else if (child.type == 6) {
					int k3 = Rasterizer.centerX;
					int j4 = Rasterizer.centerY;
					Rasterizer.centerX = k2 + child.width / 2;
					Rasterizer.centerY = l2 + child.height / 2;
					int i5 = Rasterizer.anIntArray1470[child.modelRotation1] * child.modelZoom >> 16;
					int l5 = Rasterizer.anIntArray1471[child.modelRotation1] * child.modelZoom >> 16;
					boolean flag2 = interfaceIsSelected(child);
					int i7;
					if (flag2)
						i7 = child.anInt258;
					else
						i7 = child.anInt257;
					Model model;
					if (i7 == -1) {
						model = child.method209(-1, -1, flag2);
					} else {
						Animation animation = Animation.anims[i7];
						model = child.method209(animation.anIntArray354[child.anInt246], animation.anIntArray353[child.anInt246], flag2);
					}
					if (model != null)
						model.method482(child.modelRotation2, 0, child.modelRotation1, 0, i5, l5);
					Rasterizer.centerX = k3;
					Rasterizer.centerY = j4;
				} else if (child.type == 7) {
					TextDrawingArea textDrawingArea_1 = child.textDrawingAreas;
					int k4 = 0;
					for (int j5 = 0; j5 < child.height; j5++) {
						for (int i6 = 0; i6 < child.width; i6++) {
							if (child.inv[k4] > 0) {
								ItemDef itemDef = ItemDef.forID(child.inv[k4] - 1);
								String s2 = itemDef.name;
								if (itemDef.stackable || child.invStackSizes[k4] != 1)
									s2 = s2 + " x" + intToKOrMilLongName(child.invStackSizes[k4]);
								int i9 = k2 + i6 * (115 + child.invSpritePadX);
								int k9 = l2 + j5 * (12 + child.invSpritePadY);
								if (child.centerText)
									textDrawingArea_1.method382(child.textColor, i9 + child.width / 2, s2, k9, child.textShadow);
								else
									textDrawingArea_1.method389(child.textShadow, i9, child.textColor, s2, k9);
							}
							k4++;
						}
					}
				} else if (child.type == 9) {
					drawTooltip(k2, l2, child.popupString);
				} else if (child.type == 8 && (anInt1500 == child.id || anInt1044 == child.id || anInt1129 == child.id) && anInt1501 == 0 && !menuOpen) {
					int boxWidth = 0;
					int boxHeight = 0;
					String finalMessage = child.disabledMessage;
					TextDrawingArea textDrawingArea_2 = regularText;

					if (child.parentID == 3917 && extractInterfaceValues(child, 1) >= 99) {
						String[] msg = finalMessage.split("\\\\n");
						finalMessage = msg[0].concat("\\n").concat(msg[1]);
					}

					int skillId = child.parentID == 3917 ? finalMessage.split(":")[0].equals("Total level") ? 25 : Skills.getIdByName(finalMessage.split(":")[0]) : 0;

					boolean showGoal = child.parentID == 3917 ? statsSkillGoal[skillId][0] > 0 : false;

					int currentStats = 0;

					if (showGoal) {
						int remainder = 0;
						if (skillId == 25) {
							int exp = 0;
							for (int i = 0; i < Skills.SKILLS_COUNT; i++)
								if (Skills.SKILL_ENABLED[i])
									exp += currentExp[i];
							currentStats = exp;
							remainder = statsSkillGoal[skillId][0] - exp;
						} else {
							currentStats = currentExp[skillId];
							remainder = statsSkillGoal[skillId][1] == 1 ? (statsSkillGoal[skillId][0] - currentExp[skillId]) : (Skills.EXP_FOR_LEVEL[statsSkillGoal[skillId][0] - 2] - currentExp[skillId]);
						}
						if (remainder < 0) {
							remainder = 0;
						}
						finalMessage = finalMessage.concat("\\n");
						finalMessage = finalMessage.concat("Target " + (statsSkillGoal[skillId][1] == 1 ? " XP: " : "level: ") + NumberFormat.getInstance(Locale.US).format(statsSkillGoal[skillId][0]));
						finalMessage = finalMessage.concat("\\n");
						finalMessage = finalMessage.concat("Remainder: " + NumberFormat.getInstance(Locale.US).format(remainder));
						boxHeight += 14;
					}

					for (String s1 = finalMessage; s1.length() > 0;) {
						if (s1.indexOf("%") != -1) {
							do {
								int k7 = s1.indexOf("%1");
								if (k7 == -1)
									break;
								s1 = s1.substring(0, k7) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 0)) + s1.substring(k7 + 2);
							} while (true);
							do {
								int l7 = s1.indexOf("%2");
								if (l7 == -1)
									break;
								s1 = s1.substring(0, l7) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 1)) + s1.substring(l7 + 2);
							} while (true);
							do {
								int i8 = s1.indexOf("%3");
								if (i8 == -1)
									break;
								s1 = s1.substring(0, i8) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 2)) + s1.substring(i8 + 2);
							} while (true);
							do {
								int j8 = s1.indexOf("%4");
								if (j8 == -1)
									break;
								s1 = s1.substring(0, j8) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 3)) + s1.substring(j8 + 2);
							} while (true);
							do {
								int k8 = s1.indexOf("%5");
								if (k8 == -1)
									break;
								s1 = s1.substring(0, k8) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 4)) + s1.substring(k8 + 2);
							} while (true);
						}
						int l7 = s1.indexOf("\\n");
						String s4;
						if (l7 != -1) {
							s4 = s1.substring(0, l7);
							s1 = s1.substring(l7 + 2);
						} else {
							s4 = s1;
							s1 = "";
						}
						int j10 = textDrawingArea_2.getTextWidth(s4);
						if (j10 > boxWidth) {
							boxWidth = j10;
						}
						boxHeight += textDrawingArea_2.anInt1497 + 2;
					}
					boxWidth += 6;
					boxHeight += 7;

					int xPos = (k2 + child.width) - 5 - boxWidth;
					int yPos = l2 + child.height + 5;

					if (xPos < k2 + 5) {
						xPos = k2 + 5;
					}

					if (xPos + boxWidth > k + class9.width) {
						xPos = (k + class9.width) - boxWidth;
					}
					if (yPos + boxHeight > l + class9.height) {
						yPos = (l2 - boxHeight);
					}

					if (frameMode == ScreenMode.FIXED) {
						if (skillHoverIds(child.id) == child.id && xPos + boxWidth + k + class9.width > 765) {
							xPos = 765 - boxWidth - k - class9.width - 3;
						}
					} else {
						if (skillHoverIds(child.id) == child.id && xPos + boxWidth > frameWidth) {
							xPos = frameWidth - boxWidth - 15;
						}
					}
					if (skillHoverIds(child.id) == child.id && yPos + boxHeight > frameHeight - (frameMode == ScreenMode.FIXED ? yPos + boxHeight - 118 : (frameWidth <= 1000 ? 75 : 35))) {
						yPos -= boxHeight + 35;
					}

					DrawingArea.drawPixels(boxHeight, yPos, xPos, 0xFFFFA0, boxWidth);
					DrawingArea.fillPixels(xPos, boxWidth, boxHeight, 0, yPos);

					if (showGoal) {
						int goal = statsSkillGoal[skillId][1] == 1 ? statsSkillGoal[skillId][0] : Skills.EXP_FOR_LEVEL[statsSkillGoal[skillId][0] - 2];
						int init = statsSkillGoal[skillId][2];
						double percentage = ((currentStats - init) / (double) (goal - init));
						if (percentage > 1) {
							percentage = 1;
						}

						DrawingArea.drawPixels(10, (yPos + boxHeight) - 16, xPos + 5, 0xFF0000, boxWidth - 10);
						DrawingArea.drawPixels(10, (yPos + boxHeight) - 16, xPos + 5, 0x00FF00, (int) ((boxWidth - 10) * percentage));

						DrawingArea.fillPixels(xPos + 4, boxWidth - 8, 12, 0, (yPos + boxHeight) - 17);

						newSmallFont.drawCenteredString((int) (percentage * 100) + "%", xPos + 3 + (boxWidth / 2), (yPos + boxHeight) - 6, 0, -1);
					}

					String s2 = finalMessage;
					for (int j11 = yPos + textDrawingArea_2.anInt1497 + 3; s2.length() > 0; j11 += textDrawingArea_2.anInt1497 + 2) {// anInt1497
						if (s2.indexOf("%") != -1) {
							do {
								int k7 = s2.indexOf("%1");
								if (k7 == -1)
									break;
								s2 = s2.substring(0, k7) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 0)) + s2.substring(k7 + 2);
							} while (true);
							do {
								int l7 = s2.indexOf("%2");
								if (l7 == -1)
									break;
								s2 = s2.substring(0, l7) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 1)) + s2.substring(l7 + 2);
							} while (true);
							do {
								int i8 = s2.indexOf("%3");
								if (i8 == -1)
									break;
								s2 = s2.substring(0, i8) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 2)) + s2.substring(i8 + 2);
							} while (true);
							do {
								int j8 = s2.indexOf("%4");
								if (j8 == -1)
									break;
								s2 = s2.substring(0, j8) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 3)) + s2.substring(j8 + 2);
							} while (true);
							do {
								int k8 = s2.indexOf("%5");
								if (k8 == -1)
									break;
								s2 = s2.substring(0, k8) + NumberFormat.getInstance(Locale.US).format(extractInterfaceValues(child, 4)) + s2.substring(k8 + 2);
							} while (true);
						}
						int l11 = s2.indexOf("\\n");
						String s5;
						if (l11 != -1) {
							s5 = s2.substring(0, l11);
							s2 = s2.substring(l11 + 2);
						} else {
							s5 = s2;
							s2 = "";
						}
						if (child.centerText) {
							textDrawingArea_2.method382(yPos, xPos + child.width / 2, s5, j11, false);
						} else {
							if (s5.contains("\\r")) {
								String text = s5.substring(0, s5.indexOf("\\r"));
								String text2 = s5.substring(s5.indexOf("\\r") + 2);
								textDrawingArea_2.method389(false, xPos + 3, 0, text, j11);
								int rightX = boxWidth + xPos - textDrawingArea_2.getTextWidth(text2) - 2;
								textDrawingArea_2.method389(false, rightX, 0, text2, j11);
								System.out.println("Box: " + boxWidth + "");
							} else {
								if (s5.contains(":") && !s5.contains("/") && child.parentID == 3917) {
									String[] result = s5.split(":");
									textDrawingArea_2.method389(false, xPos + 3, 0, result[0] + ":", j11);
									textDrawingArea_2.method389(false, xPos + boxWidth - textDrawingArea_2.getTextWidth(result[1]) - 3, 0, result[1], j11);
								} else {
									textDrawingArea_2.method389(false, xPos + 3, 0, s5, j11);
								}
							}
						}
					}
				} else if (child.type == 16) {
					int x = frameWidth - child.width - k2;
					int y = frameHeight - class9.height + l2;

					boolean hover = false;

					int xx = class9.childX[j2] + (frameMode == ScreenMode.FIXED ? 0 : 0);
					int yy = class9.childY[j2] + (frameMode == ScreenMode.FIXED ? 0 : 0);

					if (tabInterfaceIDs[tabID] == child.parentID) {
						xx = frameWidth - 197;
						yy = frameWidth >= 1000 ? frameHeight - 280 : frameHeight - 262 + yy - j;
					}
					
					if (super.mouseX >= xx && super.mouseX <= xx + child.width && super.mouseY >= yy && super.mouseY <= yy + child.height) {
						hover = true;
					}

					if (super.saveClickX >= xx && super.saveClickX <= xx + child.width && super.saveClickY >= yy && super.saveClickY <= yy + child.height) {
						if (RSInterface.currentInputField != child) {
							if (super.clickMode2 == 1 && !menuOpen) {
								RSInterface.currentInputField = child;
							}
						}
					}

					int color;

					if (RSInterface.currentInputField == child) {
						color = child.anInt219;

						if (hover) {
							color = child.anInt239;
						}
					} else {
						color = child.textColor;

						if (hover) {
							color = child.textHoverColor;
						}
					}

					DrawingArea.drawPixels(child.height, l2, k2, color, child.width);
					DrawingArea.fillPixels(k2, child.width, child.height, 0, l2);
					
					x = k2;
					y = l2;

					StringBuilder builder = new StringBuilder();
					
					String message = child.disabledMessage;
					
					if (child.enabledMessage != null && RSInterface.currentInputField != child) {
						message = child.enabledMessage;
					}

					if (child.displayAsterisks) {
						boldText.method389(true, (x + 8), 0xFFFFFF, builder.append(TextClass.passwordAsterisks(message)).append(((RSInterface.currentInputField == child ? 1 : 0) & (loopCycle % 40 < 20 ? 1 : 0)) != 0 ? "|" : "").toString(), (y + (child.height / 2) + 6));
					} else {
						boldText.method389(true, (x + 8), 0xFFFFFF, builder.append(message).append(((RSInterface.currentInputField == child ? 1 : 0) & (loopCycle % 40 < 20 ? 1 : 0)) != 0 ? "|" : "").toString(), (y + (child.height / 2) + 6));
					}
				}
		}
		DrawingArea.setDrawingArea(l1, i1, k1, j1);
	}

	public int skillHoverIds(int ids) {
		int[] hoverIds = { 24138, 24139, 24140, 24141, 24142, 24143, 24144, 24145, 24146, 24147, 24148, 24149, 24150, 24151, 24152, 24153, 24154, 24155, 24156, 24157, 24158, 24159, 24160, 24161 };
		for (int hover = 0; hover < hoverIds.length; hover++) {
			if (hoverIds[hover] == ids) {
				ids = hover;
				return hoverIds[ids];
			}
		}
		return 0;
	}

	private void randomizeBackground(Background background) {
		int j = 256;
		for (int k = 0; k < anIntArray1190.length; k++)
			anIntArray1190[k] = 0;

		for (int l = 0; l < 5000; l++) {
			int i1 = (int) (Math.random() * 128D * (double) j);
			anIntArray1190[i1] = (int) (Math.random() * 256D);
		}
		for (int j1 = 0; j1 < 20; j1++) {
			for (int k1 = 1; k1 < j - 1; k1++) {
				for (int i2 = 1; i2 < 127; i2++) {
					int k2 = i2 + (k1 << 7);
					anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128] + anIntArray1190[k2 + 128]) / 4;
				}

			}
			int ai[] = anIntArray1190;
			anIntArray1190 = anIntArray1191;
			anIntArray1191 = ai;
		}
		if (background != null) {
			int l1 = 0;
			for (int j2 = 0; j2 < background.anInt1453; j2++) {
				for (int l2 = 0; l2 < background.anInt1452; l2++)
					if (background.aByteArray1450[l1++] != 0) {
						int i3 = l2 + 16 + background.anInt1454;
						int j3 = j2 + 16 + background.anInt1455;
						int k3 = i3 + (j3 << 7);
						anIntArray1190[k3] = 0;
					}
			}
		}
	}

	private void appendPlayerUpdateMask(int i, int j, Stream stream, Player player) {
		if ((i & 0x400) != 0) {
			player.anInt1543 = stream.method428();
			player.anInt1545 = stream.method428();
			player.anInt1544 = stream.method428();
			player.anInt1546 = stream.method428();
			player.anInt1547 = stream.method436() + loopCycle;
			player.anInt1548 = stream.method435() + loopCycle;
			player.anInt1549 = stream.method428();
			player.method446();
		}
		if ((i & 0x100) != 0) {
			player.anInt1520 = stream.method434();
			int k = stream.readDWord();
			player.anInt1524 = k >> 16;
			player.anInt1523 = loopCycle + (k & 0xffff);
			player.anInt1521 = 0;
			player.anInt1522 = 0;
			if (player.anInt1523 > loopCycle)
				player.anInt1521 = -1;
			if (player.anInt1520 == 65535)
				player.anInt1520 = -1;
		}
		if ((i & 8) != 0) {
			int l = stream.method434();
			if (l == 65535)
				l = -1;
			int i2 = stream.method427();
			if (l == player.anim && l != -1) {
				int i3 = Animation.anims[l].anInt365;
				if (i3 == 1) {
					player.anInt1527 = 0;
					player.anInt1528 = 0;
					player.anInt1529 = i2;
					player.anInt1530 = 0;
				}
				if (i3 == 2)
					player.anInt1530 = 0;
			} else if (l == -1 || player.anim == -1 || Animation.anims[l].anInt359 >= Animation.anims[player.anim].anInt359) {
				player.anim = l;
				player.anInt1527 = 0;
				player.anInt1528 = 0;
				player.anInt1529 = i2;
				player.anInt1530 = 0;
				player.anInt1542 = player.smallXYIndex;
			}
		}
		if ((i & 4) != 0) {
			player.textSpoken = stream.readString();
			if (player.textSpoken.charAt(0) == '~') {
				player.textSpoken = player.textSpoken.substring(1);
				pushMessage(player.textSpoken, 2, player.name, player.title, player.titleColor);
			} else if (player == myPlayer)
				pushMessage(player.textSpoken, 2, player.name, player.title, player.titleColor);
			player.anInt1513 = 0;
			player.anInt1531 = 0;
			player.textCycle = 150;
		}
		if ((i & 0x80) != 0) {
			// right fucking here
			int i1 = stream.method434();
			int j2 = stream.readUnsignedByte();
			int j3 = stream.method427();
			int k3 = stream.currentOffset;
			if (player.name != null && player.visible) {
				long l3 = TextClass.longForName(player.name);
				boolean flag = false;
				if (j2 <= 1) {
					for (int i4 = 0; i4 < ignoreCount; i4++) {
						if (ignoreListAsLongs[i4] != l3)
							continue;
						flag = true;
						break;
					}

				}
				if (!flag && anInt1251 == 0)
					try {
						aStream_834.currentOffset = 0;
						stream.method442(j3, 0, aStream_834.buffer);
						aStream_834.currentOffset = 0;
						String s = TextInput.method525(j3, aStream_834);
						// s = Censor.doCensor(s);
						player.textSpoken = s;
						player.anInt1513 = i1 >> 8;
						player.privelage = j2;
						player.anInt1531 = i1 & 0xff;
						player.textCycle = 150;
						if (j2 >= 1)
							pushMessage(s, 1, "@cr" + j2 + "@" + player.name, player.title, player.titleColor);
						else
							pushMessage(s, 2, player.name, player.title, player.titleColor);
					} catch (Exception exception) {
						Signlink.reporterror("cde2");
						exception.printStackTrace();
					}
			}
			stream.currentOffset = k3 + j3;
		}
		if ((i & 1) != 0) {
			player.interactingEntity = stream.method434();
			if (player.interactingEntity == 65535)
				player.interactingEntity = -1;
		}
		if ((i & 0x10) != 0) {
			int j1 = stream.method427();
			byte abyte0[] = new byte[j1];
			Stream stream_1 = new Stream(abyte0);
			stream.readBytes(j1, 0, abyte0);
			aStreamArray895s[j] = stream_1;
			player.updatePlayer(stream_1);
		}
		if ((i & 2) != 0) {
			player.anInt1538 = stream.method436();
			player.anInt1539 = stream.method434();
		}
		if ((i & 0x20) != 0) {
			int k1 = stream.readUnsignedByte();
			int k2 = stream.method426();
			int icon = stream.readUnsignedByte();
			player.updateHitData(k2, k1, loopCycle, icon);
			player.loopCycleStatus = loopCycle + 300;
			player.currentHealth = stream.method427();
			player.maxHealth = stream.readUnsignedByte();
		}
		if ((i & 0x200) != 0) {
			int l1 = stream.readUnsignedByte();
			int l2 = stream.method428();
			int icon = stream.readUnsignedByte();
			player.updateHitData(l2, l1, loopCycle, icon);
			player.loopCycleStatus = loopCycle + 300;
			player.currentHealth = stream.readUnsignedByte();
			player.maxHealth = stream.method427();
		}
	}

	private void method108() {
		try {
			int x = myPlayer.x + anInt1278;
			int y = myPlayer.y + anInt1131;
			double rotSpeed = 2;
			if (!Configuration.enableScreenGliding) {
				screenGliding = 0;
			}
			if (anInt1014 - x < -500 || anInt1014 - x > 500 || anInt1015 - y < -500 || anInt1015 - y > 500) {
				anInt1014 = x;
				anInt1015 = y;
			}
			if (anInt1014 != x) {
				anInt1014 += (x - anInt1014) / 16;
			}
			if (anInt1015 != y) {
				anInt1015 += (y - anInt1015) / 16;
			}
			if (super.keyArray[1] == 1) {
				anInt1186 += (-24 - anInt1186) / rotSpeed;
				screenGliding++;
			} else if (super.keyArray[2] == 1) {
				anInt1186 += (24 - anInt1186) / rotSpeed;
				screenGliding++;
			} else {
				if (screenGliding >= 10) {
					if (anInt1186 > 0) {
						anInt1186--;
					} else if (anInt1186 < 0) {
						anInt1186++;
					}
				} else {
					anInt1186 /= rotSpeed;
				}
			}
			if (super.keyArray[3] == 1) {
				anInt1187 += (12 - anInt1187) / rotSpeed;
				screenGliding++;
			} else if (super.keyArray[4] == 1) {
				anInt1187 += (-12 - anInt1187) / rotSpeed;
				screenGliding++;
			} else {
				if (screenGliding >= 10) {
					if (anInt1187 > 0) {
						anInt1187--;
					} else if (anInt1187 < 0) {
						anInt1187++;
					}
				} else {
					anInt1187 /= rotSpeed;
				}
			}
			minimapInt1 = minimapInt1 + anInt1186 / (int) rotSpeed & 0x7ff;
			anInt1184 += anInt1187 / rotSpeed;
			if (anInt1184 < 128) {
				anInt1184 = 128;
			}
			if (anInt1184 > 383) {
				anInt1184 = 383;
			}
			int l = anInt1014 >> 7;
			int i1 = anInt1015 >> 7;
			int j1 = method42(plane, anInt1015, anInt1014);
			int k1 = 0;
			if (l > 3 && i1 > 3 && l < 100 && i1 < 100) {
				for (int l1 = l - 4; l1 <= l + 4; l1++) {
					for (int k2 = i1 - 4; k2 <= i1 + 4; k2++) {
						int l2 = plane;
						if (l2 < 3 && (byteGroundArray[1][l1][k2] & 2) == 2)
							l2++;
						int i3 = j1 - intGroundArray[l2][l1][k2];
						if (i3 > k1)
							k1 = i3;
					}

				}

			}
			anInt1005++;
			if (anInt1005 > 1512) {
				anInt1005 = 0;
				stream.createFrame(77);
				stream.writeWordBigEndian(0);
				int i2 = stream.currentOffset;
				stream.writeWordBigEndian((int) (Math.random() * 256D));
				stream.writeWordBigEndian(101);
				stream.writeWordBigEndian(233);
				stream.writeWord(45092);
				if ((int) (Math.random() * 2D) == 0)
					stream.writeWord(35784);
				stream.writeWordBigEndian((int) (Math.random() * 256D));
				stream.writeWordBigEndian(64);
				stream.writeWordBigEndian(38);
				stream.writeWord((int) (Math.random() * 65536D));
				stream.writeWord((int) (Math.random() * 65536D));
				stream.writeBytes(stream.currentOffset - i2);
			}
			int j2 = k1 * 192;
			if (j2 > 0x17f00)
				j2 = 0x17f00;
			if (j2 < 32768)
				j2 = 32768;
			if (j2 > anInt984) {
				anInt984 += (j2 - anInt984) / 24;
				return;
			}
			if (j2 < anInt984) {
				anInt984 += (j2 - anInt984) / 80;
			}
		} catch (Exception _ex) {
			Signlink.reporterror("glfc_ex " + myPlayer.x + "," + myPlayer.y + "," + anInt1014 + "," + anInt1015 + "," + anInt1069 + "," + anInt1070 + "," + baseX + "," + baseY);
			throw new RuntimeException("eek");
		}
	}

	public void processDrawing() {
		if (rsAlreadyLoaded || loadingError || genericLoadingError) {
			showErrorScreen();
			return;
		}
		if (!loggedIn)
			loginRenderer.displayLoginScreen();
		else
			drawGameScreen();
		anInt1213 = 0;
	}

	private boolean isFriendOrSelf(String s) {
		if (s == null)
			return false;
		for (int i = 0; i < friendsCount; i++)
			if (s.equalsIgnoreCase(friendsList[i]))
				return true;
		return s.equalsIgnoreCase(myPlayer.name);
	}

	private static String combatDiffColor(int i, int j) {
		int k = i - j;
		if (k < -9)
			return "@red@";
		if (k < -6)
			return "@or3@";
		if (k < -3)
			return "@or2@";
		if (k < 0)
			return "@or1@";
		if (k > 9)
			return "@gre@";
		if (k > 6)
			return "@gr3@";
		if (k > 3)
			return "@gr2@";
		if (k > 0)
			return "@gr1@";
		else
			return "@yel@";
	}

	private void setWaveVolume(int i) {
		Signlink.wavevol = i;
	}

	public static String formatCoins(long amount) {
		if (amount >= 1_000 && amount < 1_000_000) {
			return "" + (amount / 1_000) + "K";
		}

		if (amount >= 1_000_000 && amount < 1_000_000_000) {
			return "" + (amount / 1_000_000) + "M";
		}

		if (amount >= 1_000_000_000 && amount < 1_000_000_000_000L) {
			return "" + (amount / 1_000_000_000) + "B";
		}

		if (amount >= 1_000_000_000_000L && amount < 1_000_000_000_000_000L) {
			return "" + (amount / 1_000_000_000_000L) + "T";
		}

		if (amount >= 1_000_000_000_000_000L && amount < 1_000_000_000_000_000_000L) {
			return "" + (amount / 1_000_000_000_000_000L) + "F";
		}

		if (amount >= 1_000_000_000_000_000_000L) {
			return "" + (amount / 1_000_000_000_000_000_000L) + "A";
		}
		return "" + amount;
	}

	private int getMoneyOrbColor(long amount) {
		if (amount >= 0 && amount <= 99999) {
			return 0xFFFF00;
		} else if (amount >= 100000 && amount <= 9999999) {
			return 0xFFFFFF;
		} else {
			return 0x00FF80;
		}
	}

	private void draw3dScreen() {
		if (counterOn) {
			drawCounterOnScreen();
		}

		if (showChatComponents) {
			drawSplitPrivateChat();
		}

		BannerManager.drawMovingBanner();

		if (crossType == 1) {
			int offSet = frameMode == ScreenMode.FIXED ? 4 : 0;
			crosses[crossIndex / 100].drawSprite(crossX - 8 - offSet, crossY - 8 - offSet);
			anInt1142++;
			if (anInt1142 > 67) {
				anInt1142 = 0;
				stream.createFrame(78);
			}
		}
		if (crossType == 2) {
			int offSet = frameMode == ScreenMode.FIXED ? 4 : 0;
			crosses[4 + crossIndex / 100].drawSprite(crossX - 8 - offSet, crossY - 8 - offSet);
		}

		/** Loading Walkable Interfaces */
		if (inBarrows(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane)) {
			anInt1018 = 59000;
		} else if (inGWD(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane)) {
			anInt1018 = 61750;
		} else if (inWGLobby(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane)) {
			anInt1018 = 41250;
		} else if (inWGGame(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane)) {
			anInt1018 = 41270;	
		} else if (inCyclops(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane)) {
			anInt1018 = 51200;
		} else if (inPcBoat(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane)) {
			anInt1018 = 21119;
		} else if (inPcGame(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane)) {
			anInt1018 = 21100;
		} else if (inWilderness(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane) && Configuration.economyWorld) {
			anInt1018 = 23300;
		} else if (inPvP(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane) && !Configuration.economyWorld) {
			anInt1018 = 60250;
		} else if (inSafe(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane) && !Configuration.economyWorld) {
			anInt1018 = 60350;
		} else if (Configuration.snow) {
			anInt1018 = 11877;
		} else {
			anInt1018 = -1;
		}
		/** End */

		if (anInt1018 != -1) {
			RSInterface rsInterface = RSInterface.interfaceCache[anInt1018];
			method119(anInt945, anInt1018);

			if (anInt1018 == 11146 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], -5);
			} else if (anInt1018 == 23300) {
				drawInterface(0, frameWidth - rsInterface.width - 253, rsInterface, 0);
			} else if (anInt1018 == 23300 && frameMode == ScreenMode.RESIZABLE) {
				drawInterface(0, frameWidth / 2 - 780, RSInterface.interfaceCache[anInt1018], 80);
			} else if ((anInt1018 == 2804 || anInt1018 == 11479) && frameMode != ScreenMode.FIXED) {
				drawInterface(0, frameWidth / 2 - 1010, RSInterface.interfaceCache[anInt1018], 80);
			} else if (anInt1018 == 41270 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 8);
			} else if (anInt1018 == 41250 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 8);
			} else if (anInt1018 == 201 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, frameWidth - 520, RSInterface.interfaceCache[anInt1018], -110);
			} else if (anInt1018 == 41270 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 8);
			} else if (anInt1018 == 41250 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 8);
			} else if (anInt1018 == 59000 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], frameHeight - 495);
			} else if (anInt1018 == 21119 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 5);
			} else if (anInt1018 == 21100 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 5);
			} else if (anInt1018 == 51200 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, frameWidth - 770, RSInterface.interfaceCache[anInt1018], 25);
			} else if (anInt1018 == 61750 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, frameWidth - 800, RSInterface.interfaceCache[anInt1018], 5);
			} else if (anInt1018 == 4535 && frameMode != ScreenMode.FIXED) {
				drawInterface(0, -418, RSInterface.interfaceCache[anInt1018], -285);
			} else if ((anInt1018 == 15892 || anInt1018 == 15917 || anInt1018 == 15931 || anInt1018 == 15962) && frameMode != ScreenMode.FIXED) {
				drawInterface(0, (anInt1018 == 15892 ? -325 : -349), RSInterface.interfaceCache[anInt1018], 25);
			} else
				drawInterface(0, frameMode == ScreenMode.FIXED ? 0 : (frameWidth / 2) - -80, RSInterface.interfaceCache[anInt1018], frameMode == ScreenMode.FIXED ? 0 : (frameHeight / 2) - 550);
		}
		if (openInterfaceID == 5292) {
			method119(anInt945, openInterfaceID);
			drawInterface(0, frameMode == ScreenMode.FIXED ? 0 : (frameWidth / 2) - 356, RSInterface.interfaceCache[openInterfaceID], frameMode == ScreenMode.FIXED ? 0 : (frameHeight / 2) - 230);
		} else if (openInterfaceID != -1 && openInterfaceID != 5292) {
			method119(anInt945, openInterfaceID);
			int w = 512, h = 334;
			int x = frameMode == ScreenMode.FIXED ? 0 : (frameWidth / 2) - 256;
			int y = frameMode == ScreenMode.FIXED ? 0 : (frameHeight / 2) - 167;
			int count = !changeTabArea ? 4 : 3;
			if (frameMode != ScreenMode.FIXED) {
				for (int i = 0; i < count; i++) {
					if (x + w > (frameWidth - 225)) {
						x = x - 30;
						if (x < 0) {
							x = 0;
						}
					}
					if (y + h > (frameHeight - 182)) {
						y = y - 30;
						if (y < 0) {
							y = 0;
						}
					}
				}
			}
			drawInterface(0, x, RSInterface.interfaceCache[openInterfaceID], y);
		}
		if (!menuOpen) {
			processRightClick();
			drawTooltip();
		} else if (menuScreenArea == 0) {
			drawMenu(frameMode == ScreenMode.FIXED ? 4 : 0, frameMode == ScreenMode.FIXED ? 4 : 0);
		}
		if (anInt1055 == 1) {
			multiOverlay.drawSprite(frameMode == ScreenMode.FIXED ? 472 : frameWidth - 165, frameMode == ScreenMode.FIXED ? 296 : 160);
		}
		if (fpsOn) {
			int textColour = 0xffff00;
			if (super.fps < 15) {
				textColour = 0xff0000;
			}
			regularText.method385(textColour, "Fps: " + super.fps, 12, 5);
			Runtime runtime = Runtime.getRuntime();
			int memUsage = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			textColour = 0xffff00;
			if (memUsage > 0x2000000 && lowMem) {
				textColour = 0xff0000;
			}
			regularText.method385(textColour, "Mem: " + memUsage + "k", 27, 5);
		}
		int x = baseX + (myPlayer.x - 6 >> 7);
		int y = baseY + (myPlayer.y - 6 >> 7);
		final String screenMode = frameMode == ScreenMode.FIXED ? "Fixed" : "Resizable";
		if (clientData) {
			int textColour = 0xffff00;
			int fpsColour = 0xffff00;
			if (super.fps < 15) {
				fpsColour = 0xff0000;
			}
			smallText.method385(textColour, "Frame Width: " + (mouseX - frameWidth) + ", Frame Height: " + (mouseY - frameHeight), frameHeight - 271, 5);
			smallText.method385(textColour, "Client Zoom: " + cameraZoom, frameHeight - 257, 5);
			smallText.method385(fpsColour, "Fps: " + super.fps, frameHeight - 243, 5);
			Runtime runtime = Runtime.getRuntime();
			int clientMemory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			smallText.method385(textColour, "Memory Usage: " + NumberFormat.getInstance().format(clientMemory) + "k", frameHeight - 229, 5);
			smallText.method385(textColour, "Mouse X: " + mouseX + ", Mouse Y: " + mouseY, frameHeight - 215, 5);
			smallText.method385(textColour, "Coords: " + x + ", " + y, frameHeight - 201, 5);
			smallText.method385(textColour, "Client Mode: " + screenMode + "", frameHeight - 187, 5);
			smallText.method385(textColour, "Client Resolution: " + frameWidth + "x" + frameHeight, frameHeight - 173, 5);
		}
		if (anInt1104 != 0) {
			int j = anInt1104 / 50;
			int l = j / 60;
			int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 498;
			j %= 60;
			if (j < 10)
				smallText.method385(0xffff00, "System update in: " + l + ":0" + j, 329 + yOffset, 4);
			else
				smallText.method385(0xffff00, "System update in: " + l + ":" + j, 329 + yOffset, 4);
			anInt849++;
			if (anInt849 > 75) {
				anInt849 = 0;
				stream.createFrame(148);
			}
		}
	}

	static final class BannerManager {

		static class Banner {

			private final String text;

			private int x;

			private final int color;

			private int opacity = 0;

			private long leasureTime = 0;

			public Banner(String text, int color) {
				this.text = text;
				this.color = color;
				this.x = frameWidth - (frameMode == ScreenMode.FIXED ? 253 : 155);
			}

			public int getColor() {
				return color;
			}

			public String getText() {
				return text;
			}

			public int getX() {
				return x;
			}

			public int getOpacity() {
				return opacity;
			}

			public void update() {
				if (x <= 0) {
					banner = null;
					return;
				}

				int width = frameWidth - (frameMode == ScreenMode.FIXED ? 253 : 155);
				int delta = 4 * (1 << (int) (width / (765.0 - (frameMode == ScreenMode.FIXED ? 253 : 155))));

				if (x < width / 2.0) {
					if (opacity - delta >= 0) {
						opacity -= delta;
					}
				} else {
					if (opacity + delta <= 255) {
						opacity += delta;
					}
				}

				if (System.currentTimeMillis() - leasureTime >= 2500) {
					x -= delta;
				}

				if (leasureTime == 0) {
					int center = (int) ((width + newFancyFont.getTextWidth(text)) / 2.0);

					if (x >= center - delta && x <= center + delta) {
						leasureTime = System.currentTimeMillis();
					}
				}
			}
		}

		public static Client instance;

		private static Banner banner;

		public static void addBanner(String text, int color) {
			System.out.println(text + " _ " + color);
			banner = new Banner(text, color);
		}

		public static void drawMovingBanner() {

			if (banner == null) {
				return;
			}

			DrawingArea.drawAlphaPixels(0, 20, frameWidth, 50, banner.getColor(), banner.getOpacity());
			newFancyFont.drawRAString(banner.getText(), banner.getX(), 52, 0xFFFFFF, 0x0);

			banner.update();
		}
	}

	private final boolean inBarrows(int x, int y, int z) {
		return (x > 3539 && x < 3582 && y >= 9675 && y < 9722) || x > 3532 && x < 3546 && y > 9698 && y < 9709;
	}

	public final boolean inCyclops(int x, int y, int z) {
		return (x >= 2847 && x <= 2876 && y >= 3534 && y <= 3556 && z == 2 || x >= 2838 && x <= 2847 && y >= 3543 && y <= 3556 && z == 2);
	}
	
	private final boolean inWGGame(int x, int y, int z) {
		return (x > 2136 && x < 2166 && y > 5089 && y < 5108);
	}

	private final boolean inWGLobby(int x, int y, int z) {
		return (x > 1859 && x < 1868 && y > 5316 && y < 5323);
	}

	private final boolean inGWD(int x, int y, int z) {
		return (x > 2816 && x < 2960 && y >= 5243 && y < 5400);
	}

	public boolean inPcBoat(int x, int y, int z) {
		return (x >= 2660 && x <= 2663 && y >= 2638 && y <= 2643);
	}

	public boolean inPcGame(int x, int y, int z) {
		return (x >= 2624 && x <= 2690 && y >= 2550 && y <= 2619);
	}

	public boolean inWilderness(int x, int y, int z) {
		return (x > 2941 && x < 3392 && y > 3521 && y < 3966) || (x > 2941 && x < 3392 && y > 9918 && y < 10366);
	}

	public boolean inMaze(int x, int y, int z) {
		return (x > 1);
	}

	public boolean inSafe(int x, int y, int z) {
		return (x >= 3180 && x <= 3190 && y >= 3433 && y <= 3447) || (x >= 3091 && x <= 3098 && y >= 3488 && y <= 3499);
	}

	public boolean inPvP(int x, int y, int z) {
		return (inSafe(x, y, z) ? false : true);
	}

	private void addIgnore(long l) {
		try {
			if (l == 0L)
				return;
			if (ignoreCount >= 100) {
				pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
				return;
			}
			String s = TextClass.fixName(TextClass.nameForLong(l));
			if (s.equals(myPlayer.name)) {
				pushMessage("You may not ignore yourself!", 0, "");
				return;
			}
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage(s + " is already on your ignore list", 0, "");
					return;
				}
			for (int k = 0; k < friendsCount; k++)
				if (friendsListAsLongs[k] == l) {
					pushMessage("Please remove " + s + " from your friend list first", 0, "");
					return;
				}

			ignoreListAsLongs[ignoreCount++] = l;
			stream.createFrame(133);
			stream.writeQWord(l);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("45688, " + l + ", " + 4 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private void method114() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = myPlayerIndex;
			else
				j = playerIndices[i];
			Player player = playerArray[j];
			if (player != null)
				method96(player);
		}

	}

	private void method115() {
		if (loadingStage == 2) {
			for (Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
				if (class30_sub1.anInt1294 > 0)
					class30_sub1.anInt1294--;
				if (class30_sub1.anInt1294 == 0) {
					if (class30_sub1.anInt1299 < 0 || ObjectManager.method178(class30_sub1.anInt1299, class30_sub1.anInt1301)) {
						method142(class30_sub1.anInt1298, class30_sub1.anInt1295, class30_sub1.anInt1300, class30_sub1.anInt1301, class30_sub1.anInt1297, class30_sub1.anInt1296, class30_sub1.anInt1299);
						class30_sub1.unlink();
					}
				} else {
					if (class30_sub1.anInt1302 > 0)
						class30_sub1.anInt1302--;
					if (class30_sub1.anInt1302 == 0 && class30_sub1.anInt1297 >= 1 && class30_sub1.anInt1298 >= 1 && class30_sub1.anInt1297 <= 102 && class30_sub1.anInt1298 <= 102 && (class30_sub1.anInt1291 < 0 || ObjectManager.method178(class30_sub1.anInt1291, class30_sub1.anInt1293))) {
						method142(class30_sub1.anInt1298, class30_sub1.anInt1295, class30_sub1.anInt1292, class30_sub1.anInt1293, class30_sub1.anInt1297, class30_sub1.anInt1296, class30_sub1.anInt1291);
						class30_sub1.anInt1302 = -1;
						if (class30_sub1.anInt1291 == class30_sub1.anInt1299 && class30_sub1.anInt1299 == -1)
							class30_sub1.unlink();
						else if (class30_sub1.anInt1291 == class30_sub1.anInt1299 && class30_sub1.anInt1292 == class30_sub1.anInt1300 && class30_sub1.anInt1293 == class30_sub1.anInt1301)
							class30_sub1.unlink();
					}
				}
			}

		}
	}

	private void determineMenuSize() {
		int boxLength = newBoldFont.getTextWidth("Choose option");
		for (int row = 0; row < menuActionRow; row++) {
			int actionLength = newBoldFont.getTextWidth(menuActionName[row]);
			if (actionLength > boxLength) {
				boxLength = actionLength;
			}
		}
		boxLength += 8;
		int offset = 15 * menuActionRow + 21;
		if (super.saveClickX > 0 && super.saveClickY > 0 && super.saveClickX < frameWidth && super.saveClickY < frameHeight) {
			int xClick = super.saveClickX - boxLength / 2;
			if (xClick + boxLength > frameWidth - 4) {
				xClick = frameWidth - 4 - boxLength;
			}
			if (xClick < 0) {
				xClick = 0;
			}
			int yClick = super.saveClickY - 0;
			if (yClick + offset > frameHeight - 6) {
				yClick = frameHeight - 6 - offset;
			}
			if (yClick < 0) {
				yClick = 0;
			}
			menuOpen = true;
			menuOffsetX = xClick;
			menuOffsetY = yClick;
			menuWidth = boxLength;
			menuHeight = 15 * menuActionRow + 22;
		}
	}

	private void method117(Stream stream) {
		stream.initBitAccess();
		int j = stream.readBits(1);
		if (j == 0)
			return;
		int k = stream.readBits(2);
		if (k == 0) {
			anIntArray894[anInt893++] = myPlayerIndex;
			return;
		}
		if (k == 1) {
			int l = stream.readBits(3);
			myPlayer.moveInDir(false, l);
			int k1 = stream.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = myPlayerIndex;
			return;
		}
		if (k == 2) {
			int i1 = stream.readBits(3);
			myPlayer.moveInDir(true, i1);
			int l1 = stream.readBits(3);
			myPlayer.moveInDir(true, l1);
			int j2 = stream.readBits(1);
			if (j2 == 1)
				anIntArray894[anInt893++] = myPlayerIndex;
			return;
		}
		if (k == 3) {
			plane = stream.readBits(2);
			int j1 = stream.readBits(1);
			int i2 = stream.readBits(1);
			if (i2 == 1)
				anIntArray894[anInt893++] = myPlayerIndex;
			int k2 = stream.readBits(7);
			int l2 = stream.readBits(7);
			myPlayer.setPos(l2, k2, j1 == 1);
		}
	}

	private void nullLoader() {
		aBoolean831 = false;
		while (drawingFlames) {
			aBoolean831 = false;
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
		aBackground_966 = null;
		aBackground_967 = null;
		aBackgroundArray1152s = null;
		anIntArray850 = null;
		anIntArray851 = null;
		anIntArray852 = null;
		anIntArray853 = null;
		anIntArray1190 = null;
		anIntArray1191 = null;
		anIntArray828 = null;
		anIntArray829 = null;
		aClass30_Sub2_Sub1_Sub1_1201 = null;
		aClass30_Sub2_Sub1_Sub1_1202 = null;
	}

	private boolean method119(int i, int j) {
		boolean flag1 = false;
		RSInterface class9 = RSInterface.interfaceCache[j];
		if (class9 == null || class9.children == null) {
			return false;
		}
		for (int k = 0; k < class9.children.length; k++) {
			if (class9.children[k] == -1)
				break;
			RSInterface class9_1 = RSInterface.interfaceCache[class9.children[k]];
			if (class9_1.type == 1)
				flag1 |= method119(i, class9_1.id);
			if (class9_1.type == 6 && (class9_1.anInt257 != -1 || class9_1.anInt258 != -1)) {
				boolean flag2 = interfaceIsSelected(class9_1);
				int l;
				if (flag2)
					l = class9_1.anInt258;
				else
					l = class9_1.anInt257;
				if (l != -1) {
					Animation animation = Animation.anims[l];
					for (class9_1.anInt208 += i; class9_1.anInt208 > animation.method258(class9_1.anInt246);) {
						class9_1.anInt208 -= animation.method258(class9_1.anInt246) + 1;
						class9_1.anInt246++;
						if (class9_1.anInt246 >= animation.anInt352) {
							class9_1.anInt246 -= animation.anInt356;
							if (class9_1.anInt246 < 0 || class9_1.anInt246 >= animation.anInt352)
								class9_1.anInt246 = 0;
						}
						flag1 = true;
					}

				}
			}
		}

		return flag1;
	}

	private int method120() {
		if (!Configuration.enableRoofs) {
			return plane;
		}
		int j = 3;
		if (yCameraCurve < 310) {
			int k = xCameraPos >> 7;
			int l = yCameraPos >> 7;
			int i1 = myPlayer.x >> 7;
			int j1 = myPlayer.y >> 7;
			if ((byteGroundArray[plane][k][l] & 4) != 0)
				j = plane;
			int k1;
			if (i1 > k)
				k1 = i1 - k;
			else
				k1 = k - i1;
			int l1;
			if (j1 > l)
				l1 = j1 - l;
			else
				l1 = l - j1;
			if (k1 > l1) {
				int i2 = (l1 * 0x10000) / k1;
				int k2 = 32768;
				while (k != i1) {
					if (k < i1)
						k++;
					else if (k > i1)
						k--;
					if ((byteGroundArray[plane][k][l] & 4) != 0)
						j = plane;
					k2 += i2;
					if (k2 >= 0x10000) {
						k2 -= 0x10000;
						if (l < j1)
							l++;
						else if (l > j1)
							l--;
						if ((byteGroundArray[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			} else {
				int j2 = (k1 * 0x10000) / l1;
				int l2 = 32768;
				while (l != j1) {
					if (l < j1)
						l++;
					else if (l > j1)
						l--;
					if ((byteGroundArray[plane][k][l] & 4) != 0)
						j = plane;
					l2 += j2;
					if (l2 >= 0x10000) {
						l2 -= 0x10000;
						if (k < i1)
							k++;
						else if (k > i1)
							k--;
						if ((byteGroundArray[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			}
		}
		if ((byteGroundArray[plane][myPlayer.x >> 7][myPlayer.y >> 7] & 4) != 0)
			j = plane;
		return j;
	}

	private int method121() {
		if (!Configuration.enableRoofs) {
			return plane;
		}
		int j = method42(plane, yCameraPos, xCameraPos);
		if (j - zCameraPos < 800 && (byteGroundArray[plane][xCameraPos >> 7][yCameraPos >> 7] & 4) != 0)
			return plane;
		else
			return 3;
	}

	private void delIgnore(long l) {
		try {
			if (l == 0L)
				return;
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					ignoreCount--;
					System.arraycopy(ignoreListAsLongs, j + 1, ignoreListAsLongs, j, ignoreCount - j);

					stream.createFrame(74);
					stream.writeQWord(l);
					return;
				}

			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public String getParameter(String s) {
		if (Signlink.mainapp != null)
			return Signlink.mainapp.getParameter(s);
		else
			return super.getParameter(s);
	}

	private void adjustVolume(boolean flag, int i) {
		Signlink.midivol = i;
		if (flag)
			Signlink.midi = "voladjust";
	}

	private int extractInterfaceValues(RSInterface class9, int j) {
		if (class9.valueIndexArray == null || j >= class9.valueIndexArray.length)
			return -2;
		try {
			int ai[] = class9.valueIndexArray[j];
			int k = 0;
			int l = 0;
			int i1 = 0;
			do {
				int j1 = ai[l++];
				int k1 = 0;
				byte byte0 = 0;
				if (j1 == 0)
					return k;
				if (j1 == 1)
					k1 = currentStats[ai[l++]];
				if (j1 == 2)
					k1 = maxStats[ai[l++]];
				if (j1 == 3)
					k1 = currentExp[ai[l++]];
				if (j1 == 4) {
					RSInterface class9_1 = RSInterface.interfaceCache[ai[l++]];
					int k2 = ai[l++];
					if (k2 >= 0 && k2 < ItemDef.totalItems && (!ItemDef.forID(k2).membersObject || isMembers)) {
						for (int j3 = 0; j3 < class9_1.inv.length; j3++) {
							if (class9_1.inv[j3] == k2 + 1) {
								k1 += class9_1.invStackSizes[j3];
							}
						}
					}
				}
				if (j1 == 5)
					k1 = variousSettings[ai[l++]];
				if (j1 == 6)
					k1 = Skills.EXP_FOR_LEVEL[maxStats[ai[l++]] - 1];
				if (j1 == 7)
					k1 = (variousSettings[ai[l++]] * 100) / 46875;
				if (j1 == 8)
					k1 = myPlayer.combatLevel;
				if (j1 == 9) {
					for (int l1 = 0; l1 < Skills.SKILLS_COUNT; l1++)
						if (Skills.SKILL_ENABLED[l1])
							k1 += maxStats[l1];

				}
				if (j1 == 10) {
					RSInterface class9_2 = RSInterface.interfaceCache[ai[l++]];
					int l2 = ai[l++] + 1;
					if (l2 >= 0 && l2 < ItemDef.totalItems && isMembers) {
						for (int k3 = 0; k3 < class9_2.inv.length; k3++) {
							if (class9_2.inv[k3] != l2)
								continue;
							k1 = 0x3b9ac9ff;
							break;
						}

					}
				}
				if (j1 == 11)
					k1 = energy;
				if (j1 == 12)
					k1 = weight;
				if (j1 == 13) {
					int i2 = variousSettings[ai[l++]];
					int i3 = ai[l++];
					k1 = (i2 & 1 << i3) == 0 ? 0 : 1;
				}
				if (j1 == 14) {
					int j2 = ai[l++];
					VarBit varBit = VarBit.cache[j2];
					int l3 = varBit.anInt648;
					int i4 = varBit.anInt649;
					int j4 = varBit.anInt650;
					int k4 = anIntArray1232[j4 - i4];
					k1 = variousSettings[l3] >> i4 & k4;
				}
				if (j1 == 15)
					byte0 = 1;
				if (j1 == 16)
					byte0 = 2;
				if (j1 == 17)
					byte0 = 3;
				if (j1 == 18)
					k1 = (myPlayer.x >> 7) + baseX;
				if (j1 == 19)
					k1 = (myPlayer.y >> 7) + baseY;
				if (j1 == 20)
					k1 = ai[l++];
				if (j1 == 21)
					k1 = tabAmounts[ai[l++]];
				if (j1 == 22) {
					RSInterface class9_1 = RSInterface.interfaceCache[ai[l++]];
					int initAmount = class9_1.inv.length;
					for (int j3 = 0; j3 < class9_1.inv.length; j3++) {
						if (class9_1.inv[j3] <= 0) {
							initAmount--;
						}
					}
					k1 += initAmount;
				}
				if (j1 == 23) {
					for (int l1 = 0; l1 < Skills.SKILLS_COUNT; l1++)
						if (Skills.SKILL_ENABLED[l1])
							k1 += currentExp[l1];
				}
				if (byte0 == 0) {
					if (i1 == 0)
						k += k1;
					if (i1 == 1)
						k -= k1;
					if (i1 == 2 && k1 != 0)
						k /= k1;
					if (i1 == 3)
						k *= k1;
					i1 = 0;
				} else {
					i1 = byte0;
				}
			} while (true);
		} catch (Exception _ex) {
			return -1;
		}
	}

	private void drawTooltip() {
		if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
			return;
		}
		
		String s;
		
		if (itemSelected == 1 && menuActionRow < 2) {
			s = "Use " + selectedItemName + " with...";
		} else if (spellSelected == 1 && menuActionRow < 2) {
			s = spellTooltip + "...";
		} else {
			s = menuActionName[menuActionRow - 1];
		}
		
		if (menuActionRow > 2 && !Configuration.menuHovers) {
			s = s + "@whi@ / " + (menuActionRow - 2) + " more options";
		} else {
			s = s + "@whi@";
		}
		
		if (Configuration.menuHovers && !s.contains("Walk here")) {
			DrawingArea.drawAlphaPixels(super.mouseX, super.mouseY - 11, newBoldFont.getTextWidth(s.trim()) + 6, 17, 0, 100);
			newBoldFont.drawBasicString(s, super.mouseX + 2, super.mouseY + 2, 0xFFFFFF, 1);
		}
		newBoldFont.drawBasicString(s, 4, 15, 0xFFFFFF, 1);
	}

	private void markMinimap(Sprite sprite, int x, int y) {
		if (sprite == null) {
			return;
		}
		int k = minimapInt1 + minimapInt2 & 0x7ff;
		int l = x * x + y * y;
		if (l > 6400) {
			return;
		}
		int i1 = Model.modelIntArray1[k];
		int j1 = Model.modelIntArray2[k];
		i1 = (i1 * 256) / (minimapInt3 + 256);
		j1 = (j1 * 256) / (minimapInt3 + 256);
		int k1 = y * i1 + x * j1 >> 16;
		int l1 = y * j1 - x * i1 >> 16;
		if (frameMode == ScreenMode.FIXED) {
			sprite.drawSprite(((94 + k1) - sprite.cropWidth / 2) + 4 + 30, 83 - l1 - sprite.anInt1445 / 2 - 4 + 5);
		} else {
			sprite.drawSprite(((77 + k1) - sprite.cropWidth / 2) + 4 + (frameWidth - 167), 85 - l1 - sprite.anInt1445 / 2 - 4);
		}
	}

	private void drawMinimap() {
		if (frameMode == ScreenMode.FIXED) {
			aRSImageProducer_1164.initDrawingArea();
		}
		if (anInt1021 == 2) {
			if (frameMode == ScreenMode.FIXED) {
				fixedGameComponents[0].drawSprite(0, 0);
			} else {
				gameComponents[0].drawSprite(frameWidth - 181, 0);
				gameComponents[1].drawSprite(frameWidth - 158, 7);
			}
			if (Configuration.enableStatusOrbs) {
				if (frameMode == ScreenMode.FIXED ? super.mouseX >= 517 && super.mouseX <= 545 && super.mouseY >= 27 && super.mouseY <= 54 : super.mouseX >= frameWidth - 211 && super.mouseX <= frameWidth - 183 && super.mouseY >= 23 && super.mouseY <= 50) {
					orbComponents3[1].drawSprite(frameMode == ScreenMode.FIXED ? 3 : frameWidth - 210, frameMode == ScreenMode.FIXED ? 27 : 23);
				} else {
					orbComponents3[0].drawSprite(frameMode == ScreenMode.FIXED ? 3 : frameWidth - 210, frameMode == ScreenMode.FIXED ? 27 : 23);
				}
			}
			if (frameMode == ScreenMode.FIXED ? super.mouseX >= 742 && super.mouseX <= 765 && super.mouseY >= 0 && super.mouseY <= 24 : super.mouseX >= frameWidth - 26 && super.mouseX <= frameWidth - 1 && super.mouseY >= 2 && super.mouseY <= 24) {
				cacheSprite[348].drawARGBSprite(frameMode == ScreenMode.FIXED ? 226 : frameWidth - 23, 0, 205);
			} else {
				cacheSprite[348].drawSprite(frameMode == ScreenMode.FIXED ? 226 : frameWidth - 23, 0);
			}
			if (tabID == 14) {
				cacheSprite[349].drawSprite(frameMode == ScreenMode.FIXED ? 226 : frameWidth - 23, 0);
			}
			loadAllOrbs(frameMode == ScreenMode.FIXED ? 0 : frameWidth - 217);
			compass.method352(33, minimapInt1, anIntArray1057, 256, anIntArray968, (frameMode == ScreenMode.FIXED ? 25 : 24), 4, (frameMode == ScreenMode.FIXED ? 29 : frameWidth - 176), 33, 25);
			if (menuOpen) {
				drawMenu(frameMode == ScreenMode.FIXED ? 516 : 0, 0);
			}
			if (frameMode == ScreenMode.FIXED) {
				aRSImageProducer_1164.initDrawingArea();
			}
			return;
		}
		int i = minimapInt1 + minimapInt2 & 0x7ff;
		int j = 48 + myPlayer.x / 32;
		int l2 = 464 - myPlayer.y / 32;
		minimapImage.method352(151, i, anIntArray1229, 256 + minimapInt3, anIntArray1052, l2, (frameMode == ScreenMode.FIXED ? 9 : 7), (frameMode == ScreenMode.FIXED ? 54 : frameWidth - 158), 146, j);
		for (int j5 = 0; j5 < anInt1071; j5++) {
			int k = (anIntArray1072[j5] * 4 + 2) - myPlayer.x / 32;
			int i3 = (anIntArray1073[j5] * 4 + 2) - myPlayer.y / 32;
			markMinimap(aClass30_Sub2_Sub1_Sub1Array1140[j5], k, i3);
		}
		for (int k5 = 0; k5 < 104; k5++) {
			for (int l5 = 0; l5 < 104; l5++) {
				NodeList class19 = groundArray[plane][k5][l5];
				if (class19 != null) {
					int l = (k5 * 4 + 2) - myPlayer.x / 32;
					int j3 = (l5 * 4 + 2) - myPlayer.y / 32;
					markMinimap(mapDotItem, l, j3);
				}
			}
		}
		for (int i6 = 0; i6 < npcCount; i6++) {
			Npc npc = npcArray[npcIndices[i6]];
			if (npc != null && npc.isVisible()) {
				EntityDef entityDef = npc.desc;
				if (entityDef.childrenIDs != null) {
					entityDef = entityDef.method161();
				}
				if (entityDef != null && entityDef.aBoolean87 && entityDef.aBoolean84) {
					int i1 = npc.x / 32 - myPlayer.x / 32;
					int k3 = npc.y / 32 - myPlayer.y / 32;
					markMinimap(mapDotNPC, i1, k3);
				}
			}
		}
		for (int j6 = 0; j6 < playerCount; j6++) {
			Player player = playerArray[playerIndices[j6]];
			if (player != null && player.isVisible()) {
				int j1 = player.x / 32 - myPlayer.x / 32;
				int l3 = player.y / 32 - myPlayer.y / 32;
				boolean flag1 = false;
				boolean flag3 = false;
				for (int j3 = 0; j3 < clanList.length; j3++) {
					if (clanList[j3] == null) {
						continue;
					}
					if (!clanList[j3].equalsIgnoreCase(player.name)) {
						continue;
					}
					flag3 = true;
					break;
				}
				long l6 = TextClass.longForName(player.name);
				for (int k6 = 0; k6 < friendsCount; k6++) {
					if (l6 != friendsListAsLongs[k6] || friendsNodeIDs[k6] == 0) {
						continue;
					}
					flag1 = true;
					break;
				}
				boolean flag2 = false;
				if (myPlayer.team != 0 && player.team != 0 && myPlayer.team == player.team) {
					flag2 = true;
				}
				if (flag1) {
					markMinimap(mapDotFriend, j1, l3);
				} else if (flag3) {
					markMinimap(mapDotClan, j1, l3);
				} else if (flag2) {
					markMinimap(mapDotTeam, j1, l3);
				} else {
					markMinimap(mapDotPlayer, j1, l3);
				}
			}
		}
		if (anInt855 != 0 && loopCycle % 20 < 10) {
			if (anInt855 == 1 && anInt1222 >= 0 && anInt1222 < npcArray.length) {
				Npc class30_sub2_sub4_sub1_sub1_1 = npcArray[anInt1222];
				if (class30_sub2_sub4_sub1_sub1_1 != null) {
					int k1 = class30_sub2_sub4_sub1_sub1_1.x / 32 - myPlayer.x / 32;
					int i4 = class30_sub2_sub4_sub1_sub1_1.y / 32 - myPlayer.y / 32;
					method81(mapMarker, i4, k1);
				}
			}
			if (anInt855 == 2) {
				int l1 = ((anInt934 - baseX) * 4 + 2) - myPlayer.x / 32;
				int j4 = ((anInt935 - baseY) * 4 + 2) - myPlayer.y / 32;
				method81(mapMarker, j4, l1);
			}
			if (anInt855 == 10 && anInt933 >= 0 && anInt933 < playerArray.length) {
				Player class30_sub2_sub4_sub1_sub2_1 = playerArray[anInt933];
				if (class30_sub2_sub4_sub1_sub2_1 != null) {
					int i2 = class30_sub2_sub4_sub1_sub2_1.x / 32 - myPlayer.x / 32;
					int k4 = class30_sub2_sub4_sub1_sub2_1.y / 32 - myPlayer.y / 32;
					method81(mapMarker, k4, i2);
				}
			}
		}
		if (destX != 0) {
			int j2 = (destX * 4 + 2) - myPlayer.x / 32;
			int l4 = (destY * 4 + 2) - myPlayer.y / 32;
			markMinimap(mapFlag, j2, l4);
		}
		DrawingArea.drawPixels(3, (frameMode == ScreenMode.FIXED ? 83 : 80), (frameMode == ScreenMode.FIXED ? 127 : frameWidth - 88), 0xffffff, 3);
		if (frameMode == ScreenMode.FIXED) {
			fixedGameComponents[0].drawSprite(0, 0);
		} else {
			gameComponents[0].drawSprite(frameWidth - 181, 0);
		}
		compass.method352(33, minimapInt1, anIntArray1057, 256, anIntArray968, (frameMode == ScreenMode.FIXED ? 25 : 24), 4, (frameMode == ScreenMode.FIXED ? 29 : frameWidth - 176), 33, 25);
		if (Configuration.enableStatusOrbs) {
			if (frameMode == ScreenMode.FIXED ? super.mouseX >= 517 && super.mouseX <= 545 && super.mouseY >= 27 && super.mouseY <= 54 : super.mouseX >= frameWidth - 211 && super.mouseX <= frameWidth - 183 && super.mouseY >= 23 && super.mouseY <= 50) {
				orbComponents3[1].drawSprite(frameMode == ScreenMode.FIXED ? 3 : frameWidth - 210, frameMode == ScreenMode.FIXED ? 27 : 23);
			} else {
				orbComponents3[0].drawSprite(frameMode == ScreenMode.FIXED ? 3 : frameWidth - 210, frameMode == ScreenMode.FIXED ? 27 : 23);
			}
		}
		if (frameMode == ScreenMode.FIXED ? super.mouseX >= 742 && super.mouseX <= 765 && super.mouseY >= 0 && super.mouseY <= 24 : super.mouseX >= frameWidth - 26 && super.mouseX <= frameWidth - 1 && super.mouseY >= 2 && super.mouseY <= 24) {
			cacheSprite[348].drawARGBSprite(frameMode == ScreenMode.FIXED ? 226 : frameWidth - 23, 0, 205);
		} else {
			cacheSprite[348].drawSprite(frameMode == ScreenMode.FIXED ? 226 : frameWidth - 23, 0);
		}
		if (tabID == 14) {
			cacheSprite[349].drawSprite(frameMode == ScreenMode.FIXED ? 226 : frameWidth - 23, 0);
		}
		loadAllOrbs(frameMode == ScreenMode.FIXED ? 0 : frameWidth - 217);
		if (menuOpen) {
			drawMenu(frameMode == ScreenMode.FIXED ? 516 : 0, 0);
		}
		if (frameMode == ScreenMode.FIXED) {
			aRSImageProducer_1165.initDrawingArea();
		}
	}

	private void npcScreenPos(Entity entity, int i) {
		calcEntityScreenPos(entity.x, i, entity.y);
	}

	private void calcEntityScreenPos(int i, int j, int l) {
		if (i < 128 || l < 128 || i > 13056 || l > 13056) {
			spriteDrawX = -1;
			spriteDrawY = -1;
			return;
		}
		int i1 = method42(plane, l, i) - j;
		i -= xCameraPos;
		i1 -= zCameraPos;
		l -= yCameraPos;
		int j1 = Model.modelIntArray1[yCameraCurve];
		int k1 = Model.modelIntArray2[yCameraCurve];
		int l1 = Model.modelIntArray1[xCameraCurve];
		int i2 = Model.modelIntArray2[xCameraCurve];
		int j2 = l * l1 + i * i2 >> 16;
		l = l * i2 - i * l1 >> 16;
		i = j2;
		j2 = i1 * k1 - l * j1 >> 16;
		l = i1 * j1 + l * k1 >> 16;
		i1 = j2;
		if (l >= 50) {
			spriteDrawX = Rasterizer.centerX + (i << WorldController.viewDistance) / l;
			spriteDrawY = Rasterizer.centerY + (i1 << WorldController.viewDistance) / l;
		} else {
			spriteDrawX = -1;
			spriteDrawY = -1;
		}
	}

	private void buildSplitPrivateChatMenu() {
		if (splitPrivateChat == 0)
			return;
		int i = 0;
		if (anInt1104 != 0)
			i = 1;
		for (int j = 0; j < 100; j++)
			if (chatMessages[j] != null) {
				int k = chatTypes[j];
				String s = chatNames[j];
				if (s != null && s.startsWith("@cr")) {
					if (s.charAt(4) != '@') {
						s = s.substring(6);
					} else {
						s = s.substring(5);
					}
				}
				if ((k == 3 || k == 7) && (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
					int offSet = frameMode == ScreenMode.FIXED ? 4 : 0;
					int l = 329 - i * 13;
					if (frameMode != ScreenMode.FIXED) {
						l = frameHeight - 170 - i * 13 - extendChatArea;
					}
					if (super.mouseX > 4 && super.mouseY - offSet > l - 10 && super.mouseY - offSet <= l + 3) {
						int i1 = regularText.getTextWidth("From:  " + s + chatMessages[j]) + 25;
						if (i1 > 450)
							i1 = 450;
						if (super.mouseX < 4 + i1) {
							if (myPrivilege >= 1) {
								menuActionName[menuActionRow] = "Report abuse @whi@" + s;
								menuActionID[menuActionRow] = 2606;
								menuActionRow++;
							}
							menuActionName[menuActionRow] = "Add ignore @whi@" + s;
							menuActionID[menuActionRow] = 2042;
							menuActionRow++;
							menuActionName[menuActionRow] = "Add friend @whi@" + s;
							menuActionID[menuActionRow] = 2337;
							menuActionRow++;
						}
					}
					if (++i >= 5)
						return;
				}
				if ((k == 5 || k == 6) && privateChatMode < 2 && ++i >= 5)
					return;
			}

	}

	private void method130(int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2) {
		Class30_Sub1 class30_sub1 = null;
		for (Class30_Sub1 class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1_1 != null; class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
			if (class30_sub1_1.anInt1295 != l1 || class30_sub1_1.anInt1297 != i2 || class30_sub1_1.anInt1298 != j1 || class30_sub1_1.anInt1296 != i1)
				continue;
			class30_sub1 = class30_sub1_1;
			break;
		}

		if (class30_sub1 == null) {
			class30_sub1 = new Class30_Sub1();
			class30_sub1.anInt1295 = l1;
			class30_sub1.anInt1296 = i1;
			class30_sub1.anInt1297 = i2;
			class30_sub1.anInt1298 = j1;
			method89(class30_sub1);
			aClass19_1179.insertHead(class30_sub1);
		}
		class30_sub1.anInt1291 = k;
		class30_sub1.anInt1293 = k1;
		class30_sub1.anInt1292 = l;
		class30_sub1.anInt1302 = j2;
		class30_sub1.anInt1294 = j;
	}

	private boolean interfaceIsSelected(RSInterface class9) {
		if (class9.anIntArray245 == null)
			return false;
		for (int i = 0; i < class9.anIntArray245.length; i++) {
			int j = extractInterfaceValues(class9, i);
			int k = class9.anIntArray212[i];
			if (class9.anIntArray245[i] == 2) {
				if (j >= k)
					return false;
			} else if (class9.anIntArray245[i] == 3) {
				if (j <= k)
					return false;
			} else if (class9.anIntArray245[i] == 4) {
				if (j == k)
					return false;
			} else if (j != k)
				return false;
		}

		return true;
	}

	private DataInputStream openJagGrabInputStream(String s) throws IOException {
		// if(!aBoolean872)
		// if(signlink.mainapp != null)
		// return signlink.openurl(s);
		// else
		// return new DataInputStream((new URL(getCodeBase(), s)).openStream());
		if (aSocket832 != null) {
			try {
				aSocket832.close();
			} catch (Exception _ex) {
			}
			aSocket832 = null;
		}
		aSocket832 = openSocket(43595);
		aSocket832.setSoTimeout(10000);
		java.io.InputStream inputstream = aSocket832.getInputStream();
		OutputStream outputstream = aSocket832.getOutputStream();
		outputstream.write(("JAGGRAB /" + s + "\n\n").getBytes());
		return new DataInputStream(inputstream);
	}

	private void method134(Stream stream) {
		int j = stream.readBits(8);
		if (j < playerCount) {
			for (int k = j; k < playerCount; k++)
				anIntArray840[anInt839++] = playerIndices[k];

		}
		if (j > playerCount) {
			Signlink.reporterror(myUsername + " Too many players");
			throw new RuntimeException("eek");
		}
		playerCount = 0;
		for (int l = 0; l < j; l++) {
			int i1 = playerIndices[l];
			Player player = playerArray[i1];
			int j1 = stream.readBits(1);
			if (j1 == 0) {
				playerIndices[playerCount++] = i1;
				player.anInt1537 = loopCycle;
			} else {
				int k1 = stream.readBits(2);
				if (k1 == 0) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int l1 = stream.readBits(3);
					player.moveInDir(false, l1);
					int j2 = stream.readBits(1);
					if (j2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 2) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int i2 = stream.readBits(3);
					player.moveInDir(true, i2);
					int k2 = stream.readBits(3);
					player.moveInDir(true, k2);
					int l2 = stream.readBits(1);
					if (l2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 3)
					anIntArray840[anInt839++] = i1;
			}
		}
	}

	private void drawFlames() {

	}

	public void raiseWelcomeScreen() {
		welcomeScreenRaised = true;
	}

	private final String[] chatTitles, chatColors;

	private Npc npcDisplay;

	public void pushMessage(String s, int i, String s1, String title, String color) {
		if (i == 0 && dialogID != -1) {
			aString844 = s;
			super.clickMode3 = 0;
		}
		if (backDialogID == -1)
			inputTaken = true;
		for (int j = 499; j > 0; j--) {
			chatTypes[j] = chatTypes[j - 1];
			chatNames[j] = chatNames[j - 1];
			chatMessages[j] = chatMessages[j - 1];
			chatRights[j] = chatRights[j - 1];
			chatTitles[j] = chatTitles[j - 1];
			chatColors[j] = chatColors[j - 1];
		}
		chatTypes[0] = i;
		chatNames[0] = s1;
		chatMessages[0] = s;
		chatRights[0] = rights;
		chatTitles[0] = title;
		chatColors[0] = color;
	}
	
	public void pushMessage(String s, int i, String s1) {
		if (i == 0 && dialogID != -1) {
			aString844 = s;
			super.clickMode3 = 0;
		}
		if (backDialogID == -1) {
			inputTaken = true;
		}
		for (int j = 499; j > 0; j--) {
			chatTypes[j] = chatTypes[j - 1];
			chatNames[j] = chatNames[j - 1];
			chatMessages[j] = chatMessages[j - 1];
			chatRights[j] = chatRights[j - 1];
			chatTitles[j] = chatTitles[j - 1];
			chatColors[j] = chatColors[j - 1];
			clanTitles[j] = clanTitles[j - 1];
		}
		chatTypes[0] = i;
		chatNames[0] = s1;
		chatMessages[0] = s;
		chatRights[0] = rights;
		clanTitles[0] = clanTitle;
	}

	private void method137(Stream stream, int j) {
		if (j == 84) {
			int k = stream.readUnsignedByte();
			int j3 = anInt1268 + (k >> 4 & 7);
			int i6 = anInt1269 + (k & 7);
			int l8 = stream.readUnsignedWord();
			int k11 = stream.readUnsignedWord();
			int l13 = stream.readUnsignedWord();
			if (j3 >= 0 && i6 >= 0 && j3 < 104 && i6 < 104) {
				NodeList class19_1 = groundArray[plane][j3][i6];
				if (class19_1 != null) {
					for (Item class30_sub2_sub4_sub2_3 = (Item) class19_1.reverseGetFirst(); class30_sub2_sub4_sub2_3 != null; class30_sub2_sub4_sub2_3 = (Item) class19_1.reverseGetNext()) {
						if (class30_sub2_sub4_sub2_3.ID != (l8 & 0x7fff) || class30_sub2_sub4_sub2_3.anInt1559 != k11)
							continue;
						class30_sub2_sub4_sub2_3.anInt1559 = l13;
						break;
					}

					spawnGroundItem(j3, i6);
				}
			}
			return;
		}
		if (j == 105) {
			int l = stream.readUnsignedByte();
			int k3 = anInt1268 + (l >> 4 & 7);
			int j6 = anInt1269 + (l & 7);
			int i9 = stream.readUnsignedWord();
			int l11 = stream.readUnsignedByte();
			int i14 = l11 >> 4 & 0xf;
			int i16 = l11 & 7;
			if (myPlayer.smallX[0] >= k3 - i14 && myPlayer.smallX[0] <= k3 + i14 && myPlayer.smallY[0] >= j6 - i14 && myPlayer.smallY[0] <= j6 + i14 && aBoolean848 && !lowMem && anInt1062 < 50) {
				anIntArray1207[anInt1062] = i9;
				anIntArray1241[anInt1062] = i16;
				anIntArray1250[anInt1062] = Sounds.anIntArray326[i9];
				anInt1062++;
			}
		}
		if (j == 215) {
			int i1 = stream.method435();
			int l3 = stream.method428();
			int k6 = anInt1268 + (l3 >> 4 & 7);
			int j9 = anInt1269 + (l3 & 7);
			int i12 = stream.method435();
			int j14 = stream.readUnsignedWord();
			if (k6 >= 0 && j9 >= 0 && k6 < 104 && j9 < 104 && i12 != unknownInt10) {
				Item class30_sub2_sub4_sub2_2 = new Item();
				class30_sub2_sub4_sub2_2.ID = i1;
				class30_sub2_sub4_sub2_2.anInt1559 = j14;
				if (groundArray[plane][k6][j9] == null)
					groundArray[plane][k6][j9] = new NodeList();
				groundArray[plane][k6][j9].insertHead(class30_sub2_sub4_sub2_2);
				spawnGroundItem(k6, j9);
			}
			return;
		}
		if (j == 156) {
			int j1 = stream.method426();
			int i4 = anInt1268 + (j1 >> 4 & 7);
			int l6 = anInt1269 + (j1 & 7);
			int k9 = stream.readUnsignedWord();
			if (i4 >= 0 && l6 >= 0 && i4 < 104 && l6 < 104) {
				NodeList class19 = groundArray[plane][i4][l6];
				if (class19 != null) {
					for (Item item = (Item) class19.reverseGetFirst(); item != null; item = (Item) class19.reverseGetNext()) {
						if (item.ID != (k9 & 0x7fff))
							continue;
						item.unlink();
						break;
					}

					if (class19.reverseGetFirst() == null)
						groundArray[plane][i4][l6] = null;
					spawnGroundItem(i4, l6);
				}
			}
			return;
		}
		if (j == 160) {
			int k1 = stream.method428();
			int j4 = anInt1268 + (k1 >> 4 & 7);
			int i7 = anInt1269 + (k1 & 7);
			int l9 = stream.method428();
			int j12 = l9 >> 2;
			int k14 = l9 & 3;
			int j16 = anIntArray1177[j12];
			int j17 = stream.method435();
			if (j4 >= 0 && i7 >= 0 && j4 < 103 && i7 < 103) {
				int j18 = intGroundArray[plane][j4][i7];
				int i19 = intGroundArray[plane][j4 + 1][i7];
				int l19 = intGroundArray[plane][j4 + 1][i7 + 1];
				int k20 = intGroundArray[plane][j4][i7 + 1];
				if (j16 == 0) {
					Object1 class10 = worldController.method296(plane, j4, i7);
					if (class10 != null) {
						int k21 = class10.uid >> 14 & 0x7fff;
						if (j12 == 2) {
							class10.aClass30_Sub2_Sub4_278 = new Animable_Sub5(k21, 4 + k14, 2, i19, l19, j18, k20, j17, false);
							class10.aClass30_Sub2_Sub4_279 = new Animable_Sub5(k21, k14 + 1 & 3, 2, i19, l19, j18, k20, j17, false);
						} else {
							class10.aClass30_Sub2_Sub4_278 = new Animable_Sub5(k21, k14, j12, i19, l19, j18, k20, j17, false);
						}
					}
				}
				if (j16 == 1) {
					Object2 class26 = worldController.method297(j4, i7, plane);
					if (class26 != null)
						class26.aClass30_Sub2_Sub4_504 = new Animable_Sub5(class26.uid >> 14 & 0x7fff, 0, 4, i19, l19, j18, k20, j17, false);
				}
				if (j16 == 2) {
					Object5 class28 = worldController.method298(j4, i7, plane);
					if (j12 == 11)
						j12 = 10;
					if (class28 != null)
						class28.aClass30_Sub2_Sub4_521 = new Animable_Sub5(class28.uid >> 14 & 0x7fff, k14, j12, i19, l19, j18, k20, j17, false);
				}
				if (j16 == 3) {
					Object3 class49 = worldController.method299(i7, j4, plane);
					if (class49 != null)
						class49.aClass30_Sub2_Sub4_814 = new Animable_Sub5(class49.uid >> 14 & 0x7fff, k14, 22, i19, l19, j18, k20, j17, false);
				}
			}
			return;
		}
		if (j == 147) {
			int l1 = stream.method428();
			int k4 = anInt1268 + (l1 >> 4 & 7);
			int j7 = anInt1269 + (l1 & 7);
			int i10 = stream.readUnsignedWord();
			byte byte0 = stream.method430();
			int l14 = stream.method434();
			byte byte1 = stream.method429();
			int k17 = stream.readUnsignedWord();
			int k18 = stream.method428();
			int j19 = k18 >> 2;
			int i20 = k18 & 3;
			int l20 = anIntArray1177[j19];
			byte byte2 = stream.readSignedByte();
			int l21 = stream.readUnsignedWord();
			byte byte3 = stream.method429();
			Player player;
			if (i10 == unknownInt10)
				player = myPlayer;
			else
				player = playerArray[i10];
			if (player != null) {
				ObjectDef class46 = ObjectDef.forID(l21);
				int i22 = intGroundArray[plane][k4][j7];
				int j22 = intGroundArray[plane][k4 + 1][j7];
				int k22 = intGroundArray[plane][k4 + 1][j7 + 1];
				int l22 = intGroundArray[plane][k4][j7 + 1];
				Model model = class46.method578(j19, i20, i22, j22, k22, l22, -1);
				if (model != null) {
					method130(k17 + 1, -1, 0, l20, j7, 0, plane, k4, l14 + 1);
					player.anInt1707 = l14 + loopCycle;
					player.anInt1708 = k17 + loopCycle;
					player.aModel_1714 = model;
					int i23 = class46.anInt744;
					int j23 = class46.anInt761;
					if (i20 == 1 || i20 == 3) {
						i23 = class46.anInt761;
						j23 = class46.anInt744;
					}
					player.anInt1711 = k4 * 128 + i23 * 64;
					player.anInt1713 = j7 * 128 + j23 * 64;
					player.anInt1712 = method42(plane, player.anInt1713, player.anInt1711);
					if (byte2 > byte0) {
						byte byte4 = byte2;
						byte2 = byte0;
						byte0 = byte4;
					}
					if (byte3 > byte1) {
						byte byte5 = byte3;
						byte3 = byte1;
						byte1 = byte5;
					}
					player.anInt1719 = k4 + byte2;
					player.anInt1721 = k4 + byte0;
					player.anInt1720 = j7 + byte3;
					player.anInt1722 = j7 + byte1;
				}
			}
		}
		if (j == 151) {
			int i2 = stream.method426();
			int l4 = anInt1268 + (i2 >> 4 & 7);
			int k7 = anInt1269 + (i2 & 7);
			int j10 = stream.method434();
			int k12 = stream.method428();
			int i15 = k12 >> 2;
			int k16 = k12 & 3;
			int l17 = anIntArray1177[i15];
			if (l4 >= 0 && k7 >= 0 && l4 < 104 && k7 < 104)
				method130(-1, j10, k16, l17, k7, i15, plane, l4, 0);
			return;
		}
		if (j == 4) {
			int j2 = stream.readUnsignedByte();
			int i5 = anInt1268 + (j2 >> 4 & 7);
			int l7 = anInt1269 + (j2 & 7);
			int k10 = stream.readUnsignedWord();
			int l12 = stream.readUnsignedByte();
			int j15 = stream.readUnsignedWord();
			if (i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104) {
				i5 = i5 * 128 + 64;
				l7 = l7 * 128 + 64;
				Animable_Sub3 class30_sub2_sub4_sub3 = new Animable_Sub3(plane, loopCycle, j15, k10, method42(plane, l7, i5) - l12, l7, i5);
				aClass19_1056.insertHead(class30_sub2_sub4_sub3);
			}
			return;
		}
		if (j == 44) {
			int k2 = stream.method436();
			int j5 = stream.readUnsignedWord();
			int i8 = stream.readUnsignedByte();
			int l10 = anInt1268 + (i8 >> 4 & 7);
			int i13 = anInt1269 + (i8 & 7);
			if (l10 >= 0 && i13 >= 0 && l10 < 104 && i13 < 104) {
				Item class30_sub2_sub4_sub2_1 = new Item();
				class30_sub2_sub4_sub2_1.ID = k2;
				class30_sub2_sub4_sub2_1.anInt1559 = j5;
				if (groundArray[plane][l10][i13] == null)
					groundArray[plane][l10][i13] = new NodeList();
				groundArray[plane][l10][i13].insertHead(class30_sub2_sub4_sub2_1);
				spawnGroundItem(l10, i13);
			}
			return;
		}
		if (j == 101) {
			int l2 = stream.method427();
			int k5 = l2 >> 2;
			int j8 = l2 & 3;
			int i11 = anIntArray1177[k5];
			int j13 = stream.readUnsignedByte();
			int k15 = anInt1268 + (j13 >> 4 & 7);
			int l16 = anInt1269 + (j13 & 7);
			if (k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104)
				method130(-1, -1, j8, i11, l16, k5, plane, k15, 0);
			return;
		}
		if (j == 117) {
			int i3 = stream.readUnsignedByte();
			int l5 = anInt1268 + (i3 >> 4 & 7);
			int k8 = anInt1269 + (i3 & 7);
			int j11 = l5 + stream.readSignedByte();
			int k13 = k8 + stream.readSignedByte();
			int l15 = stream.readSignedWord();
			int i17 = stream.readUnsignedWord();
			int i18 = stream.readUnsignedByte() * 4;
			int l18 = stream.readUnsignedByte() * 4;
			int k19 = stream.readUnsignedWord();
			int j20 = stream.readUnsignedWord();
			int i21 = stream.readUnsignedByte();
			int j21 = stream.readUnsignedByte();
			if (l5 >= 0 && k8 >= 0 && l5 < 104 && k8 < 104 && j11 >= 0 && k13 >= 0 && j11 < 104 && k13 < 104 && i17 != 65535) {
				l5 = l5 * 128 + 64;
				k8 = k8 * 128 + 64;
				j11 = j11 * 128 + 64;
				k13 = k13 * 128 + 64;
				Animable_Sub4 class30_sub2_sub4_sub4 = new Animable_Sub4(i21, l18, k19 + loopCycle, j20 + loopCycle, j21, plane, method42(plane, k8, l5) - i18, k8, l5, l15, i17);
				class30_sub2_sub4_sub4.method455(k19 + loopCycle, k13, method42(plane, k13, j11) - l18, j11);
				aClass19_1013.insertHead(class30_sub2_sub4_sub4);
			}
		}
	}

	private void method139(Stream stream) {
		stream.initBitAccess();
		int k = stream.readBits(8);
		if (k < npcCount) {
			for (int l = k; l < npcCount; l++)
				anIntArray840[anInt839++] = npcIndices[l];

		}
		if (k > npcCount) {
			Signlink.reporterror(myUsername + " Too many npcs");
			throw new RuntimeException("eek");
		}
		npcCount = 0;
		for (int i1 = 0; i1 < k; i1++) {
			int j1 = npcIndices[i1];
			Npc npc = npcArray[j1];
			int k1 = stream.readBits(1);
			if (k1 == 0) {
				npcIndices[npcCount++] = j1;
				npc.anInt1537 = loopCycle;
			} else {
				int l1 = stream.readBits(2);
				if (l1 == 0) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = j1;
				} else if (l1 == 1) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					int i2 = stream.readBits(3);
					npc.moveInDir(false, i2);
					int k2 = stream.readBits(1);
					if (k2 == 1)
						anIntArray894[anInt893++] = j1;
				} else if (l1 == 2) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					int j2 = stream.readBits(3);
					npc.moveInDir(true, j2);
					int l2 = stream.readBits(3);
					npc.moveInDir(true, l2);
					int i3 = stream.readBits(1);
					if (i3 == 1)
						anIntArray894[anInt893++] = j1;
				} else if (l1 == 3)
					anIntArray840[anInt839++] = j1;
			}
		}
	}

	private void method142(int i, int j, int k, int l, int i1, int j1, int k1) {
		if (i1 >= 1 && i >= 1 && i1 <= 102 && i <= 102) {
			if (lowMem && j != plane)
				return;
			int i2 = 0;
			if (j1 == 0)
				i2 = worldController.method300(j, i1, i);
			if (j1 == 1)
				i2 = worldController.method301(j, i1, i);
			if (j1 == 2)
				i2 = worldController.method302(j, i1, i);
			if (j1 == 3)
				i2 = worldController.method303(j, i1, i);
			if (i2 != 0) {
				int i3 = worldController.method304(j, i1, i, i2);
				int j2 = i2 >> 14 & 0x7fff;
				int k2 = i3 & 0x1f;
				int l2 = i3 >> 6;
				if (j1 == 0) {
					worldController.method291(i1, j, i, (byte) -119);
					ObjectDef class46 = ObjectDef.forID(j2);
					if (class46.aBoolean767)
						aClass11Array1230[j].method215(l2, k2, class46.aBoolean757, i1, i);
				}
				if (j1 == 1)
					worldController.method292(i, j, i1);
				if (j1 == 2) {
					worldController.method293(j, i1, i);
					ObjectDef class46_1 = ObjectDef.forID(j2);
					if (i1 + class46_1.anInt744 > 103 || i + class46_1.anInt744 > 103 || i1 + class46_1.anInt761 > 103 || i + class46_1.anInt761 > 103)
						return;
					if (class46_1.aBoolean767)
						aClass11Array1230[j].method216(l2, class46_1.anInt744, i1, i, class46_1.anInt761, class46_1.aBoolean757);
				}
				if (j1 == 3) {
					worldController.method294(j, i, i1);
					ObjectDef class46_2 = ObjectDef.forID(j2);
					if (class46_2.aBoolean767 && class46_2.hasActions)
						aClass11Array1230[j].method218(i, i1);
				}
			}
			if (k1 >= 0) {
				int j3 = j;
				if (j3 < 3 && (byteGroundArray[1][i1][i] & 2) == 2)
					j3++;
				ObjectManager.method188(worldController, k, i, l, j3, aClass11Array1230[j], intGroundArray, i1, k1, j);
			}
		}
	}

	private void updatePlayers(int i, Stream stream) {
		anInt839 = 0;
		anInt893 = 0;
		method117(stream);
		method134(stream);
		method91(stream, i);
		method49(stream);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (playerArray[l].anInt1537 != loopCycle)
				playerArray[l] = null;
		}

		if (stream.currentOffset != i) {
			Signlink.reporterror("Error packet size mismatch in getplayer pos:" + stream.currentOffset + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < playerCount; i1++)
			if (playerArray[playerIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in pl list - pos:" + i1 + " size:" + playerCount);
				throw new RuntimeException("eek");
			}

	}

	private void setCameraPos(int j, int k, int l, int i1, int j1, int k1) {
		int l1 = 2048 - k & 0x7ff;
		int i2 = 2048 - j1 & 0x7ff;
		int j2 = 0;
		int k2 = 0;
		int l2 = j;
		if (l1 != 0) {
			int i3 = Model.modelIntArray1[l1];
			int k3 = Model.modelIntArray2[l1];
			int i4 = k2 * k3 - l2 * i3 >> 16;
			l2 = k2 * i3 + l2 * k3 >> 16;
			k2 = i4;
		}
		if (i2 != 0) {
			int j3 = Model.modelIntArray1[i2];
			int l3 = Model.modelIntArray2[i2];
			int j4 = l2 * j3 + j2 * l3 >> 16;
			l2 = l2 * l3 - j2 * j3 >> 16;
			j2 = j4;
		}
		xCameraPos = l - j2;
		zCameraPos = i1 - k2;
		yCameraPos = k1 - l2;
		yCameraCurve = k;
		xCameraCurve = j1;
	}

	public static void updateStrings(String str, int i) {
		switch (i) {
		case 1675:
			sendFrame126(str, 17508);
			break;// Stab
		case 1676:
			sendFrame126(str, 17509);
			break;// Slash
		case 1677:
			sendFrame126(str, 17510);
			break;// Cursh
		case 1678:
			sendFrame126(str, 17511);
			break;// Magic
		case 1679:
			sendFrame126(str, 17512);
			break;// Range
		case 1680:
			sendFrame126(str, 17513);
			break;// Stab
		case 1681:
			sendFrame126(str, 17514);
			break;// Slash
		case 1682:
			sendFrame126(str, 17515);
			break;// Crush
		case 1683:
			sendFrame126(str, 17516);
			break;// Magic
		case 1684:
			sendFrame126(str, 17517);
			break;// Range
		case 1686:
			sendFrame126(str, 17518);
			break;// Strength
		case 1687:
			sendFrame126(str, 17519);
			break;// Prayer
		}
	}

	public static void sendFrame126(String str, int i) {
		RSInterface.interfaceCache[i].disabledMessage = str;
		if (RSInterface.interfaceCache[i].parentID == tabInterfaceIDs[tabID]) {
		}
	}

	public void sendPacket185(int button, int toggle, int type) {
		switch (type) {
		case 135:
			RSInterface class9 = RSInterface.interfaceCache[button];
			boolean flag8 = true;
			if (class9.contentType > 0)
				flag8 = promptUserForInput(class9);
			if (flag8) {
				stream.createFrame(185);
				stream.writeWord(button);
			}
			break;
		case 646:
			System.out.println("toggle = " + toggle);
			stream.createFrame(185);
			stream.writeWord(button);
			RSInterface class9_2 = RSInterface.interfaceCache[button];
			if (class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
				if (variousSettings[toggle] != class9_2.anIntArray212[0]) {
					int temp = variousSettings[toggle];
					variousSettings[toggle] = class9_2.anIntArray212[0];
					if (class9_2.parentID == 50000 && !interfaceIsSelected(class9_2)) {
						variousSettings[toggle] = temp;
					}
					updateConfigValues(toggle);
				}
			}
			break;
		case 169:
			stream.createFrame(185);
			stream.writeWord(button);
			RSInterface class9_3 = RSInterface.interfaceCache[button];
			if (class9_3.valueIndexArray != null && class9_3.valueIndexArray[0][0] == 5) {
				variousSettings[toggle] = 1 - variousSettings[toggle];
				updateConfigValues(toggle);
			}
			switch (button) {
			case 74214:
				System.out.println("toggle = " + toggle);
				if (toggle == 0)
					sendFrame36(173, toggle);
				if (toggle == 1)
					sendPacket185(153, 173, 646);
				break;
			}
			break;
		}
	}

	public void sendFrame36(int id, int state) {
		anIntArray1045[id] = state;
		if (variousSettings[id] != state) {
			variousSettings[id] = state;
			updateConfigValues(id);
			if (dialogID != -1)
				inputTaken = true;
		}
	}

	public void sendFrame219() {
		if (invOverlayInterfaceID != -1) {
			invOverlayInterfaceID = -1;
			tabAreaAltered = true;
		}
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
		}
		if (inputDialogState != 0) {
			inputDialogState = 0;
			inputTaken = true;
		}
		openInterfaceID = -1;
		aBoolean1149 = false;
	}

	public void sendFrame248(int interfaceID, int sideInterfaceID) {
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
		}
		if (inputDialogState != 0) {
			inputDialogState = 0;
			inputTaken = true;
		}
		openInterfaceID = interfaceID;
		invOverlayInterfaceID = sideInterfaceID;
		tabAreaAltered = true;
		aBoolean1149 = false;
	}

	private boolean parsePacket() {
		if (socketStream == null)
			return false;
		try {
			int i = socketStream.available();
			if (i == 0)
				return false;
			if (pktType == -1) {
				socketStream.flushInputStream(inStream.buffer, 1);
				pktType = inStream.buffer[0] & 0xff;
				if (encryption != null)
					pktType = pktType - encryption.getNextKey() & 0xff;
				pktSize = SizeConstants.packetSizes[pktType];
				i--;
			}
			if (pktSize == -1)
				if (i > 0) {
					socketStream.flushInputStream(inStream.buffer, 1);
					pktSize = inStream.buffer[0] & 0xff;
					i--;
				} else {
					return false;
				}
			if (pktSize == -2)
				if (i > 1) {
					socketStream.flushInputStream(inStream.buffer, 2);
					inStream.currentOffset = 0;
					pktSize = inStream.readUnsignedWord();
					i -= 2;
				} else {
					return false;
				}
			if (i < pktSize)
				return false;
			inStream.currentOffset = 0;
			socketStream.flushInputStream(inStream.buffer, pktSize);
			anInt1009 = 0;
			anInt843 = anInt842;
			anInt842 = anInt841;
			anInt841 = pktType;
			switch (pktType) {
			case 81:
				updatePlayers(pktSize, inStream);
				aBoolean1080 = false;
				pktType = -1;
				return true;

			case 176:
				daysSinceRecovChange = inStream.method427();
				unreadMessages = inStream.method435();
				membersInt = inStream.readUnsignedByte();
				anInt1193 = inStream.method440();
				daysSinceLastLogin = inStream.readUnsignedWord();
				if (anInt1193 != 0 && openInterfaceID == -1) {
					Signlink.dnslookup(TextClass.method586(anInt1193));
					clearTopInterfaces();
					char c = '\u028A';
					if (daysSinceRecovChange != 201 || membersInt == 1)
						c = '\u028F';
					reportAbuseInput = "";
					canMute = false;
					for (int k9 = 0; k9 < RSInterface.interfaceCache.length; k9++) {
						if (RSInterface.interfaceCache[k9] == null || RSInterface.interfaceCache[k9].contentType != c)
							continue;
						openInterfaceID = RSInterface.interfaceCache[k9].parentID;

					}
				}
				pktType = -1;
				return true;

			case 64:
				anInt1268 = inStream.method427();
				anInt1269 = inStream.method428();
				for (int j = anInt1268; j < anInt1268 + 8; j++) {
					for (int l9 = anInt1269; l9 < anInt1269 + 8; l9++)
						if (groundArray[plane][j][l9] != null) {
							groundArray[plane][j][l9] = null;
							spawnGroundItem(j, l9);
						}
				}
				for (Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext())
					if (class30_sub1.anInt1297 >= anInt1268 && class30_sub1.anInt1297 < anInt1268 + 8 && class30_sub1.anInt1298 >= anInt1269 && class30_sub1.anInt1298 < anInt1269 + 8 && class30_sub1.anInt1295 == plane)
						class30_sub1.anInt1294 = 0;
				pktType = -1;
				return true;

			case 185:
				int k = inStream.method436();
				RSInterface.interfaceCache[k].anInt233 = 3;
				if (myPlayer.desc == null)
					RSInterface.interfaceCache[k].mediaID = (myPlayer.anIntArray1700[0] << 25) + (myPlayer.anIntArray1700[4] << 20) + (myPlayer.equipment[0] << 15) + (myPlayer.equipment[8] << 10) + (myPlayer.equipment[11] << 5) + myPlayer.equipment[1];
				else
					RSInterface.interfaceCache[k].mediaID = (int) (0x12345678L + myPlayer.desc.interfaceType);
				pktType = -1;
				return true;

			/* Clan chat packet */
			case 217:
				try {
					clanUsername = inStream.readString();
					clanMessage = TextInput.processText(inStream.readString());
					clanTitle = inStream.readString();
					channelRights = inStream.readUnsignedWord();
					pushMessage(clanMessage, 16, clanUsername);
				} catch (Exception e) {
					e.printStackTrace();
				}
				pktType = -1;
				return true;

			case 107:
				aBoolean1160 = false;
				for (int l = 0; l < 5; l++)
					aBooleanArray876[l] = false;
				xpCounter = 0;
				pktType = -1;
				return true;

			case 72:
				int i1 = inStream.method434();
				RSInterface class9 = RSInterface.interfaceCache[i1];
				for (int k15 = 0; k15 < class9.inv.length; k15++) {
					class9.inv[k15] = -1;
					class9.inv[k15] = 0;
				}
				pktType = -1;
				return true;

			case 214:
				ignoreCount = pktSize / 8;
				for (int j1 = 0; j1 < ignoreCount; j1++)
					ignoreListAsLongs[j1] = inStream.readQWord();
				pktType = -1;
				return true;

			case 166:
				aBoolean1160 = true;
				anInt1098 = inStream.readUnsignedByte();
				anInt1099 = inStream.readUnsignedByte();
				anInt1100 = inStream.readUnsignedWord();
				anInt1101 = inStream.readUnsignedByte();
				anInt1102 = inStream.readUnsignedByte();
				if (anInt1102 >= 100) {
					xCameraPos = anInt1098 * 128 + 64;
					yCameraPos = anInt1099 * 128 + 64;
					zCameraPos = method42(plane, yCameraPos, xCameraPos) - anInt1100;
				}
				pktType = -1;
				return true;

			case 134:
				int k1 = inStream.readUnsignedByte();
				inStream.readUnsignedByte();
				int i10 = inStream.method439();
				int l15 = inStream.readUnsignedByte();
				currentExp[k1] = i10;
				currentStats[k1] = l15;
				maxStats[k1] = 1;
				for (int k20 = 0; k20 < 98; k20++)
					if (i10 >= Skills.EXP_FOR_LEVEL[k20])
						maxStats[k1] = k20 + 2;
				pktType = -1;
				return true;

			case 71:
				int l1 = inStream.readUnsignedWord();
				int j10 = inStream.method426();
				if (l1 == 65535)
					l1 = -1;
				tabInterfaceIDs[j10] = l1;
				tabAreaAltered = true;
				pktType = -1;
				return true;

			case 74:
				int i2 = inStream.method434();
				if (i2 == 65535)
					i2 = -1;
				if (i2 != currentSong && musicEnabled && !lowMem && prevSong == 0) {
					nextSong = i2;
					songChanging = true;
					onDemandFetcher.method558(2, nextSong);
				}
				currentSong = i2;
				pktType = -1;
				return true;

			case 121:
				int j2 = inStream.method436();
				int k10 = inStream.method435();
				if (musicEnabled && !lowMem) {
					nextSong = j2;
					songChanging = false;
					onDemandFetcher.method558(2, nextSong);
					prevSong = k10;
				}
				pktType = -1;
				return true;

			case 109:
				resetLogout();
				pktType = -1;
				return false;

			case 70:
				int k2 = inStream.readSignedWord();
				int l10 = inStream.method437();
				int i16 = inStream.method434();
				RSInterface class9_5 = RSInterface.interfaceCache[i16];
				class9_5.anInt263 = k2;
				class9_5.anInt265 = l10;
				pktType = -1;
				return true;

			case 73:
			case 241:
				int l2 = anInt1069;
				int i11 = anInt1070;
				if (pktType == 73) {
					l2 = inStream.method435();
					i11 = inStream.readUnsignedWord();
					aBoolean1159 = false;
				}
				if (pktType == 241) {
					i11 = inStream.method435();
					inStream.initBitAccess();
					for (int j16 = 0; j16 < 4; j16++) {
						for (int l20 = 0; l20 < 13; l20++) {
							for (int j23 = 0; j23 < 13; j23++) {
								int i26 = inStream.readBits(1);
								if (i26 == 1)
									anIntArrayArrayArray1129[j16][l20][j23] = inStream.readBits(26);
								else
									anIntArrayArrayArray1129[j16][l20][j23] = -1;
							}
						}
					}
					inStream.finishBitAccess();
					l2 = inStream.readUnsignedWord();
					aBoolean1159 = true;
				}
				if (anInt1069 == l2 && anInt1070 == i11 && loadingStage == 2) {
					pktType = -1;
					return true;
				}
				anInt1069 = l2;
				anInt1070 = i11;
				baseX = (anInt1069 - 6) * 8;
				baseY = (anInt1070 - 6) * 8;
				aBoolean1141 = (anInt1069 / 8 == 48 || anInt1069 / 8 == 49) && anInt1070 / 8 == 48;
				if (anInt1069 / 8 == 48 && anInt1070 / 8 == 148)
					aBoolean1141 = true;
				loadingStage = 1;
				aLong824 = System.currentTimeMillis();
				aRSImageProducer_1165.initDrawingArea();
				drawLoadingMessages(1, "Loading - please wait.", null);
				aRSImageProducer_1165.drawGraphics(frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics, frameMode == ScreenMode.FIXED ? 4 : 0);
				if (pktType == 73) {
					int k16 = 0;
					for (int i21 = (anInt1069 - 6) / 8; i21 <= (anInt1069 + 6) / 8; i21++) {
						for (int k23 = (anInt1070 - 6) / 8; k23 <= (anInt1070 + 6) / 8; k23++)
							k16++;
					}
					aByteArrayArray1183 = new byte[k16][];
					aByteArrayArray1247 = new byte[k16][];
					anIntArray1234 = new int[k16];
					anIntArray1235 = new int[k16];
					anIntArray1236 = new int[k16];
					k16 = 0;
					for (int l23 = (anInt1069 - 6) / 8; l23 <= (anInt1069 + 6) / 8; l23++) {
						for (int j26 = (anInt1070 - 6) / 8; j26 <= (anInt1070 + 6) / 8; j26++) {
							anIntArray1234[k16] = (l23 << 8) + j26;
							if (aBoolean1141 && (j26 == 49 || j26 == 149 || j26 == 147 || l23 == 50 || l23 == 49 && j26 == 47)) {
								anIntArray1235[k16] = -1;
								anIntArray1236[k16] = -1;
								k16++;
							} else {
								int k28 = anIntArray1235[k16] = onDemandFetcher.method562(0, j26, l23);
								if (k28 != -1)
									onDemandFetcher.method558(3, k28);
								int j30 = anIntArray1236[k16] = onDemandFetcher.method562(1, j26, l23);
								if (j30 != -1)
									onDemandFetcher.method558(3, j30);
								k16++;
							}
						}
					}
				}
				if (pktType == 241) {
					int l16 = 0;
					int ai[] = new int[676];
					for (int i24 = 0; i24 < 4; i24++) {
						for (int k26 = 0; k26 < 13; k26++) {
							for (int l28 = 0; l28 < 13; l28++) {
								int k30 = anIntArrayArrayArray1129[i24][k26][l28];
								if (k30 != -1) {
									int k31 = k30 >> 14 & 0x3ff;
									int i32 = k30 >> 3 & 0x7ff;
									int k32 = (k31 / 8 << 8) + i32 / 8;
									for (int j33 = 0; j33 < l16; j33++) {
										if (ai[j33] != k32)
											continue;
										k32 = -1;

									}
									if (k32 != -1)
										ai[l16++] = k32;
								}
							}
						}
					}
					aByteArrayArray1183 = new byte[l16][];
					aByteArrayArray1247 = new byte[l16][];
					anIntArray1234 = new int[l16];
					anIntArray1235 = new int[l16];
					anIntArray1236 = new int[l16];
					for (int l26 = 0; l26 < l16; l26++) {
						int i29 = anIntArray1234[l26] = ai[l26];
						int l30 = i29 >> 8 & 0xff;
						int l31 = i29 & 0xff;
						int j32 = anIntArray1235[l26] = onDemandFetcher.method562(0, l31, l30);
						if (j32 != -1)
							onDemandFetcher.method558(3, j32);
						int i33 = anIntArray1236[l26] = onDemandFetcher.method562(1, l31, l30);
						if (i33 != -1)
							onDemandFetcher.method558(3, i33);
					}
				}
				int i17 = baseX - anInt1036;
				int j21 = baseY - anInt1037;
				anInt1036 = baseX;
				anInt1037 = baseY;
				for (int j24 = 0; j24 < 16384; j24++) {
					Npc npc = npcArray[j24];
					if (npc != null) {
						for (int j29 = 0; j29 < 10; j29++) {
							npc.smallX[j29] -= i17;
							npc.smallY[j29] -= j21;
						}
						npc.x -= i17 * 128;
						npc.y -= j21 * 128;
					}
				}
				for (int i27 = 0; i27 < maxPlayers; i27++) {
					Player player = playerArray[i27];
					if (player != null) {
						for (int i31 = 0; i31 < 10; i31++) {
							player.smallX[i31] -= i17;
							player.smallY[i31] -= j21;
						}
						player.x -= i17 * 128;
						player.y -= j21 * 128;
					}
				}
				aBoolean1080 = true;
				byte byte1 = 0;
				byte byte2 = 104;
				byte byte3 = 1;
				if (i17 < 0) {
					byte1 = 103;
					byte2 = -1;
					byte3 = -1;
				}
				byte byte4 = 0;
				byte byte5 = 104;
				byte byte6 = 1;
				if (j21 < 0) {
					byte4 = 103;
					byte5 = -1;
					byte6 = -1;
				}
				for (int k33 = byte1; k33 != byte2; k33 += byte3) {
					for (int l33 = byte4; l33 != byte5; l33 += byte6) {
						int i34 = k33 + i17;
						int j34 = l33 + j21;
						for (int k34 = 0; k34 < 4; k34++)
							if (i34 >= 0 && j34 >= 0 && i34 < 104 && j34 < 104)
								groundArray[k34][k33][l33] = groundArray[k34][i34][j34];
							else
								groundArray[k34][k33][l33] = null;
					}
				}
				for (Class30_Sub1 class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1_1 != null; class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
					class30_sub1_1.anInt1297 -= i17;
					class30_sub1_1.anInt1298 -= j21;
					if (class30_sub1_1.anInt1297 < 0 || class30_sub1_1.anInt1298 < 0 || class30_sub1_1.anInt1297 >= 104 || class30_sub1_1.anInt1298 >= 104)
						class30_sub1_1.unlink();
				}
				if (destX != 0) {
					destX -= i17;
					destY -= j21;
				}
				aBoolean1160 = false;
				pktType = -1;
				return true;

			case 208:
				int i3 = inStream.method437();
				if (i3 >= 0) {
					method60(i3);
					walkableInterfaceMode = true;
				} else
					walkableInterfaceMode = false;
				anInt1018 = i3;
				pktType = -1;
				return true;

			case 99:
				anInt1021 = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 75:
				int j3 = inStream.method436();
				int j11 = inStream.method436();
				RSInterface.interfaceCache[j11].anInt233 = 2;
				RSInterface.interfaceCache[j11].mediaID = j3;
				pktType = -1;
				return true;

			case 114:
				anInt1104 = inStream.method434() * 30;
				pktType = -1;
				return true;

			case 60:
				anInt1269 = inStream.readUnsignedByte();
				anInt1268 = inStream.method427();
				while (inStream.currentOffset < pktSize) {
					int k3 = inStream.readUnsignedByte();
					method137(inStream, k3);
				}
				pktType = -1;
				return true;

			case 35:
				int l3 = inStream.readUnsignedByte();
				int k11 = inStream.readUnsignedByte();
				int j17 = inStream.readUnsignedByte();
				int k21 = inStream.readUnsignedByte();
				aBooleanArray876[l3] = true;
				anIntArray873[l3] = k11;
				anIntArray1203[l3] = j17;
				anIntArray928[l3] = k21;
				anIntArray1030[l3] = 0;
				pktType = -1;
				return true;

			case 174:
				int i4 = inStream.readUnsignedWord();
				int l11 = inStream.readUnsignedByte();
				int k17 = inStream.readUnsignedWord();
				if (aBoolean848 && !lowMem && anInt1062 < 50) {
					anIntArray1207[anInt1062] = i4;
					anIntArray1241[anInt1062] = l11;
					anIntArray1250[anInt1062] = k17 + Sounds.anIntArray326[i4];
					anInt1062++;
				}
				pktType = -1;
				return true;

			case 104:
				int j4 = inStream.method427();
				int i12 = inStream.method426();
				String s6 = inStream.readString();
				if (j4 >= 1 && j4 <= 5) {
					if (s6.equalsIgnoreCase("null"))
						s6 = null;
					atPlayerActions[j4 - 1] = s6;
					atPlayerArray[j4 - 1] = i12 == 0;
				}
				pktType = -1;
				return true;

			case 78:
				destX = 0;
				pktType = -1;
				return true;

			case 253:
				String s = inStream.readString();
				if (s.endsWith(":tradereq:")) {
					String s3 = s.substring(0, s.indexOf(":"));
					long l17 = TextClass.longForName(s3);
					boolean flag2 = false;
					for (int j27 = 0; j27 < ignoreCount; j27++) {
						if (ignoreListAsLongs[j27] != l17)
							continue;
						flag2 = true;

					}
					if (!flag2 && anInt1251 == 0)
						pushMessage("wishes to trade with you.", 4, s3);
					
				} else if (s.startsWith(":updateSettings:")) {
					SettingHandler.updateText();
				} else if (s.startsWith(":defaultSettings:")) {
					SettingHandler.defaultSettings();
				} else if (s.startsWith(":saveSettings:")) {
					SettingHandler.save();			
				} else if (s.startsWith(":transparentTab:")) {
					if (frameMode != ScreenMode.FIXED) {
						transparentTabArea = !transparentTabArea;
					} else {
						pushMessage("Settings are not applicable in fixed mode!", 0, "");
					}	
				} else if (s.startsWith(":transparentChatbox:")) {
					if (frameMode != ScreenMode.FIXED) {
						changeChatArea = !changeChatArea;
					} else {
						pushMessage("Settings are not applicable in fixed mode!", 0, "");
					}
				} else if (s.startsWith(":sideStones:")) {
					if (frameMode != ScreenMode.FIXED) {
						changeTabArea = !changeTabArea;
					} else {
						pushMessage("Settings are not applicable in fixed mode!", 0, "");
					}
				} else if (s.startsWith(":prestigeColorsTrue:")) {
					Configuration.enablePrestigeColors = true;
					SettingHandler.updateText();
				} else if (s.startsWith(":prestigeColorsFalse:")) {
					Configuration.enablePrestigeColors = false;
					SettingHandler.updateText();
					
				} else if (s.endsWith(":clan:")) {
					String s4 = s.substring(0, s.indexOf(":"));
					TextClass.longForName(s4);
					pushMessage("Clan: ", 8, s4);
				} else if (s.endsWith("#url#")) {
					String link = s.substring(0, s.indexOf("#"));
					pushMessage("Join us at: ", 9, link);
				} else if (s.endsWith(":duelreq:")) {
					String s4 = s.substring(0, s.indexOf(":"));
					long l18 = TextClass.longForName(s4);
					boolean flag3 = false;
					for (int k27 = 0; k27 < ignoreCount; k27++) {
						if (ignoreListAsLongs[k27] != l18)
							continue;
						flag3 = true;

					}
					if (!flag3 && anInt1251 == 0)
						pushMessage("wishes to duel with you.", 8, s4);
				} else if (s.endsWith(":chalreq:")) {
					String s5 = s.substring(0, s.indexOf(":"));
					long l19 = TextClass.longForName(s5);
					boolean flag4 = false;
					for (int l27 = 0; l27 < ignoreCount; l27++) {
						if (ignoreListAsLongs[l27] != l19)
							continue;
						flag4 = true;

					}
					if (!flag4 && anInt1251 == 0) {
						String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
						pushMessage(s8, 8, s5);
					}
				} else {
					pushMessage(s, 0, "");
				}
				pktType = -1;
				return true;

			case 1:
				for (int k4 = 0; k4 < playerArray.length; k4++)
					if (playerArray[k4] != null)
						playerArray[k4].anim = -1;
				for (int j12 = 0; j12 < npcArray.length; j12++)
					if (npcArray[j12] != null)
						npcArray[j12].anim = -1;
				pktType = -1;
				return true;

			case 50:
				long l4 = inStream.readQWord();
				int i18 = inStream.readUnsignedByte();
				String s7 = TextClass.fixName(TextClass.nameForLong(l4));
				for (int k24 = 0; k24 < friendsCount; k24++) {
					if (l4 != friendsListAsLongs[k24])
						continue;
					if (friendsNodeIDs[k24] != i18) {
						friendsNodeIDs[k24] = i18;
						if (i18 >= 2) {
							pushMessage(s7 + " has logged in.", 5, "");
						}
						if (i18 <= 1) {
							pushMessage(s7 + " has logged out.", 5, "");
						}
					}
					s7 = null;

				}
				if (s7 != null && friendsCount < 200) {
					friendsListAsLongs[friendsCount] = l4;
					friendsList[friendsCount] = s7;
					friendsNodeIDs[friendsCount] = i18;
					friendsCount++;
				}
				for (boolean flag6 = false; !flag6;) {
					flag6 = true;
					for (int k29 = 0; k29 < friendsCount - 1; k29++)
						if (friendsNodeIDs[k29] != nodeID && friendsNodeIDs[k29 + 1] == nodeID || friendsNodeIDs[k29] == 0 && friendsNodeIDs[k29 + 1] != 0) {
							int j31 = friendsNodeIDs[k29];
							friendsNodeIDs[k29] = friendsNodeIDs[k29 + 1];
							friendsNodeIDs[k29 + 1] = j31;
							String s10 = friendsList[k29];
							friendsList[k29] = friendsList[k29 + 1];
							friendsList[k29 + 1] = s10;
							long l32 = friendsListAsLongs[k29];
							friendsListAsLongs[k29] = friendsListAsLongs[k29 + 1];
							friendsListAsLongs[k29 + 1] = l32;
							flag6 = false;
						}
				}
				pktType = -1;
				return true;

			case 110:
				if (tabID == 12) {
				}
				energy = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 254:
				anInt855 = inStream.readUnsignedByte();
				if (anInt855 == 1)
					anInt1222 = inStream.readUnsignedWord();
				if (anInt855 >= 2 && anInt855 <= 6) {
					if (anInt855 == 2) {
						anInt937 = 64;
						anInt938 = 64;
					}
					if (anInt855 == 3) {
						anInt937 = 0;
						anInt938 = 64;
					}
					if (anInt855 == 4) {
						anInt937 = 128;
						anInt938 = 64;
					}
					if (anInt855 == 5) {
						anInt937 = 64;
						anInt938 = 0;
					}
					if (anInt855 == 6) {
						anInt937 = 64;
						anInt938 = 128;
					}
					anInt855 = 2;
					anInt934 = inStream.readUnsignedWord();
					anInt935 = inStream.readUnsignedWord();
					anInt936 = inStream.readUnsignedByte();
				}
				if (anInt855 == 10)
					anInt933 = inStream.readUnsignedWord();
				pktType = -1;
				return true;

			case 248:
				int i5 = inStream.method435();
				int k12 = inStream.readUnsignedWord();
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				openInterfaceID = i5;
				invOverlayInterfaceID = k12;
				tabAreaAltered = true;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			case 79:
				int j5 = inStream.method434();
				int l12 = inStream.method435();
				RSInterface class9_3 = RSInterface.interfaceCache[j5];
				if (class9_3 != null && class9_3.type == 0) {
					if (l12 < 0)
						l12 = 0;
					if (l12 > class9_3.scrollMax - class9_3.height)
						l12 = class9_3.scrollMax - class9_3.height;
					class9_3.scrollPosition = l12;
				}
				pktType = -1;
				return true;

			case 68:
				for (int k5 = 0; k5 < variousSettings.length; k5++)
					if (variousSettings[k5] != anIntArray1045[k5]) {
						variousSettings[k5] = anIntArray1045[k5];
						updateConfigValues(k5);
					}
				pktType = -1;
				return true;

			case 173:
				try {
					pushKill(inStream.readString(), inStream.readString(), inStream.readUnsignedWord(), inStream.readUnsignedByte() == 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				pktType = -1;
				return true;
				
			case 175:
				try {			
					pushFeed(inStream.readString(), inStream.readUnsignedWord(), inStream.readUnsignedWord());		
				} catch (Exception e) {
					e.printStackTrace();
				}
				pktType = -1;
				return true;

			case 196:
				final long usernameHash = inStream.readQWord();
				int j18 = inStream.readDWord();
				int rights = inStream.readUnsignedByte();
				boolean flag5 = false;
				if (rights <= 1) {
					for (int l29 = 0; l29 < ignoreCount; l29++) {
						if (ignoreListAsLongs[l29] != usernameHash)
							continue;
						flag5 = true;

					}
				}
				if (!flag5 && anInt1251 == 0)
					try {
						anIntArray1240[anInt1169] = j18;
						anInt1169 = (anInt1169 + 1) % 100;
						String s9 = TextInput.method525(pktSize - 13, inStream);
						if (Configuration.showNotifications) {
							if (gameFrame != null) {
								if (windowFlasher == null) {
									windowFlasher = new WindowFlasher(gameFrame);
								}
								windowFlasher.flashWindow();
							}
						}
						if (rights >= 1) {
							pushMessage(s9, 7, "@cr" + rights + "@" + TextClass.fixName(TextClass.nameForLong(usernameHash)));
						} else {
							pushMessage(s9, 3, TextClass.fixName(TextClass.nameForLong(usernameHash)));
						}
					} catch (Exception exception1) {
						Signlink.reporterror("cde1");
					}
				pktType = -1;
				return true;

			case 85:
				anInt1269 = inStream.method427();
				anInt1268 = inStream.method427();
				pktType = -1;
				return true;

			case 24:
				anInt1054 = inStream.method428();
				if (anInt1054 == tabID) {
					if (anInt1054 == 3)
						tabID = 1;
					else
						tabID = 3;
				}
				pktType = -1;
				return true;

			case 246:
				int i6 = inStream.method434();
				int i13 = inStream.readUnsignedWord();
				int k18 = inStream.readUnsignedWord();
				if (k18 == 65535) {
					RSInterface.interfaceCache[i6].anInt233 = 0;
					pktType = -1;
					return true;
				} else {
					ItemDef itemDef = ItemDef.forID(k18);
					RSInterface.interfaceCache[i6].anInt233 = 4;
					RSInterface.interfaceCache[i6].mediaID = k18;
					RSInterface.interfaceCache[i6].modelRotation1 = itemDef.modelRotationY;
					RSInterface.interfaceCache[i6].modelRotation2 = itemDef.modelRotationX;
					RSInterface.interfaceCache[i6].modelZoom = (itemDef.modelZoom * 100) / i13;
					pktType = -1;
					return true;
				}

			case 171:
				boolean flag1 = inStream.readUnsignedByte() == 1;
				int j13 = inStream.readUnsignedWord();
				RSInterface.interfaceCache[j13].isMouseoverTriggered = flag1;
				pktType = -1;
				return true;

			case 142:
				int j6 = inStream.method434();
				method60(j6);
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				invOverlayInterfaceID = j6;
				tabAreaAltered = true;
				openInterfaceID = -1;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			case 126:
				try {
					String text = inStream.readString();
					int frame = inStream.method435();
					if (text.startsWith("www.") || text.startsWith("http://www.")) {
						openURL(text);
					}
					if (text.startsWith(":quicks:"))
						clickedQuickPrayers = text.substring(8).equalsIgnoreCase("on") ? true : false;
					if (text.startsWith(":prayer:"))
						prayerBook = text.substring(8);
					updateStrings(text, frame);
					sendFrame126(text, frame);
					if (frame >= 18144 && frame <= 18244) {
						clanList[frame - 18144] = text;
					}
					if (frame == 8135) {
						RSInterface.interfaceCache[8135].opacity = 108;
					}
				} catch (Exception e) {
				}
				pktType = -1;
				return true;

			case 124:
				try {
					int npc = inStream.readUnsignedByte();
					int size = inStream.readUnsignedByte();
					if (npc <= 0) {
						npcDisplay = null;
						pktType = -1;
						return true;
					}
					npcDisplay = new Npc();
					npcDisplay.desc = EntityDef.forID(npc, false);
					npcDisplay.anInt1540 = npcDisplay.desc.aByte68;
					npcDisplay.anInt1504 = npcDisplay.desc.anInt79;
					npcDisplay.desc.anInt86 = size;
					npcDisplay.desc.anInt91 = size;
					npcDisplay.anInt1511 = npcDisplay.desc.standAnim;
				} catch (Exception e) {
					e.printStackTrace();
				}
				pktType = -1;
				return true;

			case 127:
				try {
					int skill = inStream.readSignedByte();
					int exp = inStream.readDWord();
					xpCounter = inStream.readDWord();
					addXP(skill, exp);
				} catch (Exception e) {
				}
				pktType = -1;
				return true;

			case 125:
				try {
					int skill = inStream.readSignedByte();
					statsSkillGoal[skill][2] = inStream.readDWord();
					statsSkillGoal[skill][0] = inStream.readDWord();
					statsSkillGoal[skill][1] = inStream.readSignedByte();
				} catch (Exception e) {
				}
				pktType = -1;
				return true;

			case 202:
				String msg = inStream.readString();
				int col = inStream.readDWord();
				BannerManager.addBanner(msg, col);
				pktType = -1;
				return true;

			case 201:
				try {
					playerIndex = inStream.readUnsignedWord();
				} catch (Exception e) {
				}
				pktType = -1;
				return true;

			case 206:
				publicChatMode = inStream.readUnsignedByte();
				privateChatMode = inStream.readUnsignedByte();
				tradeMode = inStream.readUnsignedByte();
				inputTaken = true;
				pktType = -1;
				return true;

			case 240:
				if (tabID == 12) {
				}
				weight = inStream.readSignedWord();
				pktType = -1;
				return true;

			case 8:
				int k6 = inStream.method436();
				int l13 = inStream.readUnsignedWord();
				RSInterface.interfaceCache[k6].anInt233 = 1;
				RSInterface.interfaceCache[k6].mediaID = l13;
				pktType = -1;
				return true;

			case 122:
				int intId = inStream.method436();
				int r = inStream.readUnsignedByte();
				int g = inStream.readUnsignedByte();
				int b = inStream.readUnsignedByte();
				if (RSInterface.interfaceCache[intId] != null) {
					RSInterface.interfaceCache[intId].textColor = r << 16 | g << 8 | b;
				}
				pktType = -1;
				return true;

			case 53:
				int i7 = inStream.readUnsignedWord();
				RSInterface class9_1 = RSInterface.interfaceCache[i7];
				int j19 = inStream.readUnsignedWord();
				for (int j22 = 0; j22 < j19; j22++) {
					int i25 = inStream.readUnsignedByte();
					if (i25 == 255)
						i25 = inStream.method440();
					class9_1.inv[j22] = inStream.method436();
					class9_1.invStackSizes[j22] = i25;
				}
				for (int j25 = j19; j25 < class9_1.inv.length; j25++) {
					class9_1.inv[j25] = 0;
					class9_1.invStackSizes[j25] = 0;
				}
				if (class9_1.contentType == 206) {
					for (int tab = 0; tab < 10; tab++) {
						int amount = inStream.readSignedByte() << 8 | inStream.readUnsignedWord();
						tabAmounts[tab] = amount;
					}

					if (variousSettings[1012] == 1) {
						RSInterface bank = RSInterface.interfaceCache[5382];
						Arrays.fill(bankInvTemp, 0);
						Arrays.fill(bankStackTemp, 0);
						for (int slot = 0, bankSlot = 0; slot < bank.inv.length; slot++) {
							if (bank.inv[slot] - 1 > 0) {
								if (ItemDef.forID(bank.inv[slot] - 1).name.toLowerCase().contains(promptInput.toLowerCase())) {
									bankInvTemp[bankSlot] = bank.inv[slot];
									bankStackTemp[bankSlot++] = bank.invStackSizes[slot];
								}
							}
						}
					}
				}
				pktType = -1;
				return true;

			case 230:
				int j7 = inStream.method435();
				int j14 = inStream.readUnsignedWord();
				int k19 = inStream.readUnsignedWord();
				int k22 = inStream.method436();
				RSInterface.interfaceCache[j14].modelRotation1 = k19;
				RSInterface.interfaceCache[j14].modelRotation2 = k22;
				RSInterface.interfaceCache[j14].modelZoom = j7;
				pktType = -1;
				return true;

			case 221:
				anInt900 = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 177:
				aBoolean1160 = true;
				anInt995 = inStream.readUnsignedByte();
				anInt996 = inStream.readUnsignedByte();
				anInt997 = inStream.readUnsignedWord();
				anInt998 = inStream.readUnsignedByte();
				anInt999 = inStream.readUnsignedByte();
				if (anInt999 >= 100) {
					int k7 = anInt995 * 128 + 64;
					int k14 = anInt996 * 128 + 64;
					int i20 = method42(plane, k14, k7) - anInt997;
					int l22 = k7 - xCameraPos;
					int k25 = i20 - zCameraPos;
					int j28 = k14 - yCameraPos;
					int i30 = (int) Math.sqrt(l22 * l22 + j28 * j28);
					yCameraCurve = (int) (Math.atan2(k25, i30) * 325.94900000000001D) & 0x7ff;
					xCameraCurve = (int) (Math.atan2(l22, j28) * -325.94900000000001D) & 0x7ff;
					if (yCameraCurve < 128)
						yCameraCurve = 128;
					if (yCameraCurve > 383)
						yCameraCurve = 383;
				}
				pktType = -1;
				return true;

			case 249:
				anInt1046 = inStream.method426();
				unknownInt10 = inStream.method436();
				pktType = -1;
				return true;

			case 65:
				updateNPCs(inStream, pktSize);
				pktType = -1;
				return true;

			case 27:
				messagePromptRaised = false;
				inputDialogState = 1;
				amountOrNameInput = "";
				inputTaken = true;
				pktType = -1;
				return true;

			case 187:
				messagePromptRaised = false;
				inputDialogState = 2;
				amountOrNameInput = "";
				inputTaken = true;
				pktType = -1;
				return true;

			case 97:
				int l7 = inStream.readUnsignedWord();
				method60(l7);
				if (invOverlayInterfaceID != -1) {
					invOverlayInterfaceID = -1;
					tabAreaAltered = true;
				}
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				openInterfaceID = l7;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			case 218:
				int i8 = inStream.method438();
				dialogID = i8;
				inputTaken = true;
				pktType = -1;
				return true;

			case 87:
				int j8 = inStream.method434();
				int l14 = inStream.method439();
				anIntArray1045[j8] = l14;
				if (variousSettings[j8] != l14) {
					variousSettings[j8] = l14;
					updateConfigValues(j8);
					if (dialogID != -1)
						inputTaken = true;
				}
				pktType = -1;
				return true;

			case 36:
				int k8 = inStream.method434();
				byte byte0 = inStream.readSignedByte();
				anIntArray1045[k8] = byte0;
				if (variousSettings[k8] != byte0) {
					variousSettings[k8] = byte0;
					updateConfigValues(k8);
					if (dialogID != -1)
						inputTaken = true;
				}
				pktType = -1;
				return true;

			case 61:
				anInt1055 = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 200:
				int l8 = inStream.readUnsignedWord();
				int i15 = inStream.readSignedWord();
				RSInterface class9_4 = RSInterface.interfaceCache[l8];
				class9_4.anInt257 = i15;
				pktType = -1;
				return true;

			case 219:
				if (invOverlayInterfaceID != -1) {
					invOverlayInterfaceID = -1;
					tabAreaAltered = true;
				}
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				openInterfaceID = -1;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			case 34:
				int i9 = inStream.readUnsignedWord();
				RSInterface class9_2 = RSInterface.interfaceCache[i9];
				while (inStream.currentOffset < pktSize) {
					int j20 = inStream.method422();
					int i23 = inStream.readUnsignedWord();
					int l25 = inStream.readUnsignedByte();
					if (l25 == 255)
						l25 = inStream.readDWord();
					if (j20 >= 0 && j20 < class9_2.inv.length) {
						class9_2.inv[j20] = i23;
						class9_2.invStackSizes[j20] = l25;
					}
				}
				pktType = -1;
				return true;

			case 4:
			case 44:
			case 84:
			case 101:
			case 105:
			case 117:
			case 147:
			case 151:
			case 156:
			case 160:
			case 215:
				method137(inStream, pktType);
				pktType = -1;
				return true;

			case 106:
				tabID = inStream.method427();
				tabAreaAltered = true;
				pktType = -1;
				return true;

			case 164:
				int j9 = inStream.method434();
				method60(j9);
				if (invOverlayInterfaceID != -1) {
					invOverlayInterfaceID = -1;
					tabAreaAltered = true;
				}
				backDialogID = j9;
				inputTaken = true;
				openInterfaceID = -1;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			}
			Signlink.reporterror("T1 - " + pktType + "," + pktSize + " - " + anInt842 + "," + anInt843);
			// resetLogout();
		} catch (IOException _ex) {
			dropClient();
		} catch (Exception exception) {
			exception.printStackTrace();
			String s2 = "T2 - " + pktType + "," + anInt842 + "," + anInt843 + " - " + pktSize + "," + (baseX + myPlayer.smallX[0]) + "," + (baseY + myPlayer.smallY[0]) + " - ";
			for (int j15 = 0; j15 < pktSize && j15 < 50; j15++)
				s2 = s2 + inStream.buffer[j15] + ",";
			Signlink.reporterror(s2);
			// resetLogout();
		}
		pktType = -1;
		return true;
	}

	public void openURL(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
		}
	}

	private void replyToPM() {
		String name = null;
		for (int k = 0; k < 100; k++) {
			if (chatMessages[k] == null) {
				continue;
			}
			int l = chatTypes[k];
			if ((l == 3) || (l == 7)) {
				name = chatNames[k];
				break;
			}
		}
		if (name == null) {
			pushMessage("You haven't received any messages to which you can reply.", 0, "");
			return;
		}
		if (name != null) {
			if (name.indexOf("@") == 0) {
				name = name.substring(5);
			}
		}
		long nameAsLong = TextClass.longForName(name.trim());
		int k3 = -1;
		for (int i4 = 0; i4 < friendsCount; i4++) {
			if (friendsListAsLongs[i4] != nameAsLong) {
				continue;
			}
			k3 = i4;
			break;
		}
		if (k3 != -1) {
			if (friendsNodeIDs[k3] > 0) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 3;
				aLong953 = friendsListAsLongs[k3];
				aString1121 = "Enter message to send to " + friendsList[k3];
			} else {
				pushMessage("That player is currently offline.", 0, "");
			}
		}
	}

	private void method146() {
		anInt1265++;
		method47(true);
		method26(true);
		method47(false);
		method26(false);
		method55();
		method104();
		if (!aBoolean1160) {
			int i = anInt1184;
			if (anInt984 / 256 > i) {
				i = anInt984 / 256;
			}
			if (aBooleanArray876[4] && anIntArray1203[4] + 128 > i) {
				i = anIntArray1203[4] + 128;
			}
			int calc = minimapInt1 + anInt896 & 0x7ff;
			setCameraPos(cameraZoom + (frameWidth >= 1024 ? i + cameraZoom - frameHeight / 200 : i) * (WorldController.viewDistance == 10 ? 1 : 3), i, anInt1014, method42(plane, myPlayer.y, myPlayer.x) - 50, calc, anInt1015);
		}
		int j;
		if (!aBoolean1160)
			j = method120();
		else
			j = method121();
		int l = xCameraPos;
		int i1 = zCameraPos;
		int j1 = yCameraPos;
		int k1 = yCameraCurve;
		int l1 = xCameraCurve;
		int k2 = Rasterizer.anInt1481;
		for (int i2 = 0; i2 < 5; i2++)
			if (aBooleanArray876[i2]) {
				int j2 = (int) ((Math.random() * (double) (anIntArray873[i2] * 2 + 1) - (double) anIntArray873[i2]) + Math.sin((double) anIntArray1030[i2] * ((double) anIntArray928[i2] / 100D)) * (double) anIntArray1203[i2]);
				if (i2 == 0)
					xCameraPos += j2;
				if (i2 == 1)
					zCameraPos += j2;
				if (i2 == 2)
					yCameraPos += j2;
				if (i2 == 3)
					xCameraCurve = xCameraCurve + j2 & 0x7ff;
				if (i2 == 4) {
					yCameraCurve += j2;
					if (yCameraCurve < 128)
						yCameraCurve = 128;
					if (yCameraCurve > 383)
						yCameraCurve = 383;
				}
			}
		Model.aBoolean1684 = true;
		Model.anInt1687 = 0;
		Model.anInt1685 = super.mouseX - (frameMode == ScreenMode.FIXED ? 4 : 0);
		Model.anInt1686 = super.mouseY - (frameMode == ScreenMode.FIXED ? 4 : 0);
		DrawingArea.setAllPixelsToZero();
		if (Configuration.enableDistanceFog) {
			DrawingArea.drawPixels(frameMode == ScreenMode.FIXED ? 334 : frameHeight, 0, 0, ColorUtility.fadingToColor, frameMode == ScreenMode.FIXED ? 512 : frameWidth);
		}
		worldController.method313(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, j, yCameraCurve);
		worldController.clearObj5Cache();
		if (Configuration.enableDistanceFog) {
			if (!ColorUtility.switchColor) {
				if (fogHandler.fogColor != ColorUtility.fadingToColor) {
					ColorUtility.switchColor = true;
				}
			}
			if (ColorUtility.switchColor) {
				ColorUtility.fadeStep++;
				if (ColorUtility.fadeStep >= 100) {
					ColorUtility.fadeStep = 1;
					ColorUtility.switchColor = false;
					fogHandler.fogColor = ColorUtility.fadingToColor;
				} else {
					fogHandler.fogColor = ColorUtility.fadeColors(new Color(fogHandler.fogColor), new Color(ColorUtility.fadingToColor), ColorUtility.fadeStep);
				}
			}
			fogHandler.renderFog(aRSImageProducer_1165.canvasRaster, aRSImageProducer_1165.depthBuffer);
		}
		if (inMaze(baseX + (myPlayer.x - 6 >> 7), baseY + (myPlayer.y - 6 >> 7), plane) && filterGrayScale) {
			DrawingArea.filterGrayscale(0, 0, frameMode == ScreenMode.FIXED ? 512 : frameWidth, frameMode == ScreenMode.FIXED ? 334 : frameHeight, 1);
		}
		updateEntities();
		drawHeadIcon();
		method37(k2);
		
		if (Configuration.showKillFeed) {
			displayKillFeed();
		}
		if (frameMode != ScreenMode.FIXED) {
			drawChatArea();
			drawMinimap();
			drawTabArea();
		}
		draw3dScreen();
		if (console.openConsole) {
			console.drawConsole(frameMode == ScreenMode.FIXED ? super.myWidth : frameWidth, 334);
		}
		aRSImageProducer_1165.drawGraphics(frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics, frameMode == ScreenMode.FIXED ? 4 : 0);
		xCameraPos = l;
		zCameraPos = i1;
		yCameraPos = j1;
		yCameraCurve = k1;
		xCameraCurve = l1;
	}

	private void processMinimapActions() {
		final boolean fixed = frameMode == ScreenMode.FIXED;
		if (fixed ? super.mouseX >= 542 && super.mouseX <= 579 && super.mouseY >= 2 && super.mouseY <= 38 : super.mouseX >= frameWidth - 180 && super.mouseX <= frameWidth - 139 && super.mouseY >= 0 && super.mouseY <= 40) {
			menuActionName[1] = "Face North";
			menuActionID[1] = 696;
			menuActionRow = 2;
		}
		if (changeChatArea) {
			if (super.mouseX >= 256 && super.mouseX <= 264 && super.mouseY >= frameHeight - 170 - extendChatArea && super.mouseY <= frameHeight - 160 - extendChatArea) {
				menuActionName[1] = "Drag to Extend Chat";
				menuActionID[1] = 701;
				menuActionRow = 2;
			}
		}
		if (fixed ? super.mouseX >= 742 && super.mouseX <= 765 && super.mouseY >= 0 && super.mouseY <= 24 : super.mouseX >= frameWidth - 26 && super.mouseX <= frameWidth - 1 && super.mouseY >= 2 && super.mouseY <= 24) {
			menuActionName[1] = "Logout";
			menuActionID[1] = 1004;
			menuActionRow = 2;
		}
		if (Configuration.enableStatusOrbs) {
			if (counterHover) {
				menuActionName[3] = counterOn ? "Toggle XP Display" : "Toggle XP Display";
				menuActionID[3] = 474;
				menuActionName[2] = "Reset XP Total";
				menuActionID[2] = 475;
				menuActionName[1] = "XP Settings";
				menuActionID[1] = 476;
				menuActionRow = 4;
			}
			if (worldHover) {
				menuActionName[1] = "Teleports";
				menuActionID[1] = 850;
				menuActionRow = 2;
			}
			if (pouchHover & Configuration.enablePouch) {
				menuActionName[3] = "Withdraw coins";
				menuActionID[3] = 713;
				menuActionName[2] = "Payment plans";
				menuActionID[2] = 715;
				menuActionName[1] = "Examine pouch";
				menuActionID[1] = 714;
				menuActionRow = 4;
			}
			if (prayHover) {
				menuActionName[2] = prayClicked ? "Turn quick-prayers off" : "Turn quick-prayers on";
				menuActionID[2] = 1500;
				menuActionRow = 2;
				menuActionName[1] = "Select quick-prayers";
				menuActionID[1] = 1506;
				menuActionRow = 3;
			}
			if (runHover) {
				menuActionName[1] = variousSettings[173] == 0 ? "Turn run mode on" : "Turn run mode off";
				menuActionID[1] = 1050;
				menuActionRow = 2;
			}
		}
	}

	public int specialAttack;

	public void drawSpecialOrb() {
		if (specialHover) {
			orbComponents2[2].drawSprite(frameMode == ScreenMode.FIXED ? 153 : frameWidth - 63, frameMode == ScreenMode.FIXED ? 131 : 150);
		} else {
			orbComponents2[0].drawSprite(frameMode == ScreenMode.FIXED ? 154 : frameWidth - 62, frameMode == ScreenMode.FIXED ? 132 : 151);
		}
		orbComponents[10].myHeight = (int) (specialAttack * 27 / 100.0);
		orbComponents[10].drawSprite(frameMode == ScreenMode.FIXED ? 157 : frameWidth - 58, frameMode == ScreenMode.FIXED ? 135 : 155);
		orbComponents[6].drawSprite(frameMode == ScreenMode.FIXED ? 157 : frameWidth - 58, frameMode == ScreenMode.FIXED ? 135 : 155);
		orbComponents2[1].drawSprite(frameMode == ScreenMode.FIXED ? 162 : frameWidth - 53, frameMode == ScreenMode.FIXED ? 140 : 160);
		smallText.method382(getOrbTextColor(specialAttack), frameMode == ScreenMode.FIXED ? 198 : frameWidth - 19, Integer.toString(specialAttack), frameMode == ScreenMode.FIXED ? 158 : 177, true);
	}

	public boolean isPoisoned, clickedQuickPrayers;

	private void loadAllOrbs(int xOffset) {
		int[] orbX = { 0, 0, 24 }, orbY = { 45, 85, 121 }, orbTextX = { 15, 16, 40 }, orbTextY = { 72, 111, 148 }, coloredOrbX = { 27, 27, 51 }, coloredOrbY = { 49, 88, 125 }, currentInterface = { 4016, 4012, 149 }, maximumInterface = { 4017, 4013, 149 }, orbIconX = { 33, 30, 58 }, orbIconY = { 56, 92, 130 };
		if (!Configuration.enableStatusOrbs) {
			return;
		}

		if (frameMode != ScreenMode.FIXED) {
			xOffset += 5;
		}

		if (Configuration.enablePouch) {
			DrawingArea.fillCircle((frameMode == ScreenMode.FIXED ? 179 : frameWidth - 49), (frameMode == ScreenMode.FIXED ? 142 : 168), 15, 0x6E6D6D);
			cacheSprite[pouchHover ? 429 : 430].drawSprite((frameMode == ScreenMode.FIXED ? 162 : frameWidth - 65), (frameMode == ScreenMode.FIXED ? 127 : 153));
			String amount = RSInterface.interfaceCache[8135].disabledMessage;
			long getAmount = Long.parseLong(amount);
			smallText.method382(getMoneyOrbColor(getAmount), (frameMode == ScreenMode.FIXED ? 205 : frameWidth - 22), formatCoins(getAmount) + "", (frameMode == ScreenMode.FIXED ? 153 : 178), true);
			cacheSprite[428].drawSprite((frameMode == ScreenMode.FIXED ? 170 : frameWidth - 57), (frameMode == ScreenMode.FIXED ? 134 : 160));
		}

		int[] spriteID = { isPoisoned && hpHover ? 13 : 12, prayHover ? 13 : 12, runHover ? 13 : 12, sumActive && sumHover ? 13 : 12 }, coloredOrbSprite = { 0, clickedQuickPrayers ? 8 : 1, variousSettings[173] == 1 ? 9 : 8, sumActive ? 11 : 10 }, orbSprite = { 14, 2, (variousSettings[173] == 1 ? 4 : 3), 5 };
		int currentHP = extractInterfaceValues(RSInterface.interfaceCache[4016], 0);
		int currentEnergy = extractInterfaceValues(RSInterface.interfaceCache[19177], 0);
		for (int i = 0; i < 3; i++) {
			int currentLevel = extractInterfaceValues(RSInterface.interfaceCache[currentInterface[i]], 0), maxLevel = extractInterfaceValues(RSInterface.interfaceCache[maximumInterface[i]], 0), level = (int) ((currentLevel / (double) maxLevel) * 100D);
			orbComponents[spriteID[i]].drawSprite(orbX[i] + xOffset, orbY[i]);
			orbComponents[coloredOrbSprite[i]].drawSprite(coloredOrbX[i] + xOffset, coloredOrbY[i]);
			double percent = (i == 2 ? currentEnergy / 100D : level / 100D);
			int depleteFill = 26 - (int) (26 * percent);
			orbComponents[6].myHeight = depleteFill;
			try {
				orbComponents[6].drawSprite(coloredOrbX[i] + xOffset, coloredOrbY[i]);
			} catch (Exception e) {
			}
			if (level < 25) {
				orbComponents[orbSprite[i]].drawSprite1(orbIconX[i] + xOffset, orbIconY[i], 125 + (int) (125 * Math.sin(loopCycle / 7.0)));
			} else {
				orbComponents[orbSprite[i]].drawSprite(orbIconX[i] + xOffset, orbIconY[i]);
			}
			smallText.method382(getOrbTextColor(i == 2 ? currentEnergy : level), orbTextX[i] + xOffset, "" + (i == 2 ? currentEnergy : i == 0 && Configuration.enable10xDamage ? currentHP * 10 : currentLevel), orbTextY[i], true);
		}
		if (frameMode == ScreenMode.FIXED) {
			orbComponents2[worldHover ? 6 : 5].drawSprite(202, 20);
		} else {
			orbComponents2[worldHover ? 4 : 3].drawSprite(frameWidth - 135, 152);
		}
	}

	public int digits = 0;

	XPGain mainGain = null;
	List<Sprite> gainSprites = new ArrayList<>();
	private boolean walkableInterfaceMode = false;

	private void drawCounterOnScreen() {
		if (!Configuration.enableStatusOrbs) {
			return;
		}
		int x = frameMode == ScreenMode.FIXED ? 500 : frameWidth - 260;
		int y = walkableInterfaceMode ? 46 : 10;
		digits = xpCounter == 0 ? 1 : 1 + (int) Math.floor(Math.log10(xpCounter));
		int i = smallText.getTextWidth(Integer.toString(xpCounter)) - smallText.getTextWidth(Integer.toString(xpCounter)) / 2;
		smallText.method382(0xffffff, x - 38 - i - digits - 12, "Total:", y + 50, true);
		if (xpCounter >= 0) {
			smallText.method382(0xffffff, x + 1 - i, "+" + NumberFormat.getIntegerInstance().format(xpCounter), y + 50, true);
		}
		int currentIndex = 0;
		int offsetY = 0;
		int stop = 40;
		if (!gains.isEmpty()) {
			Iterator<XPGain> gained = gains.iterator();
			if ((gains.size() > 1)) {
				if (mainGain == null) {
					XPGain toGain = null;
					while (gained.hasNext()) {
						XPGain gain = gained.next();

						if (toGain == null) {
							toGain = new XPGain(gain.skill, 0);
						}

						Sprite sprite = cacheSprite[gain.getSkill() + 324];

						if (!gainSprites.contains(sprite)) {
							gainSprites.add(sprite);
						}

						toGain.xp += gain.getXP();
						currentIndex++;
					}

					Collections.reverse(gainSprites);
					mainGain = toGain;
				}

				if (mainGain == null) {
					return;
				}

				if (mainGain.getY() < stop) {
					if (mainGain.getY() <= 10) {
						mainGain.increaseAlpha();
					}
					if (mainGain.getY() >= stop - 10) {
						mainGain.decreaseAlpha();
					}
					mainGain.increaseY();
				} else if (mainGain.getY() >= stop) {
					mainGain = null;
					gains.clear();
					gainSprites.clear();
				}

				if (mainGain == null) {
					return;
				}

				if (mainGain.getY() < stop) {
					if (variousSettings[1030] == 0) {
						for (int ii = 0; ii < gainSprites.size(); ii++) {
							Sprite sprite = gainSprites.get(ii);
							sprite.drawSprite1(x - ii * 25 - 75 - sprite.myWidth / 2, mainGain.getY() - 5 + offsetY + y + 65 - sprite.myHeight / 2, mainGain.getAlpha());
						}
						newSmallFont.drawBasicString("<trans=" + (mainGain.getAlpha()) + ">+" + String.format("%,d", mainGain.getXP()) + "xp", x - 55, mainGain.getY() + offsetY + y + 65, 0xFFFFFF, 0);
					} else if (variousSettings[1030] == 1) {
						for (int ii = 0; ii < gainSprites.size(); ii++) {
							Sprite sprite = gainSprites.get(ii);
							sprite.drawSprite1((-mainGain.getY() + frameWidth - 280) - ii * 25 - (sprite.myWidth / 2), 80 - (sprite.myHeight / 2) + currentIndex * 28, mainGain.getAlpha());
						}
						newSmallFont.drawBasicString("<trans=" + (mainGain.getAlpha()) + ">+" + String.format("%,d", mainGain.getXP()) + "xp", -mainGain.getY() + frameWidth - 260, 85 + (walkableInterfaceMode ? 36 : 0) + currentIndex * 28, 0xFFFFFF, 0);
					} else if (variousSettings[1030] == 2) {
						for (int ii = 0; ii < gainSprites.size(); ii++) {
							Sprite sprite = gainSprites.get(ii);
							sprite.drawSprite1(x - ii * 25 - 75 - sprite.myWidth / 2, mainGain.getY() - 5 + offsetY + y + 65 - sprite.myHeight / 2, mainGain.getAlpha());
						}
						newSmallFont.drawBasicString("<trans=" + (mainGain.getAlpha()) + ">+" + String.format("%,d", mainGain.getXP()) + "xp", -mainGain.getY() + frameWidth - 260, mainGain.getY() + offsetY + y + 65, 0xFFFFFF, 0);
					}
				}
			} else {
				while (gained.hasNext()) {
					XPGain gain = gained.next();
					if (gain.getY() < stop) {
						if (gain.getY() <= 10) {
							gain.increaseAlpha();
						}
						if (gain.getY() >= stop - 10) {
							gain.decreaseAlpha();
						}
						gain.increaseY();
					} else if (gain.getY() >= stop) {
						gained.remove();
					}
					Sprite sprite = cacheSprite[gain.getSkill() + 324];
					if (gain.getY() < stop) {
						if (variousSettings[1030] == 0) {
							sprite.drawSprite1(x - 75 - sprite.myWidth / 2, gain.getY() - 5 + offsetY + y + 65 - sprite.myHeight / 2, gain.getAlpha());
							newSmallFont.drawBasicString("<trans=" + (gain.getAlpha()) + ">+" + String.format("%,d", gain.getXP()) + "xp", x - 55, gain.getY() + offsetY + y + 65, 0xFFFFFF, 0);
						} else if (variousSettings[1030] == 1) {
							sprite.drawSprite1((-gain.getY() + frameWidth - 280) - (sprite.myWidth / 2), 80 - (sprite.myHeight / 2) + currentIndex * 28, gain.getAlpha());
							newSmallFont.drawBasicString("<trans=" + (gain.getAlpha()) + ">+" + String.format("%,d", gain.getXP()) + "xp", -gain.getY() + frameWidth - 260, 85 + (walkableInterfaceMode ? 36 : 0) + currentIndex * 28, 0xFFFFFF, 0);
						} else if (variousSettings[1030] == 2) {
							sprite.drawSprite1(x - 75 - sprite.myWidth / 2, gain.getY() - 5 + offsetY + y + 65 - sprite.myHeight / 2, gain.getAlpha());
							newSmallFont.drawBasicString("<trans=" + (gain.getAlpha()) + ">+" + String.format("%,d", gain.getXP()) + "xp", -gain.getY() + frameWidth - 260, gain.getY() + offsetY + y + 65, 0xFFFFFF, 0);
						}
					}
					currentIndex++;
				}
			}
		}
	}

	public int xpCounter;

	private boolean runHover, prayHover, hpHover, prayClicked, counterOn, sumHover, sumActive, counterHover, specialHover, worldHover, pouchHover;

	public int getOrbTextColor(int statusInt) {
		if (statusInt >= 75 && statusInt <= Integer.MAX_VALUE)
			return 0x00FF00;
		else if (statusInt >= 50 && statusInt <= 74)
			return 0xFFFF00;
		else if (statusInt >= 25 && statusInt <= 49)
			return 0xFF981F;
		else
			return 0xFF0000;
	}

	public int getOrbFill(int statusInt) {
		if (statusInt <= Integer.MAX_VALUE && statusInt >= 97)
			return 0;
		else if (statusInt <= 96 && statusInt >= 93)
			return 1;
		else if (statusInt <= 92 && statusInt >= 89)
			return 2;
		else if (statusInt <= 88 && statusInt >= 85)
			return 3;
		else if (statusInt <= 84 && statusInt >= 81)
			return 4;
		else if (statusInt <= 80 && statusInt >= 77)
			return 5;
		else if (statusInt <= 76 && statusInt >= 73)
			return 6;
		else if (statusInt <= 72 && statusInt >= 69)
			return 7;
		else if (statusInt <= 68 && statusInt >= 65)
			return 8;
		else if (statusInt <= 64 && statusInt >= 61)
			return 9;
		else if (statusInt <= 60 && statusInt >= 57)
			return 10;
		else if (statusInt <= 56 && statusInt >= 53)
			return 11;
		else if (statusInt <= 52 && statusInt >= 49)
			return 12;
		else if (statusInt <= 48 && statusInt >= 45)
			return 13;
		else if (statusInt <= 44 && statusInt >= 41)
			return 14;
		else if (statusInt <= 40 && statusInt >= 37)
			return 15;
		else if (statusInt <= 36 && statusInt >= 33)
			return 16;
		else if (statusInt <= 32 && statusInt >= 29)
			return 17;
		else if (statusInt <= 28 && statusInt >= 25)
			return 18;
		else if (statusInt <= 24 && statusInt >= 21)
			return 19;
		else if (statusInt <= 20 && statusInt >= 17)
			return 20;
		else if (statusInt <= 16 && statusInt >= 13)
			return 21;
		else if (statusInt <= 12 && statusInt >= 9)
			return 22;
		else if (statusInt <= 8 && statusInt >= 7)
			return 23;
		else if (statusInt <= 6 && statusInt >= 5)
			return 24;
		else if (statusInt <= 4 && statusInt >= 3)
			return 25;
		else if (statusInt <= 2 && statusInt >= 1)
			return 26;
		else if (statusInt <= 0)
			return 27;
		return 0;
	}

	public void clearTopInterfaces() {
		stream.createFrame(130);
		if (invOverlayInterfaceID != -1) {
			invOverlayInterfaceID = -1;
			aBoolean1149 = false;
			tabAreaAltered = true;
		}
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
			aBoolean1149 = false;
		}
		openInterfaceID = -1;
		fullscreenInterfaceID = -1;
	}

	public Client() {
		ClientConstants.worldSelected = 1;
		xpCounter = 0;
		fullscreenInterfaceID = -1;
		chatRights = new int[500];
		chatTypeView = 0;
		clanChatMode = 0;
		cButtonHPos = -1;
		cButtonCPos = 0;
		chatRights = new int[500];
		chatColors = new String[500];
		chatTitles = new String[500];
		clanTitles = new String[500];
		server = ClientConstants.SERVER_IPS[ClientConstants.worldSelected - 1];
		anIntArrayArray825 = new int[104][104];
		friendsNodeIDs = new int[200];
		groundArray = new NodeList[4][104][104];
		aBoolean831 = false;
		aStream_834 = new Stream(new byte[5000]);
		npcArray = new Npc[16384];
		npcIndices = new int[16384];
		anIntArray840 = new int[1000];
		aStream_847 = Stream.create();
		aBoolean848 = true;
		openInterfaceID = -1;
		currentExp = new int[Skills.SKILLS_COUNT];
		aBoolean872 = false;
		anIntArray873 = new int[5];
		aBooleanArray876 = new boolean[5];
		drawFlames = false;
		reportAbuseInput = "";
		unknownInt10 = -1;
		menuOpen = false;
		inputString = "";
		maxPlayers = 2048;
		myPlayerIndex = 2047;
		playerArray = new Player[maxPlayers];
		playerIndices = new int[maxPlayers];
		anIntArray894 = new int[maxPlayers];
		aStreamArray895s = new Stream[maxPlayers];
		anInt897 = 1;
		anIntArrayArray901 = new int[104][104];
		aByteArray912 = new byte[16384];
		currentStats = new int[Skills.SKILLS_COUNT];
		ignoreListAsLongs = new long[100];
		loadingError = false;
		anIntArray928 = new int[5];
		anIntArrayArray929 = new int[104][104];
		chatTypes = new int[500];
		chatNames = new String[500];
		chatMessages = new String[500];
		sideIcons = new Sprite[17];
		aBoolean954 = true;
		friendsListAsLongs = new long[200];
		currentSong = -1;
		drawingFlames = false;
		spriteDrawX = -1;
		spriteDrawY = -1;
		anIntArray968 = new int[33];
		anIntArray969 = new int[256];
		decompressors = new Decompressor[6];
		variousSettings = new int[2000];
		aBoolean972 = false;
		anInt975 = 50;
		anIntArray976 = new int[anInt975];
		anIntArray977 = new int[anInt975];
		anIntArray978 = new int[anInt975];
		anIntArray979 = new int[anInt975];
		anIntArray980 = new int[anInt975];
		anIntArray981 = new int[anInt975];
		anIntArray982 = new int[anInt975];
		aStringArray983 = new String[anInt975];
		anInt985 = -1;
		hitMarks = new Sprite[20];
		hitMark = new Sprite[20];
		hitIcon = new Sprite[20];
		anIntArray990 = new int[5];
		aBoolean994 = false;
		amountOrNameInput = "";
		aClass19_1013 = new NodeList();
		aBoolean1017 = false;
		anInt1018 = -1;
		anIntArray1030 = new int[5];
		aBoolean1031 = false;
		mapFunctions = new Sprite[100];
		dialogID = -1;
		maxStats = new int[Skills.SKILLS_COUNT];
		anIntArray1045 = new int[2000];
		aBoolean1047 = true;
		anIntArray1052 = new int[152];
		anIntArray1229 = new int[152];
		anInt1054 = -1;
		aClass19_1056 = new NodeList();
		anIntArray1057 = new int[33];
		aClass9_1059 = new RSInterface();
		mapScenes = new Background[100];
		barFillColor = 0x4d4233;
		anIntArray1065 = new int[7];
		anIntArray1072 = new int[1000];
		anIntArray1073 = new int[1000];
		aBoolean1080 = false;
		friendsList = new String[200];
		inStream = Stream.create();
		expectedCRCs = new int[9];
		menuActionCmd2 = new int[500];
		menuActionCmd3 = new int[500];
		menuActionID = new int[500];
		menuActionCmd1 = new int[500];
		headIcons = new Sprite[20];
		skullIcons = new Sprite[20];
		headIconsHint = new Sprite[20];
		tabAreaAltered = false;
		aString1121 = "";
		atPlayerActions = new String[5];
		atPlayerArray = new boolean[5];
		anIntArrayArrayArray1129 = new int[4][13][13];
		anInt1132 = 2;
		aClass30_Sub2_Sub1_Sub1Array1140 = new Sprite[1000];
		aBoolean1141 = false;
		aBoolean1149 = false;
		crosses = new Sprite[8];
		musicEnabled = true;
		loggedIn = false;
		canMute = false;
		aBoolean1159 = false;
		aBoolean1160 = false;
		anInt1171 = 1;
		myUsername = "";
		myPassword = "";
		genericLoadingError = false;
		reportAbuseInterfaceID = -1;
		aClass19_1179 = new NodeList();
		anInt1184 = 128;
		invOverlayInterfaceID = -1;
		stream = Stream.create();
		menuActionName = new String[500];
		anIntArray1203 = new int[5];
		anIntArray1207 = new int[50];
		anInt1210 = 2;
		anInt1211 = 78;
		promptInput = "";
		modIcons = new Sprite[ClientConstants.ICON_AMOUNT];
		tabID = 3;
		inputTaken = false;
		songChanging = true;
		aClass11Array1230 = new Class11[4];
		anIntArray1240 = new int[100];
		anIntArray1241 = new int[50];
		aBoolean1242 = false;
		anIntArray1250 = new int[50];
		rsAlreadyLoaded = false;
		welcomeScreenRaised = false;
		messagePromptRaised = false;
		loginMessage1 = "Welcome to Vencillo";
		loginMessage2 = "Enter in your account details and begin playing!";
		backDialogID = -1;
		anInt1279 = 2;
		bigX = new int[4000];
		bigY = new int[4000];
	}

	public int rights;
	public String name;
	public String message;
	public String clanname;
	private int[] chatRights;
	public int chatTypeView;
	public int clanChatMode;
	public static Sprite[] cacheSprite;
	private ImageProducer leftFrame;
	private ImageProducer topFrame;
	private int ignoreCount;
	private long aLong824;
	private int[][] anIntArrayArray825;
	private int[] friendsNodeIDs;
	private NodeList[][][] groundArray;
	public int[] anIntArray828;
	public int[] anIntArray829;
	private volatile boolean aBoolean831;
	private Socket aSocket832;
	int loginScreenState;
	private Stream aStream_834;
	private Npc[] npcArray;
	private int npcCount;
	private int[] npcIndices;
	private int anInt839;
	private int[] anIntArray840;
	private int anInt841;
	private int anInt842;
	private int anInt843;
	private String aString844;
	public String prayerBook;
	private int privateChatMode;
	private Stream aStream_847;
	private boolean aBoolean848;
	private static int anInt849;
	public int[] anIntArray850;
	private int[] anIntArray851;
	private int[] anIntArray852;
	private int[] anIntArray853;
	private static int anInt854;
	private int anInt855;
	static int openInterfaceID;
	private int xCameraPos;
	private int zCameraPos;
	private int yCameraPos;
	private int yCameraCurve;
	private int xCameraCurve;
	static int myPrivilege;
	private final int[] currentExp;
	private Sprite mapFlag;
	private Sprite mapMarker;
	private boolean aBoolean872;
	private final int[] anIntArray873;
	private final boolean[] aBooleanArray876;
	private int weight;
	private MouseDetection mouseDetection;
	private volatile boolean drawFlames;
	private String reportAbuseInput;
	private int unknownInt10;
	private boolean menuOpen;
	private int anInt886;
	private String inputString;
	private final int maxPlayers;
	private final int myPlayerIndex;
	private Player[] playerArray;
	private int playerCount;
	private int[] playerIndices;
	private int anInt893;
	private int[] anIntArray894;
	private Stream[] aStreamArray895s;
	private int anInt896;
	public int anInt897;
	private int friendsCount;
	private int anInt900;
	private int[][] anIntArrayArray901;
	private byte[] aByteArray912;
	private int anInt913;
	private int crossX;
	private int crossY;
	private int crossIndex;
	private int crossType;
	private int plane;
	private final int[] currentStats;
	private static int anInt924;
	private final long[] ignoreListAsLongs;
	private boolean loadingError;
	private final int[] anIntArray928;
	private int[][] anIntArrayArray929;
	private Sprite aClass30_Sub2_Sub1_Sub1_931;
	private Sprite aClass30_Sub2_Sub1_Sub1_932;
	private int anInt933;
	private int anInt934;
	private int anInt935;
	private int anInt936;
	private int anInt937;
	private int anInt938;
	private final int[] chatTypes;
	private final String[] chatNames;
	private final String[] chatMessages;
	private int anInt945;
	private WorldController worldController;
	private Sprite[] sideIcons;
	private int menuScreenArea;
	private int menuOffsetX;
	private int menuOffsetY;
	private int menuWidth;
	private int menuHeight;
	private long aLong953;
	private boolean aBoolean954;
	private long[] friendsListAsLongs;
	private String[] clanList = new String[100];
	private int currentSong;
	private static int nodeID = 10;
	static int portOff;
	static boolean clientData;
	private static boolean isMembers = true;
	private static boolean lowMem;
	private volatile boolean drawingFlames;
	private int spriteDrawX;
	private int spriteDrawY;
	private final int[] anIntArray965 = { 0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff };
	public Background aBackground_966;
	public Background aBackground_967;
	private final int[] anIntArray968;
	public final int[] anIntArray969;
	final Decompressor[] decompressors;
	public int variousSettings[];
	private boolean aBoolean972;
	private final int anInt975;
	private final int[] anIntArray976;
	private final int[] anIntArray977;
	private final int[] anIntArray978;
	private final int[] anIntArray979;
	private final int[] anIntArray980;
	private final int[] anIntArray981;
	private final int[] anIntArray982;
	private final String[] aStringArray983;
	private int anInt984;
	private int anInt985;
	private static int anInt986;
	private Sprite[] hitMarks;
	private Sprite[] hitMark;
	private Sprite[] hitIcon;
	public int anInt988;
	private int dragCycle;
	private final int[] anIntArray990;
	private final boolean aBoolean994;
	private int anInt995;
	private int anInt996;
	private int anInt997;
	private int anInt998;
	private int anInt999;
	private ISAACRandomGen encryption;
	private Sprite multiOverlay;
	static final int[][] anIntArrayArray1003 = { { 6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193 }, { 8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239 }, { 25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003 }, { 4626, 11146, 6439, 12, 4758, 10270 }, { 4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574 } };
	private String amountOrNameInput;
	private static int anInt1005;
	private int daysSinceLastLogin;
	private int pktSize;
	private int pktType;
	private int anInt1009;
	private int anInt1010;
	private int anInt1011;
	private NodeList aClass19_1013;
	private int anInt1014;
	private int anInt1015;
	private int anInt1016;
	private boolean aBoolean1017;
	private int anInt1018;
	private int anInt1021;
	private int anInt1022;
	public static int loadingStage;
	private Sprite scrollBar1;
	private Sprite scrollBar2;
	private int anInt1026;
	private final int[] anIntArray1030;
	private boolean aBoolean1031;
	private Sprite[] mapFunctions;
	private int baseX;
	private int baseY;
	private int anInt1036;
	private int anInt1037;
	public int loginFailures;
	private int anInt1039;
	public int anInt1040;
	public int anInt1041;
	private int dialogID;
	public Sprite[] skillIcons = new Sprite[22];
	public Sprite[] newHitMarks = new Sprite[4];
	public Sprite[] channelButtons = new Sprite[4];
	public Sprite[] fixedGameComponents = new Sprite[4];
	public Sprite[] gameComponents = new Sprite[5];
	public Sprite[] orbComponents = new Sprite[15];
	public Sprite[] orbComponents2 = new Sprite[7];
	public Sprite[] orbComponents3 = new Sprite[10];
	public Sprite[] redStones = new Sprite[6];
	public Sprite[] hpBars = new Sprite[2];
	private final int[] maxStats;
	private final int[] anIntArray1045;
	private int anInt1046;
	private boolean aBoolean1047;
	private int anInt1048;
	private String aString1049;
	private static int anInt1051;
	private final int[] anIntArray1052;
	private StreamLoader titleStreamLoader;
	private int anInt1054;
	private int anInt1055;
	private NodeList aClass19_1056;
	private final int[] anIntArray1057;
	public final RSInterface aClass9_1059;
	private Background[] mapScenes;
	private int anInt1062;
	private final int barFillColor;
	private int friendsListAction;
	private final int[] anIntArray1065;
	private int mouseInvInterfaceIndex;
	private int lastActiveInvInterface;
	public OnDemandFetcher onDemandFetcher;
	private int anInt1069;
	private int anInt1070;
	private int anInt1071;
	private int[] anIntArray1072;
	private int[] anIntArray1073;
	private Sprite mapDotItem;
	private Sprite mapDotNPC;
	private Sprite mapDotPlayer;
	private Sprite mapDotFriend;
	private Sprite mapDotTeam;
	private Sprite mapDotClan;
	private int anInt1079;
	private boolean aBoolean1080;
	private String[] friendsList;
	private Stream inStream;
	private int focusedDragWidget;
	private int dragFromSlot;
	private int activeInterfaceType;
	private int pressX;
	private int pressY;
	public static int anInt1089;
	public static int spellID = 0;
	public static int totalRead = 0;
	private final int[] expectedCRCs;
	private int[] menuActionCmd2;
	private int[] menuActionCmd3;
	private int[] menuActionID;
	private int[] menuActionCmd1;
	private Sprite[] headIcons;
	private Sprite[] skullIcons;
	private Sprite[] headIconsHint;
	private static int anInt1097;
	private int anInt1098;
	private int anInt1099;
	private int anInt1100;
	private int anInt1101;
	private int anInt1102;
	private static boolean tabAreaAltered;
	private int anInt1104;
	private ImageProducer aRSImageProducer_1107;
	public ImageProducer aRSImageProducer_1108;
	static ImageProducer aRSImageProducer_1109;
	private ImageProducer aRSImageProducer_1110;
	private ImageProducer aRSImageProducer_1111;
	public ImageProducer aRSImageProducer_1112;
	public ImageProducer aRSImageProducer_1113;
	public ImageProducer aRSImageProducer_1114;
	public ImageProducer aRSImageProducer_1115;
	private static int anInt1117;
	private int membersInt;
	private String aString1121;
	private Sprite compass;
	private ImageProducer aRSImageProducer_1125;
	public static Player myPlayer;
	private final String[] atPlayerActions;
	private final boolean[] atPlayerArray;
	private final int[][][] anIntArrayArrayArray1129;
	public static final int[] tabInterfaceIDs = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	private int anInt1131;
	public int anInt1132;
	private int menuActionRow;
	private static int anInt1134;
	private int spellSelected;
	private int anInt1137;
	private int spellUsableOn;
	private String spellTooltip;
	private Sprite[] aClass30_Sub2_Sub1_Sub1Array1140;
	private boolean aBoolean1141;
	private static int anInt1142;
	private int energy;
	private boolean aBoolean1149;
	private Sprite[] crosses;
	private boolean musicEnabled;
	private Background[] aBackgroundArray1152s;
	private int unreadMessages;
	private static int anInt1155;
	private static boolean fpsOn;
	public static boolean loggedIn;
	private boolean canMute;
	private boolean aBoolean1159;
	private boolean aBoolean1160;
	static int loopCycle;
	public static final String validUserPassChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
	private static ImageProducer aRSImageProducer_1163;
	private ImageProducer aRSImageProducer_1164;
	private static ImageProducer aRSImageProducer_1165;
	private static ImageProducer aRSImageProducer_1166;
	private int daysSinceRecovChange;
	private RSSocket socketStream;
	private int anInt1169;
	private int minimapInt3;
	public int anInt1171;
	static int getCombatLevel;
	static String myUsername;
	static String myPassword;
	private static int anInt1175;
	private boolean genericLoadingError;
	private final int[] anIntArray1177 = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };
	private int reportAbuseInterfaceID;
	private NodeList aClass19_1179;
	private static int[] anIntArray1180;
	private static int[] anIntArray1181;
	private static int[] anIntArray1182;
	private byte[][] aByteArrayArray1183;
	private int anInt1184;
	private int minimapInt1;
	private int anInt1186;
	private int anInt1187;
	private static int anInt1188;
	private int invOverlayInterfaceID;
	private int[] anIntArray1190;
	private int[] anIntArray1191;
	public static Stream stream;
	private int anInt1193;
	private int splitPrivateChat;
	private Background mapBack;
	private String[] menuActionName;
	private Sprite aClass30_Sub2_Sub1_Sub1_1201;
	private Sprite aClass30_Sub2_Sub1_Sub1_1202;
	private final int[] anIntArray1203;
	static final int[] anIntArray1204 = { 9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486 };
	private static boolean flagged;
	private final int[] anIntArray1207;
	private int minimapInt2;
	public int anInt1210;
	static int anInt1211;
	private String promptInput;
	private int anInt1213;
	private int[][][] intGroundArray;
	private long aLong1215;
	int loginScreenCursorPos;
	private final Sprite[] modIcons;
	private long aLong1220;
	static int tabID;
	private int anInt1222;
	public static boolean inputTaken;
	private int inputDialogState;
	private static int anInt1226;
	private int nextSong;
	private boolean songChanging;
	private final int[] anIntArray1229;
	private Class11[] aClass11Array1230;
	public static int anIntArray1232[];
	private int[] anIntArray1234;
	private int[] anIntArray1235;
	private int[] anIntArray1236;
	private int anInt1237;
	private int anInt1238;
	public final int anInt1239 = 100;
	private final int[] anIntArray1240;
	private final int[] anIntArray1241;
	private boolean aBoolean1242;
	private int atInventoryLoopCycle;
	private int atInventoryInterface;
	private int atInventoryIndex;
	private int atInventoryInterfaceType;
	private byte[][] aByteArrayArray1247;
	private int tradeMode;
	private int anInt1249;
	private final int[] anIntArray1250;
	private int anInt1251;
	private final boolean rsAlreadyLoaded;
	private int anInt1253;
	public int anInt1254;
	private boolean welcomeScreenRaised;
	private boolean messagePromptRaised;
	private byte[][][] byteGroundArray;
	private int prevSong;
	private int destX;
	private int destY;
	public static Sprite minimapImage;
	private int anInt1264;
	private int anInt1265;
	String loginMessage1;
	String loginMessage2;
	private int anInt1268;
	private int anInt1269;
	public int drawCount;
	public int fullscreenInterfaceID;
	public int anInt1044;
	public int anInt1129;
	public int anInt1315;
	public int anInt1500;
	public int anInt1501;
	public static int[] fullScreenTextureArray;
	public static TextDrawingArea smallText;
	public static TextDrawingArea regularText;
	public static TextDrawingArea boldText;
	public RSFont newSmallFont;
	public static RSFont newRegularFont;
	public static RSFont newBoldFont;
	public static RSFont newFancyFont;
	public int anInt1275;
	public static int backDialogID;
	private int anInt1278;
	public int anInt1279;
	private int[] bigX;
	private int[] bigY;
	private int itemSelected;
	private int anInt1283;
	private int anInt1284;
	private int anInt1285;
	private String selectedItemName;
	private int publicChatMode;
	private static int anInt1288;
	public static int anInt1290;
	public static String server = "";
	public static boolean rememberMe = false;
	private int modifiableXValue = 0;

	public void resetAllImageProducers() {
		if (super.fullGameScreen != null) {
			return;
		}
		aRSImageProducer_1166 = null;
		aRSImageProducer_1164 = null;
		aRSImageProducer_1163 = null;
		aRSImageProducer_1165 = null;
		aRSImageProducer_1125 = null;
		aRSImageProducer_1107 = null;
		aRSImageProducer_1108 = null;
		aRSImageProducer_1109 = null;
		aRSImageProducer_1110 = null;
		aRSImageProducer_1111 = null;
		aRSImageProducer_1112 = null;
		aRSImageProducer_1113 = null;
		aRSImageProducer_1114 = null;
		aRSImageProducer_1115 = null;
		super.fullGameScreen = new ImageProducer(765, 503);
		welcomeScreenRaised = true;
	}

	public void mouseWheelDragged(int i, int j) {
		if (!mouseWheelDown) {
			return;
		}
		screenGliding = 0;
		this.anInt1186 += i * 3;
		this.anInt1187 += (j << 1);
	}

	public void launchURL(String url) {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else { // assume Unix or Linux
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape", "safari" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
						browser = browsers[count];
				if (browser == null) {
					throw new Exception("Could not find web browser");
				} else
					Runtime.getRuntime().exec(new String[] { browser, url });
			}
		} catch (Exception e) {
			pushMessage("Failed to open URL.", 0, "");
		}
	}
	
	public String entityFeedName;
	public int entityFeedHP;
	public int entityFeedMaxHP;
	public int entityFeedHP2;
	public int entityAlpha;
	
	public void pushFeed(String entityName, int HP, int maxHP) {
		entityFeedHP2 = entityFeedHP <= 0 ? entityFeedMaxHP : entityFeedHP;
		entityFeedName = entityName;
		entityFeedHP = HP;
		entityFeedMaxHP = maxHP;
		entityAlpha = 255;
	}

	@SuppressWarnings("unused")
	private void displayEntityFeed() {
		if (entityFeedName == null) {
			return;
		}
    	double percentage =  entityFeedHP / (double) entityFeedMaxHP;
    	double percentage2 =  (entityFeedHP2 - entityFeedHP) / (double) entityFeedMaxHP;
    	int width = (int) (135 * percentage);
    	int xOff = 3;
    	int yOff = 25;
    	DrawingArea.drawAlphaPixels(xOff, yOff, 145, 41, 0x000000, 115);
    	newBoldFont.drawCenteredString(entityFeedName, xOff + 72, yOff + 13, 0xFDFDFD, 0);
    	DrawingArea.drawAlphaPixels(xOff + 6 + width, yOff + 18, 135 - width, 15, 0xFF0000, 130);
    	DrawingArea.drawAlphaPixels(xOff + 6, yOff + 18, width, 15, 0x00DB00, 130);
    	if (entityAlpha > 0) {
    		entityAlpha -= 5;
    		DrawingArea.drawAlphaPixels(xOff + 6 + width, yOff + 18, (int) (135 * percentage2), 15, 0x00DB00, (int) (130 * entityAlpha / 255.0));
    	}
    	DrawingArea.drawAlphaPixels(xOff + 6, yOff + 18, width, 15, 0x00DB00, 130);
    	newBoldFont.drawCenteredString(NumberFormat.getInstance(Locale.US).format(entityFeedHP) + " / " + NumberFormat.getInstance(Locale.US).format(entityFeedMaxHP), xOff + 72, yOff + 31, 0xFDFDFD, 0);
	}

	public String[] feedKiller = new String[5];
	public String[] feedVictim = new String[5];
	public int[] feedWeapon = new int[5];
	public boolean[] feedPoison = new boolean[5];
	public Sprite[] feedImage = new Sprite[5];
	public int[] feedAlpha = new int[5];
	public int[] feedYPos = new int[5];
	public int killsDisplayed = 5;

	public void pushKill(String killer, String victim, int weapon, boolean poison) {
		for (int index = killsDisplayed - 1; index > 0; index--) {
			feedKiller[index] = feedKiller[index - 1];
			feedVictim[index] = feedVictim[index - 1];
			feedWeapon[index] = feedWeapon[index - 1];
			feedPoison[index] = feedPoison[index - 1];
			feedAlpha[index] = feedAlpha[index - 1];
			feedYPos[index] = feedYPos[index - 1];
		}
		feedKiller[0] = killer;
		feedVictim[0] = victim;
		feedWeapon[0] = weapon;
		feedPoison[0] = poison;
		feedAlpha[0] = 0;
		feedYPos[0] = 0;
	}

	public void displayKillFeed() {
		int x = 5;
		for (int index = 0; index < killsDisplayed; index++) {
			if (feedKiller[index] != null && feedVictim[index] != null) {
				if (feedKiller[index].length() > 0) {
					if (feedWeapon[index] == -1) {
						return;
					}
					if (feedKiller[index].equalsIgnoreCase(myUsername)) {
						feedKiller[index] = "You";
					}
					if (feedVictim[index].equalsIgnoreCase(myUsername)) {
						feedVictim[index] = "You";
					}
					if (feedYPos[index] < (index + 1) * 22) {
						feedYPos[index] += 1;
						if (index == 0) {
							feedAlpha[index] += 256 / 22;
						}
					} else if (feedYPos[index] == (index + 1) * 22) {
						if (feedAlpha[index] > 200) {
							feedAlpha[index] -= 1;
						} else if (feedAlpha[index] <= 200 && feedAlpha[index] > 0) {
							feedAlpha[index] -= 5;
						}
						if (feedAlpha[index] < 0) {
							feedAlpha[index] = 0;
						}
						if (feedAlpha[index] == 0) {
							clearKill(index);
						}
					}
					if (feedAlpha[index] != 0) {
						String killerText = "[" + feedKiller[index] + "] ";
						String victimText = " [" + feedVictim[index] + "]";
						String posionText = " <col=00ff00>[poisoned]</col>";
						DrawingArea.drawAlphaGradient(x, feedYPos[index], newSmallFont.getTextWidth(killerText + victimText + (feedPoison[index] ? posionText : "")) + 22, 19, 0, 0, feedAlpha[index]);
						newSmallFont.drawBasicString("<trans=" + feedAlpha[index] + ">" + killerText, x + 3, feedYPos[index] + 14, 0xffffff, 0);
						newSmallFont.drawBasicString("<trans=" + feedAlpha[index] + ">" + victimText + (feedPoison[index] ? posionText : ""), x + 3 + newSmallFont.getTextWidth(killerText) + 16, feedYPos[index] + 14, 0xffffff, 0);
						if (feedWeapon[index] != -1 && feedWeapon[index] != 65535) {
							feedImage[index] = ItemDef.getSprite(feedWeapon[index], 0, 0x000000, 2);
						}
						if (feedImage[index] != null) {
							feedImage[index].drawTransparentSprite(newSmallFont.getTextWidth(killerText) + 0, feedYPos[index] - 6, feedAlpha[index]);
						}
					}
				}
			}
		}
	}

	public void hitmarkDraw(int hitLength, int type, int icon, int damage, int move, int opacity) {
		if (damage > 0) {
			Sprite end1 = null, middle = null, end2 = null;
			int x = 0;
			switch (hitLength) {
			case 1:
				x = 8;
				break;
			case 2:
				x = 4;
				break;
			case 3:
				x = 1;
				break;
			}
			switch (type) {
			case 1:
				end1 = hitMark[0];
				middle = hitMark[1];
				end2 = hitMark[2];
				break;
			case 3:
				end1 = hitMark[3];
				middle = hitMark[4];
				end2 = hitMark[5];
				break;
			case 2:
				end1 = hitMark[6];
				middle = hitMark[7];
				end2 = hitMark[8];
				break;
			}
			if (icon <= 6)
				hitIcon[icon].drawTransparentSprite(spriteDrawX - 34 + x, spriteDrawY - 14 + move, opacity);
			end1.drawTransparentSprite(spriteDrawX - 12 + x, spriteDrawY - 12 + move, opacity);
			x += 4;
			for (int i = 0; i < hitLength * 2; i++) {
				middle.drawTransparentSprite(spriteDrawX - 12 + x, spriteDrawY - 12 + move, opacity);
				x += 4;
			}
			end2.drawTransparentSprite(spriteDrawX - 12 + x, spriteDrawY - 12 + move, opacity);
			if (opacity > 100)
				smallText.drawText(0xffffff, String.valueOf(damage), spriteDrawY + 3 + move, spriteDrawX + 4);
		} else {
			cacheSprite[474].drawTransparentSprite(spriteDrawX - 12, spriteDrawY - 14 + move, opacity);
		}
	}

	public void clearKill(int index) {
		feedKiller[index] = null;
		feedVictim[index] = null;
		feedWeapon[index] = -1;
		feedPoison[index] = false;
		feedAlpha[index] = -1;
		feedYPos[index] = -1;
	}

	static {
		anIntArray1232 = new int[32];
		int i = 2;
		for (int k = 0; k < 32; k++) {
			anIntArray1232[k] = i - 1;
			i += i;
		}
	}
}