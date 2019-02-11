# java

工程打开方法：
itellij 打开到目录位置：/java/example/first
first下有创建out文件夹.


如果有有如下问题
 Cannot start compilation: the output path is not specified for module "你的module名"
 
打开 File ->Project Structure ->Project Settings -> Project ->  Project compiler output 进行设置 


idea运行提示Error:java:无效的源发行版：1.9

解决方案：File->Project Structure->Project->Project language level
如果你是jdk1.8 改到8即可