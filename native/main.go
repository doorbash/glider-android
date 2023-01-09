package gliderandroid

import (
	"context"
	"io"
	"net"
	"net/http"
	"strings"
	"time"

	"github.com/nadoo/glider/rule"
)

const version = "0.16.2"

func Dial(args string, addr string) (MyConnI, error) {
	config := parseConfig(strings.Fields(args))

	pxy := rule.NewProxy(config.Forwards, &config.Strategy, config.rules)

	for _, r := range config.rules {
		r.IP, r.CIDR, r.Domain = nil, nil, nil
	}

	conn, _, err := pxy.Dial("tcp", addr)
	if err != nil {
		return nil, err
	}

	return NewMyConn(conn), nil
}

func HttpGet(args string, url string, timeout int) (string, error) {
	config := parseConfig(strings.Fields(args))

	pxy := rule.NewProxy(config.Forwards, &config.Strategy, config.rules)

	transport := http.Transport{
		Dial: func(network string, addr string) (net.Conn, error) {
			conn, _, err := pxy.Dial("tcp", addr)
			if err != nil {
				return nil, err
			}
			return conn, nil
		},
	}

	client := http.Client{
		Transport: &transport,
		Timeout:   time.Duration(timeout) * time.Second,
	}

	resp, err := client.Get(url)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()

	b, err := io.ReadAll(resp.Body)

	if err != nil {
		return "", err
	}

	return string(b), nil
}

func Resolve(args string, domain string, addr string, port int) (*StringArray, error) {
	config := parseConfig(strings.Fields(args))

	p := rule.NewProxy(config.Forwards, &config.Strategy, config.rules)

	for _, r := range config.rules {
		r.IP, r.CIDR, r.Domain = nil, nil, nil
	}

	resolver := net.Resolver{
		PreferGo: true,
		Dial: func(ctx context.Context, network, address string) (net.Conn, error) {
			// fmt.Println(network, address)
			switch network {
			case "udp", "udp4", "udp6":
				udpaddr := &net.UDPAddr{
					IP:   net.ParseIP(addr),
					Port: port,
				}
				rc, _, err := p.DialUDP("udp", address)
				if err != nil {
					return nil, err
				}
				return &MyUDPConn{rc, udpaddr}, nil
			default:
				return nil, &net.AddrError{Err: "unknown network", Addr: network}
			}
		},
	}

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	result, err := resolver.LookupHost(ctx, domain)
	if err != nil {
		return nil, err
	}
	return &StringArray{items: result}, nil
}
