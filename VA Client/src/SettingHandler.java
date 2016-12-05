import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Handles changing, loading, and saving settings
 * @author Daniel
 *
 */
public class SettingHandler {
	
	/**
	 * String identification
	 */
	private final static int STRING_ID = 28450;
	
	/**
	 * Path to player setting file
	 */
	private final static String PATH = Signlink.findcachedir() + "/settings.dat";
	
	/**
	 * Handles changing settings
	 * @param button
	 * @return
	 */
	public static boolean handle(int button) {
		switch (button) {
			case 28420:
				Configuration.enableTweening = !Configuration.enableTweening;
				updateText();
				return true;
			case 28421:
				Configuration.enableDistanceFog = !Configuration.enableDistanceFog;
				updateText();
				return true;
			case 28422:
				Configuration.enableHDMinimap = !Configuration.enableHDMinimap;
				Client.loadingStage = 1;
				Client.minimapImage.method343();
				updateText();
				return true;
			case 28423:
				Configuration.enableMipMapping = !Configuration.enableMipMapping;
				updateText();
				return true;	
			case 28424:
				Configuration.enableGroundDecors= !Configuration.enableGroundDecors;
				Client.loadingStage = 1;
				Client.minimapImage.method343();
				updateText();
				return true;	
			case 28425:
				Configuration.enableMovingTextures = !Configuration.enableMovingTextures;
				updateText();
				return true;	
			case 28426:
				Configuration.enableStatusOrbs = !Configuration.enableStatusOrbs;
				updateText();
				return true;	
			case 28427:
				Configuration.enableRoofs = !Configuration.enableRoofs;
				updateText();
				return true;	
			case 28428:
				Configuration.enablePouch = !Configuration.enablePouch;
				updateText();
				return true;	
			case 28429:
				Configuration.showKillFeed = !Configuration.showKillFeed;
				updateText();
				return true;	
			case 28430:
				Configuration.menuHovers = !Configuration.menuHovers;
				updateText();
				return true;	
			case 28431:
				//ClientSettings.drawEntityFeed = !ClientSettings.drawEntityFeed;
				updateText();
				return true;	
			case 28432:
				Configuration.enableNewMenus = !Configuration.enableNewMenus;
				updateText();
				return true;	
			case 28433:
				Configuration.enableNewHpBars = !Configuration.enableNewHpBars;
				updateText();
				return true;	
			case 28434:
				Configuration.enableNewHitmarks = !Configuration.enableNewHitmarks;
				updateText();
				return true;	
			case 28435:
				Configuration.enable10xDamage = !Configuration.enable10xDamage;
				updateText();
				return true;	
			case 28436:
				Configuration.enableScreenGliding = !Configuration.enableScreenGliding;
				updateText();
				return true;	
			case 28437:
				Configuration.entityAttackPriority = !Configuration.entityAttackPriority;
				updateText();
				return true;
			case 28438:
				Configuration.enableTimeStamps = !Configuration.enableTimeStamps;
				updateText();
				return true;
			case 28439:
				Configuration.snow = !Configuration.snow;
				updateText();
				return true;
		}
		return false;
	}
	
	/**
	 * List of settings available
	 */
	public final static String[] strings = { 
		"Tweening", "Fog", "HD Minimap", "Mip Mapping", "Ground Decoration", "Moving Textures", "Status Orbs", 
		"Roofs", "Money Pouch", "Kill Feed", "Hover Menus", "Entity Feed (N/A)", "Context Menu", "HP Bars", "Hitmarkers",
		"x10 Damage", "Camera Gliding", "Attack Priority", "Time Stamps", "Snow", "Prestige Colors",
	};
	
	/**
	 * Updates all the text
	 */
	public static void updateText() {
		Client.sendFrame126(prefix(Configuration.enableTweening) + strings[0], STRING_ID + 0);
		Client.sendFrame126(prefix(Configuration.enableDistanceFog) + strings[1], STRING_ID + 1);
		Client.sendFrame126(prefix(Configuration.enableHDMinimap) + strings[2], STRING_ID + 2);		
		Client.sendFrame126(prefix(Configuration.enableMipMapping) + strings[3], STRING_ID + 3);
		Client.sendFrame126(prefix(Configuration.enableGroundDecors) + strings[4], STRING_ID + 4);
		Client.sendFrame126(prefix(Configuration.enableMovingTextures) + strings[5], STRING_ID + 5);
		Client.sendFrame126(prefix(Configuration.enableStatusOrbs) + strings[6], STRING_ID + 6);
		Client.sendFrame126(prefix(Configuration.enableRoofs) + strings[7], STRING_ID + 7);
		Client.sendFrame126(prefix(Configuration.enablePouch) + strings[8], STRING_ID + 8);	
		Client.sendFrame126(prefix(Configuration.showKillFeed) + strings[9], STRING_ID + 9);
		Client.sendFrame126(prefix(Configuration.menuHovers) + strings[10], STRING_ID + 10);
		Client.sendFrame126(prefix(Configuration.drawEntityFeed) + strings[11], STRING_ID + 11);
		Client.sendFrame126(prefix(Configuration.enableNewMenus) + strings[12], STRING_ID + 12);
		Client.sendFrame126(prefix(Configuration.enableNewHpBars) + strings[13], STRING_ID + 13);
		Client.sendFrame126(prefix(Configuration.enableNewHitmarks) + strings[14], STRING_ID + 14);	
		Client.sendFrame126(prefix(Configuration.enable10xDamage) + strings[15], STRING_ID + 15);
		Client.sendFrame126(prefix(Configuration.enableScreenGliding) + strings[16], STRING_ID + 16);
		Client.sendFrame126(prefix(Configuration.entityAttackPriority) + strings[17], STRING_ID + 17);
		Client.sendFrame126(prefix(Configuration.enableTimeStamps) + strings[18], STRING_ID + 18);
		Client.sendFrame126(prefix(Configuration.snow) + strings[19], STRING_ID + 19);
		Client.sendFrame126(prefix(Configuration.enablePrestigeColors) + strings[20], STRING_ID + 20);
	}
	
