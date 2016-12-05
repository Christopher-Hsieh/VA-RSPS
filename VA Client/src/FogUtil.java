import java.awt.Color;

/**
 * 
 * @author Zion
 *
 */
public class FogUtil {

	public static int mix(int color, int fogColor, float factor) {
		if (factor >= 1f) {
			return fogColor;
		}
		if (factor <= 0f) {
			return color;
		}
		int aR = (color >> 16) & 0xFF;
		int aG = (color >> 8) & 0xFF;
		int aB = (color) & 0xFF;

		int bR = (fogColor >> 16) & 0xFF;
		int bG = (fogColor >> 8) & 0xFF;
		int bB = (fogColor) & 0xFF;
		
		int dR = bR - aR;
		int dG = bG - aG;
		int dB = bB - aB;
		
		int nR = (int) (aR + (dR * factor));
		int nG = (int) (aG + (dG * factor));
		int nB = (int) (aB + (dB * factor));
		return (nR << 16) + (nG << 8) + nB;
	}

	public void depthFilter(int[] colorBuffer, float[] depthBuffer) {
		for(int index = 0; index < colorBuffer.length; index++) {
			float buffer = depthBuffer[index] / 3500f;
			if (buffer > 1f) {
				buffer = 1f;
			}
			if (buffer < 0f) {
				buffer = 0f;
			}
			int rgb = new Color(buffer, buffer, buffer).getRGB();
			colorBuffer[index] = rgb;
		}
	}
}