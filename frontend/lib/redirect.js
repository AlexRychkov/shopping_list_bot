export default function redirect(res, url, token) {
  res.writeHead(301, {Location: url, "Authorization": token})
  res.end()
}
