var http = require('http');

http.createServer(
  function (request, response)
  {
    response.writeHead(200, {'Content-Type':'text/plain'});

    var instance = new theClz('zxc');
    var str = instance.getString();

    console.log(str);
    response.write(str + '\n', 'UTF-8', null);

    response.end('Hello World\n');

  }
).listen(3000);


console.log('Server running at http://127.0.0.1:3000');


class theClz
{
  constructor(arg1)
  {
    this.params = {};
    this.params.name = arg1;
  }

  useConsole()
  {
    console.log('Hello ' + this.params.name + '\nCopyright (c) 2016 Copyright Holder All Rights Reserved.');
  }

  getString()
  {
    this.useConsole();
    return 'Hello ' + this.params.name;
  }
}
