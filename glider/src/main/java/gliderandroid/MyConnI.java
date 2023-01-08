// Code generated by gobind. DO NOT EDIT.

// Java class gliderandroid.MyConnI is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java github.com/doorbash/glider-android
package gliderandroid;

import go.Seq;

public interface MyConnI {
	public void close() throws Exception;
	public String localAddr();
	public long read(byte[] b) throws Exception;
	public long readOffLen(byte[] b, long off, long len) throws Exception;
	public String remoteAddr();
	public void setReadTimeout(long timeout) throws Exception;
	public void setTimeout(long timeout) throws Exception;
	public void setWriteTimeout(long timeout) throws Exception;
	public long write(byte[] b) throws Exception;
	public long writeOffLen(byte[] b, long off, long len) throws Exception;
	
}

