一、关于搭建平台基本环境
	1).普通maven项目
		1.新建maven项目，选择maven-archetype-webapp；
		2.main文件夹下新建java和resources，并将Java设置为源文件夹，resources设置为配置文件夹;
	    3.标准maven项目目录结构
		src
		  -main
		      –java java源代码文件
				-com.xyc	业务代码
				-org.framework	核心代码库
		      –resources 资源库，会自动复制到classes目录里
		      –webapp web应用的目录。WEB-INF、css、js等
		  -test
		      –java 单元测试java源代码文件
		      –resources 测试需要用的资源库
		  -documents （一些文档）
		target
		README.txt Project’s readme
	    工程根目录下就只有src和target两个目录
	    target是有存放项目构建后的文件和目录，jar包、war包、编译的class文件等。
	    target里的所有内容都是maven构建的时候生成的
	2).使用spring initializr创建SpringBoot项目
二、相关文件说明
    web.xml:对项目的整体进行配置，项目上下文请求的监听，以及核心过滤
三、关于技术点说明
	3.1分层思想
    		dao(持久层)：mapper.xml/mapper
    		model(数据模型层)：实体类
    		controller:Action
    		service:Service/impl
四、关于内部核心工具类
	1.成型后应打成jar包，禁止后期私自修改；
	2.内部核心工具类均已测试成功，可使用；
	3.若出现经常使用且未整理到核心工具类的函数，可经审核后进入核心工具类。
五、快捷键
	1.Alt+Enter：创建该接口的实现类/实现未实现的方法
	2.【精准搜索】
		ctrl+f	页面内查找
		ctrl+shift+t	查找方法
		ctrl+shift+r	查找文件
		ctrl+alt+s	打开设置
	3.【重构】
		F2：修改名称/重构变量
		Alt+Enter:指定函数修改参数后可使用此快捷键重构关联函数
	4.【其他】
		ctrl + O 重写父类方法