%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit
cd /d "%~dp0"
net stop hpmeureka
net stop hpmconfig
net stop hpmzuul
net stop hpmproducerbonusallocation
net stop hpmproducerbonusallocationRY
net stop hpmproducerkpiass
net stop hpmproducerperformanceAnalysis
net stop hpmproducerreport
net stop hpmproducerscheme
net stop hpmproducerstrategymap
net stop hpmproducersys
net stop hpmproducerworkload
net stop hpmproducerworkloadcal
net stop hpmconsumer
pause