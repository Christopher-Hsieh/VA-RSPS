public final class SequenceFrame {

	public static SequenceFrame[][] animationlist;
	
	public static void load(int file, byte[] array) {
		   try {
               final Stream ay = new Stream(array);
               final Class18 b2 = new Class18(ay);
               final int n = ay.readUnsignedWord();;
               animationlist[file] = new SequenceFrame[n * 3];
               final int[] array2 = new int[500];
               final int[] array3 = new int[500];
               final int[] array4 = new int[500];
               final int[] array5 = new int[500];
               for (int j = 0; j < n; ++j) {
                   final int k = ay.readUnsignedWord();;
                   final SequenceFrame[] array6 = animationlist[file];
                   final int n2 = k;
                   final SequenceFrame q = new SequenceFrame();
                   array6[n2] = q;
                   final SequenceFrame q2 = q;
                   q.aClass18_637 = b2;
                   final int f = ay.readUnsignedByte();
                   int c2 = 0;
                   int n3 = -1;
                   for (int l = 0; l < f; ++l) {
                       final int f2;
                       if ((f2 = ay.readUnsignedByte()) > 0) {
                           if (b2.anIntArray342[l] != 0) {
                               for (int n4 = l - 1; n4 > n3; --n4) {
                                   if (b2.anIntArray342[n4] == 0) {
                                       array2[c2] = n4;
                                       array3[c2] = 0;
                                       array5[c2] = (array4[c2] = 0);
                                       ++c2;
                                       break;
                                   }
                               }
                           }
                           array2[c2] = l;
                           int n4 = 0;
                           if (b2.anIntArray342[l] == 3) {
                               n4 = 128;
                           }
                           if ((f2 & 0x1) != 0x0) {
                               array3[c2] = ay.readShort2();
                           }
                           else {
                               array3[c2] = n4;
                           }
                           if ((f2 & 0x2) != 0x0) {
                               array4[c2] = ay.readShort2();
                           }
                           else {
                               array4[c2] = n4;
                           }
                           if ((f2 & 0x4) != 0x0) {
                               array5[c2] = ay.readShort2();
                           }
                           else {
                               array5[c2] = n4;
                           }
                           n3 = l;
                           ++c2;
                       }
                   }
                   q2.anInt638 = c2;
                   q2.anIntArray639 = new int[c2];
                   q2.anIntArray640 = new int[c2];
                   q2.anIntArray641 = new int[c2];
                   q2.anIntArray642 = new int[c2];
                   for (int l = 0; l < c2; ++l) {
                       q2.anIntArray639[l] = array2[l];
                       q2.anIntArray640[l] = array3[l];
                       q2.anIntArray641[l] = array4[l];
                       q2.anIntArray642[l] = array5[l];
                   }
               }
           }
           catch (Exception ex) {}
	}

	public static void loader(int file, byte[] abyte0) {
		try {
			Stream stream = new Stream(abyte0);
			Class18 class18 = new Class18(stream);
			int k1 = stream.readUnsignedWord();
			animationlist[file] = new SequenceFrame[k1 * 3];
			int ai[] = new int[500];
			int ai1[] = new int[500];
			int ai2[] = new int[500];
			int ai3[] = new int[500];
			for (int l1 = 0; l1 < k1; l1++) {
				int i2 = stream.readUnsignedWord();
				SequenceFrame class36 = animationlist[file][i2] = new SequenceFrame();
				class36.aClass18_637 = class18;
				class36.aClass18_637 = class18;
				
				int j2 = stream.readUnsignedByte();
				int l2 = 0;
				int k2 = -1;
				for (int i3 = 0; i3 < j2; i3++) {
					int j3 = stream.readUnsignedByte();
					if (j3 > 0) {
						if (class18.anIntArray342[i3] != 0) {
							for (int l3 = i3 - 1; l3 > k2; l3--) {
								if (class18.anIntArray342[l3] != 0)
									continue;
								ai[l2] = l3;
								ai1[l2] = 0;
								ai2[l2] = 0;
								ai3[l2] = 0;
								l2++;
								break;
							}
						}
						ai[l2] = i3;
						short c = 0;
						if (class18.anIntArray342[i3] == 3)
							c = (short) 128;

						if ((j3 & 1) != 0)
							ai1[l2] = stream.readShort2();
						else
							ai1[l2] = c;
						if ((j3 & 2) != 0)
							ai2[l2] = stream.readShort2();
						else
							ai2[l2] = c;
						if ((j3 & 4) != 0)
							ai3[l2] = stream.readShort2();
						else
							ai3[l2] = c;
						k2 = i3;
						l2++;
					}
				}
				class36.anInt638 = l2;
				class36.anIntArray639 = new int[l2];
				class36.anIntArray640 = new int[l2];
				class36.anIntArray641 = new int[l2];
				class36.anIntArray642 = new int[l2];
				for (int k3 = 0; k3 < l2; k3++) {
					class36.anIntArray639[k3] = ai[k3];
					class36.anIntArray640[k3] = ai1[k3];
					class36.anIntArray641[k3] = ai2[k3];
					class36.anIntArray642[k3] = ai3[k3];
				}
			}
		} catch (Exception exception) {
		}
	}

	public static void nullLoader() {
		animationlist = null;
	}

	
	public static SequenceFrame method531(int int1) {
        try {
            final String hexString;
            final int int2 = Integer.parseInt((hexString = Integer.toHexString(int1)).substring(0, hexString.length() - 4), 16);
            int1 = Integer.parseInt(hexString.substring(hexString.length() - 4), 16);
            if (animationlist[int2].length == 0) {
            	Client.instance.onDemandFetcher.method558(1, int2);
                return null;
            }
            return animationlist[int2][int1];
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    

	public static boolean method532(int i) {
		return i == -1;
	}

	public SequenceFrame() {
	}

	public int anInt636;
	public Class18 aClass18_637;
	public int anInt638;
	public int anIntArray639[];
	public int anIntArray640[];
	public int anIntArray641[];
	public int anIntArray642[];

}
