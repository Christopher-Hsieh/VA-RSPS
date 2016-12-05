public final class Class18 {

    public final int[] anIntArray342;
    public final int[][] anIntArrayArray343;
    public final int length;

	public Class18(Stream buffer) {
		length = buffer.readUnsignedWord();
		anIntArray342 = new int[length];
		anIntArrayArray343 = new int[length][];
		for (int j = 0; j < length; j++) {
			anIntArray342[j] = buffer.readUnsignedWord();
		}
		for (int j = 0; j < length; j++) {
			anIntArrayArray343[j] = new int[buffer.readUnsignedWord()];
		}
		for (int j = 0; j < length; j++) {
			for (int l = 0; l < anIntArrayArray343[j].length; l++) {
				anIntArrayArray343[j][l] = buffer.readUnsignedWord();
			}
		}
	}
}