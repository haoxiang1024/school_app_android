@echo off

:: ��ȡ��ǰ�ű���·��
cd /d %~dp0
:: �Զ��ύ
git init 
git add . 
 git commit -m "commit:%date:~0,10%,%time:~0,8%" 
::  git commit -m "%���¹�����Ŀ%" 
git push -u origin main
@echo �Ѿ����
pause


