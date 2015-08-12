rm -rf resources/compiled/app.app
lein cljsbuild once
osacompile -s -o resources/compiled/app.app -l JavaScript -s resources/compiled/core.js
./resources/compiled/app.app/Contents/MacOS/applet 
