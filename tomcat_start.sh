#!/usr/bin/env bash
# 使用说明:
# 目录结构:
#   - /usr/local/wfx-customer
#       - restart.sh  // 本启动脚本
#       - version/  // war包存放路径,因我在查找文件命令时,指定了version的相对目录,遂需要将war包放入该目录,才可做版本回滚。
#       - tomcat/  // tomcat文件夹
# 功能简介:
#       1.脚本是以相对目录执行,只需保证相对目录结构即可.
#       2.首先项目会使用dir_in函数获取并设置当前路径.并终止当前路径下面的tomcat.
#       3.查找当前路径下最新的war包,并放入tomcat的webapps目录下,并命名为ROOT.war.
#       4.重新启动tomcat.
# 启动方式:
#       1.直接执行脚本(会根据文件修改时间,发布最新版本)
#       2.脚本+程序版本号(只需要数字部分,如:0.0.1;作用:部署指定版本,版本回滚使用)
# 备注:当前脚本针对tomcat的项目,如其他类型项目,可修改restart函数,重新定义处理流程。通常只需要将拷贝war及启动命令替换。
# 李盼庚 lipg@outlook.com
# 用来自动更新重启任务的脚本
# 定义全局变量,需根据实际情况进行配置
function env(){
    # JAVA 配置
    export JAVA_HOME=/usr/java/jdk1.7.0_79
    export PATH=$JAVA_HOME/bin:$PATH
    # 应用配置
    # 应用文件包前缀
    app_prefix='customer-core-'
    # 应用文件包后缀
    app_suffix='-SNAPSHOT.war'
    # 查找进程匹配的正则
    app_init_regex='.*/usr/local/wfx-customer/tomcat.*'
    # 查找应用文件包的正则
    app_war_regex=".*${app_prefix}.*${app_suffix}.*"
    # 在tomcat的webapps目录下的war包名称
    app_war_name='ROOT.war'
    # war包相对路径
    app_war_path="./tomcat/webapps/${app_war_name}"
}
# 进入目录,获取当前脚本所在目录,并cd进入该目录
function dir_in(){
    BaseDir=$(dirname $0)
    cd ${BaseDir}
    echo "now in dir : ${BaseDir}"
}
# 重启应用
function restart(){
    # 打印现在的进程信息
    ps -aux | grep ${app_init_regex}
    # 关闭tomcat
    ./tomcat/bin/shutdown.sh
    # 休眠5秒等待进程退出
    sleep 5
    # 关闭现有进程信息
    pgrep -f ${app_init_regex} | xargs kill -9
    # 休眠5秒,等待强制结束进程完成
    sleep 5
    # 获取最新的jar文件,通过时间排序,获取最新的,如果设置了版本号,则执行指定版本
    if [ -z ${app_version} ] ; then
        file_name=$(find ./ -iregex ${app_war_regex} | xargs ls -ltr | awk {'print $9'} | tail -1)
    else
        file_name="./version/${app_prefix}${app_version}${app_suffix}"
    fi
    # 打印文件路径
    echo "file name :${file_name}"
    # 拷贝新的war包
    \cp ${file_name} ${app_war_path}
    # 启动tomcat脚本
    nohup ./tomcat/bin/startup.sh > /dev/null &
    # 打印现有进程信息
    ps -aux | grep ${app_init_regex}
    # 已经完成,打印完成信息
    echo "done.runing the ${file_name}"
    # 等待5秒,等到脚本将进程提交
    sleep 5
}
# 设定环境变量
env
# 获取当前目录
dir_in
if [ x$1 = x'version' ] ; then
    app_version=$2
fi
restart