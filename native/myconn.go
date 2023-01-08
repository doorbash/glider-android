package gliderandroid

import (
	"net"
	"time"
)

type MyConnI interface {
	Read(b []byte) (n int, err error)
	ReadOffLen(b []byte, off int, len int) (n int, err error)
	Write(b []byte) (n int, err error)
	WriteOffLen(b []byte, off int, len int) (n int, err error)
	Close() error
	LocalAddr() string
	RemoteAddr() string
	SetTimeout(timeout int) error
	SetReadTimeout(timeout int) error
	SetWriteTimeout(timeout int) error
}

type MyConn struct {
	conn net.Conn
}

func (m *MyConn) Read(b []byte) (n int, err error) {
	return m.conn.Read(b)
}

func (m *MyConn) ReadOffLen(b []byte, off int, len int) (n int, err error) {
	return m.conn.Read(b[off : off+len])
}

func (m *MyConn) Write(b []byte) (n int, err error) {
	return m.conn.Write(b)
}

func (m *MyConn) WriteOffLen(b []byte, off int, len int) (n int, err error) {
	return m.conn.Write(b[off : off+len])
}

func (m *MyConn) Close() error {
	return m.conn.Close()
}

func (m *MyConn) LocalAddr() string {
	return m.conn.LocalAddr().String()
}

func (m *MyConn) RemoteAddr() string {
	return m.conn.RemoteAddr().String()
}

func (m *MyConn) SetTimeout(timeout int) error {
	return m.conn.SetDeadline(time.Now().Add(time.Duration(timeout) * time.Millisecond))
}

func (m *MyConn) SetReadTimeout(timeout int) error {
	return m.conn.SetReadDeadline(time.Now().Add(time.Duration(timeout) * time.Millisecond))
}
func (m *MyConn) SetWriteTimeout(timeout int) error {
	return m.conn.SetWriteDeadline(time.Now().Add(time.Duration(timeout) * time.Millisecond))
}

func NewMyConn(conn net.Conn) MyConnI {
	return &MyConn{conn}
}
