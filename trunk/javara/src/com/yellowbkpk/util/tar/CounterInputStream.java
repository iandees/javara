package com.yellowbkpk.util.tar;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CounterInputStream extends FilterInputStream {

	private long counter;

	protected CounterInputStream(InputStream in) {
		super(in);
		counter = 0;
	}

	public int read(byte[] arg0, int arg1, int arg2) throws IOException {
		int read = super.read(arg0, arg1, arg2);
		System.out.println("Read " + read + " bytes.");
		counter += read;
		return read;
	}
	
	public int read() throws IOException {
		int read = super.read();
		System.out.println("Read " + read + " bytes.");
		counter += read;
		return read;
	}

	public long skip(long arg0) throws IOException {
		long skip = super.skip(arg0);
		System.out.println("Skipped " + skip + " bytes.");
		counter += skip;
		return skip;
	}

	public long getCounter() {
		return counter;
	}
	
}
