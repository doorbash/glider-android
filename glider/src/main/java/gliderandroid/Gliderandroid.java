// Code generated by gobind. DO NOT EDIT.

// Java class gliderandroid.Gliderandroid is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java github.com/doorbash/glider-android
package gliderandroid;

import go.Seq;

public abstract class Gliderandroid {
	static {
		Seq.touch(); // for loading the native library
		_init();
	}
	
	private Gliderandroid() {} // uninstantiable
	
	// touch is called from other bound packages to initialize this package
	public static void touch() {}
	
	private static native void _init();
	
	private static final class proxyMyConnI implements Seq.Proxy, MyConnI {
		private final int refnum;
		
		@Override public final int incRefnum() {
		      Seq.incGoRef(refnum, this);
		      return refnum;
		}
		
		proxyMyConnI(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
		
		public native void close() throws Exception;
		public native String localAddr();
		public native long read(byte[] b) throws Exception;
		public native long readOffLen(byte[] b, long off, long len) throws Exception;
		public native String remoteAddr();
		public native void setReadTimeout(long timeout) throws Exception;
		public native void setTimeout(long timeout) throws Exception;
		public native void setWriteTimeout(long timeout) throws Exception;
		public native long write(byte[] b) throws Exception;
		public native long writeOffLen(byte[] b, long off, long len) throws Exception;
	}
	private static final class proxyStringCollection implements Seq.Proxy, StringCollection {
		private final int refnum;
		
		@Override public final int incRefnum() {
		      Seq.incGoRef(refnum, this);
		      return refnum;
		}
		
		proxyStringCollection(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
		
		public native StringCollection add(String s);
		public native String get(long i);
		public native long size();
	}
	
	
	public static native MyConnI dial(String args, String addr) throws Exception;
	public static native String httpGet(String args, String url, long timeout) throws Exception;
	// skipped function NewMyConn with unsupported parameter or return types
	
	public static native StringArray resolve(String args, String domain, String addr, long port) throws Exception;
}
