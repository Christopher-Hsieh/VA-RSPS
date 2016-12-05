
/**
 * Handles custom loading of interfaces & tabs
 * 
 * @author Daniel
 *
 */
public class CustomInterfaces extends RSInterface {
	/**
	 * Unpacks custom interfaces
	 * 
	 * @param Vencillio
	 */
	public static void unpackInterfaces(TextDrawingArea[] Vencillio) {
		
		/*
		 * Godwars interface
		 */
		godWars(Vencillio);

		/*
		 * The quest tab interface (Handles important information of server &
		 * player)
		 */

		questTab(Vencillio);

		/*
		 * The equipment tab & equipment screen
		 */
		equipmentTab(Vencillio);
		equipmentScreen(Vencillio);

		/*
		 * Quick prayer & curses
		 */
		quickCurses(Vencillio);
		quickPrayers(Vencillio);

		/*
		 * Handles the tab to configure client settings
		 */
		optionTab(Vencillio);

		/*
		 * Handles the wilderness target interface
		 */
		bounty(Vencillio);

		/*
		 * Handles teleporting to various locations around Vencillio
		 */
		trainingTeleport(Vencillio);
		skillingTeleport(Vencillio);
		pvpTeleport(Vencillio);
		bossTeleport(Vencillio);
		minigameTeleport(Vencillio);
		otherTeleport(Vencillio);

		/*
		 * Handles the shop interface. Important component to the economy
		 */
		shop(Vencillio);

		/*
		 * Handles changing game play settings for client
		 */
		settings(Vencillio);
		screenOptions(Vencillio);

		/*
		 * Handles the achievement tab (Lists all the achievements)
		 */
		achievementsTab(Vencillio);

		/*
		 * Handles an shop exchange system (Another vital component to the economy)
		 */
		shopExchange(Vencillio);

		/*
		 * Handles the credits system for Vencillio
		 */
		creditsTab(Vencillio);

		/*
		 * Handles showing player what items will be kept and lost on death
		 */
		itemsOnDeath(Vencillio);

		/*
		 * Handles the main interface of bank tabs (Creating and collapsing etc)
		 */
		bank(Vencillio);
		bankSettings(Vencillio);
		
		/*
		 * Handles price checking item(s) prices 
		 */
		priceChecker(Vencillio);
		
		/*
		 * Handles displaying progression of barrows minigame
		 */
		barrows(Vencillio);
		
		/*
		 * Handles reseting a player's statistic
		 */
		genie(Vencillio);
		
		/* 
		 * Handles the random event - Mystery Box
		 */
		mysteryBox(Vencillio);
		
		/*
		 * Handles the monster guide
		 */
		dropTable(Vencillio);
		
		/**
		 * Handles an item details
		 */
		itemDetails(Vencillio);
		
		/*
		 * Handles changing private messages
		 */
		chatColor(Vencillio);
		
		/*
		 * Handles changing the setting of how EXP counter will be displayed
		 */
		expCounter(Vencillio);
		
		/*
		 * Handles adding friends
		 */
		friendsTab(Vencillio);
		
		/*
		 * Handles ignoring faggots
		 */
		ignoreTab(Vencillio);
		
		/*
		 * Handles prestiging
		 */
		prestige(Vencillio);
		
		/*
		 * Handles displaying information on Warrior Guild minigame
		 */
		warriorGuild(Vencillio);
		
		/*
		 * Handles displaying information on the Pest Control minigame
		 */
		pestControlBoat(Vencillio);
		pestControlGame(Vencillio);
		
		/*
		 * Handles Player Profiler interface
		 */
		playerProfiler(Vencillio);
		myProfile(Vencillio);
		//profileTab(Vencillio);
		profileLeaderboards(Vencillio);
		
		/*
		 * Shows list of tracked statistics for a player
		 */
		tracker(Vencillio);
		
		/**
		 * Loyalty title
		 */
		loyaltyShop(Vencillio);
		
		/**
		 * Quest interface
		 */
		questInterface(Vencillio);
		
		/**
		 * Handles the fire colors
		 */
		fireColor(Vencillio);

		/*
		 * The clan chat interface & tab (Handles all the main functions of clan
		 * chat)
		 */
		clanChatTab(Vencillio);
		clanChatSetup(Vencillio);
		
		/*
		 * In PvP interface
		 */
		inPvP(Vencillio);
		
		/*
		 * In safe interface
		 */
		inSafe(Vencillio);
		
		/* 
		 * In timer interface
		 */
		inTimer(Vencillio);
		
		/*
		 * PK skilltab
		 */
		PKSkillTab(Vencillio);
		
		/*
		 * Spawn tab
		 */
		spawnTab(Vencillio);
		
		/*
		 * Starter interface
		 */
		starter(Vencillio);
		
		/**
		 * Tab Creation
		 */
		tabCreation(Vencillio);
		
		/**
		 * Report Abuse
		 */
		reportAbuse(Vencillio);
		
		/**
		 * Bolt enchanting
		 */
		boltEnchanting(Vencillio);
		
		/**
		 * Weapon Game
		 */
		weaponLobby(Vencillio);
		weaponGame(Vencillio);
		weaponStore(Vencillio);
		
		/**
		 * Bank Pins Management
		 */
		bankPinManager(Vencillio);
		
		/**
		 * Account Manager
		 */
		accountManager(Vencillio);
		
		/**
		 * Bank Pin
		 */
		bankPin(Vencillio);
		
		/**
		 * Ticket Interface
		 */
		ticketInterface(Vencillio);
		
		/**
		 * Staff Tab
		 */
		staffTab(Vencillio);
	}
	
	public static void screenOptions(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(28200);
		addSprite(28201, 472);
		addText(28202, "Screen Options", vencillio, 2, 0xff9933, true, true);
		addHoverButton(28203, 17, 21, 21, "Close", 250, 28204, 3);
		addHoveredButton(28204, 18, 21, 21, 28205);				
		addButton(28206, 470, "Transparent side panel");
		addButton(28207, 468, "Transparent chatbox");
		addButton(28208, 466, "Side-stones arrangement");
		addText(28209, "Transparent side panel", vencillio, 1, 0xff9933, false, true);
		addText(28210, "Make each side panel transparent", vencillio, 0, 0xff9933, false, true);		
		addText(28211, "Transparent chatbox", vencillio, 1, 0xff9933, false, true);
		addText(28212, "Make chatbox transparent", vencillio, 0, 0xff9933, false, true);	
		addText(28213, "Side-stones arrangement", vencillio, 1, 0xff9933, false, true);
		addText(28214, "Change the side-stones arrangement", vencillio, 0, 0xff9933, false, true);		
		addText(28215, "Settings are not applicable in fixed mode!", vencillio, 0, 0xff9933, true, true);
		
		tab.totalChildren(14);
		tab.child(0, 28201, 145, 70);
		tab.child(1, 28202, 270, 80);
		tab.child(2, 28203, 365, 77);
		tab.child(3, 28204, 365, 77);
		tab.child(4, 28206, 155, 110);
		tab.child(5, 28207, 155, 165);
		tab.child(6, 28208, 155, 220);
		tab.child(7, 28209, 200, 115);
		tab.child(8, 28210, 200, 130);		
		tab.child(9, 28211, 200, 170);
		tab.child(10, 28212, 200, 185);		
		tab.child(11, 28213, 200, 225);
		tab.child(12, 28214, 200, 240);
		tab.child(13, 28215, 270, 261);
	}
	
	public static void weaponStore(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(56500);
		addSprite(56501, 462);
		addText(56502, "Weapon Game Reward Store", daniel, 2, 0xff9933, true, true);
		itemContainer(56503, 28, 8, 6, 50, false, "Select", "Buy");
		addText(56504, "Welcome to the Weapon Game Rewards store!", daniel, 0, 0xff9933, true, true);
		addText(56505, "", daniel, 0, 0xff9933, true, true);
		addText(56506, "", daniel, 0, 0xff9933, false, true);
		addText(56507, "", daniel, 0, 0xff9933, false, true);
		itemContainer(56508, 28, 8, 20, 50, false);
		addText(56509, "", daniel, 0, 0xff9933, false, true);
		addHoverButton(56510, 17, 21, 21, "Close", 250, 56511, 3);
		addHoveredButton(56511, 18, 21, 21, 56512);	
		tab.totalChildren(11);
		tab.child(0, 56501, 50, 20);
		tab.child(1, 56502, 255, 27);
		tab.child(2, 56503, 87, 105);
		tab.child(3, 56504, 192, 62);
		tab.child(4, 56505, 191, 77);
		tab.child(5, 56506, 325, 62);
		tab.child(6, 56508, 400, 60);
		tab.child(7, 56507, 325, 77);
		tab.child(8, 56509, 340, 69);
		tab.child(9, 56510, 440, 24);
		tab.child(10, 56511, 440, 24);
	}
	
	public static void bankPin(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(48750);
		addSprite(48751, 461);
		addText(48752, "Bank Pin", daniel, 2, 0xff9933, true, true);
		addText(48753, "You need to enter your bank pin", daniel, 1, 0xff9933, true, true);
		addText(48754, "before accessing this!", daniel, 1, 0xff9933, true, true);
		addInputField(48750, 48755, 4, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 150, 23, false, false, "");
		addHoverText(48756, "Close window", "Close window", daniel, 1, 0xF7AA25, false, true, 250);	
		tab.totalChildren(6);
		tab.child(0, 48751, 125, 105);
		tab.child(1, 48752, 270, 110);
		tab.child(2, 48753, 270, 130);
		tab.child(3, 48754, 270, 145);
		tab.child(4, 48755, 197, 170);
		tab.child(5, 48756, 410, 20);
		
	}
	
	public static void accountManager(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(56000);
		addSprite(56001, 460);
		addHoverButton(56002, 17, 21, 21, "Close", 250, 56003, 3);
		addHoveredButton(56003, 18, 21, 21, 56004);	
		addText(56005, "Account Security Management", daniel, 2, 0xff9933, true, true);
		addText(56006, "Please answer the following prompts to ensure a safe account.", daniel, 0, 0xff9933, true, true);
		addText(56007, "Having a safe account is crucial in the world of Vencillio.", daniel, 0, 0xff9933, true, true);
		addText(56008, "Make sure to write all this information down!", daniel, 0, 0xff9933, true, true);
		addText(56009, "Full Name", daniel, 2, 0xff9933, true, true);
		addText(56010, "Email Address:", daniel, 2, 0xff9933, true, true);
		addText(56011, "Recovery Answer:", daniel, 2, 0xff9933, true, true);
		addText(56012, "IP Address:", daniel, 2, 0xff9933, true, true);
		addInputField(56000, 56013, 30, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 250, 23, false, false, "");
		addInputField(56000, 56014, 30, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 250, 23, false, false, "");
		addInputField(56000, 56015, 30, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 250, 23, false, false, "");
		addInputField(56000, 56016, 30, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 250, 23, false, false, "");
		addText(56017, "@red@Once you set your information it cannot be changed!", daniel, 0, 0xff9933, true, true);
		tab.totalChildren(16);
		tab.child(0, 56001, 14, 5);
		tab.child(1, 56002, 470, 12);
		tab.child(2, 56003, 470, 12);	
		tab.child(3, 56005, 263, 15);
		tab.child(4, 56006, 263, 47);
		tab.child(5, 56007, 263, 62);
		tab.child(6, 56008, 263, 77);
		tab.child(7, 56009, 105, 133);
		tab.child(8, 56010, 105, 185);
		tab.child(9, 56011, 105, 237);
		tab.child(10, 56012, 105, 289);
		tab.child(11, 56013, 220, 130);
		tab.child(12, 56014, 220, 180);
		tab.child(13, 56015, 220, 230);
		tab.child(14, 56016, 220, 280);
		tab.child(15, 56017, 263, 90);
	}
	
	public static void ticketInterface(TextDrawingArea[] daniel) {
		RSInterface boob = addInterface(51250);
		addSprite(51251, 463);
		addText(51252, "Ticket Manager", daniel, 2, 0xff9933, true, true);
		addHoverButton(51253, 17, 21, 21, "Close", 250, 51254, 3);
		addHoveredButton(51254, 18, 21, 21, 51255);
		
		addInputField(51250, 51255, 20, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 175, 30, false, false, "");
		addText(51256, "Title:", daniel, 3, 0xff9933, true, true);
		addText(51257, "Issue:", daniel, 3, 0xff9933, true, true);
		addInputField(51250, 51258, 40, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 310, 25, false, false, "");
		addInputField(51250, 51259, 40, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 310, 25, false, false, "");
		
		addHoverButton(51260, 61, 75, 30, "Send ticket", -1, 51261, 1);
		addHoveredButton(51261, 62, 75, 30, 51262);
		
		addText(51263, "Send", daniel, 3, 0xff9933, true, true);
		
		boob.totalChildren(12);
		
		boob.child(0, 51251, 75, 50);
		boob.child(1, 51252, 245, 62);
		boob.child(2, 51253, 390, 59);
		boob.child(3, 51254, 390, 59);
		boob.child(4, 51255, 158, 115);
		boob.child(5, 51256, 245, 98);
		boob.child(6, 51257, 245, 150);
		boob.child(7, 51258, 93, 170);
		boob.child(8, 51259, 93, 200);
		
		boob.child(9, 51260, 205, 235);
		boob.child(10, 51261, 205, 235);
		boob.child(11, 51263, 245, 242);
	}
	
	public static void bankPinManager(TextDrawingArea[] daniel) {
		RSInterface boob = addInterface(43750);
		addSprite(43751, 459);
		addText(43752, "Bank Pin Management", daniel, 2, 0xff9933, true, true);
		addText(43753, "</col>Bank pin status: @red@None!", daniel, 0, 0xff9933, true, true);
		addText(43754, "Type in a 4 digit number and press enter to set a pin.", daniel, 0, 0xff9933, true, true);
		addInputField(43750, 43755, 4, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 100, 23, false, false, "");
		addText(43756, "Be sure to write this down!", daniel, 0, 0xff9933, true, true);
		
		addHoverButton(43757, 17, 21, 21, "Close", 250, 43758, 3);
		addHoveredButton(43758, 18, 21, 21, 43759);
		
		boob.totalChildren(8);
		boob.child(0, 43751, 115, 90);
		boob.child(1, 43752, 255, 98);
		boob.child(2, 43753, 255, 125);
		boob.child(3, 43754, 255, 140);
		boob.child(4, 43755, 205, 160);
		boob.child(5, 43756, 255, 200);
		
		boob.child(6, 43757, 375, 94);
		boob.child(7, 43758, 375, 94);
	}
	
	public static void weaponLobby(TextDrawingArea[] daniel) {
		RSInterface boob = addInterface(41250);
		addSprite(41251, 453);
		addText(41252, "Players ready: 0", daniel, 0, 0x33cc00, false, true);
		addText(41253, "(Need 5 to 20)", daniel, 0, 0xFFcc33, false, true);
		addText(41254, "Next Departure: 60 sec", daniel, 0, 0x33ccff, false, true);
		boob.totalChildren(4);
		boob.child(0, 41251, 378, 290);
		boob.child(1, 41252, 385, 292);
		boob.child(2, 41253, 385, 305);
		boob.child(3, 41254, 385, 318);
	}
	
	public static void weaponGame(TextDrawingArea[] daniel) {
		RSInterface boob = addInterface(41270);
		addTransparentSprite(41271, 452);
		addText(41272, "Weapon Game", daniel, 2, 0xff9933, true, true);
		addText(41273, "Time Left:", daniel, 0, 0xff9933, false, true);
		addText(41274, "5:30", daniel, 0, 0xFFFFFF, false, true);		
		addText(41275, "Players:", daniel, 0, 0xff9933, false, true);
		addText(41276, "7", daniel, 0, 0xFFFFFF, false, true);	
		addText(41277, "Leader:", daniel, 0, 0xff9933, false, true);
		addText(41278, "Daniel", daniel, 0, 0xFFFFFF, false, true);	
		addText(41279, "Crates:", daniel, 0, 0xff9933, false, true);
		addText(41280, "0:14", daniel, 0, 0xFFFFFF, false, true);	
		addText(41281, "Tier:", daniel, 0, 0xff9933, false, true);
		addText(41282, "5", daniel, 0, 0xFFFFFF, false, true);
		itemContainer(41283, 45, 8, 20, 50, true);
		
		addText(41284, "Current", daniel, 0, 0xff9933, false, true);
		addText(41285, "Next", daniel, 0, 0xff9933, false, true);
		
		boob.totalChildren(15);
		boob.child(0, 41271, 355, 2);
		boob.child(1, 41272, 433, 7);
		
		boob.child(2, 41273, 375, 25);
		boob.child(3, 41274, 455, 25);
		
		boob.child(4, 41275, 375, 40);
		boob.child(5, 41276, 455, 40);
		
		boob.child(6, 41277, 375, 55);
		boob.child(7, 41278, 455, 55);
		
		boob.child(8, 41279, 375, 70);
		boob.child(9, 41280, 455, 70);
		
		boob.child(10, 41281, 375, 85);
		boob.child(11, 41282, 455, 85);
		
		boob.child(12, 41283, 378, 103);
		
		boob.child(13, 41284, 373, 130);
		boob.child(14, 41285, 458, 130);
	}
	
	public static void boltEnchanting(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(42750);
		addSprite(42751, 451);
		itemDisplay(42752, 66, 100, 5, 5, "Make");
		addHoverButton(42753, 17, 21, 21, "Close", 250, 42754, 3);
		addHoveredButton(42754, 18, 21, 21, 42755);
		addText(42756, "Magic 4", daniel, 1, 0xff9933, true, true);
		addText(42757, "Magic 7", daniel, 1, 0xff9933, true, true);
		addText(42758, "Magic 14", daniel, 1, 0xff9933, true, true);
		addText(42759, "Magic 24", daniel, 1, 0xff9933, true, true);
		addText(42760, "Magic 27", daniel, 1, 0xff9933, true, true);
		addText(42761, "Magic 29", daniel, 1, 0xff9933, true, true);
		addText(42762, "Magic 49", daniel, 1, 0xff9933, true, true);
		addText(42763, "Magic 57", daniel, 1, 0xff9933, true, true);
		addText(42764, "Magic 68", daniel, 1, 0xff9933, true, true);
		addText(42765, "Magic 87", daniel, 1, 0xff9933, true, true);
		addText(42766, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42767, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42768, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42769, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42770, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42771, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42772, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42773, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42774, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42775, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42776, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42777, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42778, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42779, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42780, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42781, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42782, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42783, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42784, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42785, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42786, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42787, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42788, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42789, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42790, "0/0", daniel, 0, 0xFA0A0A, true, true);
		addText(42791, "0/0", daniel, 0, 0xFA0A0A, true, true);
		
		tab.totalChildren(40);
		tab.child(0, 42751, 11, 15);
		tab.child(1, 42752, 40, 100);
		tab.child(2, 42753, 471, 22);
		tab.child(3, 42754, 471, 22);
		tab.child(4, 42756, 56, 70);
		tab.child(5, 42757, 156, 70);
		tab.child(6, 42758, 256, 70);
		tab.child(7, 42759, 352, 70);
		tab.child(8, 42760, 443, 70);
		tab.child(9, 42761, 65, 200);
		tab.child(10, 42762, 152, 200);
		tab.child(11, 42763, 245, 200);
		tab.child(12, 42764, 342, 200);
		tab.child(13, 42765, 438, 200);
		tab.child(14, 42766, 41, 162);
		tab.child(15, 42767, 74, 162);
		tab.child(16, 42768, 124, 162);
		tab.child(17, 42769, 155, 162);
		tab.child(18, 42770, 185, 162);
		tab.child(19, 42771, 233, 162);
		tab.child(20, 42772, 265, 162);
		tab.child(21, 42773, 331, 162);
		tab.child(22, 42774, 364, 162);
		tab.child(23, 42775, 415, 162);
		tab.child(24, 42776, 447, 162);
		tab.child(25, 42777, 476, 162);
		tab.child(26, 42778, 40, 290);
		tab.child(27, 42779, 72, 290);
		tab.child(28, 42780, 126, 290);
		tab.child(29, 42781, 156, 290);
		tab.child(30, 42782, 185, 290);
		tab.child(31, 42783, 221, 290);
		tab.child(32, 42784, 250, 290);
		tab.child(33, 42785, 280, 290);
		tab.child(34, 42786, 314, 290);
		tab.child(35, 42787, 344, 290);
		tab.child(36, 42788, 372, 290);
		tab.child(37, 42789, 413, 290);
		tab.child(38, 42790, 445, 290);
		tab.child(39, 42791, 475, 290);
	}
	
	public static void reportAbuse(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(41750);
		addSprite(41751, 449);
		addHoverButton(41752, 17, 21, 21, "Close", 250, 41753, 3);
		addHoveredButton(41753, 18, 21, 21, 41754);
		addText(41755, "Report Abuse", daniel, 2, 0xff9933, true, true);
		
		addText(41756, "This form is for reporting players who are breaking our rules.", daniel, 1, 0xff9933, true, true);
		addText(41757, "Using it sends a snapshot of the last 60 seconds of activity to us.", daniel, 1, 0xff9933, true, true);
		addText(41758, "Misusing this form will result with an infraction, or worse.", daniel, 1, 0xDB0D0D, true, true);
		addText(41759, "Please enter the username of the offending player:", daniel, 1, 0xff9933, true, true);
		
		addInputField(41750, 41760, 25, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 180, 23, false, false, "");
		
		addText(41761, "Please select the rule of which the offending player has broken.", daniel, 1, 0xff9933, true, true);
		
		String[] rules = { 
			"Offensive language", "Item scamming", "Password scamming", "Bug abuse",
			"Vencillio staff impersonation", "Account sharing/trading", "Macroing", "Multiple logging in",
			"Advertising", "Real world trading", "Misuse of customer support", "Encouraging others to break rules",
		};
		
		for (int i = 0; i < rules.length; i ++) {
			addHoverText(41762 + i, (i + 1) + ") " + rules[i], "Select", daniel, 0, 0xF7AA25, false, true, 250);	
		}
		
		addHoverButton(41774, 446, 135, 30, "Confirm selection", -1, 41775, 1);
		addHoveredButton(41775, 447, 135, 30, 41776);
		
		 tab.totalChildren(24);
		 tab.child(0, 41751, 11, 17);
		 tab.child(1, 41752, 471, 24);
		 tab.child(2, 41753, 471, 24);
		 tab.child(3, 41755, 257, 26);
		 tab.child(4, 41756, 257, 55);
		 tab.child(5, 41757, 257, 70);
		 tab.child(6, 41758, 257, 85);
		 tab.child(7, 41759, 257, 105);
		 tab.child(8, 41760, 170, 125);
		 tab.child(9, 41761, 257, 160);
		 
		 tab.child(10, 41762, 65, 190);
		 tab.child(11, 41763, 65, 205);
		 tab.child(12, 41764, 65, 220);
		 tab.child(13, 41765, 65, 235);
		 tab.child(14, 41766, 65, 250);
		 tab.child(15, 41767, 65, 265);
		 
		 tab.child(16, 41768, 285, 190);
		 tab.child(17, 41769, 285, 205);
		 tab.child(18, 41770, 285, 220);
		 tab.child(19, 41771, 285, 235);
		 tab.child(20, 41772, 285, 250);
		 tab.child(21, 41773, 285, 265);
		 
		 tab.child(22, 41774, 160, 280);
		 tab.child(23, 41775, 160, 280);
	}
	
