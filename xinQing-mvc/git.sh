#!/bin/bash
#git提交脚本
#created by xuan on 2016.10.9

#输入commit信息
if [ -z $1 ];then
	echo "请输入commit信息"
	exit
fi

#执行git命令
git add --a
git commit -m “$1”
git push origin xuanbo

if [ $? -eq 0];then
	echo "---------------------------"
	echo "提交成功"
	echo "---------------------------"
else
	echo -e "\033[31m---------------------------\033[0m"
	echo -e "\033[31m提交失败\033[0m"
	echo -e "\033[31m---------------------------\033[0m"
fi


