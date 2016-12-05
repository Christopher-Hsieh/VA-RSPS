import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class CreateUID {

	public static String getAppleSN() {
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", "ioreg -l | awk '/IOPlatformSerialNumber/ { print $4;}'");
		pb.redirectErrorStream(true);
		String result = "";
		try {
			Process p = pb.start();
			String s;
			BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((s = stdout.readLine()) != null) {
				result = s.replaceAll("\"", "");
			}
			p.getInputStream().close();
			p.getOutputStream().close();
			p.getErrorStream().close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result.trim();
	}
	
	public static String getWindowsSN() {
		String result = "";
		
		try {
			File file = File.createTempFile("vencillio", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new FileWriter(file);

			String vbs =
			  "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
			+ "Set colItems = objWMIService.ExecQuery _ \n"
			+ "   (\"Select * from Win32_BaseBoard\") \n"
			+ "For Each objItem in colItems \n"
			+ "    Wscript.Echo objItem.SerialNumber \n"
			+ "    exit for  ' do the first cpu only! \n"
			+ "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.trim();
	}

	public static String generateUID() {
		String os = System.getProperty("os.name").toLowerCase();
		String serial = "Unknown OS - " + os + " " + System.currentTimeMillis();
		
		try {
			String pending = "";
			if (os.startsWith("windows")) {
				pending = getWindowsSN();
			} else if (os.startsWith("mac")) {
				pending = getAppleSN();
			}
			
			serial = pending;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return serial;
	}
}
