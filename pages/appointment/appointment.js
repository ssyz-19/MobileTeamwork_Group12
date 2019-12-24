// pages/appointment/appointment.js
var app = getApp()
var doctor = 0
var timer = ''
var timeid = ''
var information=''
Page({
  data: {
    hello:'',
    index:0,
    doctorid:0,
    multiIndex: [0, 0, 0],
    information:'',
    editText: '提交预约'
  },
  onLoad() {
    var that = this;
    wx.request({
      url: 'http://212.64.89.136/office',
      data: {},
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res);
        var officeList = res.data.result;
        var officeArr = officeList.map(item => {　　　　// 此方法将校区名称区分到一个新数组中
          return item.office_name;
        });
        //officeArr.unshift('全部医生');
        that.setData({
          multiArray: [officeArr, []],
          officeList,
          officeArr
        })
        var default_office_id = officeList[0]['office_id'];　　　　//获取默认的校区对应的 teach_area_id
        if (default_office_id) {
          that.searchDoctorInfo(default_office_id)　　　　　　// 如果存在调用获取对应的班级数据
        }
      }
    })
  },
  searchDoctorInfo(office_id) {
    var that = this;
    wx.request({
      url: 'http://212.64.89.136/' + office_id,
      data: {},
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res);
        var doctorList = res.data.result;
        var doctorArr = doctorList.map(item => {　　　　// 此方法将校区名称区分到一个新数组中
          return item.doctor_name + '  ' + item.doctor_title;
        });
        doctorArr.unshift('选择医生');
        var officeArr = that.data.officeArr;
        that.setData({
          multiArray: [officeArr, doctorArr],
          doctorArr,
          doctorList,
        })
      },
    })
  },
  bindMultiPickerColumnChange: function (e) {
    //e.detail.column 改变的数组下标列, e.detail.value 改变对应列的值
    console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      multiArray: this.data.multiArray,
      multiIndex: this.data.multiIndex,
    };
    data.multiIndex[e.detail.column] = e.detail.value;
    var docotor_id_session = this.data.doctor_id;
    var office_id_session = this.data.office_id;　　　　// 保持之前的校区id 与新选择的id 做对比，如果改变则重新请求数据
    switch (e.detail.column) {
      case 0:
        console.log(office_id_session);
        var officeList = this.data.officeList;
        var office_id = officeList[e.detail.value]['office_id'];
        if (office_id_session != office_id) {　　　　// 与之前保持的校区id做对比，如果不一致则重新请求并赋新值
          this.searchDoctorInfo(office_id);
        }
        data.multiIndex[1] = 0;
        break;
      case 1:
       
    }
    this.setData(data);
  },

  bindMultiPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    var doctor_key = 0;
    var doctorList = this.data.doctorList;
    var select_key = e.detail.value[1];
    var real_key = select_key-1;
    var doctorid = doctorList[real_key]['id']
    this.setData({
      multiIndex: e.detail.value
    })
    this.sendID(doctorid)
  },

  sendID(doctorid){
    var that = this;
    wx.request({
      url: 'http://212.64.89.136/dateInfo',
      data: {
      id:doctorid
      },
      method:'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res);
        var time = res.data.result;
        var timeArr = time.map(item => {　　　　
          return item.time;
        });
        that.setData({
          array:timeArr,
          time,
          timeArr,
        })
        doctor = doctorid;
        timer = timeArr;
      },
    })
  },
 bindPickerChange: function (e) {
   var timeList = this.data.time;
   console.log(timeList[e.detail.value]['time']);
   timeid = timeList[e.detail.value]['time'];
   console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },

  login:function(){
    
    var that = this; 
    that.setData({ 
    editText: '提交预约' ,
    })
    var that = this;
    wx.request({
      url: 'http://212.64.89.136/confirm',
      data: {
        username: app.globalData.username,
        doctorId:doctor,
        date:timeid,
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res);
        var time = res.data.result;
        information = time['status'];
        that.setData({
  
        })
      },
      
    })
    wx.showToast({

      title: information,

      icon: 'none',

      duration: 1000,

      mask: true
    })
    var that = this;
    wx.request({
      url: 'http://212.64.89.136/record',
      data: {
        username: app.globalData.username,
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res);
       var r1 = res.data.result;
       app.globalData.doctor = r1['name'];
       app.globalData.section = r1['office'];
       app.globalData.date = r1['date'];
       
      }
    })

  }
  
})