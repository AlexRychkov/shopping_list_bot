export default function redirect(res, url) {
  res.writeHead(301, {Location: url})
  res.end()
}
