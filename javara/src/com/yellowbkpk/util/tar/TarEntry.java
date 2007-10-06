package com.yellowbkpk.util.tar;

import java.io.EOFException;
import java.io.IOException;

public class TarEntry {
	/** Where in the tar archive this entry's HEADER is found. */
	private long fileOffset = 0;

	/** The maximum size of a name */
	private static final int NAMSIZ = 100;

	private static final int TUNMLEN = 32;

	private static final int TGNMLEN = 32;

	// Next fourteen fields constitute one physical record.
	// Padded to TarFile.RECORDSIZE bytes on tape/disk.
	// Lazy Evaluation: just read fields in raw form, only format when asked.

	/** File name */
	private byte[] name = new byte[NAMSIZ];

	/** permissions, e.g., rwxr-xr-x? */
	private byte[] mode = new byte[8];

	/** user */
	private byte[] uid = new byte[8];

	/** group */
	private byte[] gid = new byte[8];

	/** size */
	private byte[] size = new byte[12];

	/** UNIX modification time */
	private byte[] mtime = new byte[12];

	/** checksum field */
	private byte[] chksum = new byte[8];

	/** file type */
	private byte[] type = new byte[1];

	/** file link name */
	private byte[] linkName = new byte[NAMSIZ];

	/** magic bytes */
	private byte[] magic = new byte[8];

	/** user name */
	private byte[] uname = new byte[TUNMLEN];

	/** group name */
	private byte[] gname = new byte[TGNMLEN];

	/** dev major */
	private byte[] devmajor = new byte[8];

	/** dev minor */
	private byte[] devminor = new byte[8];

	// End of the physical data fields.

	/** The magic field is filled with this if uname and gname are valid. */
	private static final byte TMAGIC[] = {
	// 'u', 's', 't', 'a', 'r', ' ', ' ', '\0'
			0, 0, 0, 0, 0, 0, 0x20, 0x20, 0 }; /* 7 chars and a null */

	/** Type value for Normal file, Unix compatibility */
	private static final int LF_OLDNORMAL = '\0';

	/** Type value for Normal file */
	private static final int LF_NORMAL = '0';

	/** Type value for Link to previously dumped file */
	private static final int LF_LINK = '1';

	/** Type value for Symbolic link */
	private static final int LF_SYMLINK = '2';

	/** Type value for Character special file */
	private static final int LF_CHR = '3';

	/** Type value for Block special file */
	private static final int LF_BLK = '4';

	/** Type value for Directory */
	private static final int LF_DIR = '5';

	/** Type value for FIFO special file */
	private static final int LF_FIFO = '6';

	/** Type value for Contiguous file */
	private static final int LF_CONTIG = '7';

	/** Constructor that reads the entry's header. */
	public TarEntry(CounterInputStream is) throws IOException {

		fileOffset = is.getCounter();

		// read() returns -1 at EOF
		if (is.read(name) < 0)
			throw new EOFException();
		// Tar pads to block boundary with nulls.
		if (name[0] == '\0')
			throw new EOFException();
		// OK, read remaining fields.
		is.read(mode);
		is.read(uid);
		is.read(gid);
		is.read(size);
		is.read(mtime);
		is.read(chksum);
		is.read(type);
		is.read(linkName);
		is.read(magic);
		is.read(uname);
		is.read(gname);
		is.read(devmajor);
		is.read(devminor);

		// Since the tar header is < 512, we need to skip it.
		is.skip((int) (TarFile.RECORDSIZE - (is.getCounter() % TarFile.RECORDSIZE)));

		// TODO if checksum() fails,
		// throw new TarException("Failed to find next header");

	}

	/** Returns the name of the file this entry represents. */
	public String getName() {
		return new String(name).trim();
	}

	public String getTypeName() {
		switch (type[0]) {
		case LF_OLDNORMAL:
		case LF_NORMAL:
			return "file";
		case LF_LINK:
			return "link w/in archive";
		case LF_SYMLINK:
			return "symlink";
		case LF_CHR:
		case LF_BLK:
		case LF_FIFO:
			return "special file";
		case LF_DIR:
			return "directory";
		case LF_CONTIG:
			return "contig";
		default:
			throw new IllegalStateException("TarEntry.getTypeName: type "
					+ type + " invalid");
		}
	}

	/** Returns the UNIX-specific "mode" (type+permissions) of the entry */
	public int getMode() {
		try {
			return Integer.parseInt(new String(mode).trim(), 8) & 0777;
		} catch (IllegalArgumentException e) {
			return 0;
		}
	}

	/** Returns the size of the entry */
	public int getSize() {
		try {
			return Integer.parseInt(new String(size).trim(), 8);
		} catch (IllegalArgumentException e) {
			return 0;
		}
	}

	/**
	 * Returns the name of the file this entry is a link to, or null if this
	 * entry is not a link.
	 */
	public String getLinkName() {
		// if (isLink())
		// return null;
		return new String(linkName).trim();
	}

	/** Returns the modification time of the entry */
	public long getTime() {
		try {
			return Long.parseLong(new String(mtime).trim(), 8);
		} catch (IllegalArgumentException e) {
			return 0;
		}
	}

	/** Returns the string name of the userid */
	public String getUname() {
		return new String(uname).trim();
	}

	/** Returns the string name of the group id */
	public String getGname() {
		return new String(gname).trim();
	}

	/** Returns the numeric userid of the entry */
	public int getuid() {
		try {
			return Integer.parseInt(new String(uid).trim());
		} catch (IllegalArgumentException e) {
			return -1;
		}
	}

	/** Returns the numeric gid of the entry */
	public int getgid() {
		try {
			return Integer.parseInt(new String(gid).trim());
		} catch (IllegalArgumentException e) {
			return -1;
		}
	}

	/** Returns true if this entry represents a file */
	boolean isFile() {
		return type[0] == LF_NORMAL || type[0] == LF_OLDNORMAL;
	}

	/** Returns true if this entry represents a directory */
	boolean isDirectory() {
		return type[0] == LF_DIR;
	}

	/** Returns true if this a hard link (to a file in the archive) */
	boolean isLink() {
		return type[0] == LF_LINK;
	}

	/** Returns true if this a symbolic link */
	boolean isSymLink() {
		return type[0] == LF_SYMLINK;
	}

	/** Returns true if this entry represents some type of UNIX special file */
	boolean isSpecial() {
		return type[0] == LF_CHR || type[0] == LF_BLK || type[0] == LF_FIFO;
	}

	public String toString() {
		return "TarEntry[" + getName() + ']';
	}
}
