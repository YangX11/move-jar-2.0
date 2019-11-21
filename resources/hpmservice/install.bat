%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit
cd /d "%~dp0"
cd module/eureka&&hpmeureka.exe install
cd ../config&&hpmconfig.exe install
cd ../zuul&&hpmzuul.exe install
cd ../producer-bonusallocation&&hpmproducerbonusallocation.exe install
cd ../producer-bonusallocationRY&&hpmproducerbonusallocationRY.exe install
cd ../producer-kpiass&&hpmproducerkpiass.exe install
cd ../producer-performanceAnalysis&&hpmproducerperformanceAnalysis.exe install
cd ../producer-report&&hpmproducerreport.exe install
cd ../producer-scheme&&hpmproducerscheme.exe install
cd ../producer-strategymap&&hpmproducerstrategymap.exe install
cd ../producer-sys&&hpmproducersys.exe install
cd ../producer-workload&&hpmproducerworkload.exe install
cd ../producer-workloadcal&&hpmproducerworkloadcal.exe install
cd ../consumer&&hpmconsumer.exe install
pause