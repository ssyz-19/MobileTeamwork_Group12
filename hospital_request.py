"""
服务端接口
@author huang
"""
import requests, json,sqlite3 

"""
POST
登录 账号为整型，密码为字符串
登录成功：接收到返回值 {"status":"True"}
登录失败：             {"status":"False"}
"""
def login():
    url = "http://212.64.89.136/login"
    data = {"username":1901210000, "password":"1901210000"}
    r = requests.post(url,data)
    print(r)
    result = r.json()
    print(result)
    
"""
GET
直接访问offcie页面即可
"""
def office():
    url = "http://212.64.89.136/office"
    r = requests.get(url)
    print(r)
    result = r.json()
    print(result)
    
"""
GET
直接访问科室对应页面
"""
def doctor():
    url = "http://212.64.89.136/xinnk"
    r = requests.get(url)
    print(r)
    result = r.json()
    print(result)
        
"""
POST
获取成功的返回数据:
{'result': [{'date1': 1}, {'date2': 1}, {'date3': 1}, {'date4': 1}, {'date5': 1}, {'date6': 1}, {'date7': 1}]}
"""        
def dateInfo():
    url = "http://212.64.89.136/dateInfo"
    #"id":医生的id号
    data = {"id":0}
    r = requests.post(url,data)
    print(r)
    result = r.json()
    print(result)
    
"""
POST
仅允许预约一个医生的某一天
确认成功：{'status': 'Succ'}
之前已经有预约过，则不再允许预约：{'status': 'Order exists'}
"""    
def confirm():
    url = "http://212.64.89.136/confirm"
    data = {"username":1901210000,"doctorId":0,"date":"DATE1"}
    r = requests.post(url,data)
    print(r)
    result = r.json()
    print(result)

"""
POST
取消原有预约
取消成功: {'status': 'Succ'}
之前并没有预约过: {'status': 'No order'}
"""
def cancel():
    url = "http://212.64.89.136/cancel"
    data = {"username":1901210000}
    r = requests.post(url,data)
    print(r)
    result = r.json()
    print(result)

if __name__ == "__main__":    
    office()
    login()
    doctor()
    dateInfo()
    confirm()    
    cancel()
