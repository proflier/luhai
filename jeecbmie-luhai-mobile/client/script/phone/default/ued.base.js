/*
*	ExMobi4.0 JS 框架之 控件对象类ued.base.js
*	Version	:	1.0.0
*	Author	:	WangMingzhu @ Nandy Wong
*	Email	:	wangmingzhu@nj.fiberhome.com.cn
*   Copyright 2012 (c) 南京烽火星空通信发展有限公司
*/
var header=document.getElementsByTagName("header")[0];
var footer=document.getElementsByTagName("footer")[0];
var body=document.getElementsByTagName("body")[0];
var screen={"objName":"screen"};
function $ui(x){
	var obj = null;
	//获取对象
	if(x==null){
		return x;
	}else if((typeof x)=="string"){
		obj = document.getElementById(x);
		obj = obj==null&&document.getElementsByName(x.toString()).length>0?document.getElementsByName(x.toString())[0]:obj;
	}else{
		obj = x;
	}
	//给不为空的对象添加函数
	if(!obj) return null;
	//设置对象隐藏
	obj.hide = function(){
		obj.style.display = "none";
		return obj;
	};
	//设置对象显示
	obj.show = function(){			
		obj.tagName()=="page"?document.getElementById(x).show():obj.style.display = "block";
		return obj;
	};

	//设置对象显隐反转
	obj.toggle = function(){	
		obj.style.display = obj.style.display=="none"?"block":"none";
		return obj;
	};
	
	//获得对象高度
	obj.height = function(){
		if(arguments.length>0 && typeof arguments[0]=="number"){
			obj.style["height"]=arguments[0];
			return obj;
		}
		if(obj.tagName()=="header"){
			return Util.getHeaderHeight();
		}
		if(obj.tagName()=="body"){
			return Util.getBodyHeight();
		}
		if(obj.tagName()=="footer"){
			return Util.getFooterHeight();
		}
		if(obj.tagName()=="window"){
			return Util.getWindowHeight();
		}
		if(obj.tagName()=="screen"){
			return Util.getScreenHeight();
		}
		return obj.style["height"];
	};
	
	//获得对象宽度度
	obj.width=function(){
		if(arguments.length>0 && typeof arguments[0]=="number"){
			obj.style["width"]=arguments[0];
			return obj;
		}
		if(obj.tagName()=="body"){
			return Util.getWindowWidth();
		}
		if(obj.tagName()=="screen"){
			return Util.getScreenWidth();
		}
		return obj.style["width"];
	}
		
	//获取或者设置的value
	obj.val = function(){//0:设置value值,无:返回值
		if(arguments.length>0){
			obj.value = arguments[0];
			return obj;
		}else{				
			return obj.value;
		}
	};
		
	//追加控件的value
	obj.addVal = function(){
		obj.value += arguments[0];
		return obj;
	};
		
	//获取或者设置对象的innerHTML
	obj.html = function(){//0:设置HTML值,无:返回值
		if(arguments.length>0){
			obj.innerHTML = arguments[0];
			return obj;
		}else{
			return obj.innerHTML;
		}	
	};
		
	//追加对象的innerHTML
	obj.addHtml = function(){			
		obj.innerHTML += arguments[0];
		return obj;
	};
	
	//获取设置属性
	obj.attr = function(){			
		if(arguments.length>1){
			obj[arguments[0]] = arguments[1];
			return obj;
		}else{
			return obj[arguments[0]];
		}
	};
	
	obj.addClass=function(){
		obj["className"]+=" "+arguments[0];
	};
	
	obj.removeClass=function(){
		if(obj["className"].indexOf(arguments[0])>0){
			var reg = new RegExp("\\b"+arguments[0]+"\\b","g");
			obj["className"]=obj["className"].replace(reg,"");
		}
	};
	
	obj.check=function(){
		var reg=null;
		if(arguments[0]=="phone"){
			reg=/^(1[3-9]{1}[0-9]{1})\d{8}$/;
			if(!reg.test(obj.value)){
				$phone.toast("请输入正确的手机号码！");
				obj["focus"]=true;
				return false;
			}
			return true;
		}else if(arguments[0]=="email"){
			reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if(!reg.test(obj.value)){
				$phone.toast("请输入正确的邮箱地址！");
				obj["focus"]=true;
				return false;
			}
			return true;
		}
	};

	//设置获取对象的样式
	obj.css = function(){ //0:样式名,1:样式值
		if(arguments.length>1){
			obj.style[arguments[0]] = arguments[1];
			return obj;
		}else{
			return obj.style[arguments[0]];
		}
	};
	//获取对象的标签名
	obj.tagName = function(){
		return obj.objName?obj.objName:"";
	};
	return obj;
}


