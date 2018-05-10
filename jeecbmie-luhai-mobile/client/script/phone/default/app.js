/*
*	ExMobi4.0 JS 框架之 应用场景类app.js(依赖utility.js)
*	Version	:	1.0.0.6
*	Author	:	@黄楠nandy
*	Email	:	huangnan@nj.fiberhome.com.cn
*	Weibo   :   http://weibo.com/nandy007
*   Copyright 2012 (c) 南京烽火星空通信发展有限公司
*/
var $a = {
	//拨打电话t为号码，n为名称
	tel:function(t,n){
		if(arguments.length==0&&$a["$telcache"]){
			Util.tel($a["$telcache"]);
		}else if(!t){
			alert("您所拨打的号码不存在！");
		}else{
			var str = n?"您确认拨打"+n+"的电话"+t+"吗？":"您确认拨打电话"+t+"吗？";
			$a["$telcache"] = t;
			confirm(str,$a.tel,null);
		}
	},
	//通过toast提示信息
	toast:function(t, d, s){
		if(!t) return;
		var toast= new Toast();
		toast.setText(t); /*设置Toast显示信息*/
		toast.setDuration(d?d:1000);/* 设置Toast显示时间*/
		if(s) toast.setStyle(s);/*设置弹出toast弹出位置*/		
		toast.show(true);/*展现Toast框*/
		
	},
	
	//判断是否连接超时
	isTimeOut:function(str){
		//4.3.0及以后版本超时进错误回调函数，此判断可去掉
		if(str.indexOf("script:reloadapp")!=-1){
			window.openData(str, false);
			return true;
		}

		return false;
	},
	
	//封装新的ajax，直接send，只有当successFunction为null的时候不自动发送，这时候设置一些变量后再发送
	go:function(url, method, data, successFuntion, failFunction, requestHeader, isShowProgress){
		var a = new Ajax(url, method, data, $a.globalSuccess, failFunction==null?$a.globalError:failFunction, requestHeader, isShowProgress);
		if((typeof successFuntion)=="function"){
			a.setStringData("successFuntion", successFuntion.name);
			a.send();
		}else if(successFuntion!=null){
			a.setStringData("successFuntion", successFuntion);
		}
		return a;
	},
	//通用全局成功处理
	globalSuccess:function(data){
		var str = data.responseText.trim();
		if(!$a.isTimeOut(str)&&data.getStringData("successFuntion")!=""){
			eval(data.getStringData("successFuntion")+"(data)");
		}
	},
	
	//通用全局错误处理
	globalError:function(data){
		var str = data.responseText.trim();
		var msg = "请求错误！";
		var nextaction = "script:close";
		str.replace(/<faultstring>(.*)<\/faultstring>/g, function(s, s1){
			if(s1!="") msg = s1;
			if(s1.indexOf("超时")>-1) nextaction = "script:reloadapp";
		});
		str.replace(/<faultactor>(.*)<\/faultactor>/g, function(s, s1){
			if(s1!="") nextaction = s1;
		});
		window.openData('<html type="alert"><body><alert title="提示" icontype="info"><msg>'+msg+'</msg><nextaction href="'+nextaction+'"></nextaction></alert></body></html>');
	},
	
	//base.js中provide方法里ajax成功后的处理，也可单独使用，temp为可选参数，为显示的模板，该函数不直接暴露
	provide:function(data,temp){
		var obj = document.getElementById(data.$id?data.$id:data.getStringData("$id"));
		$a["$"+obj.id+"template"] = temp?temp:$a["$"+obj.id+"template"].trim();
		var json = data.responseText;
		$a["$"+obj.id+"jsonData"] = json;
		if(obj.objName=="list"){
			$a.listProvide(obj, json);
		}else if($a["$"+obj.id+"template"]==""){//如果没有模板的情况则整个响应数据注入
			$a.noTempProvide(obj, json);
		}else{
			$a.commProvide(obj, json);
		}
		$a["$"+obj.id+"isClear"] = false;
		if($a["$"+obj.id+"callback"]){
			eval($a["$"+obj.id+"callback"]+"(data)");
		}
	},
	
	commProvide:function(obj, json){
		if($a["$"+obj.id+"isClear"]=="top"){
			obj.innerHTML = $a["$"+obj.id+"template"].tjt(json) +　obj.innerHTML;
		}else if($a["$"+obj.id+"isClear"]==true){
			obj.innerHTML = $a["$"+obj.id+"template"].tjt(json);//.htmlDecode();
		}else if(obj.objName=="div"){
			obj.append($a["$"+obj.id+"template"].tjt(json));
		}else{
			obj.innerHTML += $a["$"+obj.id+"template"].tjt(json);//.htmlDecode();
		}
	},
	
	noTempProvide:function(obj, json){
		if($a["$"+obj.id+"isClear"]=="top"){
			obj.innerHTML = json +　obj.innerHTML;
		}else if($a["$"+obj.id+"isClear"]==true){
			obj.innerHTML = json;
		}else{
			obj.innerHTML += json;
		}
	},
	
	listProvide:function(obj, json){
		var dp = obj.getDataProvider();
		if(json==null||json.length==0){
			dp.refreshData();
			return;
		};
		json = $u.transObj(typeof json=="string"?json.toJSON():json).trim();
		var index = dp.getCount();
		if($a["$"+obj.id+"isClear"]=="top"){
			index = 0;
		}else if($a["$"+obj.id+"isClear"]==true){
			dp.clear();
		}
		json = json.indexOf("{")==0?"["+json+"]":json;
		dp.addItems(json, index);
	
		dp.refreshData();
	},
		
	compareProvide:function(str,json){
		var type = "";
		var compareValue = "";
		var trueValue = "";
		str.replace(/([^\.]*)\.([^=]*)=(.*)/g,function(s, s1, s2, s3){
			type = s1;
			if(json[s2]){
				compareValue = json[s2];
			}
			//compareValue = json[s2]?json[s2]:"";
			if(s3.replace(/'|"/g,"").length!=s3.length){
				trueValue = s3.replace(/'|"/g,"");
			}else if(json[s3]){
				trueValue = json[s3];
			}							
			//trueValue = s3.replace(/'|"/g,"").length==s3.length?(json[s3]?json[s3]:""):s3.replace(/'|"/g,"");
			for(var j=0;j<compareValue.split(",").length;j++){
				if(compareValue.split(",")[j]==trueValue){
					trueValue =  trueValue+"\" "+type+"=\"true";
					break;
				}
			}
			return "";
		});
		return trueValue;
	},

};