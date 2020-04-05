const config = {
  backend: {
    baseUrl: '/backend'
  },
  graph: {
    serverUrl: `bolt://${window.location.hostname}:7687`
  }
}
export default config;