//String类原生扩展
//去掉string两边的空格
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g,"");
}

//将String中符合s1要求的字符串替换成s2
String.prototype.replaceAll = function(s1,s2){
	s1 = (typeof s1) == "string"?new RegExp(s1, "gm"):s1;
	return this.replace(s1, s2);
}

//转换为数字格式
String.prototype.toInt = function(){
	return Number(this);
}

//设置session
String.prototype.session = function(){
	if(arguments.length==0){	
		return window.getStringSession(this)||window.getArraySession(this)[0];		
	}
	if((typeof arguments[0])=="string"){
		window.setStringSession(this,arguments[0]);
	}else{
		window.setArraySession(this,arguments[0]);
	}
}

//返回截取给定字符串之前的数值
String.prototype.substrBefore = function(){
	return arguments.length > 0 && arguments[0]?this.substring(0,this.indexOf(arguments[0],arguments[1])):"";
}

//返回截取给定字符串之后的数值
String.prototype.substrAfter = function(){
	return arguments.length > 0 && arguments[0]?this.substring(this.indexOf(arguments[0],arguments[1]) + arguments[0].length,this.length):"";
}

//Number类原生扩展
//加
Number.prototype.add=function(){
	return typeof arguments[0]=="number"?this+arguments[0]:"isNan";
}
//减
Number.prototype.minus=function(){
	return typeof arguments[0]=="number"?this-arguments[0]:"isNan";
}

