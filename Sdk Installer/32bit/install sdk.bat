copy .\sdk\*.dll %windir%\system32\
copy .\jacob-1.17-M2-x86.dll %windir%\system32\
regsvr32 %windir%\system32\zkemkeeper.dll