	/**
	 * Prefix of text
	 * @param paramBoolean
	 * @return
	 */
	public static String prefix(boolean paramBoolean) {
		return paramBoolean ? "<col=4DE024>" : "<col=D61E30>";
	}
	
	/**
	 * Default settings
	 */
	public static void defaultSettings() {
		Configuration.enableTweening = false;
		Configuration.enableDistanceFog = true;
		Configuration.enableHDMinimap = true;
		Configuration.enableMipMapping = true;
		Configuration.enableGroundDecors = true;
		Configuration.enableMovingTextures = true;
		Configuration.enableStatusOrbs = true;
		Configuration.enableRoofs = false;
		Configuration.enablePouch = true;
		Configuration.showKillFeed = false;
		Configuration.menuHovers = true;
		Configuration.drawEntityFeed = false;
		Configuration.enableNewMenus = true;
		Configuration.enableNewHpBars = false;
		Configuration.enableNewHitmarks = false;
		Configuration.enable10xDamage = false;
		Configuration.enableScreenGliding = false;
		Configuration.entityAttackPriority = false;
		Configuration.enableTimeStamps = false;
		Configuration.snow = false;
		Configuration.enablePrestigeColors = true;
		updateText();
		Client.loadingStage = 1;
		Client.minimapImage.method343();
	}

	/**
	 * Saves all the settings
	 */
	public static void save() {
		try {
			File file = new File(PATH);
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			out.writeUTF(Client.myUsername);
			out.writeUTF(Client.myPassword);
			out.writeUTF(Client.chatColorHex);
			out.writeBoolean(Client.rememberMe);
			out.writeBoolean(Configuration.enableTweening);
			out.writeBoolean(Configuration.enableDistanceFog);
			out.writeBoolean(Configuration.enableHDMinimap);
			out.writeBoolean(Configuration.enableMipMapping);
			out.writeBoolean(Configuration.enableGroundDecors);
			out.writeBoolean(Configuration.enableMovingTextures);
			out.writeBoolean(Configuration.enableStatusOrbs);
			out.writeBoolean(Configuration.enableRoofs);
			out.writeBoolean(Configuration.enablePouch);
			out.writeBoolean(Configuration.showKillFeed);
			out.writeBoolean(Configuration.menuHovers);
			out.writeBoolean(Configuration.drawEntityFeed);
			out.writeBoolean(Configuration.enableNewMenus);
			out.writeBoolean(Configuration.enableNewHpBars);
			out.writeBoolean(Configuration.enableNewHitmarks);
			out.writeBoolean(Configuration.enable10xDamage);
			out.writeBoolean(Configuration.enableScreenGliding);
			out.writeBoolean(Configuration.entityAttackPriority);
			out.writeBoolean(Configuration.enableTimeStamps);
			out.writeBoolean(Configuration.snow);
			out.writeBoolean(Configuration.enablePrestigeColors);
			out.close();
			System.out.println("Successfully saved " + strings.length + " settings.");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Loads all the settings
	 */
	public static void load() {
		try {
			File file = new File(PATH);
			if (!file.exists()) {
				return;
			}
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			Client.myUsername = in.readUTF();
			Client.myPassword = in.readUTF();
			Client.chatColorHex = in.readUTF();
			Client.rememberMe = in.readBoolean();
			Configuration.enableTweening = in.readBoolean();
			Configuration.enableDistanceFog = in.readBoolean();
			Configuration.enableHDMinimap = in.readBoolean();
			Configuration.enableMipMapping = in.readBoolean();
			Configuration.enableGroundDecors = in.readBoolean();
			Configuration.enableMovingTextures = in.readBoolean();
			Configuration.enableStatusOrbs = in.readBoolean();
			Configuration.enableRoofs = in.readBoolean();
			Configuration.enablePouch = in.readBoolean();
			Configuration.showKillFeed = in.readBoolean();
			Configuration.menuHovers = in.readBoolean();
			Configuration.drawEntityFeed = in.readBoolean();
			Configuration.enableNewMenus = in.readBoolean();
			Configuration.enableNewHpBars = in.readBoolean();
			Configuration.enableNewHitmarks = in.readBoolean();
			Configuration.enable10xDamage = in.readBoolean();
			Configuration.enableScreenGliding = in.readBoolean();
			Configuration.entityAttackPriority = in.readBoolean();
			Configuration.enableTimeStamps = in.readBoolean();
			Configuration.snow = in.readBoolean();
			Configuration.enablePrestigeColors = in.readBoolean();
			in.close();
			System.out.println("Settings loaded: " + strings.length);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
