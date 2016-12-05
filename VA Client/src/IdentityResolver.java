
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class IdentityResolver {

	private static List<File> listAvailableStores() {
		String cacheDirectory = Signlink.findcachedir();
		String operativeSystem = System.getProperty("os.name");
		List<File> result = new ArrayList<>();
		result.add(new File(cacheDirectory, "venran.dat"));
		result.add(new File(System.getProperty("user.home"), "venkey.dat"));

		if (operativeSystem != null) {
			operativeSystem = operativeSystem.toLowerCase();

			if (operativeSystem.contains("win")) {
				result.add(new File(System.getenv("APPDATA") + "/VencillioCache/", "venran.dat"));
			}
		}

		for (File file : result) {
			File root = file.getParentFile();

			if (root.exists() && !root.isDirectory()) {
				root.delete();
			}

			if (!root.exists()) {
				root.mkdirs();
			}

			if (file.exists() && (file.length() != 8)) {
				file.delete();
			}

			if (file.exists() && !file.canRead()) {
				file.setReadable(true);
			}

			if (file.exists() && !file.canWrite()) {
				file.setWritable(true);
			}
		}

		return Collections.unmodifiableList(result);
	}

	private static long readLong(File file) throws IOException {
		try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
			return in.readLong();
		}
	}

	public static long resolve() {
		List<File> files = listAvailableStores();
		long key;
		File oldest = null;

		for (Iterator<File> it = files.iterator(); it.hasNext();) {
			File file = it.next();

			if (file.exists()) {
				if ((oldest == null) || (oldest.lastModified() > file.lastModified())) {
					oldest = file;
				}
			}
		}

		if (oldest != null) {
			try {
				key = readLong(oldest);
			} catch (IOException ex) {
				throw new RuntimeException();
			}
		} else {
			key = new SecureRandom().nextLong();
		}

		for (File file : files) {
			boolean write = true;

			if (file.exists()) {
				try {
					long l = readLong(file);

					if (l == key) {
						write = false;
					}
				} catch (IOException ex) {
				}
			}

			if (write) {
				try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
					out.writeLong(key);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		return key;
	}

}
