package gliderandroid

import "net"

type MyUDPConn struct {
	net.PacketConn
	addr *net.UDPAddr
}

func (m *MyUDPConn) Read(b []byte) (n int, err error) {
	n, _, err = m.ReadFrom(b)
	return
}

func (m *MyUDPConn) Write(b []byte) (n int, err error) {
	return m.WriteTo(b, m.addr)
}

func (m *MyUDPConn) RemoteAddr() net.Addr {
	return m.addr
}
