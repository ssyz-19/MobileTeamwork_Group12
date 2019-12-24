// pages/record/record.js
var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    editText:'取消预约'
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(app.globalData),
    this.setData({
      daterecord: app.globalData.date,
      sectionrecord: app.globalData.section,
      doctorrecord: app.globalData.doctor
      // daterecord: date,
      // sectionrecord:section,
      // doctorrecord: doctor
    })
  },
  cancel:function(){
    var that = this;
    if (that.data.disabled == true) {
        app.globalData.date = '',
        app.globalData.section = '',
        app.globalData.doctor = ''
      that.setData({
        editTrue: false, editText: '取消预约',
        disabled: true,
        daterecord: '',
        sectionrecord: '',
        doctorrecord: '',
        // app.globalData.date = '',
        // app.globalData.section = '',
        // app.globalData.doctor = ''
        
      })
      // app.globalData.date='',
      // app.globalData.section='',
      // app.globalData.doctor=''
    }
    else {
      that.setData({
        editTrue: true, editText: '取消预约',
        disabled: false,
      })
    }
    var that = this;
    wx.request({
      url: 'http://212.64.89.136/cancel',
      data: {
        username: '1901210000',
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res);
        var time = res.data.result;
        that.setData({
          daterecord: '',
          sectionrecord: '',
          doctorrecord: ''
        })
      },
    })
    wx.showToast({

      title: '取消成功',

      icon: 'succes',

      duration: 1000,

      mask: true
    })

  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.setData({

      daterecord: app.globalData.date,
      sectionrecord: app.globalData.section,
      doctorrecord: app.globalData.doctor

    })

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log(app.globalData),
    this.setData({
      daterecord: app.globalData.date,
      sectionrecord: app.globalData.section,
      doctorrecord: app.globalData.doctor

    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    // this.setData({

    //   date:daterecord,
    //   section:sectionrecord,
    //   doctor:doctorrecord

    // })

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})