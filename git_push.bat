@echo off

:: 获取当前脚本的路径
cd /d %~dp0
:: 自动提交
git init 
git add . 
 git commit -m "commit:%date:~0,10%,%time:~0,8%" 
::  git commit -m "%重新构建项目%" 
git push -u origin main
@echo 已经完成
pause


