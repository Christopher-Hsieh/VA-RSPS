final class Skills {

	public static final int SKILLS_COUNT = 25;
	
	public static final int ATTACK = 0;
	public static final int DEFENCE = 1;
	public static final int STRENGTH = 2;
	public static final int HITPOINTS = 3;
	public static final int RANGED = 4;
	public static final int PRAYER = 5;
	public static final int MAGIC = 6;
	public static final int COOKING = 7;
	public static final int WOODCUTTING = 8;
	public static final int FLETCHING = 9;
	public static final int FISHING = 10;
	public static final int FIREMAKING = 11;
	public static final int CRAFTING = 12;
	public static final int SMITHING = 13;
	public static final int MINING = 14;
	public static final int HERBLORE = 15;
	public static final int AGILITY = 16;
	public static final int THIEVING = 17;
	public static final int SLAYER = 18;
	public static final int FARMING = 19;
	public static final int RUNECRAFTING = 20;
	public static final int SUMMONING = 21;
	public static final int HUNTER = 22;
	public static final int CONSTRUCTION = 23;
	public static final int DUNGEONEERING = 24;

	public static final int[][] STAT_IDS = new int[][] { { 4004, 4005 } , { 4008, 4009 }, { 4006, 4007 }, { 4016, 4017 }, { 4010, 4011 }, { 4012, 4013 }, { 4014, 4015 }, { 4034, 4035 }, { 4038, 4039 }, { 4026, 4027 }, { 4032, 4033 }, { 4036, 4037 }, { 4024, 4025 }, { 4030, 4031 }, { 4028, 4029 }, { 4020, 4021 }, { 4018, 4019 }, { 4022, 4023 }, { 12166, 12167 }, { 13926, 13927 }, { 4152, 4153 }, { -1, -1 }, { 24134, 24135 }, { -1, -1 }, { -1, -1 } };

	public static final String[] SKILL_NAMES = { "attack", "defence", "strength", "hitpoints", "ranged", "prayer", "magic", "cooking", "woodcutting", "fletching", "fishing", "firemaking", "crafting", "smithing", "mining", "herblore", "agility", "thieving", "slayer", "farming", "runecrafting", "summoning", "hunter", "construction", "dungeoneering", "total level" };

	public static final boolean[] SKILL_ENABLED = new boolean[SKILLS_COUNT];

	public static final int[] EXP_FOR_LEVEL = new int[99];

	public static final int getIdByName(String skill) {
		for (int i = 0; i < SKILL_NAMES.length; i++) {
			if (SKILL_NAMES[i].equalsIgnoreCase(skill)) {
				return i;
			}
		}
		return 0;
	}

	static {
		int points = 0;
		for (int lvl = 1; lvl < 99; lvl++) {
			points += lvl + 300 * Math.pow(2, lvl / 7.0);
			EXP_FOR_LEVEL[lvl-1] = points / 4;
		}

		SKILL_ENABLED[ATTACK] = true;
		SKILL_ENABLED[DEFENCE] = true;
		SKILL_ENABLED[STRENGTH] = true;
		SKILL_ENABLED[HITPOINTS] = true;
		SKILL_ENABLED[RANGED] = true;
		SKILL_ENABLED[PRAYER] = true;
		SKILL_ENABLED[MAGIC] = true;
		SKILL_ENABLED[COOKING] = true;
		SKILL_ENABLED[WOODCUTTING] = true;
		SKILL_ENABLED[FLETCHING] = true;
		SKILL_ENABLED[FISHING] = true;
		SKILL_ENABLED[FIREMAKING] = true;
		SKILL_ENABLED[CRAFTING] = true;
		SKILL_ENABLED[SMITHING] = true;
		SKILL_ENABLED[MINING] = true;
		SKILL_ENABLED[HERBLORE] = true;
		SKILL_ENABLED[AGILITY] = true;
		SKILL_ENABLED[THIEVING] = true;
		SKILL_ENABLED[SLAYER] = true;
		SKILL_ENABLED[FARMING] = true;
		SKILL_ENABLED[RUNECRAFTING] = true;
		SKILL_ENABLED[HUNTER] = true;
		SKILL_ENABLED[SUMMONING] = false;
		SKILL_ENABLED[CONSTRUCTION] = false;
		SKILL_ENABLED[DUNGEONEERING] = false;
	}

	public static String getPrestigeColor(int prestige) {
		if (prestige == 1) {
			return "005EFF";
		} else if (prestige == 2) {
			return "336600";
		} else if (prestige == 3) {
			return "A300CC";
		} else if (prestige == 4) {
			return "E6E600";
		} else if (prestige == 5) {
			return "B80000";
		}
		return "FFFF00";
	}
}