@echo off

set in_path=proto
set out_path=E:\unityworkspace\sanguo\Assets\script\proto
cd %in_path%

rem  Support
for /R "%cd%" %%i in (*.proto) do echo %%~ni     
for /R "%cd%" %%i in (*.proto) do ..\cs-protoc\protobuf-net-r640\ProtoGen\protogen -i:%%i -o:%out_path%\%%~ni.cs -q

pause