module.exports = {
  configureWebpack: {
    devServer: {
      allowedHosts: [
        'remote-code.woldseth.xyz'
      ],
      headers: { 'Access-Control-Allow-Origin': '*' }
    }
  }
}
