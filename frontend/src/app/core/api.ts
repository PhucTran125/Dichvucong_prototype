// Empty base → same-origin requests. Angular dev-server proxies /api and
// /h2-console to http://localhost:8080 (see proxy.conf.json), so this works
// identically on localhost, LAN IP, and tunnels like ngrok.
export const API_BASE = '';
