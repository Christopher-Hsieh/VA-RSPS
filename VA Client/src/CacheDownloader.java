import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

@SuppressWarnings("all")
public class CacheDownloader implements Runnable {

	public static final String ZIP_URL = "http://www.vencillio.com/downloads/VencillioCache.zip";
	public static final String VERSION_URL = "http://www.vencillio.com/downloads/cacheVersion.txt";
	public static final String VERSION_FILE = ClientConstants.CACHE_LOCATION + "cacheVersion.dat";
	private Client client;
	private Client frame;

	public CacheDownloader(Client client) {
		this.client = client;
	}


	public double getCurrentVersion() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(VERSION_FILE)));
			return Double.parseDouble(br.readLine());
		} catch (Exception e) {
			return 0.1;
		}
	}

	public double getNewestVersion() {
		try {
			URL tmp = new URL(VERSION_URL);
			BufferedReader br = new BufferedReader(new InputStreamReader(tmp.openStream()));
			return Double.parseDouble(br.readLine());
		} catch (Exception e) {
			handleException(e);
			return -1;
		}
	}

	private void handleException(Exception e) {
		StringBuilder strBuff = new StringBuilder();
		strBuff.append("Something went wrong downloading your cache!\r\n");
		strBuff.append("Please copy the error code and contact us via forums for assistantce.\r\n");
		strBuff.append("http://www.vencillio.com\r\n\r\n");		
		strBuff.append("Error Code: [" + e.getClass().getSimpleName() + "]");
		alert("Vencillio", strBuff.toString(), true);
		int option = JOptionPane.showConfirmDialog(null, "Would you like to visit our forums?", "Vencillio Error", JOptionPane.YES_NO_OPTION);
		if (option == 0) {
			client.openURL("http://www.vencillio.com");
		} else {
			System.exit(0);
		}
	}

	private void alert(String msg) {
		alert("Message", msg, false);
	}

	private void alert(String title, String msg, boolean error) {
		JOptionPane.showMessageDialog(null, msg, title, (error ? JOptionPane.ERROR_MESSAGE : JOptionPane.PLAIN_MESSAGE));
	}

	@Override
	public void run() {
		client.drawLoadingText(0, "Checking Versions");
		try {
			double newest = getNewestVersion();
			if (newest > this.getCurrentVersion()) {
				client.drawLoadingText(0, "Update found!");
				
				StringBuilder strBuff = new StringBuilder();
				strBuff.append("Update version " + newest + " has been found!\n");
				strBuff.append("Client will now automatically update.");
				alert("Vencillio", strBuff.toString(), true);

				new ProgressBar();
				updateClient();
				
				client.drawLoadingText(0, "Vencillio has been updated!");
				alert("Vencillio", "Download finished! Restart the Client to start playing!", false);
				OutputStream out = new FileOutputStream(VERSION_FILE);
				out.write(String.valueOf(newest).getBytes());
				Runtime.getRuntime().exec("java -jar myApp.jar");
				System.exit(0);
				} else {

			}
		} catch (Exception e) {
			handleException(e);
		}
	}

	private void updateClient() {
		File clientZip = downloadClient();
		if (clientZip != null) {
			unZip(clientZip);
		}
	}

	private void unZip(File clientZip) {
		try {
			unZipFile(clientZip, new File(ClientConstants.CACHE_LOCATION));
			clientZip.delete();
		} catch (IOException e) {
			handleException(e);
		}
	}

	private void unZipFile(File zipFile, File outFile) throws IOException {
		ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
		ZipEntry e;
		long max = 0;
		long curr = 0;
		while ((e = zin.getNextEntry()) != null)
			max += e.getSize();
		zin.close();
		ZipInputStream in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
		while ((e = in.getNextEntry()) != null) {
			if (e.isDirectory())
				new File(outFile, e.getName()).mkdirs();
			else {
				FileOutputStream out = new FileOutputStream(new File(outFile, e.getName()));
				byte[] b = new byte[1024];
				int len;
				while ((len = in.read(b, 0, b.length)) > -1) {
					curr += len;
					out.write(b, 0, len);
					setUnzipPercent((int) ((curr * 100) / max));
				}
				out.flush();
				out.close();
			}
		}
	}

	public int percent = 0;
	

	public void setDownloadPercent(int amount) {
		percent = amount;
		ProgressBar.updateValue(amount);
		ProgressBar.updateString("(1/2) Downloading cache - " + ProgressBar.getValue() + "%");
		client.drawLoadingText(amount, "(1/2) Downloading VencillioCache" + " - " + amount + "%");

	}

	public int percent2 = 0;

	public void setUnzipPercent(int amount2) {
		percent2 = amount2;
		ProgressBar.updateValue(amount2);
		ProgressBar.updateString("(2/2) Extracting cache - " + ProgressBar.getValue() + "%");
		client.drawLoadingText(amount2, "(2/2) Extracting VencillioCache" + " - " + amount2 + "%");
	}

	private File downloadClient() {
		File ret = new File(ClientConstants.CACHE_LOCATION + "cache.zip");
		try {
			OutputStream out = new FileOutputStream(ret);
			URLConnection conn = new URL(ZIP_URL).openConnection();
			InputStream in = conn.getInputStream();
			long max = conn.getContentLength();
			long curr = 0;
			byte[] b = new byte[1024];
			int len;
			while ((len = in.read(b, 0, b.length)) > -1) {
				out.write(b, 0, len);
				curr += len;
				setDownloadPercent((int) ((curr * 100) / max));
			}
			out.flush();
			out.close();
			in.close();
			return ret;
		} catch (Exception e) {
			handleException(e);
			ret.delete();
			return null;
		}
	}
}