	public static void tabCreation(TextDrawingArea[] TDA) {
		  RSInterface tab = addTabInterface(26700);
		  addSprite(26701, 448);
		  addHoverButton(26702, 17, 21, 21, "Close", 250, 26703, 3);
		  addHoveredButton(26703, 18, 21, 21, 26704);
		  addText(26705, "Tablet Creation", TDA, 2, 0xff9933, true, true);
		  itemDisplay(26706, 50, 12, 3, 5, "Make", "Info");
		  addText(26707, "Please click on a tab to create it", TDA, 0, 0xff9933, true, true);
		  addText(26708, "Click on 'info' to get requirements.", TDA, 0, 0xff9933, true, true);
		  tab.totalChildren(7);
		  tab.child(0, 26701, 100, 70);
		  tab.child(1, 26702, 475, 10);
		  tab.child(2, 26703, 475, 10);
		  tab.child(3, 26705, 257, 80);
		  tab.child(4, 26706, 158, 144);
		  tab.child(5, 26707, 257, 112);
		  tab.child(6, 26708, 257, 122);
	}
	
	public static void bounty(TextDrawingArea[] TDA) {
		  RSInterface tab = addTabInterface(23300);
		  addAdvancedSprite(23301, 434);
		  addAdvancedSprite(23302, 433);
		  addConfigSprite(23303, 435, -1, 0, 876);
		  addSprite(23304, 441);
		  addText(23305, "---", TDA, 0, 0xffff00, true, true);
		  addText(23306, "Target:", TDA, 0, 0xffff00, true, true);
		  addText(23307, "None", TDA, 1, 0xffffff, true, true);
		  addText(23308, "Level: ------", TDA, 0, 0xffff00, true, true);
		  addText(23309, "Current  Record", TDA, 0, 0xffff00, true, true);
		  addText(23310, "0", TDA, 0, 0xffff00, true, true);
		  addText(23311, "0", TDA, 0, 0xffff00, true, true);
		  addText(23312, "0", TDA, 0, 0xffff00, true, true);
		  addText(23313, "0", TDA, 0, 0xffff00, true, true);
		  addText(23314, "Rogue:", TDA, 0, 0xffff00, true, true);
		  addText(23315, "Hunter:", TDA, 0, 0xffff00, true, true);
		  addConfigSprite(23316, -1, 436, 0, 877);
		  addConfigSprite(23317, -1, 437, 0, 878);
		  addConfigSprite(23318, -1, 438, 0, 879);
		  addConfigSprite(23319, -1, 439, 0, 880);
		  addConfigSprite(23320, -1, 440, 0, 881);
		  tab.totalChildren(21);
		  tab.child(0, 23301, 319, 8);
		  tab.child(1, 23302, 339, 56);
		  tab.child(2, 23303, 345, 18);
		  tab.child(3, 23304, 348, 73);
		  tab.child(4, 23305, 358, 41);
		  tab.child(5, 23306, 455, 12);
		  tab.child(6, 23307, 456, 25);
		  tab.child(7, 23308, 457, 41);
		  tab.child(8, 23309, 460, 59);
		  tab.child(9, 23310, 438, 72);
		  tab.child(10, 23311, 481, 72);
		  tab.child(11, 23312, 438, 85);
		  tab.child(12, 23313, 481, 85);
		  tab.child(13, 23314, 393, 72);
		  tab.child(14, 23315, 394, 85);
		  tab.child(15, 23316, 345, 18);
		  tab.child(16, 23317, 345, 18);
		  tab.child(17, 23318, 345, 18);
		  tab.child(18, 23319, 345, 18);
		  tab.child(19, 23320, 345, 18);
		  tab.child(20, 197, 2, 2);
	}


	
	public static String[][] shopCategories = new String[][] { { "Purchasables", "413" }, { "Achievements", "414"}, { "Colors", "407"} };

	public static String[][][] shopContent = new String[][][] {
		/** Main Titles */
		{ 
			{ "Sir", "15" }, 
			{ "Miss", "15" }, 
			{ "Lord", "20" }, 
			{ "Duderino", "25" }, 
			{ "Copycat", "50" }, 
			{ "King", "50" }, 
			{ "Queen", "50" }, 
			{ "Fam", "60" }, 
			{ "Brother", "65" }, 
			{ "Sister", "65" }, 
			{ "Overlord", "75" }, 
			{ "The", "100" }, 
			{ "RichieRich", "100" }, 
			{ "Emperor", "125" },
			{ "Immortal", "135" },
			{ "The Great", "140" },
			{ "Champion", "150" },
			{ "Swagtastic", "175" },
			{ "Godly", "200" },
			{ "Respected", "205" },
			{ "The One", "210" },
			{ "Holy", "220" },
			{ "Skilled", "230" },
			
			
		},
	
		/** Achievement Titles */
		{ 
			{ "Skeletal", "250 skeletal" }, 
			{ "Blood", "1500 blood" }, 
			{ "Multi-task", "100 tasks" }, 
			{ "Pet", "10 pets" }, 
			{ "Tztok", "50 firecape" }, 
			{ "The Game", "Win WG 10" }, 
			{ "Big Bear", "100 callisto" }, 
			//{ "Trivial", "80 triviabot" }, 
			//{ "The Killer", "Killer IV" }, 
			//{ "Tztok", "Tztok" },
			//{ "Resourceful", "Resourceful" }, 
			//{ "Entitled", "Entitled" }, 
		},
	
		/** Colors */
		{ 
			{ "<col=C22323>Red", "@whi@5,000.000" }, 
			{ "<col=0FA80F>Green", "@whi@5,000.000" }, 
			{ "<col=2AA4C9>Blue", "@whi@5,000.000" },
			{ "<col=C9BC28>Yellow", "@whi@5,000.000" },
			{ "<col=F58D16>Orange", "@whi@5,000.000" },
			{ "<col=C931E8>Purple", "@whi@5,000.000" },
			{ "<col=F52CD7>Pink", "@whi@5,000.000" },
			{ "<col=FFFFFF>White", "@whi@5,000.000" }, 
		} 
	};
	
	public static void fireColor(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(49750);
		addSprite(49751, 400);
		addHoverButton(49752, 21, 21, 21, "Close", 250, 49753, 3);
		addHoveredButton(49753, 22, 21, 21, 49754);
		addText(49755, "Fire Color Changer", 0xff9933, true, true, -1, vencillio, 2);
		addText(49756, "By paying 10 credits you can change all fire colors.", 0xff9933, true, true, -1, vencillio, 0);
		addText(49757, "You will earn double cooking & firemaking exp for 30mins.", 0xff9933, true, true, -1, vencillio, 0);
		addText(49758, "Current Color: Orange", 0xff9933, true, true, -1, vencillio, 0);
		addText(49759, "Changed by: Daniel", 0xff9933, true, true, -1, vencillio, 0);
		addText(49760, "Daniel's Clan: Vencillio", 0xff9933, true, true, -1, vencillio, 0);
		addText(49761, "Change availability: 45minutes", 0xff9933, true, true, -1, vencillio, 0);
		addHoverButton(49762, "", 0, 65, 95, "Select Orange", 0, 49763, 1);
		addHoveredButton(49763, 401, 65, 95, 49764);
		addHoverButton(49765, "", 0, 65, 95, "Select Green", 0, 49766, 1);
		addHoveredButton(49766, 402, 65, 95, 49767);
		addHoverButton(49768, "", 0, 65, 95, "Select Blue", 0, 49769, 1);
		addHoveredButton(49769, 403, 65, 95, 49770);
		addHoverButton(49771, "", 0, 65, 95, "Select Red", 0, 49772, 1);
		addHoveredButton(49772, 404, 65, 95, 49773);
		tab.totalChildren(18);
		tab.child(0, 49751, 65, 55);
		tab.child(1, 49752, 420, 63);
		tab.child(2, 49753, 420, 63);
		tab.child(3, 49755, 257, 63);
		tab.child(4, 49756, 257, 100);
		tab.child(5, 49757, 257, 112);
		tab.child(6, 49758, 257, 233);
		tab.child(7, 49759, 257, 245);
		tab.child(8, 49760, 257, 257);
		tab.child(9, 49761, 257, 269);
		tab.child(10, 49762, 92, 133);
		tab.child(11, 49763, 92, 133);
		tab.child(12, 49765, 181, 133);
		tab.child(13, 49766, 181, 133);
		tab.child(14, 49768, 270, 133);
		tab.child(15, 49769, 270, 133);
		tab.child(16, 49771, 359, 133);
		tab.child(17, 49772, 359, 133);
	}
	
	public static void starter(TextDrawingArea[] daniel) {
		RSInterface Interface = addInterface(51750);
		
		addSprite(51751, 431);
		addText(51752, "Vencillio Account Setup", 0xff9933, true, true, 52, daniel, 2);
		addHoverButton(51753, 17, 21, 21, "Close", 250, 51754, 3);
		addHoveredButton(51754, 18, 21, 21, 51755);
		addText(51756, "Select Your Game Mode:", 0xff9933, true, true, 52, daniel, 1);
		addText(51757, "Starter Items:", 0xff9933, false, true, 52, daniel, 1);
		itemDisplay(51758, 7, 15, 8, 3);
		addText(51759, "Mode Description:", 0xff9933, true, true, 52, daniel, 2);
		addText(51760, "Line 1", 0xff9933, true, true, 52, daniel, 0);
		addText(51761, "Line 2", 0xff9933, true, true, 52, daniel, 0);
		addText(51762, "Line 3", 0xff9933, true, true, 52, daniel, 0);
		
		addConfigButton(51763, 51750, 425, 426, 14, 15, "Select Normal", 0, 5, 1085);
		addConfigButton(51764, 51750, 425, 426, 14, 15, "Select Ironman", 1, 5, 1085);		
		addConfigButton(51765, 51750, 425, 426, 14, 15, "Select Ultimate Ironman", 2, 5, 1085);
		
		addHoverText(51766,"Normal", "Select Normal", daniel, 0, 0xF7AA25, false, true, 250);
		addHoverText(51767,"Iron Man", "Select Iron Man", daniel, 0, 0xF7AA25, false, true, 250);
		addHoverText(51768,"Ultimate Iron Man", "Select Ultimate Iron Man", daniel, 0, 0xF7AA25, false, true, 250);
		
		addHoverButton(51769, -1, 123, 30, "Confirm selection", -1, 51770, 1);
		addHoveredButton(51770, 432, 123, 30, 51771);
		
		Interface.totalChildren(19);
		
		Interface.child(0, 51751, 7, 8);
		Interface.child(1, 51752, 250, 16);
		Interface.child(2, 51753, 480, 500);
		Interface.child(3, 51754, 480, 500);
		Interface.child(4, 51756, 415, 45);
		Interface.child(5, 51757, 25, 48);
		Interface.child(6, 51758, 24, 70);
		Interface.child(7, 51759, 250, 220);
		Interface.child(8, 51760, 250, 250);
		Interface.child(9, 51761, 250, 270);
		Interface.child(10, 51762, 250, 290);
		Interface.child(11, 51763, 366, 75);
		Interface.child(12, 51764, 366, 115);
		Interface.child(13, 51765, 366, 153);
		Interface.child(14, 51766, 388, 78);
		Interface.child(15, 51767, 388, 117);
		Interface.child(16, 51768, 388, 154);
		
		Interface.child(17, 51769, 364, 180);
		Interface.child(18, 51770, 364, 180);
	}
	
	public static void inPvP(TextDrawingArea[] daniel) {
		RSInterface Interface = addInterface(60250);
		addSprite(60251, 422);
		
		addTooltipBox(60252, "You are currently standing in a @red@PvP@bla@ Zone.");
		interfaceCache[60252].width = 30;
		interfaceCache[60252].height = 35;
		
		addText(60253, "@or1@116 - 126", 0x000000, true, true, 52, daniel, 0);
		addText(60254, "@or1@- - -", 0x000000, true, true, 52, daniel, 0);
		
		Interface.totalChildren(4);
		Interface.child(0, 60251, 455, 275);
		Interface.child(1, 60252, 460, 295);
		Interface.child(2, 60253, 474, 310);
		Interface.child(3, 60254, 474, 320);
	}
	
	public static void godWars(TextDrawingArea[] daniel) {
		int ID = 61750;
		RSInterface Interface = addInterface(ID);
		addText(ID + 1, "NPC Killcount", 0xEDE026, false, true, 52, daniel, 0);
		String[] TEXTS = { "Armadyl", "Bandos", "Saradomin", "Zamorak" };
		for (int index = 0; index < 4; index++) {
			addText(ID + (2 + index), TEXTS[index] + " kills", 0xff9040, false, true, 52, daniel, 0);
		}
		for (int index = 0; index < 4; index++) {
			addText(ID + (6 + index), "0", 0x54BFE3, false, true, 52, daniel, 0);
		}
		Interface.totalChildren(9);
		Interface.child(0, ID + 1, 390, 7);
		for (int index = 0; index < 4; index++) {
			Interface.child(1 + index, ID + (2 + index), 390, 30 + (index * 15));
		}
		for (int index = 0; index < 4; index++) {
			Interface.child(5 + index, ID + (6 + index), 480, 30 + (index * 15));
		}
	}
	
	public static void inSafe(TextDrawingArea[] daniel) {
		RSInterface Interface = addInterface(60350);
		addSprite(60351, 423);
		
		addTooltipBox(60352, "You are currently standing in a @blu@Safe@bla@ Zone.");
		interfaceCache[60352].width = 30;
		interfaceCache[60352].height = 35;
		
		Interface.totalChildren(2);
		Interface.child(0, 60351, 460, 295);
		Interface.child(1, 60352, 460, 295);
	}
	
	public static void inTimer(TextDrawingArea[] daniel) {
		RSInterface Interface = addInterface(60450);
		addSprite(60451, 424);
		addTooltipBox(60452, "You currently have a PvP timer.");
		addText(60453, "@whi@10", 0x000000, true, true, 52, daniel, 3);
		interfaceCache[60452].width = 30;
		interfaceCache[60452].height = 35;
		
		Interface.totalChildren(3);
		Interface.child(0, 60451, 460, 295);
		Interface.child(1, 60452, 460, 295);
		Interface.child(2, 60453, 476, 303);
	}
	
	public static void spawnTab(TextDrawingArea[] daniel) {
		RSInterface Interface = addInterface(61250);
		addSprite(61251, 82);
		addSprite(61252, 38);
		addText(61253, "VencillioPK Spawn Tab", daniel, 0, 0xF7AA25, true, true);
		addInputField(61250, 61254, 20, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 180, 23, false, false, "");
		addInputField(61250, 61255, 20, 0x332E24, 0x4D4636, 0x383631, 0x474540, "1", 180, 23, true, false, "");
		addText(61257, "Item name:", daniel, 0, 0xF7AA25, false, true);
		addText(61258, "Item amount:", daniel, 0, 0xF7AA25, false, true);
		
		
		addText(61262, "Unnote:", daniel, 0, 0xF7AA25, false, true);
		addText(61248, "Note:", daniel, 0, 0xF7AA25, false, true);
		addText(61264, "Bank:", daniel, 0, 0xF7AA25, false, true);
		addConfigButton(61263, 61250, 425, 426, 14, 15, "Select unnote", 0, 5, 1075);
		addConfigButton(61249, 61250, 425, 426, 14, 15, "Select note", 1, 5, 1075);		
		addConfigButton(61265, 61250, 425, 426, 14, 15, "Select bank", 2, 5, 1075);

		addSprite(61259, 74);
		RSInterface scrollInterface = addTabInterface(61100);
		scrollInterface.width = 179;
		scrollInterface.height = 136;
		scrollInterface.scrollMax = 40 * 100 + 12;
		setChildren(200, scrollInterface);
		int y = 17;
		for (int i = 0; i < 100; i++) {
			addHoverText(61101 + i,"", "Select item", daniel, 1, 0xF7AA25, false, false, 250);
			setBounds(61101 + i, 41, y, i, scrollInterface);
			
			addSprite(61266 + i, new Sprite(25, 25));
			setBounds(61266 + i, 8, y - 10, i + 100, scrollInterface);
			y += 40;
		}
		
		Interface.totalChildren(21);
		Interface.child(0, 61251, -4, 15);
		Interface.child(1, 61252, -0, 15);
		Interface.child(2, 61251, -4, 58);
		Interface.child(3, 61253, 92, 2);
		Interface.child(4, 61257, 10, 19);
		Interface.child(5, 61258, 10, 58);
		Interface.child(6, 61252, 0, 253);
		Interface.child(7, 61262, 10, 100);
		Interface.child(8, 61263, 50, 97);
		Interface.child(9, 61264, 140, 100);
		Interface.child(10, 61265, 170, 97);
		Interface.child(11, 61252, 0, 115);
		Interface.child(12, 61259, 0, 118);
		Interface.child(13, 61254, 6, 32);
		Interface.child(14, 61255, 6, 71);
		Interface.child(15, 61259, 0, 182);
		Interface.child(15, 61259, 0, 182);
		Interface.child(16, 61259, 70, 118);
		Interface.child(17, 61259, 70, 182);
		Interface.child(18, 61100, -5, 117);
		
		Interface.child(19, 61248, 80, 100);
		Interface.child(20, 61249, 110, 97);
		
	}
	
	public static void PKSkillTab(TextDrawingArea[] daniel) {
		RSInterface Interface = addInterface(63700);
		addSprite(63701, 71);
		String[] levels = { "Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Bounty" };
		for (int i = 0; i < 8; i ++) {
			addText(63702 + i, "@or1@" + levels[i], 0x000000, false, true, 52, daniel, 2);
		}
		for (int i = 0; i < 8; i ++) {
			addText(63720 + i, "@or2@99/99", 0x000000, false, true, 52, daniel, 1);
		}
		addHoverButton(64102, -1, 300, 25, "Set attack level", -1, 64103, 1);
		addHoveredButton(64103, 386, 300, 25, 64104);
		addHoverButton(64105, -1, 300, 25, "Set defence level", -1, 64106, 1);
		addHoveredButton(64106, 388, 300, 25, 64107);	
		addHoverButton(64108, -1, 300, 25, "Set strength level", -1, 64109, 1);
		addHoveredButton(64109, 387, 300, 25, 64110);	
		addHoverButton(64111, -1, 300, 25, "Set hitpoints level", -1, 64112, 1);
		addHoveredButton(64112, 392, 300, 25, 64113);
		addHoverButton(64114, -1, 300, 25, "Set ranged level", -1, 64115, 1);
		addHoveredButton(64115, 389, 300, 25, 64116);
		addHoverButton(64117, -1, 300, 25, "Set prayer level", -1, 64118, 1);
		addHoveredButton(64118, 391, 300, 25, 64119);
		addHoverButton(64120, -1, 300, 25, "Set magic level", -1, 64121, 1);
		addHoveredButton(64121, 390, 300, 25, 64122);
		Interface.totalChildren(31);
		Interface.child(0, 63701, 1, 8);
		Interface.child(1, 64102, 2, 10);
		Interface.child(2, 64103, 2, 10);
		Interface.child(3, 64105, 2, 40);
		Interface.child(4, 64106, 2, 40);
		Interface.child(5, 64108, 2, 70);
		Interface.child(6, 64109, 2, 70);
		Interface.child(7, 64111, 2, 100);
		Interface.child(8, 64112, 2, 100);
		Interface.child(9, 64114, 2, 130);
		Interface.child(10, 64115, 2, 130);
		Interface.child(11, 64117, 2, 160);
		Interface.child(12, 64118, 2, 160);
		Interface.child(13, 64120, 2, 190);
		Interface.child(14, 64121, 2, 190);
		for (int i = 0; i < 8; i ++) {
			Interface.child(15 + i, 63702 + i, 30, 16 + (i * 30));
		}
		for (int i = 0; i < 8; i ++) {
			Interface.child(23 + i, 63720 + i, 125, 17 + (i * 30));
		}
	}
	
	
	
	public static void questInterface(TextDrawingArea[] TDA) {
		RSInterface Interface = addInterface(8134);
		Interface.centerText = true;
		addSprite(8135, 382);
		addSprite(8136, 383);
		addText(8144, "Quest Name", 0x000000, true, false, 52, TDA, 3);
		addHoverButton(8137, 384, 26, 23, "Close", 250, 8138, 3);
		addHoveredButton(8138, 385, 26, 23, 8139);
		setChildren(6, Interface);
		setBounds(8136, 18, 4, 0, Interface);
		setBounds(8135, 18, 62, 1, Interface);
		setBounds(8144, 260, 15, 2, Interface);
		setBounds(8140, 50, 86, 3, Interface);
		setBounds(8137, 452, 63, 4, Interface);
		setBounds(8138, 452, 63, 5, Interface);
		Interface = addInterface(8140);
		Interface.height = 217;
		Interface.width = 404;
		Interface.scrollMax = 1000;
		setChildren(51, Interface);
		int Ypos = 18;
		int frameID = 0;
		for(int iD = 8145; iD <= 8195;iD++) {
			addText(iD, "", 0x000080, true, false, 52, TDA, 1);
			setBounds(iD, 202, Ypos, frameID, Interface);
			frameID++;
			Ypos += 19;
			Ypos ++;
		}
	}

