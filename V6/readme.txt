此版本开始完成响应客户端第一个页面的操作
1.页面的构成，html的基础语法
2.HTTP相应的内容

首先创立一个页面
实现：
1.在项目目录下新建一个目录webapps
  这个目录用于存放当前服务器所有部署的网路应用，每个网络应用一个独立的子目录放在webapps下，
  并且子目录的名字就是该网络应用的名字（tomcat也是这样管理的）
2.在webapps下新建一个子目录：myweb,作为我们第一个网路应用（网站内容）
3.在myweb下新建一个首页：index.html

在ClientHandler中将一个页面以标准的HTTP响应格式发送给浏览器
实现：
在ClientHandler第三步响应客户端环节，先将固定的的一个页面webapps/myweb/index.html发送给浏览器去显示