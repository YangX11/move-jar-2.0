%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit
cd /d "%~dp0"
cd module/eureka&&hpmeureka.exe uninstall
cd ../config&&hpmconfig.exe uninstall
cd ../zuul&&hpmzuul.exe uninstall
cd ../producer-bonusallocation&&hpmproducerbonusallocation.exe uninstall
cd ../producer-bonusallocationRY&&hpmproducerbonusallocationRY.exe uninstall
cd ../producer-kpiass&&hpmproducerkpiass.exe uninstall
cd ../producer-performanceAnalysis&&hpmproducerperformanceAnalysis.exe uninstall
cd ../producer-report&&hpmproducerreport.exe uninstall
cd ../producer-scheme&&hpmproducerscheme.exe uninstall
cd ../producer-strategymap&&hpmproducerstrategymap.exe uninstall
cd ../producer-sys&&hpmproducersys.exe uninstall
cd ../producer-workload&&hpmproducerworkload.exe uninstall
cd ../producer-workloadcal&&hpmproducerworkloadcal.exe uninstall
cd ../consumer&&hpmconsumer.exe uninstall
pause