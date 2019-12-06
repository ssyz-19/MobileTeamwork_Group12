# MobileTeamwork_Group12
19年软微秋学期移动端开发课程小组项目
## Android端
- 创建应用 12.3
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
  只完成了页面设计，具体的页面挑战还没有实现
- 添加就诊人界面 12.6   
  1.创建碎片布局文件
  2.创建相应的碎片


