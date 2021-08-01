# APIJSON测试 - Python

### 1.下载后解压APIJSON-Demo工程

[打开APIJSON-Demo的GitHub主页](https://github.com/APIJSON/APIJSON-Demo) &gt; Clone or download &gt; [Download ZIP](https://github.com/APIJSON/APIJSON-Demo/archive/master.zip) &gt; 解压到一个路径并记住这个路径。

<br />

### 2.运行Python脚本

#### Windows

假设解压路径为 D:\Downloads <br />

打开 CMD命令行窗口 <br />
cd D:\Downloads\APIJSON-Demo-master\APIJSON-Python<br />
python test.py<br />

如果失败，一般是因为 "python 不是内部或外部命令"，则执行： <br />
set PATH=%PATH%;C:\Python27<br />
其中，Python27对应2.7版，如果是其它版本请修改下数字，例如3.6版就改为Python36。 <br />

然后重新执行： <br />
python test.py<br />

#### Linux 或 Mac OS

假设解压路径为 /Users/Tommy/Downloads <br />

打开 Terminal终端窗口，然后分别执行： <br />
cd /Users/Tommy/Downloads/APIJSON-Demo-master/APIJSON-Python<br />
python test.py<br />

<br />

### 3.测试接口<h3/>

看 命令行控制台。<br />
如果默认url不可用，修改为一个可用的，比如正在运行APIJSON后端工程的电脑的IPV4地址，然后重新运行。

也可以直接使用 [APIAuto-机器学习 HTTP 接口工具](http://apijson.cn/api) 或 Postman 等其它 HTTP 接口工具，格式为 HTTP POST JSON，具体示例参考通用文档 <br />
https://github.com/Tencent/APIJSON/blob/master/Document.md
<br />