	public static void loyaltyShop(TextDrawingArea[] tda) {
		int id = 55000;
		RSInterface shop = addTabInterface(id);
		addSprite(id + 1, 372);
		addHoverButton(id + 2, 17, 21, 21, "Close", 250, id + 3, 3);
		addHoveredButton(id + 3, 18, 21, 21, id + 4);
		addText(id + 5, "Title Shop", tda, 0, 0xe6be78, true, true);
		addSprite(id + 6, 407);
		addText(id + 7, "Credits", tda, 0, 0xe6be78, false, true);
		addText(id + 8, "Welcome to the Vencillio title shop.", tda, 0, 0xe6be78, false, true);

		shop.totalChildren(8 + shopCategories.length * 3);
		for (int index = 0, frame = 0, configOffset = 0; index < shopCategories.length * 3; index += 3) {
			addHoverButton(id + frame + 9, 380, 120, 28, shopCategories[index / 3][0], 0xBABE, id + frame + 10, 1);
			addHoveredButton(id + frame + 10, 381, 120, 28, id + frame + 11);
			addText(id + frame + 12, shopCategories[index / 3][0], tda, 0, 0xE6BE78, false, true);

			loyaltySubShop(id + frame + 13, id, configOffset, tda, shopContent[index / 3], Integer.parseInt(shopCategories[index / 3][1]));

			shop.child(index + 7, id + frame + 9, 9, 100 + (index / 3) * 30);
			shop.child(index + 8, id + frame + 10, 9, 100 + (index / 3) * 30);
			shop.child(index + 9, id + frame + 12, 38, 108 + (index / 3) * 30);
			frame += shopContent[index / 3].length * 8 + 5;
			configOffset += shopContent[index / 3].length;
		}

		shop.child(0, id + 1, 1, 15);
		shop.child(1, id + 2, 484, 22);
		shop.child(2, id + 3, 484, 22);
		shop.child(3, id + 5, 65, 55);
		shop.child(4, id + 6, 17, 78);
		shop.child(5, id + 7, 35, 78);
		shop.child(6, id + 8, 223, 53);
		shop.child(8 + shopCategories.length * 3 - 1, id + 13, 131, 70);
	}

	public static void loyaltySubShop(int id, int parent, int configOffset, TextDrawingArea[] tda, String[][] titles, int currency) {
		RSInterface shop = addTabInterface(id);
		shop.totalChildren(titles.length * 7);
		int frame = 0;
		for (int index = 0; index < titles.length * 7; index += 7) {
			String title = titles[(index / 7)][0];
			String price = titles[(index / 7)][1];
			int x = ((index / 7) % 3) * 120;
			int y = ((index / 7) / 3) * 50;
			addSprite(id + frame + 1, 377);

			addHoverConfigButton(id + frame + 2, id + frame + 3, 374, 375, 56, 11, "Buy", new int[] { 0 }, new int[] { 0 }, new int[][] { { 1040 + index / 7 + configOffset } });
			addHoveredConfigButton(interfaceCache[id + frame + 2], id + frame + 3, id + frame + 4, 373, 376);

			addText(id + frame + 5, title, tda, 0, 0xE6BE78, true, true);
			addSprite(id + frame + 6, currency);
			addText(id + frame + 7, price, tda, 0, 0xDB9000, false, true);
			addText(id + frame + 8, "Buy", tda, 0, 0xE6BE78, false, true);

			shop.child(index, id + frame + 1, x, y);
			shop.child(index + 1, id + frame + 2, x + 48, y + 32);
			shop.child(index + 2, id + frame + 3, x + 48, y + 32);
			shop.child(index + 3, id + frame + 5, x + 73, y + 8);
			shop.child(index + 4, id + frame + 6, x + 39, y + 21);
			shop.child(index + 5, id + frame + 7, x + 51, y + 21);
			shop.child(index + 6, id + frame + 8, x + 67, y + 32);

			frame += 8;
		}
		shop.width = 355;
		shop.height = 236;
		shop.scrollMax = (titles.length / 3 + (titles.length % 3 > 0 ? 1 : 0)) * 50;
		shop.parentID = parent;
	}


	public static void tracker(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(54100);
		addSprite(54101, 371);
		addHoverButton(54102, 17, 21, 21, "Close", 250, 54103, 3);
		addHoveredButton(54103, 18, 21, 21, 54104);
		addText(54105, "Tracker", 0xff9933, true, true, -1, vencillio, 2);
		addText(54106, "Show's a list of tracked statistics.", 0xff9933, true, true, -1, vencillio, 0);
		tab.totalChildren(6);
		tab.child(0, 54101, 100, 45);
		tab.child(1, 54102, 390, 52);
		tab.child(2, 54103, 390, 52);
		tab.child(3, 54105, 260, 55);
		tab.child(4, 54106, 260, 285);
		tab.child(5, 54110, -155, 107);
		RSInterface scrollInterface = addTabInterface(54110);
		scrollInterface.width = 524;
		scrollInterface.height = 166;
		scrollInterface.scrollMax = 1000;
		setChildren(50, scrollInterface);
		int y = 0;
		for (int i = 0; i < 50; i++) {
			addHoverText(54111 + i, "", "", vencillio, 0, 0xcc8000, true, true, 300);
			setBounds(54111 + i, 260, y, i, scrollInterface);
			y += 20;
		}
	}
	
	/**
	 * Bank settings
	 * @param vencillio
	 */
	public static void bankSettings(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(32500);
		addSprite(32501, 368);
		addText(32502, "Vencillio Bank Settings", 0xff9933, true, true, -1, vencillio, 2);
		addHoverButton(32503, 17, 21, 21, "Close", 250, 32504, 3);
		addHoveredButton(32504, 18, 21, 21, 32505);
		addConfigButton(32506, 32500, 289, 290, 14, 15, "Select", 0, 5, 1011);
		addConfigButton(32507, 32500, 289, 290, 14, 15, "Select", 1, 5, 1011);
		addConfigButton(32508, 32500, 289, 290, 14, 15, "Select", 2, 5, 1011);
		addText(32509, "First item in tab", 0xff9933, true, true, -1, vencillio, 1);
		addText(32510, "Digit (1, 2, 3)", 0xff9933, true, true, -1, vencillio, 1);
		addText(32511, "Roman numeral (I, II, III)", 0xff9933, true, true, -1, vencillio, 1);
		addHoverText(32512, "Back to bank", "View", vencillio, 1, 0xcc8000, true, true, 100, 0xFFFFFF);
		tab.totalChildren(11);
		tab.child(0, 32501, 115, 35);
		tab.child(1, 32502, 263, 44);
		tab.child(2, 32503, 373, 42);
		tab.child(3, 32504, 373, 42);
		tab.child(4, 32506, 150, 65 + 30);
		tab.child(5, 32507, 150, 65 + 60);
		tab.child(6, 32508, 150, 65 + 90);
		tab.child(7, 32509, 218, 65 + 30);
		tab.child(8, 32510, 210, 65 + 60);
		tab.child(9, 32511, 239, 65 + 90);
		tab.child(10, 32512, 275, 265);
	}
	
	/**
	 * Profile Leaderboards
	 * @param daniel
	 */
	public static void profileLeaderboards(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(47400);			
		addSprite(47401, 367);
		addHoverButton(47402, 17, 21, 21, "Close", 250, 47403, 3);
		addHoveredButton(47403, 18, 21, 21, 47404);
		addText(47405, "Profile Leaderboards", daniel, 2, 0xF7AA25, true, true);		
		addHoverButton(47406, 59, 123, 30, "Views", 0, 47407, 1);
		addHoveredButton(47407, 60, 123, 30, 47408);
		addHoverButton(47409, 59, 123, 30, "Tab 2", 0, 47410, 1);
		addHoveredButton(47410, 60, 123, 30, 47411);
		addHoverButton(47412, 59, 123, 30, "Tab 3", 0, 47413, 1);
		addHoveredButton(47413, 60, 123, 30, 47414);
		addHoverButton(47415, 59, 123, 30, "Tab 4", 0, 47416, 1);
		addHoveredButton(47416, 60, 123, 30, 47417);
		addText(47418, "Views", daniel, 2, 0xF7AA25, true, true);	
		addText(47419, "Likes", daniel, 2, 0xF7AA25, true, true);	
		addText(47420, "Dislikes", daniel, 2, 0xF7AA25, true, true);	
		addText(47421, "Ratio", daniel, 2, 0xF7AA25, true, true);	
		tab.totalChildren(17);	
		tab.child(0, 47401, 11, 17);
		tab.child(1, 47402, 475, 23);
		tab.child(2, 47403, 475, 23);
		tab.child(3, 47405, 258, 26);
		tab.child(4, 47406, 16, 51);
		tab.child(5, 47407, 16, 51);
		tab.child(6, 47409, 136, 51);
		tab.child(7, 47410, 136, 51);
		tab.child(8, 47412, 256, 51);
		tab.child(9, 47413, 256, 51);
		tab.child(10, 47415, 376, 51);
		tab.child(11, 47416, 376, 51);		
		tab.child(12, 47418, 75, 56);
		tab.child(13, 47419, 195, 56);
		tab.child(14, 47420, 315, 56);
		tab.child(15, 47421, 435, 56);
		tab.child(16, 51550, 206, 97);
		RSInterface scrollInterface = addTabInterface(51550);
		scrollInterface.width = 175;
		scrollInterface.height = 201;
		scrollInterface.scrollMax = 500;
		setChildren(25, scrollInterface);
		int y = 0;
		for (int i = 0; i < 25; i++) {
			addHoverText(51551 + i, "Username", "View", daniel, 0, 0xcc8000, true, true, 100, 0xFFFFFF);
			setBounds(51551 + i, 0, y, i, scrollInterface);
			y += 20;
		}
	}
	
	public static void settings(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(28400);
		
		addSprite(28401, 360);
		addSprite(28402, 38);
		addText(28403, "Settings", daniel, 2, 0xF7AA25, true, true);
		addText(28404, "Customize your game play!", daniel, 0, 0xF7AA25, true, true);		
		addHoverButton(28405, 446, 130, 24, "Confirm Selection", -1, 28406, 1);
		addHoveredButton(28406, 447, 130, 24, 28407);		
		addHoverButton(28408, 351, 15, 15, "Reset to default", -1, 28409, 1);
		addHoveredButton(28409, 352, 15, 15, 28410);	
		
		tab.totalChildren(10);	
		tab.child(0, 28401, -4, 34);
		tab.child(1, 28402, -0, 34);
		tab.child(2, 28402, -0, 229);
		tab.child(3, 28403, 95, 4);
		tab.child(4, 28404, 95, 20);	
		tab.child(5, 28415, 5, 36);
		tab.child(6, 28405, -10, 233);
		tab.child(7, 28406, -10, 233);	
		tab.child(8, 28408, 160, 237);
		tab.child(9, 28409, 160, 237);
		
		int amount = SettingHandler.strings.length;
		
		RSInterface scrollInterface = addTabInterface(28415);
		scrollInterface.width = 170;
		scrollInterface.height = 192;
		scrollInterface.scrollMax = 650;
		setChildren(amount * 2, scrollInterface);
		
		int y = 0;
		for (int i = 0; i < amount; i++) {		
			addButton(28420 + i, 473, "Toggle " + SettingHandler.strings[i]);
			setBounds(28420 + i, 35, y + 5, i, scrollInterface);
			addText(28450 + i, "", daniel, 0, 0xF7AA25, false, true);
			setBounds(28450 + i, 45, y + 10, i + amount, scrollInterface);	
			y += 30;
		}
	}
	
	public static void staffTab(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(49700);			
		addSprite(49701, 360);
		addSprite(49702, 38);
		addText(49703, "Staff Tab", daniel, 2, 0xF7AA25, true, true);
		addText(49704, "You have access to all commands!", daniel, 0, 0xF7AA25, true, true);
		addText(49705, "</col>Rank: @red@<img=2> Owner", daniel, 0, 0xF7AA25, true, true);
		addHoverText(49706, "> Back to Quest Tab <", "Go back", daniel, 0, 0xF7AA25, true, true, 60);
		
		tab.totalChildren(8);	
		tab.child(0, 49701, -4, 34);
		tab.child(1, 49702, -0, 34);
		tab.child(2, 49702, -0, 229);
		tab.child(3, 49703, 93, 4);
		tab.child(4, 49704, 93, 235);
		tab.child(5, 49710, 5, 36);
		tab.child(6, 49705, 93, 20);
		tab.child(7, 49706, 61, 247);
		
		String[] titles = { 
			"@mbl@Check bank user", "@mbl@Kick user", "@mbl@Mute user", "@mbl@Unmute user", "@mbl@Ban user",
			"@mbl@Unban user", "@mbl@Jail user", "@mbl@Unjail user", "@mbl@Move home user", "@mye@Copy user", 
			"@mye@Freeze user", "@mbl@Get info user", "@mye@Demote user", "@mye@Give mod user", "@mre@Kill user",
			"@mbl@Tele to user", "@mbl@Tele to me user", "@mre@Boo user", "@mre@Random NPC user", "@mye@Refresh"
		};
		
		int amount = titles.length;
		
		RSInterface scrollInterface = addTabInterface(49710);
		scrollInterface.width = 170;
		scrollInterface.height = 192;
		scrollInterface.scrollMax = 665;
		setChildren(amount, scrollInterface);
		int y = 0;
		for (int i = 0; i < amount; i++) {
			addInputField(49700, 49720 + i, 15, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 136, 25, false, false, titles[i]);
			setBounds(49720 + i, 15, y + 5, i, scrollInterface);
			y += 35;
		}
		
	}
	
	/**
	 * Profile Tab
	 * @param daniel
	 */
	public static void profileTab(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(51500);			
		addSprite(51501, 360);
		addSprite(51502, 38);
		addText(51503, "Player Profiler", daniel, 2, 0xF7AA25, true, true);
		addHoverButton(51504, 44, 45, 45, "Search", 51504, 51505, 1);
		addHoveredButton(51505, 45, 45, 45, 51506);	
		addConfigButton(51507, 51500, 289, 290, 14, 15, "Select", 1, 5, 1032);
		addConfigButton(51508, 51500, 289, 290, 14, 15, "Select", 2, 5, 1032);
		addHoverText(51509, "Enable Privacy", "Enable", daniel, 0, 0xF7AA25, false, true, 60);
		addHoverText(51510, "Disable Privacy", "Disable", daniel, 0, 0xF7AA25, false, true, 60);	
		addHoverButton(51511, 36, 150, 35, "View my profile", 0, 51512, 1);
		addHoveredButton(51512, 37, 150, 35, 51513);
		addText(51514, "My Profile", daniel, 2, 0xF7AA25, true, true);	
		addHoverButton(51515, 36, 150, 35, "View profile leaderboards", 0, 51516, 1);
		addHoveredButton(51516, 37, 150, 35, 51517);
		addText(51518, "Leaderboards", daniel, 2, 0xF7AA25, true, true);	
		addHoverButton(51519, 362, 50, 20, "Settings", 0, 51520, 1);
		addHoveredButton(51520, 361, 50, 20, 51521);	
		tab.totalChildren(18);	
		tab.child(0, 51501, -4, 34);
		tab.child(1, 51502, -0, 34);
		tab.child(2, 51502, -0, 229);
		tab.child(3, 51503, 92, 9);
		tab.child(4, 51504, 0, 37);
		tab.child(5, 51505, 0, 37);
		tab.child(6, 51507, 50, 37);
		tab.child(7, 51508, 50, 56);
		tab.child(8, 51509, 70, 39);
		tab.child(9, 51510, 70, 58);
		tab.child(10, 51511, 20, 95);
		tab.child(11, 51512, 20, 95);
		tab.child(12, 51514, 92, 103);
		tab.child(13, 51515, 20, 155);
		tab.child(14, 51516, 20, 155);
		tab.child(15, 51518, 92, 163);
		tab.child(16, 51519, 1000, 1000);
		tab.child(17, 51520, 1000, 1000);
	}
	
	/**
	 * My Profile
	 * @param daniel
	 */
	public static void myProfile(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(51600);	
		addSprite(51601, 359);
		addText(51602, "My Profile", daniel, 2, 0xff981f, true, true);
		addHoverButton(51603, 17, 21, 21, "Close", 250, 51604, 3);
		addHoveredButton(51604, 18, 21, 21, 51605);
		addChar(51606, 700);
		addText(51607, "</col>Name: @gre@Daniel", daniel, 1, 0xff981f, true, true);
		addText(51608, "</col>Rank: @cr2@@gre@  Owner", daniel, 1, 0xff981f, true, true);
		addText(51609, "</col>Level: @gre@126", daniel, 1, 0xff981f, true, true);
		for (int i = 0; i < 21; i++) {
			addSprite(51610 + i, 324 + i);
		}
		tab.totalChildren(51);
		tab.child(8, 51680, 303, 49);
		RSInterface scrollInterface = addTabInterface(51680);
		scrollInterface.width = 170;
		scrollInterface.height = 267;
		scrollInterface.scrollMax = 450;
		setChildren(35, scrollInterface);
		int y = 0;
		for (int i = 0; i < 35; i++) {
			addHoverText(51681 + i, "" , "", daniel, 0, 0xff981f, true, true, 160, 0xff981f);
			setBounds(51681 + i, 0, y, i, scrollInterface);
			y += 20;
		}
		tab.child(0, 51601, 10, 5);
		tab.child(1, 51602, 255, 13);
		tab.child(2, 51603, 478, 11);
		tab.child(3, 51604, 478, 11);
		tab.child(4, 51606, 35, 210);
		tab.child(5, 51607, 105, 55);
		tab.child(6, 51608, 105, 70);
		tab.child(7, 51609, 105, 85);
		for (int i = 0; i < 20; i++) {
			tab.child(9 + i, 51610 + i, 205 + (i / 10) * 36, 50 + (i % 10) * 25);
			addTooltipBox(51632 + i, Skills.SKILL_NAMES[i].substring(0, 1).toUpperCase() + Skills.SKILL_NAMES[i].substring(1) + " level: 1/1\\nPrestige level: 1");
			interfaceCache[51632 + i].width = 25;
			interfaceCache[51632 + i].height = 25;
			tab.child(30 + i, 51632 + i, 205 + (i / 10) * 36, 50 + (i % 10) * 25);
		}
		tab.child(29, 51610 + 20, 222, 290);
		addTooltipBox(51632 + 20, "Runecrafting level: 1/1\\nPrestige level: 1");
		interfaceCache[51632 + 20].width = 25;
		interfaceCache[51632 + 20].height = 25;
		tab.child(50, 51632 + 20, 222, 290);
	}
	
	/**
	 * Player Profiler
	 * @param daniel
	 */
	public static void playerProfiler(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(51800);	
		addSprite(51801, 359);
		addText(51802, "Player Profiler", daniel, 2, 0xff981f, true, true);
		addHoverButton(51803, 17, 21, 21, "Close", 250, 51804, 3);
		addHoveredButton(51804, 18, 21, 21, 51805);
		addOtherChar(51806, 700);
		addText(51807, "</col>Name: @gre@Daniel", daniel, 1, 0xff981f, true, true);
		addText(51808, "</col>Rank: @cr2@@gre@  Owner", daniel, 1, 0xff981f, true, true);
		addText(51809, "</col>Level: @gre@126", daniel, 1, 0xff981f, true, true);
		for (int i = 0; i < 21; i++) {
			addSprite(51810 + i, 324 + i);
		}
		
		addHoverButton(51990, 363, 30, 30, "Like", 0, 51991, 1);
		addHoveredButton(51991, 364, 30, 30, 51992);
		
		addHoverButton(51993, 365, 30, 30, "Dislike", 0, 51994, 1);
		addHoveredButton(51994, 366, 30, 30, 51995);
		
		tab.totalChildren(55);
		tab.child(8, 51880, 303, 49);
		RSInterface scrollInterface = addTabInterface(51880);
		scrollInterface.width = 170;
		scrollInterface.height = 267;
		scrollInterface.scrollMax = 450;
		setChildren(35, scrollInterface);
		int y = 0;
		for (int i = 0; i < 35; i++) {
			addHoverText(51881 + i, "" , "", daniel, 0, 0xff981f, true, true, 160, 0xff981f);
			setBounds(51881 + i, 0, y, i, scrollInterface);
			y += 20;
		}
		tab.child(0, 51801, 10, 5);
		tab.child(1, 51802, 255, 13);
		tab.child(2, 51803, 478, 11);
		tab.child(3, 51804, 478, 11);
		tab.child(4, 51806, 35, 210);
		tab.child(5, 51807, 105, 55);
		tab.child(6, 51808, 105, 70);
		tab.child(7, 51809, 105, 85);
		for (int i = 0; i < 20; i++) {
			tab.child(9 + i, 51810 + i, 205 + (i / 10) * 36, 50 + (i % 10) * 25);
			addTooltipBox(51832 + i, Skills.SKILL_NAMES[i].substring(0, 1).toUpperCase() + Skills.SKILL_NAMES[i].substring(1) + " level: 1/1\\nPrestige level: 1");
			interfaceCache[51832 + i].width = 25;
			interfaceCache[51832 + i].height = 25;
			tab.child(30 + i, 51832 + i, 205 + (i / 10) * 36, 50 + (i % 10) * 25);
		}
		tab.child(29, 51810 + 20, 222, 290);
		addTooltipBox(51832 + 20, "Runecrafting level: 1/1\\nPrestige level: 1");
		interfaceCache[51832 + 20].width = 25;
		interfaceCache[51832 + 20].height = 25;
		tab.child(50, 51832 + 20, 222, 290);
		tab.child(51, 51990, 185, 10);
		tab.child(52, 51991, 185, 10);
		tab.child(53, 51993, 307, 10);
		tab.child(54, 51994, 307, 10);
	}
	
