# MobileTeamwork_Group12
19年软微秋学期移动端开发课程小组项目
## Android端
- 底部导航栏(**done**) 12.4    
&emsp;*1.如何隐藏顶部导航栏？*   
&emsp;在AndroidManifest.xml下设置theme属性:   
&emsp;<code>android:theme="@style/Theme.AppCompat.NoActionBar"</code>   
&emsp;*2.底部导航栏实现思路*   
&emsp;&emsp;4个TxtView放在底部，然后中间空出一部分用于放碎片。   
&emsp;*3.动态加载Fragment的流程*   
&emsp;&emsp;(1)通过getSupportFragmentManager()获得FragmentManager对象；  
&emsp;&emsp;(2)获得FragmentTransaction对象,FragmentManager调用beginTransaction()方法;   
&emsp;&emsp;(3)调用FragmentTransaction的add或replace方法加载Fragment;示例：add(要传入的容器，fragment对象)     
&emsp;&emsp;(4)在前面的基础上调用FragmentTransaction的commit()方法提交事务，或者remove()方法等。  

- 个人中心界面  12.5   
  只完成了页面设计，具体的页面跳转还没有实现    
  1.实现关于我们页面的跳转（**ing**） 12.9  
  &emsp;1.1添加listView的监听事件，点击“关于我们”可以查看app相关介绍  
  &emsp;1.2创建新的活动SenconActivity（as自动完成注册）和相应的布局文件
  &emsp;1.3创建“关于我们”的碎片布局，并加载到上述活动中。   
  &emsp;**错误：** 碎片布局对应的页面不显示。 **错误原因：** 没有调用commit()方法   
  &emsp;1.4解决TextView如何显示大段文字的问题
- 添加就诊人界面 12.6   
  1.创建碎片布局(适当修改和个人中心的页面布局)  
  2.创建相应的碎片  
  3.未完成部分：就诊人信息列表的item还没有设计。
- 挂号记录界面 12.6     
  1.创建碎片布局    
  2.创建相应的碎片  
  3.未完成部分：就诊人信息列表的item还没有设计。    
- 添加全局变量Utils/GlobalVar.java 12.7     
  包含：    
  1.用户登录状态（用于个人中心界面的登录和注销）
- 完善挂号预约的功能 12.7     
  1.创建碎片布局



