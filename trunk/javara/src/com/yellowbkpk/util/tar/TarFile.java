package com.yellowbkpk.util.tar;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

class TarFile {
	/** True after we've done the expensive read. */
	protected boolean read = false;

	/** The list of entries found in the archive */
	protected Vector<TarEntry> list;

	/** Size of header block. */
	public static final int RECORDSIZE = 512;

	/** Size of each block, in records */
	protected int blocking;

	/** Size of each block, in bytes */
	protected int blocksize;
	
	private CounterInputStream inputStream;

	/** Construct (open) a Tar file by name */
	public TarFile(InputStream is) {
		inputStream = new CounterInputStream(is);
		list = new Vector<TarEntry>();
	}

	/**
	 * Read the Tar archive in its entirety. This is semi-lazy evaluation, in
	 * that we don't read the file until we need to. A future revision may use
	 * even lazier evaluation: in getEntry, scan the list and, if not found,
	 * continue reading! For now, just read the whole file.
	 */
	protected void readFile() throws IOException {
		TarEntry hdr;
		try {
			do {
				hdr = new TarEntry(inputStream);
				if (hdr.getSize() < 0) {
					System.out.println("Size < 0");
					break;
				}
				// System.out.println(hdr.toString());
				list.addElement(hdr);
				// Get the size of the entry
				int nbytes = hdr.getSize(), diff;
				// Round it up to blocksize.
				if ((diff = (nbytes % RECORDSIZE)) != 0) {
					nbytes += RECORDSIZE - diff;
				}
				// And skip over the data portion.
				// System.out.println("Skipping " + nbytes + " bytes");
				inputStream.skip(nbytes);
			} while (true);
		} catch (EOFException e) {
			// OK, just stop reading.
		}
		// All done, say we've read the contents.
		read = true;
	}

	/** Close the Tar file. */
	public void close() {
		try {
			inputStream.close();
		} catch (IOException e) {
			// nothing to do
		}
	}

	/** Returns an enumeration of the Tar file entries. */
	public Enumeration<TarEntry> entries() throws IOException {
		if (!read) {
			readFile();
		}
		return list.elements();
	}

}
