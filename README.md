# MobileTeamwork_Group12
19年软微秋学期移动端开发课程小组项目
## Android端
- 底部导航栏(**done**) 12.4    
    *1.如何隐藏顶部导航栏？*   
    &emsp;在AndroidManifest.xml下设置theme属性:   
    ```xml
        android:theme="@style/Theme.AppCompat.NoActionBar"
    ```
    *2.底部导航栏实现思路*   
    >4个TxtView放在底部，然后中间空出一部分用于放碎片。   
    
    *3.动态加载Fragment的流程*   
    &emsp;(1)通过getSupportFragmentManager()获得FragmentManager对象；  
    &emsp;(2)获得FragmentTransaction对象,FragmentManager调用beginTransaction()方法;   
    &emsp;(3)调用FragmentTransaction的add或replace方法加载Fragment;示例：add(要传入的容器，fragment对象)     
    &emsp;(4)在前面的基础上调用FragmentTransaction的commit()方法提交事务，或者remove()方法等。  

- 个人中心界面  12.5   
  只完成了页面设计，具体的页面跳转还没有实现    
  1. 实现关于我们页面的跳转（**ing**） 12.9  
      1.1添加listView的监听事件，点击“关于我们”可以查看app相关介绍  
      1.2创建新的活动SenconActivity（as自动完成注册）和相应的布局文件     
      1.3创建“关于我们”的碎片布局，并加载到上述活动中。   
      **错误：** 碎片布局对应的页面不显示。       
      **错误原因：** 没有调用commit()方法   
      1.4解决TextView如何显示大段文字的问题   
      1.5解决TextView首行缩进，文字对齐“\t\t\t\t”，一个\t代表一个英文字符     
      1.6解决TextView大段文字两端不对齐的问题，重新定义一个JustifyTextView类。    
      **问题：** v7包中TextView用不了，用androidx.appcompat.appcompat中的AppcompatTextView代替    
    
  2. 实现用户的登录功能 12.12-12.13     
      2.1创建登录界面布局文件     
      2.2创建登录界面对应的fragment
      2.3使用Okhttp post学号和密码
      2.4更改登录状态，回调fragment的onResume方法     
      2.5添加退出登录功能
    2.6登录界面增加返回按钮 12.23
    
      2.7修改了登录界面UI，以及部分导航栏UI
    
    ![image-20191225145536839](../../AppData/Roaming/Typora/typora-user-images/image-20191225145536839.png)
    
    
  
- 添加就诊人信息界面 12.6   
  1. 创建碎片布局(适当修改和个人中心的页面布局)  
  2. 创建相应的碎片  
  3. 未完成部分：就诊人信息列表的item还没有设计。
- 挂号记录界面 12.6     
  1. 创建碎片布局    
  2. 创建相应的碎片  
  3. 显示挂号记录功能完成，添加取消预约功能。12.23   
- 添加全局变量Utils/GlobalVar.java 12.7     
  包含：    global
  1. 用户登录状态（用于个人中心界面的登录和注销）
  2. 服务器地址
  3. 登录成功设置用户id
- 完善挂号预约的功能 12.7     
  1. 创建碎片布局
  2. 获取科室数据并返回列表 12.15   
      2.1添加获取科室数据的http请求   
      2.2Utility类中解析json格式数据 12.16-12.17
            **问题：网络请求时出现了问题**     
            原因：服务端开了多线程，具体原因不明。  
            今天只完成了少部分的解析    
      2.3添加数据库存放科室(litepal，**done**)  12.17
      2.4科室列表添加成功     
      **bug**为什么数据库中会有多个内容？     
      多次添加了数据库内容    
  3. 获取科室医生数据 12.18  
      是否需要存储在数据库中？先存着再说吧    
      3.1解析获取的科室医生数据 
      3.2为了方便数据查找，在医生类中增加从属科室的属性
  4. 获取可预约时间 12.19   
      4.1遇到一个问题，dataList直接复制会显示不出list的内容，怀疑可能是复制的时候datalist的指向发生了改变   
      12.20预约功能完成，下一步将预约信息放到缓存中用于显示，然后增加取消预约功能   
      4.2将预约记录存入到缓存中(SharedPreference)
      4.3编写自定义控件显示预约记录
  

  **后退按钮后期统一加上**