	/**
	 * Pest control boat
	 * @param tda
	 */
	public static void pestControlBoat(TextDrawingArea[] tda) {
		RSInterface RSinterface = addInterface(21119);
		addText(21120, "", 0x999999, false, true, 52, tda, 1);
		addText(21121, "", 0x33cc00, false, true, 52, tda, 1);
		addText(21122, "", 0xFFcc33, false, true, 52, tda, 1);
		addText(21123, "", 0x33ccff, false, true, 52, tda, 1);
		int last = 4;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];
		setBounds(21120, 15, 12, 0, RSinterface);
		setBounds(21121, 15, 30, 1, RSinterface);
		setBounds(21122, 15, 48, 2, RSinterface);
		setBounds(21123, 15, 66, 3, RSinterface);
	}

	/**
	 * Pest control game
	 * @param tda
	 */
	public static void pestControlGame(TextDrawingArea[] tda) {
		RSInterface RSinterface = addInterface(21100);
		addSprite(21101, 353);
		addSprite(21102, 354);
		addSprite(21103, 355);
		addSprite(21104, 356);
		addSprite(21105, 357);
		addSprite(21106, 358);
		addText(21107, "", 0xCC00CC, false, true, 52, tda, 1);
		addText(21108, "", 0x0000FF, false, true, 52, tda, 1);
		addText(21109, "", 0xFFFF44, false, true, 52, tda, 1);
		addText(21110, "", 0xCC0000, false, true, 52, tda, 1);
		addText(21111, "250", 0x99FF33, false, true, 52, tda, 1);
		addText(21112, "250", 0x99FF33, false, true, 52, tda, 1);
		addText(21113, "250", 0x99FF33, false, true, 52, tda, 1); 
		addText(21114, "250", 0x99FF33, false, true, 52, tda, 1); 
		addText(21115, "200", 0x99FF33, false, true, 52, tda, 1); 
		addText(21116, "0", 0x99FF33, false, true, 52, tda, 1); 
		addText(21117, "Time Remaining:", 0xFFFFFF, false, true, 52, tda, 0);
		addText(21118, "", 0xFFFFFF, false, true, 52, tda, 0);
		int last = 18;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];
		setBounds(21101, 361, 26, 0, RSinterface);
		setBounds(21102, 396, 26, 1, RSinterface);
		setBounds(21103, 436, 26, 2, RSinterface);
		setBounds(21104, 474, 26, 3, RSinterface);
		setBounds(21105, 3, 21, 4, RSinterface);
		setBounds(21106, 3, 50, 5, RSinterface);
		setBounds(21107, 371, 60, 6, RSinterface);
		setBounds(21108, 409, 60, 7, RSinterface);
		setBounds(21109, 443, 60, 8, RSinterface);
		setBounds(21110, 479, 60, 9, RSinterface);
		setBounds(21111, 362, 10, 10, RSinterface);
		setBounds(21112, 398, 10, 11, RSinterface);
		setBounds(21113, 436, 10, 12, RSinterface);
		setBounds(21114, 475, 10, 13, RSinterface);
		setBounds(21115, 32, 32, 14, RSinterface);
		setBounds(21116, 32, 62, 15, RSinterface);
		setBounds(21117, 8, 88, 16, RSinterface);
		setBounds(21118, 87, 88, 17, RSinterface);
	}
	
	/**
	 * Warrior Guild
	 * @param daniel
	 */
	public static void warriorGuild(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(51200);	
		addText(51202, "Warrior's Guild", daniel, 2, 0xff981f, true, true);
		itemDisplay(51203, 30, 30, 4, 5);
		addText(51204, "Dropping:", daniel, 0, 0xff981f, true, true);
		addText(51205, "Tokens Used:", daniel, 0, 0xff981f, true, true);
		addText(51206, "Cyclops Killed:", daniel, 0, 0xff981f, true, true);	
		tab.totalChildren(5);
		tab.child(0, 51202, 460, 215);
		tab.child(1, 51203, 450, 260);
		tab.child(2, 51204, 460, 235);
		tab.child(3, 51205, 460, 300);
		tab.child(4, 51206, 460, 320);
	}
	
	/**
	 * Prestige
	 * @param daniel
	 */
	public static void prestige(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(51000);
		addSprite(51001, 301);
		addChar(51002, 560);
		addHoverButton(51003, 17, 21, 21, "Close", 250, 51004, 3);
		addHoveredButton(51004, 18, 21, 21, 51005);
		addText(51006, "Prestige Panel", daniel, 2, 0xff981f, false, true);
		addText(51007, "Daniel", daniel, 1, 0xff981f, true, true);
		addText(51008, "Total Prestige(s): 0", daniel, 1, 0xff981f, true, true);
		addText(51009, "Prestige Pnt(s): 0", daniel, 1, 0xff981f, true, true);
		addText(51010, "Attack (0)", daniel, 0, 0xff981f, true, true);
		addText(51011, "Strength (0)", daniel, 0, 0xff981f, true, true);
		addText(51012, "Defence (0)", daniel, 0, 0xff981f, true, true);
		addText(51013, "Ranged (0)", daniel, 0, 0xff981f, true, true);
		addText(51014, "Prayer (0)", daniel, 0, 0xff981f, true, true);
		addText(51015, "Magic (0)", daniel, 0, 0xff981f, true, true);
		addText(51016, "RC (0)", daniel, 0, 0xff981f, true, true);
		addText(51017, "Hitpoints (0)", daniel, 0, 0xff981f, true, true);
		addText(51018, "Agility (0)", daniel, 0, 0xff981f, true, true);
		addText(51019, "Herblore (0)", daniel, 0, 0xff981f, true, true);
		addText(51020, "Thieving (0)", daniel, 0, 0xff981f, true, true);
		addText(51021, "Crafting (0)", daniel, 0, 0xff981f, true, true);
		addText(51022, "Fletching (0)", daniel, 0, 0xff981f, true, true);
		addText(51023, "Slayer (0)", daniel, 0, 0xff981f, true, true);	
		addText(51024, "Mining (0)", daniel, 0, 0xff981f, true, true);
		addText(51025, "Smithing (0)", daniel, 0, 0xff981f, true, true);
		addText(51026, "Fishing (0)", daniel, 0, 0xff981f, true, true);
		addText(51027, "Cooking (0)", daniel, 0, 0xff981f, true, true);
		addText(51028, "Firemaking (0)", daniel, 0, 0xff981f, true, true);
		addText(51029, "Woodcutting (0)", daniel, 0, 0xff981f, true, true);
		addText(51030, "Farming (0)", daniel, 0, 0xff981f, true, true);
		addText(51031, "Hunter (0)", daniel, 0, 0xff981f, true, true);		
		addHoverButton(51032, "", 0, 104, 30, "Prestige Attack", 0, 51033, 1);
		addHoveredButton(51033, 302, 104, 30, 51034);
		addHoverButton(51035,  "", 0, 104, 30, "Prestige Strength", 0, 51036, 1);
		addHoveredButton(51036, 303, 104, 30, 51037);
		addHoverButton(51038,  "", 0, 104, 30, "Prestige Defence", 0, 51039, 1);
		addHoveredButton(51039, 304, 104, 30, 51040);	
		addHoverButton(51041,  "", 0, 104, 30, "Prestige Ranged", 0, 51042, 1);
		addHoveredButton(51042, 305, 104, 30, 51043);		
		addHoverButton(51044,  "", 0, 104, 30, "Prestige Prayer", 0, 51045, 1);
		addHoveredButton(51045, 306, 104, 30, 51046);		
		addHoverButton(51047,  "", 0, 104, 30, "Prestige Magic", 0, 51048, 1);
		addHoveredButton(51048, 307, 104, 30, 51049);	
		addHoverButton(51050,  "", 0, 104, 30, "Prestige Runecrafting", 0, 51051, 1);
		addHoveredButton(51051, 308, 104, 30, 51052);	
		addHoverButton(51053,  "", 0, 104, 30, "Prestige Hitpoints", 0, 51054, 1);
		addHoveredButton(51054, 309, 104, 30, 51055);	
		addHoverButton(51056,  "", 0, 104, 30, "Prestige Agility", 0, 51057, 1);
		addHoveredButton(51057, 310, 104, 30, 51058);	
		addHoverButton(51059,  "", 0, 104, 30, "Prestige Herblore", 0, 51060, 1);
		addHoveredButton(51060, 311, 104, 30, 51061);		
		addHoverButton(51062,  "", 0, 104, 30, "Prestige Thieving", 0, 51063, 1);
		addHoveredButton(51063, 312, 104, 30, 51064);
		addHoverButton(51065,  "", 0, 104, 30, "Prestige Crafting", 0, 51066, 1);
		addHoveredButton(51066, 313, 104, 30, 51067);		
		addHoverButton(51068,  "", 0, 104, 30, "Prestige Fletching", 0, 51069, 1);
		addHoveredButton(51069, 314, 104, 30, 51070);		
		addHoverButton(51071,  "", 0, 104, 30, "Prestige Slayer", 0, 51072, 1);
		addHoveredButton(51072, 315, 104, 30, 51073);		
		addHoverButton(51074,  "", 0, 104, 30, "Prestige Mining", 0, 51075, 1);
		addHoveredButton(51075, 316, 104, 30, 51076);	
		addHoverButton(51077,  "", 0, 104, 30, "Prestige Smithing", 0, 51078, 1);
		addHoveredButton(51078, 317, 104, 30, 51079);	
		addHoverButton(51080,  "", 0, 104, 30, "Prestige Fishing", 0, 51081, 1);
		addHoveredButton(51081, 318, 104, 30, 51082);	
		addHoverButton(51083,  "", 0, 104, 30, "Prestige Cooking", 0, 51084, 1);
		addHoveredButton(51084, 319, 104, 30, 51085);
		addHoverButton(51086,  "", 0, 104, 30, "Prestige Firemaking", 0, 51087, 1);
		addHoveredButton(51087, 320, 104, 30, 51088);		
		addHoverButton(51089,  "", 0, 104, 30, "Prestige Woodcutting", 0, 51090, 1);
		addHoveredButton(51090, 321, 104, 30, 51091);
		addHoverButton(51092,  "", 0, 104, 30, "Prestige Farming", 0, 51093, 1);
		addHoveredButton(51093, 322, 104, 30, 51094);	
		addHoverButton(51095,  "", 0, 104, 30, "Prestige Slayer", 0, 51096, 1);
		addHoveredButton(51096, 323, 104, 30, 51097);	
		tab.totalChildren(74);
		tab.child(0, 51001, 10, 2);
		tab.child(1, 51002, 28, 160);
		tab.child(2, 51003, 475, 8);
		tab.child(3, 51004, 475, 8);
		tab.child(4, 51032, 181, 37);
		tab.child(5, 51033, 181, 37);	
		tab.child(6, 51035, 181, 74);
		tab.child(7, 51036, 181, 74);
		tab.child(8, 51038, 181, 111);
		tab.child(9, 51039, 181, 111);
		tab.child(10, 51041, 181, 148);
		tab.child(11, 51042, 181, 148);
		tab.child(12, 51044, 181, 185);
		tab.child(13, 51045, 181, 185);
		tab.child(14, 51047, 181, 222);
		tab.child(15, 51048, 181, 222);
		tab.child(16, 51050, 181, 259);
		tab.child(17, 51051, 181, 259);
		tab.child(18, 51053, 286, 37);
		tab.child(19, 51054, 286, 37);
		tab.child(20, 51056, 286, 74);
		tab.child(21, 51057, 286, 74);
		tab.child(22, 51059, 286, 111);
		tab.child(23, 51060, 286, 111);
		tab.child(24, 51062, 286, 148);
		tab.child(25, 51063, 286, 148);
		tab.child(26, 51065, 286, 185);
		tab.child(27, 51066, 286, 185);
		tab.child(28, 51068, 286, 222);
		tab.child(29, 51069, 286, 222);
		tab.child(30, 51071, 286, 259);
		tab.child(31, 51072, 286, 259);
		tab.child(32, 51074, 390, 37);
		tab.child(33, 51075, 390, 37);
		tab.child(34, 51077, 390, 74);
		tab.child(35, 51078, 390, 74);
		tab.child(36, 51080, 390, 111);
		tab.child(37, 51081, 390, 111);
		tab.child(38, 51083, 390, 148);
		tab.child(39, 51084, 390, 148);
		tab.child(40, 51086, 390, 185);
		tab.child(41, 51087, 390, 185);
		tab.child(42, 51089, 390, 222);
		tab.child(43, 51090, 390, 222);
		tab.child(44, 51092, 390, 259);
		tab.child(45, 51093, 390, 259);
		tab.child(46, 51095, 286, 293);
		tab.child(47, 51096, 286, 293);	
		tab.child(48, 51006, 220, 10);
		tab.child(49, 51007, 95, 275);
		tab.child(50, 51008, 95, 290);
		tab.child(51, 51009, 95, 305);	
		tab.child(52, 51010, 235, 46);
		tab.child(53, 51011, 235, 85);
		tab.child(54, 51012, 235, 121);
		tab.child(55, 51013, 235, 158);
		tab.child(56, 51014, 235, 195);
		tab.child(57, 51015, 235, 232);
		tab.child(58, 51016, 235, 269);	
		tab.child(59, 51017, 345, 46);
		tab.child(60, 51018, 345, 85);
		tab.child(61, 51019, 345, 121);
		tab.child(62, 51020, 345, 158);
		tab.child(63, 51021, 345, 195);
		tab.child(64, 51022, 345, 232);
		tab.child(65, 51023, 345, 269);	
		tab.child(66, 51024, 450, 46);
		tab.child(67, 51025, 450, 85);
		tab.child(68, 51026, 450, 121);
		tab.child(69, 51027, 450, 158);
		tab.child(70, 51028, 450, 195);
		tab.child(71, 51029, 450, 232);
		tab.child(72, 51030, 450, 269);	
		tab.child(73, 51031, 345, 303);
	}
	
	/**
	 * Friends tab
	 * 
	 * @param tda
	 */
	public static void friendsTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(5065);
		RSInterface list = interfaceCache[5066];
		addSprite(16126, 291);
		addSprite(16127, 292);
		addText(5067, "Friends List", tda, 1, 0xff9933, true, true);
		addText(5070, "   Vencillio", tda, 0, 0xff9933, false, true);
		addText(5071, "", tda, 0, 0xff9933, false, true);
		addHoverButton(5068, 293, 29, 29, "Add Name", 201, 5072, 1);
		addHoveredButton(5072, 294, 29, 29, 5073);
		addHoverButton(5069, 295, 29, 29, "Delete Name", 202, 5074, 1);
		addHoveredButton(5074, 296, 29, 29, 5075);
		addText(5079, "0 / 200", tda, 0, 0xff9933, false, true, 901, 0);
		tab.totalChildren(12);
		tab.child(0, 16127, 0, 40);
		tab.child(1, 5067, 92, 5);
		tab.child(2, 16126, 0, 40);
		tab.child(3, 5066, 0, 42);
		tab.child(4, 16126, 0, 231);
		tab.child(5, 5068, 5, 240);
		tab.child(6, 5072, 4, 240);
		tab.child(7, 5069, 25, 240);
		tab.child(8, 5074, 24, 240);
		tab.child(9, 5070, 64, 25);
		tab.child(10, 5071, 106, 237);
		tab.child(11, 5079, 145, 242);
		list.height = 189;
		list.width = 174;
		list.scrollMax = 200;
		for (int id = 5092, i = 0; id <= 5191 && i <= 99; id++, i++) {
			list.children[i] = id;
			list.childX[i] = 3;
			list.childY[i] = list.childY[i] - 7;
		}
		for (int id = 5192, i = 100; id <= 5291 && i <= 199; id++, i++) {
			list.children[i] = id;
			list.childX[i] = 131;
			list.childY[i] = list.childY[i] - 7;
		}
	}
	
	/**
	 * Ignore tab
	 * 
	 * @param tda
	 */
	public static void ignoreTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(5715);
		RSInterface list = interfaceCache[5716];
		addText(5717, "Ignore List", tda, 1, 0xff9933, true, true);
		addText(5720, "   Vencillio", tda, 0, 0xff9933, false, true);
		addText(5721, "", tda, 0, 0xff9933, false, true);
		addHoverButton(5718, 297, 29, 29, "Add Name", 501, 5722, 1);
		addHoveredButton(5722, 298, 29, 29, 5723);
		addHoverButton(5719, 299, 29, 29, "Delete Name", 502, 5724, 1);
		addHoveredButton(5724, 300, 29, 29, 5725);
		addText(5824, "0 / 100", tda, 0, 0xff9933, false, true, 902, 0);
		tab.totalChildren(12);
		tab.child(0, 5717, 92, 5);
		tab.child(1, 16127, 0, 40);
		tab.child(2, 16126, 0, 40);
		tab.child(3, 5716, 0, 42);
		tab.child(4, 16126, 0, 231);
		tab.child(5, 5718, 5, 240);
		tab.child(6, 5722, 4, 240);
		tab.child(7, 5719, 25, 240);
		tab.child(8, 5724, 24, 240);
		tab.child(9, 5720, 64, 25);
		tab.child(10, 5721, 108, 237);
		tab.child(11, 5824, 145, 242);
		list.height = 189;
		list.width = 174;
		list.scrollMax = 200;
		for (int id = 5742, i = 0; id <= 5841 && i <= 99; id++, i++) {
			list.children[i] = id;
			list.childX[i] = 3;
			list.childY[i] = list.childY[i] - 7;
		}
	}
	
	/**
	 * Exp Counter
	 * @param vencillio
	 */
	public static void expCounter(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(32800);
		addSprite(32801, 369);
		addHoverButton(32802, 17, 20, 20, "Close", 250, 32803, 3);
		addHoveredButton(32803, 18, 20, 20, 32804);
		addText(32805, "Exp Counter Settings", 0xff9933, true, true, -1, daniel, 2);
		addConfigButton(32806, 32800, 289, 290, 14, 15, "Select", 0, 5, 1030);
		addConfigButton(32807, 32800, 289, 290, 14, 15, "Select", 1, 5, 1030);
		addConfigButton(32808, 32800, 289, 290, 14, 15, "Select", 2, 5, 1030);
		addText(32809, "Normal", 0xff9933, false, true, -1, daniel, 1);
		addText(32810, "Sideways", 0xff9933, false, true, -1, daniel, 1);
		addText(32811, "Scrambled", 0xff9933, false, true, -1, daniel, 1);
		tab.totalChildren(10);
		tab.child(0, 32801, 105, 45);
		tab.child(1, 32802, 391, 52);
		tab.child(2, 32803, 391, 52);
		tab.child(3, 32805, 263, 54);
		tab.child(4, 32806, 165, 140);
		tab.child(5, 32807, 165, 165);
		tab.child(6, 32808, 165, 190);
		tab.child(7, 32809, 190, 140);
		tab.child(8, 32810, 190, 165);
		tab.child(9, 32811, 190, 190);
	}
	
	/**
	 * Chat Color
	 * @param daniel
	 */
	public static void chatColor(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(37500);
		addSprite(37501, 261);
		addHoverButton(37502, 17, 21, 21, "Close", 250, 37503, 3);
		addHoveredButton(37503, 18, 21, 21, 37504);
		addText(37505, "Private Chat Color Changer", 0xff9933, true, true, -1, daniel, 3);
		addText(37506, "Chosen color: ", 0xff9933, true, true, -1, daniel, 1);
		addHoverButton(37507, 262, 45, 45, "Search", 0, 37508, 1);
		addHoveredButton(37508, 263, 45, 45, 37509);
		addHoverButton(37510, 264, 30, 30, "Select white", 0, 37511, 1);
		addHoveredButton(37511, 265, 30, 30, 37512);
		addHoverButton(37513, 266, 30, 30, "Select black", 0, 37514, 1);
		addHoveredButton(37514, 267, 30, 30, 37515);
		addHoverButton(37516, 268, 30, 30, "Select grey", 0, 37517, 1);
		addHoveredButton(37517, 269, 30, 30, 37518);
		addHoverButton(37519, 270, 30, 30, "Select red", 0, 37520, 1);
		addHoveredButton(37520,271, 30, 30, 37521);
		addHoverButton(37522, 272, 30, 30, "Select orange", 0, 37523, 1);
		addHoveredButton(37523, 273, 30, 30, 37524);
		addHoverButton(37525, 274, 30, 30, "Select yellow", 0, 37526, 1);
		addHoveredButton(37526, 275, 30, 30, 37527);
		addHoverButton(37528, 276, 30, 30, "Select green", 0, 37529, 1);
		addHoveredButton(37529, 277, 30, 30, 37530);
		addHoverButton(37531, 278, 30, 30, "Select blue", 0, 37532, 1);
		addHoveredButton(37532, 279, 30, 30, 37533);
		addHoverButton(37534, 280, 30, 30, "Select purple", 0, 37535, 1);
		addHoveredButton(37535, 281, 30, 30, 37536);
		addHoverButton(37537, 282, 30, 30, "Select pink", 0, 37538, 1);
		addHoveredButton(37538, 283, 30, 30, 37539);
		addHoverButton(37540, 284, 30, 30, "Select cyan", 0, 37541, 1);
		addHoveredButton(37541, 285, 30, 30, 37542);
		addHoverButton(37543, 286, 30, 30, "Select turquoise", 0, 37544, 1);
		addHoveredButton(37544, 287, 30, 30, 37545);
		tab.totalChildren(31);
		tab.child(0, 37501, 100, 75);
		tab.child(1, 37502, 390, 82);
		tab.child(2, 37503, 390, 82);
		tab.child(3, 37505, 260, 83);
		tab.child(4, 37506, 260, 131);
		tab.child(5, 37507, 770, 127);
		tab.child(6, 37508, 770, 127);
		tab.child(7, 37510, 126, 171);
		tab.child(8, 37511, 126, 171);
		tab.child(9, 37513, 174, 171);
		tab.child(10, 37514, 174, 171);
		tab.child(11, 37516, 222, 171);
		tab.child(12, 37517, 222, 171);
		tab.child(13, 37519, 270, 171);
		tab.child(14, 37520, 270, 171);
		tab.child(15, 37522, 318, 171);
		tab.child(16, 37523, 318, 171);
		tab.child(17, 37525, 364, 171);
		tab.child(18, 37526, 364, 171);
		tab.child(19, 37528, 126, 219);
		tab.child(20, 37529, 126, 219);
		tab.child(21, 37531, 174, 219);
		tab.child(22, 37532, 174, 219);
		tab.child(23, 37534, 222, 219);
		tab.child(24, 37535, 222, 219);
		tab.child(25, 37537, 270, 219);
		tab.child(26, 37538, 270, 219);
		tab.child(27, 37540, 318, 219);
		tab.child(28, 37541, 318, 219);
		tab.child(29, 37543, 364, 219);
		tab.child(30, 37544, 364, 219);
	}

	
	public static void itemDetails(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(59750);
		addSprite(59751, 456);
		addText(59752, "Item Details:", 0xff9933, true, true, -1, vencillio, 2);
		addText(59753, "</col>Item: @gre@Armadyl Godsword", 0xff9933, true, true, -1, vencillio, 0);
		addText(59754, "</col>General Price: @gre@85M", 0xff9933, true, true, -1, vencillio, 0);
		addText(59755, "</col>Tradeable: @gre@True", 0xff9933, true, true, -1, vencillio, 0);
		addText(59756, "</col>Noted: @gre@False", 0xff9933, true, true, -1, vencillio, 0);
		itemDisplay(59757, 30, 10, 5, 5, "Complain");
		addHoverText(59758, "-> Back <-" , "Go back to drop table", vencillio, 0, 0xff9933, true, true, 300);
		tab.totalChildren(8);
		tab.child(0, 59751, 130, 100);
		tab.child(1, 59752, 295, 115);
		tab.child(2, 59753, 295, 140);
		tab.child(3, 59754, 295, 155);
		tab.child(4, 59755, 295, 170);
		tab.child(5, 59756, 295, 185);
		tab.child(6, 59757, 150, 143);
		tab.child(7, 59758, 145, 205);
	}
	
	/**
	 * Monster guide
	 * @param vencillio
	 */
	public static void dropTable(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(59800);
		addSprite(59801, 16);
		addHoverButton(59802, 454, 17, 17, "Close", 250, 59803, 3);
		addHoveredButton(59803, 455, 17, 17, 59804);
		addText(59805, "Monster Drop Guide", 0xff9933, true, true, -1, vencillio, 2);	
		addText(59806, "Name:", 0xff9933, true, false, -1, vencillio, 0);
		addText(59807, "Level:", 0xff9933, true, false, -1, vencillio, 0);
		addText(59818, "", 0xff9933, true, false, -1, vencillio, 0);	
		String[] table = { "Always", "Common", "Uncommon", "Rare", "Very Rare" };	
		for (int i = 0; i < table.length; i++) {
			addText(59808 + i, table[i], 0x000000, true, false, -1, vencillio, 0);			
		}	
		itemContainer(59813, 30, 8, 5, 50, false, "Details");
		addInputField(59800, 59814, 15, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 136, 25, false, false, "Search for item");		
		addInputField(59800, 59815, 15, 0x332E24, 0x4D4636, 0x383631, 0x474540, "", 136, 25, false, false, "Search for Npc ");

		tab.totalChildren(16);
		tab.child(0, 59801, 15, 2);
		tab.child(1, 59802, 475, 8);
		tab.child(2, 59803, 475, 8);
		tab.child(3, 59805, 265, 9);
		tab.child(4, 59806, 334, 43);
		tab.child(5, 59807, 334, 60);
		tab.child(6, 59808, 204, 91);
		tab.child(7, 59809, 262, 91);
		tab.child(8, 59810, 324, 91);
		tab.child(9, 59811, 385, 91);
		tab.child(10, 59812, 446, 91);
		tab.child(11, 59814, 24, 37);
		tab.child(12, 59817, 171, 119);
		tab.child(13, 59900, -230, 97);
		tab.child(14, 59818, 334, 63);
		tab.child(15, 59815, 24, 65);
		
		RSInterface scrollInterface = addTabInterface(59817);
		scrollInterface.width = 305;
		scrollInterface.height = 208;
		scrollInterface.scrollMax = 41 * 50;
		setChildren(2, scrollInterface);
		addSprite(59819, 458);
		setBounds(59819, 0, 0, 0, scrollInterface);
		setBounds(59813, 13, 4, 1, scrollInterface);
		
		scrollInterface = addTabInterface(59900);
		scrollInterface.width = 377;
		scrollInterface.height = 231;
		scrollInterface.scrollMax = 1000;
		setChildren(50, scrollInterface);
		int y = 0;
		for (int i = 0; i < 50; i++) {
			addHoverText(59901 + i, "" , "", vencillio, 0, 0xCF4F0A, false, true, 300);
			setBounds(59901 + i, 260, y + 4, i, scrollInterface);
			y += 20;
		}
	}
	
	/**
	 * Mystery Box
	 * @param daniel
	 */
	public static void mysteryBox(TextDrawingArea[] daniel) {
		RSInterface rsinterface = addTabInterface(17000);
		addSprite(17001, 257);
		lotteryItem(17002);
		mysteryWonItem(17007);
		addText(17003, "Mystery Box ", daniel, 2, 0xF7AA25, true, true);
		addHoverButton(17004, 258, 146, 37, "Bet credits", -1, 17005, 1);
		addHoveredButton(17005, 259, 146, 37, 9454);
		addText(17006, "</col>Credits: @gre@1,000", daniel, 0, 0xF7AA25, true, true);
		addText(17015, "", daniel, 0, 0xF7AA25, true, true);
		addHoverButton(47267, 21, 16, 16, "Close", -1, 47268, 1);
		addHoveredButton(47268, 22, 16, 16, 47269);
		addText(17008, "Mystery Box is a gambling minigame where you can bet 1 credit.", daniel, 0, 0xF7AA25, true, true);
		addText(17009, "Rewards can vary from items worth @gre@1,000</col> - @gre@100,000,000</col> coins!", daniel, 0, 0xF7AA25, true, true);
		addText(17010, "Good luck, may the odds be ever in your favor.", daniel, 0, 0xF7AA25, true, true);
		addHoverText(17011, "Purchase more credits", "Purchase Vencillio credits", daniel, 0, 0xF7AA25, false, true, 168);
		
		addText(17012, "Play", daniel, 2, 0xF7AA25, true, true);
		rsinterface.totalChildren(15);
		
		rsinterface.child(0, 17001, 14, 5);
		rsinterface.child(1, 17002, 240, 190);
		rsinterface.child(2, 17003, 260, 10);
		rsinterface.child(3, 17004, 44, 123);
		rsinterface.child(4, 17005, 44, 123);
		rsinterface.child(5, 47267, 474, 8);
		rsinterface.child(6, 47268, 474, 8);
		rsinterface.child(7, 17008, 260, 55);
		rsinterface.child(8, 17009, 260, 70);
		rsinterface.child(9, 17010, 260, 85);
		rsinterface.child(10, 17011, 352, 138);
		rsinterface.child(11, 17006, 407, 128);
		rsinterface.child(12, 17007, 397, 127);
		rsinterface.child(13, 17015, 255, 300);
		rsinterface.child(14, 17012, 120, 130);
	}
	
	/**
	 * Genie
	 * @param daniel
	 */
	public static void genie(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(59500);
		addSprite(59501, 249);
		addText(59502, "Reset Statistics", 0xff9933, true, true, -1, daniel, 2);
		addHoverButton(59503, 21, 21, 21, "Close", 250, 59504, 3);
		addHoveredButton(59504, 22, 21, 21, 59505);
		addHoverButton(59506, "", 0, 130, 30, "Reset Attack", 0, 59507, 1);
		addHoveredButton(59507, 250, 130, 30, 59508);
		addHoverButton(59509, "", 0, 130, 30, "Reset Strength", 0, 59510, 1);
		addHoveredButton(59510, 251, 130, 30, 59511);
		addHoverButton(59512, "", 0, 130, 30, "Reset Defence", 0, 59513, 1);
		addHoveredButton(59513, 252, 130, 30, 59514);
		addHoverButton(59515, "", 0, 130, 30, "Reset Range", 0, 59516, 1);
		addHoveredButton(59516, 253, 130, 30, 59517);
		addHoverButton(59518, "", 0, 130, 30, "Reset Magic", 0, 59519, 1);
		addHoveredButton(59519, 254, 130, 30, 59520);
		addHoverButton(59521, "", 0, 130, 30, "Reset Prayer", 0, 59522, 1);
		addHoveredButton(59522, 255, 130, 30, 59523);
		addHoverButton(59524, "", 0, 130, 30, "Reset Hitpoints", 0, 59525, 1);
		addHoveredButton(59525, 256, 130, 30, 59526);
		addText(59527, "Attack", 0xff9933, true, true, -1, daniel, 0);
		addText(59528, "Strength ", 0xff9933, true, true, -1, daniel, 0);
		addText(59529, "Defence", 0xff9933, true, true, -1, daniel, 0);
		addText(59530, "Range", 0xff9933, true, true, -1, daniel, 0);
		addText(59531, "Magic", 0xff9933, true, true, -1, daniel, 0);
		addText(59532, "Prayer", 0xff9933, true, true, -1, daniel, 0);
		addText(59533, "Hitpoints", 0xff9933, true, true, -1, daniel, 0);
		addText(59534, "Please select the statistic you would like to reset.", 0xff9953, true, true, -1, daniel, 0);
		tab.totalChildren(26);
		tab.child(0, 59501, 90, 90);
		tab.child(1, 59502, 250, 97);
		tab.child(2, 59503, 398, 96);
		tab.child(3, 59504, 398, 96);
		tab.child(4, 59506, 101, 145);
		tab.child(5, 59507, 101, 145);
		tab.child(6, 59509, 209, 145);
		tab.child(7, 59510, 209, 145);
		tab.child(8, 59512, 317, 145);
		tab.child(9, 59513, 317, 145);
		tab.child(10, 59515, 101, 179);
		tab.child(11, 59516, 101, 179);
		tab.child(12, 59518, 209, 179);
		tab.child(13, 59519, 209, 179);
		tab.child(14, 59521, 317, 179);
		tab.child(15, 59522, 317, 179);
		tab.child(16, 59524, 209, 213);
		tab.child(17, 59525, 209, 213);
		tab.child(18, 59527, 155, 155);
		tab.child(19, 59528, 265, 155);
		tab.child(20, 59529, 375, 155);
		tab.child(21, 59530, 155, 189);
		tab.child(22, 59531, 265, 189);
		tab.child(23, 59532, 375, 189);
		tab.child(24, 59533, 265, 223);
		tab.child(25, 59534, 255, 122);
	}
	
	/**
	 * Barrows
	 * @param daniel
	 */
	public static void barrows(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(59000);
		addTransparentSprite(59001, 248);
		addText(59002, "Barrows Brothers", 0xff9933, true, true, -1, daniel, 2);
		addText(59003, "Ahrim the Blighted", 0xff9933, true, true, -1, daniel, 1);
		addText(59004, "Dharok the Wretched", 0xff9933, true, true, -1, daniel, 1);
		addText(59005, "Guthan the Infested", 0xff9933, true, true, -1, daniel, 1);
		addText(59006, "Karil the Tainted", 0xff9933, true, true, -1, daniel, 1);
		addText(59007, "Torag the Corrupted", 0xff9933, true, true, -1, daniel, 1);
		addText(59008, "Verac the Defiled", 0xff9933, true, true, -1, daniel, 1);
		tab.totalChildren(8);
		tab.child(0, 59001, 357, 200);
		tab.child(1, 59002, 432, 210);
		tab.child(2, 59003, 432, 235);
		tab.child(3, 59004, 432, 250);
		tab.child(4, 59005, 432, 265);
		tab.child(5, 59006, 432, 280);
		tab.child(6, 59007, 432, 295);
		tab.child(7, 59008, 432, 310);
	}

	/**
	 * Price Checker
	 * @param daniel
	 */
	public static void priceChecker(TextDrawingArea[] daniel) {
		RSInterface rsi = addInterface(48500);
		addSprite(48501, 243);
		addPriceChecker(48542);
		addHoverButton(48502, 17, 16, 21, "Close", -1, 48503, 3);
		addHoveredButton(48503, 18, 21, 21, 48504);
		addHoverButton(48505, 244, 36, 36, "Add all", -1, 48506, 1);
		addHoveredButton(48506, 245, 36, 36, 48507);
		addHoverButton(48578, 246, 36, 36, "Withdraw all", -1, 48579, 1);
		addHoveredButton(48579, 247, 36, 36, 48580);
		addHoverButton(48508, 44, 36, 36, "Search for item", -1, 48509, 1);
		addHoveredButton(48509, 45, 36, 36, 48510);
		addText(48511, "Price Checker", daniel, 2, 0xFF981F, true, true);
		addText(48512, "Total price:", daniel, 1, 0xFF981F, true, true);
		addText(48513, "115,424,152", daniel, 0, 0xffffff, true, true);
		addText(48550, "", daniel, 0, 0xffffff, true, true);
		addText(48551, "", daniel, 0, 0xffffff, true, true);
		addText(48552, "", daniel, 0, 0xffffff, true, true);
		addText(48553, "", daniel, 0, 0xffffff, true, true);
		addText(48554, "", daniel, 0, 0xffffff, true, true);
		addText(48555, "", daniel, 0, 0xffffff, true, true);
		addText(48556, "", daniel, 0, 0xffffff, true, true);
		addText(48557, "", daniel, 0, 0xffffff, true, true);
		addText(48558, "", daniel, 0, 0xffffff, true, true);
		addText(48559, "", daniel, 0, 0xffffff, true, true);
		addText(48560, "", daniel, 0, 0xffffff, true, true);
		addText(48561, "", daniel, 0, 0xffffff, true, true);
		addText(48562, "", daniel, 0, 0xffffff, true, true);
		addText(48563, "", daniel, 0, 0xffffff, true, true);
		addText(48564, "", daniel, 0, 0xffffff, true, true);
		addText(48565, "", daniel, 0, 0xffffff, true, true);
		addText(48566, "", daniel, 0, 0xffffff, true, true);
		addText(48567, "", daniel, 0, 0xffffff, true, true);
		addText(48568, "", daniel, 0, 0xffffff, true, true);
		addText(48569, "", daniel, 0, 0xffffff, true, true);
		addText(48570, "", daniel, 0, 0xffffff, true, true);
		addText(48571, "", daniel, 0, 0xffffff, true, true);
		addText(48572, "", daniel, 0, 0xffffff, true, true);
		addText(48573, "", daniel, 0, 0xffffff, true, true);
		addText(48574, "", daniel, 0, 0xffffff, true, true);
		addText(48575, "", daniel, 0, 0xffffff, true, true);
		addText(48576, "", daniel, 0, 0xffffff, true, true);
		addText(48577, "", daniel, 0, 0xffffff, true, true);
		rsi.totalChildren(41);
		rsi.child(0, 48501, 0, 0);
		rsi.child(1, 48502, 485, 7);
		rsi.child(2, 48503, 485, 7);
		rsi.child(3, 48505, 434, 290);
		rsi.child(4, 48506, 434, 290);
		rsi.child(5, 48508, 8, 290);
		rsi.child(6, 48509, 8, 290);
		rsi.child(7, 48511, 255, 10);
		rsi.child(8, 48512, 255, 290);
		rsi.child(9, 48513, 255, 310);
		rsi.child(10, 48542, 40, 45);
		rsi.child(11, 48550, 57, 80);
		rsi.child(12, 48551, 123, 80);
		rsi.child(13, 48552, 189, 80);
		rsi.child(14, 48553, 256, 80);
		rsi.child(15, 48554, 323, 80);
		rsi.child(16, 48555, 390, 80);
		rsi.child(17, 48556, 458, 80);
		rsi.child(18, 48557, 57, 142);
		rsi.child(19, 48558, 123, 142);
		rsi.child(20, 48559, 189, 142);
		rsi.child(21, 48560, 256, 142);
		rsi.child(22, 48561, 323, 142);
		rsi.child(23, 48562, 390, 142);
		rsi.child(24, 48563, 458, 142);
		rsi.child(25, 48564, 57, 204);
		rsi.child(26, 48565, 123, 204);
		rsi.child(27, 48566, 189, 204);
		rsi.child(28, 48567, 256, 204);
		rsi.child(29, 48568, 323, 204);
		rsi.child(30, 48569, 390, 204);
		rsi.child(31, 48570, 458, 204);
		rsi.child(32, 48571, 57, 266);
		rsi.child(33, 48572, 123, 266);
		rsi.child(34, 48573, 189, 266);
		rsi.child(35, 48574, 256, 266);
		rsi.child(36, 48575, 323, 266);
		rsi.child(37, 48576, 390, 266);
		rsi.child(38, 48577, 458, 266);
		rsi.child(39, 48578, 470, 290);
		rsi.child(40, 48579, 470, 290);
	}

	/**
	 * Items on death
	 * 
	 * @param daniel
	 */
	public static void itemsOnDeath(TextDrawingArea[] daniel) {
		RSInterface rsinterface = addInterface(17100);
		addSprite(17101, 242);
		addHoverButton(17102, 21, 15, 15, "Close", 250, 10601, 3);
		addHoveredButton(10601, 22, 15, 15, 10602);		
		addText(17103, "Items Kept on Death", daniel, 2, 0xff981f);
		addText(17104, "Items you keep on death if not skulled:", daniel, 0, 0xff981f);
		addText(17105, "Items you loose on death if not skulled:", daniel, 0, 0xff981f);
		addText(17106, "Information:", daniel, 1, 0xff981f);
		addText(17107, "Max Items Kept on death:", daniel, 1, 0xff981f);
		addText(17108, "~ 3 ~", daniel, 1, 0xffcc33);
		rsinterface.scrollMax = 0;
		rsinterface.children = new int[12];
		rsinterface.childX = new int[12];
		rsinterface.childY = new int[12];
		rsinterface.children[0] = 17101;
		rsinterface.childX[0] = 7;
		rsinterface.childY[0] = 8;
		rsinterface.children[1] = 17102;
		rsinterface.childX[1] = 480;
		rsinterface.childY[1] = 17;
		rsinterface.children[2] = 17103;
		rsinterface.childX[2] = 185;
		rsinterface.childY[2] = 18;
		rsinterface.children[3] = 17104;
		rsinterface.childX[3] = 22;
		rsinterface.childY[3] = 50;
		rsinterface.children[4] = 17105;
		rsinterface.childX[4] = 22;
		rsinterface.childY[4] = 110;
		rsinterface.children[5] = 17106;
		rsinterface.childX[5] = 347;
		rsinterface.childY[5] = 47;
		rsinterface.children[6] = 17107;
		rsinterface.childX[6] = 349;
		rsinterface.childY[6] = 270;
		rsinterface.children[7] = 17108;
		rsinterface.childX[7] = 398;
		rsinterface.childY[7] = 298;
		rsinterface.children[8] = 17115;
		rsinterface.childX[8] = 348;
		rsinterface.childY[8] = 64;
		rsinterface.children[9] = 10494;
		rsinterface.childX[9] = 26;
		rsinterface.childY[9] = 74;
		rsinterface.children[10] = 10600;
		rsinterface.childX[10] = 26;
		rsinterface.childY[10] = 133;
		rsinterface.children[11] = 10601;
		rsinterface.childX[11] = 480;
		rsinterface.childY[11] = 17;
		rsinterface = interfaceCache[10494];
		rsinterface.invSpritePadX = 6;
		rsinterface.invSpritePadY = 5;
		rsinterface = interfaceCache[10600];
		rsinterface.invSpritePadX = 6;
		rsinterface.invSpritePadY = 5;
	}

	/**
	 * Credits Tab
	 * 
	 * @param daniel
	 */
	public static void creditsTab(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(52500);
		addSprite(52501, 38);
		addText(52502, "Vencillio Credits", 0xff9933, true, true, -1, daniel, 2);
		addSprite(52503, 55);
		addText(52504, "Current credits:", 0xff9933, true, true, -1, daniel, 0);
		addHoverText(52505, "Purchase more credits", "Purchase Vencillio credits", daniel, 0, 0xF7AA25, true, true, 168);
		addSprite(52506, 56);
		tab.totalChildren(9);
		tab.child(0, 52501, 0, 25);
		tab.child(1, 52502, 95, 5);
		tab.child(2, 52503, -5, 28);
		tab.child(3, 52504, 95, 35);
		tab.child(4, 52505, 12, 50);
		tab.child(5, 52506, 2, 79);
		tab.child(6, 52501, 0, 70);
		tab.child(7, 52501, 0, 254);
		tab.child(8, 52530, 5, 79);
		RSInterface scrollInterface = addTabInterface(52530);
		scrollInterface.parentID = 52500;
		scrollInterface.scrollPosition = 0;
		scrollInterface.atActionType = 0;
		scrollInterface.contentType = 0;
		scrollInterface.width = 169;
		scrollInterface.height = 167;
		scrollInterface.scrollMax = 150;
		int x = 7, y = 9;
		scrollInterface.totalChildren(30);
		for (int i = 0; i < 30; i++) {
			addHoverText(52531 + i, "", "Spend credit", daniel, 0, 0xE3A724, true, true, 168, 0xFFFFFF);
			scrollInterface.child(i, 52531 + i, x, y);
			y += 18;
		}
	}

	/**
	 * Shop Exchange
	 * 
	 * @param daniel
	 */
	public static void shopExchange(TextDrawingArea[] daniel) {
		RSInterface tab = addInterface(53500);
		addSprite(53501, 43);
		addHoverButton(53502, 17, 21, 21, "Close", 250, 53503, 3);
		addHoveredButton(53503, 18, 21, 21, 53504);
		addText(53505, "Shops Exchange", 0xff9933, true, true, -1, daniel, 2);
		addHoverButton(53506, 44, 45, 45, "Search", 0, 53507, 1);
		addHoveredButton(53507, 45, 45, 45, 53508);
		addText(53509, "Shop Owner:", 0xff9933, true, true, -1, daniel, 2);
		addText(53510, "Shop Description:", 0xff9933, true, true, -1, daniel, 2);
		tab.totalChildren(9);
		tab.child(0, 53501, 11, 2);
		tab.child(1, 53502, 475, 8);
		tab.child(2, 53503, 475, 8);
		tab.child(3, 53505, 255, 12);
		tab.child(4, 53506, 456, 36);
		tab.child(5, 53507, 456, 36);
		tab.child(6, 53509, 109, 65);
		tab.child(7, 53510, 330, 65);
		tab.child(8, 53515, 45, 84);
		RSInterface scrollInterface = addTabInterface(53515);
		scrollInterface.width = 412;
		scrollInterface.height = 225;
		scrollInterface.scrollMax = 3000;
		setChildren(400, scrollInterface);
		int y = 0;
		for (int i = 0; i < 200; i++) {
			addHoverText(53516 + i, "Name: " + i, "View", daniel, 0, 0xcc8000, false, true, 100, 0xFFFFFF);
			setBounds(53516 + i, 0, y, i, scrollInterface);
			y += 20;
		}
		y = 0;
		for (int i = 0; i < 200; i++) {
			addHoverText(53516 + 200 + i, "Motto: " + i, "View", daniel, 0, 0xcc8000, false, true, 100, 0xFFFFFF);
			setBounds(53516 + 200 + i, 150, y, i + 200, scrollInterface);
			y += 20;
		}
	}

	/**
	 * Achievement tab
	 * 
	 * @param daniel
	 */
	private static void achievementsTab(TextDrawingArea[] daniel) {
		RSInterface tab = addTabInterface(31000);
		addSprite(31001, 15);
		addText(31002, "Achievements", 0xff9040, false, true, 52, daniel, 2);
		setChildren(5, tab);
		setBounds(31001, 0, -3, 0, tab);
		setBounds(31002, 45, 5, 1, tab);
		setBounds(31003, -6, 25, 2, tab);
		addHoverButton(31004, 13, 25, 25, "Back to Quest tab", -1, 31005, 1);
		addHoveredButton(31005, 14, 25, 25, 31096);		
		RSInterface scrollInterface = addTabInterface(31003);
		scrollInterface.width = 179;
		scrollInterface.height = 236;
		scrollInterface.scrollMax = 5 + 15 * 80;
		setChildren(80, scrollInterface);
		int y = 5;
		for (int i = 0; i < 80; i++) {
			addHoverText(31006 + i, "", "View", daniel, 0, 0xff0000, false, true, 300);
			setBounds(31006 + i, 11, y, i, scrollInterface);
			if (i == 4) {
				y += 15;
			}
			y += 15;
		}
		tab.child(3, 31004, 160, 0);
		tab.child(4, 31005, 160, 0);
	}

	
	/**
	 * Shop interface
	 * 
	 * @param vencillio
	 */
	private static void shop(TextDrawingArea[] vencillio) {
		RSInterface rsinterface = addInterface(3824);
		setChildren(8 + 36, rsinterface);
		addSprite(3825, 75);
		
		addHoverButton(3902, 17, 21, 21, "Close store", 0, 3826, 1);
		addHoveredButton(3826, 18, 21, 21, 3827);
		
		addText(19679, "", 0xff981f, false, true, 52, vencillio, 1);
		addText(19680, "", 0xbf751d, false, true, 52, vencillio, 1);
		addButton(19681, 2, "Interfaces/Shop/SHOP", 0, 0, "", 1);
		addSprite(19687, 1, "Interfaces/Shop/ITEMBG");
		for (int i = 0; i < 36; i++) {
			addText(28000 + i, "-1,0", 0xffff00, false, false, 52, vencillio, 0);
		}
		setBounds(3825, 6, 8, 0, rsinterface);
		setBounds(3902, 479, 15, 1, rsinterface);
		setBounds(3826, 479, 15, 2, rsinterface);
		setBounds(3900, 32, 50, 3, rsinterface);
		setBounds(3901, 240, 17, 4, rsinterface);
		setBounds(19679, 42, 54, 5, rsinterface);
		setBounds(19680, 150, 54, 6, rsinterface);
		setBounds(19681, 129, 50, 7, rsinterface);
		for (int i = 0; i < 36; i++) {
			int x = i % 9;
			int y = i / 9;
			x = 52 * x + 32;
			y = 65 * y + 84;
			setBounds(28000 + i, x, y, 8 + i, rsinterface);
		}
		rsinterface = interfaceCache[3900];
		setChildren(1, rsinterface);
		setBounds(19687, 6, 15, 0, rsinterface);
		rsinterface.invSpritePadX = 20;
		rsinterface.width = 9;
		rsinterface.height = 4;
		rsinterface.invSpritePadY = 33;
		rsinterface.spritesX = new int[36];
		rsinterface.spritesY = new int[36];
		rsinterface.inv = new int[36];
		rsinterface.invStackSizes = new int[36];
		rsinterface = addInterface(19682);
		addSprite(19683, 1, "Interfaces/Shop/SHOP");
		addText(19684, "Main Stock", 0xbf751d, false, true, 52, vencillio, 1);
		addText(19685, "Store Info", 0xff981f, false, true, 52, vencillio, 1);
		addButton(19686, 2, "Interfaces/Shop/SHOP", 95, 19, "Main Stock", 1);
		setChildren(7, rsinterface);
		setBounds(19683, 12, 12, 0, rsinterface);
		setBounds(3901, 240, 21, 1, rsinterface);
		setBounds(19684, 42, 54, 2, rsinterface);
		setBounds(19685, 150, 54, 3, rsinterface);
		setBounds(19686, 23, 50, 4, rsinterface);
		setBounds(3902, 471, 22, 5, rsinterface);
		setBounds(3826, 60, 85, 6, rsinterface);
	}

	/**
	 * Training teleport
	 * 
	 * @param vencillio
	 */
	public static void trainingTeleport(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(61000);
		addSprite(61001, 63);
		addHoverButton(61002, 17, 21, 21, "Close", 250, 61003, 3);
		addHoveredButton(61003, 18, 21, 21, 61004);
		addHoverButton(61005, 59, 123, 30, "Training", 0, 61006, 1);
		addHoveredButton(61006, 60, 123, 30, 61007);
		addHoverButton(61008, 59, 123, 30, "Skilling", 0, 61009, 1);
		addHoveredButton(61009, 60, 123, 30, 61010);
		addHoverButton(61011, 59, 123, 30, "Player Vs Player", 0, 61012, 1);
		addHoveredButton(61012, 60, 123, 30, 61013);
		addHoverButton(61014, 59, 123, 30, "Boss", 0, 61015, 1);
		addHoveredButton(61015, 60, 123, 30, 61016);
		addHoverButton(61017, 59, 123, 30, "Minigame", 0, 61018, 1);
		addHoveredButton(61018, 60, 123, 30, 61019);
		addHoverButton(61020, 59, 123, 30, "Other", 0, 61021, 1);
		addHoveredButton(61021, 60, 123, 30, 61022);
		addText(61023, "@or2@Teleportation Menu (@red@Training@or2@)", 0xff9933, true, true, -1, vencillio, 2);
		addText(61024, "@gre@Training", 0xff9933, true, true, -1, vencillio, 1);
		addText(61025, "Skilling", 0xff9933, true, true, -1, vencillio, 1);
		addText(61026, "Player Vs Player", 0xff9933, true, true, -1, vencillio, 1);
		addText(61027, "Player Vs Monster", 0xff9933, true, true, -1, vencillio, 1);
		addText(61028, "Minigame", 0xff9933, true, true, -1, vencillio, 1);
		addText(61029, "Other", 0xff9933, true, true, -1, vencillio, 1);
		addText(61030, "Teleport Information:", 0xff9933, true, true, -1, vencillio, 2);
		addText(61031, "Selected: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(61032, "Cost: @red@Free", 0xff9933, false, true, -1, vencillio, 0);
		addText(61033, "Requirement: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(61034, "Other: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addHoverButton(61035, 61, 123, 30, "Confirm Teleport", 0, 61036, 1);
		addHoveredButton(61036, 62, 123, 30, 61037);
		addText(61038, "Go!", 0xff9933, true, true, -1, vencillio, 3);
		tab.totalChildren(31);
		tab.child(0, 61001, 11, 2);
		tab.child(1, 61002, 475, 8);
		tab.child(2, 61003, 475, 8);
		tab.child(3, 61005, 16, 35);
		tab.child(4, 61006, 16, 35);
		tab.child(5, 61008, 136, 35);
		tab.child(6, 61009, 136, 35);
		tab.child(7, 61011, 256, 35);
		tab.child(8, 61012, 256, 35);
		tab.child(9, 61014, 376, 35);
		tab.child(10, 61015, 376, 35);
		tab.child(11, 61017, 136, 61);
		tab.child(12, 61018, 136, 61);
		tab.child(13, 61020, 256, 61);
		tab.child(14, 61021, 256, 61);
		tab.child(15, 61023, 255, 9);
		tab.child(16, 61024, 75, 39);
		tab.child(17, 61025, 195, 39);
		tab.child(18, 61026, 315, 39);
		tab.child(19, 61027, 435, 39);
		tab.child(20, 61028, 195, 65);
		tab.child(21, 61029, 315, 65);
		tab.child(22, 61030, 110, 117);
		tab.child(23, 61031, 40, 140);
		tab.child(24, 61032, 40, 160);
		tab.child(25, 61033, 40, 180);
		tab.child(26, 61034, 40, 200);
		tab.child(27, 61035, 70, 250);
		tab.child(28, 61036, 70, 250);
		tab.child(29, 61038, 110, 257);
		tab.child(30, 61050, -70, 109);
		RSInterface scrollInterface = addTabInterface(61050);
		scrollInterface.width = 538;
		scrollInterface.height = 204;
		scrollInterface.scrollMax = 250;
		setChildren(40, scrollInterface);
		int y = 0;
		for (int i = 0; i < 40; i++) {
			addHoverText(61051 + i, "", "Select", vencillio, 1, 0xff9633, true, true, 300);
			setBounds(61051 + i, 260, y, i, scrollInterface);
			y += 25;
		}
	}

	/**
	 * Skilling teleport
	 * 
	 * @param vencillio
	 */
	public static void skillingTeleport(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(62000);
		addSprite(62001, 64);
		addHoverButton(62002, 17, 21, 21, "Close", 250, 62003, 3);
		addHoveredButton(62003, 18, 21, 21, 62004);
		addHoverButton(62005, 59, 123, 30, "Training", 0, 62006, 1);
		addHoveredButton(62006, 60, 123, 30, 62007);
		addHoverButton(62008, 59, 123, 30, "Skilling", 0, 62009, 1);
		addHoveredButton(62009, 60, 123, 30, 62010);
		addHoverButton(62011, 59, 123, 30, "Player Vs Player", 0, 62012, 1);
		addHoveredButton(62012, 60, 123, 30, 62013);
		addHoverButton(62014, 59, 123, 30, "Boss", 0, 62015, 1);
		addHoveredButton(62015, 60, 123, 30, 62016);
		addHoverButton(62017, 59, 123, 30, "Minigame", 0, 62018, 1);
		addHoveredButton(62018, 60, 123, 30, 62019);
		addHoverButton(62020, 59, 123, 30, "Other", 0, 62021, 1);
		addHoveredButton(62021, 60, 123, 30, 62022);
		addText(62023, "@or2@Teleportation Menu (@red@Skilling@or2@)", 0xff9933, true, true, -1, vencillio, 2);
		addText(62024, "Training", 0xff9933, true, true, -1, vencillio, 1);
		addText(62025, "@gre@Skilling", 0xff9933, true, true, -1, vencillio, 1);
		addText(62026, "Player Vs Player", 0xff9933, true, true, -1, vencillio, 1);
		addText(62027, "Player Vs Monster", 0xff9933, true, true, -1, vencillio, 1);
		addText(62028, "Minigame", 0xff9933, true, true, -1, vencillio, 1);
		addText(62029, "Other", 0xff9933, true, true, -1, vencillio, 1);
		addText(62030, "Teleport Information:", 0xff9933, true, true, -1, vencillio, 2);
		addText(62031, "Selected: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(62032, "Cost: @red@Free", 0xff9933, false, true, -1, vencillio, 0);
		addText(62033, "Requirement: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(62034, "Other: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addHoverButton(62035, 61, 123, 30, "Confirm Teleport", 0, 62036, 1);
		addHoveredButton(62036, 62, 123, 30, 62037);
		addText(62038, "Go!", 0xff9933, true, true, -1, vencillio, 3);
		tab.totalChildren(31);
		tab.child(0, 62001, 11, 2);
		tab.child(1, 62002, 475, 8);
		tab.child(2, 62003, 475, 8);
		tab.child(3, 62005, 16, 35);
		tab.child(4, 62006, 16, 35);
		tab.child(5, 62008, 136, 35);
		tab.child(6, 62009, 136, 35);
		tab.child(7, 62011, 256, 35);
		tab.child(8, 62012, 256, 35);
		tab.child(9, 62014, 376, 35);
		tab.child(10, 62015, 376, 35);
		tab.child(11, 62017, 136, 61);
		tab.child(12, 62018, 136, 61);
		tab.child(13, 62020, 256, 61);
		tab.child(14, 62021, 256, 61);
		tab.child(15, 62023, 255, 9);
		tab.child(16, 62024, 75, 39);
		tab.child(17, 62025, 195, 39);
		tab.child(18, 62026, 315, 39);
		tab.child(19, 62027, 435, 39);
		tab.child(20, 62028, 195, 65);
		tab.child(21, 62029, 315, 65);
		tab.child(22, 62030, 110, 117);
		tab.child(23, 62031, 40, 140);
		tab.child(24, 62032, 40, 160);
		tab.child(25, 62033, 40, 180);
		tab.child(26, 62034, 40, 200);
		tab.child(27, 62035, 70, 250);
		tab.child(28, 62036, 70, 250);
		tab.child(29, 62038, 110, 257);
		tab.child(30, 62050, -70, 109);
		RSInterface scrollInterface = addTabInterface(62050);
		scrollInterface.width = 538;
		scrollInterface.height = 204;
		scrollInterface.scrollMax = 300;
		setChildren(40, scrollInterface);
		int y = 0;
		for (int i = 0; i < 40; i++) {
			addHoverText(62051 + i, "", "Select", vencillio, 1, 0xff9633, true, true, 300);
			setBounds(62051 + i, 260, y, i, scrollInterface);
			y += 30;
		}
	}

	/**
	 * PVP teleport
	 * 
	 * @param vencillio
	 */
	public static void pvpTeleport(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(63000);
		addSprite(63001, 65);
		addHoverButton(63002, 17, 21, 21, "Close", 250, 63003, 3);
		addHoveredButton(63003, 18, 21, 21, 63004);
		addHoverButton(63005, 59, 123, 30, "Training", 0, 63006, 1);
		addHoveredButton(63006, 60, 123, 30, 63007);
		addHoverButton(63008, 59, 123, 30, "Skilling", 0, 63009, 1);
		addHoveredButton(63009, 60, 123, 30, 63010);
		addHoverButton(63011, 59, 123, 30, "Player Vs Player", 0, 63012, 1);
		addHoveredButton(63012, 60, 123, 30, 63013);
		addHoverButton(63014, 59, 123, 30, "Boss", 0, 63015, 1);
		addHoveredButton(63015, 60, 123, 30, 63016);
		addHoverButton(63017, 59, 123, 30, "Minigame", 0, 63018, 1);
		addHoveredButton(63018, 60, 123, 30, 63019);
		addHoverButton(63020, 59, 123, 30, "Other", 0, 63021, 1);
		addHoveredButton(63021, 60, 123, 30, 63022);
		addText(63023, "@or2@Teleportation Menu (@red@Player Vs Player@or2@)", 0xff9933, true, true, -1, vencillio, 2);
		addText(63024, "Training", 0xff9933, true, true, -1, vencillio, 1);
		addText(63025, "Skilling", 0xff9933, true, true, -1, vencillio, 1);
		addText(63026, "@gre@Player Vs Player", 0xff9933, true, true, -1, vencillio, 1);
		addText(63027, "Player Vs Monster", 0xff9933, true, true, -1, vencillio, 1);
		addText(63028, "Minigame", 0xff9933, true, true, -1, vencillio, 1);
		addText(63029, "Other", 0xff9933, true, true, -1, vencillio, 1);
		addText(63030, "Teleport Information:", 0xff9933, true, true, -1, vencillio, 2);
		addText(63031, "Selected: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(63032, "Cost: @red@Free", 0xff9933, false, true, -1, vencillio, 0);
		addText(63033, "Requirement: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(63034, "Other: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addHoverButton(63035, 61, 123, 30, "Confirm Teleport", 0, 63036, 1);
		addHoveredButton(63036, 62, 123, 30, 63037);
		addText(63038, "Go!", 0xff9933, true, true, -1, vencillio, 3);
		tab.totalChildren(31);
		tab.child(0, 63001, 11, 2);
		tab.child(1, 63002, 475, 8);
		tab.child(2, 63003, 475, 8);
		tab.child(3, 63005, 16, 35);
		tab.child(4, 63006, 16, 35);
		tab.child(5, 63008, 136, 35);
		tab.child(6, 63009, 136, 35);
		tab.child(7, 63011, 256, 35);
		tab.child(8, 63012, 256, 35);
		tab.child(9, 63014, 376, 35);
		tab.child(10, 63015, 376, 35);
		tab.child(11, 63017, 136, 61);
		tab.child(12, 63018, 136, 61);
		tab.child(13, 63020, 256, 61);
		tab.child(14, 63021, 256, 61);
		tab.child(15, 63023, 255, 9);
		tab.child(16, 63024, 75, 39);
		tab.child(17, 63025, 195, 39);
		tab.child(18, 63026, 315, 39);
		tab.child(19, 63027, 435, 39);
		tab.child(20, 63028, 195, 65);
		tab.child(21, 63029, 315, 65);
		tab.child(22, 63030, 110, 117);
		tab.child(23, 63031, 40, 140);
		tab.child(24, 63032, 40, 160);
		tab.child(25, 63033, 40, 180);
		tab.child(26, 63034, 40, 200);
		tab.child(27, 63035, 70, 250);
		tab.child(28, 63036, 70, 250);
		tab.child(29, 63038, 110, 257);
		tab.child(30, 63050, -70, 109);
		RSInterface scrollInterface = addTabInterface(63050);
		scrollInterface.width = 538;
		scrollInterface.height = 204;
		scrollInterface.scrollMax = 270;
		setChildren(40, scrollInterface);
		int y = 0;
		for (int i = 0; i < 40; i++) {
			addHoverText(63051 + i, "", "Select", vencillio, 1, 0xff9633, true, true, 300);
			setBounds(63051 + i, 260, y, i, scrollInterface);
			y += 30;
		}
	}

	/**
	 * Boss teleport
	 * 
	 * @param vencillio
	 */
	public static void bossTeleport(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(64000);
		addSprite(64001, 66);
		addHoverButton(64002, 17, 21, 21, "Close", 250, 64003, 3);
		addHoveredButton(64003, 18, 21, 21, 64004);
		addHoverButton(64005, 59, 123, 30, "Training", 0, 64006, 1);
		addHoveredButton(64006, 60, 123, 30, 64007);
		addHoverButton(64008, 59, 123, 30, "Skilling", 0, 64009, 1);
		addHoveredButton(64009, 60, 123, 30, 64010);
		addHoverButton(64011, 59, 123, 30, "Player Vs Player", 0, 64012, 1);
		addHoveredButton(64012, 60, 123, 30, 64013);
		addHoverButton(64014, 59, 123, 30, "Boss", 0, 64015, 1);
		addHoveredButton(64015, 60, 123, 30, 64016);
		addHoverButton(64017, 59, 123, 30, "Minigame", 0, 64018, 1);
		addHoveredButton(64018, 60, 123, 30, 64019);
		addHoverButton(64020, 59, 123, 30, "Other", 0, 64021, 1);
		addHoveredButton(64021, 60, 123, 30, 64022);
		addText(64023, "@or2@Teleportation Menu (@red@Player Vs Monster@or2@)", 0xff9933, true, true, -1, vencillio, 2);
		addText(64024, "Training", 0xff9933, true, true, -1, vencillio, 1);
		addText(64025, "Skilling", 0xff9933, true, true, -1, vencillio, 1);
		addText(64026, "Player Vs Player", 0xff9933, true, true, -1, vencillio, 1);
		addText(64027, "@gre@Player Vs Monster", 0xff9933, true, true, -1, vencillio, 1);
		addText(64028, "Minigame", 0xff9933, true, true, -1, vencillio, 1);
		addText(64029, "Other", 0xff9933, true, true, -1, vencillio, 1);
		addText(64030, "Teleport Information:", 0xff9933, true, true, -1, vencillio, 2);
		addText(64031, "Selected: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(64039, "Level: @red@0", 0xff9933, false, true, -1, vencillio, 0);
		addText(64032, "Cost: @red@Free", 0xff9933, false, true, -1, vencillio, 0);
		addText(64033, "Requirement: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(64034, "Other: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addHoverButton(64035, 61, 123, 30, "Confirm Teleport", 0, 64036, 1);
		addHoveredButton(64036, 62, 123, 30, 64037);
		addText(64038, "Go!", 0xff9933, true, true, -1, vencillio, 3);
		tab.totalChildren(32);
		tab.child(0, 64001, 11, 2);
		tab.child(1, 64002, 475, 8);
		tab.child(2, 64003, 475, 8);
		tab.child(3, 64005, 16, 35);
		tab.child(4, 64006, 16, 35);
		tab.child(5, 64008, 136, 35);
		tab.child(6, 64009, 136, 35);
		tab.child(7, 64011, 256, 35);
		tab.child(8, 64012, 256, 35);
		tab.child(9, 64014, 376, 35);
		tab.child(10, 64015, 376, 35);
		tab.child(11, 64017, 136, 61);
		tab.child(12, 64018, 136, 61);
		tab.child(13, 64020, 256, 61);
		tab.child(14, 64021, 256, 61);
		tab.child(15, 64023, 255, 9);
		tab.child(16, 64024, 75, 39);
		tab.child(17, 64025, 195, 39);
		tab.child(18, 64026, 315, 39);
		tab.child(19, 64027, 435, 39);
		tab.child(20, 64028, 195, 65);
		tab.child(21, 64029, 315, 65);
		tab.child(22, 64030, 110, 117);
		tab.child(23, 64031, 40, 140);
		
		
		tab.child(24, 64032, 40, 180);
		tab.child(25, 64033, 40, 200);
		tab.child(26, 64034, 40, 220);
		tab.child(27, 64035, 70, 250);
		tab.child(28, 64036, 70, 250);
		tab.child(29, 64038, 110, 257);
		tab.child(30, 64050, -70, 109);
		tab.child(31, 64039, 40, 160);
		RSInterface scrollInterface = addTabInterface(64050);
		scrollInterface.width = 538;
		scrollInterface.height = 204;
		scrollInterface.scrollMax = 500;
		setChildren(40, scrollInterface);
		int y = 0;
		for (int i = 0; i < 40; i++) {
			addHoverText(64051 + i, "", "Select", vencillio, 1, 0xff9633, true, true, 300);
			setBounds(64051 + i, 260, y, i, scrollInterface);
			y += 30;
		}
	}

	/**
	 * Minigame teleport
	 * 
	 * @param vencillio
	 */
	public static void minigameTeleport(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(65000);
		addSprite(65001, 67);
		addHoverButton(65002, 17, 21, 21, "Close", 250, 65003, 3);
		addHoveredButton(65003, 18, 21, 21, 65004);
		addHoverButton(65005, 59, 123, 30, "Training", 0, 65006, 1);
		addHoveredButton(65006, 60, 123, 30, 65007);
		addHoverButton(65008, 59, 123, 30, "Skilling", 0, 65009, 1);
		addHoveredButton(65009, 60, 123, 30, 65010);
		addHoverButton(65011, 59, 123, 30, "Player Vs Player", 0, 65012, 1);
		addHoveredButton(65012, 60, 123, 30, 65013);
		addHoverButton(65014, 59, 123, 30, "Boss", 0, 65015, 1);
		addHoveredButton(65015, 60, 123, 30, 65016);
		addHoverButton(65017, 59, 123, 30, "Minigame", 0, 65018, 1);
		addHoveredButton(65018, 60, 123, 30, 65019);
		addHoverButton(65020, 59, 123, 30, "Other", 0, 65021, 1);
		addHoveredButton(65021, 60, 123, 30, 65022);
		addText(65023, "@or2@Teleportation Menu (@red@Minigame@or2@)", 0xff9933, true, true, -1, vencillio, 2);
		addText(65024, "Training", 0xff9933, true, true, -1, vencillio, 1);
		addText(65025, "Skilling", 0xff9933, true, true, -1, vencillio, 1);
		addText(65026, "Player Vs Player", 0xff9933, true, true, -1, vencillio, 1);
		addText(65027, "Player Vs Monster", 0xff9933, true, true, -1, vencillio, 1);
		addText(65028, "@gre@Minigame", 0xff9933, true, true, -1, vencillio, 1);
		addText(65029, "Other", 0xff9933, true, true, -1, vencillio, 1);
		addText(65030, "Teleport Information:", 0xff9933, true, true, -1, vencillio, 2);
		addText(65031, "Selected: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(65032, "Cost: @red@Free", 0xff9933, false, true, -1, vencillio, 0);
		addText(65033, "Requirement: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(65034, "Other: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addHoverButton(65035, 61, 123, 30, "Confirm Teleport", 0, 65036, 1);
		addHoveredButton(65036, 62, 123, 30, 65037);
		addText(65038, "Go!", 0xff9933, true, true, -1, vencillio, 3);
		tab.totalChildren(31);
		tab.child(0, 65001, 11, 2);
		tab.child(1, 65002, 475, 8);
		tab.child(2, 65003, 475, 8);
		tab.child(3, 65005, 16, 35);
		tab.child(4, 65006, 16, 35);
		tab.child(5, 65008, 136, 35);
		tab.child(6, 65009, 136, 35);
		tab.child(7, 65011, 256, 35);
		tab.child(8, 65012, 256, 35);
		tab.child(9, 65014, 376, 35);
		tab.child(10, 65015, 376, 35);
		tab.child(11, 65017, 136, 61);
		tab.child(12, 65018, 136, 61);
		tab.child(13, 65020, 256, 61);
		tab.child(14, 65021, 256, 61);
		tab.child(15, 65023, 255, 9);
		tab.child(16, 65024, 75, 39);
		tab.child(17, 65025, 195, 39);
		tab.child(18, 65026, 315, 39);
		tab.child(19, 65027, 435, 39);
		tab.child(20, 65028, 195, 65);
		tab.child(21, 65029, 315, 65);
		tab.child(22, 65030, 110, 117);
		tab.child(23, 65031, 40, 140);
		tab.child(24, 65032, 40, 160);
		tab.child(25, 65033, 40, 180);
		tab.child(26, 65034, 40, 200);
		tab.child(27, 65035, 70, 250);
		tab.child(28, 65036, 70, 250);
		tab.child(29, 65038, 110, 257);
		tab.child(30, 65050, -70, 109);
		RSInterface scrollInterface = addTabInterface(65050);
		scrollInterface.width = 538;
		scrollInterface.height = 204;
		scrollInterface.scrollMax = 270;
		setChildren(40, scrollInterface);
		int y = 0;
		for (int i = 0; i < 40; i++) {
			addHoverText(65051 + i, "", "Select", vencillio, 1, 0xff9633, true, true, 300);
			setBounds(65051 + i, 260, y, i, scrollInterface);
			y += 30;
		}
	}

	/**
	 * Other teleports
	 * 
	 * @param vencillio
	 */
	public static void otherTeleport(TextDrawingArea[] vencillio) {
		RSInterface tab = addInterface(61500);
		addSprite(61501, 68);
		addHoverButton(61502, 17, 21, 21, "Close", 250, 61503, 3);
		addHoveredButton(61503, 18, 21, 21, 61504);
		addHoverButton(61505, 59, 123, 30, "Training", 0, 61506, 1);
		addHoveredButton(61506, 60, 123, 30, 61507);
		addHoverButton(61508, 59, 123, 30, "Skilling", 0, 61509, 1);
		addHoveredButton(61509, 60, 123, 30, 61510);
		addHoverButton(61511, 59, 123, 30, "Player Vs Player", 0, 61512, 1);
		addHoveredButton(61512, 60, 123, 30, 61513);
		addHoverButton(61514, 59, 123, 30, "Boss", 0, 61515, 1);
		addHoveredButton(61515, 60, 123, 30, 61516);
		addHoverButton(61517, 59, 123, 30, "Minigame", 0, 61518, 1);
		addHoveredButton(61518, 60, 123, 30, 61519);
		addHoverButton(61520, 59, 123, 30, "Other", 0, 61521, 1);
		addHoveredButton(61521, 60, 123, 30, 61522);
		addText(61523, "@or2@Teleportation Menu (@red@Other@or2@)", 0xff9933, true, true, -1, vencillio, 2);
		addText(61524, "Training", 0xff9933, true, true, -1, vencillio, 1);
		addText(61525, "Skilling", 0xff9933, true, true, -1, vencillio, 1);
		addText(61526, "Player Vs Player", 0xff9933, true, true, -1, vencillio, 1);
		addText(61527, "Player Vs Monster", 0xff9933, true, true, -1, vencillio, 1);
		addText(61528, "Minigame", 0xff9933, true, true, -1, vencillio, 1);
		addText(61529, "@gre@Other", 0xff9933, true, true, -1, vencillio, 1);
		addText(61530, "Teleport Information:", 0xff9933, true, true, -1, vencillio, 2);
		addText(61531, "Selected: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(61532, "Cost: @red@Free", 0xff9933, false, true, -1, vencillio, 0);
		addText(61533, "Requirement: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addText(61534, "Other: @red@None", 0xff9933, false, true, -1, vencillio, 0);
		addHoverButton(61535, 61, 123, 30, "Confirm Teleport", 0, 61536, 1);
		addHoveredButton(61536, 62, 123, 30, 61537);
		addText(61538, "Go!", 0xff9933, true, true, -1, vencillio, 3);
		tab.totalChildren(31);
		tab.child(0, 61501, 11, 2);
		tab.child(1, 61502, 475, 8);
		tab.child(2, 61503, 475, 8);
		tab.child(3, 61505, 16, 35);
		tab.child(4, 61506, 16, 35);
		tab.child(5, 61508, 136, 35);
		tab.child(6, 61509, 136, 35);
		tab.child(7, 61511, 256, 35);
		tab.child(8, 61512, 256, 35);
		tab.child(9, 61514, 376, 35);
		tab.child(10, 61515, 376, 35);
		tab.child(11, 61517, 136, 61);
		tab.child(12, 61518, 136, 61);
		tab.child(13, 61520, 256, 61);
		tab.child(14, 61521, 256, 61);
		tab.child(15, 61523, 255, 9);
		tab.child(16, 61524, 75, 39);
		tab.child(17, 61525, 195, 39);
		tab.child(18, 61526, 315, 39);
		tab.child(19, 61527, 435, 39);
		tab.child(20, 61528, 195, 65);
		tab.child(21, 61529, 315, 65);
		tab.child(22, 61530, 110, 117);
		tab.child(23, 61531, 40, 140);
		tab.child(24, 61532, 40, 160);
		tab.child(25, 61533, 40, 180);
		tab.child(26, 61534, 40, 200);
		tab.child(27, 61535, 70, 250);
		tab.child(28, 61536, 70, 250);
		tab.child(29, 61538, 110, 257);
		tab.child(30, 61550, -70, 109);
		RSInterface scrollInterface = addTabInterface(61550);
		scrollInterface.width = 538;
		scrollInterface.height = 204;
		scrollInterface.scrollMax = 205;
		setChildren(40, scrollInterface);
		int y = 0;
		for (int i = 0; i < 40; i++) {
			addHoverText(61551 + i, "", "Select", vencillio, 1, 0xff9633, true, true, 300);
			setBounds(61551 + i, 260, y, i, scrollInterface);
			y += 30;
		}
	}

	
	/**
	 * Option tab
	 * 
	 * @param vencillio
	 */

	public static void optionTab(TextDrawingArea[] vencillio) {
		RSInterface tab = addTabInterface(904);
		RSInterface energy = interfaceCache[149];
		energy.textColor = 0xff9933;
		addSprite(905, 86);
		addSprite(907, 87);
		addSprite(909, 88);
		addSprite(951, 89);
		addSprite(953, 90);
		addSprite(955, 91);
		addSprite(36001, 38);
		addSprite(36002, 38);
		addSprite(36017, 38);
		addConfigButton(152, 904, 92, 93, 40, 40, "Toggle-run", 1, 5, 173);
		addConfigButton(913, 904, 92, 93, 40, 40, "Toggle-Mouse Buttons", 0, 5, 170);
		addConfigButton(957, 904, 92, 93, 40, 40, "Toggle-Split Private Chat", 1, 5, 287);
		addConfigButton(12464, 904, 92, 93, 40, 40, "Toggle-Accept Aid", 0, 5, 427);
		addConfigButton(906, 904, 94, 95, 32, 16, "Dark", 1, 5, 166);
		addConfigButton(908, 904, 96, 97, 32, 16, "Normal", 2, 5, 166);
		addConfigButton(910, 904, 98, 99, 32, 16, "Bright", 3, 5, 166);
		addConfigButton(912, 904, 100, 101, 32, 16, "Very Bright", 4, 5, 166);
		addSprite(947, 102);
		addSprite(949, 103);
		addHoverButton(36004, 0, 54, 43, "Fixed", -1, 36005, 1);
		addHoveredButton(36005, 1, 54, 43, 36006);
		addHoverButton(36007, 2, 54, 43, "Resizable", -1, 36008, 1);
		addHoveredButton(36008, 3, 54, 43, 36009);
		addHoverButton(36010, 4, 54, 43, "Fullscreen", -1, 36011, 1);
		addHoveredButton(36011, 5, 54, 43, 36012);
		addHoverButton(36026, 104, 40, 40, "Extra settings", -1, 36027, 1);
		addHoveredButton(36027, 105, 40, 40, 36028);
		addHoverButton(36029, 69, 40, 40, "Change chat colors", -1, 36030, 1);
		addHoveredButton(36030, 70, 40, 40, 36031);
		addText(36016, "", vencillio, 1, 0xff9933, true, true);
		addText(36003, "Options Tab", vencillio, 1, 0xff9933, true, true);
		addText(36013, "Fixed", vencillio, 0, 0xff9933, true, true);
		addText(36014, "Resizable", vencillio, 0, 0xff9933, true, true);
		addText(36015, "Fullscreen", vencillio, 0, 0xff9933, true, true);
		addHoverText(36025, "Screen Options", "Screen Options", vencillio, 0, 0xff981f, true, true, 60);
		tab.totalChildren(34);
		tab.child(0, 913, 15, 153);
		tab.child(1, 955, 19, 159);
		tab.child(2, 36029, 75, 153);
		tab.child(3, 953, 79, 160);
		tab.child(4, 957, 135, 153);
		tab.child(5, 951, 139, 159);
		tab.child(6, 12464, 15, 208);
		tab.child(7, 949, 20, 213);
		tab.child(8, 152, 75, 208);
		tab.child(9, 947, 86, 212);
		tab.child(10, 149, 80, 230);
		tab.child(11, 36001, 0, 18);
		tab.child(12, 36002, 0, -100);
		tab.child(13, 36003, 93, 2);
		tab.child(14, 36004, 9, 26);
		tab.child(15, 36005, 9, 26);
		tab.child(16, 36007, 70, 26);
		tab.child(17, 36008, 70, 26);
		tab.child(18, 36010, 131, 26);
		tab.child(19, 36011, 131, 26);
		tab.child(20, 36013, 37, 72);
		tab.child(21, 36014, 97, 72);
		tab.child(22, 36015, 157, 72);
		tab.child(23, 36016, 93, 88);
		tab.child(24, 36017, 0, 106);
		tab.child(25, 905, 13, 115);
		tab.child(26, 906, 48, 123);
		tab.child(27, 908, 80, 123);
		tab.child(28, 910, 112, 123);
		tab.child(29, 912, 144, 123);
		tab.child(30, 36025, 69, 89);
		tab.child(31, 36026, 135, 208);
		tab.child(32, 36027, 135, 208);
		tab.child(33, 36030, 75, 153);
		/*tab.child(34, 36032, 10, 141);
		
		tab.child(35, 36033, 38, 143);
		tab.child(36, 36034, 70, 143);
		tab.child(37, 36035, 102, 143);
		tab.child(38, 36036, 134, 143);
		tab.child(39, 36037, 156, 143);*/
		
	}

	/**
	 * Bank tab interface
	 * 
	 * @param wid
	 */
	public static void bank(TextDrawingArea[] wid) {
		RSInterface bank = addInterface(5292);

		setChildren(57, bank);

		int id = 50_000;

		Sprite disabled = Client.cacheSprite[137];
		Sprite enabled = Client.cacheSprite[138];
		Sprite button1 = method207(0, aClass44, "miscgraphics");
		Sprite button2 = method207(9, aClass44, "miscgraphics");

		addSprite(id, 108);
		addHoverButton(id + 1, method207(2, aClass44, "miscgraphics2"), 21, 21, "Close", 250, id + 2, 3);
		addHoveredButton(id + 2, method207(3, aClass44, "miscgraphics2"), 21, 21, id + 3);
		addContainer(5382, 109, 8, 44, new String[] { "Withdraw-1", "Withdraw-5", "Withdraw-10", "Withdraw-All", "Withdraw-X", null, "Withdraw-All but one" });
		addConfigButton(id + 4, id + 4, button1, button2, 36, 36, "Search", 1, 5, 1012);
		interfaceCache[id + 4].contentType = 555;
		addButton(id + 5, button2, button1, "Deposit inventory", 36, 36);
		addButton(id + 6, button2, button1, "Deposit worn equipment", 36, 36);
		addButton(id + 7, method207(0, aClass44, "miscgraphics3"), method207(0, aClass44, "miscgraphics3"), "Show menu", 25, 25);
		addSprite(id + 8, 118);
		addSprite(id + 9, 116);
		addSprite(id + 10, 117);
		addSprite(id + 11, 115);

		addSprite(id + 12, 113);

		addText(id + 53, "%1", wid, 0, 0xFE9624, true);
		RSInterface line = addInterface(id + 54);
		line.type = 3;
		line.aBoolean227 = true;
		line.width = 14;
		line.height = 1;
		line.textColor = 0xFE9624;
		addText(id + 55, "350", wid, 0, 0xFE9624, true);

		interfaceCache[id + 53].valueIndexArray = new int[][] { { 22, 5382, 0 } };

		int child = 0;

		bank.child(child++, id, 12, 2);
		bank.child(child++, id + 1, 472, 9);
		bank.child(child++, id + 2, 472, 9);

		bank.child(child++, id + 53, 30, 8);
		bank.child(child++, id + 54, 24, 19);
		bank.child(child++, id + 55, 30, 20);

		for (int tab = 0; tab < 40; tab += 4) {
			addButton(id + 13 + tab, Client.cacheSprite[111], Client.cacheSprite[111], "Collapse " + (tab == 0 ? "@or2@all tabs" : "tab @or2@" + (tab / 4)), 39, 40);
			int[] array = { 21, (tab / 4), 0 };
			if (tab / 4 == 0) {
				array = new int[] { 5, 1000, 0 };
			}
			addHoverConfigButton(id + 14 + tab, id + 15 + tab, 109, 111, 39, 40, tab == 0 ? "View all" : "New tab", new int[] { 1, tab / 4 == 0 ? 1 : 3 }, new int[] { (tab / 4), 0 }, new int[][] { { 5, 1000, 0 }, array });
			addHoveredConfigButton(interfaceCache[id + 14 + tab], id + 15 + tab, id + 16 + tab, 110, 111);
			interfaceCache[id + 14 + tab].parentID = id;
			interfaceCache[id + 15 + tab].parentID = id;
			interfaceCache[id + 16 + tab].parentID = id;
			bank.child(child++, id + 13 + tab, 57 + 40 * (tab / 4), 37);
			bank.child(child++, id + 14 + tab, 57 + 40 * (tab / 4), 37);
			bank.child(child++, id + 15 + tab, 57 + 40 * (tab / 4), 37);
		}

		interfaceCache[5385].width += 20;
		interfaceCache[5385].height -= 18;
		interfaceCache[5382].contentType = 206;

		int[] interfaces = new int[] { 5386, 5387, 8130, 8131 };

		for (int rsint : interfaces) {
			interfaceCache[rsint].disabledSprite = disabled;
			interfaceCache[rsint].enabledSprite = enabled;
			interfaceCache[rsint].width = enabled.myWidth;
			interfaceCache[rsint].height = enabled.myHeight;
		}

		bank.child(child++, id + 12, 59, 41);

		bank.child(child++, 5383, 180, 12);
		bank.child(child++, 5385, 31, 77);

		bank.child(child++, 8131, 102, 306);
		bank.child(child++, 8130, 17, 306);

		bank.child(child++, 5386, 282, 306);
		bank.child(child++, 5387, 197, 306);
		bank.child(child++, 8132, 127, 309);
		bank.child(child++, 8133, 45, 309);
		bank.child(child++, 5390, 54, 291);

		bank.child(child++, 5389, 227, 309);
		bank.child(child++, 5391, 311, 309);
		bank.child(child++, 5388, 248, 291);
		bank.child(child++, id + 4, 376, 291);
		bank.child(child++, id + 5, 417, 291);
		bank.child(child++, id + 6, 458, 291);
		bank.child(child++, id + 7, 463, 44);
		bank.child(child++, id + 8, 379, 298);
		bank.child(child++, id + 9, 420, 298);
		bank.child(child++, id + 10, 461, 298);
		bank.child(child++, id + 11, 463, 44);
	}

	/**
	 * Quick prayers
	 * 
	 * @param vencillio
	 */
	public static void quickPrayers(TextDrawingArea[] vencillio) {
		int frame = 0;
		RSInterface tab = addTabInterface(17200);
		setChildren(58, tab);//
		setBounds(5632, 5, 8 + 20, frame++, tab);
		setBounds(5633, 44, 8 + 20, frame++, tab);
		setBounds(5634, 79, 11 + 20, frame++, tab);
		setBounds(19813, 116, 10 + 20, frame++, tab);
		setBounds(19815, 153, 9 + 20, frame++, tab);
		setBounds(5635, 5, 48 + 20, frame++, tab);
		setBounds(5636, 44, 47 + 20, frame++, tab);
		setBounds(5637, 79, 49 + 20, frame++, tab);
		setBounds(5638, 116, 50 + 20, frame++, tab);
		setBounds(5639, 154, 50 + 20, frame++, tab);
		setBounds(5640, 4, 84 + 20, frame++, tab);
		setBounds(19817, 44, 87 + 20, frame++, tab);
		setBounds(19820, 81, 85 + 20, frame++, tab);
		setBounds(5641, 117, 85 + 20, frame++, tab);
		setBounds(5642, 156, 87 + 20, frame++, tab);
		setBounds(5643, 5, 125 + 20, frame++, tab);
		setBounds(5644, 43, 124 + 20, frame++, tab);
		setBounds(13984, 83, 124 + 20, frame++, tab);
		setBounds(5645, 115, 121 + 20, frame++, tab);
		setBounds(19822, 154, 124 + 20, frame++, tab);
		setBounds(19824, 5, 160 + 20, frame++, tab);
		setBounds(5649, 41, 158 + 20, frame++, tab);
		setBounds(5647, 79, 163 + 20, frame++, tab);
		setBounds(5648, 116, 158 + 20, frame++, tab);
		setBounds(19826, 161, 160 + 20, frame++, tab);
		setBounds(19828, 4, 207 + 12, frame++, tab);
		setBounds(17229, 0, 25, frame++, tab);
		setBounds(17201, 0, 22, frame++, tab);
		setBounds(17201, 0, 237, frame++, tab);
		setBounds(17202, 5 - 3, 8 + 17, frame++, tab);
		setBounds(17203, 44 - 3, 8 + 17, frame++, tab);
		setBounds(17204, 79 - 3, 8 + 17, frame++, tab);
		setBounds(17205, 116 - 3, 8 + 17, frame++, tab);
		setBounds(17206, 153 - 3, 8 + 17, frame++, tab);
		setBounds(17207, 5 - 3, 48 + 17, frame++, tab);
		setBounds(17208, 44 - 3, 48 + 17, frame++, tab);
		setBounds(17209, 79 - 3, 48 + 17, frame++, tab);
		setBounds(17210, 116 - 3, 48 + 17, frame++, tab);
		setBounds(17211, 153 - 3, 48 + 17, frame++, tab);
		setBounds(17212, 5 - 3, 85 + 17, frame++, tab);
		setBounds(17213, 44 - 3, 85 + 17, frame++, tab);
		setBounds(17214, 79 - 3, 85 + 17, frame++, tab);
		setBounds(17215, 116 - 3, 85 + 17, frame++, tab);
		setBounds(17216, 153 - 3, 85 + 17, frame++, tab);
		setBounds(17217, 5 - 3, 124 + 17, frame++, tab);
		setBounds(17218, 44 - 3, 124 + 17, frame++, tab);
		setBounds(17219, 79 - 3, 124 + 17, frame++, tab);
		setBounds(17220, 116 - 3, 124 + 17, frame++, tab);
		setBounds(17221, 153 - 3, 124 + 17, frame++, tab);
		setBounds(17222, 5 - 3, 160 + 17, frame++, tab);
		setBounds(17223, 44 - 3, 160 + 17, frame++, tab);
		setBounds(17224, 79 - 3, 160 + 17, frame++, tab);
		setBounds(17225, 116 - 3, 160 + 17, frame++, tab);
		setBounds(17226, 153 - 3, 160 + 17, frame++, tab);
		setBounds(17227, 4 - 3, 207 + 4, frame++, tab);
		setBounds(17230, 5, 5, frame++, tab);
		setBounds(17231, 0, 237, frame++, tab);
		setBounds(17232, 0, 237, frame++, tab);
	}

	/**
	 * Quick curses
	 * 
	 * @param vencillio
	 */
	public static void quickCurses(TextDrawingArea[] vencillio) {
		RSInterface tab = addTabInterface(17234);
		addTransparentSprite(17229, 444);
		addSprite(17201, 445);
		addText(17230, "Select your quick prayers:", vencillio, 0, 0xFF981F, false, true);
		for (int i = 17202, j = 630; i <= 17228 || j <= 656; i++, j++) {
			addConfigButton(i, 17200, 79, 78, 14, 15, "Select prayer", 0, 1, j);
		    // addConfigButton(j, 17200, 2, 1, "QuickPrayer/Sprite", 14, 15, "Select", 0, 1, k);
		      
		}
		addHoverButton(17231, 446, 190, 24, "Confirm Selection", -1, 17232, 1);
		addHoveredButton(17232, 447, 190, 24, 17233);
		int frame = 0;
		setChildren(46, tab);
		setBounds(21358, 11, 8 + 20, frame++, tab);
		setBounds(21360, 50, 11 + 20, frame++, tab);
		setBounds(21362, 87, 11 + 20, frame++, tab);
		setBounds(21364, 122, 10 + 20, frame++, tab);
		setBounds(21366, 159, 11 + 20, frame++, tab);
		setBounds(21368, 12, 45 + 20, frame++, tab);
		setBounds(21370, 46, 45 + 20, frame++, tab);
		setBounds(21372, 83, 46 + 20, frame++, tab);
		setBounds(21374, 119, 45 + 20, frame++, tab);
		setBounds(21376, 157, 45 + 20, frame++, tab);
		setBounds(21378, 11, 83 + 20, frame++, tab);
		setBounds(21380, 49, 84 + 20, frame++, tab);
		setBounds(21382, 84, 83 + 20, frame++, tab);
		setBounds(21384, 123, 84 + 20, frame++, tab);
		setBounds(21386, 159, 83 + 20, frame++, tab);
		setBounds(21388, 12, 119 + 20, frame++, tab);
		setBounds(21390, 49, 119 + 20, frame++, tab);
		setBounds(21392, 88, 119 + 20, frame++, tab);
		setBounds(21394, 122, 121 + 20, frame++, tab);
		setBounds(21396, 155, 122 + 20, frame++, tab);
		setBounds(17229, 0, 25, frame++, tab);
		setBounds(17201, 0, 22, frame++, tab);
		setBounds(17201, 0, 237, frame++, tab);
		setBounds(17202, 13 - 3, 8 + 17, frame++, tab);
		setBounds(17203, 52 - 3, 8 + 17, frame++, tab);
		setBounds(17204, 90 - 3, 8 + 17, frame++, tab);
		setBounds(17205, 126 - 3, 8 + 17, frame++, tab);
		setBounds(17206, 162 - 3, 8 + 17, frame++, tab);
		setBounds(17207, 13 - 3, 45 + 17, frame++, tab);
		setBounds(17208, 52 - 3, 45 + 17, frame++, tab);
		setBounds(17209, 90 - 3, 45 + 17, frame++, tab);
		setBounds(17210, 126 - 3, 45 + 17, frame++, tab);
		setBounds(17211, 162 - 3, 45 + 17, frame++, tab);
		setBounds(17212, 13 - 3, 80 + 17, frame++, tab);
		setBounds(17213, 52 - 3, 80 + 17, frame++, tab);
		setBounds(17214, 90 - 3, 80 + 17, frame++, tab);
		setBounds(17215, 126 - 3, 80 + 17, frame++, tab);
		setBounds(17216, 162 - 3, 80 + 17, frame++, tab);
		setBounds(17217, 13 - 3, 119 + 17, frame++, tab);
		setBounds(17218, 52 - 3, 119 + 17, frame++, tab);
		setBounds(17219, 90 - 3, 119 + 17, frame++, tab);
		setBounds(17220, 126 - 3, 119 + 17, frame++, tab);
		setBounds(17221, 162 - 3, 119 + 17, frame++, tab);
		setBounds(17230, 5, 5, frame++, tab);
		setBounds(17231, 0, 237, frame++, tab);
		setBounds(17232, 0, 237, frame++, tab);
	}

	/**
	 * Equipment screen interface
	 * 
	 * @param vencillio
	 */
	public static void equipmentScreen(TextDrawingArea[] vencillio) {
		RSInterface tab = addTabInterface(15106);
		addSprite(15107, 54);
		addHoverButton(15210, 21, 21, 21, "Close", 250, 15211, 3);
		addHoveredButton(15211, 22, 21, 21, 15212);
		addText(15111, "", vencillio, 2, 0xe4a146, false, true);
		addText(15112, "Attack bonus", vencillio, 2, 0xFF981F, false, true);
		addText(15113, "Defence bonus", vencillio, 2, 0xFF981F, false, true);
		addText(15114, "Other bonuses", vencillio, 2, 0xFF981F, false, true);
		addText(15115, "Equipment Bonuses", vencillio, 2, 0xFF981F, false, true);
		addText(15116, "", vencillio, 0, 0xFF981F, true, true);
		addText(15117, "", vencillio, 0, 0xFF981F, true, true);
		addText(15118, "", vencillio, 0, 0xFF981F, true, true);
		for (int i = 1675; i <= 1684; i++) {
			textSize(i, vencillio, 1);
		}
		textSize(1686, vencillio, 1);
		textSize(1687, vencillio, 1);
		addChar(15125, 560);
		tab.totalChildren(48);
		tab.child(0, 15107, 15, 5);
		tab.child(1, 15210, 480, 9);
		tab.child(2, 15211, 480, 9);
		tab.child(3, 15111, 14, 30);
		int shift = 3;
		int rofl = 2;
		int Child = 4;
		int Y = 44 + shift;
		tab.child(16, 15112, 23, 30 + shift - rofl);
		for (int i = 1675; i <= 1679; i++) {
			tab.child(Child, i, 27, Y - rofl);
			Child++;
			Y += 14;
		}
		int edit = rofl;
		tab.child(17, 15113, 24, 122 - edit + shift);
		tab.child(9, 1680, 27, 137 - edit - 1 + shift);
		tab.child(10, 1681, 27, 153 - edit - 3 + shift);
		tab.child(11, 1682, 27, 168 - edit - 4 + shift);
		tab.child(12, 1683, 27, 183 - edit - 5 + shift);
		tab.child(13, 1684, 27, 197 - edit - 6 + shift);
		tab.child(18, 15114, 24, 211 + shift);
		tab.child(14, 1686, 27, 250 - 24 + shift);
		tab.child(15, 15125, 188, 185);
		tab.child(19, 1645, 104 + 295, 149 - 52);
		tab.child(20, 1646, 399, 163);
		tab.child(21, 1647, 399, 163);
		tab.child(22, 1648, 399, 58 + 146);
		tab.child(23, 1649, 26 + 22 + 297 - 2, 110 - 44 + 118 - 13 + 5);
		tab.child(24, 1650, 321 + 22, 58 + 154);
		tab.child(25, 1651, 321 + 134, 58 + 118);
		tab.child(26, 1652, 321 + 134, 58 + 154);
		tab.child(27, 1653, 321 + 48, 58 + 81);
		tab.child(28, 1654, 321 + 107, 58 + 81);
		tab.child(29, 1655, 321 + 58, 58 + 42);
		tab.child(30, 1656, 321 + 112, 58 + 41);
		tab.child(31, 1657, 321 + 78, 58 + 4);
		tab.child(32, 1658, 321 + 37, 58 + 43);
		tab.child(33, 1659, 321 + 78, 58 + 43);
		tab.child(34, 1660, 321 + 119, 58 + 43);
		tab.child(35, 1661, 321 + 22, 58 + 82);
		tab.child(36, 1662, 321 + 78, 58 + 82);
		tab.child(37, 1663, 321 + 134, 58 + 82);
		tab.child(38, 1664, 321 + 78, 58 + 122);
		tab.child(39, 1665, 321 + 78, 58 + 162);
		tab.child(40, 1666, 321 + 22, 58 + 162);
		tab.child(41, 1667, 321 + 134, 58 + 162);
		tab.child(42, 1688, 50 + 297 - 2, 110 - 13 + 5);
		tab.child(43, 1687, 27, 250 - 24 + 14 + shift);
		tab.child(44, 15115, 195, 9);
		tab.child(45, 15116, 420, 275);
		tab.child(46, 15117, 420, 290);
		tab.child(47, 15118, 420, 305);
		for (int i = 1675; i <= 1684; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xFF9200;
			rsi.centerText = false;
		}
		for (int i = 1686; i <= 1687; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xFF9200;
			rsi.centerText = false;
		}
	}

	/**
	 * Equipment tab interface
	 * 
	 * @param vencillio
	 */
	public static void equipmentTab(TextDrawingArea[] vencillio) {
		RSInterface Interface = interfaceCache[1644];
		removeSomething(21338);
		removeSomething(21344);
		removeSomething(21342);
		removeSomething(21341);
		removeSomething(21340);
		removeSomething(15103);
		removeSomething(15104);
		Interface.children[26] = 27650;
		Interface.childX[26] = 0;
		Interface.childY[26] = 0;
		Interface = addInterface(27650);
		addHoverButton(15201, 46, 40, 40, "Show Equipment Screen", 0, 15202, 1);
		addHoveredButton(15202, 47, 40, 40, 15203);
		addHoverButton(15204, 48, 40, 40, "Items Kept on Death", 0, 15205, 1);
		addHoveredButton(15205, 49, 40, 40, 15206);
		addHoverButton(15207, 50, 40, 40, "Price Checker", 0, 15208, 1);
		addHoveredButton(15208, 51, 40, 40, 15209);
		addHoverButton(15310, 52, 40, 40, "Experience Toggle", 0, 15311, 1);
		addHoveredButton(15311, 53, 40, 40, 15312);
		setChildren(8, Interface);
		setBounds(15201, 21, 210, 0, Interface);
		setBounds(15202, 21, 210, 1, Interface);
		setBounds(15204, 132, 210, 2, Interface);
		setBounds(15205, 132, 210, 3, Interface);
		setBounds(15207, 76, 210, 4, Interface);
		setBounds(15208, 76, 210, 5, Interface);
		setBounds(15310, 5, 2, 6, Interface);
		setBounds(15311, 5, 2, 7, Interface);
	}

	/**
	 * The clan chat tab
	 * 
	 * @param vencillio
	 */
	public static void clanChatTab(TextDrawingArea[] vencillio) {
		RSInterface tab = addTabInterface(18128);
		addHoverButton(18129, 40, 72, 32, "Join Chat", 550, 18130, 5);
		addHoveredButton(18130, 41, 72, 32, 18131);
		addHoverButton(18132, 40, 72, 32, "Clan Setup", -1, 18133, 5);
		addHoveredButton(18133, 41, 72, 32, 18134);
		
		addButtons(18250, 31, "Toggle lootshare", 18253, 1);
		drawTooltip(18253, "Toggle-Lootshare");
		
		addText(18135, "Join/Leave", vencillio, 0, 0xff9b00, true, true);
		addText(18136, "Clan Setup", vencillio, 0, 0xff9b00, true, true);
		addSprite(18137, 42);
		addText(18138, "Clan Chat", vencillio, 2, 0xff9b00, true, true);
		addText(18139, "Talking in: Not in chat", vencillio, 0, 0xff9b00, false, true);
		addText(18140, "Owner: None", vencillio, 0, 0xff9b00, false, true);
		
		addText(18252, "(0/100)", vencillio, 0, 0xff9b00, false, true);
		tab.totalChildren(14);
		tab.child(0, 18137, 3, 57);
		tab.child(1, 18143, 7, 62);
		tab.child(2, 18129, 15, 227);
		tab.child(3, 18130, 15, 227);
		tab.child(4, 18132, 96, 227);
		tab.child(5, 18133, 96, 227);
		tab.child(6, 18135, 51, 236);
		tab.child(7, 18136, 132, 236);
		tab.child(8, 18138, 95, 3);
		tab.child(9, 18139, 10, 23);
		tab.child(10, 18140, 25, 38);
		tab.child(11, 18250, 148, 24);
		tab.child(12, 18253, 74, 60);
		tab.child(13, 18252, 140, 5);
		/* Text area */
		RSInterface list = addTabInterface(18143);
		list.totalChildren(100);
		for (int i = 18144; i <= 18244; i++) {
			addText(i, "", vencillio, 0, 0xffffff, false, true);
		}
		for (int id = 18144, i = 0; id <= 18243 && i <= 99; id++, i++) {
			list.children[i] = id;
			list.childX[i] = 5;
			for (int id2 = 18144, i2 = 1; id2 <= 18243 && i2 <= 99; id2++, i2++) {
				list.childY[0] = 2;
				list.childY[i2] = list.childY[i2 - 1] + 14;
			}
		}
		for (int id = 18144, i = 0; id <= 18243 && i <= 99; id++, i++) {
			interfaceCache[id].actions = new String[] { "Edit Rank", "Kick", "Ban" };
			list.children[i] = id;
			list.childX[i] = 5;
			for (int id2 = 18144, i2 = 1; id2 <= 18243 && i2 <= 99; id2++, i2++) {
				list.childY[0] = 2;
				list.childY[i2] = list.childY[i2 - 1] + 14;
			}
		}
		list.height = 152;
		list.width = 164;
		list.scrollMax = 1405;
	}

	/**
	 * The clan chat setup interface
	 * 
	 * @param vencillio
	 */
	public static void clanChatSetup(TextDrawingArea[] vencillio) {
		RSInterface rsi = addInterface(43700);
		rsi.totalChildren(12 + 15);
		int count = 0;
		/* Background */
		addSprite(43701, 35);
		rsi.child(count++, 43701, 14, 18);
		/* Close button */
		addButton(43702, 21, "Close");
		interfaceCache[43702].atActionType = 3;
		rsi.child(count++, 43702, 475, 26);
		/* Clan Setup title */
		addText(43703, "Clan Setup", vencillio, 2, 0xFF981F, true, true);
		rsi.child(count++, 43703, 256, 26);
		/* Setup buttons */
		String[] titles = { "Clan name:", "Who can enter chat?", "Who can talk on chat?", "Who can kick on chat?", "Who can ban on chat?" };
		String[] defaults = { "Chat Disabled", "Anyone", "Anyone", "Anyone", "Anyone" };
		String[] whoCan = { "Anyone", "Recruit", "Corporal", "Sergeant", "Lieutenant", "Captain", "General", "Only Me" };
		for (int index = 0, id = 43704, y = 50; index < titles.length; index++, id += 3, y += 40) {
			addButton(id, 36, "");
			interfaceCache[id].atActionType = 0;
			if (index > 0) {
				interfaceCache[id].actions = whoCan;
			} else {
				interfaceCache[id].actions = new String[] { "Change title", "Delete clan" };
			}
			addText(id + 1, titles[index], vencillio, 0, 0xFF981F, true, true);
			addText(id + 2, defaults[index], vencillio, 1, 0xFFFFFF, true, true);
			rsi.child(count++, id, 25, y);
			rsi.child(count++, id + 1, 100, y + 4);
			rsi.child(count++, id + 2, 100, y + 17);
		}
		/* Table */
		addSprite(43719, 39);
		rsi.child(count++, 43719, 197, 70);
		/* Labels */
		int id = 43720;
		int y = 74;
		addText(id, "Ranked Members", vencillio, 2, 0xFF981F, false, true);
		rsi.child(count++, id++, 202, y);
		addText(id, "Banned Members", vencillio, 2, 0xFF981F, false, true);
		rsi.child(count++, id++, 339, y);
		/* Ranked members list */
		RSInterface list = addInterface(id++);
		int lines = 100;
		list.totalChildren(lines);
		String[] ranks = { "Demote", "Recruit", "Corporal", "Sergeant", "Lieutenant", "Captain", "General", "Owner" };
		list.childY[0] = 2;
		// System.out.println(id);
		for (int index = id; index < id + lines; index++) {
			addText(index, "", vencillio, 1, 0xffffff, false, true);
			interfaceCache[index].actions = ranks;
			list.children[index - id] = index;
			list.childX[index - id] = 2;
			list.childY[index - id] = (index - id > 0 ? list.childY[index - id - 1] + 14 : 0);
		}
		id += lines;
		list.width = 119;
		list.height = 210;
		list.scrollMax = (lines * 14) + 2;
		rsi.child(count++, list.id, 199, 92);
		/* Banned members list */
		list = addInterface(id++);
		list.totalChildren(lines);
		list.childY[0] = 2;
		// System.out.println(id);
		for (int index = id; index < id + lines; index++) {
			addText(index, "", vencillio, 1, 0xffffff, false, true);
			interfaceCache[index].actions = new String[] { "Unban" };
			list.children[index - id] = index;
			list.childX[index - id] = 0;
			list.childY[index - id] = (index - id > 0 ? list.childY[index - id - 1] + 14 : 0);
		}
		id += lines;
		list.width = 119;
		list.height = 210;
		list.scrollMax = (lines * 14) + 2;
		rsi.child(count++, list.id, 339, 92);
		/* Table info text */
		y = 47;
		addText(id, "You can manage both ranked and banned members here.", vencillio, 0, 0xFF981F, true, true);
		rsi.child(count++, id++, 337, y);
		addText(id, "Right click on a name to edit the member.", vencillio, 0, 0xFF981F, true, true);
		rsi.child(count++, id++, 337, y + 11);
		/* Add ranked member button */
		y = 75;
		addButton(id, 33, "Add ranked member");
		interfaceCache[id].atActionType = 5;
		rsi.child(count++, id++, 319, y);
		/* Add banned member button */
		addButton(id, 33, "Add banned member");
		interfaceCache[id].atActionType = 5;
		rsi.child(count++, id++, 459, y);

		/* Hovers */
		int[] clanSetup = { 43702, 43704, 43707, 43710, 43713, 43716, 43826, 43827 };
		String[] names = { "close", "sprite", "sprite", "sprite", "sprite", "sprite", "plus", "plus" };
		int[] ids = { 1, 3, 3, 3, 3, 3, 1, 1 };
		for (int index = 0; index < clanSetup.length; index++) {
			rsi = interfaceCache[clanSetup[index]];
			rsi.disabledHover = imageLoader(ids[index], "Interfaces/Clan Chat/" + names[index]);
		}
	}

	/**
	 * Quest tab interface
	 * 
	 * @param vencillio
	 */
	public static void questTab(TextDrawingArea[] vencillio) {
		RSInterface tab = addTabInterface(29400);
		addSprite(29401, 82);
		addSprite(29402, 38);
		addText(29403, "Vencillio", vencillio, 2, 0xF7AA25, true, true);
		addText(29407, "Www.Vencillio.Com", vencillio, 2, 0xF7AA25, true, true);
		addHoverButton(29404, 84, 25, 25, "Achievement diary", -1, 29405, 1);
		addHoveredButton(29405, 85, 25, 25, 29406);
		addHoverButton(29410, 351, 15, 15, "Refresh", -1, 29411, 1);
		addHoveredButton(29411, 352, 15, 15, 29412);				
		addHoverText(29413, "", "", vencillio, 0, 0x47B320, false, true, 50);
		tab.scrollMax = 0;
		tab.totalChildren(11);
		tab.child(0, 29401, -4, 34);
		tab.child(1, 29402, -0, 34);
		tab.child(2, 29402, -0, 229);
		tab.child(3, 29403, 92, 9);
		tab.child(4, 29404, 160, 4);
		tab.child(5, 29405, 160, 4);
		tab.child(6, 29407, 92, 239);
		tab.child(7, 29500, 10, 36);
		tab.child(8, 29410, 160, 36);
		tab.child(9, 29411, 160, 36);
		tab.child(10, 29413, 8, 22);
		RSInterface scrollInterface = addTabInterface(29500);
		scrollInterface.parentID = 29400;
		scrollInterface.scrollPosition = 0;
		scrollInterface.atActionType = 0;
		scrollInterface.contentType = 0;
		scrollInterface.width = 165;
		scrollInterface.height = 193;
		scrollInterface.scrollMax = 330;
		int x = 7, y = 9;
		int amountOfLines = 28;
		scrollInterface.totalChildren(amountOfLines);
		for (int i = 0; i < amountOfLines; i++) {
			addHoverText(29501 + i, "", "", vencillio, 0, 0x47B320, false, true, 168);
			scrollInterface.child(i, 29501 + i, x, y);
			y += 18;
		}
	}
	
}