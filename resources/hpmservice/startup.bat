%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit
cd /d "%~dp0"
net start hpmeureka
net start hpmconfig
net start hpmzuul
net start hpmproducerbonusallocation
net start hpmproducerbonusallocationRY
net start hpmproducerkpiass
net start hpmproducerperformanceAnalysis
net start hpmproducerreport
net start hpmproducerscheme
net start hpmproducerstrategymap
net start hpmproducersys
net start hpmproducerworkload
net start hpmproducerworkloadcal
net start hpmconsumer
pause