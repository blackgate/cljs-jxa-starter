(ns cljs-jxa-starter.core)

(def objc-import (aget js/ObjC "import"))
(def app (js/Application.currentApplication))

(def app-state (atom {:name-input  nil}))

(set! (.-includeStandardAdditions app) true)

(objc-import "Cocoa")

(defn get-msg []
  (str "Hello, " (js/ObjC.unwrap (.-stringValue (@app-state :name-input)))))

(defn speak-msg [sender]
  (.say app (get-msg)))

(defn display-msg [sender]
  (.displayAlert app "Welcome" #js{:message (get-msg)}))

(js/ObjC.registerSubclass
  (clj->js {
    :name "AppDelegate"
    :methods {
      "speakHandler:" {
        :types ["void" ["id"]]
        :implementation speak-msg}
      "displayHandler:" {
        :types ["void" ["id"]]
        :implementation display-msg}}}))

(def app-delegate $.AppDelegate.alloc.init)

(defn set-props! [obj & props]
  (doseq [[k v] (partition 2 props)] (aset obj k v)))

(defn add-views! [obj & views]
  (doseq [v views] (.addSubview (.-contentView obj) v)))

(defn create-window [rect]
  (js/$.NSWindow.alloc.initWithContentRectStyleMaskBackingDefer
    rect
    (bit-or js/$.NSTitledWindowMask
            js/$.NSClosableWindowMask
            js/$.NSResizableWindowMask
            js/$.NSMiniaturizableWindowMask)
    js/$.NSBackingStoreBuffered
    false))

(defn create-btn [rect title handler]
  (let [btn (js/$.NSButton.alloc.initWithFrame rect)]
    (set-props!
      btn
      "title" title
      "bezelStyle" js/$.NSRoundedBezelStyle
      "buttonType" js/$.NSMomentaryLightButton
      "target" app-delegate
      "action" handler)    
    btn))

(defn create-txt [rect val editable]
  (let [txt (js/$.NSTextField.alloc.initWithFrame rect)]
    (set-props!
      txt
      "stringValue" val
      "drawsBackground" editable
      "bezeled" editable
      "selectable" true
      "editable" editable)
    txt))

(let [[width height] [250 180]
      btn-width (- width 50)
      win (create-window (js/$.NSMakeRect 0 0 width height))
      speak-btn (create-btn (js/$.NSMakeRect 25 20 btn-width 25) "Speak" "speakHandler:")
      disp-btn (create-btn (js/$.NSMakeRect 25 50 btn-width 25) "Display" "displayHandler:")
      lbl (create-txt (js/$.NSMakeRect 25 (- height 50) btn-width 25) "Your Name" false)
      txt (create-txt (js/$.NSMakeRect 25 (- height 75) btn-width 25) "" true)]
  (swap! app-state assoc :name-input txt)
  (add-views! win speak-btn disp-btn lbl txt)
  (.-center win)
  (set! (.-title win) "Welcome")
  (.makeKeyAndOrderFront win win))