//录音功能
var record=Util.getRecord();
record.maxtime=180;
var autoPlayer=Util.getAudioPlayer();
var filepath=window.getStringSession("filepath");
//秒
var s=0;
//分
var m=0;
//时间展示
var timepanel;
var applist=Util.getApplicationInfos();
var $phone={
		//通过toast提示信息
		toast:function(t, d, s){
			if(!t) return;
			var toast= new Toast();
			toast.setText(t); //设置Toast显示信息
			toast.setDuration(d?d:1000);//设置Toast显示时间
			if(s) toast.setStyle(s);//设置弹出toast弹出位置		
			toast.show(true);//展现Toast框
		},
		//拨打电话t为号码，n为名称
		tel:function(t,n){
			if(arguments.length==0 && $phone["$telcache"]){
				Util.tel($phone["$telcache"]);
			}else if(!t){
				alert("您所拨打的号码不存在！");
			}else{
				var str = n?"您确认拨打"+n+"的电话吗？":"您确认拨打电话"+t+"吗？";
				$phone["$telcache"] = t;
				confirm(str,$phone.tel,null);
			}
		},
		message:function(t,m){
			if(Util.sendSms(t,m)){
				$phone.toast("发送短信成功！");
				window.close();
				return;
			}
			$phone.toast("发送短信失败！");
		},
		sysmessage:function(){
			Util.openSystemSms();
		},
		systel:function(){
			Util.openSystemDial();
		},
		syscontact:function(){
			Util.openSystemContact();
		},
		close:function(){
			if(arguments.length>0){
				for(var i=0;i<arguments.length;i++){
					var objWin=util.getWindowById(arguments[i]);
					if(objWin){
						objWin.close();
					}
				}
			}
			window.close();
		},
		os:function(){
			return Util.getOs();
		},
		esn:function(){
			return Util.getEsn();
		},
		imsi:function(){
			return Util.getImsi();
		},
		//获得设备分辨率
		resolution:function(){
			return Util.getScreenWidth()+"*"+Util.getScreenHeight();
		},
		//获得基座版本号
		version:function(){
			return Util.getVersion();
		},
		//获得当前应用版本号
		appversion:function(){
			if(arguments.length>0){
				return applist[arguments[0]].localVersion;
			}
			return applist[0].localVersion;
		},
		//获得应用更新日期
		appdate:function(){
			if(arguments.length>0){
				return applist[arguments[0]].date;
			}
			return applist[0].date;
		},
		//获得应用描述
		appdescription:function(){
			if(arguments.length>0){
				return applist[arguments[0]].description;
			}
			return applist[0].description;
		},
		//获得设备型号
		model:function(){
			return Util.getPhoneModel();
		},
		home:function(){
			Util.execScript("script:goapphome");
		},
		//打开应用
		openapp:function(appid){
			Util.execScript("script:openapplication("+appid+")");
		},
		//退出应用
		exitapp:function(){
			Util.execScript("script:exitapp");
		},
		//退出基座
		exit:function(){
			Util.execScript("script:exit");
		},
		update:function(){
			Util.execScript("script:gogaeaupdate");
		},
		download:function(){
			Util.execScript("script:godownload");
		},
		reload:function(){
			Util.execScript("script:reloadapp");
		},
		//录音功能
		startAudio:function(){
			m=0;
			s=0;
			if(arguments[0])timepanel=$ui(arguments[0]);
			record.startRecord();
			timer=window.setInterval("$phone.funtimeout()",1000);
		},
		stopAudio:function(){
			window.clearInterval(timer);
			record.stopRecord();
			$phone.toast("录音完毕,文件保存至:"+record.path);
			filepath=record.value;
			window.setStringSession("filepath",filepath);
		},
		funtimeout:function(){
			s++;
			if(s >= 59){
				s=0;
				m++;
			}
			if(timepanel){
				timepanel.html($phone.formatTime());
			}
		},
		formatTime:function(){
			var mm=m;
			var ss=s;
			if(s.toString().length==1){
				ss="0"+s.toString();
			}
			if(m.toString().length==1){
				mm="0"+m.toString();
			}
			return mm+":"+ss;
		},
		playAudio:function(){
			if(record.isRecord()){
				$phone.toast("正在录音，请停止后再播放！");
				return;
			}
			if(filepath){
				autoPlayer.start(filepath,false);
			}
		},
		pausePlay:function(){
			autoPlayer.pause();/*停止播放音频*/
		},
		resumePlay:function(){
			autoPlayer.resume();/*从暂停状态恢复*/
		},
		duration:function(){/*读取当前播放的音频的长度*/
			if(filepath){
				var t=parseInt(autoPlayer.getDuration()/1000);
				if(t>60){
					m=parseInt(t/60);
					s=t%60;
				}
				return $phone.formatTime();
			}
			return "00:00";
		}
};
var $date={
		//获取当前日期时间
		current:function(){
			var myDate = new Date();
			return myDate;
		},
		//获取当前日期，默认为yyyy-MM-dd
		getFullDate:function(){
			var date = this.current();
			var s = arguments.length>0?arguments[0]:"-";
			return date.getFullYear()+s+(date.getMonth()+1<10?"0":"")+(date.getMonth()+1)+s+(date.getDate()<10?"0":"")+date.getDate();
		},
		//获取当前时间，默认为hh:mm:ss,有参数的话为hh:mm
		getTime:function(){
			var date = this.current();
			var s = ":";
			if(arguments.length>0){
				return (date.getHours()<10?"0":"")+date.getHours()+s+(date.getMinutes()<10?"0":"")+date.getMinutes();
			}else{
				return (date.getHours()<10?"0":"")+date.getHours()+s+(date.getMinutes()<10?"0":"")+date.getMinutes()+s+(date.getSeconds()<10?"0":"")+date.getSeconds();
			}
		},
		//获取当前日期时间，默认为yyyy-MM-dd hh:mm:ss	
		getDateTime:function(){	
			return (arguments.length>0?this.getDate(arguments[0]):this.getDate())+" "+(arguments.length>1?this.getTime(arguments[1]):this.getTime());
		},
		//获得当月最大天数
		getMaxDate:function(){
			var date = this.current();
			var arrmonth =new Array();
			var nowyear=date.getFullYear();
			var nowmonth=date.getMonth();
			if(nowyear % 4 == 0 && (nowyear % 100 != 0 || nowyear % 400 == 0)){		
			//闰年
				arrmonth = [31,29,31,30,31,30,31,31,30,31,30,31];		 
			}
			else{
			//非闰年
				 arrmonth = [31,28,31,30,31,30,31,31,30,31,30,31];		
			}
			return arrmonth[nowmonth];
		},
		//最大可推后到下个月最大天数
		getAfterDate:function(){
			var afterdate=1;
			if(arguments[0]){
				afterdate=arguments[0];
			}
			var date = this.current();
			var month=date.getMonth()+1;
			var year=date.getFullYear();
			var sumday=date.getDate()+afterdate;
			if(sumday>this.getMaxDate()){
				month+=1;
				sumday=sumday-this.getMaxDate();
			}
			if(month>12){
				year+=1;
				month-=12;
			}
			return year+"-"+(month<10?"0":"")+month+"-"+(sumday<10?"0":"")+sumday;
		},
		//获得星期，返回数字1-7
		getDay:function(){
			return this.current().getDay()==0?7:this.current().getDay();
		},
		getYear:function(){
			var date = this.current();
			return date.getFullYear();
		},
		getMonth:function(){
			var date=this.current();
			return (date.getMonth()+1<10?"0":"")+(date.getMonth()+1);
		},
		getDate:function(){
			var date=this.current();
			return date.getDate();
		},
};
